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
import rs.htec.cms.cms_bulima.domain.FantasyLeague;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/fantasyLeague")
public class FantasyLeagueRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method: .../rest/fantasyLeague/{id} This method return single
     * element of club at index in JSON. Example for JSON response:<br/> {<br/> "logo":
     * "",<br/> "createDate": 1406902029000,<br/> "startDate": 1406902029000,<br/>
     * "numMembers": 1,<br/> "activity": "sehr aktiv",<br/> "leaguehash":
     * "cfbd436c-dc48-462c-bf31-413e03dc53db",<br/> "secondLeague": 0,<br/>
     * "dayOneActivity": 0,<br/> "dayTwoActivity": 0,<br/> "dayThreeActivity": 0,<br/>
     * "dayFourActivity": 0,<br/> "dayFiveActivity": 0,<br/> "daySixActivity": 0,<br/>
     * "daySevenActivity": 0,<br/> "lastSevenDaysActivity": 0,<br/> "leagueType":
     * "NORMAL",<br/> "leagueStatus": "NORMAL",<br/> "inactive": 0,<br/> "warnedAt":
     * 1437697830000,<br/> "updatedForSeason": 0,<br/> "options": 0,<br/> "description": null,<br/>
     * "password": "test1234",<br/> "name": "TestLiga3",<br/> "id": 2 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param id of league we are searching for
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "There is no league at index 1!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyLeague(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyLeague league = em.find(FantasyLeague.class, id);
        if (league == null) {
            throw new DataNotFoundException("There is no league at index " + id + "!");
        }
        return Response.ok().entity(league).build();
    }
}
