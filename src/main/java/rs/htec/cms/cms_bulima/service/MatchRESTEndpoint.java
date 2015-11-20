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
import javax.persistence.NoResultException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Match;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchPOJO;

/**
 *
 * @author marko
 */
@Path("/matches")
public class MatchRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Match for defined id. <br/>
     * Example for json response:<br/>
     * {<br/>
     * "matchdayUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchdays/1",<br/>
     * "clubGuesUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/3",<br/>
     * "clubHomeUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/93",<br/>
     * "clubGuestName": "1. FC Kaiserslautern",<br/> "clubHomeName": "MSV
     * Duisburg",<br/>
     * "createDate": 1437385012000,<br/> "updateAt": 1438071030000,<br/>
     * "isCalculated": 1,<br/> "idSport1Match": "2415925",<br/> "homeScore":
     * 1,<br/> "guestScore": 3,<br/>
     * "startTime": 1437762600000,<br/> "id": 1<br/> }
     *
     *
     * @param token is a header parameter for checking permission
     * @param id of Match that is looked for
     * @return 
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no matches for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        MatchPOJO pojo;
        try {
            Match match = (Match) em.createNamedQuery("Match.findById").setParameter("id", id).getSingleResult();
            pojo = new MatchPOJO(match);
        } catch (NoResultException e) {
            throw new DataNotFoundException("Match at index " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }

    /**
     * API for method:
     * .../rest/match?page=VALUE&limit=VALUE&clubID=VALUE&matchdayID=VALUE&startDate=VALUE
     * This method returns JSON list and count number. Default value for page is
     * 1, and for limit is 10. There is a possibility for search by clubID and
     * matchdayID. Filtering by startDate. It produces APPLICATION_JSON media
     * type. Example for JSON list for 1 page, 2 limit:<br/> { <br/>"count":
     * 612,<br/> "data": [ {<br/> "matchdayUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchdays/1",<br/>
     * "clubGuesUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/3",<br/>
     * "clubHomeUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/93",<br/>
     * "clubGuestName": "1. FC Kaiserslautern",<br/> "clubHomeName": "MSV
     * Duisburg",<br/>
     * "createDate": 1437385012000,<br/> "updateAt": 1438071030000,<br/>
     * "isCalculated": 1,<br/> "idSport1Match": "2415925",<br/> "homeScore":
     * 1,<br/> "guestScore": 3,<br/>
     * "startTime": 1437762600000,<br/> "id": 1<br/> },<br/> {<br/>
     * "matchdayUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchdays/1",<br/>
     * "clubGuesUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/59",<br/>
     * "clubHomeUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/58",<br/>
     * "clubGuestName": "Karlsruher SC",<br/> "clubHomeName": "SpVgg Greuther
     * Fürth",<br/>
     * "createDate": 1437385012000,<br/> "updateAt": 1438071038000,<br/>
     * "isCalculated": 1,<br/> "idSport1Match": "2415969",<br/> "homeScore":
     * 1,<br/> "guestScore": 0,<br/>
     * "startTime": 1437822000000,<br/> "id": 2<br/> } ]<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for Match
     * @param limit number of Match returns
     * @param clubID id of club
     * @param matchdayID id of match day
     * @param startDate start date
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no matches for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatch(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("clubID") long clubID, @QueryParam("matchdayID") long matchdayID, @QueryParam("startDate") long startDate) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT m FROM Match m ");
        if (clubID != 0) {
            query.append("WHERE (m.idGuestClub.id = ")
                    .append(clubID)
                    .append(" OR m.idHomeClub.id = ")
                    .append(clubID)
                    .append(")");
        }
        if (matchdayID != 0) {
            query.append(clubID != 0 ? " AND" : "WHERE")
                    .append(" m.idMatchday.id = ")
                    .append(matchdayID);
        }
        if (startDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(startDate);
            query.append(clubID != 0 || matchdayID != 0 ? " AND" : "WHERE")
                    .append(" m.startTime LIKE '%")
                    .append(sdf.format(date))
                    .append("%'");
        }
        System.out.println(query);
        List<Match> matches = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (matches == null || matches.isEmpty()) {
            throw new DataNotFoundException("There is no mathes for this search!");
        }
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(MatchPOJO.toMatchPOJOList(matches));
        return Response.ok().entity(go).build();
    }
}
