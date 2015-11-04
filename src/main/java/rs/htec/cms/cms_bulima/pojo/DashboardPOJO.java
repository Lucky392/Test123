/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.List;

/**
 *
 * @author stefan
 */
public class DashboardPOJO {

    private List<Object> dailyActiveUsers;
    private List<Object> registrations;
    private List<Object> payingUser;
    //long revenue;

    public DashboardPOJO() {
    }

    public List<Object> getDailyActiveUsers() {
        return dailyActiveUsers;
    }

    public void setDailyActiveUsers(List<Object> dailyActiveUsers) {
        this.dailyActiveUsers = dailyActiveUsers;
    }

    public List<Object> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Object> registrations) {
        this.registrations = registrations;
    }

    public List<Object> getPayingUser() {
        return payingUser;
    }

    public void setPayingUser(List<Object> payingUser) {
        this.payingUser = payingUser;
    }

}
