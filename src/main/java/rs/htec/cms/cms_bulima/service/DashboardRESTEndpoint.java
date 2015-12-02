/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    private final String[] dates = {"today", "yesterday", "thisMonth", "lastMonth"};

    @InjectParam
    RestHelperClass helper;
    Date date = new Date(1439762400000l); // treba podesiti trenutni datum

    /**
     * Returns data for Dashboard. Data are summarized by platform [Android,
     * IOS, Web and Total] for time periods: today, yesterday, current month and last month.
     * <br/>
     * <br/>
     * Example for JSON response:<br/>
     * <br/>
     * {<br/>
     * "TOTAL": {<br/>
     * "revenue": 1295.88,<br/>
     * "registrations": 7090,<br/>
     * "payingUsers": 4254,<br/>
     * "activeUsers": 63789<br/>
     * },<br/>
     * "WEB": {<br/>
     * "revenue": 148.88,<br/>
     * "registrations": 1076,<br/>
     * "payingUsers": 1260,<br/>
     * "activeUsers": 16349<br/>
     * },<br/>
     * "ANDROID": {<br/>
     * "revenue": 321.51,<br/>
     * "registrations": 4900,<br/>
     * "payingUsers": 1919,<br/>
     * "activeUsers": 30626<br/>
     * },<br/>
     * "IOS": {<br/>
     * "revenue": 825.49,<br/>
     * "registrations": 1114,<br/>
     * "payingUsers": 1075,<br/>
     * "activeUsers": 16814<br/>
     * }<br/>
     * }<br/>
     *
     *
     * @param token header parameter for checking permission
     * @param day
     * @return 200 OK
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{day}")
    public Response getDashboard(@HeaderParam("authorization") String token, @PathParam("day") String day) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
//
//        HashMap result = new HashMap();
//
//        for (String d : dates) {
//            result.put(d, getDashboardFormDB(d));
//        }
        return Response.ok().entity(getDashboardFormDB(day)).build();
    }

    private HashMap getDashboardFormDB(String day) {
        EntityManager em = helper.getEntityManager();
        String date2 = date.toString();
        switch (day) { // to-do: make this simpler
            case "yesterday":
                date = new Date(1439762400000l - 86400l);
                date2 = date.toString();
                break;
            case "thisMonth":
                date2 = date2.substring(0, 7);
                break;
            case "lastMonth":
                date2 = date2.substring(0, 7);
                String month = date2.substring(date2.lastIndexOf('-') + 1);
                String monthBefore = (Integer.parseInt(month) - 1) + "";
                monthBefore = monthBefore.length() == 1 ? "-0" + monthBefore : "-" + monthBefore;
                date2 = date2.replaceFirst(("-" + month), monthBefore);
                break;
        }
        // active users, paying users and registrations
        StringBuilder query = new StringBuilder("SELECT platform, sum(u.createDate LIKE ('").append(date2).append("%')), sum(u.payingUser=1), count(*) FROM bulima.USER u left join bulima.LOGIN_HISTORY lh on(u.id=lh.ID_USER) where ");

        query.append(" lh.createDate BETWEEN '");
        appendDateToQuery(query, day);
        query.append(" group by platform");

        List<Object[]> list = em.createNativeQuery(query.toString()).getResultList();
        // revenue
        List<Double> list2 = getRevenue(day);

        HashMap results = toMap(list, list2);
        System.out.println(query);
        return results;
    }

    // retrives revenue made for android, ios, web and total revenue for defined dates
    List<Double> getRevenue(String day) {
        EntityManager em = helper.getEntityManager();

        //SELECT comment, sum(sellingPrice) FROM bulima.SALESORDER WHERE authResult = 'AUTHORISED' group by comment AND requestDate between '2015-08-16 00:00:00' AND '2015-08-17 00:00:00';
        StringBuilder queryRevenue = new StringBuilder("SELECT comment, sum(sellingPrice) FROM bulima.SALESORDER WHERE authResult = 'AUTHORISED' AND requestDate BETWEEN '");

        appendDateToQuery(queryRevenue, day);
        queryRevenue.append(" group by comment");
        List<Object[]> list = em.createNativeQuery(queryRevenue.toString()).getResultList();
        double androidRevenue = 0;
        double iosRevenue = 0;
        double webRevenue = 0;
        double totalRevenue = 0;
        for (Object[] objects : list) {
            if (objects[1] == null) {
                break;
            }
            if (objects[0] == null) {
                objects[0] = "";
            }
            switch ((String) objects[0]) {
                case ("Android"):
                    androidRevenue += ((BigDecimal) objects[1]).doubleValue();
                    break;
                case ("iOS"):
                    iosRevenue += ((BigDecimal) objects[1]).doubleValue();
                    break;
                default:
                    webRevenue += ((BigDecimal) objects[1]).doubleValue();
            }
        }
        totalRevenue = webRevenue + iosRevenue + androidRevenue;
        totalRevenue = new BigDecimal(totalRevenue).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<Double> revenue = new ArrayList<>();
        revenue.add(androidRevenue);
        revenue.add(iosRevenue);
        revenue.add(webRevenue);
        revenue.add(totalRevenue);

        return revenue;
    }

    // convert results from DB in formated HashMap
    private HashMap toMap(List<Object[]> list, List<Double> listRevenue) {
        HashMap hm = new HashMap();
        long activeUsers = 0;
        long registrations = 0;
        long payingUsers = 0;
        int i = 0;
        for (Object[] objects : list) {
            HashMap hm2 = new HashMap();
            hm2.put("activeUsers", objects[3]);
            hm2.put("registrations", objects[1]);
            hm2.put("payingUsers", objects[2]);
            hm2.put("revenue", listRevenue.get(i++));
            hm.put(objects[0], hm2);

            activeUsers += (((BigInteger) objects[3]).longValue());
            registrations += (((BigDecimal) objects[1]).longValue());
            payingUsers += (((BigDecimal) objects[2]).longValue());
        }

        HashMap hm2 = new HashMap();
        hm2.put("activeUsers", activeUsers);
        hm2.put("registrations", registrations);
        hm2.put("payingUsers", payingUsers);
        hm2.put("revenue", listRevenue.get(i));
        hm.put("TOTAL", hm2);
        return hm;
    }

    // appends condition for date filter
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
