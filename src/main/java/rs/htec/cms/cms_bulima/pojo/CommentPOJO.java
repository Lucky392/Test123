/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.Comment;

/**
 *
 * @author stefan
 */
public class CommentPOJO {

    private Long id;
    private String message;
    private Date createDate;
    private Long idNews;
    private String newsType;
    private Long idFantasyClub;
    private String fantasyClubName;

    public CommentPOJO(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.createDate = comment.getCreateDate();
        if (comment.getIdNews() != null) {
            this.idNews = comment.getIdNews().getId();
            this.newsType = comment.getIdNews().getNewsType();
        }
        if (comment.getIdFantasyClub() != null) {
            this.idFantasyClub = comment.getIdFantasyClub().getId();
            this.fantasyClubName = comment.getIdFantasyClub().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdNews() {
        return idNews;
    }

    public void setIdNews(Long idNews) {
        this.idNews = idNews;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public Long getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(Long idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public String getFantasyClubName() {
        return fantasyClubName;
    }

    public void setFantasyClubName(String fantasyClubName) {
        this.fantasyClubName = fantasyClubName;
    }

    public static List<CommentPOJO> toCommentPOJOList(List<Comment> list) {
        CommentPOJO pojo;
        List<CommentPOJO> pojos = new ArrayList<>();
        for (Comment comment : list) {
            pojo = new CommentPOJO(comment);
            pojos.add(pojo);
        }
        return pojos;
    }
    
}
