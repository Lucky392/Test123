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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyManagerMatchdayChallengeLineUpPlayerPOJO;

/**
 *
 * @author lazar
 */
@Path("/challengeLineUpPlayers")
public class FantasyManagerMatchdayChallengeLineUpPlayerRESTEndpoint{ 
    
    @InjectParam
    RestHelperClass helper;
    
    /**
     * Returns FantasyManagerMatchdayChallengeLineUpPlayer for defined id.
     * 
     * {<br/>
     * "createDate": 1437558945000,<br/>
     * "idMatchdayChallengePlayer": 996,<br/>
     * "idMatchdayChallengeLineUp": 225,<br/>
     * "id": 6718,<br/>
     * "slot": 9<br/>
     * }<br/>
     * 
     * @param token
     * @param id
     * @return FantasyManagerMatchdayChallengeLineUpPlayer
     * @throws DataNotFoundException if FantasyManagerMatchdayChallengeLineUpPlayer doesn't exist for defined id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyManagerMatchdayChallengeLineUpPlayerById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyManagerMatchdayChallengeLineUpPlayerPOJO pojo;
        try {
            FantasyManagerMatchdayChallengeLineUpPlayer matchdayChallengeLineUpPlayer = (FantasyManagerMatchdayChallengeLineUpPlayer) em.createNamedQuery("FantasyManagerMatchdayChallengeLineUpPlayer.findById").setParameter("id", id).getSingleResult();
            pojo = new FantasyManagerMatchdayChallengeLineUpPlayerPOJO(matchdayChallengeLineUpPlayer);
        } catch (Exception e) {
            throw new DataNotFoundException("FantasyManagerMatchdayChallengeLineUp with id " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }
    
    /**
     * Returns list of FantasyManagerMatchdayChallengeLineUpPlayer for defined parameters.
     * That list can be:
     * -ordered by specified column
     * -filtered by idMatchdayChallengeLineUp and idMatchdayChallengePlayer
     * -filtered between two dates
     * Example for JSON response (/rest/challengeLineUpPlayer?player=1999&slot=2)
     *{<br/>
     *  "data": [<br/>
     *    {<br/>
     *      "createDate": 1437596274000,<br/>
     *      "idMatchdayChallengePlayer": 1999,<br/>
     *      "idMatchdayChallengeLineUp": 2969,<br/>
     *      "id": 101739,<br/>
     *      "slot": 2<br/>
     *    },<br/>
     *    {<br/>
     *      "createDate": 1437639067000,<br/>
     *      "idMatchdayChallengePlayer": 1999,<br/>
     *      "idMatchdayChallengeLineUp": 3827,<br/>
     *      "id": 132213,<br/>
     *      "slot": 2<br/>
     *    },<br/>
     *    {<br/>
     *      "createDate": 1437640535000,<br/>
     *      "idMatchdayChallengePlayer": 1999,<br/>
     *      "idMatchdayChallengeLineUp": 3894,<br/>
     *      "id": 134370,<br/>
     *      "slot": 2<br/>
     *    }<br/>
     *  ],<br/>
     *  "count": 2<br/>
     *}<br/>
     * 
     * @param token
     * @param page
     * @param limit
     * @param orderBy column
     * @param from some Date
     * @param to some Date
     * @param idMatchdayChallengePlayer
     * @param idMatchdayChallengeLineUp
     * @param slot
     * @return List of FantasyManagerMatchdayChallengeLineUpPlayer for search at defined page and limit and their total count
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyManagerMatchdayChallengeLineUpPlayer(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy, @QueryParam("form") long from, @QueryParam("to") long to,
            @QueryParam("idMatchdayChallengePlayer") String idMatchdayChallengePlayer, @QueryParam("idMatchdayChallengeLineUp") String idMatchdayChallengeLineUp, @QueryParam("slot") String slot) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<FantasyManagerMatchdayChallengeLineUpPlayer> challengeLineUpPlayerList;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyManagerMatchdayChallengeLineUpPlayer f ");

        if (from != 0 && to != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(from);
            Date d2 = new Date(to);
            query.append("WHERE f.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
        }

        if (idMatchdayChallengePlayer != null) {
            query.append(from != 0 ? " AND" : "WHERE").append(" f.idMatchdayChallengePlayer = '").append(idMatchdayChallengePlayer).append("'");
        }

        if (idMatchdayChallengeLineUp != null) {
            query.append(from != 0 || idMatchdayChallengePlayer != null ? " AND" : "WHERE").append(" f.idMatchdayChallengeLineUp = '").append(idMatchdayChallengeLineUp).append("'");
        }

        if (slot != null) {
            query.append(from != 0 || idMatchdayChallengePlayer != null || idMatchdayChallengeLineUp!=null ? " AND" : "WHERE").append(" f.slot = '").append(slot).append("'");
        }
        
        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        challengeLineUpPlayerList = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (challengeLineUpPlayerList == null || challengeLineUpPlayerList.isEmpty()) {
            throw new DataNotFoundException("There is FantasyManagerMatchdayChallengeLineUpPlayer for this search!");
        }

        List<FantasyManagerMatchdayChallengeLineUpPlayerPOJO> pojos = FantasyManagerMatchdayChallengeLineUpPlayerPOJO.toFantasyManagerMatchdayChallengeLineUpPlayerPOJOList(challengeLineUpPlayerList);

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("f", "count(f)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }
    
    /**
     * Updates FantasyManagerMatchdayChallengeLineUpPlayer in DB, based on id of FantasyManagerMatchdayChallengeLineUpPlayer in body.
     * 
     * Example for request body.
     * {<br/>
     * "createDate": 1437596274000,<br/>
     * "idMatchdayChallengePlayer": 996,<br/>
     * "idMatchdayChallengeLineUp": 225,<br/>
     * "id": 6718,<br/>
     * "slot": 9<br/>
     * }<br/>
     * @param token
     * @param fantasyManagerMatchdayChallengeLineUpPlayer
     * @return Status OK (200)
     * @throws DataNotFoundException if Object in body does not exits in DB.
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFantasyManagerMatchdayChallengeLineUpPlayer(@HeaderParam("authorization") String token, FantasyManagerMatchdayChallengeLineUpPlayer fantasyManagerMatchdayChallengeLineUpPlayer) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        FantasyManagerMatchdayChallengeLineUpPlayer foundedFantasyManagerMatchdayChallengeLineUpPlayer = em.find(FantasyManagerMatchdayChallengeLineUpPlayer.class, fantasyManagerMatchdayChallengeLineUpPlayer.getId());
        if (foundedFantasyManagerMatchdayChallengeLineUpPlayer != null) {
            helper.mergeObject(em, fantasyManagerMatchdayChallengeLineUpPlayer);
        } else {
            throw new DataNotFoundException("FantasyManagerMatchdayChallengeLineUpPlayer at index " + fantasyManagerMatchdayChallengeLineUpPlayer.getId() + " does not exits");
        }
        return Response.ok().build();
    }
    
    /**
     * Inserts new FantasyManagerMatchdayChallengeLineUpPlayer in DB.
     * New FantasyManagerMatchdayChallengeLineUpPlayer should be defined in body.
     * Method will override createDate to current date.
     * 
     *Example for request body.
     * {<br/>
     * "idMatchdayChallengePlayer": 996,<br/>
     * "idMatchdayChallengeLineUp": 225,<br/>
     * "id": 6718,<br/>
     * "slot": 9<br/>
     * }<br/>
     * 
     * @param token
     * @param fantasyManagerMatchdayChallengeLineUpPlayer
     * @return Status OK (200)
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertFantasyManagerMatchdayChallengeLineUpPlayer(@HeaderParam("authorization") String token, FantasyManagerMatchdayChallengeLineUpPlayer fantasyManagerMatchdayChallengeLineUpPlayer) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        fantasyManagerMatchdayChallengeLineUpPlayer.setCreateDate(new Date());
        helper.persistObject(em, fantasyManagerMatchdayChallengeLineUpPlayer);
        return Response.ok().build();
    }
}
