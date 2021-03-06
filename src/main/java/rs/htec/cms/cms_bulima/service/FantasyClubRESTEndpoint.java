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
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.FantasyClub;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.exception.MethodNotAllowedException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.FantasyClubPOJO;

/**
 *
 * @author stefan
 */
@Path("/fantasyClubs")
public class FantasyClubRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for this method: .../rest/fantasyClubs/manager/{id} This method
     * returns JSON list of Fantasy Clubs for one Fantasy Manager. It produces
     * APPLICATION_JSON media type. Example for JSON list: <br/>[ {<br/>
     * "createDate": 1407155814000,<br/>
     * "activity": "removed",<br/>
     * "lastLoginWith": null,<br/>
     * "credit": 40000000,<br/>
     * "updateTimestamp": 1438389034000,<br/>
     * "totalBLMPoints": 0,<br/>
     * "changeLineUpTimestamp": null,<br/>
     * "isLeagueFounder": 0,<br/>
     * "amountSubstituteBench": 0,<br/>
     * "amountCoTrainer": 0,<br/>
     * "lastLogin": 1407155814000,<br/>
     * "firstLogin": 1407155814000,<br/>
     * "sportDirectorActiveTimestamp": null,<br/>
     * "lastQuestionAnswered": null,<br/>
     * "questionRightAnswered": 0,<br/>
     * "amountSubstituteBenchSlots": 0,<br/>
     * "isLineUpChangedByCoTrainer": 0,<br/>
     * "fantasyLeagueName": null,<br/>
     * "fantasyLeagueID": null,<br/>
     * "fantasyManagerName": "sefesfes null",<br/>
     * "fantasyManagerID": 56,<br/>
     * "captainFantasyPlayerID": null,<br/>
     * "fantasyClubLogoID": 293,<br/>
     * "formationName": "4-4-2",<br/>
     * "captianName": null,<br/>
     * "formationID": 1,<br/>
     * "emblemID": 303,<br/>
     * "name": "ASV Inter Lupfer 1920",<br/>
     * "id": 55<br/>
     * }<br/> ]
     *
     * @param token is a header parameter for checking permission
     * @param page
     * @param limit
     * @param fantasyManagerID
     * @param fantasyLeagueID
     * @param name
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception: <br/>{<br/>
     * "errorMessage": "There is no Fantasy Club for this Manager!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyClubs(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("fantasyManagerID") long fantasyManagerID, @QueryParam("fantasyLeagueID") long fantasyLeagueID, @QueryParam("name") String name) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<FantasyClub> fc;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyClub f ");
        if (fantasyManagerID != 0) {
            query.append("WHERE f.idFantasyManager.id = ")
                    .append(fantasyManagerID);
        }
        if (fantasyLeagueID != 0) {
            query.append(fantasyManagerID != 0 ? " AND" : "WHERE")
                    .append(" f.idFantasyLeague.id = ")
                    .append(fantasyLeagueID);
        }
        if (name != null) {
            query.append(fantasyManagerID != 0 || fantasyLeagueID != 0 ? " AND" : "WHERE")
                    .append(" f.name LIKE '%")
                    .append(name)
                    .append("%'");
        }
        if (fantasyManagerID == 0 && fantasyLeagueID == 0 && name == null) {
            helper.setResponseToHistory(history, new MethodNotAllowedException("Method not allowed, you need to use some query params!"), em);
            throw new MethodNotAllowedException("Method not allowed, you need to use some query params!");
        }
        fc = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (fc.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no Fantasy Club for this Manager!"), em);
            throw new DataNotFoundException("There is no Fantasy Club for this Manager!");
        } else {
            String countQuery = query.toString().replaceFirst("f", "count(f)");
            long count = (long) em.createQuery(countQuery).getSingleResult();
            GetObject go = new GetObject();
            go.setCount(count);
            go.setData(FantasyClubPOJO.toFantasyCLubPOJOList(fc));
            helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
            return Response.ok().entity(go).build();
        }
    }

    /**
     * API for this method: .../rest/fantasyClubs/{id} This method returns
     * FantasyClub for defined id. It produces APPLICATION_JSON media type.
     * Example for JSON response:
     * <br/>
     * {<br/>
     * "createDate": 1407155814000,<br/>
     * "activity": "removed",<br/>
     * "lastLoginWith": null,<br/>
     * "credit": 40000000,<br/>
     * "updateTimestamp": 1438389034000,<br/>
     * "totalBLMPoints": 0,<br/>
     * "changeLineUpTimestamp": null,<br/>
     * "isLeagueFounder": 0,<br/>
     * "amountSubstituteBench": 0,<br/>
     * "amountCoTrainer": 0,<br/>
     * "lastLogin": 1407155814000,<br/>
     * "firstLogin": 1407155814000,<br/>
     * "sportDirectorActiveTimestamp": null,<br/>
     * "lastQuestionAnswered": null,<br/>
     * "questionRightAnswered": 0,<br/>
     * "amountSubstituteBenchSlots": 0,<br/>
     * "isLineUpChangedByCoTrainer": 0,<br/>
     * "fantasyLeagueName": null,<br/>
     * "fantasyLeagueID": null,<br/>
     * "fantasyManagerName": "sefesfes null",<br/>
     * "fantasyManagerID": 56,<br/>
     * "captainFantasyPlayerID": null,<br/>
     * "fantasyClubLogoID": 293,<br/>
     * "formationName": "4-4-2",<br/>
     * "captianName": null,<br/>
     * "formationID": 1,<br/>
     * "emblemID": 303,<br/>
     * "name": "ASV Inter Lupfer 1920",<br/>
     * "id": 55<br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Club
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception: <br/>{<br/>
     * "errorMessage": "There is no Fantasy Club for this id!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyClubById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        FantasyClub fc;
        StringBuilder query = new StringBuilder("SELECT f FROM FantasyClub f WHERE f.id = ");
        query.append(id);
        fc = (FantasyClub) em.createQuery(query.toString()).getSingleResult();
        if (fc == null) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no Fantasy Club for this id!"), em);
            throw new DataNotFoundException("There is no Fantasy Club for this id!");
        } else {
            FantasyClubPOJO pojo = new FantasyClubPOJO(fc);
            helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
            return Response.ok().entity(pojo).build();
        }
    }

    /**
     * Updates FantasyClub.
     * <br/>
     * Example for body: {<br/>
     * "activity": "removed",<br/>
     * "lastLoginWith": "WEB",<br/>
     * "credit": 18039000,<br/>
     * "totalBLMPoints": 0,<br/>
     * "changeLineUpTimestamp": null,<br/>
     * "isLeagueFounder": 0,<br/>
     * "amountSubstituteBench": 0,<br/>
     * "amountCoTrainer": 0,<br/>
     * "sportDirectorActiveTimestamp": null,<br/>
     * "questionRightAnswered": 0,<br/>
     * "isLineUpChangedByCoTrainer": 0,<br/>
     * "name": "3.FC Uckermark",<br/>
     * "id": 1000<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param fantasyClub FantasyClub with changes that should be made
     * @return 200 OK
     * @throws InputValidationException if values in FantasyClub are invalid
     * @throws DataNotFoundException if id of FantasyClub doesn't exist in DB
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFantasyClub(@HeaderParam("authorization") String token, @Context HttpServletRequest request, FantasyClub fantasyClub) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), fantasyClub);
        FantasyClub oldFC = em.find(FantasyClub.class, fantasyClub.getId());
        if (oldFC != null) {
            if (validator.checkLenght(fantasyClub.getName(), 255, true)
                    && validator.checkLenght(fantasyClub.getActivity(), 255, true)
                    && validator.checkLenght(fantasyClub.getLastLoginWith(), 255, true)) {
                fantasyClub.setCreateDate(oldFC.getCreateDate());
                fantasyClub.setAmountSubstituteBenchSlots(oldFC.getAmountSubstituteBenchSlots());
                fantasyClub.setIdFantasyManager(oldFC.getIdFantasyManager());
                fantasyClub.setLastLogin(oldFC.getLastLogin());
                fantasyClub.setFirstLogin(oldFC.getFirstLogin());
                fantasyClub.setLastQuestionAnswered(oldFC.getLastQuestionAnswered());

                helper.mergeObject(em, fantasyClub);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("FantasyClub at index " + fantasyClub.getId() + " does not exits"), em);
            throw new DataNotFoundException("FantasyClub at index " + fantasyClub.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

}
