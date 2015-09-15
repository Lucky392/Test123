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
@Table(name = "MATCHDAY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matchday.findAll", query = "SELECT m FROM Matchday m"),
    @NamedQuery(name = "Matchday.findById", query = "SELECT m FROM Matchday m WHERE m.id = :id"),
    @NamedQuery(name = "Matchday.findByIdSport1Matchday", query = "SELECT m FROM Matchday m WHERE m.idSport1Matchday = :idSport1Matchday"),
    @NamedQuery(name = "Matchday.findByMatchday", query = "SELECT m FROM Matchday m WHERE m.matchday = :matchday"),
    @NamedQuery(name = "Matchday.findByIsCurrent", query = "SELECT m FROM Matchday m WHERE m.isCurrent = :isCurrent"),
    @NamedQuery(name = "Matchday.findByStartDate", query = "SELECT m FROM Matchday m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "Matchday.findByEndDate", query = "SELECT m FROM Matchday m WHERE m.endDate = :endDate"),
    @NamedQuery(name = "Matchday.findByCreateDate", query = "SELECT m FROM Matchday m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "Matchday.findByIsCalculated", query = "SELECT m FROM Matchday m WHERE m.isCalculated = :isCalculated"),
    @NamedQuery(name = "Matchday.findByIsCompleted", query = "SELECT m FROM Matchday m WHERE m.isCompleted = :isCompleted")})
public class Matchday implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ID_SPORT1_MATCHDAY")
    private String idSport1Matchday;
    @Basic(optional = false)
    @NotNull
    @Column(name = "matchday")
    private int matchday;
    @Column(name = "isCurrent")
    private Short isCurrent;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "isCalculated")
    private Short isCalculated;
    @Column(name = "isCompleted")
    private Short isCompleted;
    @OneToMany(mappedBy = "idFirstMatchday")
    private List<Season> seasonList;
    @JoinColumn(name = "ID_SEASON", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Season idSeason;
    @OneToMany(mappedBy = "idBlockStartMatchday")
    private List<Player> playerList;

    public Matchday() {
    }

    public Matchday(Long id) {
        this.id = id;
    }

    public Matchday(Long id, int matchday, Date createDate) {
        this.id = id;
        this.matchday = matchday;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Matchday() {
        return idSport1Matchday;
    }

    public void setIdSport1Matchday(String idSport1Matchday) {
        this.idSport1Matchday = idSport1Matchday;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public Short getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Short isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(Short isCalculated) {
        this.isCalculated = isCalculated;
    }

    public Short getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Short isCompleted) {
        this.isCompleted = isCompleted;
    }

    @XmlTransient
    @JsonIgnore
    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    public Season getIdSeason() {
        return idSeason;
    }

    public void setIdSeason(Season idSeason) {
        this.idSeason = idSeason;
    }

    @XmlTransient
    @JsonIgnore
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
        if (!(object instanceof Matchday)) {
            return false;
        }
        Matchday other = (Matchday) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }
    
}
