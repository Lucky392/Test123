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
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.UserPremiumItem;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.UserPremiumItemPOJO;

/**
 *
 * @author stefan
 */
@Path("/userPremiumItems")
public class UserPremiumItemRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for this method: .../rest/userPremiumItem/user/{email} This method
     * return JSON list of User Premium Item for one User based on his email.
     * Example for JSON object: <br/>
     * {<br/>
     * "count": 2,<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1408626038000,<br/>
     * "updateTimestamp": 1430734323000,<br/>
     * "startDate": null,<br/>
     * "endDate": null,<br/>
     * "idPremiumItem": 4,<br/>
     * "charges": 3,<br/>
     * "idUser": 10162,<br/>
     * "id": 8493<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1408626038000,<br/>
     * "updateTimestamp": 1408626038000,<br/>
     * "startDate": null,<br/>
     * "endDate": null,<br/>
     * "idPremiumItem": 5,<br/>
     * "charges": 1,<br/>
     * "idUser": 10162,<br/>
     * "id": 8494<br/>
     * }<br/>
     * ]<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param email - email for user you want UserPremiumItem
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no User Premium Item for this user!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPremiumItemForUserEmail(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("email") String email) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<UserPremiumItem> items;
        StringBuilder query = new StringBuilder("SELECT upi FROM UserPremiumItem upi JOIN upi.idUser u WHERE u.email = '");
        query.append(email).append("'");
        items = em.createQuery(query.toString()).getResultList();
        if (items.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no User Premium Item for this user!"), em);
            throw new DataNotFoundException("There is no User Premium Item for this user!");
        } else {
            String countQuery = query.toString().replaceFirst("upi", "count(upi)");
            long count = (long) em.createQuery(countQuery).getSingleResult();
            GetObject go = new GetObject();
            List<UserPremiumItemPOJO> pojos = UserPremiumItemPOJO.toUserPremiumItemPOJOList(items);
            go.setCount(count);
            go.setData(pojos);

            Response response = Response.ok().entity(go).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        }
    }

    /**
     * Create and insert new UserPremiumItem in DB.
     *
     * Example for JSON body: <br/>
     * { <br/>
     * "startDate": null,<br/>
     * "endDate": null,<br/>
     * "idPremiumItem": 4,<br/>
     * "charges": 3,<br/>
     * "idUser": 10162<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param userPremiumItem - UserPremiumItem in JSON
     * @return status 201 created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertUserPremiumItem(@HeaderParam("authorization") String token, @Context HttpServletRequest request, UserPremiumItem userPremiumItem) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), userPremiumItem);
        userPremiumItem.setCreateDate(new Date());
        helper.persistObject(em, userPremiumItem);
        Response response = Response.status(Response.Status.CREATED).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * Finds and updates UserPremiumItem. Example for body: {<br/>
     * "startDate": null,<br/>
     * "endDate": null,<br/>
     * "idPremiumItem": 4,<br/>
     * "charges": 7,<br/>
     * "idUser": 10162,<br/>
     * "id": 2194951<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param userPremiumItem - UserPremiumItem in JSON
     * @return status 200 OK
     * @throws DataNotFoundException if UserPremiumItem with id defined in body
     * doesn't exist
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserPremiumItem(@HeaderParam("authorization") String token, @Context HttpServletRequest request, UserPremiumItem userPremiumItem) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), userPremiumItem);

        UserPremiumItem oldUserPremiumItem = em.find(UserPremiumItem.class, userPremiumItem.getId());
        if (oldUserPremiumItem != null) {
            userPremiumItem.setCreateDate(oldUserPremiumItem.getCreateDate());
            userPremiumItem.setUpdateTimestamp(new Date());
            helper.mergeObject(em, userPremiumItem);
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("UserPremiumItem at index " + userPremiumItem.getId() + " does not exits"), em);
            throw new DataNotFoundException("UserPremiumItem at index " + userPremiumItem.getId() + " does not exits");
        }

        Response response = Response.status(Response.Status.OK).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/userPremiumItem/{id} This method find
     * userPremiumItem with defined id. Id is retrieved from URL. If
     * UserPremiumItem with that index does not exist method throws exception.
     * Otherwise method remove that UserPremiumItem.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of UserPremiumItem that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUserPremiumItem(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.DELETE, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        UserPremiumItem userPremiumItem = em.find(UserPremiumItem.class, id);
        helper.removeObject(em, userPremiumItem, id);
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
