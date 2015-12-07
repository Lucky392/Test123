/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.News;

/**
 *
 * @author stefan
 */
public class NewsPOJO {

    private BigInteger commentsCount;
    private Long idAuction;
    private Long idFantasyClubSender;
    private Long id;
    private String newsType;
    private Date newsDate;
    private String newsHeadlineWeb;
    private String newsMessageWeb;
    private Date createDate;
    private String newsHeadlineMobile;
    private String newsMessageMobile;
    private Long idFantasyLeague;
    private Long idFantasyClub;
    private String auctionName;
    private String fantasyClubSenderName;
    private String fantasyClubName;
    private String fantasyLeagueName;
    

    public NewsPOJO(News news) {
        this.id = news.getId();
        this.commentsCount = news.getCommentsCount();
        if (news.getIdAuction() != null) {
            this.idAuction = news.getIdAuction().getId();
            this.auctionName = news.getIdAuction().getNewsType();
        }
        if (news.getIdFantasyClubSender() != null) {
            this.idFantasyClubSender = news.getIdFantasyClubSender().getId();
            this.fantasyClubSenderName = news.getIdFantasyClubSender().getName();
        }
        this.newsType = news.getNewsType();
        this.newsDate = news.getNewsDate();
        this.newsHeadlineWeb = news.getNewsHeadlineWeb();
        this.newsMessageWeb = news.getNewsMessageWeb();
        this.createDate = news.getCreateDate();
        this.newsHeadlineMobile = news.getNewsHeadlineMobile();
        this.newsMessageMobile = news.getNewsMessageMobile();
        if (news.getIdFantasyLeague() != null) {
            this.idFantasyLeague = news.getIdFantasyLeague().getId();
            this.fantasyLeagueName = news.getIdFantasyLeague().getName();
            
        }
        if (news.getIdFantasyClub() != null) {
            this.idFantasyClub = news.getIdFantasyClub().getId();
            this.fantasyClubName = news.getIdFantasyClub().getName();
        }
    }

    public Long getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(Long idAuction) {
        this.idAuction = idAuction;
    }

    public Long getIdFantasyClubSender() {
        return idFantasyClubSender;
    }

    public void setIdFantasyClubSender(Long idFantasyClubSender) {
        this.idFantasyClubSender = idFantasyClubSender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsHeadlineWeb() {
        return newsHeadlineWeb;
    }

    public void setNewsHeadlineWeb(String newsHeadlineWeb) {
        this.newsHeadlineWeb = newsHeadlineWeb;
    }

    public String getNewsMessageWeb() {
        return newsMessageWeb;
    }

    public void setNewsMessageWeb(String newsMessageWeb) {
        this.newsMessageWeb = newsMessageWeb;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNewsHeadlineMobile() {
        return newsHeadlineMobile;
    }

    public void setNewsHeadlineMobile(String newsHeadlineMobile) {
        this.newsHeadlineMobile = newsHeadlineMobile;
    }

    public String getNewsMessageMobile() {
        return newsMessageMobile;
    }

    public void setNewsMessageMobile(String newsMessageMobile) {
        this.newsMessageMobile = newsMessageMobile;
    }

    public Long getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(Long idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    public Long getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(Long idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public BigInteger getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(BigInteger commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getFantasyClubSenderName() {
        return fantasyClubSenderName;
    }

    public void setFantasyClubSenderName(String fantasyClubSenderName) {
        this.fantasyClubSenderName = fantasyClubSenderName;
    }

    public String getFantasyClubName() {
        return fantasyClubName;
    }

    public void setFantasyClubName(String fantasyClubName) {
        this.fantasyClubName = fantasyClubName;
    }

    public String getFantasyLeagueName() {
        return fantasyLeagueName;
    }

    public void setFantasyLeagueName(String fantasyLeagueName) {
        this.fantasyLeagueName = fantasyLeagueName;
    }

    public static List<NewsPOJO> toNewsPOJOList(List<News> list) {
        NewsPOJO pojo;
        List<NewsPOJO> pojos = new ArrayList<>();
        for (News news : list) {
            pojo = new NewsPOJO(news);
            pojos.add(pojo);
        }
        return pojos;
    }

}
