/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "MATCHDAY_CHALLENGE_PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchdayChallengePlayer.findAll", query = "SELECT m FROM MatchdayChallengePlayer m"),
    @NamedQuery(name = "MatchdayChallengePlayer.findById", query = "SELECT m FROM MatchdayChallengePlayer m WHERE m.id = :id")})
public class MatchdayChallengePlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MatchdayChallenge idMatchdayChallenge;
    @JoinColumn(name = "ID_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Player idPlayer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallengePlayer")
    private List<FantasyManagerMatchdayChallengeLineUpPlayer> fantasyManagerMatchdayChallengeLineUpPlayerList;

    public MatchdayChallengePlayer() {
    }

    public MatchdayChallengePlayer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    @JsonIgnore
    public MatchdayChallenge getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(MatchdayChallenge idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
    }

    public Player getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = idPlayer;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyManagerMatchdayChallengeLineUpPlayer> getFantasyManagerMatchdayChallengeLineUpPlayerList() {
        return fantasyManagerMatchdayChallengeLineUpPlayerList;
    }

    public void setFantasyManagerMatchdayChallengeLineUpPlayerList(List<FantasyManagerMatchdayChallengeLineUpPlayer> fantasyManagerMatchdayChallengeLineUpPlayerList) {
        this.fantasyManagerMatchdayChallengeLineUpPlayerList = fantasyManagerMatchdayChallengeLineUpPlayerList;
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
        if (!(object instanceof MatchdayChallengePlayer)) {
            return false;
        }
        MatchdayChallengePlayer other = (MatchdayChallengePlayer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchdayChallengePlayer[ id=" + id + " ]";
    }
    
}
