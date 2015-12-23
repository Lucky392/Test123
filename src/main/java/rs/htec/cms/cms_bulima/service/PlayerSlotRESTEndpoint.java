/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import rs.htec.cms.cms_bulima.domain.PlayerSlot;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDayPrize;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/playerSlots")
public class PlayerSlotRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns PlayerSlot for defined id. Example for response:<br/>
     * <br/>
     * {<br/>
     * "createDate": 1388530800000,<br/>
     * "position": 8,<br/>
     * "name": "slot8",<br/>
     * "id": 8<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param id id of PlayerSlot that should be returned
     * @return PlayerSlot in JSON for defined id
     * @throws DataNotFoundException if there is no PlayerSlot with id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerSlotById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        PlayerSlot playerSlot;
        try {
            playerSlot = (PlayerSlot) em.createNamedQuery("PlayerSlot.findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            throw new DataNotFoundException("PlayerSlot at index " + id + " does not exist..");
        }
        return Response.ok().entity(playerSlot).build();
    }

    /**
     * Returns all player slots. <br>Api: ../rest/playerSlots
     *
     * Example for JSON response: <br>{<br>
     * "count": 15,<br>
     * "data": [<br>
     * {<br>
     * "createDate": 1388530800000,<br>
     * "position": 1,<br>
     * "name": "Torwart",<br>
     * "id": 1<br>
     * },<br>
     * {<br>
     * "createDate": 1388530800000,<br>
     * "position": 2,<br>
     * "name": "Abwehr",<br>
     * "id": 2<br>
     * }<br>
     *
     * @param token header parameter for checking permission
     * @param page number of page at which we search for slots
     * @param limit number of slots method returns
     * @return list of player slots
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerSlots(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<PlayerSlot> slots = em.createNamedQuery("PlayerSlot.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (slots.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = "Select COUNT(ps) From PlayerSlot ps";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(slots);
        return Response.ok().entity(go).build();
    }

}
