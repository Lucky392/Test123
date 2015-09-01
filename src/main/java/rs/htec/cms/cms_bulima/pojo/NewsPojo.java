/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marko
 */
@Entity
@XmlRootElement
public class NewsPojo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
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

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @XmlElement
    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    @XmlElement
    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    @XmlElement
    public String getNewsHeadlineWeb() {
        return newsHeadlineWeb;
    }

    public void setNewsHeadlineWeb(String newsHeadlineWeb) {
        this.newsHeadlineWeb = newsHeadlineWeb;
    }

    @XmlElement
    public String getNewsMessageWeb() {
        return newsMessageWeb;
    }

    public void setNewsMessageWeb(String newsMessageWeb) {
        this.newsMessageWeb = newsMessageWeb;
    }

    @XmlElement
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlElement
    public String getNewsHeadlineMobile() {
        return newsHeadlineMobile;
    }

    public void setNewsHeadlineMobile(String newsHeadlineMobile) {
        this.newsHeadlineMobile = newsHeadlineMobile;
    }

    @XmlElement
    public String getNewsMessageMobile() {
        return newsMessageMobile;
    }

    public void setNewsMessageMobile(String newsMessageMobile) {
        this.newsMessageMobile = newsMessageMobile;
    }

    @XmlElement
    public Long getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(Long idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    @XmlElement
    public Long getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(Long idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }
    
    
}
