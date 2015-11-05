/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyClubCreditHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/creditHistory")
public class CreditHistoryRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    @InjectParam
    Validator validator;

    /**
     * API for this method: .../rest/creditHistory/{id} This method returns JSON
     * list of Credit History for one Fantasy Club. It produces APPLICATION_JSON
     * media type. Example for JSON list: <br/> [ {
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
    public Response getCreditHistoryForFantasyClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
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

    /**
     * Create and insert new FantasyClubCreditHistory in db.
     * Create date property is automatically set to current time.
     * <br/>
     * Example for JSON body: <br/>
     * { <br/>
     * "idFantasyClub" : 54,<br/>
     * "action": "QUESTION_OF_THE_DAY",<br/>
     * "credit": 10000,<br/>
     * "updatedCredit": 12000<br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param creditHistory FantasyClubCreditHistory in JSON
     * @return status 201 created
     * @throws InputValidationException if validation is invalid
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertsFantasyClubCreditHistory(@HeaderParam("authorization") String token, FantasyClubCreditHistory creditHistory) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        if (validator.checkLenght(creditHistory.getAction(), 255, false)) {
            creditHistory.setCreateDate(new Date());
            helper.persistObject(em, creditHistory);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");

        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFantasyClubCreditHistory(@HeaderParam("authorization") String token, FantasyClubCreditHistory fantasyClubCreditHistory) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        FantasyClubCreditHistory oldFantasyClubCreditHistory = em.find(FantasyClubCreditHistory.class, fantasyClubCreditHistory.getId());
        if (oldFantasyClubCreditHistory != null) {
            oldFantasyClubCreditHistory.setCredit(fantasyClubCreditHistory.getCredit());
            oldFantasyClubCreditHistory.setUpdatedCredit(fantasyClubCreditHistory.getCredit());
        } else {
            throw new DataNotFoundException("FantasyClubCreditHistory at index " + fantasyClubCreditHistory.getId() + " does not exits");
        }
        return Response.ok().build();
    }

}
