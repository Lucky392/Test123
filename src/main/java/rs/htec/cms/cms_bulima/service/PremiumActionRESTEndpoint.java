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
import rs.htec.cms.cms_bulima.domain.PremiumAction;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author lazar
 */
@Path("/premium_actions")
public class PremiumActionRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for method: .../rest/premium_actions?page=VALUE&limit=VALUE This
     * method returns JSON list. Default value for page is 1, and for limit is
     * 10. You can put your values for page, limit. It produces APPLICATION_JSON
     * media type. Example for JSON list for 1 page, 2 limit: <br/>
     * [{<br/>
     * "createDate": 1418727045000,<br/>
     * "name": "Sofortverkauf",<br/>
     * "id": 1<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1418727045000,<br/>
     * "name": "Question of the Day",<br/>
     * "id": 2 <br/>
     * }]<br/>
     * You can also
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for Premium actions
     * @param limit number of Premium actions method returns
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumActions(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<PremiumAction> premiumActions = em.createNamedQuery("PremiumAction.findAll").getResultList();
        if (premiumActions == null || premiumActions.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = "Select COUNT(p) From PremiumAction p";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(premiumActions);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/premium_actions/{id} This method return single
     * element of premium action at index in JSON. Example for JSON response:
     * {<br/>
     * "createDate": 1418727045000,<br/>
     * "name": "Sofortverkauf",<br/>
     * "id": 1<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of premium action we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActionById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        PremiumAction action = null;
        try {
            action = (PremiumAction) em.createNamedQuery("PremiumAction.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium action at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Premium action at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(action).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/premium_actions This method recieves JSON
     * object, and put it in the base. Example for JSON that you need to send:
     * <br/>
     * {<br/>
     * "name": "bla bla" <br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param premiumActions
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPremiumAction(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumAction premiumActions) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        premiumActions.setCreateDate(new Date());
        if (validator.checkLenght(premiumActions.getName(), 255, true)) {
            helper.persistObject(em, premiumActions);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

//    /**
//     * API for method: .../rest/premium_actions/{id} This method find
//     * premium_action with defined id. Id is retrieved from URL. If Premium
//     * actions with that index does not exist method throws exception. Otherwise
//     * method remove that Premium action. If Premium action is used, it can't be
//     * deleted.
//     *
//     * @param token is a header parameter for checking permission
//     * @param id of Premium action that should be deleted.
//     * @return Response 200 OK
//     */
//    @DELETE
//    @Path("/{id}")
//    public Response deletePremiumAction(@HeaderParam("authorization") String token, @PathParam("id") long id) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
//        PremiumAction premiumActions = em.find(PremiumAction.class, id);
//        helper.removeObject(em, premiumActions, id);
//        return Response.ok().build();
//    }
    /**
     * API for this method is .../rest/premium_actions This method recieves JSON
     * object, and update database. Example for JSON that you need to send:<br/>
     * {<br/>
     * "name": "bla bla 1123",<br/>
     * "id": 15<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param premiumAction
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "News at index 15 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePremiumAction(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumAction premiumAction) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        PremiumAction oldPremiumAction = em.find(PremiumAction.class, premiumAction.getId());
        if (oldPremiumAction != null) {
            if (validator.checkLenght(premiumAction.getName(), 255, true)) {
                premiumAction.setCreateDate(oldPremiumAction.getCreateDate());
                helper.mergeObject(em, premiumAction);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium action at index" + premiumAction.getId() + " does not exits"), em);
            throw new DataNotFoundException("Premium action at index" + premiumAction.getId() + " does not exits");
        }
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method: .../rest/premium_actions/count This method return
     * number of all actions in database.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountPremiumAction(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(p) From PremiumAction p";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
