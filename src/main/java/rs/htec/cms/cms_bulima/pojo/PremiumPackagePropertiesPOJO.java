/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.PremiumPackageProperties;

/**
 *
 * @author marko
 */
public class PremiumPackagePropertiesPOJO {
    
    private Long id;
    private String highlightImageUrl;
    private Short showOnlySpecial;
    private String imageUrlSpecial;
    private Short forPayingUsers;
    private Short forNonPayingUsers;
    private String redirectUrl;
    private Integer redirectPositionTop;
    private Integer redirectPositionLeft;
    private String redirectImageUrl;
    private BigDecimal charityDonation;
    private String charityDescription;
    private Date showFrom;
    private Date showUntil;
    private Date updateTimestamp;
    private Date createDate;
    private Integer maxPurchasesPerUser;
    private Long idPremiumPackageUpgrade;
    private Long idFavoriteClub;
    private Long idPremiumPackageSuccessor;
    private String premiumPackageUpgrade;
    private String favoriteClub;
    private String premiumPackageSuccessor;

    public PremiumPackagePropertiesPOJO(PremiumPackageProperties ppp) {
        this.id = ppp.getId();
        this.highlightImageUrl = ppp.getHighlightImageUrl();
        this.showOnlySpecial = ppp.getShowOnlySpecial();
        this.imageUrlSpecial = ppp.getImageUrlSpecial();
        this.forPayingUsers = ppp.getForPayingUsers();
        this.forNonPayingUsers = ppp.getForNonPayingUsers();
        this.redirectUrl = ppp.getRedirectUrl();
        this.redirectPositionTop = ppp.getRedirectPositionTop();
        this.redirectPositionLeft = ppp.getRedirectPositionLeft();
        this.redirectImageUrl = ppp.getRedirectImageUrl();
        this.charityDonation = ppp.getCharityDonation();
        this.charityDescription = ppp.getCharityDescription();
        this.showFrom = ppp.getShowFrom();
        this.showUntil = ppp.getShowUntil();
        this.updateTimestamp = ppp.getUpdateTimestamp();
        this.createDate = ppp.getCreateDate();
        this.maxPurchasesPerUser = ppp.getMaxPurchasesPerUser();
        if (ppp.getIdPremiumPackageUpgrade() != null){
            this.idPremiumPackageUpgrade = ppp.getIdPremiumPackageUpgrade().getId();
            premiumPackageUpgrade = ppp.getIdPremiumPackageUpgrade().getName();
        }
        if (ppp.getIdFavoriteClub() != null){
            this.idFavoriteClub = ppp.getIdFavoriteClub().getId();
            favoriteClub = ppp.getIdFavoriteClub().getMediumName();
        }
        if (ppp.getIdPremiumPackageSuccessor() != null){
            this.idPremiumPackageSuccessor = ppp.getIdPremiumPackageSuccessor().getId();
            premiumPackageSuccessor = ppp.getIdPremiumPackageUpgrade().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHighlightImageUrl() {
        return highlightImageUrl;
    }

    public void setHighlightImageUrl(String highlightImageUrl) {
        this.highlightImageUrl = highlightImageUrl;
    }

    public Short getShowOnlySpecial() {
        return showOnlySpecial;
    }

    public void setShowOnlySpecial(Short showOnlySpecial) {
        this.showOnlySpecial = showOnlySpecial;
    }

    public String getImageUrlSpecial() {
        return imageUrlSpecial;
    }

    public void setImageUrlSpecial(String imageUrlSpecial) {
        this.imageUrlSpecial = imageUrlSpecial;
    }

    public Short getForPayingUsers() {
        return forPayingUsers;
    }

    public void setForPayingUsers(Short forPayingUsers) {
        this.forPayingUsers = forPayingUsers;
    }

    public Short getForNonPayingUsers() {
        return forNonPayingUsers;
    }

    public void setForNonPayingUsers(Short forNonPayingUsers) {
        this.forNonPayingUsers = forNonPayingUsers;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getRedirectPositionTop() {
        return redirectPositionTop;
    }

    public void setRedirectPositionTop(Integer redirectPositionTop) {
        this.redirectPositionTop = redirectPositionTop;
    }

    public Integer getRedirectPositionLeft() {
        return redirectPositionLeft;
    }

    public void setRedirectPositionLeft(Integer redirectPositionLeft) {
        this.redirectPositionLeft = redirectPositionLeft;
    }

    public String getRedirectImageUrl() {
        return redirectImageUrl;
    }

    public void setRedirectImageUrl(String redirectImageUrl) {
        this.redirectImageUrl = redirectImageUrl;
    }

    public BigDecimal getCharityDonation() {
        return charityDonation;
    }

    public void setCharityDonation(BigDecimal charityDonation) {
        this.charityDonation = charityDonation;
    }

    public String getCharityDescription() {
        return charityDescription;
    }

    public void setCharityDescription(String charityDescription) {
        this.charityDescription = charityDescription;
    }

    public Date getShowFrom() {
        return showFrom;
    }

    public void setShowFrom(Date showFrom) {
        this.showFrom = showFrom;
    }

    public Date getShowUntil() {
        return showUntil;
    }

    public void setShowUntil(Date showUntil) {
        this.showUntil = showUntil;
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

    public Integer getMaxPurchasesPerUser() {
        return maxPurchasesPerUser;
    }

    public void setMaxPurchasesPerUser(Integer maxPurchasesPerUser) {
        this.maxPurchasesPerUser = maxPurchasesPerUser;
    }

    public Long getIdPremiumPackageUpgrade() {
        return idPremiumPackageUpgrade;
    }

    public void setIdPremiumPackageUpgrade(Long idPremiumPackageUpgrade) {
        this.idPremiumPackageUpgrade = idPremiumPackageUpgrade;
    }

    public Long getIdFavoriteClub() {
        return idFavoriteClub;
    }

    public void setIdFavoriteClub(Long idFavoriteClub) {
        this.idFavoriteClub = idFavoriteClub;
    }

    public Long getIdPremiumPackageSuccessor() {
        return idPremiumPackageSuccessor;
    }

    public void setIdPremiumPackageSuccessor(Long idPremiumPackageSuccessor) {
        this.idPremiumPackageSuccessor = idPremiumPackageSuccessor;
    }
    
    public static List<PremiumPackagePropertiesPOJO> toPremiumPackagePropertiesPOJOList(List<PremiumPackageProperties> properties){
        PremiumPackagePropertiesPOJO pojo;
        List<PremiumPackagePropertiesPOJO> pojos = new ArrayList<>();
        for (PremiumPackageProperties property : properties) {
            pojo = new PremiumPackagePropertiesPOJO(property);
            pojos.add(pojo);
        }
        return pojos;
    }

    public String getPremiumPackageUpgrade() {
        return premiumPackageUpgrade;
    }

    public void setPremiumPackageUpgrade(String premiumPackageUpgrade) {
        this.premiumPackageUpgrade = premiumPackageUpgrade;
    }

    public String getFavoriteClub() {
        return favoriteClub;
    }

    public void setFavoriteClub(String favoriteClub) {
        this.favoriteClub = favoriteClub;
    }

    public String getPremiumPackageSuccessor() {
        return premiumPackageSuccessor;
    }

    public void setPremiumPackageSuccessor(String premiumPackageSuccessor) {
        this.premiumPackageSuccessor = premiumPackageSuccessor;
    }


}
