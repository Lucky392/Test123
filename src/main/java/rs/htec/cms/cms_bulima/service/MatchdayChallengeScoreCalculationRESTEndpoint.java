/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeScoreCalculation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.MatchdayChallengeScoreCalculationPOJO;

/**
 *
 * @author marko
 */
@Path("matchdayChallengeScoreCalculations")
public class MatchdayChallengeScoreCalculationRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * Returns list of MatchdayChallengeScoreCalculation for defined
     * parameters.
     * That list can be: {<br/>
     * "data": [<br/>
     * {<br/>
     * "calculationSql": "SUM(match_shotsTotal)",<br/> "createDate": 1437421634000,<br/>
     * "id": 1 <br/>},<br/> {<br/> "calculationSql": "SUM(match_foulsSuffered)",<br/> "createDate":
     * 1437421641000, <br/>"id": 2 <br/>}<br/>
     * ],<br/>
     * "count": 2<br/>
     * }<br/>
     *
     * @param token
     * @param request
     * @param page
     * @param limit
     * @param search - something in SQL statement
     * @param matchChallengeID
     * @return List of FantasyManagerMatchdayChallengeLineUpPlayer for search at
     * defined page and limit and their total count
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScoreCalculation(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit,
            @QueryParam("search") String search,
            @QueryParam("matchdayChallengeID") long matchChallengeID) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT b FROM MatchdayChallengeScoreCalculation b ");
        if (search != null) {
            search = "%" + search + "%";
            query.append("WHERE b.calculationSql LIKE '")
                    .append(search)
                    .append("'");
        }
        if (matchChallengeID != 0) {
            query.append(search != null ? " AND" : "WHERE")
                    .append(" b.idMatchdayChallenge.id = ")
                    .append(matchChallengeID);
        }
        List<MatchdayChallengeScoreCalculation> scoreCalculation = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (scoreCalculation == null || scoreCalculation.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no MatchdayChallengeScoreCalculation for this search!"), em);
            throw new DataNotFoundException("There is no MatchdayChallengeScoreCalculation for this search!");
        }
        String countQuery = query.toString().replaceFirst("b", "count(b)");
        List<MatchdayChallengeScoreCalculationPOJO> pojos = MatchdayChallengeScoreCalculationPOJO.toNewsPOJOList(scoreCalculation);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(pojos);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * API for this call is: /rest/matchdayChallengeScoreCalculations/{id}
     * @param token
     * @param request
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScoreCalculationById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        MatchdayChallengeScoreCalculationPOJO pojo = null;
        try {
            MatchdayChallengeScoreCalculation calculation = (MatchdayChallengeScoreCalculation) em.createNamedQuery("MatchdayChallengeScoreCalculation.findById").setParameter("id", id).getSingleResult();
            pojo = new MatchdayChallengeScoreCalculationPOJO(calculation);
            helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
            return Response.ok().entity(pojo).build();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("MatchdayChallengeScoreCalculation at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("MatchdayChallengeScoreCalculation at index " + id + " does not exist..");
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response insertScoreCalculation(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            MatchdayChallengeScoreCalculation scoreCalculation) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), scoreCalculation);
//        scoreCalculation.setIdMatchdayChallenge(em.find(MatchdayChallenge.class, scoreCalculation.));
        long idMatchdayChallenge = scoreCalculation.getIdMatchdayChallenge().getId();
        long count = (long) em.createQuery("SELECT count(m) FROM MatchdayChallengeScoreCalculation m  WHERE m.idMatchdayChallenge.id=" + idMatchdayChallenge).getSingleResult();
        if (count > 0) {
            helper.setResponseToHistory(history, new InputValidationException("Matchday challenge score calculation for matchday challange with id " + idMatchdayChallenge + " already exists"), em);
            throw new InputValidationException("Matchday challenge score calculation for matchday challange with id " + idMatchdayChallenge + " already exists");
        }
        if (validator.checkLenght(scoreCalculation.getCalculationSql(), 255, true)) {
            scoreCalculation.setCreateDate(new Date());
            helper.persistObject(em, scoreCalculation);
        }
        helper.setResponseToHistory(history, Response.status(Response.Status.CREATED).build(), em);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response updateMatchdayChallenge(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, 
            MatchdayChallengeScoreCalculation scoreCalculation) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), scoreCalculation);
        MatchdayChallengeScoreCalculation x = em.find(MatchdayChallengeScoreCalculation.class, scoreCalculation.getId());
        if (x != null) {
            if (validator.checkLenght(scoreCalculation.getCalculationSql(), 255, true)) {
                scoreCalculation.setCreateDate(x.getCreateDate());
                helper.mergeObject(em, scoreCalculation);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Matchday Challenge Score Calculation at index " + scoreCalculation.getId() + " does not exits"), em);
            throw new DataNotFoundException("Matchday Challenge Score Calculation at index " + scoreCalculation.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

}
