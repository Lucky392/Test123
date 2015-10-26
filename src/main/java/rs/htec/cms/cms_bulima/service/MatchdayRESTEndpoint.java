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
import javax.persistence.NoResultException;
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
import rs.htec.cms.cms_bulima.domain.Matchday;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchdayPOJO;

/**
 *
 * @author stefan
 */
@Path("/matchday")
public class MatchdayRESTEndpoint {

    RestHelperClass helper;

    public MatchdayRESTEndpoint() {
        helper = new RestHelperClass();
    }

    /**
     * Returns Matchday for specified id.
     * <br/>
     * Example for Matchday response:<br/>
     *
     * {<br/>
     * "createDate": 1437385012000,<br/>
     * "startDate": 1439569800000,<br/>
     * "endDate": 1439835300000,<br/>
     * "idSport1Matchday": "3",<br/>
     * "matchday": 3,<br/>
     * "isCurrent": 1,<br/>
     * "isCalculated": 0,<br/>
     * "isCompleted": 0,<br/>
     * "idSeason": 4,<br/>
     * "urlToSeason":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/season/4",<br/>
     * "id": 3<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param id - of Matchday that should be returned
     * @return Matchday for defined id
     * @throws DataNotFoundException if Matchday does not exist for
     * search
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchDayById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        MatchdayPOJO pojo;
        try {
            Matchday matchday = (Matchday) em.createNamedQuery("Matchday.findById").setParameter("id", id).getSingleResult();
            pojo = new MatchdayPOJO(matchday);
        } catch (NoResultException e) {
            throw new DataNotFoundException("Matchday at index " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }

    /**
     * Returns list of Matchday objects. That list can be:<br/>
     * -ordered by specified column<br/>
     * -filtered by idSeason and matchday (for matchday ID should be provided)<br/>
     * -filtered between two dates<br/>
     *
     * {<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1437385012000,<br/>
     * "startDate": 1437762600000,<br/>
     * "endDate": 1438020900000,<br/>
     * "idSport1Matchday": "1",<br/>
     * "matchday": 1,<br/>
     * "isCurrent": 0,<br/>
     * "isCalculated": 1,<br/>
     * "isCompleted": 1,<br/>
     * "idSeason": 4,<br/>
     * "urlToSeason":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/season/4",<br/>
     * "id": 1<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1437385012000,<br/>
     * "startDate": 1438360200000,<br/>
     * "endDate": 1438625700000,<br/>
     * "idSport1Matchday": "2",<br/>
     * "matchday": 2,<br/>
     * "isCurrent": 0,<br/>
     * "isCalculated": 1,<br/>
     * "isCompleted": 1,<br/>
     * "idSeason": 4,<br/>
     * "urlToSeason":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/season/4",<br/>
     * "id": 2<br/>
     * }<br/>
     * ],<br/>
     * "count": 2<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param page - number of page for searched results
     * @param limit - number of matchPlayerStats that are returned in body
     * @param orderBy - column name (if there is '-' before colum name, results will be sorted in descending order)
     * @param matchday - filters results base on defined matchday
     * @param idSeason - filters results base on defined id for Season
     * @param minDate - filters result form defined date
     * @param maxDate - filters result to defined date
     * @return Matchdays for specified search
     * @throws DataNotFoundException if Matchday does not exist for
     * search
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBugReport(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("orderBy") String orderBy, @QueryParam("matchday") String matchday,
            @QueryParam("idSeason") String idSeason, @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<Matchday> matchdays;
        StringBuilder query = new StringBuilder("SELECT m FROM Matchday m");
        String operator = " WHERE";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(" WHERE m.startDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND";
        }

        if (matchday != null) {
            query.append(operator).append(" m.matchday = '").append(matchday).append("'");
            operator = " AND";
        }

        if (idSeason != null) {
            query.append(operator).append(" m.idSeason = '").append(idSeason).append("'");
        }

        if (orderBy != null) {
            if (orderBy.startsWith("-")) {
                orderBy = orderBy.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderBy);
        }

        matchdays = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (matchdays == null || matchdays.isEmpty()) {
            throw new DataNotFoundException("There is no Matchday for this search!");
        }

        GetObject go = new GetObject();
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        go.setCount(count);
        List<MatchdayPOJO> pojos = MatchdayPOJO.toMatchdayPOJOList(matchdays);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }
}