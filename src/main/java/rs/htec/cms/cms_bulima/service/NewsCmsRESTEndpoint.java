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
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author marko
 */
@Path("/news")
public class NewsCmsRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public NewsCmsRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: /news/{page}/{limit}<br>
     * This method returns JSON list of news at defined page with defined limit.
     * It produces APPLICATION_JSON media type. Example for JSON list for 1
     * page, 2 limit: <br/>
     * [{<br/>
     * "idFantasyClub": "null",<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/>
     * "newsHeadlineWeb": "NEUES VOM TRANSFERMARKT",<br/>
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu Sport1",<br/>
     * "newsMessageMobile": "Kehrer wechselt für 100.000 von Los Chipirones zu Sport1",<br/>
     * "id": "4",<br/>
     * "newsDate": "2015-07-20 15:32:35.0",<br/>
     * "newsType": "transfer",<br/>
     * "createDate": "2015-07-20 15:32:36.0",<br/>
     * "idFantasyLeague": "rs.htec.cms.cms_bulima.domain.FantasyLeague[ id=7175 ]"<br/>
     * },<br/>
     * {<br/>
     * "idFantasyClub": "rs.htec.cms.cms_bulima.domain.FantasyClub[ id=27483 ]",<br/>
     * "newsHeadlineMobile": "NEUE AUFSTELLUNG",<br/>
     * "newsHeadlineWeb": "NEUE AUFSTELLUNG",<br/>
     * "newsMessageWeb": "System: 4-3-3{@code <br/>}Tor: Langerak{@code <br/>}Abwehr: Garcia,
     * Schär, Matip, Felipe{@code <br/>}Mittelfeld: Clemens, Holtby,
     * Schwegler{@code <br/>}Sturm: Kurányi, Kachunga, Osako", "newsMessageMobile": "Für
     * den kommenden 2. Spieltag hast du ein 4-3-3-System mit folgenden Spielern
     * aufgestellt:{@code <br/>}Tor: Langerak{@code <br/>}Abwehr: Garcia, Schär, Matip,
     * Felipe{@code <br/>}Mittelfeld: Clemens, Holtby, Schwegler{@code <br/>}Sturm: Kurányi,
     * Kachunga, Osako",<br/> "id": "5",<br/> "newsDate": "2015-08-17 14:47:54.0",<br/>
     * "newsType": "lineup",<br/> "createDate": "2015-07-20 15:33:48.0",<br/>
     * "idFantasyLeague": "null"<br/> } ]
     * @param token
     * @param page number of page at which we search for News
     * @param limit number of News method returns
     * @return {@link Response} Response {@link Status#OK} 200 OK with JSON body
     * @throws DataNotFoundException
     * @throws NotAuthorizedException
     */

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

    /**
     * API for this method is /rest/news
     *This method recieves JSON object, and put it in the base. Example for JSON:
     *      {
                "newsHeadlineMobile": "NEUER TRANSFER",
                "newsHeadlineWeb": "NEUES VOM TRANSFERMARKT",
                "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu Sport1",
                "newsMessageMobile": "Kehrer wechselt für 100.000 von Los Chipirones zu Sport1",
                "newsDate": "2015-07-20T15:32:35.0",
                "newsType": "transfer"
            }
     * @param token token is header param
     * @param news
     * @return Response with status CREATED (201)
     * @throws InputValidationException
     * @throws NotAuthorizedException
    **/
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token);
            news.setCreateDate(new Date());
            if (validator.checkLenght(news.getNewsHeadlineMobile(), 255, true) && validator.checkLenght(news.getNewsHeadlineWeb(), 255, true)
                    && validator.checkLenght(news.getNewsMessageMobile(), 255, true) && validator.checkLenght(news.getNewsMessageWeb(), 255, true)
                    && validator.checkLenght(news.getNewsType(), 255, true)) {

                helper.persistObject(em, news);
                return Response.status(Response.Status.CREATED).build();
            } else {
                throw new InputValidationException("Validation failed");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    /**
     * API for method: /news/{id} This method find news with defined id. Id is
     * retrieved from URL. If News with that index does not exist method throws
     * exception. Otherwise method remove that News.
     *
     * @param token
     * @param id of News that should be deleted.
     * @return Response 200 OK
     * @throws NotAuthorizedException
     */
    @DELETE
    @Path("/{id}")
    public Response deleteNews(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.DELETE, token);
            News news = em.find(News.class, id);
            helper.removeObject(em, news, id);
            return Response.ok().build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    /**
     * API for this method is /rest/news This method recieves JSON object, and
     * update database. Example for JSON: { "newsHeadlineMobile": "NEUER
     * TRANSFER", "newsHeadlineWeb": "NEUES VOM TRANSFERMARKT",
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1", "newsMessageMobile": "Kehrer wechselt für 100.000 von Los
     * Chipirones zu Sport1", "newsDate": "2015-07-20T15:32:35.0", "newsType":
     * "transfer" }
     *
     * @param token
     * @param news
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException
     * @throws DataNotFoundException
     * @throws NotAuthorizedException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token);
            News oldNews = em.find(News.class, news.getId());
            if (oldNews != null) {
                if (validator.checkLenght(news.getNewsHeadlineMobile(), 255, true) && validator.checkLenght(news.getNewsHeadlineWeb(), 255, true)
                        && validator.checkLenght(news.getNewsMessageMobile(), 255, true) && validator.checkLenght(news.getNewsMessageWeb(), 255, true)
                        && validator.checkLenght(news.getNewsType(), 255, true)) {

                    helper.mergeObject(em, news);
                } else {
                    throw new InputValidationException("Validation failed");
                }
            } else {
                throw new DataNotFoundException("Slider at index" + news.getId() + " does not exits");
            }
            return Response.ok("Successfully updated!").build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }
}
