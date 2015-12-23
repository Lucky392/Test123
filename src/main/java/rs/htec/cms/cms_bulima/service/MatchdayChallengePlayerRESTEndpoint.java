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
import rs.htec.cms.cms_bulima.domain.MatchdayChallenge;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengePlayer;
import rs.htec.cms.cms_bulima.domain.Player;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.MatchdayChallengePlayerPOST;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.MatchdayChallengePlayerPOJO;

/**
 *
 * @author lazar
 */
@Path("/MatchdayChallengePlayers")
public class MatchdayChallengePlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for this call is :
     * /rest/MatchdayChallengePlayers/matchdayChallenge/{matchdayChallengeID}
     * Returns list of MatchdayChallengePlayers for defined parameters. That
     * list can be: {<br/>
     * "data": [<br/>
     * {<br/>
     * "idPlayer": {<br/> "fullname": "Daniel Halfar",<br/> "marketValue": 0,
     * <br/>"updateAt": 1439784030000,<br/> "isCaptain": 0,
     * <br/>"totalBLMPoints": 5,<br/>
     * "marketValueUpdateAt": 1437438630000,<br/> "blmPointsDiff": 1,<br/>
     * "idSport1Player": "1874",<br/> "firstName": "Daniel", <br/>"lastName":
     * "Halfar",<br/>
     * "trikotName": "Daniel Halfar",<br/> "sizeCm": 173,<br/> "weightKg":
     * 65,<br/>
     * "dateOfBirth": 568508400000,<br/> "photoUrl": null,<br/>
     * "dateJoinedTeam": 1435701600000,<br/> "shirtNumber": "28",<br/>
     * "matchesTopLeage": 0,<br/>
     * "scoreCountTopLeague": 0,<br/> "gradeAutoSeason": 0,<br/>
     * "gradeAutoSeasonLeagueAvg": 0,<br/> "isHurt": 0,<br/> "hasYellowCard":
     * 0,<br/>
     * "hasYellowRedCard": 0,<br/> "hasRedCard": 0,<br/> "photoUrl2":
     * "http://assets.bundesligamanager.htec.co.rs/images/bulima-player-card/photos/playerpic_Daniel_Halfar.png",<br/>
     * "blockType": null,<br/> "blockMatchdayAmount": 0,
     * <br/>"idPlayerPosition": <br/>{<br/>
     * "createDate": 1388530800000,<br/> "name": "Midfield",<br/> "id": 3<br/>
     * },<br/> "idNation": <br/>{<br/>
     * "createDate": 1388530800000,<br/> "name": "Deutschland", <br/>"id": 1
     * <br/>},<br/>
     * "idSeasonCurrent": <br/>{<br/> "idLeague": <br/>{<br/> "idSport1League":
     * "3", <br/>"sport": "soccer",<br/> "idCompetition": <br/>{<br/> "sport":
     * "Fußball",<br/> "gender": "male",<br/>
     * "createDate": 1406914086000,<br/> "name": "2. Bundesliga", <br/>"id": 2,
     * <br/>"type": "championship" <br/>}, <br/>"createDate":
     * 1406914139000,<br/> "id": 2 <br/>},<br/>
     * "idSport1Season": "18337",<br/> "idFirstMatchday": null,<br/>
     * "createDate": 1437383427000, <br/>"name": "2015/2016", <br/>"id": 4<br/>
     * }, <br/>"idBlockStartMatchday": null,<br/> "idClub":<br/> {
     * <br/>"idSport1Team": "6",<br/> "mediumName": "1. FC Kaiserslautern",<br/>
     * "logoUrl":
     * "http://assets.bundesligamanager.htec.co.rs/images/favoriteclublogos/1.-FC-Kaiserslautern.png",<br/>
     * "idLeague": <br/>{<br/> "idSport1League": "3", <br/>"sport":
     * "soccer",<br/> "idCompetition":<br/>
     * {<br/> "sport": "Fußball", <br/>"gender": "male",<br/> "createDate":
     * 1406914086000,<br/>
     * "name": "2. Bundesliga",<br/> "id": 2,<br/> "type": "championship"<br/>
     * },<br/> "createDate": 1406914139000,<br/> "id": 2 <br/>},
     * <br/>"shortName": "FCK",<br/> "logo": null, <br/>"createDate":
     * 1388530800000,<br/> "id": 3 <br/>},<br/> "photo": null,<br/>
     * "createDate": 1407424292000,<br/>
     * "id": 3690 <br/>},<br/> "id": 1 <br/>}<br/>
     * ],<br/>
     * "count": 1<br/>
     * }<br/>
     *
     * @param token
     * @param request
     * @param id
     * @param page
     * @param limit
     * @param orderingColumn
     * @param filter
     * @param club
     * @param position
     * @param nation
     * @param isCaptain
     * @param minSizeCm
     * @param minWeightKg
     * @param maxSizeCm
     * @param maxWeightKg
     * @param hasYellow
     * @param hasYellowRed
     * @param hasRed
     * @param isHurt
     * @return List of MatchdayChallengePlayers for search at defined page and
     * limit and their total count
     */
    @GET
    @Path("/matchdayChallenge/{matchdayChallengeId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMatchdayChallengePlayers(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            @PathParam("matchdayChallengeId") Long id,
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit,
            @QueryParam("column") String orderingColumn,
            @QueryParam("filter") String filter,
            @QueryParam("club") String club,
            @QueryParam("position") String position,
            @QueryParam("nation") String nation,
            @QueryParam("isCaptain") Boolean isCaptain,
            @QueryParam("minSizeCm") Integer minSizeCm,
            @QueryParam("minWeightKg") Integer minWeightKg,
            @QueryParam("maxSizeCm") Integer maxSizeCm,
            @QueryParam("maxWeightKg") Integer maxWeightKg,
            @QueryParam("hasYellow") Boolean hasYellow,
            @QueryParam("hasYellowRed") Boolean hasYellowRed,
            @QueryParam("hasRed") Boolean hasRed,
            @QueryParam("isHurt") Boolean isHurt) {

        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<Player> players;
        StringBuilder query = new StringBuilder("SELECT x FROM MatchdayChallengePlayer x WHERE x.idMatchdayChallenge = " + id);
        if (filter != null) {
            query.append(" AND (x.idPlayer.firstName LIKE '%")
                    .append(filter)
                    .append("%' OR x.idPlayer.lastName LIKE '%")
                    .append(filter)
                    .append("%'")
                    .append(" OR x.idPlayer.fullname LIKE '%")
                    .append(filter)
                    .append("%')");
        }
        if (club != null) {
            query.append(" AND x.idPlayer.club LIKE '%")
                    .append(club)
                    .append("%'");
        }
        if (position != null) {
            query.append(" AND x.idPlayer.position LIKE '%")
                    .append(position)
                    .append("%'");
        }
        if (nation != null) {
            query.append(" AND x.idPlayer.nation LIKE '%")
                    .append(nation)
                    .append("%'");
        }
        if (isCaptain != null) {
            query.append(" AND x.idPlayer.isCaptain = ").append(isCaptain ? 1 : 0);
        }
        if (hasYellow != null) {
            query.append(" AND x.idPlayer.hasYellowCard = ").append(hasYellow ? 1 : 0);
        }
        if (hasYellowRed != null) {
            query.append(" AND x.idPlayer.hasYellowRedCard = ").append(hasYellowRed ? 1 : 0);
        }
        if (hasRed != null) {
            query.append(" AND x.idPlayer.hasRedCard = ").append(hasRed ? 1 : 0);
        }
        if (isHurt != null) {
            query.append(" AND x.idPlayer.isHurt = ").append(isHurt ? 1 : 0);
        }
        if (maxSizeCm != null) {
            query.append(" AND x.idPlayer.sizeCm <= ").append(maxSizeCm);
        }
        if (minSizeCm != null) {
            query.append(" AND x.idPlayer.sizeCm >= ").append(minSizeCm);
        }
        if (maxWeightKg != null) {
            query.append(" AND x.idPlayer.weightKg <= ").append(maxWeightKg);
        }
        if (minWeightKg != null) {
            query.append(" AND x.idPlayer.weightKg >= ").append(minWeightKg);
        }
        players = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (players == null || players.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no MatchdayChallengePlayers for this search!"), em);
            throw new DataNotFoundException("There is no MatchdayChallengePlayers for this search!");
        }
        String countQuery = query.toString().replaceFirst("x", "count(x)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(players);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * API for this call is:
     * /rest/MatchdayChallengePlayers/matchdayChallengePlayerID/{matchdayChallengePlayerID}
     *
     * @param token
     * @param request
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/matchdayChallengePlayerID/{matchdayChallengePlayerID}")
    public Response getById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("matchdayChallengePlayerID") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        MatchdayChallengePlayer player = em.find(MatchdayChallengePlayer.class, id);
        if (player != null) {
            helper.setResponseToHistory(history, Response.ok().entity(new MatchdayChallengePlayerPOJO(player)).build(), em);
            return Response.ok().entity(new MatchdayChallengePlayerPOJO(player)).build();
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Player with this id doesn't exist!"), em);
            throw new DataNotFoundException("Player with this id doesn't exist!");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertMatchdayChallengePlayer(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            MatchdayChallengePlayer matchdayChallengePlayer) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), matchdayChallengePlayer);
        if (matchdayChallengePlayer.getIdMatchdayChallenge() != null
                && matchdayChallengePlayer.getIdPlayer() != null) {
            helper.persistObject(em, matchdayChallengePlayer);
            helper.setResponseToHistory(history, Response.status(Response.Status.CREATED).build(), em);
            return Response.status(Response.Status.CREATED).build();
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for this call is /rest/MatchdayChallengePlayers/insertPlayers. JSON
     * that you need to send: <br/> { <br/>"matchdayID" : 1,<br/>
     * "listOfPlayersID" : [<br/>
     * 3715,<br/> 3673 <br/>] <br/>}
     * 
     *
     * @param token
     * @param request
     * @param players
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertPlayers")
    public Response insertPlayers(@HeaderParam("authorization") String token, @Context HttpServletRequest request, MatchdayChallengePlayerPOST players) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), players);
        for (int i = 0; i < players.getListOfPlayersID().size(); i++) {
            MatchdayChallengePlayer player = new MatchdayChallengePlayer();
            player.setIdMatchdayChallenge(new MatchdayChallenge(players.getMatchdayID()));
            player.setIdPlayer(new Player(players.getListOfPlayersID().get(i)));
            helper.persistObject(em, player);
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMatchdayChallengePlayer(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            MatchdayChallengePlayer matchdayChallengePlayer) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), matchdayChallengePlayer);
        MatchdayChallenge x = em.find(MatchdayChallenge.class, matchdayChallengePlayer.getId());
        if (x != null) {
            if (matchdayChallengePlayer.getIdMatchdayChallenge() != null
                    && matchdayChallengePlayer.getIdPlayer() != null) {
                helper.mergeObject(em, matchdayChallengePlayer);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + matchdayChallengePlayer.getId() + " does not exits"), em);
            throw new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + matchdayChallengePlayer.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

    /**
     * API for method: .../rest/matchdayChallengePlayer/{id} This method find
     * matchdayChallengePlayer with defined id. Id is retrieved from URL. If
     * matchdayChallengePlayer with that index does not exist method throws
     * exception. Otherwise method remove that matchdayChallengePlayer.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of matchdayChallengePlayer that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMatchdayChallengePlayer(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.DELETE, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        MatchdayChallengePlayer x = em.find(MatchdayChallengePlayer.class, id);
        helper.removeObject(em, x, id);
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

}
