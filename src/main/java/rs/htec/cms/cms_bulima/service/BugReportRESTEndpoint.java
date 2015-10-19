/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.BugReport;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.BugReportPOJO;

/**
 *
 * @author stefan
 *
 * Provides methods for edit, view, search and filter.
 *
 */
@Path("/bugReport")
public class BugReportRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public BugReportRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * Returns Bug report for specified id.
     * 
     * Example for returned BugReport 
     * {<br/>
     * "urlToUser": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/user/24076",<br/>
     * "errorType": "Others",<br/>
     * "email": "christest1@stryking.com",<br/>
     * "system": "iOS 8.4.1",<br/>
     * "deviceType": "iPhone 6 Plus",<br/>
     * "other": "",<br/>
     * "reportDate": 968577300000,<br/>
     * "appVersion": "3.4",<br/>
     * "clubName": "VfR Atletico Schrotkorn 1943",<br/>
     * "userId": 24076,<br/>
     * "origin": "8DFDAF95-0DDA-4330-BFF3-76DBF139727A",<br/>
     * "task": null,<br/>
     * "description": "Die manager ist foll toll!",<br/>
     * "status": null,<br/>
     * "id": 7<br/>
     *}<br/>
     * 
     * 
     * @param token
     * @param id
     * @return BugReportPojo
     * @throws DataNotFoundException if Bug report with defined id doesn't exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBugReportById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        BugReportPOJO pojo;
        try {
            BugReport bugReport = (BugReport) em.createNamedQuery("BugReport.findById").setParameter("id", id).getSingleResult();
            pojo = new BugReportPOJO(bugReport);
        } catch (Exception e) {
            throw new DataNotFoundException("Bug report at index " + id + " does not exist.." + e.getMessage());
        }
        return Response.ok().entity(pojo).build();
    }

    /**
     * Returns list of Bug reports.
     * That list can be:
     * -ordered by specified column
     * -filtered by erroorType and origin (WEB for example)
     * -searched through club name and description
     * -filtered between two dates
     *[<br/>
     * "count": 10,<br/>
     * "data": [ <br/>
     * {<br/>
     * "urlToUser": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/user/24076",<br/>
     * "errorType": "Others",<br/>
     * "email": "christest1@stryking.com",<br/>
     * "system": "iOS 8.4.1",<br/>
     * "deviceType": "iPhone 6 Plus",<br/>
     * "other": "",<br/>
     * "reportDate": 968577300000,<br/>
     * "appVersion": "3.4",<br/>
     * "clubName": "VfR Atletico Schrotkorn 1943",<br/>
     * "userId": 24076,<br/>
     * "origin": "8DFDAF95-0DDA-4330-BFF3-76DBF139727A",<br/>
     * "task": null,<br/>
     * "description": "Die manager ist foll toll!",<br/>
     * "status": null,<br/>
     * "id": 7<br/>
     *},<br/>
     *{<br/>
     * "urlToUser": null,<br/>
     * "errorType": "Registration",<br/>
     * "email": "arne.boehm+1@stryking.com",<br/>
     * "system": "4.3",<br/>
     * "deviceType": "Samsung Galaxy S3",<br/>
     * "other": "",<br/>
     * "reportDate": 1441367128000,<br/>
     * "appVersion": "3.1.2",<br/>
     * "clubName": null,<br/>
     * "userId": null,<br/>
     * "origin": "355449052588209",<br/>
     * "task": null,<br/>
     * "description": "hallo test ",<br/>
     * "status": null,<br/>
     * "id": 2<br/>
     *}<br/>
     *]<br/>
     *}<br/>
     * 
     * 
     * @param token
     * @param page
     * @param limit
     * @param orderBy defined column, if there is '-' before column it orders it in descending
     * @param search words in description and clubName
     * @param minDate
     * @param maxDate
     * @param errorType filter for errorTupe
     * @param origin filter for origin that have bug
     * @return list of Bug reports
     * @throws DataNotFoundException if there is no result for query
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBugReport(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate, @QueryParam("errorType") String errorType, @QueryParam("origin") String origin) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<BugReport> bugReport;
        StringBuilder query = new StringBuilder("SELECT b FROM BugReport b ");

        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append("WHERE b.reportDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
        }

        if (errorType != null) {
            query.append(minDate != 0 ? " AND" : "WHERE").append(" b.errorType = '").append(errorType).append("'");
        }

        if (origin != null) {
            query.append(minDate != 0 || errorType != null ? " AND" : "WHERE").append(" b.origin = '").append(origin).append("'");
        }

        if (search != null) {
            search = "%" + search + "%";
            query.append(minDate != 0 || errorType != null || origin != null ? " AND" : "WHERE")
                    .append(" (b.description LIKE '")
                    .append(search).append("' OR b.clubName LIKE '")
                    .append(search).append("')");
        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        bugReport = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (bugReport == null || bugReport.isEmpty()) {
            throw new DataNotFoundException("There is no bug report for this search!");
        }

        List<BugReportPOJO> pojos = BugReportPOJO.toBugReportPOJOList(bugReport);

        GetObject go = new GetObject();
        go.setCount(10l);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }

    /**
     * Returns all error types that exist for bugs.
     * 
     * Example:
     * [<br/>
     * "Shop",<br/>
     *  "Registration",<br/>
     *  "Others",<br/>
     *  "Settings",<br/>
     *  "Reporting and Statistics",<br/>
     *  "Transfermarket",<br/>
     *  "Profile",<br/>
     *  "Aufstellung",<br/>
     *  "Matchday Challenge",<br/>
     *  "Line-up",<br/>
     *  "Sonstiges",<br/>
     *  "Auswertungen und Statistiken",<br/>
     *  "Registrierung"<br/>
     *]<br/>
     * 
     * @param token
     * @return list of all error types
     */
    @GET
    @Path("/errorType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getErrorType(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        String query = "SELECT distinct b.errorType FROM BugReport b";
        List<String> list = em.createQuery(query).getResultList();
        return Response.ok().entity(list).build();
    }

    /**
     * Return all origins that caused bug report.
     * Example:
     * [<br/>
     *  "358239056742523",<br/>
     *  "355449052588209",<br/>
     *  "WEB",<br/>
     *  "8DFDAF95-0DDA-4330-BFF3-76DBF139727A",<br/>
     *  "353918058050135"<br/>
     * ]<br/>
     * 
     * @param token
     * @return list of origins
     */
    @GET
    @Path("/errorOrigin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrigin(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        String query = "SELECT distinct b.origin FROM BugReport b";
        List<String> list = em.createQuery(query).getResultList();
        return Response.ok().entity(list).build();
    }

    /**
     * Finds Bug report in db by id of BugReport specified in json body,
     * and updates it fields with changed attributes.
     * 
     * Example for json body that should be sent:
     *{ <br/>
     * "urlToUser": "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/user/24076",<br/>
     * "errorType": "Others",<br/>
     * "email": "christest1@stryking.com",<br/>
     * "system": "iOS 8.4.1",<br/>
     * "deviceType": "iPhone 6 Plus",<br/>
     * "other": "",<br/>
     * "reportDate": 968577300000,<br/>
     * "appVersion": "3.4",<br/>
     * "clubName": "VfR Atletico Schrotkorn 1943",<br/>
     * "userId": 24076,<br/>
     * "origin": "8DFDAF95-0DDA-4330-BFF3-76DBF139727A",<br/>
     * "task": null,<br/>
     * "description": "Die manager ist foll toll!",<br/>
     * "status": null,<br/>
     * "id": 7<br/>
     *}<br/>
     * 
     * @param token
     * @param bugReport
     * @return response OK (200) if Bug report is updated
     * @throws DataNotFoundException if Bug report requested for update doesn't exist
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBugReport(@HeaderParam("authorization") String token, BugReport bugReport) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token);
        BugReport foundedBugReport = em.find(BugReport.class, bugReport.getId());
        if (foundedBugReport != null) {

            helper.mergeObject(em, bugReport);

        } else {
            throw new DataNotFoundException("Bug report at index " + bugReport.getId() + " does not exits");
        }
        return Response.ok().build();
    }
}