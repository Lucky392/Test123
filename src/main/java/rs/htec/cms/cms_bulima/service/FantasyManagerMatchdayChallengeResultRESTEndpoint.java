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
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeResult;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.FantasyManagerMatchdayChallengeResultPOJO;

/**
 *
 * @author lazar
 */
@Path("challenge_results")
public class FantasyManagerMatchdayChallengeResultRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * Returns list of FantasyManagerMatchdayChallengeResult for defined
     * parameters. 
     * That list can be: {<br/>
     * "data": [<br/>
     * {<br/>
     * "score": 0,<br/> "isSucceed": 0,<br/> "updateAt": 1438073140000,<br/>
     * "idMatchdayChallengeLineUp": 1,<br/> "createDate": 1438073140000, <br/>"id": 1<br/> },<br/> {<br/>
     * "score": 0,<br/> "isSucceed": 0, <br/>"updateAt": 1438073140000,<br/>
     * "idMatchdayChallengeLineUp": 6,<br/> "createDate": 1438073140000,<br/> "id": 2<br/>
     * }<br/>
     * ],<br/>
     * "count": 2<br/>
     * }<br/>
     *
     * @param token
     * @param page
     * @param limit
     * @param orderingColumn
     * @param minScore
     * @param maxScore
     * @param mcLineup
     * @return List of FantasyManagerMatchdayChallengeResult for search at
     * defined page and limit and their total count
     */
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFantasyManagerMatchdayChallengeResult(@HeaderParam("authorization") String token,
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit,
            @QueryParam("column") String orderingColumn,
            @QueryParam("minScore") String minScore,
            @QueryParam("maxScore") String maxScore,
            @QueryParam("mc_lineup") String mcLineup) {

        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyManagerMatchdayChallengeResult> fantasyManagerMatchdayChallengeResult;
        StringBuilder query = new StringBuilder("SELECT x FROM FantasyManagerMatchdayChallengeResult x WHERE x.createDate LIKE '%'");
        if (minScore != null) {
            query.append(" AND x.score >= ")
                    .append(minScore);
        }
        if (maxScore != null) {
            query.append(" AND x.score <= ")
                    .append(maxScore);
        }
        if (mcLineup != null) {
            query.append(" AND x.idMatchdayChallengeLineUp = ")
                    .append(mcLineup);
        }
        fantasyManagerMatchdayChallengeResult = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (fantasyManagerMatchdayChallengeResult == null || fantasyManagerMatchdayChallengeResult.isEmpty()) {
            throw new DataNotFoundException("There is no FantasyManagerMatchdayChallengeResult for this search!");
        }
        String countQuery = query.toString().replaceFirst("x", "count(x)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        List<FantasyManagerMatchdayChallengeResultPOJO> pojos = new ArrayList<>();
        for (FantasyManagerMatchdayChallengeResult x : fantasyManagerMatchdayChallengeResult) {
            pojos.add(new FantasyManagerMatchdayChallengeResultPOJO(x));
        }
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getResult(@HeaderParam("authorization") String token, @PathParam("id") long id){
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyManagerMatchdayChallengeResult result = em.find(FantasyManagerMatchdayChallengeResult.class, id);
        if (result == null){
            throw new DataNotFoundException("Result at index " + id + " does not exist..");
        }
        return Response.ok().entity(new FantasyManagerMatchdayChallengeResultPOJO(result)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertFantasyManagerMatchdayChallengeResult(@HeaderParam("authorization") String token,
            FantasyManagerMatchdayChallengeResult fantasyManagerMatchdayChallengeResult) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (fantasyManagerMatchdayChallengeResult.getIdMatchdayChallengeLineUp() != null) {
            fantasyManagerMatchdayChallengeResult.setCreateDate(new Date());
            helper.persistObject(em, fantasyManagerMatchdayChallengeResult);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFantasyManagerMatchdayChallengeResult(@HeaderParam("authorization") String token,
            FantasyManagerMatchdayChallengeResult fantasyManagerMatchdayChallengeResult) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        FantasyManagerMatchdayChallengeResult x = em.find(FantasyManagerMatchdayChallengeResult.class, fantasyManagerMatchdayChallengeResult.getId());
        if (x != null) {
            if (fantasyManagerMatchdayChallengeResult.getIdMatchdayChallengeLineUp() != null) {
                helper.mergeObject(em, fantasyManagerMatchdayChallengeResult);
                return Response.status(Response.Status.CREATED).build();
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Fantasy Manager Matchday Challenge Result at index " + fantasyManagerMatchdayChallengeResult.getId() + " does not exits");
        }
    }

    /**
     * API for method: .../rest/challenge_results/{id} This method find
     * FantasyManagerMatchdayChallengeResult with defined id. Id is retrieved
     * from URL. If FantasyManagerMatchdayChallengeResult with that index does
     * not exist method throws exception. Otherwise method remove that
     * FantasyManagerMatchdayChallengeResult.
     *
     * @param token is a header parameter for checking permission
     * @param id of matchdayChallengePlayer that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteFantasyManagerMatchdayChallengeResult(@HeaderParam("authorization") String token,
            @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.DELETE, token);
        FantasyManagerMatchdayChallengeResult x = em.find(FantasyManagerMatchdayChallengeResult.class, id);
        helper.removeObject(em, x, id);
        return Response.ok().build();
    }

}
