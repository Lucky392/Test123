/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;
import rs.htec.cms.cms_bulima.exception.BasicAuthenticationException;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.Password;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.CmsUserPojo;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;
import rs.htec.cms.cms_bulima.token.Base64Token;

/**
 *
 * @author lazar
 */
@Path("/cmsUsers")
public class UserCmsRESTEndpoint {

    AbstractTokenCreator tokenHelper;

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    public UserCmsRESTEndpoint() {
        tokenHelper = new Base64Token();
    }

    /**
     * API for this method: .../rest/cmsUsers This method return list of users
     * in JSON object. Example for JSON: <br/>[ {<br/>
     * "idRole": {<br/> "name": "admin",<br/> "id": 1 <br/>}, <br/>"userName":
     * "name", <br/>"token": "TOKEN",<br/> "password": "pass",<br/> "id": 1<br/>
     * } ]
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.CMS_USER, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<CmsUser> users = em.createNamedQuery("CmsUser.findAll").getResultList();
        if (users == null || users.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        // should be cripted
        for (CmsUser user : users) {
            user.setPassword("******");
        }
        Response response = Response.ok().entity(users).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/cmsUsers/login Method that accepts HTTP Basic
     * authentication from HTTP header, checks in the database whether the user
     * exists and if so, returns custom token that in future calls should be put
     * in the authorization parameter of the HTTP header and returns all User
     * privileges. Example for JSON:<br/> {<br/>
     * "cmsUserPrivileges": [ <br/>{ <br/>"cmsRole": {<br/> "name":
     * "admin",<br/> "id": 1 <br/>},<br/>
     * "cmsUserPrivilegesPK": { <br/>"tableId": 7,<br/> "roleId": 1 <br/>},
     * <br/>"searchAction": true, <br/>"editAction": true,<br/> "addAction":
     * true,<br/> "deleteAction": true,<br/>
     * "cmsTables": {<br/> "tableName": "CMS_ROLE",<br/> "id": 7 <br/>}
     * <br/>},<br/> { <br/>"cmsRole": {<br/>
     * "name": "admin", <br/>"id": 1 <br/>},<br/> "cmsUserPrivilegesPK": {
     * <br/>"tableId": 8,<br/>
     * "roleId": 1 <br/>}, <br/>"searchAction": true,<br/> "editAction":
     * true,<br/> "addAction": true,<br/> "deleteAction": true,<br/>
     * "cmsTables": { <br/>"tableName": "STATISTICS",<br/>
     * "id": 8 <br/>} <br/>} <br/>],<br/> "token": "VE9LRU4jIzE=" <br/>}
     *
     * @param authorization Basic HTTP authorization.
     * @return Response 200 OK with custom authorization value and User
     * privileges in JSON body.
     * @throws BasicAuthenticationException Response 401 Unauthorized if [user
     * doesn't exist, if password is incorrect]
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(@HeaderParam("authorization") String authorization) {
        String[] userPass;
        EntityManager em = helper.getEntityManager();
        try {
            userPass = tokenHelper.decodeBasicAuth(authorization);
        } catch (RuntimeException e) {
            throw new BasicAuthenticationException(e.getMessage());
        }
        CmsUser user = null;
        try {
            user = (CmsUser) em
                    .createQuery("SELECT u FROM CmsUser u WHERE u.userName = :userName")
                    .setParameter("userName", userPass[0])
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BasicAuthenticationException("This username doesn't exist");
        }

        boolean pass = false;
        try {
            pass = Password.check(userPass[1], user.getPassword());
        } catch (Exception e) {
            throw new BasicAuthenticationException(e.getMessage());
        }

        if (!pass) {
            throw new BasicAuthenticationException("The password you have entered is incorrect");
        }

        if (pass && (user.getToken() == null || user.getToken().equals(""))) {
            user.setToken(tokenHelper.createToken(user.getId()));
            helper.mergeObject(em, user);
        }
//            JsonToken jsonToken = new JsonToken(tokenHelper.encode(user.getToken()));
        List<CmsUserPrivileges> cmsUserPrivileges = em.createNamedQuery("CmsUserPrivileges.findByRoleId").setParameter("roleId", user.getIdRole().getId()).getResultList();
//            jsonToken.setCmsUserPrivileges(cmsUserPrivileges);
//            jsonToken.setUser(user);
        CmsUserPojo userPojo = new CmsUserPojo();
        userPojo.setUsername(user.getUserName());
        userPojo.setId(user.getId());
        userPojo.createPermissions(cmsUserPrivileges);
        userPojo.setToken(tokenHelper.encode(user.getToken()));
        return Response.ok().entity(userPojo).build();
    }

    /**
     * API for method: .../rest/cmsUsers/logout The method, which receives
     * authorization parameter from the HTTP header, it checks whether the user
     * logged in, and if so, logging it out.
     *
     * @param token HTTP header authorization token.
     * @return Response 200 OK.
     */
    @POST
    @Path("/logout")
    public Response logOut(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
        user.setToken(null);
        helper.mergeObject(em, user);
        return Response.ok().build();
    }

    /**
     * API for this method: .../rest/cmsUsers This method recieves JSON object,
     * and put him in the base.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param user is object that Jackson convert from JSON
     * @return Response 201 CREATED
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@HeaderParam("authorization") String token, @Context HttpServletRequest request, CmsUser user) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.CMS_USER, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), user);
        List<CmsUser> userDb = (List<CmsUser>) em
                .createQuery("SELECT u FROM CmsUser u WHERE u.userName = :userName")
                .setParameter("userName", user.getUserName())
                .getResultList();
        if (userDb != null && userDb.size() > 0) {
            helper.setResponseToHistory(history, new InputValidationException("User with username '" + user.getUserName() + "' already exist"), em);
            throw new InputValidationException("User with username '" + user.getUserName() + "' already exist");
        }
        if (validator.checkLenght(user.getUserName(), 255, false) && validator.checkLenght(user.getPassword(), 255, false)) {
            try {
                String pass = Password.getSaltedHash(user.getPassword());
                user.setPassword(pass);
            } catch (Exception ex) {
                Logger.getLogger(UserCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
            helper.persistObject(em, user);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@HeaderParam("authorization") String token, @Context HttpServletRequest request, CmsUser user) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.CMS_USER, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), user);
        if (validator.checkLenght(user.getUserName(), 255, false) && validator.checkLenght(user.getPassword(), 255, false)) {
            CmsUser oldUser = em.find(CmsUser.class, user.getId());
            if (oldUser != null) {
                helper.mergeObject(em, user);
            } else {
                helper.setResponseToHistory(history, new DataNotFoundException("User at index " + user.getId() + " does not exits"), em);
                throw new DataNotFoundException("User at index " + user.getId() + " does not exits");
            }
            Response response = Response.ok().build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }
}
