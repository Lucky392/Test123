/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyClubLineUpPlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/lineupPlayer")
public class FantasyClubLineUpPlayerRESTEndpoint {
 
    @InjectParam
    RestHelperClass helper;
    
    /**
     * API for method: .../rest/lineupPlayer/{id} This method return single element 
     * of lineupPlayer with defined id in JSON. 
     * 
     * Example for JSON response: 
     * 
     * @param token header parameter for checking permission
     * @param id of LineUpPlayer
     * @return 200 OK with LineUpPlayer in JSON format
     * @throws DataNotFoundException if LineUpPlayer with id doesn't exist in DB
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyClubLineUpById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyClubLineUpPlayerPOJO pojo = null;
        try {
            FantasyClubLineUpPlayer fantasyClubLineUpPlayer = (FantasyClubLineUpPlayer) em.createNamedQuery("FantasyClubLineUpPlayer.findById").setParameter("id", id).getSingleResult();   
            pojo = new FantasyClubLineUpPlayerPOJO(fantasyClubLineUpPlayer);
        } catch (Exception e) {
            throw new DataNotFoundException("Fantasy Club Line Up Player at index " + id + " does not exist..");
        }
        
        return Response.ok().entity(pojo).build();
    }
    
}
