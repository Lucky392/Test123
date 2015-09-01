/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.google.gson.Gson;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.NewsPojo;

/**
 *
 * @author lazar
 */
@Path("/cms")
public class UserCmsRESTEndpoint {

    RestHelperClass helper;

    public UserCmsRESTEndpoint() {
        helper = new RestHelperClass();
    }

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("rs.htec.cms_CMS_Bulima_war_1.0PU");
        EntityManager ecm = emf.createEntityManager();
        return ecm;
    }

    @GET
    @Path("/login")
    public Response logIn(@HeaderParam("authorization") String authorization) {
        String[] userPass;
        EntityManager em = getEntityManager();
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
        EntityManager em = getEntityManager();
        CmsUser user = em.find(CmsUser.class, Long.parseLong(helper.decode(token).split("##")[1]));
        user.setToken(null);
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        return Response.ok("You are logged out!").build();
    }

    @GET
    @Path("/news/{page}/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNews(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(helper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                List<News> news = em.createNamedQuery("News.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
                List<NewsPojo> newsPojos = new NewsPojo().createPojoList(news);
                return Response.ok().entity(new Gson().toJson(newsPojos)).build();
            }
        } catch (Exception e) {
            Logger.getLogger(UserCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, e);
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
