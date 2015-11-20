/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.Auction;
import rs.htec.cms.cms_bulima.domain.Bid;
import rs.htec.cms.cms_bulima.domain.FantasyClub;

/**
 *
 * @author stefan
 */
public class BidPOJO {

    private Long id;
    private Integer biddingPrice;
    private Date biddingAtTimestamp;
    private Date returnMoneyTimestamp;
    private short bidManager;
    private Integer bidManagerPrice;
    private Date createDate;
    private Long idAuction;
    private Long idFantasyClubBidder;
    private String fantasyClubBidderName;

    public BidPOJO(Bid bid) {
        this.id = bid.getId();
        this.biddingPrice = bid.getBiddingPrice();
        this.biddingAtTimestamp = bid.getBiddingAtTimestamp();
        this.returnMoneyTimestamp = bid.getReturnMoneyTimestamp();
        this.bidManager = bid.getBidManager();
        this.bidManagerPrice = bid.getBidManagerPrice();
        this.createDate = bid.getCreateDate();
        if (bid.getIdAuction() != null) {
            this.idAuction = bid.getIdAuction().getId();
        }
        if (bid.getIdFantasyClubBidder() != null) {
            this.idFantasyClubBidder = bid.getIdFantasyClubBidder().getId();
            this.fantasyClubBidderName = bid.getIdFantasyClubBidder().getName();
        }
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

    public Long getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(Long idAuction) {
        this.idAuction = idAuction;
    }

    public Long getIdFantasyClubBidder() {
        return idFantasyClubBidder;
    }

    public void setIdFantasyClubBidder(Long idFantasyClubBidder) {
        this.idFantasyClubBidder = idFantasyClubBidder;
    }

    public String getFantasyClubBidderName() {
        return fantasyClubBidderName;
    }

    public void setFantasyClubBidderName(String fantasyClubBidderName) {
        this.fantasyClubBidderName = fantasyClubBidderName;
    }

    
    public static List<BidPOJO> toBidPOJOList (List<Bid> list) {
        BidPOJO pojo;
        List<BidPOJO> pojos = new ArrayList<>();
        for (Bid bid : list) {
            pojo = new BidPOJO(bid);
            pojos.add(pojo);
        }
        return pojos;
    }
}
