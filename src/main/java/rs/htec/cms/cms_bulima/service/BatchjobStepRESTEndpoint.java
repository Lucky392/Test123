/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Batchjob;
import rs.htec.cms.cms_bulima.domain.BatchjobStep;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.BatchjobStepPOJO;

/**
 *
 * @author marko
 */
@Path("batchjobSteps")
public class BatchjobStepRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    
    /**
     * Returns BatchjobStep object in JSON format.
     *
     * Example for response: 
     * <br/>
     * {<br/>
     * "stepName": "FantasyLeagueActivityBatchJob",<br/>
     * "idBatchjob": 1,<br/>
     * "enabled": 0,<br/>
     * "id": 1<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id for BatchjobStep that should be retrieved
     * @return BatchjobStep with status 200
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBatchjobStepById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        BatchjobStepPOJO pojo;
        try {
            BatchjobStep step = (BatchjobStep) em.createNamedQuery("BatchjobStep.findById").setParameter("id", id).getSingleResult();
            pojo = new BatchjobStepPOJO(step);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("BatchjobStep at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("BatchjobStep at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }
    
    /**
     * API for method:
     * .../rest/batchjobSteps?page=VALUE&limit=VALUE&search=VALUE&idBatchjob=VALUE
     * This method returns JSON list, and count number. Default value for page
     * is 1, and for limit is 10. There is a possibility for search by
     * searchName, and by batchjobID. It produces APPLICATION_JSON media type.
     * Example for JSON list for 1 page, 2 limit: <br/>{ <br/>"count": 20,
     * <br/>"data": [ {<br/>
     * "jobName": "dailyJob",<br/> "stepName":
     * "FantasyLeagueActivityBatchJob",<br/>
     * "idBatchjob": 1,<br/> "enabled": 0,<br/> "id": 1<br/> },<br/> {<br/>
     * "jobName": "dailyJob",<br/>
     * "stepName": "AddAuctionsToTransferMarketBatchJob",<br/> "idBatchjob":
     * 1,<br/>
     * "enabled": 0,<br/> "id": 2<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for BatchjobStep
     * @param limit number of BatchjobSteps method returns
     * @param search word for searching searchName
     * @param idBatchjob id of Batchjob
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no batchjob steps for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBatchjobStep(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search, @QueryParam("idBatchjob") long idBatchjob) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT b FROM BatchjobStep b ");
        if (search != null) {
            search = "%" + search + "%";
            query.append("WHERE b.stepName LIKE '")
                    .append(search)
                    .append("'");
        }
        if (idBatchjob != 0) {
            query.append(search != null ? " AND" : "WHERE").append(" b.idBatchjob.id = ").append(idBatchjob);
        }
        List<BatchjobStep> steps = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (steps == null || steps.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no batchjob steps for this search!"), em);
            throw new DataNotFoundException("There is no batchjob steps for this search!");
        }
        String countQuery = query.toString().replaceFirst("b", "count(b)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(BatchjobStepPOJO.toBatchjobStepPOJOList(steps));
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * API for this method is .../rest/batchjobSteps This method recieves JSON
     * object, and put it in the base. Example for JSON that you need to send:
     * <br/>{
     * <br/>"stepName": "FinishAuctionsBatchJob",<br/> "idBatchjob": 1,<br/>
     * "enabled": 0,<br/> "id": 1 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param batchjobStep is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertBatchjobStep(@HeaderParam("authorization") String token, @Context HttpServletRequest request, BatchjobStep batchjobStep) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), batchjobStep);
        if (validator.checkLenght(batchjobStep.getStepName(), 255, true)) {
            helper.persistObject(em, batchjobStep);
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
        helper.setResponseToHistory(history, Response.status(Response.Status.CREATED).build(), em);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * API for this method is .../rest/batchjobSteps This method recieves JSON
     * object, and update database. Example for JSON that you need to
     * send:<br/>{
     * <br/>"stepName": "FinishAuctionsBatchJob",<br/> "idBatchjob": 1,<br/>
     * "enabled": 0,<br/> "id": 1 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param batchjobStep is an object that Jackson convert from JSON to object
     * @return Response with status OK (200)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Batchjob step at index 10 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBatchjob(@HeaderParam("authorization") String token, @Context HttpServletRequest request, BatchjobStep batchjobStep) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), batchjobStep);
        BatchjobStep oldBatchjobStep = em.find(BatchjobStep.class, batchjobStep.getId());
        if (oldBatchjobStep != null) {
            if (validator.checkLenght(batchjobStep.getStepName(), 255, true)) {
                helper.mergeObject(em, batchjobStep);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Batchjob step at index " + batchjobStep.getId() + " does not exits"), em);
            throw new DataNotFoundException("Batchjob step at index " + batchjobStep.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }
}
