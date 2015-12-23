/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import rs.htec.cms.cms_bulima.domain.User;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/users")
public class UserRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns User for specified id.
     *
     * Example for response:<br/>
     * {<br/>
     * "createDate": 1406901623000,<br/>
     * "payingUser": 1,<br/>
     * "email": "denis.heil@sport1.de",<br/>
     * "userhash": "077b49da-0603-4dfd-9188-09d211b7f952",<br/>
     * "newEmail": null,<br/>
     * "newEmailActiveTimestamp": null,<br/>
     * "landingPage": null,<br/>
     * "socialNetwork": null,<br/>
     * "premiumStatusActiveTimestamp": null,<br/>
     * "amountPremiumCurrency": 102,<br/>
     * "newsletter": 0,<br/>
     * "confirmed": 0,<br/>
     * "banned": 0,<br/>
     * "password": "VPrDHin257E+/LX5C2KCVzlVbbU=",<br/>
     * "id": 3<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param id - of User that should be returned
     * @return User
     * @throws DataNotFoundException if User for defined id does not exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        User user = null;
        try {
            user = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("User with id " + id + " does not exist.."), em);
            throw new DataNotFoundException("User with id " + id + " does not exist..");
        }
        Response response = Response.ok().entity(user).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * Returns Users for defined search.
     *
     * @param token header parameter for checking permission
     * @param request
     * @param page
     * @param limit
     * @param email of User we are searching for
     * @return User
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("email") String email) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT u FROM User u");
        if (email != null) {
            query.append(" WHERE u.email='").append(email).append("'");
        }
        List<User> users = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (users == null || users.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no users!"), em);
            throw new DataNotFoundException("There is no users!");
        }
        String countQuery = query.toString().replaceFirst("u", "count(u)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(users);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@HeaderParam("authorization") String token, @Context HttpServletRequest request, User user) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), user);
        User oldUser = em.find(User.class, user.getId());
        if (oldUser == null) {
            helper.setResponseToHistory(history, new DataNotFoundException("User at index " + user.getId() + " doesn't exist.."), em);
            throw new DataNotFoundException("User at index " + user.getId() + " doesn't exist..");
        }
        user.setCreateDate(oldUser.getCreateDate());
        if (user.getEmail() == null) {
            user.setEmail(oldUser.getEmail());
        }
        if (user.getUserhash() == null) {
            user.setUserhash(oldUser.getUserhash());
        }
        if (user.getNewEmail() == null) {
            user.setNewEmail(oldUser.getNewEmail());
        }
        if (user.getNewEmailActiveTimestamp() == null) {
            user.setNewEmailActiveTimestamp(oldUser.getNewEmailActiveTimestamp());
        }
        if (user.getLandingPage() == null) {
            user.setLandingPage(oldUser.getLandingPage());
        }
        if (user.getSocialNetwork() == null) {
            user.setSocialNetwork(oldUser.getSocialNetwork());
        }
        if (user.getPremiumStatusActiveTimestamp() == null) {
            user.setPremiumStatusActiveTimestamp(oldUser.getPremiumStatusActiveTimestamp());
        }
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        helper.mergeObject(em, user);
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

}
