/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;

/**
 *
 * @author marko
 */
@XmlRootElement
public class CmsUserPojo {
    private long id;
    private String username;
    private String token;
    private RolePOJO role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RolePOJO getRole() {
        return role;
    }

    public void setRole(RolePOJO role) {
        this.role = role;
    }
    
    public void createPermissions(List<CmsUserPrivileges> privileges){
        RolePOJO rolePojo = new RolePOJO();
        rolePojo.setRoleName(privileges.get(0).getCmsRole().getName());
        rolePojo.setId(privileges.get(0).getCmsRole().getId());
//        List<TablePOJO> permissions = new ArrayList<>();
        Map map = new HashMap();
        for (CmsUserPrivileges privilege : privileges) {
//            TablePOJO table = new TablePOJO();
//            table.setTableName(privilege.getCmsTables().getTableName());
            
            List<String> actions = new ArrayList<>();
            if (privilege.getAddAction()){
//                actions.put(privilege.getCmsTables().getTableName(), table)
                actions.add("ADD");
            }
            if (privilege.getDeleteAction()){
                actions.add("DELETE");
            }
            if (privilege.getEditAction()){
                actions.add("EDIT");
            }
            if (privilege.getSearchAction()){
                actions.add("SEARCH");
            }
            map.put(privilege.getCmsTables().getTableName(), actions);
//            table.setActions(actions);
//            permissions.add(table);
        }
        rolePojo.setPermissions((HashMap) map);
        role = rolePojo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
