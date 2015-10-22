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
import rs.htec.cms.cms_bulima.domain.MatchPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchPlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/matchPlayer")
public class MatchPlayerRESTEndpoint {

    RestHelperClass helper;

    public MatchPlayerRESTEndpoint() {
        helper = new RestHelperClass();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayerById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
//        MatchPlayerPOJO pojo;
        MatchPlayer player;
        try {
            player = (MatchPlayer) em.createNamedQuery("MatchPlayer.findById").setParameter("id", id).getSingleResult();
//            pojo = new MatchPlayerPOJO(player);
        } catch (Exception e) {
            throw new DataNotFoundException("MatchPlayer at index " + id + " does not exist.." + e.getMessage());
        }
        return Response.ok().entity(player).build();
    }

}
