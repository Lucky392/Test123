    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.text.SimpleDateFormat;
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
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
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
     * API for method:
     * .../rest/news?page=VALUE&limit=VALUE&column=VALUE&search=VALUE&minDate=VALUE&maxDate=VALUE&newsType=VALUE
     * This method returns JSON list. Default value for page is 1, and for limit
     * is 10. You can put your values for page, limit, orderColumn, searchWord,
     * start and end date, newsType. It produces APPLICATION_JSON media type.
     * Example for JSON list for 1 page, 2 limit: <br/>
     * [{<br/>
     * "idFantasyClub": "null",<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/>
     * "newsHeadlineWeb": "NEUES VOM TRANSFERMARKT",<br/>
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1",<br/>
     * "newsMessageMobile": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1",<br/>
     * "id": "4",<br/>
     * "newsDate": "2015-07-20 15:32:35.0",<br/>
     * "newsType": "transfer",<br/>
     * "createDate": "2015-07-20 15:32:36.0",<br/>
     * "idFantasyLeague": "rs.htec.cms.cms_bulima.domain.FantasyLeague[ id=7175
     * ]"<br/>
     * },<br/>
     * {<br/>
     * "idFantasyClub": "rs.htec.cms.cms_bulima.domain.FantasyClub[ id=27483
     * ]",<br/>
     * "newsHeadlineMobile": "NEUE AUFSTELLUNG",<br/>
     * "newsHeadlineWeb": "NEUE AUFSTELLUNG",<br/>
     * "newsMessageWeb": "System: 4-3-3{@code <br/>}Tor:
     * Langerak{@code <br/>}Abwehr: Garcia, Schär, Matip,
     * Felipe{@code <br/>}Mittelfeld: Clemens, Holtby,
     * Schwegler{@code <br/>}Sturm: Kurányi, Kachunga, Osako",
     * "newsMessageMobile": "Für den kommenden 2. Spieltag hast du ein
     * 4-3-3-System mit folgenden Spielern aufgestellt:{@code <br/>}Tor:
     * Langerak{@code <br/>}Abwehr: Garcia, Schär, Matip,
     * Felipe{@code <br/>}Mittelfeld: Clemens, Holtby,
     * Schwegler{@code <br/>}Sturm: Kurányi, Kachunga, Osako",<br/> "id":
     * "5",<br/> "newsDate": "2015-08-17 14:47:54.0",<br/>
     * "newsType": "lineup",<br/> "createDate": "2015-07-20 15:33:48.0",<br/>
     * "idFantasyLeague": "null"<br/> } ] You can also
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for News
     * @param limit number of News method returns
     * @param orderingColumn column name for ordering, if you put "-" before
     * column name, that mean DESC ordering.
     * @param search word for searching newsType, newsHeadlineWeb,
     * newsHeadlineMobile
     * @param minDate is a start date for filtering time in millis
     * @param maxDate is a end date for filtering time in millis
     * @param newsType type of News
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET  //question?page=1&limit=10&minDate=1438387200000&maxDate=1439164800000&search=Viktor&column=id
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNews(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate, @QueryParam("newsType") String newsType) {

        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token);
        List<News> news;
        StringBuilder query = new StringBuilder("SELECT n FROM News n ");

        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append("WHERE n.newsDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
        }

        if (newsType != null) {
            query.append(minDate != 0 ? " AND" : "WHERE").append(" n.newsType = '").append(newsType).append("'");
        }

        if (search != null) {
            search = "%" + search + "%";
            query.append(minDate != 0 || newsType != null ? " AND" : "WHERE")
                    .append(" n.newsHeadlineWeb LIKE '")
                    .append(search)
                    .append("' OR n.newsHeadlineMobile LIKE '")
                    .append(search).append("' OR n.newsMessageWeb LIKE '")
                    .append(search).append("' OR n.newsMessageMobile LIKE '")
                    .append(search).append("'");
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        news = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (news == null || news.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        return Response.ok().entity(helper.getJson(news)).build();
    }

    /**
     * API for method: .../rest/news/newsType This method return list of
     * newsType in JSON. Example for JSON: <br/>[<br/> "important",<br/>
     * "Stryking",<br/> "transfer",<br/>
     * "matchday",<br/> "lineup",<br/> "welcome",<br/> "reward",<br/>
     * "leagueJoined",<br/> "clubLeft",<br/>
     * "leagueNameChanged"<br/> ]
     *
     * @param token is a header parameter for checking permission
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/newsType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewsType(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token);
        String query = "SELECT distinct newsType FROM News n";
        List<String> list = em.createQuery(query).getResultList();
        return Response.ok().entity(list).build();
    }

    /**
     * API for this method is .../rest/news This method recieves JSON object,
     * and put it in the base. Example for JSON that you need to send: {<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/> "newsHeadlineWeb": "NEUES
     * VOM TRANSFERMARKT",<br/>
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1",<br/> "newsMessageMobile": "Kehrer wechselt für 100.000 von Los
     * Chipirones zu Sport1",<br/> "newsDate": "2015-07-20T15:32:35.0",<br/>
     * "newsType": "transfer" <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param news is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     *
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
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
    }

    /**
     * API for method: .../rest/news/{id} This method find news with defined id.
     * Id is retrieved from URL. If News with that index does not exist method
     * throws exception. Otherwise method remove that News.
     *
     * @param token is a header parameter for checking permission
     * @param id of News that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteNews(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.DELETE, token);
        News news = em.find(News.class, id);
        helper.removeObject(em, news, id);
        return Response.ok().build();
    }

    /**
     * API for this method is .../rest/news This method recieves JSON object,
     * and update database. Example for JSON that you need to send: {<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/> "newsHeadlineWeb": "NEUES
     * VOM TRANSFERMARKT",<br/>
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1",<br/> "newsMessageMobile": "Kehrer wechselt für 100.000 von Los
     * Chipirones zu Sport1",<br/> "newsDate": "2015-07-20T15:32:35.0",<br/>
     * "newsType": "transfer"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param news is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "News at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNews(@HeaderParam("authorization") String token, News news) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token);
        News oldNews = em.find(News.class, news.getId());
        if (oldNews
                != null) {
            if (validator.checkLenght(news.getNewsHeadlineMobile(), 255, true) && validator.checkLenght(news.getNewsHeadlineWeb(), 255, true)
                    && validator.checkLenght(news.getNewsMessageMobile(), 255, true) && validator.checkLenght(news.getNewsMessageWeb(), 255, true)
                    && validator.checkLenght(news.getNewsType(), 255, true)) {

                helper.mergeObject(em, news);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("News at index" + news.getId() + " does not exits");
        }

        return Response.ok("Successfully updated!").build();
    }
}
