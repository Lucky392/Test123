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
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "MATCH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Match.findAll", query = "SELECT m FROM Match m"),
    @NamedQuery(name = "Match.findById", query = "SELECT m FROM Match m WHERE m.id = :id"),
    @NamedQuery(name = "Match.findByIdSport1Match", query = "SELECT m FROM Match m WHERE m.idSport1Match = :idSport1Match"),
    @NamedQuery(name = "Match.findByUpdateAt", query = "SELECT m FROM Match m WHERE m.updateAt = :updateAt"),
    @NamedQuery(name = "Match.findByHomeScore", query = "SELECT m FROM Match m WHERE m.homeScore = :homeScore"),
    @NamedQuery(name = "Match.findByGuestScore", query = "SELECT m FROM Match m WHERE m.guestScore = :guestScore"),
    @NamedQuery(name = "Match.findByCreateDate", query = "SELECT m FROM Match m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "Match.findByStartTime", query = "SELECT m FROM Match m WHERE m.startTime = :startTime"),
    @NamedQuery(name = "Match.findByIsCalculated", query = "SELECT m FROM Match m WHERE m.isCalculated = :isCalculated")})
public class Match implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ID_SPORT1_MATCH")
    private String idSport1Match;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Column(name = "homeScore")
    private Integer homeScore;
    @Column(name = "guestScore")
    private Integer guestScore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "startTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "isCalculated")
    private Short isCalculated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatch")
    private List<MatchPlayer> matchPlayerList;
    @JoinColumn(name = "ID_GUEST_CLUB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Club idGuestClub;
    @JoinColumn(name = "ID_MATCHDAY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Matchday idMatchday;
    @JoinColumn(name = "ID_HOME_CLUB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Club idHomeClub;

    public Match() {
    }

    public Match(Long id) {
        this.id = id;
    }

    public Match(Long id, Date updateAt, Date createDate) {
        this.id = id;
        this.updateAt = updateAt;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Match() {
        return idSport1Match;
    }

    public void setIdSport1Match(String idSport1Match) {
        this.idSport1Match = idSport1Match;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(Integer guestScore) {
        this.guestScore = guestScore;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Short getIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(Short isCalculated) {
        this.isCalculated = isCalculated;
    }

    @XmlTransient
    @JsonIgnore
    public List<MatchPlayer> getMatchPlayerList() {
        return matchPlayerList;
    }

    public void setMatchPlayerList(List<MatchPlayer> matchPlayerList) {
        this.matchPlayerList = matchPlayerList;
    }

    public Club getIdGuestClub() {
        return idGuestClub;
    }

    public void setIdGuestClub(Club idGuestClub) {
        this.idGuestClub = idGuestClub;
    }

    public Matchday getIdMatchday() {
        return idMatchday;
    }

    public void setIdMatchday(Matchday idMatchday) {
        this.idMatchday = idMatchday;
    }

    public Club getIdHomeClub() {
        return idHomeClub;
    }

    public void setIdHomeClub(Club idHomeClub) {
        this.idHomeClub = idHomeClub;
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
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Match[ id=" + id + " ]";
    }
    
}
