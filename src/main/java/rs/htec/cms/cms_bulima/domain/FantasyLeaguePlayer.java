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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_LEAGUE_PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyLeaguePlayer.findAll", query = "SELECT f FROM FantasyLeaguePlayer f"),
    @NamedQuery(name = "FantasyLeaguePlayer.findById", query = "SELECT f FROM FantasyLeaguePlayer f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyLeaguePlayer.findByHasClub", query = "SELECT f FROM FantasyLeaguePlayer f WHERE f.hasClub = :hasClub"),
    @NamedQuery(name = "FantasyLeaguePlayer.findByIsInAuction", query = "SELECT f FROM FantasyLeaguePlayer f WHERE f.isInAuction = :isInAuction"),
    @NamedQuery(name = "FantasyLeaguePlayer.findByCreateDate", query = "SELECT f FROM FantasyLeaguePlayer f WHERE f.createDate = :createDate")})
public class FantasyLeaguePlayer implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLeaguePlayer")
    private List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayerList;
    @OneToMany(mappedBy = "idFantasyLeaguePlayer")
    private List<Auction> auctionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasClub")
    private short hasClub;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isInAuction")
    private short isInAuction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idLeaguePlayer")
    private FantasyPlayer fantasyPlayer;
    @JoinColumn(name = "ID_FANTASY_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private FantasyLeague idFantasyLeague;
    @JoinColumn(name = "ID_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Player idPlayer;

    public FantasyLeaguePlayer() {
    }

    public FantasyLeaguePlayer(Long id) {
        this.id = id;
    }

    public FantasyLeaguePlayer(Long id, short hasClub, short isInAuction, Date createDate) {
        this.id = id;
        this.hasClub = hasClub;
        this.isInAuction = isInAuction;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getHasClub() {
        return hasClub;
    }

    public void setHasClub(short hasClub) {
        this.hasClub = hasClub;
    }

    public short getIsInAuction() {
        return isInAuction;
    }

    public void setIsInAuction(short isInAuction) {
        this.isInAuction = isInAuction;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public FantasyPlayer getFantasyPlayer() {
        return fantasyPlayer;
    }

    public void setFantasyPlayer(FantasyPlayer fantasyPlayer) {
        this.fantasyPlayer = fantasyPlayer;
    }

    @XmlTransient
    @JsonIgnore
    public FantasyLeague getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(FantasyLeague idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    @XmlTransient
    @JsonIgnore
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
        if (!(object instanceof FantasyLeaguePlayer)) {
            return false;
        }
        FantasyLeaguePlayer other = (FantasyLeaguePlayer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }

    @XmlTransient
    @JsonIgnore
    public List<Auction> getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(List<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyClubLineUpPlayer> getFantasyClubLineUpPlayerList() {
        return fantasyClubLineUpPlayerList;
    }

    public void setFantasyClubLineUpPlayerList(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayerList) {
        this.fantasyClubLineUpPlayerList = fantasyClubLineUpPlayerList;
    }
    
}
