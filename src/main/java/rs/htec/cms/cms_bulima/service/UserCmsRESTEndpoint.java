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
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("/user")
public class UserCmsRESTEndpoint {

    RestHelperClass helper;

    public UserCmsRESTEndpoint() {
        helper = new RestHelperClass();
    }

    @GET
    @Path("/login")
    public Response logIn(@HeaderParam("authorization") String authorization) {
        String[] userPass;
        EntityManager em = helper.getEntityManager();

        try {
            userPass = helper.decodeBasicAuth(authorization);
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        CmsUser user = (CmsUser) em
                .createQuery("SELECT u FROM CmsUser u WHERE u.userName = :userName AND u.password = :password")
                .setParameter("userName", userPass[0])
                .setParameter("password", userPass[1])
                .getSingleResult();
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            if (user.getToken() == null || user.getToken().equals("")) {
                user.setToken(helper.createToken(user.getId()));
                em.getTransaction().begin();
                em.merge(user);
                em.getTransaction().commit();
            }
            return Response.ok(helper.encode(user.getToken())).build();

        }
    }

    @GET
    @Path("/logout")
    public Response logOut(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        CmsUser user = em.find(CmsUser.class, Long.parseLong(helper.decode(token).split("##")[1]));
        user.setToken(null);
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        return Response.ok("You are logged out!").build();
    }

}
