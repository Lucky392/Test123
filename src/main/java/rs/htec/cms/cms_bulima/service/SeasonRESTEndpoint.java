/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Player;
import rs.htec.cms.cms_bulima.domain.Season;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.PlayerPOJO;
import rs.htec.cms.cms_bulima.pojo.SeasonPOJO;

/**
 *
 * @author stefan
 */
@Path("/season")
public class SeasonRESTEndpoint {
    
    @InjectParam
    RestHelperClass helper;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeasonById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        SeasonPOJO pojo;
        try {
            Season season = (Season) em.createNamedQuery("Season.findById").setParameter("id", id).getSingleResult();
            pojo = new SeasonPOJO(season);
        } catch (NoResultException e) {
            throw new DataNotFoundException("Season at index " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }    
    
}