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
import javax.persistence.CascadeType;
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

/**
 *
 * @author marko
 */
@Entity
@Table(name = "LEAGUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "League.findAll", query = "SELECT l FROM League l"),
    @NamedQuery(name = "League.findById", query = "SELECT l FROM League l WHERE l.id = :id"),
    @NamedQuery(name = "League.findByIdSport1League", query = "SELECT l FROM League l WHERE l.idSport1League = :idSport1League"),
    @NamedQuery(name = "League.findBySport", query = "SELECT l FROM League l WHERE l.sport = :sport"),
    @NamedQuery(name = "League.findByCreateDate", query = "SELECT l FROM League l WHERE l.createDate = :createDate")})
public class League implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ID_SPORT1_LEAGUE")
    private String idSport1League;
    @Size(max = 255)
    @Column(name = "sport")
    private String sport;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idLeague")
    private List<Club> clubList;
    @OneToMany(mappedBy = "idLeague")
    private List<FavoriteClub> favoriteClubList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLeague")
    private List<Season> seasonList;
    @JoinColumn(name = "ID_COMPETITION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Competition idCompetition;

    public League() {
    }

    public League(Long id) {
        this.id = id;
    }

    public League(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1League() {
        return idSport1League;
    }

    public void setIdSport1League(String idSport1League) {
        this.idSport1League = idSport1League;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    @XmlTransient
    public List<FavoriteClub> getFavoriteClubList() {
        return favoriteClubList;
    }

    public void setFavoriteClubList(List<FavoriteClub> favoriteClubList) {
        this.favoriteClubList = favoriteClubList;
    }

    @XmlTransient
    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    public Competition getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(Competition idCompetition) {
        this.idCompetition = idCompetition;
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
        if (!(object instanceof League)) {
            return false;
        }
        League other = (League) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.League[ id=" + id + " ]";
    }
    
}
