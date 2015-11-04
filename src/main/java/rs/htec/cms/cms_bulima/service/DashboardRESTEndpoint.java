/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/dashboard")
public class DashboardRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    Date date = new Date(1439762400000l); // treba podesiti trenutni datum

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getDashboard(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        String[] dates = {"today", "yesterday", "thisMonth", "lastMonth"};


        HashMap result = getDashboardFormDB("lastMonth");
        //HashMap result2 = getDashboardFormDB("yesterday");
        return Response.ok().entity(result).build();
    }

    private HashMap getDashboardFormDB(String day) {
        //SELECT sum(u.createDate LIKE ('2015-07-21%')) AS registration,sum(u.payingUser=1) AS paying,count(*) AS total, platform FROM bulima.USER u right join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where lh.createDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00'
        //group by platform;
        EntityManager em = helper.getEntityManager();
        String date2 = date.toString();
        System.out.println("Date" + date);
        switch(day) {
            case "yesterday":
                date = new Date(1439762400000l - 86400l);
                date2 = date.toString();
                break;
            case "thisMonth":
                date2 = date2.substring(0, 7);
                break;
            case "lastMonth":
                date2 = date2.substring(0, 7);
                int month = Integer.parseInt(date2.substring(date2.lastIndexOf('-')+1));
                date2 = date2.replaceFirst("-"+month, "-"+(month-1));
                System.out.println(date2);
                break;
        }
        
        StringBuilder query = new StringBuilder("SELECT platform, sum(u.createDate LIKE ('").append(date2).append("%')), sum(u.payingUser=1), count(*) FROM bulima.USER u right join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where ");

        query.append(" lh.createDate BETWEEN '");
        appendDateToQuery(query, day);
        query.append(" group by platform");

        List<Object[]> list = em.createNativeQuery(query.toString()).getResultList();
        HashMap results = toMap(list);
        return results;
    }

    private HashMap toMap(List<Object[]> list) {
        HashMap hm = new HashMap();
        long activeUsers = 0;
        long registrations = 0;
        long payingUsers = 0;
        for (Object[] objects : list) {
            HashMap hm2 = new HashMap();
            hm2.put("activeUsers", objects[3]);
            hm2.put("registrations", objects[1]);
            hm2.put("payingUsers", objects[2]);
            hm.put(objects[0], hm2);
            
            activeUsers+=(((BigInteger) objects[3]).longValue());
            registrations+=(((BigDecimal) objects[1]).longValue());
            payingUsers+=(((BigDecimal) objects[2]).longValue());
            
//            hm.put("revenue", new Double(3434.34));
        }
  
        HashMap hm2 = new HashMap();
        hm2.put("activeUsers", activeUsers);
        hm2.put("registrations", registrations);
        hm2.put("payingUsers", payingUsers);
        hm.put("TOTAL", hm2);
        return hm;
    }

//    private List<PlatformPOJO> getActiveUser(String day) {
//        EntityManager em = helper.getEntityManager();
//
//        //#SELECT count(*), platform FROM bulima.LOGIN_HISTORY where createDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00' group by platform;
//        StringBuilder query = new StringBuilder("SELECT count(l), l.platform FROM LoginHistory l WHERE ");
//
//        query.append(" l.createDate BETWEEN '");
//        appendDateToQuery(query, day);
//        query.append(" group by platform");
//
//        List<Object[]> objects = em.createQuery(query.toString()).getResultList();
//        
//        List<PlatformPOJO> list = getPlatformList(objects);
//        return list;
//    }
//    
//    private List<PlatformPOJO> getPlatformList(List<Object[]> objects) {
//        List<PlatformPOJO> list = new ArrayList<>(objects.size());
//        PlatformPOJO pojo = null;
//
//        long total = 0;
//        for (Object[] object : objects) {
//            pojo = new PlatformPOJO((String) object[1], (long) object[0]);
//            list.add(pojo);
//            total += (long) object[0];
//        }
//        pojo = new PlatformPOJO("TOTAL", total);
//        list.add(pojo);
//
//        return list;
//    }
//
//    private List<PlatformPOJO> getRegistrations(String day) {
//        EntityManager em = helper.getEntityManager();
//
//        //#SELECT count(*), platform FROM bulima.USER u right join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where u.createDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00'
//        //#group by platform;
//        StringBuilder query = new StringBuilder("SELECT count(l), l.platform FROM LoginHistory l JOIN l.idUser u WHERE ");
//
//        query.append(" u.createDate BETWEEN '");
//        appendDateToQuery(query, day);
//        query.append(" group by platform");
//
//        List<Object[]> objects = em.createQuery(query.toString()).getResultList();
//        List<PlatformPOJO> list = getPlatformList(objects);
//        return list;
//    }
//
//    private List<PlatformPOJO> getPayingUser(String day) {
//        EntityManager em = helper.getEntityManager();
//
//        //#SELECT count(*), platform FROM bulima.USER u right join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where u.payingUser = 1 and lh.loginDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00'
//        //#group by platform;
//        StringBuilder query = new StringBuilder("SELECT count(l), l.platform FROM LoginHistory l JOIN l.idUser u WHERE u.payingUser = 1 AND ");
//
//        query.append(" l.loginDate BETWEEN '");
//        appendDateToQuery(query, day);
//        query.append(" group by platform");
//
//        List<Object[]> objects = em.createQuery(query.toString()).getResultList();
//        List<PlatformPOJO> list = getPlatformList(objects);
//        return list;
//    }
    private void appendDateToQuery(StringBuilder query, String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (day) {
            case "thisMonth":
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
}
