/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.exception.BasicAuthenticationException;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;
import rs.htec.cms.cms_bulima.token.JsonToken;

/**
 *
 * @author lazar
 */
@Path("/user")
public class UserCmsRESTEndpoint {

    RestHelperClass helper;
    AbstractTokenCreator tokenHelper;

    public UserCmsRESTEndpoint() {
        helper = new RestHelperClass();
        tokenHelper = helper.getAbstractToken();
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@HeaderParam("authorization") String token){
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.CMS_USER, MethodConstants.SEARCH, token);
            List<CmsUser> users = em.createNamedQuery("CmsUser.findAll").getResultList();
            if (users.isEmpty()) {
                throw new DataNotFoundException("Requested page does not exist..");
            }
            return Response.ok().entity(users).build();
        } catch (IllegalArgumentException e) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, e);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    /**
     * API for method: /rest/user/login
     * Method that accepts HTTP Basic authentication from HTTP header, checks in
     * the database whether the user exists and if so, returns custom token that
     * in future calls should be put in the authorization parameter of the HTTP
     * header.
     *
     * @param authorization Basic HTTP authorization.
     * @return Response 200 OK with custom authorization value in JSON body.
     * @throws BasicAuthenticationException Response 401 Unauthorized if user doesn't exist.
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(@HeaderParam("authorization") String authorization) {
        String[] userPass;
        EntityManager em = helper.getEntityManager();
        try {
            userPass = tokenHelper.decodeBasicAuth(authorization);

            CmsUser user = (CmsUser) em
                    .createQuery("SELECT u FROM CmsUser u WHERE u.userName = :userName AND u.password = :password")
                    .setParameter("userName", userPass[0])
                    .setParameter("password", userPass[1])
                    .getSingleResult();

            if (user.getToken() == null || user.getToken().equals("")) {
                user.setToken(tokenHelper.createToken(user.getId()));
                helper.mergeObject(em, user);
            }
            JsonToken jsonToken = new JsonToken(tokenHelper.encode(user.getToken()));
            return Response.ok().entity(jsonToken).build();
        } catch (RuntimeException e) {
            throw new BasicAuthenticationException(e.getMessage());
        }
    }

    /**
     * API for method: /rest/user/logout
     * The method, which receives authorization parameter from the HTTP header,
     * it checks whether the user logged in, and if so, logging it out.
     *
     * @param token HTTP header athorization token.
     * @return Response 200 OK.
     * @throws NotAuthorizedException If user doesn't have valid authorization
     * token.
     */
    @POST
    @Path("/logout")
    public Response logOut(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            user.setToken(null);
            helper.mergeObject(em, user);
            return Response.ok().build();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new NotAuthorizedException("Not authorized!");
        }
    }

}
