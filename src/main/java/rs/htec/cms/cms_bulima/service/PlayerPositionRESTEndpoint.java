/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.PlayerPosition;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
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
     * API for method: .../rest/playerPositions/{id} This method return single
     * element of club at index in JSON. Example for JSON response:<br/>{<br/>
     * "createDate": 1388530800000,<br/> "name": "Midfield",<br/> "id": 3<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of player position we are searching for
     * @return Response 200 OK status with JSON body
     * @throw DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no player position at index 5!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPlayerPosition(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        PlayerPosition position = em.find(PlayerPosition.class, id);
        if (position == null) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no player position at index " + id + "!"), em);
            throw new DataNotFoundException("There is no player position at index " + id + "!");
        }
        Response response = Response.ok().entity(position).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * Returns all player positions.
     * <br>Api: ../rest/playerPositions
     *
     * Example for JSON response: <br>
     * {<br>
     * "count": 4,<br>
     * "data": [<br>
     * {<br>
     * "createDate": 1388530800000,<br>
     * "name": "Goalie",<br>
     * "id": 1<br>
     * },<br>
     * {<br>
     * "createDate": 1388530800000,<br>
     * "name": "Defense",<br>
     * "id": 2<br>
     * },<br>
     * {<br>
     * "createDate": 1388530800000,<br>
     * "name": "Midfield",<br>
     * "id": 3<br>
     * },<br>
     * {<br>
     * "createDate": 1388530800000,<br>
     * "name": "Forward",<br>
     * "id": 4<br>
     * }<br>
     * ]<br>
     * }
     * <br>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param page number of page at which we search for positions
     * @param limit number of positions method returns
     * @return list of player positions
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerPositions(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<PlayerPosition> positions = em.createNamedQuery("PlayerPosition.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (positions.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = "Select COUNT(p) From PlayerPosition p";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(positions);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
