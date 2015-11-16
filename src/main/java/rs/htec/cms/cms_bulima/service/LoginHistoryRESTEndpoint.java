/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.sql.Date;
import java.util.Calendar;
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
import rs.htec.cms.cms_bulima.domain.LoginHistory;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.LoginHistoryPOJO;

/**
 *
 * @author marko
 */
@Path("loginHistory")
public class LoginHistoryRESTEndpoint {
// methods for this Enpoint shouldn't be used!
    @InjectParam
    RestHelperClass helper;
    Date date = new Date(1439762400000l); // treba podesiti trenutni datum

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/activeUser/{day}")
    public Response getUserCount(@HeaderParam("authorization") String token, @QueryParam("platform") String platform, @PathParam("day") String day) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        StringBuilder query = new StringBuilder("SELECT count(l) FROM LoginHistory l WHERE ");

        appendPlatformToQuery(query, platform);
        query.append(" l.loginDate BETWEEN '");
        appendDateToQuery(query, day);

        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return Response.ok().entity(cw).build();
    }

    public long getActiveUser(String platform, String day) {
        EntityManager em = helper.getEntityManager();
        StringBuilder query = new StringBuilder("SELECT count(l) FROM LoginHistory l WHERE ");
        appendPlatformToQuery(query, platform);
        query.append(" l.loginDate BETWEEN '");
        appendDateToQuery(query, day);

        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return cw.getCount();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registration/{day}")
    public Response getRegistrationUser(@HeaderParam("authorization") String token, @QueryParam("platform") String platform, @PathParam("day") String day) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        StringBuilder query = new StringBuilder("SELECT count(u) FROM LoginHistory l JOIN l.idUser u WHERE ");

        appendPlatformToQuery(query, platform);
        query.append(" u.createDate BETWEEN '");
        appendDateToQuery(query, day);

        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return Response.ok().entity(cw).build();
    }

    public long getRegistrations(String platform, String day) {
        EntityManager em = helper.getEntityManager();

        StringBuilder query = new StringBuilder("SELECT count(u) FROM LoginHistory l JOIN l.idUser u WHERE ");

        appendPlatformToQuery(query, platform);
        query.append(" u.createDate BETWEEN '");
        appendDateToQuery(query, day);

        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return cw.getCount();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/payingUser/{day}")
    public Response getPayingUser(@HeaderParam("authorization") String token, @QueryParam("platform") String platform, @PathParam("day") String day) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        StringBuilder query = new StringBuilder("SELECT count(u) FROM LoginHistory l JOIN l.idUser u WHERE u.payingUser = 1 AND ");

        appendPlatformToQuery(query, platform);
        query.append(" l.loginDate BETWEEN '");
        appendDateToQuery(query, day);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//        java.util.Date d = new java.util.Date();
//        Date d1 = new Date(d.getTime());

        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return Response.ok().entity(cw).build();
    }

    public long getPayingUsers(String platform, String day) {
        EntityManager em = helper.getEntityManager();

        StringBuilder query = new StringBuilder("SELECT count(u) FROM LoginHistory l JOIN l.idUser u WHERE u.payingUser = 1 AND ");

        appendPlatformToQuery(query, platform);
        query.append(" l.loginDate BETWEEN '");
        appendDateToQuery(query, day);

        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return cw.getCount();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/revenue/{day}")
    public Response getRevenue(@HeaderParam("authorization") String token, @QueryParam("platform") String platform, @PathParam("day") String day) {
        // Treba promeniti upit, verovatno ne vadi dobar obracun
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT SUM(p.directPurchasePrice) FROM UserPremiumItem upi, LoginHistory l JOIN upi.idPremiumItem p WHERE l.idUser=upi.idUser AND ");

        appendPlatformToQuery(query, platform);
        query.append(" upi.updateTimestamp BETWEEN '");
        appendDateToQuery(query, day);
        
        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return Response.ok().entity(cw).build();
    }

    public long getRevenues(String platform, String day) {
        // Treba promeniti upit, verovatno ne vadi dobar obracun
        EntityManager em = helper.getEntityManager();
        //StringBuilder query = new StringBuilder("SELECT SUM(p.directPurchasePrice) FROM UserPremiumItem upi JOIN upi.idUser JOIN upi.idPremiumItem p WHERE ");
        StringBuilder query = new StringBuilder("SELECT SUM(p.directPurchasePrice) FROM UserPremiumItem upi, LoginHistory l JOIN upi.idPremiumItem p WHERE l.idUser=upi.idUser AND ");

        appendPlatformToQuery(query, platform);
        query.append(" upi.updateTimestamp BETWEEN '");
        appendDateToQuery(query, day);
        CountWrapper cw = new CountWrapper((long) em.createQuery(query.toString()).getSingleResult());
        return cw.getCount();
    }

    private void appendDateToQuery(StringBuilder query, String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (day) {
            case "month":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                query.append(new Date(calendar.getTimeInMillis()))
                        .append(" 00:00:00' AND '")
                        .append(date)
                        .append(" 23:59:59'");
                break;
            case "lastMonth":
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                query.append(new Date(calendar.getTimeInMillis()))
                        .append(" 00:00:00' AND '");
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                query.append(new Date(calendar.getTimeInMillis()))
                        .append(" 00:00:00'");
                break;
            case "yesterday":
                date = new Date(1439762400000l - 86400l);
            default:
                query.append(date)
                        .append(" 00:00:00' AND '")
                        .append(date)
                        .append(" 23:59:59'");
        }
    }

    private void appendPlatformToQuery(StringBuilder query, String platform) {
        if (platform != null) {
            query.append("l.platform = '")
                    .append(platform)
                    .append("' AND");
        }
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/dashboard")
//    public Response getDashboard(@HeaderParam("authorization") String token, @DefaultValue("all") @QueryParam("platform") String platform) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
//
//        Dashboard dashboard = new Dashboard();
//        if ("all".equals(platform)) {
//            dashboard.instantiateFullDashboard();
//        } else {
//            if ("total".equals(platform)) {
//                platform = null;
//            }
//            dashboard.instantiateDashboard(platform);
//        }
//        return Response.ok().entity(dashboard).build();
//    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getHistoryById(@HeaderParam("authorization") String token, @PathParam("id") long id){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        LoginHistory history = em.find(LoginHistory.class, id);
        if (history == null) {
            throw new DataNotFoundException("History at index " + id + " does not exist..");
        }
        return Response.ok().entity(new LoginHistoryPOJO(history)).build();
    }
}
