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
import javax.ws.rs.core.Response.Status;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDay;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/question")
public class QuestionOfTheDayCmsRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public QuestionOfTheDayCmsRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: /question/{page}/{limit} This method returns JSON list of
     * questions at defined page with defined limit. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2 limit:<br/> [
     * {<br/> "date": "2015-07-20 00:00:00.0",<br/> "wrongAnswer3": "Mehmet Scholl",<br/>
     * "question": "Wer erzielte das entscheidende Tor für den FC Bayern München
     * in der Saison 200/01, als der FC Schalke 04 für ein paar Minuten Meister
     * war?",<br/> "id": "1",<br/> "correctAnswer": "Patrik Andersson",<br/> "wrongAnswer1":
     * "Giovanne Elber",<br/> "wrongAnswer2": "Stefan Effenberg"<br/> },<br/> { <br/>"date":
     * "2015-07-21 00:00:00.0",<br/> "wrongAnswer3": "12 Minuten",<br/> "question":
     * "Michael Tönnies erzielte den schnellsten Hattrick der Bundesliga
     * Geschichte. Wie viele Minuten benötigte er?",<br/> "id": "2",<br/> "correctAnswer":
     * "5 Minuten",<br/> "wrongAnswer1": "7 Minuten",<br/> "wrongAnswer2": "10 Minuten"<br/> }
     * ]
     *
     * @param token
     * @param page number of page at which we search for Question
     * @param limit number of Question method returns
     * @return Respond 200 OK with JSON body
     * @throws DataNotFoundException
     * @throws NotAuthorizedException
     */
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
            throw new NotAuthorizedException(ex.getMessage());
        }
    }

    /**
     * API for this method is /rest/question This method recieves JSON object,
     * and put it in the base. Example for JSON: { "date":
     * "2015-07-25T00:00:00.0", "wrongAnswer3": "Jürgen Kohler", "question":
     * "Welcher Spieler erfand die \"Schutzschwalbe\"?", "correctAnswer":
     * "Andreas Möller", "wrongAnswer1": "Rudi Völler", "wrongAnswer2": "Dede" }
     *
     * @param token
     * @param question
     * @return Response with status CREATED (201)
     * @throws InputValidationException
     * @throws NotAuthorizedException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertQuestion(@HeaderParam("authorization") String token, QuestionOfTheDay question) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.ADD, token);
            if (validator.checkLenght(question.getWrongAnswer1(), 255, false) && validator.checkLenght(question.getWrongAnswer2(), 255, false)
                    && validator.checkLenght(question.getWrongAnswer3(), 255, false) && validator.checkLenght(question.getQuestion(), 255, false)
                    && validator.checkLenght(question.getCorrectAnswer(), 255, true)) {

                helper.persistObject(em, question);
                return Response.status(Status.CREATED).build();

            } else {
                throw new InputValidationException("Validation failed");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException(
                    "You are not logged in!");
        }
    }

    /**
     * API for method: /question/{id} This method find question with defined id.
     * Id is retrieved from URL. If question with that id does not exist method
     * throws exception. Otherwise method remove that question.
     *
     * @param token
     * @param id of question that should be deleted.
     * @return Response 200 OK
     * @throws NotAuthorizedException
     */
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

    /**
     * API for this method is /rest/question This method recieves JSON object,
     * and update database. Example for JSON: { "date": "2015-07-25T00:00:00.0",
     * "wrongAnswer3": "Jürgen Kohler", "question": "Welcher Spieler erfand die
     * \"Schutzschwalbe\"?", "correctAnswer": "Andreas Möller", "wrongAnswer1":
     * "Rudi Völler", "wrongAnswer2": "Dede" }
     *
     * @param token
     * @param question
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException
     * @throws DataNotFoundException
     * @throws NotAuthorizedException
     */
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
                if (validator.checkLenght(question.getWrongAnswer1(), 255, false) && validator.checkLenght(question.getWrongAnswer2(), 255, false)
                        && validator.checkLenght(question.getWrongAnswer3(), 255, false) && validator.checkLenght(question.getQuestion(), 255, false)
                        && validator.checkLenght(question.getCorrectAnswer(), 255, true)) {
                    helper.mergeObject(em, question);
                } else {
                    throw new InputValidationException("Validation failed");
                }
            } else {
                throw new DataNotFoundException("Slider at index" + question.getId() + " does not exits");
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
