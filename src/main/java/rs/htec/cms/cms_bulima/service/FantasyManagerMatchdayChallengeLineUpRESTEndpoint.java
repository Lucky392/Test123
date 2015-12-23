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
@Path("/FantasyManagerMatchdayChallengeLineUps")
public class FantasyManagerMatchdayChallengeLineUpRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * Returns list of FantasyManagerMatchdayChallengeLineUp for defined
     * parameters. That list can be: 
     * { <br/>"data": <br/>[ <br/>{ <br/>"id_FANTASY_MANAGER":
     * "29811",<br/> "id_MATCHDAY_CHALLENGE": "1",<br/> "formation": "0-0-2-3",<br/>
     * "createDate": 1437421809000,<br/> "id": 1 <br/>}<br/> ],<br/>
     * "count": 2<br/>
     * }<br/>
     *
     * @param token
     * @param request
     * @param page
     * @param limit
     * @param orderingColumn
     * @param managerId
     * @param matchdayChallenge
     * @param formation
     * @param createDate
     * @return List of FantasyManagerMatchdayChallengeLineUpPlayer for search at
     * defined page and limit and their total count
     */
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("managerId") Long managerId,
            @QueryParam("matchdayChallenge") Long matchdayChallenge, @QueryParam("formation") String formation, @QueryParam("createDate") String createDate) {

        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
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
            helper.setResponseToHistory(history, new DataNotFoundException("There is no FantasyManagerMatchdayChallengeLineUp for this search!"), em);
            throw new DataNotFoundException("There is no FantasyManagerMatchdayChallengeLineUp for this search!");
        }

        String countQuery = query.toString().replaceFirst("f", "count(f)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(returnPOJOs(fantasyManagerMatchdayChallengeLineUps));
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getLineUpById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id){
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyManagerMatchdayChallengeLineUp lineup = em.find(FantasyManagerMatchdayChallengeLineUp.class, id);
        if (lineup == null){
            helper.setResponseToHistory(history, new DataNotFoundException("Line up at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Line up at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(new FantasyManagerMatchdayChallengeLineUpPOJO(lineup)).build(), em);
        return Response.ok().entity(new FantasyManagerMatchdayChallengeLineUpPOJO(lineup)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), fantasyManagerMatchdayChallengeLineUp);
        if (fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager() != null
                && fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge() != null
                && validator.checkLenght(fantasyManagerMatchdayChallengeLineUp.getFormation(), 255, false)) {
            fantasyManagerMatchdayChallengeLineUp.setCreateDate(new Date());
            helper.persistObject(em, fantasyManagerMatchdayChallengeLineUp);
            helper.setResponseToHistory(history, Response.status(Response.Status.CREATED).build(), em);
            return Response.status(Response.Status.CREATED).build();
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");

        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token,
            @Context HttpServletRequest request, FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), fantasyManagerMatchdayChallengeLineUp);
        FantasyManagerMatchdayChallengeLineUp f = em.find(FantasyManagerMatchdayChallengeLineUp.class, fantasyManagerMatchdayChallengeLineUp.getId());
        if (f != null) {
            if (fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager() != null
                    && fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge() != null
                    && validator.checkLenght(fantasyManagerMatchdayChallengeLineUp.getFormation(), 255, false)) {
                fantasyManagerMatchdayChallengeLineUp.setCreateDate(f.getCreateDate());
                helper.mergeObject(em, fantasyManagerMatchdayChallengeLineUp);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + fantasyManagerMatchdayChallengeLineUp.getId() + " does not exits"), em);
            throw new DataNotFoundException("Fantasy Manager Matchday Challenge Line Up at index " + fantasyManagerMatchdayChallengeLineUp.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }
    
    /**
     * API for method: .../rest/FantasyManagerMatchdayChallengeLineUps/{id} This method find
     * FantasyManagerMatchdayChallengeLineUp with defined id. Id is retrieved from URL. If
     * FantasyManagerMatchdayChallengeLineUp with that index does not exist method throws exception.
     * Otherwise method remove that FantasyManagerMatchdayChallengeLineUp.
     *
     * @param token is a header parameter for checking permission
     * @param id of FantasyManagerMatchdayChallengeLineUp that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteFantasyManagerMatchdayChallengeLineUp(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.DELETE, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyManagerMatchdayChallengeLineUp x = em.find(FantasyManagerMatchdayChallengeLineUp.class, id);
        helper.removeObject(em, x, id);
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

    private List<FantasyManagerMatchdayChallengeLineUpPOJO> returnPOJOs(List<FantasyManagerMatchdayChallengeLineUp> fantasyManagerMatchdayChallengeLineUps) {
        List<FantasyManagerMatchdayChallengeLineUpPOJO> fantasyManagerMatchdayChallengeLineUpPOJOs = new ArrayList<>();
        for (FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp : fantasyManagerMatchdayChallengeLineUps) {
            fantasyManagerMatchdayChallengeLineUpPOJOs.add(new FantasyManagerMatchdayChallengeLineUpPOJO(fantasyManagerMatchdayChallengeLineUp));
        }
        return fantasyManagerMatchdayChallengeLineUpPOJOs;
    }

}
