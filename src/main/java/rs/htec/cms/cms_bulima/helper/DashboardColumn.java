/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import rs.htec.cms.cms_bulima.service.LoginHistoryRESTEndpoint;

/**
 *
 * @author stefan
 */
public class DashboardColumn {

    private String platform;

    private String time;
    private long dailyActiveUser;
    private long registrations;
    private long payingUser;
    //private String revenue;
    LoginHistoryRESTEndpoint login = new LoginHistoryRESTEndpoint();

    public DashboardColumn(String platform, String time) {
        this.platform = platform;
        this.time = time;
    }
    
    @XmlTransient
    @JsonIgnore
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDailyActiveUser() {
        return dailyActiveUser;
    }

    public void setDailyActiveUser(long dailyActiveUser) {
        this.dailyActiveUser = dailyActiveUser;
    }

    public long getRegistrations() {
        return registrations;
    }

    public void setRegistrations(long registrations) {
        this.registrations = registrations;
    }

    public long getPayingUser() {
        return payingUser;
    }

    public void setPayingUser(long payingUser) {
        this.payingUser = payingUser;
    }

    public void instantiateColumn() {
        this.setDailyActiveUser(login.getActiveUser(platform, time));
        this.setRegistrations(login.getRegistrations(platform, time));
        this.setPayingUser(login.getPayingUsers(platform, time));
    }

}
