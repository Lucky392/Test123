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
import javax.persistence.CascadeType;
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
 * @author marko
 */
@Entity
@Table(name = "PREMIUM_ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumItem.findAll", query = "SELECT p FROM PremiumItem p"),
    @NamedQuery(name = "PremiumItem.findById", query = "SELECT p FROM PremiumItem p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumItem.findByName", query = "SELECT p FROM PremiumItem p WHERE p.name = :name"),
    @NamedQuery(name = "PremiumItem.findByImageUrl", query = "SELECT p FROM PremiumItem p WHERE p.imageUrl = :imageUrl"),
    @NamedQuery(name = "PremiumItem.findByUpdateTimestamp", query = "SELECT p FROM PremiumItem p WHERE p.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "PremiumItem.findByCreateDate", query = "SELECT p FROM PremiumItem p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "PremiumItem.findByShowInItemShop", query = "SELECT p FROM PremiumItem p WHERE p.showInItemShop = :showInItemShop"),
    @NamedQuery(name = "PremiumItem.findByShopName", query = "SELECT p FROM PremiumItem p WHERE p.shopName = :shopName"),
    @NamedQuery(name = "PremiumItem.findByDescription", query = "SELECT p FROM PremiumItem p WHERE p.description = :description"),
    @NamedQuery(name = "PremiumItem.findByDescriptionLong", query = "SELECT p FROM PremiumItem p WHERE p.descriptionLong = :descriptionLong"),
    @NamedQuery(name = "PremiumItem.findBySlotNumber", query = "SELECT p FROM PremiumItem p WHERE p.slotNumber = :slotNumber"),
    @NamedQuery(name = "PremiumItem.findByDirectPurchasePrice", query = "SELECT p FROM PremiumItem p WHERE p.directPurchasePrice = :directPurchasePrice"),
    @NamedQuery(name = "PremiumItem.findByPositionInPackage", query = "SELECT p FROM PremiumItem p WHERE p.positionInPackage = :positionInPackage")})
public class PremiumItem implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPremiumItem")
    private List<PremiumPackageContent> premiumPackageContentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPremiumItem")
    private List<PremiumItemPackage> premiumItemPackageList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "showInItemShop")
    private short showInItemShop;
    @Size(max = 255)
    @Column(name = "shopName")
    private String shopName;
    @Size(max = 639)
    @Column(name = "description")
    private String description;
    @Size(max = 639)
    @Column(name = "descriptionLong")
    private String descriptionLong;
    @Column(name = "slotNumber")
    private Integer slotNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "directPurchasePrice")
    private int directPurchasePrice;
    @Column(name = "positionInPackage")
    private Integer positionInPackage;
    @OneToMany(mappedBy = "idPremiumItem")
    private List<PremiumHistory> premiumHistoryList;
    @OneToMany(mappedBy = "idPremiumItem")
    private List<Reward> rewardList;

    public PremiumItem() {
    }

    public PremiumItem(Long id) {
        this.id = id;
    }

    public PremiumItem(Long id, String name, Date createDate, short showInItemShop, int directPurchasePrice) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.showInItemShop = showInItemShop;
        this.directPurchasePrice = directPurchasePrice;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public short getShowInItemShop() {
        return showInItemShop;
    }

    public void setShowInItemShop(short showInItemShop) {
        this.showInItemShop = showInItemShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getDirectPurchasePrice() {
        return directPurchasePrice;
    }

    public void setDirectPurchasePrice(int directPurchasePrice) {
        this.directPurchasePrice = directPurchasePrice;
    }

    public Integer getPositionInPackage() {
        return positionInPackage;
    }

    public void setPositionInPackage(Integer positionInPackage) {
        this.positionInPackage = positionInPackage;
    }

    @XmlTransient
    @JsonIgnore
    public List<PremiumHistory> getPremiumHistoryList() {
        return premiumHistoryList;
    }

    public void setPremiumHistoryList(List<PremiumHistory> premiumHistoryList) {
        this.premiumHistoryList = premiumHistoryList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Reward> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
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
        if (!(object instanceof PremiumItem)) {
            return false;
        }
        PremiumItem other = (PremiumItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }

    @XmlTransient
    @JsonIgnore
    public List<PremiumPackageContent> getPremiumPackageContentList() {
        return premiumPackageContentList;
    }

    public void setPremiumPackageContentList(List<PremiumPackageContent> premiumPackageContentList) {
        this.premiumPackageContentList = premiumPackageContentList;
    }
    
    @XmlTransient
    @JsonIgnore
    public List<PremiumItemPackage> getPremiumItemPackageList() {
        return premiumItemPackageList;
    }

    public void setPremiumItemPackageList(List<PremiumItemPackage> premiumItemPackageList) {
        this.premiumItemPackageList = premiumItemPackageList;
    }
    
}
