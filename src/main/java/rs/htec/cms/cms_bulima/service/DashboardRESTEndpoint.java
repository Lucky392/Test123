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
import java.util.Calendar;
import java.util.HashMap;
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
     * IOS, Web and Total] for today, yesterday, current month and last month.
     * <br/>
     * <br/>
     * Example for JSON response:<br/>
     * <br/>
     * {<br/>
     * "yesterday": {<br/>
     * "TOTAL": {<br/>
     * "registrations": 7090,<br/>
     * "payingUsers": 4254,<br/>
     * "activeUsers": 63789<br/>
     * },<br/>
     * "WEB": {<br/>
     * "registrations": 1076,<br/>
     * "payingUsers": 1260,<br/>
     * "activeUsers": 16349<br/>
     * },<br/>
     * "ANDROID": {<br/>
     * "registrations": 4900,<br/>
     * "payingUsers": 1919,<br/>
     * "activeUsers": 30626<br/>
     * },<br/>
     * "IOS": {<br/>
     * "registrations": 1114,<br/>
     * "payingUsers": 1075,<br/>
     * "activeUsers": 16814<br/>
     * }<br/>
     * },<br/>
     * "today": {<br/>
     * "TOTAL": {<br/>
     * "registrations": 1265,<br/>
     * "payingUsers": 4000,<br/>
     * "activeUsers": 49489<br/>
     * },<br/>
     * "WEB": {<br/>
     * "registrations": 421,<br/>
     * "payingUsers": 1894,<br/>
     * "activeUsers": 19740<br/>
     * },<br/>
     * "ANDROID": {<br/>
     * "registrations": 716,<br/>
     * "payingUsers": 1394,<br/>
     * "activeUsers": 19482<br/>
     * },<br/>
     * "IOS": {<br/>
     * "registrations": 128,<br/>
     * "payingUsers": 712,<br/>
     * "activeUsers": 10267<br/>
     * }<br/>
     * },<br/>
     * "lastMonth": {<br/>
     * "TOTAL": {<br/>
     * "registrations": 373442,<br/>
     * "payingUsers": 25507,<br/>
     * "activeUsers": 423524<br/>
     * },<br/>
     * "WEB": {<br/>
     * "registrations": 96917,<br/>
     * "payingUsers": 10018,<br/>
     * "activeUsers": 118992<br/>
     * },<br/>
     * "ANDROID": {<br/>
     * "registrations": 170107,<br/>
     * "payingUsers": 10508,<br/>
     * "activeUsers": 187121<br/>
     * },<br/>
     * "IOS": {<br/>
     * "registrations": 106418,<br/>
     * "payingUsers": 4981,<br/>
     * "activeUsers": 117411<br/>
     * }<br/>
     * },<br/>
     * "thisMonth": {<br/>
     * "TOTAL": {<br/>
     * "registrations": 380898,<br/>
     * "payingUsers": 59306,<br/>
     * "activeUsers": 816245<br/>
     * },<br/>
     * "WEB": {<br/>
     * "registrations": 112361,<br/>
     * "payingUsers": 21307,<br/>
     * "activeUsers": 245487<br/>
     * },<br/>
     * "ANDROID": {<br/>
     * "registrations": 185024,<br/>
     * "payingUsers": 23907,<br/>
     * "activeUsers": 360985<br/>
     * },<br/>
     * "IOS": {<br/>
     * "registrations": 83513,<br/>
     * "payingUsers": 14092,<br/>
     * "activeUsers": 209773<br/>
     * }<br/>
     * }<br/>
     * } <br/>
     *
     *
     * @param token - header parameter for checking permission
     * @return 200 OK
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getDashboard(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        HashMap result = new HashMap();

        for (String d : dates) {
            result.put(d, getDashboardFormDB(d));
        }
        return Response.ok().entity(result).build();
    }

    private HashMap getDashboardFormDB(String day) {
        EntityManager em = helper.getEntityManager();
        String date2 = date.toString();
        System.out.println("Date" + date);
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
                System.out.println("mo " + month);
                String monthBefore = (Integer.parseInt(month) - 1) + "";
                monthBefore = monthBefore.length() == 1 ? "-0" + monthBefore : "-" + monthBefore;
                date2 = date2.replaceFirst(("-" + month), monthBefore);
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

    // convert results from DB in formated HashMap
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

            activeUsers += (((BigInteger) objects[3]).longValue());
            registrations += (((BigDecimal) objects[1]).longValue());
            payingUsers += (((BigDecimal) objects[2]).longValue());

//            hm.put("revenue", new Double(3434.34)); // to-do
        }

        HashMap hm2 = new HashMap();
        hm2.put("activeUsers", activeUsers);
        hm2.put("registrations", registrations);
        hm2.put("payingUsers", payingUsers);
        hm.put("TOTAL", hm2);
        return hm;
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
