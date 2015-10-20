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
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_MANAGER_MATCHDAY_CHALLENGE_LINE_UP_PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUpPlayer.findAll", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUpPlayer f"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUpPlayer.findById", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUpPlayer f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUpPlayer.findBySlot", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUpPlayer f WHERE f.slot = :slot"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUpPlayer.findByCreateDate", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUpPlayer f WHERE f.createDate = :createDate")})
public class FantasyManagerMatchdayChallengeLineUpPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "slot")
    private Integer slot;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MatchdayChallengePlayer idMatchdayChallengePlayer;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE_LINE_UP", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyManagerMatchdayChallengeLineUp idMatchdayChallengeLineUp;

    public FantasyManagerMatchdayChallengeLineUpPlayer() {
    }

    public FantasyManagerMatchdayChallengeLineUpPlayer(Long id) {
        this.id = id;
    }

    public FantasyManagerMatchdayChallengeLineUpPlayer(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public MatchdayChallengePlayer getIdMatchdayChallengePlayer() {
        return idMatchdayChallengePlayer;
    }

    public void setIdMatchdayChallengePlayer(MatchdayChallengePlayer idMatchdayChallengePlayer) {
        this.idMatchdayChallengePlayer = idMatchdayChallengePlayer;
    }

    public FantasyManagerMatchdayChallengeLineUp getIdMatchdayChallengeLineUp() {
        return idMatchdayChallengeLineUp;
    }

    public void setIdMatchdayChallengeLineUp(FantasyManagerMatchdayChallengeLineUp idMatchdayChallengeLineUp) {
        this.idMatchdayChallengeLineUp = idMatchdayChallengeLineUp;
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
        if (!(object instanceof FantasyManagerMatchdayChallengeLineUpPlayer)) {
            return false;
        }
        FantasyManagerMatchdayChallengeLineUpPlayer other = (FantasyManagerMatchdayChallengeLineUpPlayer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUpPlayer[ id=" + id + " ]";
    }
    
}
