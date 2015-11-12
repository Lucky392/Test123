/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsTables;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("/tables")
public class CmsTablesRESTEndpoint {
    
    @InjectParam
    RestHelperClass helper;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTables(@HeaderParam("authorization") String token){
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.TABLE, MethodConstants.SEARCH, token);
        List<CmsTables> tables = em.createNamedQuery("CmsTables.findAll").getResultList();
        return Response.ok().entity(tables).build();
    }
    
}
