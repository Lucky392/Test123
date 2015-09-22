/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDay;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
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
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2
     * limit:<br/> [ {<br/> "date": "2015-07-20 00:00:00.0",<br/>
     * "wrongAnswer3": "Mehmet Scholl",<br/>
     * "question": "Wer erzielte das entscheidende Tor für den FC Bayern München
     * in der Saison 200/01, als der FC Schalke 04 für ein paar Minuten Meister
     * war?",<br/> "id": "1",<br/> "correctAnswer": "Patrik Andersson",<br/>
     * "wrongAnswer1": "Giovanne Elber",<br/> "wrongAnswer2": "Stefan
     * Effenberg"<br/> },<br/> { <br/>"date": "2015-07-21 00:00:00.0",<br/>
     * "wrongAnswer3": "12 Minuten",<br/> "question": "Michael Tönnies erzielte
     * den schnellsten Hattrick der Bundesliga Geschichte. Wie viele Minuten
     * benötigte er?",<br/> "id": "2",<br/> "correctAnswer": "5 Minuten",<br/>
     * "wrongAnswer1": "7 Minuten",<br/> "wrongAnswer2": "10 Minuten"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for Question
     * @param limit number of Question method returns
     * @param orderingColumn column name for ordering
     * @param search word for searching question, correctAnswer, wrongAnswer1,
     * wrongAnswer2, wrongAnswer3
     * @param minDate is a start date for filtering
     * @param maxDate is a end date for filtering
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionss(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.SEARCH, token);

        List<QuestionOfTheDay> questions = null;

        StringBuilder query = new StringBuilder("SELECT q FROM QuestionOfTheDay q ");

        if (minDate != 0 && maxDate != 0) {
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append("WHERE (q.date BETWEEN ").append(d1).append(" AND ").append(d2);
        }

        if (search != null) {
            search = "%" + search + "%";
            query.append(minDate != 0 ? " AND" : "WHERE")
                    .append(" q.question LIKE '")
                    .append(search)
                    .append("' OR q.correctAnswer LIKE '")
                    .append(search).append("' OR q.wrongAnswer1 LIKE '")
                    .append(search).append("' OR n.wrongAnswer2 LIKE '")
                    .append(search).append("' OR n.wrongAnswer3 LIKE '")
                    .append(search).append("'");
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        System.out.println("QUERY " + query);
        questions = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();

        if (questions == null || questions.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        return Response.ok().entity(helper.getJson(questions)).build();
    }

    /**
     * API for this method is /rest/question This method recieves JSON object,
     * and put it in the base. Example for JSON: {<br/> "date":
     * "2015-07-25T00:00:00.0",<br/> "wrongAnswer3": "Jürgen Kohler",<br/>
     * "question": "Welcher Spieler erfand die \"Schutzschwalbe\"?",<br/>
     * "correctAnswer": "Andreas Möller",<br/> "wrongAnswer1": "Rudi
     * Völler",<br/> "wrongAnswer2": "Dede"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param question is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertQuestion(@HeaderParam("authorization") String token, QuestionOfTheDay question) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.ADD, token);
        if (validator.checkLenght(question.getWrongAnswer1(), 255, false) && validator.checkLenght(question.getWrongAnswer2(), 255, false)
                && validator.checkLenght(question.getWrongAnswer3(), 255, false) && validator.checkLenght(question.getQuestion(), 255, false)
                && validator.checkLenght(question.getCorrectAnswer(), 255, true)) {

            helper.persistObject(em, question);
            return Response.status(Status.CREATED).build();

        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: /question/{id} This method find question with defined id.
     * Id is retrieved from URL. If question with that id does not exist method
     * throws exception. Otherwise method remove that question.
     *
     * @param token is a header parameter for checking permission
     * @param id of question that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteQuestion(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.QUESTION_OF_THE_DAY, MethodConstants.DELETE, token);
        QuestionOfTheDay question = em.find(QuestionOfTheDay.class, id);
        helper.removeObject(em, question, id);
        return Response.ok().build();

    }

    /**
     * API for this method is /rest/question This method recieves JSON object,
     * and update database. Example for JSON: { <br/>"date":
     * "2015-07-25T00:00:00.0",<br/>
     * "wrongAnswer3": "Jürgen Kohler",<br/> "question": "Welcher Spieler erfand
     * die \"Schutzschwalbe\"?",<br/> "correctAnswer": "Andreas Möller",<br/>
     * "wrongAnswer1": "Rudi Völler",<br/> "wrongAnswer2": "Dede"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param question is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException DataNotFoundException
     * Example for exception:<br/> {<br/>
     * "errorMessage": "Question at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@HeaderParam("authorization") String token, QuestionOfTheDay question
    ) {
        EntityManager em = helper.getEntityManager();
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
            throw new DataNotFoundException("Question at index" + question.getId() + " does not exits");
        }
        return Response.ok("Successfully updated!").build();

    }

}
