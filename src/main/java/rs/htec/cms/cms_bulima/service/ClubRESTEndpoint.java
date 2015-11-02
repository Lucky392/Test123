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
import rs.htec.cms.cms_bulima.domain.Club;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.ClubPOJO;

/**
 *
 * @author marko
 */
@Path("/club")
public class ClubRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method: .../rest/club/{id} This method return single element of
     * club at index in JSON. Example for JSON response:<br/> { <br/>"createDate":
     * 1388530800000,<br/> "shortName": "FCK",<br/> "leagueUrl":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/league/2",<br/>
     * "idSport1Team": "6",<br/> "mediumName": "1. FC Kaiserslautern",<br/> "logoUrl":
     * "http://assets.bundesligamanager.htec.co.rs/images/favoriteclublogos/1.-FC-Kaiserslautern.png",<br/>
     * "logo": null,<br/> "id": 3<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param id of club we are searching for
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "There is no club at index 5!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        Club club = em.find(Club.class, id);
        if (club == null) {
            throw new DataNotFoundException("There is no club at index " + id + "!");
        }
        return Response.ok().entity(new ClubPOJO(club)).build();
    }
}
