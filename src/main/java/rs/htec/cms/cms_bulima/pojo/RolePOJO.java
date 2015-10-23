/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marko
 */
@XmlRootElement
public class RolePOJO {
    
    private String roleName;
    private HashMap permissions;
    private long id;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public HashMap getPermissions() {
        return permissions;
    }

    public void setPermissions(HashMap permissions) {
        this.permissions = permissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
