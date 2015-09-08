/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;

/**
 *
 * @author marko
 */
@Path("/news")
public class NewsCmsRESTEndpoint {

    RestHelperClass helper;

    public NewsCmsRESTEndpoint() {
        helper = new RestHelperClass();
    }

    @GET
    @Path("/{page}/{limit}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNews(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token);
            List<News> news = em.createNamedQuery("News.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
            if (news.isEmpty()) {
                throw new DataNotFoundException("Requested page does not exist..");
            }
            return Response.ok().entity(helper.getJson(news)).build();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token);
            news.setCreateDate(new Date());
            em.getTransaction().begin();
            em.persist(news);
            em.getTransaction().commit();
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNews(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.DELETE, token);
            News news = em.find(News.class, id);
            if (news != null) {
                em.getTransaction().begin();
                em.remove(news);
                em.getTransaction().commit();
                return Response.ok().build();
            } else {
                throw new DataNotFoundException("News at index: " + id + " does not exits");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token);
            News oldNews = em.find(News.class, news.getId());
            if (oldNews != null) {
                em.getTransaction().begin();
                em.merge(news);
                em.getTransaction().commit();
                return Response.ok("Successfully updated!").build();
            } else {
                throw new DataNotFoundException("News at index" + news.getId() + " does not exits");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

}
