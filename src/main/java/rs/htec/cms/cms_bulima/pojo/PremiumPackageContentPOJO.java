/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.PremiumPackageContent;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class PremiumPackageContentPOJO {

    private Long id;
    private Integer amount;
    private Date updateTimestamp;
    private Date createDate;
    private Long idPremiumItem;
    private Long idPremiumPackage;
    private String urlToPremiumItem;
    private String urlToPremiumPackage;

    public PremiumPackageContentPOJO(PremiumPackageContent content) {
        this.id = content.getId();
        this.amount = content.getAmount();
        this.updateTimestamp = content.getUpdateTimestamp();
        this.createDate = content.getCreateDate();
        if (content.getIdPremiumItem() != null) {
            this.idPremiumItem = content.getIdPremiumItem().getId();
            this.urlToPremiumItem = Util.getInstance().getUrl() + "rest/items/" + content.getIdPremiumItem().getId();
        }
        if (content.getIdPremiumPackage() != null) {
            this.idPremiumPackage = content.getIdPremiumPackage().getId();
            this.urlToPremiumPackage = Util.getInstance().getUrl() + "rest/package/" + content.getIdPremiumPackage().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Long getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(Long idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }

    public Long getIdPremiumPackage() {
        return idPremiumPackage;
    }

    public void setIdPremiumPackage(Long idPremiumPackage) {
        this.idPremiumPackage = idPremiumPackage;
    }

    public String getUrlToPremiumItem() {
        return urlToPremiumItem;
    }

    public void setUrlToPremiumItem(String urlToPremiumItem) {
        this.urlToPremiumItem = urlToPremiumItem;
    }

    public String getUrlToPremiumPackage() {
        return urlToPremiumPackage;
    }

    public void setUrlToPremiumPackage(String urlToPremiumPackage) {
        this.urlToPremiumPackage = urlToPremiumPackage;
    }
    
    public static List<PremiumPackageContentPOJO> toBugReportPOJOList (List<PremiumPackageContent> list) {
        PremiumPackageContentPOJO pojo;
        List<PremiumPackageContentPOJO> pojos = new ArrayList<>();
        for (PremiumPackageContent content : list) {
            pojo = new PremiumPackageContentPOJO(content);
            pojos.add(pojo);
        }
        return pojos;
    }

}
