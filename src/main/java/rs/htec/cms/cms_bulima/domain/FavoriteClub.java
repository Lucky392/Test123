/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FAVORITE_CLUB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FavoriteClub.findAll", query = "SELECT f FROM FavoriteClub f"),
    @NamedQuery(name = "FavoriteClub.findById", query = "SELECT f FROM FavoriteClub f WHERE f.id = :id"),
    @NamedQuery(name = "FavoriteClub.findByIdSport1Team", query = "SELECT f FROM FavoriteClub f WHERE f.idSport1Team = :idSport1Team"),
    @NamedQuery(name = "FavoriteClub.findByMediumName", query = "SELECT f FROM FavoriteClub f WHERE f.mediumName = :mediumName"),
    @NamedQuery(name = "FavoriteClub.findByShortName", query = "SELECT f FROM FavoriteClub f WHERE f.shortName = :shortName"),
    @NamedQuery(name = "FavoriteClub.findByLogoUrl", query = "SELECT f FROM FavoriteClub f WHERE f.logoUrl = :logoUrl"),
    @NamedQuery(name = "FavoriteClub.findByFans", query = "SELECT f FROM FavoriteClub f WHERE f.fans = :fans"),
    @NamedQuery(name = "FavoriteClub.findByFansUpdateAt", query = "SELECT f FROM FavoriteClub f WHERE f.fansUpdateAt = :fansUpdateAt"),
    @NamedQuery(name = "FavoriteClub.findByPoint", query = "SELECT f FROM FavoriteClub f WHERE f.point = :point"),
    @NamedQuery(name = "FavoriteClub.findByPointUpdateAt", query = "SELECT f FROM FavoriteClub f WHERE f.pointUpdateAt = :pointUpdateAt"),
    @NamedQuery(name = "FavoriteClub.findByCreateDate", query = "SELECT f FROM FavoriteClub f WHERE f.createDate = :createDate")})
public class FavoriteClub implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ID_SPORT1_TEAM")
    private String idSport1Team;
    @Size(max = 255)
    @Column(name = "mediumName")
    private String mediumName;
    @Size(max = 255)
    @Column(name = "shortName")
    private String shortName;
    @Size(max = 255)
    @Column(name = "logoUrl")
    private String logoUrl;
    @Column(name = "fans")
    private Integer fans;
    @Column(name = "fansUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fansUpdateAt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "point")
    private Double point;
    @Column(name = "pointUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pointUpdateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idFavClub")
    private List<FantasyManager> fantasyManagerList;
    @JoinColumn(name = "ID_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private League idLeague;

    public FavoriteClub() {
    }

    public FavoriteClub(Long id) {
        this.id = id;
    }

    public FavoriteClub(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Team() {
        return idSport1Team;
    }

    public void setIdSport1Team(String idSport1Team) {
        this.idSport1Team = idSport1Team;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Date getFansUpdateAt() {
        return fansUpdateAt;
    }

    public void setFansUpdateAt(Date fansUpdateAt) {
        this.fansUpdateAt = fansUpdateAt;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Date getPointUpdateAt() {
        return pointUpdateAt;
    }

    public void setPointUpdateAt(Date pointUpdateAt) {
        this.pointUpdateAt = pointUpdateAt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyManager> getFantasyManagerList() {
        return fantasyManagerList;
    }

    public void setFantasyManagerList(List<FantasyManager> fantasyManagerList) {
        this.fantasyManagerList = fantasyManagerList;
    }

    public League getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(League idLeague) {
        this.idLeague = idLeague;
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
        if (!(object instanceof FavoriteClub)) {
            return false;
        }
        FavoriteClub other = (FavoriteClub) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FavoriteClub[ id=" + id + " ]";
    }
    
}
