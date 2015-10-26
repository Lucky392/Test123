/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.MatchdayChallenge;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeScoreCalculation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author marko
 */
@Path("matchdayChallengeScoreCalculation")
public class MatchdayChallengeScoreCalculationRESTEndpoint {
    
    RestHelperClass helper;
    Validator validator;

    public MatchdayChallengeScoreCalculationRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScoreCalculation(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
                @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search, @QueryParam("matchdayChallengeID") long matchChallengeID){
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT b FROM MatchdayChallengeScoreCalculation b ");
        if (search != null) {
            search = "%" + search + "%";
            query.append("WHERE b.calculationSql LIKE '")
                    .append(search)
                    .append("'");
        }
        if (matchChallengeID != 0){
            query.append(search != null ? " AND" : "WHERE")
                    .append(" b.idMatchdayChallenge.id = ")
                    .append(matchChallengeID);
        }
        List<MatchdayChallengeScoreCalculation> scoreCalculation = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (scoreCalculation == null || scoreCalculation.isEmpty()) {
            throw new DataNotFoundException("There is no batchjobs for this search!");
        }
        String countQuery = query.toString().replaceFirst("b", "count(b)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(scoreCalculation);
        return Response.ok().entity(go).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{matchdayChallengeID}")
    public Response insertScoreCalculation(@HeaderParam("authorization") String token, MatchdayChallengeScoreCalculation scoreCalculation, @PathParam("matchdayChallengeID") long matchdayChallengeID){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        scoreCalculation.setIdMatchdayChallenge(em.find(MatchdayChallenge.class, matchdayChallengeID));
        helper.persistObject(em, scoreCalculation);
        return Response.status(Response.Status.CREATED).build();
    }
}
