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
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDayPrize;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.ForbbidenException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;

/**
 *
 * @author stefan
 */
@Path("/prize")
public class QuestionOfTheDayPrizeRESTEndpoint {

    RestHelperClass helper;
    AbstractTokenCreator tokenHelper;

    public QuestionOfTheDayPrizeRESTEndpoint() {
        helper = new RestHelperClass();
        tokenHelper = helper.getAbstractToken();
    }

    @GET
    @Path("/{page}/{limit}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrize(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                List<QuestionOfTheDayPrize> prize = em.createNamedQuery("QuestionOfTheDayPrize.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
                if (prize.isEmpty()) {
                    throw new DataNotFoundException("Requested page does not exist..");
                }
                return Response.ok().entity(helper.getJson(prize)).build();
            } else {
                throw new NotAuthorizedException("You are not logged in!");
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrize(@HeaderParam("authorization") String token, QuestionOfTheDayPrize prize) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (helper.isNewsAdmin(user)) {
                    prize.setCreateDate(new Date());
                    em.getTransaction().begin();
                    em.persist(prize);
                    em.getTransaction().commit();
                    return Response.status(Response.Status.CREATED).build();
                } else {
                    throw new ForbbidenException("You don't have permission to insert data");
                }
            } else {
                throw new NotAuthorizedException("You are not logged in!");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePrize(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (helper.isAdmin(user)) {
                    QuestionOfTheDayPrize prize = em.find(QuestionOfTheDayPrize.class, id);
                    if (prize != null) {
                        em.getTransaction().begin();
                        em.remove(prize);
                        em.getTransaction().commit();
                        return Response.ok().build();
                    } else {
                        throw new DataNotFoundException("Prize at index: " + id + " does not exits");
                    }
                } else {
                    throw new ForbbidenException("You don't have permission to delete data");
                }
            } else {
                throw new NotAuthorizedException("You are not logged in!");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
        
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePrize(@HeaderParam("authorization") String token, QuestionOfTheDayPrize prize) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (helper.isAdmin(user)) {
                    QuestionOfTheDayPrize oldPrize = em.find(QuestionOfTheDayPrize.class, prize.getId());
                    if (oldPrize != null) {
                        prize.setCreateDate(new Date());
                        em.getTransaction().begin();
                        em.merge(prize);
                        em.getTransaction().commit();
                        return Response.ok("Successfully updated!").build();
                    }  else {
                        throw new DataNotFoundException("Prize at index" + prize.getId() + " does not exits");
                    }
                } else {
                    throw new ForbbidenException("You don't have permission to update data");
                }
            } else {
                throw new NotAuthorizedException("You are not logged in!");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }
}
