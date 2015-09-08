/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

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
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDay;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;

/**
 *
 * @author stefan
 */
@Path("/question")
public class QuestionOfTheDayCmsRESTEndpoint {

    RestHelperClass helper;
    AbstractTokenCreator tokenHelper;

    public QuestionOfTheDayCmsRESTEndpoint() {
        helper = new RestHelperClass();
        //tokenHelper = helper.getAbstractToken();
    }

    @GET
    @Path("/{page}/{limit}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestions(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.SEARCH, token);
            List<QuestionOfTheDay> question = em.createNamedQuery("QuestionOfTheDay.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
            if (question.isEmpty()) {
                throw new DataNotFoundException("Requested page does not exist..");
            }
            return Response.ok().entity(helper.getJson(question)).build();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertQuestion(@HeaderParam("authorization") String token, QuestionOfTheDay question) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.ADD, token);
            helper.persistObject(em, question);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException(
                    "You are not logged in!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteQuestion(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.DELETE, token);
            QuestionOfTheDay question = em.find(QuestionOfTheDay.class, id);
            helper.removeObject(em, question, id);
            return Response.ok().build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@HeaderParam("authorization") String token, QuestionOfTheDay question
    ) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.EDIT, token);
            QuestionOfTheDay oldQuestion = em.find(QuestionOfTheDay.class, question.getId());
            if (oldQuestion != null) {
            helper.mergeObject(em, question);                
            }  else {
                throw new DataNotFoundException("Slider at index" + oldQuestion.getId() + " does not exits");
            }
            return Response.ok("Successfully updated!").build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException(
                    "You are not logged in!");
        }
    }

}
