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
@Table(name = "FANTASY_CLUB_LINE_UP_PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyClubLineUpPlayer.findAll", query = "SELECT f FROM FantasyClubLineUpPlayer f"),
    @NamedQuery(name = "FantasyClubLineUpPlayer.findById", query = "SELECT f FROM FantasyClubLineUpPlayer f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyClubLineUpPlayer.findByIsCaptain", query = "SELECT f FROM FantasyClubLineUpPlayer f WHERE f.isCaptain = :isCaptain"),
    @NamedQuery(name = "FantasyClubLineUpPlayer.findByCreateDate", query = "SELECT f FROM FantasyClubLineUpPlayer f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyClubLineUpPlayer.findByLineUpId", query = "SELECT f FROM FantasyClubLineUpPlayer f WHERE f.idLineUp.id = :idLineUp ORDER BY f.idPlayerSlot.id")})
public class FantasyClubLineUpPlayer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "isCaptain")
    private Short isCaptain;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_PLAYER_SLOT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PlayerSlot idPlayerSlot;
    @JoinColumn(name = "ID_LEAGUE_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyLeaguePlayer idLeaguePlayer;
    @JoinColumn(name = "ID_LINE_UP", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyClubLineUp idLineUp;

    public FantasyClubLineUpPlayer() {
    }

    public FantasyClubLineUpPlayer(Long id) {
        this.id = id;
    }

    public FantasyClubLineUpPlayer(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Short isCaptain) {
        this.isCaptain = isCaptain;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public PlayerSlot getIdPlayerSlot() {
        return idPlayerSlot;
    }

    public void setIdPlayerSlot(PlayerSlot idPlayerSlot) {
        this.idPlayerSlot = idPlayerSlot;
    }

    
    public FantasyLeaguePlayer getIdLeaguePlayer() {
        return idLeaguePlayer;
    }

    public void setIdLeaguePlayer(FantasyLeaguePlayer idLeaguePlayer) {
        this.idLeaguePlayer = idLeaguePlayer;
    }

    public FantasyClubLineUp getIdLineUp() {
        return idLineUp;
    }

    public void setIdLineUp(FantasyClubLineUp idLineUp) {
        this.idLineUp = idLineUp;
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
        if (!(object instanceof FantasyClubLineUpPlayer)) {
            return false;
        }
        FantasyClubLineUpPlayer other = (FantasyClubLineUpPlayer) object;
        if ((this.id == null && other.id != null) || (this.id != null && this.id.equals(other.id)
                && this.idPlayerSlot.getId().equals(other.idPlayerSlot.getId()) && this.isCaptain.equals(other.isCaptain))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer[ id=" + id + " ]";
    }

}
