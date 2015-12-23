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
import rs.htec.cms.cms_bulima.domain.BatchjobHistory;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("batchjobHistories")
public class BatchjobHistoryRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns single BatchjobHistory for defined id. 
     * <br/> api: rest/batchjobHistories/{id}
     * <br/> Example for
     * response:
     * <br/>
     * {<br/>
     * "createDate": 1437383700000,<br/>
     * "jobName": "FinishAuctionsBatchJob",<br/>
     * "startDate": 1437383700000,<br/>
     * "endDate": 1437383700000,<br/>
     * "timeNeeded": 110,<br/>
     * "returnValue": "OK",<br/>
     * "errorStack": "",<br/>
     * "id": 5<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param id for BatchjobHistory that should be returned
     * @return BatchjobHistory status 200 OK
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBatchjobHistoryById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token);
        BatchjobHistory history;
        try {
            history = (BatchjobHistory) em.createNamedQuery("BatchjobHistory.findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            throw new DataNotFoundException("BatchjobStep at index " + id + " does not exist..");
        }
        return Response.ok().entity(history).build();
    }

    /**
     * API for method:
     * .../rest/batchjobHistories?page=VALUE&limit=VALUE&search=VALUE&startDate=VALUE&endDate=VALUE&returnValue=VALUE
     * This method returns JSON list and count number. Default value for page is
     * 1, and for limit is 10. There is a possibility for search by jobName,
     * startDate and endDate. Filtering by returnValue. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2 limit:
     * <br/>{ <br/>"data": [ {<br/>
     * "createDate": 1437383400000,<br/> "jobName": "UpdateScoutBatchJob",<br/>
     * "startDate": 1437383400000,<br/> "endDate": 1437383400000,<br/>
     * "timeNeeded": 105,<br/>
     * "returnValue": "OK",<br/> "errorStack": "",<br/> "id": 1 <br/>},<br/>
     * {<br/> "createDate": 1437383400000,<br/> "jobName":
     * "FinishAuctionsBatchJob",<br/> "startDate": 1437383400000,<br/>
     * "endDate": 1437383400000,<br/> "timeNeeded": 108,<br/>
     * "returnValue": "OK",<br/> "errorStack": "",<br/> "id": 2 <br/>} ],<br/>
     * "count": 23766 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for Batchjob history
     * @param limit number of Batchjob history returns
     * @param search word for searching jobName
     * @param startDate date in timestamp
     * @param endDate date in timestamp
     * @param returnValue string of return value
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no batch job history for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory(@HeaderParam("authorization") String token, @Context HttpServletRequest request,@DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search,
            @QueryParam("startDate") long startDate, @QueryParam("endDate") long endDate, @QueryParam("returnValue") String returnValue) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT bh FROM BatchjobHistory bh ");
        if (startDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = new Date(startDate);
            String date = "%" + sdf.format(d1) + "%";
            query.append("WHERE bh.startDate LIKE '")
                    .append(date).append("'");
        }
        if (endDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d2 = new Date(endDate);
            String date = "%" + sdf.format(d2) + "%";
            query.append(startDate != 0 ? " AND" : "WHERE")
                    .append(" bh.endDate LIKE '")
                    .append(date)
                    .append("'");
        }
        if (search != null) {
            search = "%" + search + "%";
            query.append(startDate != 0 || endDate != 0 ? " AND" : "WHERE")
                    .append(" bh.jobName LIKE '")
                    .append(search)
                    .append("'");
        }
        if (returnValue != null) {
            query.append(startDate != 0 || endDate != 0 || search != null ? " AND" : "WHERE")
                    .append(" bh.returnValue = '")
                    .append(returnValue.toUpperCase())
                    .append("'");
        }
        List<BatchjobHistory> bhistory = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (bhistory == null || bhistory.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no batch job history for this search!"), em);
            throw new DataNotFoundException("There is no batch job history for this search!");
        }
        String countQuery = query.toString().replaceFirst("bh", "count(bh)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(bhistory);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * API for method: .../rest/batchjobHistories/returnValues This method
     * return list of returnValues in JSON. Example for JSON:<br/> [<br/>
     * "OK",<br/> "FAILED"<br/> ]
     *
     * @param token
     * @param request
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no returnValues!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/returnValues")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReturnValues(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BATCHJOB, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "SELECT distinct bh.returnValue FROM BatchjobHistory bh";
        List<String> returnValues = em.createQuery(query).getResultList();
        if (returnValues.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no returnValues!"), em);
            throw new DataNotFoundException("There is no returnValues!");
        }
        helper.setResponseToHistory(history, Response.ok().entity(returnValues).build(), em);
        return Response.ok().entity(returnValues).build();
    }
}
