/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

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
@Path("batchjob")
public class BatchjobRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public BatchjobRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: .../rest/batchjob?page=VALUE&limit=VALUE&search=VALUE
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
    public Response getBatchjob(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT b FROM Batchjob b ");
        if (search != null) {
            search = "%" + search + "%";
            query.append("WHERE b.jobName LIKE '")
                    .append(search)
                    .append("'");
        }
        List<Batchjob> batchjobs = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (batchjobs == null || batchjobs.isEmpty()) {
            throw new DataNotFoundException("There is no batchjobs for this search!");
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
     * API for this method is .../rest/batchjob This method recieves JSON
     * object, and put it in the base. Example for JSON that you need to send:
     * <br/>{<br/>
     * "jobName": "dailyJob",<br/> "cronExpression": "30 00 6 ? * *",<br/>
     * "defaultCronExpression": "30 00 6 ? * *",<br/> "enabled": 0 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param batchjob is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertBatchjob(@HeaderParam("authorization") String token, Batchjob batchjob) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (validator.checkLenght(batchjob.getJobName(), 255, true)
                && validator.checkLenght(batchjob.getCronExpression(), 255, true)
                && validator.checkLenght(batchjob.getDefaultCronExpression(), 255, true)) {
            helper.persistObject(em, batchjob);
        } else {
            throw new InputValidationException("Validation failed");
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * API for this method is .../rest/batchjob This method recieves JSON
     * object, and update database. Example for JSON that you need to send: <br/>{<br/>
     * "jobName": "dailyJob",<br/> "cronExpression": "30 00 6 ? * *",<br/>
     * "defaultCronExpression": "30 00 6 ? * *",<br/> "enabled": 0,<br/> "id": 10 <br/>}
     *
     * @param token is a header parameter for checking permission
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
    public Response updateBatchjob(@HeaderParam("authorization") String token, Batchjob batchjob) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token);
        Batchjob oldBatchjob = em.find(Batchjob.class, batchjob.getId());
        if (oldBatchjob != null) {
            if (validator.checkLenght(batchjob.getJobName(), 255, true)
                    && validator.checkLenght(batchjob.getCronExpression(), 255, true)
                    && validator.checkLenght(batchjob.getDefaultCronExpression(), 255, true)) {
                helper.mergeObject(em, batchjob);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Batchjob at index " + batchjob.getId() + " does not exits");
        }
        return Response.ok().build();
    }
}
