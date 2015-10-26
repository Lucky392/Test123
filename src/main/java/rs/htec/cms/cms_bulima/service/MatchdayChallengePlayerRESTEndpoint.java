/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

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
import rs.htec.cms.cms_bulima.domain.MatchdayChallenge;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengePlayer;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.domain.Player;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author lazar
 */
@Path("/MatchdayChallengePlayer")
public class MatchdayChallengePlayerRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public MatchdayChallengePlayerRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    @GET
    @Path("/{matchdayChallengeId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMatchdayChallengePlayers(@HeaderParam("authorization") String token,
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
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
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
            throw new DataNotFoundException("There is no MatchdayChallengePlayers for this search!");
        }
        String countQuery = query.toString().replaceFirst("x", "count(x)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(players);
        return Response.ok().entity(go).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMatchdayChallenge(@HeaderParam("authorization") String token,
            MatchdayChallengePlayer matchdayChallengePlayer) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (matchdayChallengePlayer.getIdMatchdayChallenge() != null
                && matchdayChallengePlayer.getIdPlayer() != null) {
            helper.persistObject(em, matchdayChallengePlayer);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMatchdayChallenge(@HeaderParam("authorization") String token,
            MatchdayChallengePlayer matchdayChallengePlayer) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        MatchdayChallenge x = em.find(MatchdayChallenge.class, matchdayChallengePlayer.getId());
        if (x != null) {
            if (matchdayChallengePlayer.getIdMatchdayChallenge() != null
                && matchdayChallengePlayer.getIdPlayer() != null) {
                helper.mergeObject(em, matchdayChallengePlayer);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + matchdayChallengePlayer.getId() + " does not exits");
        }
        return Response.ok().build();
    }
    
    
    /**
     * API for method: .../rest/matchdayChallengePlayer/{id} This method find matchdayChallengePlayer with defined id.
     * Id is retrieved from URL. If matchdayChallengePlayer with that index does not exist method
     * throws exception. Otherwise method remove that matchdayChallengePlayer.
     *
     * @param token is a header parameter for checking permission
     * @param id of matchdayChallengePlayer that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteNews(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.DELETE, token);
        MatchdayChallengePlayer x = em.find(MatchdayChallengePlayer.class, id);
        helper.removeObject(em, x, id);
        return Response.ok().build();
    }

}
