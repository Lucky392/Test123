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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "AUCTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a"),
    @NamedQuery(name = "Auction.findById", query = "SELECT a FROM Auction a WHERE a.id = :id"),
    @NamedQuery(name = "Auction.findByStartPrice", query = "SELECT a FROM Auction a WHERE a.startPrice = :startPrice"),
    @NamedQuery(name = "Auction.findByIsFair", query = "SELECT a FROM Auction a WHERE a.isFair = :isFair"),
    @NamedQuery(name = "Auction.findByAuctionStartTimestamp", query = "SELECT a FROM Auction a WHERE a.auctionStartTimestamp = :auctionStartTimestamp"),
    @NamedQuery(name = "Auction.findByAuctionEndTimestamp", query = "SELECT a FROM Auction a WHERE a.auctionEndTimestamp = :auctionEndTimestamp"),
    @NamedQuery(name = "Auction.findByAuctionFinishedTimestamp", query = "SELECT a FROM Auction a WHERE a.auctionFinishedTimestamp = :auctionFinishedTimestamp"),
    @NamedQuery(name = "Auction.findByCreateDate", query = "SELECT a FROM Auction a WHERE a.createDate = :createDate")})
public class Auction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startPrice")
    private int startPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isFair")
    private short isFair;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auctionStartTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auctionStartTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auctionEndTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auctionEndTimestamp;
    @Column(name = "auctionFinishedTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auctionFinishedTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_FANTASY_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private FantasyLeague idFantasyLeague;
    @JoinColumn(name = "ID_FANTASY_CLUB_SELLER", referencedColumnName = "ID")
    @ManyToOne
    private FantasyClub idFantasyClubSeller;
    @JoinColumn(name = "ID_FANTASY_LEAGUE_PLAYER", referencedColumnName = "ID")
    @ManyToOne
    private FantasyLeaguePlayer idFantasyLeaguePlayer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuction")
    private List<Bid> bidList;

    public Auction() {
    }

    public Auction(Long id) {
        this.id = id;
    }

    public Auction(Long id, int startPrice, short isFair, Date auctionStartTimestamp, Date auctionEndTimestamp, Date createDate) {
        this.id = id;
        this.startPrice = startPrice;
        this.isFair = isFair;
        this.auctionStartTimestamp = auctionStartTimestamp;
        this.auctionEndTimestamp = auctionEndTimestamp;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public short getIsFair() {
        return isFair;
    }

    public void setIsFair(short isFair) {
        this.isFair = isFair;
    }

    public Date getAuctionStartTimestamp() {
        return auctionStartTimestamp;
    }

    public void setAuctionStartTimestamp(Date auctionStartTimestamp) {
        this.auctionStartTimestamp = auctionStartTimestamp;
    }

    public Date getAuctionEndTimestamp() {
        return auctionEndTimestamp;
    }

    public void setAuctionEndTimestamp(Date auctionEndTimestamp) {
        this.auctionEndTimestamp = auctionEndTimestamp;
    }

    public Date getAuctionFinishedTimestamp() {
        return auctionFinishedTimestamp;
    }

    public void setAuctionFinishedTimestamp(Date auctionFinishedTimestamp) {
        this.auctionFinishedTimestamp = auctionFinishedTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public FantasyLeague getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(FantasyLeague idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    public FantasyClub getIdFantasyClubSeller() {
        return idFantasyClubSeller;
    }

    public void setIdFantasyClubSeller(FantasyClub idFantasyClubSeller) {
        this.idFantasyClubSeller = idFantasyClubSeller;
    }

    public FantasyLeaguePlayer getIdFantasyLeaguePlayer() {
        return idFantasyLeaguePlayer;
    }

    public void setIdFantasyLeaguePlayer(FantasyLeaguePlayer idFantasyLeaguePlayer) {
        this.idFantasyLeaguePlayer = idFantasyLeaguePlayer;
    }

    @XmlTransient
    @JsonIgnore
    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
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
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Auction[ id=" + id + " ]";
    }
    
}
