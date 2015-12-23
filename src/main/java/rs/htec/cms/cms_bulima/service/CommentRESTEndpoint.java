/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.Comment;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.CommentPOJO;

/**
 *
 * @author stefan
 */
@Path("comments")
public class CommentRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns comment fore defined id.
     * Example for response: <br/>
     * { <br/>
     * "createDate": 1437402240000,<br/>
     * "newsType": "lineup",<br/>
     * "idNews": 111,<br/>
     * "idFantasyClub": 1,<br/>
     * "fantasyClubName": "RB Sportfreunde Hühnerbein 1951",<br/>
     * "message": "test",<br/>
     * "id": 3<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param id id for News
     * @return Comment with  status 200 OK
     * @throws DataNotFoundException
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        CommentPOJO pojo;
        try {
            Comment comment = (Comment) em.createNamedQuery("Comment.findById").setParameter("id", id).getSingleResult();
            pojo = new CommentPOJO(comment);
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Comment at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Comment at index " + id + " does not exist..");
        }
        helper.setResponseToHistory(history, Response.ok().entity(pojo).build(), em);
        return Response.ok().entity(pojo).build();
    }

    /**
     * Return comments for search. 
     * Example for response: <br/>
     * {<br/>
     * "count": 2,<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1437402240000,<br/>
     * "newsType": "lineup",<br/>
     * "idNews": 111,<br/>
     * "idFantasyClub": 1,<br/>
     * "fantasyClubName": "RB Sportfreunde Hühnerbein 1951",<br/>
     * "message": "test",<br/>
     * "id": 3<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1449483377000,<br/>
     * "newsType": "lineup",<br/>
     * "idNews": 112,<br/>
     * "idFantasyClub": 12,<br/>
     * "fantasyClubName": "RSC Germania Bolzburg 1910",<br/>
     * "message": "test2",<br/>
     * "id": 4<br/>
     * }<br/>
     * ]<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param page number of page at which we search for Comment
     * @param limit number of Comment method returns
     * @param orderingColumn column name for ordering, if you put "-" before
     * column name, that mean DESC ordering.
     * @param minDate  start date for filtering time in millis
     * @param maxDate  end date for filtering time in millis
     * @param idNews id for News
     * @param idFantasyClub id for fantasy club
     * @return List of comments
     * @throws DataNotFoundException
     */
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getComments(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate,
            @QueryParam("idNews") String idNews, @QueryParam("idFantasyClub") String idFantasyClub) {

        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<Comment> comments;
        StringBuilder query = new StringBuilder("SELECT x FROM Comment x");
        String operator = " WHERE ";
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(operator).append("x.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
            operator = " AND ";
        }

        if (idNews != null) {
            query.append(operator).append("x.idNews = ").append(idNews);
            operator = " AND ";
        }

        if (idFantasyClub != null) {
            query.append(operator).append("x.idFantasyClub = ").append(idFantasyClub);
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        comments = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (comments == null || comments.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no comments for this search!"), em);
            throw new DataNotFoundException("There is no comments for this search!");
        }
        String countQuery = query.toString().replaceFirst("x", "count(x)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        List<CommentPOJO> pojos = CommentPOJO.toCommentPOJOList(comments);
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(pojos);
        helper.setResponseToHistory(history, Response.ok().entity(go).build(), em);
        return Response.ok().entity(go).build();
    }

    /**
     * Insert new comment. 
     * Example for body: <br/>
     * {<br/>
     * "idNews": 112,<br/>
     * "idFantasyClub": 12,<br/>
     * "message": "test2"<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param comment Comment in JSON format
     * @return status 201 CREATED
     * @throws InputValidationException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertComment(@HeaderParam("authorization") String token, @Context HttpServletRequest request, Comment comment) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        Validator validator = new Validator();
        if (validator.checkLenght(comment.getMessage(), 505, true)) {
            comment.setCreateDate(new Date());
            helper.persistObject(em, comment);
            helper.setResponseToHistory(history, Response.status(Response.Status.CREATED).build(), em);
            return Response.status(Response.Status.CREATED).build();
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * Updates comment.
     * Example for body: <br/>
     * {<br/>
     * "idNews": 112,<br/>
     * "idFantasyClub": 12,<br/>
     * "message": "test2",<br/>
     * "id": 4<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param request
     * @param comment
     * @return status 200 OK
     * @throws InputValidationException
     * @throws DataNotFoundException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(@HeaderParam("authorization") String token, @Context HttpServletRequest request, Comment comment) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.EDIT, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        Comment oldComment = em.find(Comment.class, comment.getId());
        Validator validator = new Validator();
        if (oldComment != null) {
            if (validator.checkLenght(comment.getMessage(), 505, true)) {
                comment.setCreateDate(oldComment.getCreateDate());
                helper.mergeObject(em, comment);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Comment at index " + comment.getId() + " does not exits"), em);
            throw new DataNotFoundException("Comment at index " + comment.getId() + " does not exits");
        }
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }

    /**
     * Deletes Comment for defined id.
     * 
     * @param token header parameter for checking permission
     * @param request
     * @param id of Comment that should be deleted
     * @return status 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteComment(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.DELETE, token, request.getRequestURL().toString()+(request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        Comment comment = em.find(Comment.class, id);
        helper.removeObject(em, comment, id);
        helper.setResponseToHistory(history, Response.ok().build(), em);
        return Response.ok().build();
    }
}
