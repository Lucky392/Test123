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
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.LineUpDifference;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.FantasyClubLineUpPOJO;
import rs.htec.cms.cms_bulima.pojo.FantasyClubLineUpPlayerPOJO;
import rs.htec.cms.cms_bulima.pojo.LineUpDifferencePOJO;

/**
 *
 * @author stefan
 */
@Path("/lineups")
public class FantasyClubLineUpRESTEndpoint {

    // to-do:
    // update when lineuphistory is available  
    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for method: .../rest/lineups/{idFantasyClub}/{idMatchday} This method
     * return list of Fantasy Club lineup players for defined club and matchday
     * in JSON.
     *
     * Example for JSON response:<br/>
     * [<br/>
     * {<br/>
     * "createDate": 1437439301000,<br/>
     * "idLeaguePlayer": 6337,<br/>
     * "idPlayerSlot": 1,<br/>
     * "isCaptain": 0,<br/>
     * "idLineUp": 1,<br/>
     * "urlToLineUp":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/lineups/1",<br/>
     * "urlToPlayerSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlots/1",<br/>
     * "leaguePlayerName": "Fabian Giefer",<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeaguePlayers/6337",<br/>
     * "id": 7<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1437439301000,<br/>
     * "idLeaguePlayer": 6374,<br/>
     * "idPlayerSlot": 13,<br/>
     * "isCaptain": 0,<br/>
     * "idLineUp": 1,<br/>
     * "urlToLineUp":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/lineups/1",<br/>
     * "urlToPlayerSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlots/13",<br/>
     * "leaguePlayerName": "Todor Nedelev",<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeaguePlayers/6374",<br/>
     * "id": 12<br/>
     * }<br/>
     * ]<br/>
     *
     *
     * @param token header parameter for checking permission
     * @param request
     * @param idFantasyClub id for fantasy club
     * @param idMatchday id for matchday
     * @return 200 OK with FantasyClubLineUpPlayer list in JSON format
     */
    @GET
    @Path("/{idFantasyClub}/{idMatchday}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLineUp(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("idFantasyClub") long idFantasyClub,
            @PathParam("idMatchday") long idMatchday) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyClubLineUp lineUp = LineUpDifference.getLineUp(em, idFantasyClub, idMatchday);
        List<FantasyClubLineUpPlayer> lineUpPlayer = LineUpDifference.getLineUpList(em, lineUp.getId());

        List<FantasyClubLineUpPlayerPOJO> lineUpPlayerPOJO = FantasyClubLineUpPlayerPOJO.toFantasyClubLineUpPlayerPOJOList(lineUpPlayer);
        helper.setResponseToHistory(history, Response.ok().entity(lineUpPlayerPOJO).build(), em);
        return Response.ok().entity(lineUpPlayerPOJO).build();
    }

