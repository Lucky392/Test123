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
import javax.persistence.OneToOne;
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
@Table(name = "FANTASY_MANAGER_MATCHDAY_CHALLENGE_LINE_UP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUp.findAll", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUp f"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUp.findById", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUp f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUp.findByFormation", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUp f WHERE f.formation = :formation"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeLineUp.findByCreateDate", query = "SELECT f FROM FantasyManagerMatchdayChallengeLineUp f WHERE f.createDate = :createDate")})
public class FantasyManagerMatchdayChallengeLineUp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "formation")
    private String formation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallengeLineUp")
    private List<FantasyManagerMatchdayChallengeLineUpPlayer> fantasyManagerMatchdayChallengeLineUpPlayerList;
    @JoinColumn(name = "ID_FANTASY_MANAGER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyManager idFantasyManager;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MatchdayChallenge idMatchdayChallenge;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallengeLineUp")
    private FantasyManagerMatchdayChallengeResult fantasyManagerMatchdayChallengeResult;

    public FantasyManagerMatchdayChallengeLineUp() {
    }

    public FantasyManagerMatchdayChallengeLineUp(Long id) {
        this.id = id;
    }

    public FantasyManagerMatchdayChallengeLineUp(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyManagerMatchdayChallengeLineUpPlayer> getFantasyManagerMatchdayChallengeLineUpPlayerList() {
        return fantasyManagerMatchdayChallengeLineUpPlayerList;
    }

    public void setFantasyManagerMatchdayChallengeLineUpPlayerList(List<FantasyManagerMatchdayChallengeLineUpPlayer> fantasyManagerMatchdayChallengeLineUpPlayerList) {
        this.fantasyManagerMatchdayChallengeLineUpPlayerList = fantasyManagerMatchdayChallengeLineUpPlayerList;
    }

    public FantasyManager getIdFantasyManager() {
        return idFantasyManager;
    }

    public void setIdFantasyManager(FantasyManager idFantasyManager) {
        this.idFantasyManager = idFantasyManager;
    }

    public MatchdayChallenge getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(MatchdayChallenge idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
    }

    public FantasyManagerMatchdayChallengeResult getFantasyManagerMatchdayChallengeResult() {
        return fantasyManagerMatchdayChallengeResult;
    }

    public void setFantasyManagerMatchdayChallengeResult(FantasyManagerMatchdayChallengeResult fantasyManagerMatchdayChallengeResult) {
        this.fantasyManagerMatchdayChallengeResult = fantasyManagerMatchdayChallengeResult;
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
        if (!(object instanceof FantasyManagerMatchdayChallengeLineUp)) {
            return false;
        }
        FantasyManagerMatchdayChallengeLineUp other = (FantasyManagerMatchdayChallengeLineUp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUp[ id=" + id + " ]";
    }
    
}
