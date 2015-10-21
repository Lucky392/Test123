/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUp;
import rs.htec.cms.cms_bulima.domain.MatchdayChallenge;
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
@Path("/matchday_challenge")
public class MatchdayChallengeRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public MatchdayChallengeRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
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
        go.setData(matchdayChallenges);
        return Response.ok().entity(go).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token,
            FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager() != null
                && fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge() != null
                && validator.checkLenght(fantasyManagerMatchdayChallengeLineUp.getFormation(), 255, false)) {
            fantasyManagerMatchdayChallengeLineUp.setCreateDate(new Date());
            helper.persistObject(em, fantasyManagerMatchdayChallengeLineUp);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

}
