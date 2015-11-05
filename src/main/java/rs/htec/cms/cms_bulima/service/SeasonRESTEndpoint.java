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
import rs.htec.cms.cms_bulima.domain.Season;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.SeasonPOJO;

/**
 *
 * @author stefan
 */
@Path("/season")
public class SeasonRESTEndpoint {
    
    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Season for specified id.
     * 
     * { <br/>
*  "createDate": 1420066800000,<br/>
*  "idLeague": 1,<br/>
*  "idSport1Season": "355",<br/>
*  "idFirstMatchday": null,<br/>
*  "urlToFirstMatchday": null,<br/>
*  "urlToLeague": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/league/1",<br/>
*  "name": "2014/2015",<br/>
*  "id": 1<br/>
*}<br/>
     * 
     * @param token- header parameter for checking permission
     * @param id - for Season
     * @return 200 OK and Season in JSON
     * @throws DataNotFoundException if there is no Season for id
     */
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
