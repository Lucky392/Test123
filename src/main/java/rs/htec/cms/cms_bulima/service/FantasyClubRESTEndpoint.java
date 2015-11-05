/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyClub;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.FantasyClubPOJO;

/**
 *
 * @author stefan
 */
@Path("/fantasyClub")
public class FantasyClubRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for this method: .../rest/fantasyClub/{id} This method returns
     * JSON list of Fantasy Clubs for one Fantasy Manager. It produces
     * APPLICATION_JSON media type. Example for JSON list: <br/>[ {<br/>
     * "lastLogin": "2014-08-04 14:36:54.0",<br/> "totalBLMPoints": "0",<br/>
     * "auctionList": "[]",<br/> "changeLineUpTimestamp": "null",<br/>
     * "activity": "removed",<br/> "firstLogin": "2014-08-04 14:36:54.0",<br/>
     * "isLeagueFounder": "0",<br/>
     * "lastLoginWith": "null",<br/> "isLineUpChangedByCoTrainer": "0",<br/>
     * "sportDirectorActiveTimestamp": "null",<br/> "bidList": "[]",<br/>
     * "idFantasyClubLogo": "293",<br/> "lastQuestionAnswered": "null",<br/>
     * "id": "55",<br/>
     * "credit": "40000000",<br/> "amountSubstituteBench": "0",<br/>
     * "idActiveFormation": "1",<br/> "createDate": "2014-08-04
     * 14:36:54.0",<br/> "idEmblem": "303",<br/>
     * "fantasyPlayerList": "[]",<br/> "idCaptain": "null",<br/>
     * "fantasyClubCreditHistoryList": "[]",<br/> "idFantasyManager": "56",<br/>
     * "updateTimestamp": "2015-08-01 02:30:34.0",<br/> "questionRightAnswered":
     * "0",<br/>
     * "newsList": "[]",<br/> "amountCoTrainer": "0",<br/> "name": "ASV Inter
     * Lupfer 1920",<br/> "amountSubstituteBenchSlots": "0",<br/>
     * "idFantasyLeague": "null"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Manager
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception: <br/>{<br/>
     * "errorMessage": "There is no Fantasy Club for this Manager!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyClub> fc;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyClub f JOIN f.idFantasyManager u WHERE u.id = ");
        query.append(id);
        fc = em.createQuery(query.toString()).getResultList();
        if (fc.isEmpty()) {
            throw new DataNotFoundException("There is no Fantasy Club for this Manager!");
        } else {
            return Response.ok().entity(FantasyClubPOJO.toFantasyCLubPOJOList(fc)).build();
        }
    }
    
    /**
     * Updates FantasyClub.
     * 
     * @param token header parameter for checking permission
     * @param fantasyClub FantasyClub with changes that should be made
     * @return 200 OK
     * @throws InputValidationException if values in FantasyClub are invalid
     * @throws DataNotFoundException if id of FantasyClub doesn't exist in DB
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFantasyClub(@HeaderParam("authorization") String token, FantasyClub fantasyClub) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
        FantasyClub oldFC = em.find(FantasyClub.class, fantasyClub.getId());
        if (oldFC != null) {
            if (validator.checkLenght(fantasyClub.getName(), 255, true)
                    && validator.checkLenght(fantasyClub.getActivity(), 255, true)
                    && validator.checkLenght(fantasyClub.getLastLoginWith(), 255, true)) {
                fantasyClub.setCreateDate(oldFC.getCreateDate());
                helper.mergeObject(em, fantasyClub);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("FantasyClub at index " + fantasyClub.getId() + " does not exits");
        }
        return Response.ok().build();
    }
    
}
