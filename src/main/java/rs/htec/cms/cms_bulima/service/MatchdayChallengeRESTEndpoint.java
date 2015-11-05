/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.MatchdayChallengePOJO;

/**
 *
 * @author lazar
 */
@Path("/matchday_challenge")
public class MatchdayChallengeRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * Returns MatchdayChallenges for given parameters. 
     *
     * {
     * "data":<br/>
     * [<br/> {<br/>
     * "cost": 0,<br/> "isPublished": 1,<br/> "idReward": 20,<br/> "formation":
     * "0-0-2-3",<br/> "matchdayChallengeTitle": "Ballerei!",<br/>
     * "matchdayChallengeDescription": "Wähle 5 Spieler aus, die zusammen
     * mindestens 20 Mal in Richtung Tor schießen.",<br/> "matchdayChallengeType":
     * "1", <br/>"logo": "", <br/>"idMatchday": 1,<br/> "subheadline": "Einsteiger-Challenge",<br/>
     * "squad": "Mittelfeld, Sturm",<br/> "createDate": 1437421634000, <br/>"id": 1,<br/>
     * "target": "20 Schüsse Richtung Tor"<br/> }<br/>
     * ],<br/> "count": 18<br/>
     * }
     *
     * @param token - authorization token
     * @param page - which page to display
     * @param limit - how much results to display on page
     * @param orderingColumn - by which column to order (add - before text for desc order)
     * @param filter - text to filter titles
     * @param matchdayChallengeType - 1, 2 or 3
     * @param isPublished - true/false
     * @return List of MatchdayChallenges and count
     * @throws DataNotFoundException if MatchdayChallenges doesn't exist
     * @throws InputValidationException if some data isn't good
     */
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMatchdayChallenges(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("filter") String filter,
            @QueryParam("matchdayChallengeType") int matchdayChallengeType, @QueryParam("isPublished") Boolean isPublished) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<MatchdayChallenge> matchdayChallenges;
        StringBuilder query = new StringBuilder("SELECT m FROM MatchdayChallenge m ");
        if (filter != null) {
            query.append("WHERE m.matchdayChallengeTitle LIKE '%").append(filter).append("%' AND m.matchdayChallengeTitle LIKE '%").append(filter).append("%'");
        } else {
            query.append("WHERE m.matchdayChallengeTitle LIKE '%'");
        }
        if (matchdayChallengeType != 0) {
            switch (matchdayChallengeType) {
                case 1:
                case 2:
                case 3:
                    query.append(" AND m.matchdayChallengeType = ").append(matchdayChallengeType);
                default:
                    throw new InputValidationException("matchdayChallengeType have to be 1, 2 or 3!");
            }
        }
        if (isPublished != null) {
            query.append(" AND m.isPublished = ").append(isPublished ? 1 : 0);
        }
        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        matchdayChallenges = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (matchdayChallenges == null || matchdayChallenges.isEmpty()) {
            throw new DataNotFoundException("There is no FantasyManagerMatchdayChallengeLineUp for this search!");
        }
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        List<MatchdayChallengePOJO> pojos = new ArrayList<>();
        for (MatchdayChallenge mc : matchdayChallenges) {
            pojos.add(new MatchdayChallengePOJO(mc));
        }
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }

    /**
     * Returns MatchdayChallenge for given id. 
     *
     * {
     * "data":<br/>
     * [<br/> {<br/>
     * "cost": 0,<br/> "isPublished": 1,<br/> "idReward": 20,<br/> "formation":
     * "0-0-2-3",<br/> "matchdayChallengeTitle": "Ballerei!",<br/>
     * "matchdayChallengeDescription": "Wähle 5 Spieler aus, die zusammen
     * mindestens 20 Mal in Richtung Tor schießen.",<br/> "matchdayChallengeType":
     * "1", <br/>"logo": "", <br/>"idMatchday": 1,<br/> "subheadline": "Einsteiger-Challenge",<br/>
     * "squad": "Mittelfeld, Sturm",<br/> "createDate": 1437421634000, <br/>"id": 1,<br/>
     * "target": "20 Schüsse Richtung Tor"<br/> }<br/>
     * ],<br/> "count": 18<br/>
     * }
     *
     * @param token - authorization token
     * @param id - matchdayChallenge id
     * @return List of MatchdayChallenges and count
     * @throws DataNotFoundException if MatchdayChallenges doesn't exist
     * @throws InputValidationException if some data isn't good
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMatchdayChallenge(@HeaderParam("authorization") String token,
            @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        try {
            MatchdayChallenge matchdayChallenge = (MatchdayChallenge) em.createNamedQuery("MatchdayChallenge.findById").setParameter("id", id).getSingleResult();
            return Response.ok().entity(matchdayChallenge).build();
        } catch (NoResultException nre) {
            throw new DataNotFoundException("There is no matchday challenge with id = " + id);
        }
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMatchdayChallenge(@HeaderParam("authorization") String token,
            MatchdayChallenge matchdayChallenge) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (validator.checkLenght(matchdayChallenge.getMatchdayChallengeTitle(), 255, false)
                && validator.checkLenght(matchdayChallenge.getMatchdayChallengeDescription(), 255, false)
                && validator.checkLenght(matchdayChallenge.getMatchdayChallengeType(), 255, false)
                && validator.checkLenght(matchdayChallenge.getLogo(), 255, false)
                && validator.checkLenght(matchdayChallenge.getFormation(), 255, true)
                && matchdayChallenge.getIdMatchday() != null
                && validator.checkLenght(matchdayChallenge.getTarget(), 255, true)
                && validator.checkLenght(matchdayChallenge.getSubheadline(), 255, true)
                && validator.checkLenght(matchdayChallenge.getSquad(), 255, true)) {
            matchdayChallenge.setCreateDate(new Date());
            helper.persistObject(em, matchdayChallenge);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMatchdayChallenge(@HeaderParam("authorization") String token,
            MatchdayChallenge matchdayChallenge) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        MatchdayChallenge x = em.find(MatchdayChallenge.class, matchdayChallenge.getId());
        if (x != null) {
            if (validator.checkLenght(matchdayChallenge.getMatchdayChallengeTitle(), 255, false)
                    && validator.checkLenght(matchdayChallenge.getMatchdayChallengeDescription(), 255, false)
                    && validator.checkLenght(matchdayChallenge.getMatchdayChallengeType(), 255, false)
                    && validator.checkLenght(matchdayChallenge.getLogo(), 255, false)
                    && validator.checkLenght(matchdayChallenge.getFormation(), 255, true)
                    && matchdayChallenge.getIdMatchday() != null
                    && validator.checkLenght(matchdayChallenge.getTarget(), 255, true)
                    && validator.checkLenght(matchdayChallenge.getSubheadline(), 255, true)
                    && validator.checkLenght(matchdayChallenge.getSquad(), 255, true)) {
                matchdayChallenge.setCreateDate(x.getCreateDate());
                helper.mergeObject(em, matchdayChallenge);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + matchdayChallenge.getId() + " does not exits");
        }
        return Response.ok().build();
    }

    /**
     * API for method: .../rest/matchday_challenge/{id} This method find
     * matchdayChallenge with defined id. Id is retrieved from URL. If
     * matchdayChallenge with that index does not exist method throws exception.
     * Otherwise method remove that matchdayChallenge.
     *
     * @param token is a header parameter for checking permission
     * @param id of matchdayChallenge that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMatchdayChallenge(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.DELETE, token);
        MatchdayChallenge x = em.find(MatchdayChallenge.class, id);
        helper.removeObject(em, x, id);
        return Response.ok().build();
    }

}
