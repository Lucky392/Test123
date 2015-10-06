/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.ForbbidenException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;
import rs.htec.cms.cms_bulima.token.Base64Token;

/**
 *
 * @author lazar
 */
public class RestHelperClass {

    AbstractTokenCreator tokenHelper;

    public AbstractTokenCreator getAbstractToken() {
        return tokenHelper;
    }

    public RestHelperClass() {
        tokenHelper = new Base64Token();
    }

    public String getJson(List list) {
        JSONArray jsonList = new JSONArray();
        for (Object o : list) {
            JSONObject obj1 = new JSONObject();
            for (Field field : o.getClass().getDeclaredFields()) {
                if (!field.getName().equals("serialVersionUID")) {
                    field.setAccessible(true);
                    String s = "";
                    try {
                        s = field.get(o) + "";
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(RestHelperClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    obj1.put(field.getName(), s);
                }
            }
            jsonList.add(obj1);
        }
        return jsonList.toJSONString();
    }

    public JSONArray getJsonArray(List list) {
        JSONArray jsonList = new JSONArray();
        for (Object o : list) {
            JSONObject obj1 = new JSONObject();
            for (Field field : o.getClass().getDeclaredFields()) {
                if (!field.getName().equals("serialVersionUID")) {
                    field.setAccessible(true);
                    String s = "";
                    try {
                        s = field.get(o) + "";
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(RestHelperClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    obj1.put(field.getName(), s);
                }
            }
            jsonList.add(obj1);
        }
        return jsonList;
    }
    
    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("rs.htec.cms_CMS_Bulima_war_1.0PU");
        EntityManager ecm = emf.createEntityManager();
        return ecm;
    }

    public void checkUserAndPrivileges(EntityManager em, long tableId, MethodConstants method, String token) {
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (!havePrivilege(em, user, tableId, method)) {
                    throw new ForbbidenException("You don't have permission to search data");
                }
            } else {
                throw new NotAuthorizedException("You are not logged in!");
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    private boolean havePrivilege(EntityManager em, CmsUser user, long tableID, MethodConstants method) {
        CmsUserPrivileges cup = (CmsUserPrivileges) em.createNamedQuery("CmsUserPrivileges.findByPK")
                .setParameter("roleId", user.getIdRole().getId())
                .setParameter("tableId", tableID)
                .getSingleResult();
        if (cup == null) {
            return false;
        }
        switch (method) {
            case SEARCH:
                return cup.getSearchAction();
            case EDIT:
                return cup.getEditAction();
            case ADD:
                return cup.getAddAction();
            case DELETE:
                return cup.getDeleteAction();
            default:
                return false;
        }
    }

    public void persistObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public void removeObject(EntityManager em, Object o, Long id) {
        if (o == null) {
            throw new DataNotFoundException("News at index: " + id + " does not exits");
        }
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

    public void mergeObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

}
