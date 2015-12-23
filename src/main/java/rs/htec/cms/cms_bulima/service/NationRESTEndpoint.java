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
import rs.htec.cms.cms_bulima.domain.Nation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/nations")
public class NationRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNations(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("name") String name, @Context HttpServletRequest request) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT n FROM Nation n ");
        if (name != null){
            query.append("WHERE n.name LIKE '%")
                    .append(name)
                    .append("%'");
        }
        List<Nation> nations = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (nations.isEmpty()){
            helper.setResponseToHistory(history, new DataNotFoundException("There is no nations!"), em);
            throw new DataNotFoundException("There is no nations!");
        }
        String countQuery = query.toString().replaceFirst("n", "count(n)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(nations);
        Response r = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, r, em);
        return r;
    }

    /**
     * API for method: .../rest/nations/{id} This method return single element
     * of club at index in JSON. Example for JSON response:<br/>{
     * <br/>"createDate": 1388530800000,<br/> "name": "Österreich",<br/> "id": 3
     * <br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of nation we are searching for
     * @return Response 200 OK status with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no nation at index 5!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getNation(@HeaderParam("authorization") String token, @Context HttpServletRequest request,@PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        Nation nation = em.find(Nation.class, id);
        if (nation == null) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no nation at index " + id + "!"), em);
            throw new DataNotFoundException("There is no nation at index " + id + "!");
        }
        helper.setResponseToHistory(history, Response.ok().entity(nation).build(), em);
        return Response.ok().entity(nation).build();
    }
}
