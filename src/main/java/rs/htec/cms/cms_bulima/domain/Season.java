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
@Table(name = "SEASON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Season.findAll", query = "SELECT s FROM Season s"),
    @NamedQuery(name = "Season.findById", query = "SELECT s FROM Season s WHERE s.id = :id"),
    @NamedQuery(name = "Season.findByIdSport1Season", query = "SELECT s FROM Season s WHERE s.idSport1Season = :idSport1Season"),
    @NamedQuery(name = "Season.findByName", query = "SELECT s FROM Season s WHERE s.name = :name"),
    @NamedQuery(name = "Season.findByCreateDate", query = "SELECT s FROM Season s WHERE s.createDate = :createDate")})
public class Season implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ID_SPORT1_SEASON")
    private String idSport1Season;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_FIRST_MATCHDAY", referencedColumnName = "ID")
    @ManyToOne
    private Matchday idFirstMatchday;
    @JoinColumn(name = "ID_LEAGUE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private League idLeague;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeason")
    private List<Matchday> matchdayList;
    @OneToMany(mappedBy = "idSeasonCurrent")
    private List<Player> playerList;

    public Season() {
    }

    public Season(Long id) {
        this.id = id;
    }

    public Season(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Season() {
        return idSport1Season;
    }

    public void setIdSport1Season(String idSport1Season) {
        this.idSport1Season = idSport1Season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Matchday getIdFirstMatchday() {
        return idFirstMatchday;
    }

    public void setIdFirstMatchday(Matchday idFirstMatchday) {
        this.idFirstMatchday = idFirstMatchday;
    }

    public League getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(League idLeague) {
        this.idLeague = idLeague;
    }

    @XmlTransient
    public List<Matchday> getMatchdayList() {
        return matchdayList;
    }

    public void setMatchdayList(List<Matchday> matchdayList) {
        this.matchdayList = matchdayList;
    }

    @XmlTransient
    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
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
        if (!(object instanceof Season)) {
            return false;
        }
        Season other = (Season) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Season[ id=" + id + " ]";
    }
    
}
