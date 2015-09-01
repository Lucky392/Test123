/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Base64;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import java.util.Date;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.pojo.NewsPojo;

/**
 *
 * @author lazar
 */
@Path("/cms")
public class UserCmsRESTEndpoint {

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("rs.htec.cms_CMS_Bulima_war_1.0PU");
        EntityManager ecm = emf.createEntityManager();
        return ecm;
    }

    @GET
    @Path("/login")
    public Response logIn(@HeaderParam("authorization") String authorization) {
        String[] userPass;
        EntityManager em = getEntityManager();
        try {
            userPass = decodeBasicAuth(authorization);
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        CmsUser user = (CmsUser) em
                .createQuery("SELECT u FROM CmsUser u WHERE u.userName = :userName AND u.password = :password")
                .setParameter("userName", userPass[0])
                .setParameter("password", userPass[1])
                .getSingleResult();
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            if (user.getToken() == null || user.getToken().equals("")) {
                user.setToken(createToken(user.getId()));
                em.getTransaction().begin();
                em.merge(user);
                em.getTransaction().commit();

            }
            return Response.ok(encode(user.getToken())).build();

        }
    }

    @GET
    @Path("/logout")
    public Response logOut(@HeaderParam("authorization") String token) {
        EntityManager em = getEntityManager();
        CmsUser user = em.find(CmsUser.class, Long.parseLong(decode(token).split("##")[1]));
        user.setToken(null);
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        return Response.ok("You are logged out!").build();
    }

    @GET
    @Path("/news/{page}/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNews(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
//                List <News> news = em.createNamedQuery("News.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
                List <NewsPojo> newsPojo = em.createNativeQuery("Select * from News limit " + ((page - 1) * limit) + ", " + limit).getResultList();
//                for (int i = 0; i < news.size(); i++) {
//                    news.get(i).setIdFantasyClub(null);
//                    news.get(i).setIdFantasyLeague(null);
//                }
                
                return Response.ok().entity(new Gson().toJson(newsPojo)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(long id) {
        return "TOKEN##" + id + "##" + (new Date()).getTime();
    }

    private String encode(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    private String decode(String token) {
        return new String(Base64.getDecoder().decode(token));
    }

    public static String[] decodeBasicAuth(String authorization) {
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

}
