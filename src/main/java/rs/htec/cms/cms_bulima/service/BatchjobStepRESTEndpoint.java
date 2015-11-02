/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Batchjob;
import rs.htec.cms.cms_bulima.domain.BatchjobStep;
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
@Path("batchjobStep")
public class BatchjobStepRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    @InjectParam
    Validator validator;

    /**
     * API for method:
     * .../rest/batchjobStep?page=VALUE&limit=VALUE&search=VALUE&idBatchjob=VALUE
     * This method returns JSON list, and count number. Default value for page
     * is 1, and for limit is 10. There is a possibility for search by
     * searchName, and by batchjobID. It produces APPLICATION_JSON media type.
     * Example for JSON list for 1 page, 2 limit: <br/>{<br/> "count": 20,<br/>
     * "data": [ {<br/>
     * "stepName": "FantasyLeagueActivityBatchJob",<br/> "idBatchjob": {
     * <br/>"jobName": "dailyJob",<br/> "cronExpression": "30 30 2 ? * *",<br/>
     * "defaultCronExpression": "30 30 2 ? * *",<br/> "enabled": 1,<br/> "id":
     * 1<br/> }, <br/>"enabled": 0,<br/> "id": 1 <br/>}, <br/>{<br/>
     * "stepName": "AddAuctionsToTransferMarketBatchJob",<br/> "idBatchjob":
     * {<br/>
     * "jobName": "dailyJob",<br/> "cronExpression": "30 30 2 ? * *",<br/>
     * "defaultCronExpression": "30 30 2 ? * *",<br/> "enabled": 1,<br/> "id":
     * 1<br/> },<br/>
     * "enabled": 0,<br/> "id": 2 <br/>} <br/>] }
     *
     * @param token is a header parameter for checking permission
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
    public Response getBatchjobStep(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search, @QueryParam("idBatchjob") long idBatchjob) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
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
        List<Batchjob> batchjobs = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (batchjobs == null || batchjobs.isEmpty()) {
            throw new DataNotFoundException("There is no batchjob steps for this search!");
        }
        String countQuery = query.toString().replaceFirst("b", "count(b)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(batchjobs);
        return Response.ok().entity(go).build();
    }

    /**
     * API for this method is .../rest/batchjobStep This method recieves JSON
     * object, and put it in the base. Example for JSON that you need to send:
     * <br/>{ "stepName": "FinishAuctionsBatchJob",<br/> "idBatchjob": {
     * <br/>"jobName": "dailyJob",<br/> "cronExpression": "30 30 2 ? * *",<br/>
     * "defaultCronExpression": "30 30 2 ? * *",<br/> "enabled": 1,<br/> "id":
     * 1<br/> },<br/> "enabled": 0 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param batchjobStep is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertBatchjobStep(@HeaderParam("authorization") String token, BatchjobStep batchjobStep) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (validator.checkLenght(batchjobStep.getStepName(), 255, true)) {
            helper.persistObject(em, batchjobStep);
        } else {
            throw new InputValidationException("Validation failed");
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * API for this method is .../rest/batchjobStep This method recieves JSON
     * object, and update database. Example for JSON that you need to send: <br/>{<br/>
     * "stepName": "UpdateUnqualifiedFantasyClubBatchJob",<br/> "idBatchjob": {<br/>
     * "jobName": "dailyJob",<br/> "cronExpression": "30 30 2 ? * *",<br/>
     * "defaultCronExpression": "30 30 2 ? * *",<br/> "enabled": 1,<br/> "id": 1<br/> },<br/>
     * "enabled": 0,<br/> "id": 21<br/> }
     *
     * @param token is a header parameter for checking permission
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
    public Response updateBatchjob(@HeaderParam("authorization") String token, BatchjobStep batchjobStep) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token);
        BatchjobStep oldBatchjobStep = em.find(BatchjobStep.class, batchjobStep.getId());
        if (oldBatchjobStep != null) {
            if (validator.checkLenght(batchjobStep.getStepName(), 255, true)) {
                helper.mergeObject(em, batchjobStep);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Batchjob step at index " + batchjobStep.getId() + " does not exits");
        }
        return Response.ok().build();
    }
}
