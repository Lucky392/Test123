/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUp;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.FantasyManagerMatchdayChallengeLineUpPOJO;

/**
 *
 * @author lazar
 */
@Path("/FantasyManagerMatchdayChallengeLineUp")
public class FantasyManagerMatchdayChallengeLineUpRESTEndpoint {
    
    RestHelperClass helper;
    Validator validator;

    public FantasyManagerMatchdayChallengeLineUpRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("managerId") Long managerId,
            @QueryParam("matchdayChallenge") Long matchdayChallenge, @QueryParam("formation") String formation, @QueryParam("createDate") String createDate) {

        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyManagerMatchdayChallengeLineUp> fantasyManagerMatchdayChallengeLineUps;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyManagerMatchdayChallengeLineUp f ");
        
        if (formation != null) {
            query.append("WHERE f.formation = '").append(formation).append("'");
        } else {
            query.append("WHERE f.formation LIKE '%'");
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
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token,
            FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        FantasyManagerMatchdayChallengeLineUp f = em.find(FantasyManagerMatchdayChallengeLineUp.class, fantasyManagerMatchdayChallengeLineUp.getId());
        if (f != null) {
            if (fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager() != null
                && fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge() != null
                && validator.checkLenght(fantasyManagerMatchdayChallengeLineUp.getFormation(), 255, false)) {
                fantasyManagerMatchdayChallengeLineUp.setCreateDate(f.getCreateDate());
                helper.mergeObject(em, fantasyManagerMatchdayChallengeLineUp);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + fantasyManagerMatchdayChallengeLineUp.getId() + " does not exits");
        }
        return Response.ok().build();
    }
    
    private List<FantasyManagerMatchdayChallengeLineUpPOJO> returnPOJOs(List<FantasyManagerMatchdayChallengeLineUp> fantasyManagerMatchdayChallengeLineUps){
        List<FantasyManagerMatchdayChallengeLineUpPOJO> fantasyManagerMatchdayChallengeLineUpPOJOs = new ArrayList<>();
        for (FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp : fantasyManagerMatchdayChallengeLineUps) {
            fantasyManagerMatchdayChallengeLineUpPOJOs.add(new FantasyManagerMatchdayChallengeLineUpPOJO(fantasyManagerMatchdayChallengeLineUp));
        }
        return fantasyManagerMatchdayChallengeLineUpPOJOs;
    }
    
}
