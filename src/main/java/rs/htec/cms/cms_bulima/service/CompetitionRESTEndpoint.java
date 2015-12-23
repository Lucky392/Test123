/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
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
import rs.htec.cms.cms_bulima.domain.Competition;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/competitions")
public class CompetitionRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for method: .../rest/competitions/{id} This method return single element of
     * club at index in JSON. Example for JSON response: <br/> { <br/>"createDate":
     * 1406914086000,<br/> "sport": "Fußball",<br/> "gender": "male",<br/> "name": "2.
     * Bundesliga",<br/> "id": 2, <br/>"type": "championship"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of competition we are searching for
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "There is no competition at index 5!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompetition(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        Competition competition = em.find(Competition.class, id);
        if (competition == null) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no competition at index " + id + "!"), em);
            throw new DataNotFoundException("There is no competition at index " + id + "!");
        }
        helper.setResponseToHistory(history, Response.ok().entity(competition).build(), em);
        return Response.ok().entity(competition).build();
    }
}
