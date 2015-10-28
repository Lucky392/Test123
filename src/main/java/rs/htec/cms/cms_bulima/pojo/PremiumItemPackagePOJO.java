/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.Match;
import rs.htec.cms.cms_bulima.domain.PremiumItemPackage;

/**
 *
 * @author marko
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

    public void setIdPremiumItem(Long idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }
    
    public static List<PremiumItemPackagePOJO> toPremiumItemPackagePOJOList(List<PremiumItemPackage> packages){
        PremiumItemPackagePOJO pojo;
        List<PremiumItemPackagePOJO> pojos = new ArrayList<>();
        for (PremiumItemPackage packag : packages) {
            pojo = new PremiumItemPackagePOJO(packag);
            pojos.add(pojo);
        }
        return pojos;
    }
}
