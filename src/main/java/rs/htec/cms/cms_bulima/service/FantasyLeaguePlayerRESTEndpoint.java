/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.FantasyLeaguePlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyLeaguePlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/fantasyLeaguePlayers")
public class FantasyLeaguePlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns FantasyLeaguePlayer for defined id. Example for response:<br/>
     * <br/>
     * {<br/>
     * "urlToFantasyLeague":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyLeagues/2",<br/>
     * "urlToPlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/players/3677",<br/>
     * "createDate": 1437385460000,<br/>
     * "idPlayer": 3677,<br/>
     * "idFantasyLeague": 2,<br/>
     * "hasClub": 0,<br/>
     * "isInAuction": 0,<br/>
     * "id": 4<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param id id of FantasyLeaguePlayer that should be returned
     * @return FantasyLeaguePlayer in JSON for defined id
     * @throws DataNotFoundException if there is no FantasyLeaguePlayer with id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyLeaguePlayerById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyLeaguePlayerPOJO pojo;
        try {
            FantasyLeaguePlayer fantasyLeaguePlayer = (FantasyLeaguePlayer) em.createNamedQuery("FantasyLeaguePlayer.findById").setParameter("id", id).getSingleResult();
            pojo = new FantasyLeaguePlayerPOJO(fantasyLeaguePlayer);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("FantasyLeaguePlayer at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("FantasyLeaguePlayer at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

}
