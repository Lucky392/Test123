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
import rs.htec.cms.cms_bulima.domain.MatchPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchPlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/matchPlayer")
public class MatchPlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    /**
     * Returns MatchPlayer for specified id.
     * <br/>
     * Example for MatchPlayer response:<br/>
     * 
     * {<br/>
     *  "createDate": 1438071103000,<br/>
     *  "blmPoints": -2,<br/>
     *  "idPlayer": 4463,<br/>
     *  "updateAt": 1438071040000,<br/>
     *  "blmPointsUpdateAt": 1438071355000,<br/>
     *  "rating": 4.55,<br/>
     *  "ratingAt": 1438071040000,<br/>
     *  "extra1": 0,<br/>
     *  "extra2": 0,<br/>
     *  "idMatch": 2,<br/>
     *  "urlToPlayer": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/player/4463",<br/>
     *  "urlToMatch": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/match/2",<br/>
     *  "id": 34<br/>
     *}<br/>
     * 
     * @param token - header parameter for checking permission
     * @param id - of MatchPlayer that should be returned
     * @return MatchPlayer in JSON
     * @throws DataNotFoundException if MatchPlayer doesn't exist for defined id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayerById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        MatchPlayerPOJO pojo;
        try {
            MatchPlayer player = (MatchPlayer) em.createNamedQuery("MatchPlayer.findById").setParameter("id", id).getSingleResult();
            pojo = new MatchPlayerPOJO(player);
        } catch (Exception e) {
            throw new DataNotFoundException("MatchPlayer at index " + id + " does not exist.." + e.getMessage());
        }
        return Response.ok().entity(pojo).build();
    }
    
    /**
     *  Returns list of MatchPlayer objects.
     * That list can be:<br/>
     * -ordered by specified column<br/>
     * -filtered by idMatch and idPlayer<br/>
     * -filtered between two dates<br/>
     * 
     * {<br/>
     * "data": [<br/>
     * {<br/>
     *  "createDate": 1438071103000,<br/>
     *  "blmPoints": -2,<br/>
     *  "idPlayer": 4463,<br/>
     *  "updateAt": 1438071040000,<br/>
     *  "blmPointsUpdateAt": 1438071355000,<br/>
     *  "rating": 4.55,<br/>
     *  "ratingAt": 1438071040000,<br/>
     *  "extra1": 0,<br/>
     *  "extra2": 0,<br/>
     *  "idMatch": 2,<br/>
     *  "urlToPlayer": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/player/4463",<br/>
     *  "urlToMatch": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/match/2",<br/>
     *  "id": 34<br/>
     *},<br/>
     *{
     *"createDate": 1438071103000,<br/>
     * "blmPoints": 6,<br/>
     * "idPlayer": 4619,<br/>
     * "updateAt": 1438071034000,<br/>
     * "blmPointsUpdateAt": 1438071355000,<br/>
     * "rating": 1.94,<br/>
     * "ratingAt": 1438071034000,<br/>
     * "extra1": 0,<br/>
     * "extra2": 0,<br/>
     * "idMatch": 1,<br/>
     * "urlToPlayer": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/player/4619",<br/>
     * "urlToMatch": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/match/1",<br/>
     * "id": 10<br/>
    *}<br/>
     *],<br/>
     *"count": 743<br/>
     *}<br/>
     * 
     * @param token - header parameter for checking permission
     * @param page - number of page at which we search for News
     * @param limit - number of News method returns
     * @param orderBy - column name for ordering, if you put "-" before
     * column name, that mean DESC ordering.
     * @param idMatch - filter based on id for Match
     * @param idPlayer - filter based on id for Player
     * @param minDate - start date for filtering time in millis
     * @param maxDate - end date for filtering time in millis
     * @return 200 OK with list of MatchPlayer in JSON
     * @throws DataNotFoundException if there is no MatchPlayer for search
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayer(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy, @QueryParam("idMatch") String idMatch,
            @QueryParam("idPlayer") String idPlayer, @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<MatchPlayer> matchPlayers;
        StringBuilder query = new StringBuilder("SELECT m FROM MatchPlayer m");
        String operator = " WHERE";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(" WHERE m.ratingAt BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND";
        }
        
        if (idMatch != null) {
            query.append(operator).append(" m.idMatch = '").append(idMatch).append("'");
            operator = " AND";
        }

        if (idPlayer != null) {
            query.append(operator).append(" m.idPlayer = '").append(idPlayer).append("'");
        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        matchPlayers = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (matchPlayers == null || matchPlayers.isEmpty()) {
            throw new DataNotFoundException("There is no MatchPlayer for this search!");
        }

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        List<MatchPlayerPOJO> pojos = MatchPlayerPOJO.toMatchdayPOJOList(matchPlayers);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }

}