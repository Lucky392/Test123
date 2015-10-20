/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUp;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
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
            @QueryParam("matchdayChallengeType") int matchdayChallengeType, @QueryParam("isPublished") boolean isPublished) {

        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyManagerMatchdayChallengeLineUp> fantasyManagerMatchdayChallengeLineUps;
        StringBuilder query = new StringBuilder("SELECT m FROM MatchdayChallenge m ");
        
        if (filter != null) {
            query.append("WHERE m.matchdayChallengeTitle LIKE '%").append(filter).append("%' AND m.matchdayChallengeTitle LIKE '%").append(filter).append("%'");
        } else {
            query.append("WHERE m.matchdayChallengeTitle LIKE '%'");
        }
        
        if (createDate != null) {
            query.append(" AND f.createDate LIKE '%").append(createDate).append("%'");
        }
        
        if (matchdayChallenge != null) {
            query.append(" AND f.idMatchdayChallenge = ").append(matchdayChallenge);
        }
        
        if (managerId != null) {
            query.append(" AND f.idFantasyManager = ").append(managerId);
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        
        fantasyManagerMatchdayChallengeLineUps = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (fantasyManagerMatchdayChallengeLineUps == null || fantasyManagerMatchdayChallengeLineUps.isEmpty()) {
            throw new DataNotFoundException("There is no FantasyManagerMatchdayChallengeLineUp for this search!");
        }
        
        String countQuery = "Select COUNT(f) From FantasyManagerMatchdayChallengeLineUp f";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(returnPOJOs(fantasyManagerMatchdayChallengeLineUps));
        return Response.ok().entity(go).build();
    }
    
}