//    /**
//     * API for method: .../rest/lineup/points/{idFantasyClub}/{idMatchday} This
//     * method return bulima points of lineup for defined club and matchday.
//     *
//     * Example for JSON response: 20
//     *
//     * @param token
//     * @param idFantasyClub
//     * @param idMatchday
//     * @return long value for points
//     */
//    @GET
//    @Path("points/{idFantasyClub}/{idMatchday}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getPoints(@HeaderParam("authorization") String token, @PathParam("idFantasyClub") long idFantasyClub,
//            @PathParam("idMatchday") long idMatchday) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
//        long points = LineUpDifference.getBlmPoints(em, idFantasyClub, idMatchday);
//
//        return Response.ok().entity(points).build();
//    }
//
//    @GET
//    @Path("formation/{idFantasyClub}/{idMatchday}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getFormation(@HeaderParam("authorization") String token, @PathParam("idFantasyClub") long idFantasyClub,
//            @PathParam("idMatchday") long idMatchday) {
//        EntityManager em = helper.getEntityManager();
////        EntityManager em = EMF.createEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
//        LineUpDifference lineUp = new LineUpDifference();
//        String f1 = lineUp.getFormation(idFantasyClub, idMatchday);
//
//        return Response.ok().entity(f1).build();
//    }
    /**
     * API for method:
     * .../rest/lineups/difference?idFantasyClub1=value&idFantasyClub2=value&idMatchday1=value&idMatchday2=value
     * This method return difference for two lineups [defined club and match
     * day].
     *<br/>
     * Example for JSON response (for API
     * .../rest/lineup/difference?idFantasyClub1=27217&idFantasyClub2=27217&idMatchday1=1&idMatchday2=2):
     * {<br/>
     * "playerDifference": [<br/>
     * {<br/>
     * "leaguePlayerName": "Manuel Riemann",<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeaguePlayers/1575121",<br/>
     * "urlToLineUp":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/lineups/1909",<br/>
     * "urlToPlayerSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlots/1",<br/>
     * "createDate": 1438393876000,<br/>
     * "idLeaguePlayer": 1575121,<br/>
     * "idPlayerSlot": 1,<br/>
     * "isCaptain": 0,<br/>
     * "idLineUp": 1909,<br/>
     * "id": 14356<br/>
     * },<br/>
     * {<br/>
     * "leaguePlayerName": "Florian Ruck",<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeaguePlayers/1575291",<br/>
     * "urlToLineUp":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/lineups/1909",<br/>
     * "urlToPlayerSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlots/5",<br/>
     * "createDate": 1438393876000,<br/>
     * "idLeaguePlayer": 1575291,<br/>
     * "idPlayerSlot": 5,<br/>
     * "isCaptain": 0,<br/>
     * "idLineUp": 1909,<br/>
     * "id": 14349<br/>
     * },<br/>
     * {<br/>
     * "leaguePlayerName": "Marcel Sabitzer",<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeaguePlayers/1575281",<br/>
     * "urlToLineUp":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/lineups/1909",<br/>
     * "urlToPlayerSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlots/9",<br/>
     * "createDate": 1438393876000,<br/>
     * "idLeaguePlayer": 1575281,<br/>
     * "idPlayerSlot": 9,<br/>
     * "isCaptain": 1,<br/>
     * "idLineUp": 1909,<br/>
     * "id": 14353<br/>
     * }<br/>
     * ],<br/>
     * "formationDifference": "Formations are different, frst formation: 3-4-3,
     * second: 4-3-3",<br/>
     * "bulimaPointDifference": -6<br/>
     * }<br/>
     *
     *
     * @param token header parameter for checking permission
     * @param request
     * @param idFantasyClub1 id for first fantasy club
     * @param idFantasyClub2 id for second fantasy club
     * @param idMatchday1 id for first match day
     * @param idMatchday2 id for second match day
     * @return formation in String
     */
    @GET
    @Path("/difference")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDifference(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @QueryParam("idFantasyClub1") long idFantasyClub1,
            @QueryParam("idFantasyClub2") long idFantasyClub2, @QueryParam("idMatchday1") long idMatchday1, @QueryParam("idMatchday2") long idMatchday2) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);

        FantasyClubLineUp lineUp = LineUpDifference.getLineUp(em, idFantasyClub1, idMatchday1);
        List<FantasyClubLineUpPlayer> lineUpPlayers1 = LineUpDifference.getLineUpList(em, lineUp.getId());
        FantasyClubLineUp lineUp2 = LineUpDifference.getLineUp(em, idFantasyClub2, idMatchday2);
        List<FantasyClubLineUpPlayer> lineUpPlayers2 = LineUpDifference.getLineUpList(em, lineUp2.getId());

        String fDiff = LineUpDifference.returnDifferenceForFormation(lineUp.getIdFormation().getName(), lineUp2.getIdFormation().getName());

        List<FantasyClubLineUpPlayerPOJO> lineUpDifference = LineUpDifference.returnDifferencePOJO(lineUpPlayers1, lineUpPlayers2);
        Long points2 = LineUpDifference.getBlmPoints(em, idFantasyClub2, idMatchday2, lineUp2.getCreateDate());
        Long points1 = LineUpDifference.getBlmPoints(em, idFantasyClub1, idMatchday1, lineUp.getCreateDate());
        Long pointsDifference = (points1 != null && points2 != null) ? (points2 - points1) : null;

        LineUpDifferencePOJO pojo = new LineUpDifferencePOJO(lineUpDifference, fDiff, pointsDifference);
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

    /**
     * API for method: .../rest/lineups/{id} This method return single element of
     * Fantasy Club lineup with defined id in JSON.
     *
     * Example for JSON response: <br/>
     * {<br/>
     * "createDate": 1437439301000,<br/>
     * "idMatchday": 70,<br/>
     * "credit": 19400000,<br/>
     * "idFantasyLeague": 820,<br/>
     * "idFantasyClub": 4623,<br/>
     * "marketValue": 2735000,<br/>
     * "idFormation": 1,<br/>
     * "formationName": "4-4-2",<br/>
     * "urlToFantasyClub":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyClubs/4623",<br/>
     * "fantasyLeagueName": "Pep Ära",<br/>
     * "fantasyClubName": "Klaro", "urlToFantasyLeague":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeagues/820",<br/>
     * "urlToFormation":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/formations/1",<br/>
     * "urlToMatchday":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchdays/70",<br/>
     * "id": 35<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id of FantasyClubLineUp
     * @return 200 OK with FantasyClubLineUp in JSON format
     * @throws DataNotFoundException if FantasyClubLineUp with id doesn't exist
     * in DB
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLineUpId(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyClubLineUpPOJO pojo = null;
        try {
            FantasyClubLineUp fantasyClubLineUp = (FantasyClubLineUp) em.createNamedQuery("FantasyClubLineUp.findById").setParameter("id", id).getSingleResult();
            pojo = new FantasyClubLineUpPOJO(fantasyClubLineUp);
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Fantasy Club Line Up at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Fantasy Club Line Up at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }
}
