/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDayPrize;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/prizes")
public class QuestionOfTheDayPrizeRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for method: .../rest/prizes?page=VALUE&limit=VALUE This method
     * returns JSON list of questions at defined page with defined limit.
     * Default value for page is 1, and for limit is 10. It produces
     * APPLICATION_JSON media type. Example for JSON list for 2 page, 2 limit:
     * <br/>[ {<br/> "prizeMoney": "30000",<br/> "name": "Tag 3",<br/> "id":
     * "3",<br/> "createDate": "2014-12-03 17:11:04.0"<br/> },<br/> {<br/>
     * "prizeMoney": "60000",<br/> "name": "Tag 4",<br/>
     * "id": "4",<br/> "createDate": "2014-12-03 17:11:04.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for prizes
     * @param limit number of prizes method returns
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrize(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<QuestionOfTheDayPrize> prize = em.createNamedQuery("QuestionOfTheDayPrize.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (prize.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = "Select COUNT(ip) From QuestionOfTheDayPrize ip";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(prize);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/prizes/{id} This method return single element of
     * questionOfTheDayPrize at index in JSON. Example for JSON response:
     * <br/>{<br/> "prizeMoney": "30000",<br/> "name": "Tag 3",<br/> "id":
     * "3",<br/> "createDate": "2014-12-03 17:11:04.0"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of questionOfTheDayPrize we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrizeById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        QuestionOfTheDayPrize prize = null;
        try {
            prize = (QuestionOfTheDayPrize) em.createNamedQuery("QuestionOfTheDayPrize.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Question of the day prize at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Question of the day prize at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(prize).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/prizes This method receives JSON object,
     * and put it in the base. Example for JSON: {<br/> "prizeMoney":
     * "10000",<br/>
     * "name": "Tag 1" <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param prize is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrize(@HeaderParam("authorization") String token, @Context HttpServletRequest request, QuestionOfTheDayPrize prize) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        if (validator.checkLenght(prize.getName(), 255, true)) {
            prize.setCreateDate(new Date());
            helper.persistObject(em, prize);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: .../rest/prizes/{id} This method find prize with defined
     * id. Id is retrieved from URL. If prize with that index does not exist
     * method throws exception. Otherwise method remove that prize.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of prize that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deletePrize(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.DELETE, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        QuestionOfTheDayPrize prize = em.find(QuestionOfTheDayPrize.class, id);
        helper.removeObject(em, prize, id);
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/prizes This method recieves JSON object,
     * and update database. Example for JSON: { <br/>"prizeMoney": "10000",
     * <br/>"name": "Tag 1" <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param prize is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Prize at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrize(@HeaderParam("authorization") String token, @Context HttpServletRequest request, QuestionOfTheDayPrize prize) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        QuestionOfTheDayPrize oldPrize = em.find(QuestionOfTheDayPrize.class, prize.getId());
        if (oldPrize != null) {
            if (validator.checkLenght(prize.getName(), 255, true)) {
                prize.setCreateDate(new Date());
                helper.mergeObject(em, prize);
                Response response = Response.ok().build();
                helper.setResponseToHistory(history, response, em);
                return response;
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Prize at index" + prize.getId() + " does not exits"), em);
            throw new DataNotFoundException("Prize at index" + prize.getId() + " does not exits");
        }
    }

    /**
     * API for this method: .../rest/prizes/count This method return number of
     * all prizes in database.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountPrize(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(ip) From QuestionOfTheDayPrize ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
