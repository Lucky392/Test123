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
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeResultTableElement;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.MatchdayChallengeResultTableElementPOJO;

/**
 *
 * @author stefan
 */
@Path("/matchdayChallengeResultTableElements")
public class MatchdayChallengeResultTableElementRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns MatchdayChallengeResultTableElement for defined id. Example for
     * JSON response:<br/>
     * {<br/>
     * "idMatchdayChallenge": 6,<br/>
     * "elementPosition": 2,<br/>
     * "elementTable": "",<br/>
     * "elementField": "player_firstName;player_lastName",<br/>
     * "urlToMatchdayChallenge":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchday_challenges/6",<br/>
     * "elementName": "Spieler",<br/>
     * "elementType": "",<br/>
     * "id": 17<br/>
     * }<br/>
     *
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id of MatchdayChallengeResultTableElement that should be
     * returned
     * @return MatchdayChallengeResultTableElement
     * @throws DataNotFoundException if there is no
     * MatchdayChallengeResultTableElement for id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchdayChallengeResultTableElementById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        MatchdayChallengeResultTableElementPOJO pojo;
        try {
            MatchdayChallengeResultTableElement element = (MatchdayChallengeResultTableElement) em.createNamedQuery("MatchdayChallengeResultTableElement.findById").setParameter("id", id).getSingleResult();
            pojo = new MatchdayChallengeResultTableElementPOJO(element);
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("MatchdayChallengeResultTableElement at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("MatchdayChallengeResultTableElement at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

    /**
     * Returns MatchdayChallengeResultTableElement list
     *
     * @param token header parameter for checking permission
     * @param request
     * @param page number of page for searched results
     * @param limit number of matchPlayerStats that are returned in body
     * @param orderBy column name (if there is '-' before colum name, results
     * will be sorted in descending order)
     * @param elementName filter for elementName (for example in URL:
     * elementName=Spieler)
     * @param idMatchdayChallenge filter for MatchdayChallenge by id
     * @return status 200 OK and list of MatchdayChallengeResultTableElement
     * @throws DataNotFoundException if there is no result for search
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchdayChallengeResultTableElement(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy,
            @QueryParam("elementName") String elementName, @QueryParam("idMatchdayChallenge") String idMatchdayChallenge) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);

        List<MatchdayChallengeResultTableElement> element;
        StringBuilder query = new StringBuilder("SELECT m FROM MatchdayChallengeResultTableElement m");

        String operator = " WHERE";

        if (elementName != null) {
            query.append(operator).append(" m.elementName = '").append(elementName).append("'");
            operator = " AND";
        }

        if (idMatchdayChallenge != null) {
            query.append(operator).append(" m.idMatchdayChallenge = '").append(idMatchdayChallenge).append("'");
        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        element = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (element == null || element.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no MatchdayChallengeResultTableElement for this search!"), em);
            throw new DataNotFoundException("There is no MatchdayChallengeResultTableElement for this search!");
        }

        List<MatchdayChallengeResultTableElementPOJO> pojos = MatchdayChallengeResultTableElementPOJO.toMatchdayChallengeResultTableElementPOJOList(element);

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        go.setData(pojos);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * Finds MatchdayChallengeResultTableElement in db by id of
     * MatchdayChallengeResultTableElement specified in JSON body, and updates
     * it fields with changed attributes. <br/>
     * Example for json body that should be sent:<br/>
     * {<br/>
     * "idMatchdayChallenge": 2,<br/>
     * "elementPosition": 1,<br/>
     * "elementTable": "",<br/>
     * "elementField": "ID_PLAYER_POSITION",<br/>
     * "urlToMatchdayChallenge":
     * "elementName": "Pos.",<br/>
     * "elementType": "",<br/>
     * "id": 4<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param matchdayChallengeResultTableElement
     * MatchdayChallengeResultTableElement in body in JSON
     * @return response OK (200) if MatchdayChallengeResultTableElement is
     * updated
     * @throws DataNotFoundException if MatchdayChallengeResultTableElement
     * requested for update doesn't exist
     * @throws InputValidationException if MatchdayChallengeResultTableElement
     * object is not valid
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMatchdayChallengeResultTableElement(@HeaderParam("authorization") String token, @Context HttpServletRequest request, MatchdayChallengeResultTableElement matchdayChallengeResultTableElement) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), matchdayChallengeResultTableElement);
        MatchdayChallengeResultTableElement foundedElement = em.find(MatchdayChallengeResultTableElement.class, matchdayChallengeResultTableElement.getId());
        if (foundedElement != null) {
            Validator validator = new Validator();
            if (validator.checkLenght(matchdayChallengeResultTableElement.getElementName(), 255, false) && validator.checkLenght(matchdayChallengeResultTableElement.getElementTable(), 255, false)
                    && validator.checkLenght(matchdayChallengeResultTableElement.getElementType(), 255, false) && validator.checkLenght(matchdayChallengeResultTableElement.getElementField(), 255, false)) {

                helper.mergeObject(em, matchdayChallengeResultTableElement);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }

        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("MatchdayChallengeResultTableElement at index " + matchdayChallengeResultTableElement.getId() + " does not exits"), em);
            throw new DataNotFoundException("MatchdayChallengeResultTableElement at index " + matchdayChallengeResultTableElement.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

}
