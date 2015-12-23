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
import rs.htec.cms.cms_bulima.domain.BugReport;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
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
@Path("/bugReports")
public class BugReportRESTEndpoint {
    
    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Bug report for specified id.
     * <br/>
     * Example for returned BugReport: <br/>
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
     * @param token header parameter for checking permission
     * @param request
     * @param id of BugReport that should be returned
     * @return BugReport
     * @throws DataNotFoundException if BugReport with defined id doesn't exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBugReportById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BUG_REPORT, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        BugReportPOJO pojo;
        try {
            BugReport bugReport = (BugReport) em.createNamedQuery("BugReport.findById").setParameter("id", id).getSingleResult();
            pojo = new BugReportPOJO(bugReport);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Bug report at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Bug report at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

    /**
     * Returns list of Bug reports. <br/>
     * That list can be:<br/>
     * -ordered by specified column<br/>
     * -filtered by erroorType and origin (WEB for example)<br/>
     * -searched through club name and description<br/>
     * -filtered between two dates<br/>
     *[<br/>
     * "count": 2,<br/>
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
     * @param token header parameter for checking permission
     * @param request
     * @param page number of page for searched results
     * @param limit number of bugReports that are returned in body
     * @param orderBy column name (if there is '-' before colum name, results will be sorted in descending order)
     * @param search words in description and clubName
     * @param minDate filters result form defined date
     * @param maxDate filters result to defined date
     * @param errorType filter for errorTupe
     * @param origin filter for origin that have bug
     * @param system of the origin
     * @return list of BugReports
     * @throws DataNotFoundException if there is no result for query
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBugReport(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate, @QueryParam("errorType") String errorType,
            @QueryParam("origin") String origin, @QueryParam("system") String system) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BUG_REPORT, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);

        List<BugReport> bugReport;
        StringBuilder query = new StringBuilder("SELECT b FROM BugReport b");
        
        String operator = " WHERE";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(operator).append(" b.reportDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND";
        }

        if (errorType != null) {
            query.append(operator).append(" b.errorType = '").append(errorType).append("'");
            operator = " AND";
        }

        if (origin != null) {
            query.append(operator).append(" b.origin = '").append(origin).append("'");
            operator = " AND";
        }
        
        if (system != null) {
            query.append(operator).append(" b.system = '").append(system).append("'");
            operator = " AND";
        }

        if (search != null) {
            search = "%" + search + "%";
            query.append(operator)
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
            helper.setResponseToHistory(history, new DataNotFoundException("There is no bug report for this search!"), em);
            throw new DataNotFoundException("There is no bug report for this search!");
        }

        List<BugReportPOJO> pojos = BugReportPOJO.toBugReportPOJOList(bugReport);

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("b", "count(b)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        go.setData(pojos);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * Returns all error types that exist for bugs.
     * <br/>
     * Example:<br/>
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
     * @param request
     * @return list of all error types
     */
    @GET
    @Path("/errorType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getErrorTypes(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BUG_REPORT, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "SELECT distinct b.errorType FROM BugReport b";
        List<String> list = em.createQuery(query).getResultList();
        helper.setResponseToHistory(history, Response.ok().entity(list).build(), em);
        return Response.ok().entity(list).build();
    }

    /**
     * Return all origins that caused bug report.
     * <br/>
     * Example:<br/>
     * [<br/>
     *  "358239056742523",<br/>
     *  "355449052588209",<br/>
     *  "WEB",<br/>
     *  "8DFDAF95-0DDA-4330-BFF3-76DBF139727A",<br/>
     *  "353918058050135"<br/>
     * ]<br/>
     * 
     * @param token header parameter for checking permission
     * @param request
     * @return list of origins
     */
    @GET
    @Path("/errorOrigin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrigins(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BUG_REPORT, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "SELECT distinct b.origin FROM BugReport b";
        List<String> list = em.createQuery(query).getResultList();
        helper.setResponseToHistory(history, Response.ok().entity(list).build(), em);
        return Response.ok().entity(list).build();
    }

    /**
     * Finds Bug report in db by id of BugReport specified in JSON body,
     * and updates it fields with changed attributes. <br/>
     * <br/>
     * Example for json body that should be sent:<br/>
     *{ <br/>
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
     * @param token header parameter for checking permission
     * @param request
     * @param bugReport in body in JSON
     * @return response OK (200) if Bug report is updated
     * @throws DataNotFoundException if BugReport requested for update doesn't exist
     * @throws InputValidationException if BugReport object is not valid
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBugReport(@HeaderParam("authorization") String token, @Context HttpServletRequest request, BugReport bugReport) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.BUG_REPORT, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), bugReport);
        BugReport foundedBugReport = em.find(BugReport.class, bugReport.getId());
        if (foundedBugReport != null) {
            Validator validator = new Validator();
            if (validator.checkLenght(bugReport.getEmail(), 255, false) && validator.checkLenght(bugReport.getErrorType(), 255, false) &&
                    validator.checkLenght(bugReport.getDescription(), 1500, false) && validator.checkLenght(bugReport.getOrigin(), 255, false) && 
                    validator.checkLenght(bugReport.getSystem(), 255, false) && validator.checkLenght(bugReport.getDeviceType(), 255, false) &&
                    validator.checkLenght(bugReport.getTask(), 255, true) && validator.checkLenght(bugReport.getStatus(), 255, true) &&
                    validator.checkLenght(bugReport.getOther(), 500, true) && validator.checkLenght(bugReport.getAppVersion(), 255, true) &&
                    validator.checkLenght(bugReport.getClubName(), 255, true)) {

                helper.mergeObject(em, bugReport);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }

        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Bug report at index " + bugReport.getId() + " does not exits"), em);
            throw new DataNotFoundException("Bug report at index " + bugReport.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }
}
