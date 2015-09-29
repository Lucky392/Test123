/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("loginHistory")
public class LoginHistoryRESTEndpoint {
    
    RestHelperClass helper;
    Date date = new Date(1439762400000l);

    public LoginHistoryRESTEndpoint() {
        helper = new RestHelperClass();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    java.util.Date d = new java.util.Date();
    Date d1 = new Date(d.getTime());
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public Response getUserCount(@HeaderParam("authorization") String token, @QueryParam("platform") String platform){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        
        StringBuilder query = new StringBuilder("SELECT count(l) FROM LoginHistory l WHERE ");
        if (platform != null){
            query.append("l.platform = '")
                    .append(platform)
                    .append("' AND");
        }
        query.append(" l.loginDate BETWEEN '")
                .append(date)
                .append(" 00:00:00' AND '")
                .append(date)
                .append(" 23:59:59'");
//        System.out.println(sdf.format(d1));
        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return Response.ok().entity(cw).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registration")
    public Response getPlayinUser(@HeaderParam("authorization") String token, @QueryParam("platform") String platform){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        
        StringBuilder query = new StringBuilder("SELECT count(u) FROM LoginHistory l JOIN l.idUser u WHERE ");
        if (platform != null){
            query.append("l.platform = '")
                    .append(platform)
                    .append("' AND");
        }
        query.append(" u.createDate BETWEEN '")
                .append(date)
                .append(" 00:00:00' AND '")
                .append(date)
                .append(" 23:59:59'");
        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return Response.ok().entity(cw).build();
    }
}
