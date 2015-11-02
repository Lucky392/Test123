/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.DashboardPOJO;
import rs.htec.cms.cms_bulima.pojo.DashboardTimePOJO;
import rs.htec.cms.cms_bulima.pojo.PlatformPOJO;

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

        String[] dates = {"today", "yesterday","thisMonth","lastMonth"};
        List<DashboardTimePOJO> result = new ArrayList<>();

        for (String day : dates) {
            DashboardPOJO dashbard = new DashboardPOJO();
            dashbard.setDailyActiveUsers(getActiveUser(day));
            dashbard.setRegistrations(getRegistrations(day));
            dashbard.setPayingUser(getPayingUser(day));
            DashboardTimePOJO pojo = new DashboardTimePOJO(day, dashbard);
            result.add(pojo);
        }
        return Response.ok().entity(result).build();
    }

    private List<PlatformPOJO> getActiveUser(String day) {
        EntityManager em = helper.getEntityManager();

        //#SELECT count(*), platform FROM bulima.LOGIN_HISTORY where createDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00' group by platform;
        StringBuilder query = new StringBuilder("SELECT count(l), l.platform FROM LoginHistory l WHERE ");

        query.append(" l.createDate BETWEEN '");
        appendDateToQuery(query, day);
        query.append(" group by platform");

        List<Object[]> objects = em.createQuery(query.toString()).getResultList();
        
        List<PlatformPOJO> list = getPlatformList(objects);
        return list;
    }
    
    private List<PlatformPOJO> getPlatformList(List<Object[]> objects) {
        List<PlatformPOJO> list = new ArrayList<>(objects.size());
        PlatformPOJO pojo = null;

        long total = 0;
        for (Object[] object : objects) {
            pojo = new PlatformPOJO((String) object[1], (long) object[0]);
            list.add(pojo);
            total += (long) object[0];
        }
        pojo = new PlatformPOJO("TOTAL", total);
        list.add(pojo);

        return list;
    }

    private List<PlatformPOJO> getRegistrations(String day) {
        EntityManager em = helper.getEntityManager();

        //#SELECT count(*), platform FROM bulima.USER u right join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where u.createDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00'
        //#group by platform;
        StringBuilder query = new StringBuilder("SELECT count(l), l.platform FROM LoginHistory l JOIN l.idUser u WHERE ");

        query.append(" u.createDate BETWEEN '");
        appendDateToQuery(query, day);
        query.append(" group by platform");

        List<Object[]> objects = em.createQuery(query.toString()).getResultList();
        List<PlatformPOJO> list = getPlatformList(objects);
        return list;
    }

    private List<PlatformPOJO> getPayingUser(String day) {
        EntityManager em = helper.getEntityManager();

        //#SELECT count(*), platform FROM bulima.USER u right join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where u.payingUser = 1 and lh.loginDate between '2015-07-21 00:00:01' AND '2015-07-22 00:00:00'
        //#group by platform;
        StringBuilder query = new StringBuilder("SELECT count(l), l.platform FROM LoginHistory l JOIN l.idUser u WHERE u.payingUser = 1 AND ");

        query.append(" l.loginDate BETWEEN '");
        appendDateToQuery(query, day);
        query.append(" group by platform");

        List<Object[]> objects = em.createQuery(query.toString()).getResultList();
        List<PlatformPOJO> list = getPlatformList(objects);
        return list;
    }

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
