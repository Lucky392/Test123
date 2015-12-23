    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.NewsPOJO;
import rs.htec.cms.cms_bulima.pojo.SeasonPOJO;

/**
 *
 * @author marko
 */
@Path("/news")
public class NewsCmsRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * New fields
     * added:[idFantasyLeague,idFantasyClub,idAuction,idFantasyClubSender,commentsCount]
     * API for method:
     * .../rest/news?page=VALUE&limit=VALUE&column=VALUE&search=VALUE&minDate=VALUE&maxDate=VALUE&newsType=VALUE
     * This method returns JSON list. Default value for page is 1, and for limit
     * is 10. You can put your values for page, limit, orderColumn, searchWord,
     * start and end date, newsType. It produces APPLICATION_JSON media type.
     * Example for JSON list for 1 page, 2 limit:<br/> { <br/> "count":
     * 837536,<br/> "data": [<br/> {<br/>
     * "createDate": 1437402253000,<br/>
     * "newsType": "transfer",<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/>
     * "newsHeadlineWeb": "NEUES VOM TRANSFERMARKT",<br/>
     * "newsMessageMobile": "Brouwers wechselt für 1.000.000 von FC Test zu
     * Sport1",<br/>
     * "newsMessageWeb": "Brouwers wechselt für 1.000.000 von FC Test zu
     * Sport1",<br/>
     * "idFantasyLeague": 7175,<br/>
     * "idAuction": null,<br/>
     * "idFantasyClub": null,<br/>
     * "newsDate": 1437402252000,<br/>
     * "commentsCount": 0,<br/>
     * "idFantasyClubSender": null,<br/>
     * "id": 22<br/>
     * },<br/> { "createDate": 1437402314000,<br/>
     * "newsType": "lineup",<br/>
     * "newsHeadlineMobile": "NEUE AUFSTELLUNG",<br/>
     * "newsHeadlineWeb": "NEUE AUFSTELLUNG",<br/>
     * "newsMessageMobile": "Für den kommenden 2. Spieltag hast du ein
     * 3-5-2-System mit folgenden Spielern aufgestellt:<br/>Tor:
     * Özcan<br/>Abwehr: Djakpa, Brooks, Sorg<br/>Mittelfeld: Koo, Gülselam,
     * Nordtveit, Strobl, Grillitsch<br/>Sturm: Robben, Green",<br/>
     * "newsMessageWeb": "System: 3-5-2<br/>Tor: Özcan<br/>Abwehr: Djakpa,
     * Brooks, Sorg<br/>Mittelfeld: Koo, Gülselam, Nordtveit, Strobl,
     * Grillitsch<br/>Sturm: Robben, Green",<br/>
     * "idFantasyLeague": null,<br/>
     * "idAuction": null,<br/>
     * "idFantasyClub": 50774,<br/>
     * "newsDate": 1439799996000,<br/>
     * "commentsCount": 0,<br/>
     * "idFantasyClubSender": null,<br/>
     * "id": 23<br/>
     * }<br/> ]
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for News
     * @param limit number of News method returns
     * @param orderingColumn column name for ordering, if you put "-" before
     * column name, that mean DESC ordering.
     * @param search word for searching newsType, newsHeadlineWeb,
     * newsHeadlineMobile
     * @param minDate is a start date for filtering time in millis
     * @param maxDate is a end date for filtering time in millis
     * @param newsType type of News
     * @param idFantasyLeague #filter News base on Fantasy League id
     * @param idFantasyClub #filter News base on Fantasy Club id
     * @param idAuction #id of News
     * @param idFantasyClubSender #filter News base on Fantasy Club Sender id
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET  //question?page=1&limit=10&minDate=1438387200000&maxDate=1439164800000&search=Viktor&column=id
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getNews(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate, @QueryParam("newsType") String newsType,
            @QueryParam("idFantasyLeague") String idFantasyLeague, @QueryParam("idFantasyClub") String idFantasyClub, @QueryParam("idAuction") String idAuction, @QueryParam("idFantasyClubSender") String idFantasyClubSender) {

//        EntityManager em = helper.getEntityManager();
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<News> news;
        StringBuilder query = new StringBuilder("SELECT n FROM News n");
        String operator = " WHERE ";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(operator).append("n.newsDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND ";
        }

        if (newsType != null) {
            query.append(operator).append("n.newsType = '").append(newsType).append("'");
            operator = " AND ";
        }

        if (idFantasyLeague != null) {
            query.append(operator).append("n.idFantasyLeague = ").append(idFantasyLeague);
            operator = " AND ";
        }

        if (idFantasyClub != null) {
            query.append(operator).append("n.idFantasyClub = ").append(idFantasyClub);
            operator = " AND ";
        }

        if (idAuction != null) {
            query.append(operator).append("n.idAuction = ").append(idAuction);
            operator = " AND ";
        }

        if (idFantasyClubSender != null) {
            query.append(operator).append("n.idFantasyClubSender = ").append(idFantasyClubSender);
            operator = " AND ";
        }

        if (search != null) {
            search = "%" + search + "%";
            query.append(operator)
                    .append("(n.newsHeadlineWeb LIKE '")
                    .append(search)
                    .append("' OR n.newsHeadlineMobile LIKE '")
                    .append(search).append("' OR n.newsMessageWeb LIKE '")
                    .append(search).append("' OR n.newsMessageMobile LIKE '")
                    .append(search).append("')");
//            operator = " AND ";
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        news = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (news == null || news.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no news for this search!"), em);
            throw new DataNotFoundException("There is no news for this search!");
        }
//        GenericEntity<List<News>> newsEntity = new GenericEntity<List<News>>(news) {
//        };
        String countQuery = query.toString().replaceFirst("n", "count(n)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        List<NewsPOJO> pojos = NewsPOJO.toNewsPOJOList(news);
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(pojos);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/news/{id} This method return single element of
     * news at index in JSON. Example for JSON response: <br/>{ "createDate":
     * 1437490202000, <br/>
     * "newsType": "transfer",<br/>
     * "newsHeadlineMobile": "SCOUT - VOLLTREFFER",<br/>
     * "newsHeadlineWeb": "SCOUT - VOLLTREFFER",<br/>
     * "newsMessageMobile": "Herzlichen Glückwunsch! Dein Scout hat einen
     * passenden Spieler dich gefunden.<br/><br/>Mittelfeld Karim Bellarabi von
     * Bayer Leverkusen wurde herausgesucht.<br/>Marktwert:
     * 13.500.000<br/>Punkte: 0<br/><br/>Gehe zum Transfermarkt und besuche den
     * Scoutbereich, um für diesen Spieler ein Gebot abzugeben",<br/>
     * "newsMessageWeb": "Herzlichen Glückwunsch! Dein Scout hat einen passenden
     * Spieler dich gefunden.<br/><br/>Mittelfeld Karim Bellarabi von Bayer
     * Leverkusen wurde herausgesucht.<br/>Marktwert: 13.500.000<br/>Punkte:
     * 0<br/><br/>Gehe zum Transfermarkt und besuche den Scoutbereich, um für
     * diesen Spieler ein Gebot abzugeben",<br/>
     * "idFantasyLeague": null,<br/>
     * "idAuction": null,<br/>
     * "idFantasyClub": 49245,<br/>
     * "idFantasyClubSender": null,<br/>
     * "commentsCount": 0,<br/>
     * "newsDate": 1437490202000,<br/>
     * "id": 2451<br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of news we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewsById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        NewsPOJO pojo;
        try {
            News news = (News) em.createNamedQuery("News.findById").setParameter("id", id).getSingleResult();
            pojo = new NewsPOJO(news);
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("News at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("News at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(pojo).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/news/newsType This method return list of
     * newsType in JSON. Example for JSON: <br/>[<br/> "important",<br/>
     * "Stryking",<br/> "transfer",<br/>
     * "matchday",<br/> "lineup",<br/> "welcome",<br/> "reward",<br/>
     * "leagueJoined",<br/> "clubLeft",<br/>
     * "leagueNameChanged"<br/>]
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/newsType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewsType(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "SELECT distinct newsType FROM News n";
        List<String> list = em.createQuery(query).getResultList();
        Response response = Response.ok().entity(list).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/news This method recieves JSON object,
     * and put it in the base. Example for JSON that you need to send: {<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/> "newsHeadlineWeb": "NEUES
     * VOM TRANSFERMARKT",<br/>
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1",<br/> "newsMessageMobile": "Kehrer wechselt für 100.000 von Los
     * Chipirones zu Sport1",<br/> "newsDate": "2015-07-20T15:32:35.0",<br/>
     * "newsType": "transfer", <br/>"idFantasyLeague": null,<br/>
     * "idAuction": null,<br/>
     * "idFantasyClub": 49245,<br/>
     * "idFantasyClubSender": null,<br/>
     * "commentsCount": 0,<br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param news is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertNews(@HeaderParam("authorization") String token, @Context HttpServletRequest request, News news) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        if (validator.checkLenght(news.getNewsHeadlineMobile(), 255, true) && validator.checkLenght(news.getNewsHeadlineWeb(), 255, true)
                && validator.checkLenght(news.getNewsMessageMobile(), 255, true) && validator.checkLenght(news.getNewsMessageWeb(), 255, true)
                && validator.checkLenght(news.getNewsType(), 255, true)) {
            news.setCreateDate(new Date());
            helper.persistObject(em, news);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: .../rest/news/{id} This method find news with defined id.
     * Id is retrieved from URL. If News with that index does not exist method
     * throws exception. Otherwise method remove that News.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of News that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteNews(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.DELETE, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        News news = em.find(News.class, id);
        helper.removeObject(em, news, id);
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/news This method recieves JSON object,
     * and update database. Example for JSON that you need to send: {<br/>
     * "newsHeadlineMobile": "NEUER TRANSFER",<br/> "newsHeadlineWeb": "NEUES
     * VOM TRANSFERMARKT",<br/>
     * "newsMessageWeb": "Kehrer wechselt für 100.000 von Los Chipirones zu
     * Sport1",<br/> "newsMessageMobile": "Kehrer wechselt für 100.000 von Los
     * Chipirones zu Sport1",<br/> "newsDate": "2015-07-20T15:32:35.0",<br/>
     * "newsType": "transfer",<br/> "idFantasyLeague": null,<br/>
     * "idAuction": null,<br/>
     * "idFantasyClub": 49245,<br/>
     * "idFantasyClubSender": null,<br/>
     * "commentsCount": 0,<br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param news is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "News at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNews(@HeaderParam("authorization") String token, @Context HttpServletRequest request, News news) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        News oldNews = em.find(News.class, news.getId());
        if (oldNews != null) {
            if (validator.checkLenght(news.getNewsHeadlineMobile(), 255, true) && validator.checkLenght(news.getNewsHeadlineWeb(), 255, true)
                    && validator.checkLenght(news.getNewsMessageMobile(), 255, true) && validator.checkLenght(news.getNewsMessageWeb(), 255, true)
                    && validator.checkLenght(news.getNewsType(), 255, true)) {
                news.setCreateDate(oldNews.getCreateDate());
                helper.mergeObject(em, news);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("News at index " + news.getId() + " does not exits"), em);
            throw new DataNotFoundException("News at index " + news.getId() + " does not exits");
        }
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method: .../rest/news/count This method return number of all
     * news in database.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountNews(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(n) From News n";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
