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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyPlayer.findAll", query = "SELECT f FROM FantasyPlayer f"),
    @NamedQuery(name = "FantasyPlayer.findById", query = "SELECT f FROM FantasyPlayer f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyPlayer.findByJoinFantasyClubTimestamp", query = "SELECT f FROM FantasyPlayer f WHERE f.joinFantasyClubTimestamp = :joinFantasyClubTimestamp"),
    @NamedQuery(name = "FantasyPlayer.findByCreateDate", query = "SELECT f FROM FantasyPlayer f WHERE f.createDate = :createDate")})
public class FantasyPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "joinFantasyClubTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinFantasyClubTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idCaptain")
    private List<FantasyClub> fantasyClubList;
    @JoinColumn(name = "ID_LEAGUE_PLAYER", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private FantasyLeaguePlayer idLeaguePlayer;
    @JoinColumn(name = "ID_PLAYER_SLOT", referencedColumnName = "ID")
    @ManyToOne
    private PlayerSlot idPlayerSlot;
    @JoinColumn(name = "ID_FANTASY_CLUB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyClub idFantasyClub;

    public FantasyPlayer() {
    }

    public FantasyPlayer(Long id) {
        this.id = id;
    }

    public FantasyPlayer(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJoinFantasyClubTimestamp() {
        return joinFantasyClubTimestamp;
    }

    public void setJoinFantasyClubTimestamp(Date joinFantasyClubTimestamp) {
        this.joinFantasyClubTimestamp = joinFantasyClubTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyClub> getFantasyClubList() {
        return fantasyClubList;
    }

    public void setFantasyClubList(List<FantasyClub> fantasyClubList) {
        this.fantasyClubList = fantasyClubList;
    }

    public FantasyLeaguePlayer getIdLeaguePlayer() {
        return idLeaguePlayer;
    }

    public void setIdLeaguePlayer(FantasyLeaguePlayer idLeaguePlayer) {
        this.idLeaguePlayer = idLeaguePlayer;
    }

    public PlayerSlot getIdPlayerSlot() {
        return idPlayerSlot;
    }

    public void setIdPlayerSlot(PlayerSlot idPlayerSlot) {
        this.idPlayerSlot = idPlayerSlot;
    }

    public FantasyClub getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(FantasyClub idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
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
        if (!(object instanceof FantasyPlayer)) {
            return false;
        }
        FantasyPlayer other = (FantasyPlayer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyPlayer[ id=" + id + " ]";
    }
    
}
