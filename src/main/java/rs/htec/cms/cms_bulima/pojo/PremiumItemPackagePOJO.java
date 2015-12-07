/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.htec.cms.cms_bulima.domain.PremiumItemPackage;

/**
 *
<<<<<<< HEAD
 * @author marko
 * @author stefan
 */
public class PremiumItemPackagePOJO {
    
    private Long id;
    private String name;
    private int pricePremiumCurrency;
    private Integer amountPremiumItems;
    private String additionalInfo;
    private Date updateTimestamp;
    private Date createDate;
    private String title;
    private Short active;
    private Integer position;
    private String highlightUrl;
    private Long idPremiumItem;
    private String namePremiumItem;
    private String googlePlayStoreId;
    private String itunesStoreId;
    private String amazonStoreId;
    
    public PremiumItemPackagePOJO(PremiumItemPackage pip) {
        this.id = pip.getId();
        this.name = pip.getName();
        this.pricePremiumCurrency = pip.getPricePremiumCurrency();
        this.amountPremiumItems = pip.getAmountPremiumItems();
        this.additionalInfo = pip.getAdditionalInfo();
        this.updateTimestamp = pip.getUpdateTimestamp();
        this.createDate = pip.getCreateDate();
        this.title = pip.getTitle();
        this.active = pip.getActive();
        this.position = pip.getPosition();
        this.highlightUrl = pip.getHighlightUrl();
        if (pip.getIdPremiumItem() != null){
            this.idPremiumItem = pip.getIdPremiumItem().getId();
            this.namePremiumItem = pip.getIdPremiumItem().getName();
        }
        this.googlePlayStoreId = pip.getGooglePlayStoreId();
        this.itunesStoreId = pip.getItunesStoreId();
        this.amazonStoreId = pip.getAmazonStoreId();
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

    public int getPricePremiumCurrency() {
        return pricePremiumCurrency;
    }

    public void setPricePremiumCurrency(int pricePremiumCurrency) {
        this.pricePremiumCurrency = pricePremiumCurrency;
    }

    public Integer getAmountPremiumItems() {
        return amountPremiumItems;
    }

    public void setAmountPremiumItems(Integer amountPremiumItems) {
        this.amountPremiumItems = amountPremiumItems;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getHighlightUrl() {
        return highlightUrl;
    }

    public void setHighlightUrl(String highlightUrl) {
        this.highlightUrl = highlightUrl;
    }

    public Long getIdPremiumItem() {
        return idPremiumItem;
    }

    public String getGooglePlayStoreId() {
        return googlePlayStoreId;
    }

    public void setGooglePlayStoreId(String googlePlayStoreId) {
        this.googlePlayStoreId = googlePlayStoreId;
    }

    public String getItunesStoreId() {
        return itunesStoreId;
    }

    public void setItunesStoreId(String itunesStoreId) {
        this.itunesStoreId = itunesStoreId;
    }

    public String getAmazonStoreId() {
        return amazonStoreId;
    }

    public void setAmazonStoreId(String amazonStoreId) {
        this.amazonStoreId = amazonStoreId;
    }
    
    public void setIdPremiumItem(Long idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }

    public String getNamePremiumItem() {
        return namePremiumItem;
    }

    public void setNamePremiumItem(String namePremiumItem) {
        this.namePremiumItem = namePremiumItem;
    }

    public static List<PremiumItemPackagePOJO> toPremiumItemPackagePOJOList(List<PremiumItemPackage> list) {
        PremiumItemPackagePOJO pojo;
        List<PremiumItemPackagePOJO> pojos = new ArrayList<>();
        for (PremiumItemPackage pip : list) {
            pojo = new PremiumItemPackagePOJO(pip);
            pojos.add(pojo);
        }
        return pojos;
    }

}
