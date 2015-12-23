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
import rs.htec.cms.cms_bulima.domain.FantasyClubValuation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/valuations")
public class ValuationRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for this method: .../rest/valuations/league/{idLeague}/{idMatchday}
     * This method return JSON list of Valuation for one League in one
     * Matchday.It produces APPLICATION_JSON media type. Example for JSON
     * list:<br/>
     * [ { <br/>"winMatchdays": "0.0",<br/> "blmPointsDiff": "0",<br/>
     * "creditDiff": "0",<br/>
     * "marketValue": "8574147",<br/> "positionUpdateAt": "2015-07-28
     * 10:16:28.0",<br/>
     * "positionDiff": "9",<br/> "blmPoints": "0",<br/> "blmPointsUpdateAt":
     * "2015-07-28 10:16:28.0",<br/> "idFantasyClub": "24036",<br/>
     * "idMatchday": "1",<br/>
     * "winMatchdaysUpdateAt": "2015-07-28 10:16:28.0",<br/> "id": "9",<br/>
     * "position": "9",<br/> "marketValueUpdateAt": "2015-07-28
     * 10:16:28.0",<br/> "createDate": "2015-07-28 10:16:28.0"<br/> },<br/>
     * {<br/> "winMatchdays": "0.0",<br/> "blmPointsDiff": "13",<br/>
     * "creditDiff": "39000",<br/> "marketValue": "13612245",<br/>
     * "positionUpdateAt": "2015-07-28 10:16:28.0",<br/> "positionDiff":
     * "3",<br/>
     * "blmPoints": "13",<br/> "blmPointsUpdateAt": "2015-07-28
     * 10:16:28.0",<br/>
     * "idFantasyClub": "27288",<br/> "idMatchday": "1",<br/>
     * "winMatchdaysUpdateAt": "2015-07-28 10:16:28.0",<br/> "id": "3",<br/>
     * "position": "3",<br/>
     * "marketValueUpdateAt": "2015-07-28 10:16:28.0",<br/> "createDate":
     * "2015-07-28 10:16:28.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param idLeague is id of Fantasy League
     * @param idMatchday is id of Matchday
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no valuation for clubs in this league!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("league/{idLeague}/{idMatchday}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeagueValuation(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("idLeague") long idLeague, @PathParam("idMatchday") long idMatchday) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<FantasyClubValuation> valuation;
        StringBuilder query = new StringBuilder("SELECT fcv FROM FantasyClubValuation fcv JOIN fcv.idFantasyClub fc JOIN fc.idFantasyLeague fl WHERE fl.id = ");
        query.append(idLeague).append(" AND fcv.idMatchday.id = ").append(idMatchday);
        valuation = em.createQuery(query.toString()).getResultList();
        if (valuation.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no valuation for clubs in this league!"), em);
            throw new DataNotFoundException("There is no valuation for clubs in this league!");
        } else {
            Response response = Response.ok().entity(valuation).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        }
    }

    /**
     * API for this method:
     * .../rest/statistics/valuations/club/{idClub}/{idMatchday} This method
     * return JSON object of Valuation for one Club in one Matchday.It produces
     * APPLICATION_JSON media type. Example for JSON object:<br/> [ {
     * <br/>"winMatchdays": "0.0",<br/> "blmPointsDiff": "0",<br/> "creditDiff":
     * "0",<br/> "marketValue": "8574147",<br/>
     * "positionUpdateAt": "2015-07-28 10:16:28.0",<br/> "positionDiff":
     * "9",<br/> "blmPoints": "0",<br/> "blmPointsUpdateAt": "2015-07-28
     * 10:16:28.0",<br/> "idFantasyClub": "24036",<br/> "idMatchday": "1",<br/>
     * "winMatchdaysUpdateAt": "2015-07-28 10:16:28.0",<br/> "id": "9",<br/>
     * "position": "9",<br/> "marketValueUpdateAt": "2015-07-28
     * 10:16:28.0",<br/> "createDate": "2015-07-28 10:16:28.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param idClub is id of Fantasy Club
     * @param idMatchday is id of Matchday
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no valuation for this club!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("club/{idClub}/{idMatchday}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClubValuation(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("idClub") long idClub, @PathParam("idMatchday") long idMatchday) {
        EntityManager em = helper.getEntityManager();
         CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<FantasyClubValuation> fcv;
        StringBuilder query = new StringBuilder("SELECT fcv FROM FantasyClubValuation fcv WHERE fcv.idFantasyClub.id = ");
        query.append(idClub).append(" AND fcv.idMatchday.id = ").append(idMatchday);
        fcv = em.createQuery(query.toString()).getResultList();
        if (fcv.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no valuation for this club!"), em);
            throw new DataNotFoundException("There is no valuation for this club!");
        } else {
            Response response = Response.ok().entity(fcv).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        }
    }

}
