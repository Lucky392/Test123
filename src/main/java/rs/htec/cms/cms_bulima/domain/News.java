/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "NEWS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "News.findAll", query = "SELECT n FROM News n "),
    @NamedQuery(name = "News.findById", query = "SELECT n FROM News n WHERE n.id = :id"),
    @NamedQuery(name = "News.findByNewsType", query = "SELECT n FROM News n WHERE n.newsType = :newsType"),
    @NamedQuery(name = "News.findByNewsDate", query = "SELECT n FROM News n WHERE n.newsDate = :newsDate"),
    @NamedQuery(name = "News.findByNewsHeadlineWeb", query = "SELECT n FROM News n WHERE n.newsHeadlineWeb = :newsHeadlineWeb"),
    @NamedQuery(name = "News.findByCreateDate", query = "SELECT n FROM News n WHERE n.createDate = :createDate"),
    @NamedQuery(name = "News.findByNewsHeadlineMobile", query = "SELECT n FROM News n WHERE n.newsHeadlineMobile = :newsHeadlineMobile"),
    @NamedQuery(name = "News.findAllByLike", query = "SELECT n FROM News n WHERE n.newsType LIKE :searchedWord OR n.newsHeadlineWeb LIKE :searchedWord OR n.newsHeadlineMobile LIKE :searchedWord OR n.newsMessageWeb LIKE :searchedWord OR n.newsMessageMobile LIKE :searchedWord"), //order by :column_name
    @NamedQuery(name = "News.findByNewsBetweenDate", query = "SELECT n FROM News n WHERE n.newsDate BETWEEN :min AND :max"),
    @NamedQuery(name = "News.findAllByLikeBetweenDate", query = "SELECT n FROM News n WHERE (n.newsDate BETWEEN :min AND :max) AND (n.newsType LIKE :searchedWord OR n.newsHeadlineWeb LIKE :searchedWord OR n.newsHeadlineMobile LIKE :searchedWord OR n.newsMessageWeb LIKE :searchedWord OR n.newsMessageMobile LIKE :searchedWord)")})

    // ORDER BY CASE :column_name WHEN 'id' THEN n.id WHEN 'newsDate' THEN n.newsDate WHEN 'createDate' THEN n.createDate END
public class News implements Serializable {
    @OneToMany(mappedBy = "idNews")
    private List<Comment> commentList;
    @Column(name = "commentsCount")
    private BigInteger commentsCount;
    @OneToMany(mappedBy = "idAuction")
    private List<News> newsList;
    @JoinColumn(name = "ID_AUCTION", referencedColumnName = "ID")
    @ManyToOne
    private News idAuction;
    @JoinColumn(name = "ID_FANTASY_CLUB_SENDER", referencedColumnName = "ID")
    @ManyToOne
    private FantasyClub idFantasyClubSender;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "newsType")
    private String newsType;
    @Column(name = "newsDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newsDate;
    @Size(max = 255)
    @Column(name = "newsHeadlineWeb")
    private String newsHeadlineWeb;
    @Lob
    @Size(max = 65535)
    @Column(name = "newsMessageWeb")
    private String newsMessageWeb;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "newsHeadlineMobile")
    private String newsHeadlineMobile;
    @Lob
    @Size(max = 65535)
    @Column(name = "newsMessageMobile")
    private String newsMessageMobile;
    @JoinColumn(name = "ID_FANTASY_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private FantasyLeague idFantasyLeague;
    @JoinColumn(name = "ID_FANTASY_CLUB", referencedColumnName = "ID")
    @ManyToOne
    private FantasyClub idFantasyClub;

    public News() {
    }

    public News(Long id) {
        this.id = id;
    }

    public News(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }
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
    public FantasyLeague getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(FantasyLeague idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }
    @XmlElement
    public FantasyClub getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(FantasyClub idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof News)) {
            return false;
        }
        News other = (News) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }

    public BigInteger getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(BigInteger commentsCount) {
        this.commentsCount = commentsCount;
    }

    @XmlTransient
    @JsonIgnore
    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public News getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(News idAuction) {
        this.idAuction = idAuction;
    }

    public FantasyClub getIdFantasyClubSender() {
        return idFantasyClubSender;
    }

    public void setIdFantasyClubSender(FantasyClub idFantasyClubSender) {
        this.idFantasyClubSender = idFantasyClubSender;
    }

    @XmlTransient
    @JsonIgnore
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
    
}
