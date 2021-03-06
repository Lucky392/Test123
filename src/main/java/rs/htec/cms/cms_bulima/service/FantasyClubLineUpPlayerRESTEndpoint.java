/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyClubLineUpPlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/lineupPlayers")
public class FantasyClubLineUpPlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method: .../rest/lineupPlayers/{id} This method return single
     * element of lineupPlayer with defined id in JSON.
     *
     * Example for JSON response: <br/>
     * {<br/>
     * "createDate": 1437439301000,<br/>
     * "idLeaguePlayer": 93488,<br/>
     * "idPlayerSlot": 3,<br/>
     * "isCaptain": 0,<br/>
     * "idLineUp": 23,<br/>
     * "urlToLineUp":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/lineup/23",<br/>
     * "urlToPlayerSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlot/3",<br/>
     * "leaguePlayerName": "Hiroki Sakai",<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeaguePlayer/93488",<br/>
     * "id": 34<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id of LineUpPlayer
     * @return 200 OK with LineUpPlayer in JSON format
     * @throws DataNotFoundException if LineUpPlayer with id doesn't exist in DB
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyClubLineUpById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyClubLineUpPlayerPOJO pojo = null;
        try {
            FantasyClubLineUpPlayer fantasyClubLineUpPlayer = (FantasyClubLineUpPlayer) em.createNamedQuery("FantasyClubLineUpPlayer.findById").setParameter("id", id).getSingleResult();
            pojo = new FantasyClubLineUpPlayerPOJO(fantasyClubLineUpPlayer);
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Fantasy Club Line Up Player at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Fantasy Club Line Up Player at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

}
