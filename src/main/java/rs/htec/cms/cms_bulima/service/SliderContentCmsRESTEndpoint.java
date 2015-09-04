/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.domain.CmsUser;
import rs.htec.cms.cms_bulima.domain.SliderContent;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.token.AbstractTokenCreator;

/**
 *
 * @author stefan
 */
@Path("/slider")
public class SliderContentCmsRESTEndpoint {

    RestHelperClass helper;
    AbstractTokenCreator tokenHelper;

    public SliderContentCmsRESTEndpoint() {
        helper = new RestHelperClass();
        tokenHelper = helper.getAbstractToken();
    }

    @GET
    @Path("/{page}/{limit}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSlider(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                List<SliderContent> slider = em.createNamedQuery("SliderContent.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
                return Response.ok().entity(helper.getJson(slider)).build();
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger(QuestionOfTheDayCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, e);
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertSlider(@HeaderParam("authorization") String token, SliderContent slider) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (helper.isAdmin(user)) {
                    slider.setCreateDate(new Date());
                    em.getTransaction().begin();
                    em.persist(slider);
                    em.getTransaction().commit();
                    return Response.status(Response.Status.CREATED).build();
                } else {
                    return Response.status(Response.Status.FORBIDDEN).build();
                }
            }
        } catch (Exception ignore) {
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSlider(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (helper.isAdmin(user)) {
                    SliderContent slider = em.find(SliderContent.class, id);
                    if (slider != null) {
                        em.getTransaction().begin();
                        em.remove(slider);
                        em.getTransaction().commit();
                        return Response.ok().build();
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                } else {
                    return Response.status(Response.Status.FORBIDDEN).build();
                }
            }
        } catch (Exception ignore) {
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSlider(@HeaderParam("authorization") String token, SliderContent slider) {
        EntityManager em = helper.getEntityManager();
        try {
            CmsUser user = em.find(CmsUser.class, Long.parseLong(tokenHelper.decode(token).split("##")[1]));
            if (user.getToken() != null && !user.getToken().equals("")) {
                if (helper.isAdmin(user)) {
                    SliderContent oldSlider = em.find(SliderContent.class, slider.getId());
                    if (oldSlider != null) {
                        slider.setCreateDate(new Date());
                        em.getTransaction().begin();
                        em.merge(slider);
                        em.getTransaction().commit();
                        return Response.ok("Successfully updated!").build();
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                } else {
                    return Response.status(Response.Status.FORBIDDEN).build();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
