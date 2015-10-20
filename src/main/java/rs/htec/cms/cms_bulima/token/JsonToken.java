/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.token;

import java.util.List;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;

/**
 *
 * @author lazar
 */
public class JsonToken {
   
    private CmsUser user;

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
    private String token;
    private List<CmsUserPrivileges> cmsUserPrivileges;

    public JsonToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CmsUserPrivileges> getCmsUserPrivileges() {
        return cmsUserPrivileges;
    }

    public void setCmsUserPrivileges(List<CmsUserPrivileges> cmsUserPrivileges) {
        this.cmsUserPrivileges = cmsUserPrivileges;
    }
    
    
}
