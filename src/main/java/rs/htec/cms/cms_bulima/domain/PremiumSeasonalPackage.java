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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "PREMIUM_SEASONAL_PACKAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumSeasonalPackage.findAll", query = "SELECT p FROM PremiumSeasonalPackage p"),
    @NamedQuery(name = "PremiumSeasonalPackage.findById", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByName", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.name = :name"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByPrice", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.price = :price"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByUpdateTimestamp", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByCreateDate", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByAdditionalInfo", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.additionalInfo = :additionalInfo"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByImageUrl", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.imageUrl = :imageUrl"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByPlatform", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.platform = :platform"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByIsActive", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.isActive = :isActive"),
    @NamedQuery(name = "PremiumSeasonalPackage.findByAmountPremiumCurrency", query = "SELECT p FROM PremiumSeasonalPackage p WHERE p.amountPremiumCurrency = :amountPremiumCurrency")})
public class PremiumSeasonalPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "additionalInfo")
    private String additionalInfo;
    @Size(max = 255)
    @Column(name = "imageUrl")
    private String imageUrl;
    @Size(max = 255)
    @Column(name = "platform")
    private String platform;
    @Column(name = "isActive")
    private Short isActive;
    @Column(name = "amountPremiumCurrency")
    private Integer amountPremiumCurrency;
    @OneToMany(mappedBy = "idPremiumSeasonalPackage")
    private List<Salesorder> salesorderList;

    public PremiumSeasonalPackage() {
    }

    public PremiumSeasonalPackage(Long id) {
        this.id = id;
    }

    public PremiumSeasonalPackage(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Integer getAmountPremiumCurrency() {
        return amountPremiumCurrency;
    }

    public void setAmountPremiumCurrency(Integer amountPremiumCurrency) {
        this.amountPremiumCurrency = amountPremiumCurrency;
    }

    @XmlTransient
    @JsonIgnore
    public List<Salesorder> getSalesorderList() {
        return salesorderList;
    }

    public void setSalesorderList(List<Salesorder> salesorderList) {
        this.salesorderList = salesorderList;
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
        if (!(object instanceof PremiumSeasonalPackage)) {
            return false;
        }
        PremiumSeasonalPackage other = (PremiumSeasonalPackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.PremiumSeasonalPackage[ id=" + id + " ]";
    }
    
}
