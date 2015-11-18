/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import rs.htec.cms.cms_bulima.domain.Auction;
import rs.htec.cms.cms_bulima.domain.Bid;
import rs.htec.cms.cms_bulima.domain.FantasyClubCreditHistory;

/**
 *
 * @author lazar
 */
@XmlRootElement
public class AuctionPOJO {

    private List<FantasyClubCreditHistory> fantasyClubCreditHistoryList;
    private Long id;
    private int startPrice;
    private short isFair;
    private Date auctionStartTimestamp;
    private Date auctionEndTimestamp;
    private Date auctionFinishedTimestamp;
    private Date createDate;
    private long idFantasyLeague;
    private String fantasyLeague;
    private long idFantasyClubSeller;
    private String fantasyClubSeller;
    private long idFantasyLeaguePlayer;
    private String fantasyLeaguePlayer;
    private List<Bid> bidList;

    public AuctionPOJO(Auction auction) {
        fantasyClubCreditHistoryList = auction.getFantasyClubCreditHistoryList();
        id = auction.getId();
        startPrice = auction.getStartPrice();
        isFair = auction.getIsFair();
        auctionStartTimestamp = auction.getAuctionStartTimestamp();
        auctionEndTimestamp = auction.getAuctionEndTimestamp();
        auctionFinishedTimestamp = auction.getAuctionFinishedTimestamp();
        createDate = auction.getCreateDate();
        idFantasyLeague = auction.getIdFantasyLeague().getId();
        fantasyLeague = auction.getIdFantasyLeague().getName();
        idFantasyClubSeller = auction.getIdFantasyClubSeller().getId();
        fantasyClubSeller = auction.getIdFantasyClubSeller().getName();
        idFantasyLeaguePlayer = auction.getIdFantasyLeaguePlayer().getId();
        fantasyLeaguePlayer = auction.getIdFantasyLeaguePlayer().getIdPlayer().getFullname();
        bidList = auction.getBidList();
    }

    public List<FantasyClubCreditHistory> getFantasyClubCreditHistoryList() {
        return fantasyClubCreditHistoryList;
    }

    public void setFantasyClubCreditHistoryList(List<FantasyClubCreditHistory> fantasyClubCreditHistoryList) {
        this.fantasyClubCreditHistoryList = fantasyClubCreditHistoryList;
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

    public long getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(long idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    public String getFantasyLeague() {
        return fantasyLeague;
    }

    public void setFantasyLeague(String fantasyLeague) {
        this.fantasyLeague = fantasyLeague;
    }

    public long getIdFantasyClubSeller() {
        return idFantasyClubSeller;
    }

    public void setIdFantasyClubSeller(long idFantasyClubSeller) {
        this.idFantasyClubSeller = idFantasyClubSeller;
    }

    public String getFantasyClubSeller() {
        return fantasyClubSeller;
    }

    public void setFantasyClubSeller(String fantasyClubSeller) {
        this.fantasyClubSeller = fantasyClubSeller;
    }

    public long getIdFantasyLeaguePlayer() {
        return idFantasyLeaguePlayer;
    }

    public void setIdFantasyLeaguePlayer(long idFantasyLeaguePlayer) {
        this.idFantasyLeaguePlayer = idFantasyLeaguePlayer;
    }

    public String getFantasyLeaguePlayer() {
        return fantasyLeaguePlayer;
    }

    public void setFantasyLeaguePlayer(String fantasyLeaguePlayer) {
        this.fantasyLeaguePlayer = fantasyLeaguePlayer;
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

}
