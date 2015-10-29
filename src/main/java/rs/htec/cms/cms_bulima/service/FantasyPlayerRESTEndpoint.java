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
import rs.htec.cms.cms_bulima.domain.FantasyPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyPlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/fantasyPlayer")
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
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/leaguePlayer/6205",<br/>
     * "urlToPlaterSlot":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerSlot/8",<br/>
     * "urlToFantasyClub":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/fantasyClub/27",<br/>
     * "id": 4<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param id - id of FantasyPlayer that should be returned
     * @return FantasyPlayer in JSON for defined id
     * @throws DataNotFoundException if there is no FantasyPlayer with id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyPlayerById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyPlayerPOJO pojo;
        try {
            FantasyPlayer fantasyPlayer = (FantasyPlayer) em.createNamedQuery("FantasyPlayer.findById").setParameter("id", id).getSingleResult();
            pojo = new FantasyPlayerPOJO(fantasyPlayer);
        } catch (NoResultException e) {
            throw new DataNotFoundException("FantasyPlayer at index " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }

}
