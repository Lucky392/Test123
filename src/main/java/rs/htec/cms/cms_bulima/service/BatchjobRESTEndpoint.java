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
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author marko
 */
@Path("batchjobs")
public class BatchjobRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * Returns Batchjob object in JSON format.
     *
     * Example for response: {<br/>
     * "jobName": "dailyJob",<br/>
     * "cronExpression": "30 30 2 ? * *",<br/>
     * "defaultCronExpression": "30 30 2 ? * *",<br/>
     * "enabled": 1,<br/>
     * "id": 1<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id for Batchjob that should be retrieved
     * @return Batchjob with status 200
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBatchjobById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        Batchjob batchjob;
        try {
            batchjob = (Batchjob) em.createNamedQuery("Batchjob.findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Batchjob at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Batchjob at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(batchjob).build(), em);
        return Response.ok().entity(batchjob).build();
    }

    /**
     * API for method: .../rest/batchjobs?page=VALUE&limit=VALUE&search=VALUE
     * This method returns JSON list, and count number. Default value for page
     * is 1, and for limit is 10. There is a possibility for search by jobName.
     * It produces APPLICATION_JSON media type. Example for JSON list for 1
     * page, 2 limit: <br/>{<br/> "count": 7,<br/> "data": [ <br/>{<br/>
     * "jobName": "dailyJob",<br/>
     * "cronExpression": "30 30 2 ? * *",<br/> "defaultCronExpression": "30 30 2
     * ? * *",<br/> "enabled": 1,<br/> "id": 1<br/> },<br/> { <br/>"jobName":
     * "minuteJob",<br/> "cronExpression": "0 0/5 * * * ?",<br/>
     * "defaultCronExpression": "0 0/5 * * * ?",<br/> "enabled": 1,<br/>
     * "id": 2<br/> } <br/>] }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for Batchjob
     * @param limit number of Batchjob method returns
     * @param search word for searching jobName
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no batchjobs for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBatchjob(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT b FROM Batchjob b ");
        if (search != null) {
            search = "%" + search + "%";
            query.append("WHERE b.jobName LIKE '")
                    .append(search)
                    .append("'");
        }
        List<Batchjob> batchjobs = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (batchjobs == null || batchjobs.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no batchjobs for this search!"), em);
            throw new DataNotFoundException("There is no batchjobs for this search!");
        }
        String countQuery = query.toString().replaceFirst("b", "count(b)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(batchjobs);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * API for this method is .../rest/batchjobs This method recieves JSON
     * object, and put it in the base. Example for JSON that you need to send:
     * <br/>{<br/>
     * "jobName": "dailyJob",<br/> "cronExpression": "30 00 6 ? * *",<br/>
     * "defaultCronExpression": "30 00 6 ? * *",<br/> "enabled": 0 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param batchjob is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertBatchjob(@HeaderParam("authorization") String token, @Context HttpServletRequest request, Batchjob batchjob) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), batchjob);
        if (validator.checkLenght(batchjob.getJobName(), 255, true)
                && validator.checkLenght(batchjob.getCronExpression(), 255, true)
                && validator.checkLenght(batchjob.getDefaultCronExpression(), 255, true)) {
            helper.persistObject(em, batchjob);
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
        helper.setResponseToHistory(history, Response.status(Response.Status.CREATED).build(), em);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * API for this method is .../rest/batchjobs This method recieves JSON
     * object, and update database. Example for JSON that you need to send:
     * <br/>{<br/>
     * "jobName": "dailyJob",<br/> "cronExpression": "30 00 6 ? * *",<br/>
     * "defaultCronExpression": "30 00 6 ? * *",<br/> "enabled": 0,<br/> "id":
     * 10 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param batchjob is an object that Jackson convert from JSON to object
     * @return Response with status OK (200)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Batchjob at index 10 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBatchjob(@HeaderParam("authorization") String token, @Context HttpServletRequest request, Batchjob batchjob) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), batchjob);
        Batchjob oldBatchjob = em.find(Batchjob.class, batchjob.getId());
        if (oldBatchjob != null) {
            if (validator.checkLenght(batchjob.getJobName(), 255, true)
                    && validator.checkLenght(batchjob.getCronExpression(), 255, true)
                    && validator.checkLenght(batchjob.getDefaultCronExpression(), 255, true)) {
                helper.mergeObject(em, batchjob);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Batchjob at index " + batchjob.getId() + " does not exits"), em);
            throw new DataNotFoundException("Batchjob at index " + batchjob.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }
}

