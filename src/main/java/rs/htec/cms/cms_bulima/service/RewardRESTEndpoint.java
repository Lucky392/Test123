/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import rs.htec.cms.cms_bulima.domain.Reward;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.RewardPOJO;

/**
 *
 * @author stefan
 */
@Path("/rewards")
public class RewardRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Reward for defined id. Example for response: { <br/>
     * "createDate": 1424078743000,<br/>
     * "imageUrl": null,<br/>
     * "amountPremiumCurrency": 0,<br/>
     * "idPremiumItem": 4,<br/>
     * "updateAt": null,<br/>
     * "amountPremiumItem": 1,<br/>
     * "amountIngameCurrency": 0,<br/>
     * "chance": 25,<br/>
     * "description": null,<br/>
     * "name": "1x Scout",<br/>
     * "id": 3<br/>
     * }<br/>
     *
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param id - id of Reward that should be returned
     * @return Reward in JSON for defined id
     * @throws DataNotFoundException if there is no Reward with id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRewardById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        RewardPOJO pojo;
        try {
            Reward reward = (Reward) em.createNamedQuery("Reward.findById").setParameter("id", id).getSingleResult();
            pojo = new RewardPOJO(reward);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Reward at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Reward at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(pojo).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method:
     * .../rest/rewards?page=VALUE&limit=VALUE&search=VALUE&minDate=VALUE&maxDate=VALUE&idPremiumItem=VALUE
     * This method returns JSON list and count number. Default value for page is
     * 1, and for limit is 10.
     *
     * @param token a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for target calculation
     * @param limit number of Rewards method returns
     * @param orderBy specified column
     * @param idPremiumItem filters results based on id premium item
     * @param minDate a start date for filtering time in millis
     * @param maxDate a end date for filtering time in millis
     * @return Response 200 OK with JSON body
     * @throw DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no Rewards for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRewards(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy,
            @QueryParam("idPremiumItem") String idPremiumItem, @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.MATCHDAY, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);

        List<Reward> rewards;
        StringBuilder query = new StringBuilder("SELECT m FROM Reward m");
        String operator = " WHERE";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(" WHERE m.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND";
        }

        if (idPremiumItem != null) {
            query.append(operator).append(" m.idPremiumItem = '").append(idPremiumItem).append("'");
        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        rewards = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (rewards == null || rewards.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no Reward for this search!"), em);
            throw new DataNotFoundException("There is no Reward for this search!");
        }

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        List<RewardPOJO> pojos = RewardPOJO.toRewardPOJOList(rewards);
        go.setData(pojos);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

}
