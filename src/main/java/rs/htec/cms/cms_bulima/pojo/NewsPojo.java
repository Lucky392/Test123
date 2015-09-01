/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import rs.htec.cms.cms_bulima.domain.News;

/**
 *
 * @author marko
 */
@Entity
@XmlRootElement
public class NewsPojo implements Serializable, AbstractFacadePojo {

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

    @Override
    public List createPojoList(List list) {
        List<NewsPojo> newsPojos = new ArrayList<>();
        for (News nue : (List<News>) list) {
            NewsPojo np = new NewsPojo();
            id = nue.getId();
            if (nue.getIdFantasyClub() == null) {
                idFantasyClub = new Long(0);
            } else {
                idFantasyClub = nue.getIdFantasyClub().getId();
            }
            if (nue.getIdFantasyLeague() == null) {
                idFantasyLeague = new Long(0);
            } else {
                idFantasyLeague = nue.getIdFantasyLeague().getId();
            }
            newsDate = nue.getNewsDate();
            newsHeadlineMobile = nue.getNewsHeadlineMobile();
            newsHeadlineWeb = nue.getNewsHeadlineWeb();
            newsMessageMobile = nue.getNewsMessageMobile();
            newsMessageWeb = nue.getNewsMessageWeb();
            newsType = nue.getNewsType();
            createDate = nue.getCreateDate();
            newsPojos.add(np);
        }
        return newsPojos;
    }

}
