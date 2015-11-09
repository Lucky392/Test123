/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.Reward;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class RewardPOJO {
    
    private Long id;
    private String name;
    private String description;
    private Integer amountPremiumItem;
    private Integer amountPremiumCurrency;
    private Integer amountIngameCurrency;
    private Integer chance;
    private Date updateAt;
    private Date createDate;
    private String imageUrl;
    private Long idPremiumItem;
    private String premiumItemName;
    private String urlToPremiumItem;

    public RewardPOJO(Reward reward) {
        this.id = reward.getId();
        this.name = reward.getName();
        this.description = reward.getDescription();
        this.amountPremiumItem = reward.getAmountPremiumItem();
        this.amountPremiumCurrency = reward.getAmountPremiumCurrency();
        this.amountIngameCurrency = reward.getAmountIngameCurrency();
        this.chance = reward.getChance();
        this.updateAt = reward.getUpdateAt();
        this.createDate = reward.getCreateDate();
        this.imageUrl = reward.getImageUrl();
        this.idPremiumItem = reward.getIdPremiumItem().getId();
        if (reward.getIdPremiumItem() != null) {
            this.urlToPremiumItem = Util.getInstance().getUrl() + "rest/items/" + reward.getIdPremiumItem().getId();
            premiumItemName = reward.getIdPremiumItem().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmountPremiumItem() {
        return amountPremiumItem;
    }

    public void setAmountPremiumItem(Integer amountPremiumItem) {
        this.amountPremiumItem = amountPremiumItem;
    }

    public Integer getAmountPremiumCurrency() {
        return amountPremiumCurrency;
    }

    public void setAmountPremiumCurrency(Integer amountPremiumCurrency) {
        this.amountPremiumCurrency = amountPremiumCurrency;
    }

    public Integer getAmountIngameCurrency() {
        return amountIngameCurrency;
    }

    public void setAmountIngameCurrency(Integer amountIngameCurrency) {
        this.amountIngameCurrency = amountIngameCurrency;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(Long idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }

    public String getUrlToPremiumItem() {
        return urlToPremiumItem;
    }

    public void setUrlToPremiumItem(String urlToPremiumItem) {
        this.urlToPremiumItem = urlToPremiumItem;
    }

    public String getPremiumItemName() {
        return premiumItemName;
    }

    public void setPremiumItemName(String premiumItemName) {
        this.premiumItemName = premiumItemName;
    }
    
}
