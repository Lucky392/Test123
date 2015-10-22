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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Match;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.MatchPOJO;

/**
 *
 * @author marko
 */
@Path("/match")
public class MatchRESTEndpoint {
    
    RestHelperClass helper;

    public MatchRESTEndpoint() {
        helper = new RestHelperClass();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatch(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("clubID") long clubID, @QueryParam("matchdayID") long matchdayID, @QueryParam("startDate") long startDate){
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT m FROM Match m ");
        if (clubID != 0){
            query.append("WHERE (m.idGuestClub.id = ")
                    .append(clubID)
                    .append(" OR m.idHomeClub.id = ")
                    .append(clubID)
                    .append(")");
        }
        if (matchdayID != 0){
            query.append(clubID != 0 ? " AND" : "WHERE")
                    .append(" m.idMatchday.id = ")
                    .append(matchdayID);
        }
        if (startDate != 0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(startDate);
            query.append(clubID != 0 || matchdayID != 0 ? " AND" : "WHERE")
                    .append(" m.startTime LIKE '%")
                    .append(sdf.format(date))
                    .append("%'");
        }
        System.out.println(query);
        List<Match> matches = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (matches == null || matches.isEmpty()) {
            throw new DataNotFoundException("There is no mathes for this search!");
        }
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(MatchPOJO.toMatchPOJOList(matches));
        return Response.ok().entity(go).build();
    }
}
