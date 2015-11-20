/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
import javax.persistence.EntityManager;
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
import rs.htec.cms.cms_bulima.domain.FantasyManager;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.MethodNotAllowedException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.FantasyManagerPOJO;

/**
 *
 * @author stefan
 */
@Path("/managers")
public class FantasyManagerRESTEndpoint {

    RestHelperClass helper;

    public FantasyManagerRESTEndpoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for this method: .../rest/managers/{email} This method returns JSON
     * list of Fantasy Managers for one user. It produces APPLICATION_JSON media
     * type. Example for JSON list: <br/>[ {<br/>
     * "idUser": "1",<br/> "firstname": "Wilhelm",<br/> "fantasyClubList":
     * "[1]",<br/>
     * "idFavClub": "null",<br/> "id": "1",<br/> "secondLeague": "0",<br/>
     * "updateTimestamp": "2014-08-01 15:52:31.0",<br/> "username":
     * "WilhelmTest",<br/> "lastname": "Penske",<br/>
     * "createDate": "2014-08-01 15:52:31.0",<br/> "profilePhotoUrl":
     * "null"<br/> },<br/> {<br/>
     * "idUser": "1",<br/> "firstname": "attila ",<br/> "fantasyClubList":
     * "[46146]",<br/>
     * "idFavClub": "null",<br/> "id": "46134",<br/> "secondLeague": "0",<br/>
     * "updateTimestamp": "2015-08-12 11:34:41.0",<br/> "username":
     * "falsbuak",<br/>
     * "lastname": "nemeth",<br/> "createDate": "2015-04-30 19:39:20.0",<br/>
     * "profilePhotoUrl": "null"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param page
     * @param limit
     * @param userID
     * @param username
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no Fantasy Managers for this user!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyManager(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("userID") long userID, @QueryParam("username") String username) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyManager> fm;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyManager f ");
        if (userID != 0) {
            query.append("WHERE f.idUser.id = ")
                    .append(userID);
        }
        if (username != null) {
            query.append(userID != 0 ? " AND" : "WHERE")
                    .append(" f.username = '")
                    .append(username)
                    .append("'");
        }
        //method not allowed if there is no query params!!!
        if (userID == 0 && username == null) {
            throw new MethodNotAllowedException("Method not allowed, you need to use some query params!");
        }
        fm = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (fm.isEmpty()) {
            throw new DataNotFoundException("There is no Fantasy Managers for this user!");
        } else {
            String countQuery = query.toString().replaceFirst("f", "count(f)");
            long count = (long) em.createQuery(countQuery).getSingleResult();
            GetObject go = new GetObject();
            go.setCount(count);
            go.setData(FantasyManagerPOJO.toFantasyManagerPOJO(fm));
            return Response.ok().entity(go).build();
        }
    }

}
