/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDayPrize;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/prize")
public class QuestionOfTheDayPrizeRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public QuestionOfTheDayPrizeRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: .../rest/prize/{page}/{limit} This method returns JSON
     * list of questions at defined page with defined limit. It produces
     * APPLICATION_JSON media type. Example for JSON list for 2 page, 2 limit:
     * <br/>[ {<br/> "prizeMoney": "30000",<br/> "name": "Tag 3",<br/> "id":
     * "3",<br/> "createDate": "2014-12-03 17:11:04.0"<br/> },<br/> {<br/>
     * "prizeMoney": "60000",<br/> "name": "Tag 4",<br/>
     * "id": "4",<br/> "createDate": "2014-12-03 17:11:04.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for prizes
     * @param limit number of prizes method returns
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{page}/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrize(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token);
        List<QuestionOfTheDayPrize> prize = em.createNamedQuery("QuestionOfTheDayPrize.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (prize.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        return Response.ok().entity(helper.getJson(prize)).build();
    }

    /**
     * API for method: .../rest/prize/{id} This method return single element of questionOfTheDayPrize at index
     * in JSON. Example for JSON response: <br/>{<br/> "prizeMoney": "30000",<br/> "name": "Tag 3",<br/> "id":
     * "3",<br/> "createDate": "2014-12-03 17:11:04.0"<br/> }
     * @param token is a header parameter for checking permission
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
    public Response getPrizeById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token);
        QuestionOfTheDayPrize prize = null;
        try {
            prize = (QuestionOfTheDayPrize) em.createNamedQuery("QuestionOfTheDayPrize.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("Question of the day prize at index " + id + " does not exist..");
        }
        return Response.ok().entity(prize).build();
    }
    
    /**
     * API for this method is .../rest/prize This method receives JSON object,
     * and put it in the base. Example for JSON: {<br/> "prizeMoney":
     * "10000",<br/>
     * "name": "Tag 1" <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param prize is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrize(@HeaderParam("authorization") String token, QuestionOfTheDayPrize prize) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.ADD, token);
        if (validator.checkLenght(prize.getName(), 255, true)) {
            prize.setCreateDate(new Date());
            helper.persistObject(em, prize);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: .../rest/prize/{id} This method find prize with defined
     * id. Id is retrieved from URL. If prize with that index does not exist
     * method throws exception. Otherwise method remove that prize.
     *
     * @param token is a header parameter for checking permission
     * @param id of prize that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deletePrize(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.DELETE, token);
        QuestionOfTheDayPrize prize = em.find(QuestionOfTheDayPrize.class, id);
        helper.removeObject(em, prize, id);
        return Response.ok().build();
    }

    /**
     * API for this method is .../rest/prize This method recieves JSON object,
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePrize(@HeaderParam("authorization") String token, QuestionOfTheDayPrize prize) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY_PRIZE, MethodConstants.SEARCH, token);
        QuestionOfTheDayPrize oldPrize = em.find(QuestionOfTheDayPrize.class, prize.getId());
        if (oldPrize != null) {
            if (validator.checkLenght(prize.getName(), 255, true)) {
                prize.setCreateDate(new Date());
                helper.mergeObject(em, prize);
                return Response.ok("Successfully updated!").build();
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Prize at index" + prize.getId() + " does not exits");
        }
    }
}
