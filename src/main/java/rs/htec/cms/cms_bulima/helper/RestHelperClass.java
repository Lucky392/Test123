/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author lazar
 */
public class RestHelperClass {
    
    public String createToken(long id) {
        return "TOKEN##" + id + "##" + (new Date()).getTime();
    }

    public String encode(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    public String decode(String token) {
        return new String(Base64.getDecoder().decode(token));
    }

    public String[] decodeBasicAuth(String authorization) {
        if (authorization == null) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        if (authorization.length() < 9) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        if (authorization.length() > 64) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        String s[] = authorization.split("\\s", 3);
        if (s.length < 2) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        for (int i = 0; i < s.length; i++) {
            String part = s[i];
            if (part.compareTo("Basic") == 0) {
                String userPassBase64 = s[i + 1];
                if (!userPassBase64.isEmpty()) {
                    String userPass = null;
                    try {
                        userPass = new String(DatatypeConverter.parseBase64Binary(userPassBase64));
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Authorization cannot be decoded.", e);
                    }
                    String userPassArray[] = userPass.split(":");
                    if (userPassArray.length == 2) {
                        return userPassArray;
                    } else {
                        throw new RuntimeException("Invalid Authorization String.");
                    }
                } else {
                    throw new RuntimeException("Invalid Authorization String.");
                }
            }
        }
        throw new RuntimeException("Authorization cannot be decoded.");
    }
    
    public String getJson(List list) throws IllegalArgumentException, IllegalAccessException {
        JSONArray jsonList = new JSONArray();
        for (Object o : list) {
            JSONObject obj1 = new JSONObject();
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String s = field.get(o) + "";
                obj1.put(field.getName(), s);
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
    
}
