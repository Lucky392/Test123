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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.BugReport;
import rs.htec.cms.cms_bulima.domain.MatchPlayerStat;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.BugReportPOJO;
import rs.htec.cms.cms_bulima.pojo.MatchPlayerStatPOJO;

/**
 *
 * @author stefan
 */
@Path("/matchPlayerStat")
public class MatchPlayerStatRESTEndpoint {
    
    RestHelperClass helper;
    
    public MatchPlayerStatRESTEndpoint() {
        helper = new RestHelperClass();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayerStatById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        MatchPlayerStat matchPlayerStat = null;
        try {
            matchPlayerStat = (MatchPlayerStat) em.createNamedQuery("MatchPlayerStat.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("MatchPlayerStat with id " + id + " does not exist..");
        }
        return Response.ok().entity(matchPlayerStat).build();
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPlayerStat(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate, @QueryParam("errorType") String errorType,
            @QueryParam("origin") String origin, @QueryParam("system") String system) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<MatchPlayerStat> matchPlayerStat;
        StringBuilder query = new StringBuilder("SELECT m FROM MatchPlayerStat m");
        
        String operator = " WHERE";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(" WHERE m.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND";
        }
//
//        if (errorType != null) {
//            query.append(operator).append(" b.errorType = '").append(errorType).append("'");
//            operator = " AND";
//        }
//
//        if (origin != null) {
//            query.append(operator).append(" b.origin = '").append(origin).append("'");
//            operator = " AND";
//        }
//        
//        if (system != null) {
//            query.append(operator).append(" b.system = '").append(system).append("'");
//            operator = " AND";
//        }

//        if (search != null) {
//            search = "%" + search + "%";
//            query.append(operator)
//                    .append(" (b.description LIKE '")
//                    .append(search).append("' OR b.clubName LIKE '")
//                    .append(search).append("')");
//        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        matchPlayerStat = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (matchPlayerStat == null || matchPlayerStat.isEmpty()) {
            throw new DataNotFoundException("There is no MatchPlayerStat for this search!");
        }

        List<MatchPlayerStatPOJO> pojos = MatchPlayerStatPOJO.toMatchPlayerStatPOJOList(matchPlayerStat);

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }
}
