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
@Table(name = "BID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bid.findAll", query = "SELECT b FROM Bid b"),
    @NamedQuery(name = "Bid.findById", query = "SELECT b FROM Bid b WHERE b.id = :id"),
    @NamedQuery(name = "Bid.findByBiddingPrice", query = "SELECT b FROM Bid b WHERE b.biddingPrice = :biddingPrice"),
    @NamedQuery(name = "Bid.findByBiddingAtTimestamp", query = "SELECT b FROM Bid b WHERE b.biddingAtTimestamp = :biddingAtTimestamp"),
    @NamedQuery(name = "Bid.findByReturnMoneyTimestamp", query = "SELECT b FROM Bid b WHERE b.returnMoneyTimestamp = :returnMoneyTimestamp"),
    @NamedQuery(name = "Bid.findByBidManager", query = "SELECT b FROM Bid b WHERE b.bidManager = :bidManager"),
    @NamedQuery(name = "Bid.findByBidManagerPrice", query = "SELECT b FROM Bid b WHERE b.bidManagerPrice = :bidManagerPrice"),
    @NamedQuery(name = "Bid.findByCreateDate", query = "SELECT b FROM Bid b WHERE b.createDate = :createDate")})
public class Bid implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "biddingPrice")
    private Integer biddingPrice;
    @Column(name = "biddingAtTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biddingAtTimestamp;
    @Column(name = "returnMoneyTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnMoneyTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bidManager")
    private short bidManager;
    @Column(name = "bidManagerPrice")
    private Integer bidManagerPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_AUCTION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Auction idAuction;
    @JoinColumn(name = "ID_FANTASY_CLUB_BIDDER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyClub idFantasyClubBidder;

    public Bid() {
    }

    public Bid(Long id) {
        this.id = id;
    }

    public Bid(Long id, short bidManager, Date createDate) {
        this.id = id;
        this.bidManager = bidManager;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(Integer biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public Date getBiddingAtTimestamp() {
        return biddingAtTimestamp;
    }

    public void setBiddingAtTimestamp(Date biddingAtTimestamp) {
        this.biddingAtTimestamp = biddingAtTimestamp;
    }

    public Date getReturnMoneyTimestamp() {
        return returnMoneyTimestamp;
    }

    public void setReturnMoneyTimestamp(Date returnMoneyTimestamp) {
        this.returnMoneyTimestamp = returnMoneyTimestamp;
    }

    public short getBidManager() {
        return bidManager;
    }

    public void setBidManager(short bidManager) {
        this.bidManager = bidManager;
    }

    public Integer getBidManagerPrice() {
        return bidManagerPrice;
    }

    public void setBidManagerPrice(Integer bidManagerPrice) {
        this.bidManagerPrice = bidManagerPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Auction getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(Auction idAuction) {
        this.idAuction = idAuction;
    }

    public FantasyClub getIdFantasyClubBidder() {
        return idFantasyClubBidder;
    }

    public void setIdFantasyClubBidder(FantasyClub idFantasyClubBidder) {
        this.idFantasyClubBidder = idFantasyClubBidder;
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
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Bid[ id=" + id + " ]";
    }
    
}
