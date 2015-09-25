/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "FANTASY_CLUB_VALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyClubValuation.findAll", query = "SELECT f FROM FantasyClubValuation f"),
    @NamedQuery(name = "FantasyClubValuation.findById", query = "SELECT f FROM FantasyClubValuation f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyClubValuation.findByMarketValue", query = "SELECT f FROM FantasyClubValuation f WHERE f.marketValue = :marketValue"),
    @NamedQuery(name = "FantasyClubValuation.findByMarketValueUpdateAt", query = "SELECT f FROM FantasyClubValuation f WHERE f.marketValueUpdateAt = :marketValueUpdateAt"),
    @NamedQuery(name = "FantasyClubValuation.findByWinMatchdays", query = "SELECT f FROM FantasyClubValuation f WHERE f.winMatchdays = :winMatchdays"),
    @NamedQuery(name = "FantasyClubValuation.findByWinMatchdaysUpdateAt", query = "SELECT f FROM FantasyClubValuation f WHERE f.winMatchdaysUpdateAt = :winMatchdaysUpdateAt"),
    @NamedQuery(name = "FantasyClubValuation.findByBlmPoints", query = "SELECT f FROM FantasyClubValuation f WHERE f.blmPoints = :blmPoints"),
    @NamedQuery(name = "FantasyClubValuation.findByBlmPointsUpdateAt", query = "SELECT f FROM FantasyClubValuation f WHERE f.blmPointsUpdateAt = :blmPointsUpdateAt"),
    @NamedQuery(name = "FantasyClubValuation.findByPosition", query = "SELECT f FROM FantasyClubValuation f WHERE f.position = :position"),
    @NamedQuery(name = "FantasyClubValuation.findByPositionUpdateAt", query = "SELECT f FROM FantasyClubValuation f WHERE f.positionUpdateAt = :positionUpdateAt"),
    @NamedQuery(name = "FantasyClubValuation.findByBlmPointsDiff", query = "SELECT f FROM FantasyClubValuation f WHERE f.blmPointsDiff = :blmPointsDiff"),
    @NamedQuery(name = "FantasyClubValuation.findByPositionDiff", query = "SELECT f FROM FantasyClubValuation f WHERE f.positionDiff = :positionDiff"),
    @NamedQuery(name = "FantasyClubValuation.findByCreditDiff", query = "SELECT f FROM FantasyClubValuation f WHERE f.creditDiff = :creditDiff"),
    @NamedQuery(name = "FantasyClubValuation.findByCreateDate", query = "SELECT f FROM FantasyClubValuation f WHERE f.createDate = :createDate")})
public class FantasyClubValuation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "marketValue")
    private Integer marketValue;
    @Column(name = "marketValueUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date marketValueUpdateAt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "winMatchdays")
    private Double winMatchdays;
    @Column(name = "winMatchdaysUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date winMatchdaysUpdateAt;
    @Column(name = "blmPoints")
    private Integer blmPoints;
    @Column(name = "blmPointsUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date blmPointsUpdateAt;
    @Column(name = "position")
    private Integer position;
    @Column(name = "positionUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date positionUpdateAt;
    @Column(name = "blmPointsDiff")
    private Integer blmPointsDiff;
    @Column(name = "positionDiff")
    private Integer positionDiff;
    @Column(name = "creditDiff")
    private Integer creditDiff;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_FANTASY_CLUB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyClub idFantasyClub;
    @JoinColumn(name = "ID_MATCHDAY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Matchday idMatchday;

    public FantasyClubValuation() {
    }

    public FantasyClubValuation(Long id) {
        this.id = id;
    }

    public FantasyClubValuation(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Integer marketValue) {
        this.marketValue = marketValue;
    }

    public Date getMarketValueUpdateAt() {
        return marketValueUpdateAt;
    }

    public void setMarketValueUpdateAt(Date marketValueUpdateAt) {
        this.marketValueUpdateAt = marketValueUpdateAt;
    }

    public Double getWinMatchdays() {
        return winMatchdays;
    }

    public void setWinMatchdays(Double winMatchdays) {
        this.winMatchdays = winMatchdays;
    }

    public Date getWinMatchdaysUpdateAt() {
        return winMatchdaysUpdateAt;
    }

    public void setWinMatchdaysUpdateAt(Date winMatchdaysUpdateAt) {
        this.winMatchdaysUpdateAt = winMatchdaysUpdateAt;
    }

    public Integer getBlmPoints() {
        return blmPoints;
    }

    public void setBlmPoints(Integer blmPoints) {
        this.blmPoints = blmPoints;
    }

    public Date getBlmPointsUpdateAt() {
        return blmPointsUpdateAt;
    }

    public void setBlmPointsUpdateAt(Date blmPointsUpdateAt) {
        this.blmPointsUpdateAt = blmPointsUpdateAt;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getPositionUpdateAt() {
        return positionUpdateAt;
    }

    public void setPositionUpdateAt(Date positionUpdateAt) {
        this.positionUpdateAt = positionUpdateAt;
    }

    public Integer getBlmPointsDiff() {
        return blmPointsDiff;
    }

    public void setBlmPointsDiff(Integer blmPointsDiff) {
        this.blmPointsDiff = blmPointsDiff;
    }

    public Integer getPositionDiff() {
        return positionDiff;
    }

    public void setPositionDiff(Integer positionDiff) {
        this.positionDiff = positionDiff;
    }

    public Integer getCreditDiff() {
        return creditDiff;
    }

    public void setCreditDiff(Integer creditDiff) {
        this.creditDiff = creditDiff;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public FantasyClub getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(FantasyClub idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    @XmlTransient
    @JsonIgnore
    public Matchday getIdMatchday() {
        return idMatchday;
    }

    public void setIdMatchday(Matchday idMatchday) {
        this.idMatchday = idMatchday;
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
        if (!(object instanceof FantasyClubValuation)) {
            return false;
        }
        FantasyClubValuation other = (FantasyClubValuation) object;
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
