/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.LoginHistory;

/**
 *
 * @author marko
 */
public class LoginHistoryPOJO {
    
    private Long id;
    private Date loginDate;
    private Date createDate;
    private String userIP;
    private String platform;
    private Long idUser;

    public LoginHistoryPOJO(LoginHistory lh) {
        this.id = lh.getId();
        this.loginDate = lh.getLoginDate();
        this.createDate = lh.getCreateDate();
        this.userIP = lh.getUserIP();
        this.platform = lh.getPlatform();
        if (lh.getIdUser() != null){
            this.idUser = lh.getIdUser().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    
    
}
