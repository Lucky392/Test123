/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
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
import rs.htec.cms.cms_bulima.domain.FantasyClubCreditHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/creditHistory")
public class CreditHistoryRESTEndpoint {

    RestHelperClass helper;

    public CreditHistoryRESTEndpoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for this method: .../rest/creditHistory/{id} This method
     * returns JSON list of Credit History for one Fantasy Club. It produces
     * APPLICATION_JSON media type. Example for JSON list: <br/> [ {
     * <br/> "updatedCredit": "16502803",<br/> "idFantasyClub": "100",<br/>
     * "idAuction": "null",<br/> "action": "QUESTION_OF_THE_DAY",<br/> "id":
     * "380601",<br/> "credit": "10000",<br/> "createDate": "2015-07-29
     * 10:17:30.0"<br/> },<br/> { <br/> "updatedCredit": "18460833",<br/>
     * "idFantasyClub": "100",<br/> "idAuction": "1171683",<br/> "action":
     * "BATCH_AUCTION_SELLING_TO_SPORT1",<br/> "id": "430034",<br/> "credit":
     * "1958030",<br/>
     * "createDate": "2015-07-30 10:25:08.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Club
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no credit history for this club!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreditHistory(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyClubCreditHistory> creditHistory;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyClubCreditHistory f WHERE f.idFantasyClub.id = ");
        query.append(id);
        creditHistory = em.createQuery(query.toString()).getResultList();
        if (creditHistory.isEmpty()) {
            throw new DataNotFoundException("There is no credit history for this club!");
        } else {
            return Response.ok().entity(creditHistory).build();
        }
    }
}
