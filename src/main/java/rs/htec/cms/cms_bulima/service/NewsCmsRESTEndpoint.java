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
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

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
            CmsUser user = em.find(CmsUser.class, Long.parseLong(helper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                List<News> news = em.createNamedQuery("News.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
                return Response.ok().entity(helper.getJson(news)).build();
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger(UserCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, e);
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response insertNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(helper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                news.setCreateDate(new Date());
                em.getTransaction().begin();
                em.persist(news);
                em.getTransaction().commit();
                return Response.ok().build();
            }
        } catch (Exception ignore) {
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteNews(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(helper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                News news = em.find(News.class, id);
                if (news != null) {
                    em.getTransaction().begin();
                    em.remove(news);
                    em.getTransaction().commit();
                    return Response.ok().build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            }
        } catch (Exception ignore) {
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
