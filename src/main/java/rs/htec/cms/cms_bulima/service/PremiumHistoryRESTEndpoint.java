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
import rs.htec.cms.cms_bulima.domain.PremiumHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/premiumHistory")
public class PremiumHistoryRESTEndpoint {

    RestHelperClass helper;

    public PremiumHistoryRESTEndpoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for this method: .../rest/premiumHistory/user/{email} This
     * method return JSON list of Premium History for one User. It produces
     * APPLICATION_JSON media type. Example for JSON object: <br/>[ {
     * <br/>"idUser": "54483",<br/> "charges": "1",<br/> "idFantasyClub":
     * "52162",<br/> "idReward": "null",<br/>
     * "premiumCurrency": "25",<br/> "updatedPremiumCurrency": "null",<br/>
     * "idPremiumAction": "8",<br/> "idFantasyManager": "51978",<br/>
     * "idPremiumItem": "4",<br/> "updatedCharges": "1",<br/> "id": "989",<br/>
     * "createDate": "2015-07-22 11:55:23.0"<br/> },<br/> { <br/>"idUser":
     * "54483",<br/> "charges": "1",<br/> "idFantasyClub": "52162",<br/>
     * "idReward": "null",<br/> "premiumCurrency": "25",<br/>
     * "updatedPremiumCurrency": "null",<br/> "idPremiumAction": "7",<br/>
     * "idFantasyManager": "51978",<br/> "idPremiumItem": "null",<br/>
     * "updatedCharges": "1",<br/> "id": "78432",<br/> "createDate": "2015-07-31
     * 07:44:19.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param email is email for what user you want Fantasy Managers
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no Premium History for this user!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumHistoryUser(@HeaderParam("authorization") String token, @PathParam("email") String email) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<PremiumHistory> history;
        StringBuilder query = new StringBuilder("SELECT ph FROM PremiumHistory ph JOIN ph.idUser u WHERE u.email = '");
        query.append(email).append("'");
        history = em.createQuery(query.toString()).getResultList();
        if (history.isEmpty()) {
            throw new DataNotFoundException("There is no Premium History for this user!");
        } else {
            return Response.ok().entity(helper.getJson(history)).build();
        }
    }

    /**
     * API for this method: .../rest/premiumHistory/club/{idClub} This
     * method return JSON list of Premium History for one Club. It produces
     * APPLICATION_JSON media type. Example for JSON object: <br/>[ {
     * <br/>"idUser": "29867",<br/> "charges": "1",<br/> "idFantasyClub":
     * "27434",<br/> "idReward": "null",<br/>
     * "premiumCurrency": "158",<br/> "updatedPremiumCurrency": "11",<br/>
     * "idPremiumAction": "1",<br/> "idFantasyManager": "27805",<br/>
     * "idPremiumItem": "null",<br/> "updatedCharges": "1",<br/> "id": "1",<br/>
     * "createDate": "2015-07-20 15:32:35.0"<br/> },<br/> {<br/> "idUser":
     * "29867",<br/> "charges": "1",<br/> "idFantasyClub": "27434",<br/>
     * "idReward": "null",<br/> "premiumCurrency": "112",<br/>
     * "updatedPremiumCurrency": "11",<br/> "idPremiumAction": "1",<br/>
     * "idFantasyManager": "27805",<br/> "idPremiumItem": "null",<br/>
     * "updatedCharges": "1",<br/> "id": "305",<br/> "createDate": "2015-07-21
     * 14:01:26.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param idClub is id of Fantasy Club
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no Premium History for this Club!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("club/{idClub}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumHistoryClub(@HeaderParam("authorization") String token, @PathParam("idClub") long idClub) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<PremiumHistory> history;
        StringBuilder query = new StringBuilder("SELECT ph FROM PremiumHistory ph WHERE ph.idFantasyClub.id = ");
        query.append(idClub);
        history = em.createQuery(query.toString()).getResultList();
        if (history.isEmpty()) {
            throw new DataNotFoundException("There is no Premium History for this Club!");
        } else {
            return Response.ok().entity(helper.getJson(history)).build();
        }
    }

}