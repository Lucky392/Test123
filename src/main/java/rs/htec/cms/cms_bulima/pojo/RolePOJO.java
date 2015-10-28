/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.internal.expressions.MapEntryExpression;
import rs.htec.cms.cms_bulima.domain.CmsRole;
import rs.htec.cms.cms_bulima.domain.CmsTables;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;

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
    
    public static List<CmsUserPrivileges> createListPrivileges(RolePOJO role){
        CmsRole cmsRole = new CmsRole();
        cmsRole.setName(role.getRoleName());
        HashMap map = role.getPermissions();
        Iterator it = map.entrySet().iterator();
        List<CmsUserPrivileges> privileges = new ArrayList<>();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            CmsUserPrivileges cup = new CmsUserPrivileges();
            CmsTables cmsTable = new CmsTables();
            cmsTable.setTableName((String) pair.getKey());
            List<String> list = (List<String>) pair.getValue();
            for (String item : list) {
                switch(item){
                    case "ADD":
                        cup.setAddAction(true);
                        break;
                    case "DELETE":
                        cup.setDeleteAction(true);
                        break;
                    case "EDIT":
                        cup.setEditAction(true);
                        break;
                    case "SEARCH":
                        cup.setSearchAction(true);
                        break;
                }
            }
            cup.setCmsRole(cmsRole);
            cup.setCmsTables(cmsTable);
            privileges.add(cup);
        }
        return privileges;
    }
}
