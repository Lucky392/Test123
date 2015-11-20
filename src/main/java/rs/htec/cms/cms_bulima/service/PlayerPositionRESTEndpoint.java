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
import rs.htec.cms.cms_bulima.domain.PlayerPosition;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/playerPositions")
public class PlayerPositionRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method: .../rest/playerPositions/{id} This method return single element of
     * club at index in JSON. Example for JSON response:<br/>{<br/>
     * "createDate": 1388530800000,<br/> "name": "Midfield",<br/> "id": 3<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param id of player position we are searching for
     * @return Response 200 OK status with JSON body
     * @throw DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no player position at index 5!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPlayerPosition(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        PlayerPosition position = em.find(PlayerPosition.class, id);
        if (position == null) {
            throw new DataNotFoundException("There is no player position at index " + id + "!");
        }
        return Response.ok().entity(position).build();
    }
}
