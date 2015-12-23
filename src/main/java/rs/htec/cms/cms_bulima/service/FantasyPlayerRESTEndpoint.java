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
import rs.htec.cms.cms_bulima.domain.FantasyPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyPlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/fantasyPlayers")
public class FantasyPlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns FantasyPlayer for defined id. Example for response:<br/>
     * <br/>
     * {<br/>
     * "createDate": 1437560739000,<br/>
     * "idFantasyClub": 27,<br/>
     * "joinFantasyClubTimestamp": 1437398912000,<br/>
     * "idLeaguePlayer": 6205,<br/>
     * "idPlayerSlot": 8,<br/>
     * "urlToLeaguePlayer":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/leaguePlayers/6205",<br/>
     * "urlToPlaterSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlots/8",<br/>
     * "urlToFantasyClub":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyClubs/27",<br/>
     * "id": 4<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param id - id of FantasyPlayer that should be returned
     * @return FantasyPlayer in JSON for defined id
     * @throws DataNotFoundException if there is no FantasyPlayer with id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyPlayerById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyPlayerPOJO pojo;
        try {
            FantasyPlayer fantasyPlayer = (FantasyPlayer) em.createNamedQuery("FantasyPlayer.findById").setParameter("id", id).getSingleResult();
            pojo = new FantasyPlayerPOJO(fantasyPlayer);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("FantasyPlayer at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("FantasyPlayer at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

}
