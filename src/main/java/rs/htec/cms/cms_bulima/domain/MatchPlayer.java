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

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "MATCH_PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchPlayer.findAll", query = "SELECT m FROM MatchPlayer m"),
    @NamedQuery(name = "MatchPlayer.findById", query = "SELECT m FROM MatchPlayer m WHERE m.id = :id"),
    @NamedQuery(name = "MatchPlayer.findByUpdateAt", query = "SELECT m FROM MatchPlayer m WHERE m.updateAt = :updateAt"),
    @NamedQuery(name = "MatchPlayer.findByRating", query = "SELECT m FROM MatchPlayer m WHERE m.rating = :rating"),
    @NamedQuery(name = "MatchPlayer.findByRatingAt", query = "SELECT m FROM MatchPlayer m WHERE m.ratingAt = :ratingAt"),
    @NamedQuery(name = "MatchPlayer.findByBlmPoints", query = "SELECT m FROM MatchPlayer m WHERE m.blmPoints = :blmPoints"),
    @NamedQuery(name = "MatchPlayer.findByBlmPointsUpdateAt", query = "SELECT m FROM MatchPlayer m WHERE m.blmPointsUpdateAt = :blmPointsUpdateAt"),
    @NamedQuery(name = "MatchPlayer.findByExtra1", query = "SELECT m FROM MatchPlayer m WHERE m.extra1 = :extra1"),
    @NamedQuery(name = "MatchPlayer.findByExtra2", query = "SELECT m FROM MatchPlayer m WHERE m.extra2 = :extra2"),
    @NamedQuery(name = "MatchPlayer.findByCreateDate", query = "SELECT m FROM MatchPlayer m WHERE m.createDate = :createDate")})
public class MatchPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Double rating;
    @Column(name = "ratingAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ratingAt;
    @Column(name = "blmPoints")
    private Integer blmPoints;
    @Column(name = "blmPointsUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date blmPointsUpdateAt;
    @Column(name = "extra1")
    private Short extra1;
    @Column(name = "extra2")
    private Short extra2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_MATCH", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Match idMatch;
    @JoinColumn(name = "ID_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Player idPlayer;

    public MatchPlayer() {
    }

    public MatchPlayer(Long id) {
        this.id = id;
    }

    public MatchPlayer(Long id, Date updateAt, Date createDate) {
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getRatingAt() {
        return ratingAt;
    }

    public void setRatingAt(Date ratingAt) {
        this.ratingAt = ratingAt;
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

    public Short getExtra1() {
        return extra1;
    }

    public void setExtra1(Short extra1) {
        this.extra1 = extra1;
    }

    public Short getExtra2() {
        return extra2;
    }

    public void setExtra2(Short extra2) {
        this.extra2 = extra2;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Match getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Match idMatch) {
        this.idMatch = idMatch;
    }

    public Player getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = idPlayer;
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
        if (!(object instanceof MatchPlayer)) {
            return false;
        }
        MatchPlayer other = (MatchPlayer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchPlayer[ id=" + id + " ]";
    }
    
}
