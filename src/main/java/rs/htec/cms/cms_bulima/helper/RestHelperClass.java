/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.lang.reflect.Field;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;
import rs.htec.cms.cms_bulima.token.Base64Token;

/**
 *
 * @author lazar
 */
public class RestHelperClass {

    public AbstractTokenCreator getAbstractToken(){
        return new Base64Token();
    }

    public String getJson(List list) throws IllegalArgumentException, IllegalAccessException {
        JSONArray jsonList = new JSONArray();
        for (Object o : list) {
            JSONObject obj1 = new JSONObject();
            for (Field field : o.getClass().getDeclaredFields()) {
                if (!field.getName().equals("serialVersionUID")) {
                    field.setAccessible(true);
                    String s = field.get(o) + "";
                    obj1.put(field.getName(), s);
                }
            }
            jsonList.add(obj1);
        }
        return jsonList.toJSONString();
    }

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("rs.htec.cms_CMS_Bulima_war_1.0PU");
        EntityManager ecm = emf.createEntityManager();
        return ecm;
    }

    public boolean isAdmin(CmsUser user) {
        return user.getIdRole().getRoleName().equals("admin");
    }

    public boolean isNewsAdmin(CmsUser user) {
        return user.getIdRole().getRoleName().equals("news_admin");
    }

}
