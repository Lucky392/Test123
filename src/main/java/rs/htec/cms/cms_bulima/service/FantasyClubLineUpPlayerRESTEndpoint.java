/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

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
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/lineupPlayer")
public class FantasyClubLineUpPlayerRESTEndpoint {
 
    RestHelperClass helper;

    public FantasyClubLineUpPlayerRESTEndpoint() {
        helper = new RestHelperClass();
    }   
    
    /**
     * API for method: .../rest/lineupPlayer/{id} This method return single element 
     * of lineupPlayer with defined id in JSON. 
     * 
     * Example for JSON response: 
     * 
     * @param token
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewsById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyClubLineUpPlayer fantasyClubLineUpPlayer = null;
        try {
            fantasyClubLineUpPlayer = (FantasyClubLineUpPlayer) em.createNamedQuery("FantasyClubLineUpPlayer.findById").setParameter("id", id).getSingleResult();   
        } catch (Exception e) {
            throw new DataNotFoundException("Fantasy Club Line Up Player at index " + id + " does not exist..");
        }
        
        return Response.ok().entity(fantasyClubLineUpPlayer).build();
    }
    
}
