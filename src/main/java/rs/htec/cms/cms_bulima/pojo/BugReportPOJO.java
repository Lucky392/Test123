/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.BugReport;

/**
 *
 * @author stefan
 */
public class BugReportPOJO {

    private Long id;
    private String email;
    private String errorType;
    private String description;
    private String origin;
    private String system;
    private String deviceType;
    private String task;
    private String status;
    private String other;
    private Date reportDate;
    private String appVersion;
    private String clubName;
    private BigInteger userId;
    private String urlToUser;

    public BugReportPOJO(BugReport bugReport) {
//        Properties properties = new Properties();
//        properties.load(new FileInputStream("/WEB-INF/BulimaProperties.properties"));
//        //properties.load(getServletContext().getResourceAsStream("/WEB-INF/BulimaProperties.properties"));

        this.id = bugReport.getId();
        this.email = bugReport.getEmail();
        this.errorType = bugReport.getErrorType();
        this.description = bugReport.getDescription();
        this.origin = bugReport.getOrigin();
        this.system = bugReport.getSystem();
        this.deviceType = bugReport.getDeviceType();
        this.task = bugReport.getTask();
        this.status = bugReport.getStatus();
        this.other = bugReport.getOther();
        this.reportDate = bugReport.getReportDate();
        this.appVersion = bugReport.getAppVersion();
        this.clubName = bugReport.getClubName();
        this.userId = bugReport.getUserId();
        if (bugReport.getUserId() != null) {
            this.urlToUser = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/user/" + bugReport.getUserId();
        }

    }

    public BugReportPOJO(Long id, String email, String errorType, String description, String origin, String system, String deviceType, String task, String status, String other, Date reportDate, String appVersion, String clubName, BigInteger userId, String urlToUser) {
        this.id = id;
        this.email = email;
        this.errorType = errorType;
        this.description = description;
        this.origin = origin;
        this.system = system;
        this.deviceType = deviceType;
        this.task = task;
        this.status = status;
        this.other = other;
        this.reportDate = reportDate;
        this.appVersion = appVersion;
        this.clubName = clubName;
        this.userId = userId;
        this.urlToUser = urlToUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUrlToUser() {
        return urlToUser;
    }

    public void setUrlToUser(String urlToUser) {
        this.urlToUser = urlToUser;
    }

//    public void mapToBugReportPOJO(BugReport bugReport) {
//        this.setUserId(bugReport.getUserId());
//        this.email = bugReport.getEmail();
//        this.errorType = bugReport.getErrorType();
//        this.description = bugReport.getDescription();
//        this.origin = bugReport.getOrigin();
//        this.system = bugReport.getSystem();
//        this.deviceType = deviceType;
//        this.task = task;
//        this.status = status;
//        this.other = other;
//        this.reportDate = reportDate;
//        this.appVersion = appVersion;
//        this.clubName = clubName;
//        this.userId = userId;
//        this.urlToUser = urlToUser;
//    }
    
    public static List<BugReportPOJO> toBugReportPOJOList (List<BugReport> list) {
        BugReportPOJO pojo;
        List<BugReportPOJO> pojos = new ArrayList<>();
        for (BugReport bug : list) {
            pojo = new BugReportPOJO(bug);
            pojos.add(pojo);
        }
        return pojos;
    }
}
