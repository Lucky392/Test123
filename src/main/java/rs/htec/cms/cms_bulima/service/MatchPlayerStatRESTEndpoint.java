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
import rs.htec.cms.cms_bulima.domain.MatchPlayerStat;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchPlayerStatPOJO;

/**
 *
 * @author stefan
 */
@Path("/matchPlayerStats")
public class MatchPlayerStatRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns MatchPlayerStat for defined id.
     *<br/>
     * Example for response:<br/>
     *{<br/>
     * "createDate": 1439797302000,<br/>
     * "score": 0,<br/>
     * "idMatchPlayer": 523,<br/>
     * "rating": 4.72,<br/>
     * "assists": 0,<br/>
     * "scorePenalty": 0,<br/>
     * "scoreFoot": 0,<br/>
     * "scoreHeader": 0,<br/>
     * "scoreOwnGoal": 0,<br/>
     * "goalsAgainst": 5,<br/>
     * "cleanSheet": 0,<br/>
     * "shots": 0,<br/>
     * "shotsFoot": 0,<br/>
     * "shotsHeader": 0,<br/>
     * "shotsInsideBox": 0,<br/>
     * "shotsOutsideBox": 0,<br/>
     * "shotsPenalty": 0,<br/>
     * "shotsPenaltyShotMissed": 0,<br/>
     * "shotAssists": 0,<br/>
     * "penaltySaves": 0,<br/>
     * "playing": 1,<br/>
     * "playingLineup": 1,<br/>
     * "playingSubstituteIn": 0,<br/>
     * "playingSubstituteOut": 0,<br/>
     * "playingMinutes": 90,<br/>
     * "cardYellow": 0,<br/>
     * "cardYellowRed": 0,<br/>
     * "cardRed": 0,<br/>
     * "ballsTouched": 38,<br/>
     * "passesComplete": 4,<br/>
     * "passesCompletePercentage": 26.67,<br/>
     * "passesFailed": 11,<br/>
     * "passesFailedPercentage": 73.33,<br/>
     * "duelsWon": 4,<br/>
     * "duelsWonGround": 4,<br/>
     * "duelsWonHeader": 0,<br/>
     * "duelsWonPercentage": 44.44,<br/>
     * "duelsLost": 5,<br/>
     * "duelsLostGround": 4,<br/>
     * "duelsLostHeader": 1,<br/>
     * "duelsLostPercentage": 55.56,<br/>
     * "foulsCommitted": 0,<br/>
     * "foulsSuffered": 0,<br/>
     * "crosses": 1,<br/>
     * "cornerKicks": 0,<br/>
     * "freekicks": 0,<br/>
     * "offsides": 0,<br/>
     * "saves": 0,<br/>
     * "trackingDistance": 10636,<br/>
     * "trackingFastRuns": 34,<br/>
     * "trackingSprints": 19,<br/>
     * "trackingMaxSpeed": 33,<br/>
     * "positionId": null,<br/>
     * "uniformNumber": null,<br/>
     * "grade": 3.3,<br/>
     * "yCoordinate": null,<br/>
     * "urlToMatchPlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchPlayers/523",<br/>
     * "xCoordinate": null,<br/>
     * "id": 523<br/>
     * }<br/>
     * 
     * @param token - header parameter for checking permission
     * @param id - of MatchPlayerStat that should be returned
     * @return MatchPlayerStat
     * @throws DataNotFoundException if MatchPlayerStat for id does not exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayerStatById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        MatchPlayerStatPOJO pojo;
        try {
            MatchPlayerStat matchPlayerStat = (MatchPlayerStat) em.createNamedQuery("MatchPlayerStat.findById").setParameter("id", id).getSingleResult();
            pojo = new MatchPlayerStatPOJO(matchPlayerStat);
        } catch (NoResultException e) {
            throw new DataNotFoundException("MatchPlayerStat with id " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }

    /**
     * Returns list of MatchPlayerStat-s for specified search.
     * <br/>
     * Example for return:<br/>
     *
     * {<br/>
     * "count": 1,<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1439797302000,<br/>
     * "score": 0,<br/>
     * "idMatchPlayer": 523,<br/>
     * "rating": 4.72,<br/>
     * "assists": 0,<br/>
     * "scorePenalty": 0,<br/>
     * "scoreFoot": 0,<br/>
     * "scoreHeader": 0,<br/>
     * "scoreOwnGoal": 0,<br/>
     * "goalsAgainst": 5,<br/>
     * "cleanSheet": 0,<br/>
     * "shots": 0,<br/>
     * "shotsFoot": 0,<br/>
     * "shotsHeader": 0,<br/>
     * "shotsInsideBox": 0,<br/>
     * "shotsOutsideBox": 0,<br/>
     * "shotsPenalty": 0,<br/>
     * "shotsPenaltyShotMissed": 0,<br/>
     * "shotAssists": 0,<br/>
     * "penaltySaves": 0,<br/>
     * "playing": 1,<br/>
     * "playingLineup": 1,<br/>
     * "playingSubstituteIn": 0,<br/>
     * "playingSubstituteOut": 0,<br/>
     * "playingMinutes": 90,<br/>
     * "cardYellow": 0,<br/>
     * "cardYellowRed": 0,<br/>
     * "cardRed": 0,<br/>
     * "ballsTouched": 38,<br/>
     * "passesComplete": 4,<br/>
     * "passesCompletePercentage": 26.67,<br/>
     * "passesFailed": 11,<br/>
     * "passesFailedPercentage": 73.33,<br/>
     * "duelsWon": 4,<br/>
     * "duelsWonGround": 4,<br/>
     * "duelsWonHeader": 0,<br/>
     * "duelsWonPercentage": 44.44,<br/>
     * "duelsLost": 5,<br/>
     * "duelsLostGround": 4,<br/>
     * "duelsLostHeader": 1,<br/>
     * "duelsLostPercentage": 55.56,<br/>
     * "foulsCommitted": 0,<br/>
     * "foulsSuffered": 0,<br/>
     * "crosses": 1,<br/>
     * "cornerKicks": 0,<br/>
     * "freekicks": 0,<br/>
     * "offsides": 0,<br/>
     * "saves": 0,<br/>
     * "trackingDistance": 10636,<br/>
     * "trackingFastRuns": 34,<br/>
     * "trackingSprints": 19,<br/>
     * "trackingMaxSpeed": 33,<br/>
     * "positionId": null,<br/>
     * "uniformNumber": null,<br/>
     * "grade": 3.3,<br/>
     * "yCoordinate": null,<br/>
     * "urlToMatchPlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchPlayers/523",<br/>
     * "xCoordinate": null,<br/>
     * "id": 523<br/>
     * }<br/>
     * ]<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param page number of page for searched results
     * @param limit number of matchPlayerStats that are returned in body
     * @param orderBy column name (if there is - before colum name, results will be sorted in descending order)
     * @param minDate filters result form defined date
     * @param maxDate filters result to defined date
     * @param idMatchPlayer filters results base on defined id for MatchPlayer
     * @return list of MatchPlayerStat
     * @throws DataNotFoundException if MatchPlayerStat does not exist for
     * search
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayerStat(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate, @QueryParam("idMatchPlayer") String idMatchPlayer) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<MatchPlayerStat> matchPlayerStat;
        StringBuilder query = new StringBuilder("SELECT m FROM MatchPlayerStat m");

        String operator = " WHERE";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(" WHERE m.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND";
        }

        if (idMatchPlayer != null) {
            query.append(operator).append(" m.idMatchPlayer = '").append(idMatchPlayer).append("'");
        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        matchPlayerStat = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (matchPlayerStat == null || matchPlayerStat.isEmpty()) {
            throw new DataNotFoundException("There is no MatchPlayerStat for this search!");
        }

        List<MatchPlayerStatPOJO> pojos = MatchPlayerStatPOJO.toMatchPlayerStatPOJOList(matchPlayerStat);

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }
}
