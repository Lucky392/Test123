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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "PREMIUM_ITEM_PACKAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumItemPackage.findAll", query = "SELECT p FROM PremiumItemPackage p"),
    @NamedQuery(name = "PremiumItemPackage.findById", query = "SELECT p FROM PremiumItemPackage p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumItemPackage.findByName", query = "SELECT p FROM PremiumItemPackage p WHERE p.name = :name"),
    @NamedQuery(name = "PremiumItemPackage.findByPricePremiumCurrency", query = "SELECT p FROM PremiumItemPackage p WHERE p.pricePremiumCurrency = :pricePremiumCurrency"),
    @NamedQuery(name = "PremiumItemPackage.findByAmountPremiumItems", query = "SELECT p FROM PremiumItemPackage p WHERE p.amountPremiumItems = :amountPremiumItems"),
    @NamedQuery(name = "PremiumItemPackage.findByAdditionalInfo", query = "SELECT p FROM PremiumItemPackage p WHERE p.additionalInfo = :additionalInfo"),
    @NamedQuery(name = "PremiumItemPackage.findByUpdateTimestamp", query = "SELECT p FROM PremiumItemPackage p WHERE p.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "PremiumItemPackage.findByCreateDate", query = "SELECT p FROM PremiumItemPackage p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "PremiumItemPackage.findByTitle", query = "SELECT p FROM PremiumItemPackage p WHERE p.title = :title"),
    @NamedQuery(name = "PremiumItemPackage.findByActive", query = "SELECT p FROM PremiumItemPackage p WHERE p.active = :active"),
    @NamedQuery(name = "PremiumItemPackage.findByPosition", query = "SELECT p FROM PremiumItemPackage p WHERE p.position = :position"),
    @NamedQuery(name = "PremiumItemPackage.findByHighlightUrl", query = "SELECT p FROM PremiumItemPackage p WHERE p.highlightUrl = :highlightUrl")})
public class PremiumItemPackage implements Serializable {
    @Size(max = 255)
    @Column(name = "googlePlayStoreId")
    private String googlePlayStoreId;
    @Size(max = 255)
    @Column(name = "itunesStoreId")
    private String itunesStoreId;
    @Size(max = 255)
    @Column(name = "amazonStoreId")
    private String amazonStoreId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pricePremiumCurrency")
    private int pricePremiumCurrency;
    @Column(name = "amountPremiumItems")
    private Integer amountPremiumItems;
    @Size(max = 255)
    @Column(name = "additionalInfo")
    private String additionalInfo;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @Column(name = "active")
    private Short active;
    @Column(name = "position")
    private Integer position;
    @Size(max = 255)
    @Column(name = "highlight_url")
    private String highlightUrl;
    @JoinColumn(name = "ID_PREMIUM_ITEM", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PremiumItem idPremiumItem;

    public PremiumItemPackage() {
    }

    public PremiumItemPackage(Long id) {
        this.id = id;
    }

    public PremiumItemPackage(Long id, int pricePremiumCurrency, Date createDate) {
        this.id = id;
        this.pricePremiumCurrency = pricePremiumCurrency;
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

    @XmlTransient
    @JsonIgnore
    public PremiumItem getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(PremiumItem idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
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
        if (!(object instanceof PremiumItemPackage)) {
            return false;
        }
        PremiumItemPackage other = (PremiumItemPackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.PremiumItemPackage[ id=" + id + " ]";
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
    
}
