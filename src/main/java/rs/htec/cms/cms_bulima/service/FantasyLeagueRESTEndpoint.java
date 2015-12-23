/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import rs.htec.cms.cms_bulima.domain.FantasyLeague;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/fantasyLeagues")
public class FantasyLeagueRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method: .../rest/fantasyLeagues/{id} This method return single
     * element of club at index in JSON. Example for JSON response:<br/> {<br/>
     * "logo": "",<br/> "createDate": 1406902029000,<br/> "startDate":
     * 1406902029000,<br/>
     * "numMembers": 1,<br/> "activity": "sehr aktiv",<br/> "leaguehash":
     * "cfbd436c-dc48-462c-bf31-413e03dc53db",<br/> "secondLeague": 0,<br/>
     * "dayOneActivity": 0,<br/> "dayTwoActivity": 0,<br/> "dayThreeActivity":
     * 0,<br/>
     * "dayFourActivity": 0,<br/> "dayFiveActivity": 0,<br/> "daySixActivity":
     * 0,<br/>
     * "daySevenActivity": 0,<br/> "lastSevenDaysActivity": 0,<br/>
     * "leagueType": "NORMAL",<br/> "leagueStatus": "NORMAL",<br/> "inactive":
     * 0,<br/> "warnedAt": 1437697830000,<br/> "updatedForSeason": 0,<br/>
     * "options": 0,<br/> "description": null,<br/>
     * "password": "test1234",<br/> "name": "TestLiga3",<br/> "id": 2 <br/>}
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id of league we are searching for
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "There is no league at index 1!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyLeague(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyLeague league = em.find(FantasyLeague.class, id);
        if (league == null) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no league at index " + id + "!"), em);
            throw new DataNotFoundException("There is no league at index " + id + "!");
        }
        helper.setResponseToHistory(history, Response.ok().entity(league).build(), em);
        return Response.ok().entity(league).build();
    }

    /**
     * Returns FantasyLeagues for define parameters. Example for response: {
     * <br/>
     * "count": 1,<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1407406747000,<br/>
     * "logo": null,<br/>
     * "activity": "sehr aktiv",<br/>
     * "startDate": 1406909792000,<br/>
     * "numMembers": 18,<br/>
     * "leaguehash": "37e0e52b-abf2-4369-92c7-a460db49f57c",<br/>
     * "secondLeague": 0,<br/>
     * "dayOneActivity": 0,<br/>
     * "dayTwoActivity": 0,<br/>
     * "dayThreeActivity": 0,<br/>
     * "dayFourActivity": 0,<br/>
     * "dayFiveActivity": 0,<br/>
     * "daySixActivity": 0,<br/>
     * "daySevenActivity": 0,<br/>
     * "lastSevenDaysActivity": 0,<br/>
     * "leagueType": "NORMAL",<br/>
     * "leagueStatus": "NORMAL",<br/>
     * "inactive": 0,<br/>
     * "warnedAt": 1437697831000,<br/>
     * "updatedForSeason": 0,<br/>
     * "options": 1,<br/>
     * "description": null,<br/>
     * "password": null,<br/>
     * "name": "TestName",<br/>
     * "id": 3<br/>
     * }<br/>
     * ]<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param page number of page for searched results
     * @param limit number of matchPlayerStats that are returned in body
     * @param name of FantasyLeagues that is searched
     * @param leagueType of FantasyLeagues that are searched
     * @return FantasyLeagues list in JSON format
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyLeagues(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("name") String name,
            @QueryParam("leagueType") String leagueType) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<FantasyLeague> bids;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyLeague f ");
        String operator = "WHERE";
        if (name != null) {
            query.append(operator).append(" f.name = '").append(name).append("'");
            operator = " AND";
        }
        if (leagueType != null) {
            query.append(operator).append(" f.leagueType = ").append(leagueType);
        }
        bids = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (bids.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no FantasyLeague for this search!"), em);
            throw new DataNotFoundException("There is no FantasyLeague for this search!");
        } else {
            String countQuery = query.toString().replaceFirst("f", "count(f)");
            long count = (long) em.createQuery(countQuery).getSingleResult();
            GetObject go = new GetObject();
            go.setCount(count);
            go.setData(bids);
            helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
            return Response.ok().entity(go).build();
        }
    }

    /**
     * Returns all league types. Example for response: [<br/>
     * "NORMAL",<br/>
     * "CLUB_HOLDER_LEAGUE",<br/>
     * "OFFICIAL"<br/>
     * ]<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @return status 200 OK with JSON body
     */
    @GET
    @Path("/leagueTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeagueTypes(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "SELECT distinct leagueType FROM FantasyLeague f";
        List<String> list = em.createQuery(query).getResultList();
        helper.setResponseToHistory(history, Response.ok().entity(list).build(), em);
        return Response.ok().entity(list).build();
    }
}
