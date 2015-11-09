/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.PremiumHistory;

/**
 *
 * @author stefan
 */
public class PremiumHistoryPOJO {

    private Long id;
    private Date createDate;
    private Integer charges;
    private Integer updatedCharges;
    private Integer premiumCurrency;
    private Integer updatedPremiumCurrency;
    private Long idReward;
    private Long idFantasyManager;
    private Long idFantasyClub;
    private Long idUser;
    private Long idPremiumAction;
    private Long idPremiumItem;
    private String reward;
    private String fantasyManager;
    private String fantasyClub;
    private String user;
    private String premiumAction;
    private String premiumItem;

    public PremiumHistoryPOJO(PremiumHistory premiumHistory) {
        this.id = premiumHistory.getId();
        this.createDate = premiumHistory.getCreateDate();
        this.charges = premiumHistory.getCharges();
        this.updatedCharges = premiumHistory.getUpdatedCharges();
        this.premiumCurrency = premiumHistory.getPremiumCurrency();
        this.updatedPremiumCurrency = premiumHistory.getUpdatedPremiumCurrency();
        if (premiumHistory.getIdReward() != null) {
            this.idReward = premiumHistory.getIdReward().getId();
            reward = premiumHistory.getIdReward().getName();
        }
        if (premiumHistory.getIdFantasyManager() != null) {
            this.idFantasyManager = premiumHistory.getIdFantasyManager().getId();
            fantasyManager = premiumHistory.getIdFantasyManager().getUsername();
        }
        if (premiumHistory.getIdFantasyClub() != null) {
            this.idFantasyClub = premiumHistory.getIdFantasyClub().getId();
            fantasyClub = premiumHistory.getIdFantasyClub().getName();
        }
        if (premiumHistory.getIdUser() != null) {
            this.idUser = premiumHistory.getIdUser().getId();
            user = premiumHistory.getIdUser().getEmail();
        }
        if (premiumHistory.getIdPremiumAction() != null) {
            this.idPremiumAction = premiumHistory.getIdPremiumAction().getId();
            premiumAction = premiumHistory.getIdPremiumAction().getName();
        }
        if (premiumHistory.getIdPremiumItem() != null) {
            this.idPremiumItem = premiumHistory.getIdPremiumItem().getId();
            premiumItem = premiumHistory.getIdPremiumItem().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public Integer getUpdatedCharges() {
        return updatedCharges;
    }

    public void setUpdatedCharges(Integer updatedCharges) {
        this.updatedCharges = updatedCharges;
    }

    public Integer getPremiumCurrency() {
        return premiumCurrency;
    }

    public void setPremiumCurrency(Integer premiumCurrency) {
        this.premiumCurrency = premiumCurrency;
    }

    public Integer getUpdatedPremiumCurrency() {
        return updatedPremiumCurrency;
    }

    public void setUpdatedPremiumCurrency(Integer updatedPremiumCurrency) {
        this.updatedPremiumCurrency = updatedPremiumCurrency;
    }

    public Long getIdReward() {
        return idReward;
    }

    public void setIdReward(Long idReward) {
        this.idReward = idReward;
    }

    public Long getIdFantasyManager() {
        return idFantasyManager;
    }

    public void setIdFantasyManager(Long idFantasyManager) {
        this.idFantasyManager = idFantasyManager;
    }

    public Long getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(Long idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdPremiumAction() {
        return idPremiumAction;
    }

    public void setIdPremiumAction(Long idPremiumAction) {
        this.idPremiumAction = idPremiumAction;
    }

    public Long getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(Long idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }

    public static List<PremiumHistoryPOJO> toPremiumHistoryPOJOList(List<PremiumHistory> list) {
        PremiumHistoryPOJO pojo;
        List<PremiumHistoryPOJO> pojos = new ArrayList<>();
        for (PremiumHistory ph : list) {
            pojo = new PremiumHistoryPOJO(ph);
            pojos.add(pojo);
        }
        return pojos;
    }

    public String getFantasyManager() {
        return fantasyManager;
    }

    public void setFantasyManager(String fantasyManager) {
        this.fantasyManager = fantasyManager;
    }

    public String getFantasyClub() {
        return fantasyClub;
    }

    public void setFantasyClub(String fantasyClub) {
        this.fantasyClub = fantasyClub;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPremiumAction() {
        return premiumAction;
    }

    public void setPremiumAction(String premiumAction) {
        this.premiumAction = premiumAction;
    }

    public String getPremiumItem() {
        return premiumItem;
    }

    public void setPremiumItem(String premiumItem) {
        this.premiumItem = premiumItem;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

}
