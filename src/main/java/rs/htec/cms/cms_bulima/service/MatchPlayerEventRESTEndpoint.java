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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.MatchPlayerEvent;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchPlayerEventPOJO;

/**
 *
 * @author marko
 */
@Path("matchPlayerEvents")
public class MatchPlayerEventRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method:
     * .../rest/matchPlayerEvents?page=VALUE&limit=VALUE&type=VALUE&min=VALUE&matchPlayerID=VALUE
     * This method returns JSON list and count number. Default value for page is
     * 1, and for limit is 10. There is a possibility for search by
     * eventTimeMinute and matchPlayer. Filtering by type. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2
     * limit:<br/> {<br/>
     * "count": 1233,<br/> "data": [ {<br/> "matchPlayerUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchPlayer/26",<br/>
     * "createDate": 1438071103000,<br/> "updateAt": null,<br/>
     * "eventTimeMinute": 0,<br/>
     * "id": 1,<br/> "type": "soccer_match_playing_lineup"<br/> },<br/> {<br/>
     * "matchPlayerUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchPlayer/20",<br/>
     * "createDate": 1438071103000,<br/> "updateAt": null,<br/>
     * "eventTimeMinute": 0,<br/>
     * "id": 2,<br/> "type": "soccer_match_playing_lineup"<br/> } <br/>] }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for Match player event
     * @param limit number of Match player event returns
     * @param type type of event
     * @param min event time minute
     * @param matchPlayerID id of match player
     * @return Response 200 OK with JSON body 
     * @throws DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "There is no events for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("type") String type, @QueryParam("min") int min, @QueryParam("matchPlayerID") long matchPlayerID) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT m FROM MatchPlayerEvent m ");
        if (type != null) {
            query.append("WHERE m.type = '")
                    .append(type)
                    .append("'");
        }
        if (min != 0) {
            query.append(type != null ? " AND" : "WHERE")
                    .append(" m.eventTimeMinute = ")
                    .append(min);
        }
        if (matchPlayerID != 0) {
            query.append(type != null || min != 0 ? " AND" : "WHERE")
                    .append(" m.idMatchPlayer.id = ")
                    .append(matchPlayerID);
        }
        System.out.println(query);
        List<MatchPlayerEvent> events = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (events == null || events.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no events for this search!"), em);
            throw new DataNotFoundException("There is no events for this search!");
        }
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(MatchPlayerEventPOJO.toMatchPlayerEventPOJOList(events));
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * API for method: .../rest/matchPlayerEvents/types This method return list
     * of types in JSON. Example for JSON:<br/> [<br/> "soccer_match_playing_lineup",<br/>
     * "soccer_match_playing_bench",<br/> "soccer_match_goal_left-foot",<br/>
     * "soccer_match_card_yellow",<br/> "soccer_match_goal_own-goal",<br/>
     * "soccer_match_goal_right-foot",<br/> "soccer_match_playing_substitute-out",<br/>
     * "soccer_match_playing_substitute-in",<br/> "soccer_match_goal_head",<br/>
     * "soccer_match_goal_penalty",<br/> "soccer_match_card_red",<br/>
     * "soccer_match_special_missed-penalty",<br/> "soccer_match_card_yellow-red"<br/> ]
     *
     * @param token 
     * @param request 
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/types")
    public Response getTypes(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "SELECT distinct type FROM MatchPlayerEvent m";
        List<String> list = em.createQuery(query).getResultList();
        if (list.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no types in match playes event!"), em);
            throw new DataNotFoundException("There is no types in match playes event!");
        }
        helper.setResponseToHistory(history, Response.ok().entity(list).build(), em);
        return Response.ok().entity(list).build();
    }
}
