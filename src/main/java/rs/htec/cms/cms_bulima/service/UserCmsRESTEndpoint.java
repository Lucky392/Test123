/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.exception.BasicAuthenticationException;
import rs.htec.cms.cms_bulima.exception.JPAQueryException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;

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
    @Path("/login")
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
            if (user == null) {
                throw new BasicAuthenticationException("Bad login parameters!");
            } else {
                if (user.getToken() == null || user.getToken().equals("")) {
                    user.setToken(tokenHelper.createToken(user.getId()));
                    helper.mergeObject(em, user);
                }
                return Response.ok(tokenHelper.encode(user.getToken())).build();
            }
        } catch (RuntimeException e) {
            throw new BasicAuthenticationException(e.getMessage());
        } catch (Exception e) {
            throw new JPAQueryException("Database query error");
        }
    }

    @GET
    @Path("/logout")
    public Response logOut(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            user.setToken(null);
            helper.mergeObject(em, user);
            return Response.ok("You are logged out!").build();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new NotAuthorizedException("Not authorized!");
        }
    }

}
