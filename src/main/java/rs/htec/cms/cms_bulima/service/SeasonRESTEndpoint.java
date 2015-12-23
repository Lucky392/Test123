/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.Season;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.SeasonPOJO;

/**
 *
 * @author stefan
 */
@Path("/seasons")
public class SeasonRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Season for specified id.
     *
     * { <br/>
     * "createDate": 1420066800000,<br/>
     * "idLeague": 1,<br/>
     * "idSport1Season": "355",<br/>
     * "idFirstMatchday": null,<br/>
     * "urlToFirstMatchday": null,<br/>
     * "urlToLeague":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/leagues/1",<br/>
     * "name": "2014/2015",<br/>
     * "id": 1<br/>
     * }<br/>
     *
     * @param token- header parameter for checking permission
     * @param request
     * @param id - for Season
     * @return 200 OK and Season in JSON
     * @throws DataNotFoundException if there is no Season for id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeasonById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        SeasonPOJO pojo;
        try {
            Season season = (Season) em.createNamedQuery("Season.findById").setParameter("id", id).getSingleResult();
            pojo = new SeasonPOJO(season);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Season at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Season at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(pojo).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * Returns all seasons. <br>[<br>{<br>
     * "urlToFirstMatchday": null,<br>
     * "createDate": 1420066800000,<br>
     * "leagueName": "12",<br>
     * "idLeague": 1,<br>
     * "idSport1Season": "355",<br>
     * "idFirstMatchday": null,<br>
     * "urlToLeague":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/leagues/1",<br>
     * "firstMatchdayName": null,<br>
     * "name": "2014/2015",<br>
     * "id": 1<br>
     * }]<br>
     *
     * @param token
     * @param request
     * @return Season List
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeasons(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<Season> seasons = em.createNamedQuery("Season.findAll").getResultList();
        if (seasons.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no seasons!"), em);
            throw new DataNotFoundException("There is no seasons!");
        }
        Response response = Response.ok().entity(SeasonPOJO.toSeasonPOJOList(seasons)).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
