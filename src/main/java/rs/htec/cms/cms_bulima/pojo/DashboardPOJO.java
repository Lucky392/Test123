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

    private List<PlatformPOJO> dailyActiveUsers;
    private List<PlatformPOJO> registrations;
    private List<PlatformPOJO> payingUser;
    //long revenue;

    public DashboardPOJO() {
    }

    public List<PlatformPOJO> getDailyActiveUsers() {
        return dailyActiveUsers;
    }

    public void setDailyActiveUsers(List<PlatformPOJO> dailyActiveUsers) {
        this.dailyActiveUsers = dailyActiveUsers;
    }

    public List<PlatformPOJO> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<PlatformPOJO> registrations) {
        this.registrations = registrations;
    }

    public List<PlatformPOJO> getPayingUser() {
        return payingUser;
    }

    public void setPayingUser(List<PlatformPOJO> payingUser) {
        this.payingUser = payingUser;
    }

}
