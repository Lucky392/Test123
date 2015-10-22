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
import rs.htec.cms.cms_bulima.domain.League;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.LeaguePOJO;

/**
 *
 * @author marko
 */
@Path("/league")
public class LeagueRESTEndpoint {

    RestHelperClass helper;

    public LeagueRESTEndpoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for method: .../rest/league/{id} This method return single element of
     * club at index in JSON. Example for JSON response: <br/> {<br/> "competitionUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/competition/2",<br/>
     * "createDate": 1406914139000,<br/> "idSport1League": "3",<br/> "sport": "soccer",<br/>
     * "id": 2<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param id of league we are searching for
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "There is no league at index 5!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeague(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        League league = em.find(League.class, id);
        if (league == null) {
            throw new DataNotFoundException("There is no league at index " + id + "!");
        }
        return Response.ok().entity(new LeaguePOJO(league)).build();
    }
}
