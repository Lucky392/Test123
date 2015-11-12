/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author lazar
 */
@Entity
@Table(name = "PREMIUM_PACKAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumPackage.findAll", query = "SELECT p FROM PremiumPackage p"),
    @NamedQuery(name = "PremiumPackage.findById", query = "SELECT p FROM PremiumPackage p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumPackage.findByName", query = "SELECT p FROM PremiumPackage p WHERE p.name = :name"),
    @NamedQuery(name = "PremiumPackage.findByPrice", query = "SELECT p FROM PremiumPackage p WHERE p.price = :price"),
    @NamedQuery(name = "PremiumPackage.findByAmountPremiumCurrency", query = "SELECT p FROM PremiumPackage p WHERE p.amountPremiumCurrency = :amountPremiumCurrency"),
    @NamedQuery(name = "PremiumPackage.findByImageUrl", query = "SELECT p FROM PremiumPackage p WHERE p.imageUrl = :imageUrl"),
    @NamedQuery(name = "PremiumPackage.findByPlatform", query = "SELECT p FROM PremiumPackage p WHERE p.platform = :platform"),
    @NamedQuery(name = "PremiumPackage.findByIsActive", query = "SELECT p FROM PremiumPackage p WHERE p.isActive = :isActive"),
    @NamedQuery(name = "PremiumPackage.findByPosition", query = "SELECT p FROM PremiumPackage p WHERE p.position = :position"),
    @NamedQuery(name = "PremiumPackage.findByUpdateTimestamp", query = "SELECT p FROM PremiumPackage p WHERE p.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "PremiumPackage.findByCreateDate", query = "SELECT p FROM PremiumPackage p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "PremiumPackage.findByTitle", query = "SELECT p FROM PremiumPackage p WHERE p.title = :title"),
    @NamedQuery(name = "PremiumPackage.findByPremiumStatusDuration", query = "SELECT p FROM PremiumPackage p WHERE p.premiumStatusDuration = :premiumStatusDuration")})
public class PremiumPackage implements Serializable {
    @OneToMany(mappedBy = "idPremiumPackage")
    private List<Salesorder> salesorderList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "amountPremiumCurrency")
    private Integer amountPremiumCurrency;
    @Size(max = 255)
    @Column(name = "imageUrl")
    private String imageUrl;
    @Size(max = 255)
    @Column(name = "platform")
    private String platform;
    @Column(name = "isActive")
    private Short isActive;
    @Column(name = "position")
    private Integer position;
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
    @Size(max = 10)
    @Column(name = "premium_status_duration")
    private String premiumStatusDuration;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPremiumPackage")
    private List<PremiumPackageContent> premiumPackageContentList;
    @JoinColumn(name = "ID_PREMIUM_PACKAGE_PROPERTIES", referencedColumnName = "ID")
    @ManyToOne
    private PremiumPackageProperties idPremiumPackageProperties;
    @OneToMany(mappedBy = "idPremiumPackageUpgrade")
    private List<PremiumPackageProperties> premiumPackagePropertiesList;
    @OneToMany(mappedBy = "idPremiumPackageSuccessor")
    private List<PremiumPackageProperties> premiumPackagePropertiesList1;

    public PremiumPackage() {
    }

    public PremiumPackage(Long id) {
        this.id = id;
    }

    public PremiumPackage(Long id, Date createDate) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmountPremiumCurrency() {
        return amountPremiumCurrency;
    }

    public void setAmountPremiumCurrency(Integer amountPremiumCurrency) {
        this.amountPremiumCurrency = amountPremiumCurrency;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public String getPremiumStatusDuration() {
        return premiumStatusDuration;
    }

    public void setPremiumStatusDuration(String premiumStatusDuration) {
        this.premiumStatusDuration = premiumStatusDuration;
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
    public PremiumPackageProperties getIdPremiumPackageProperties() {
        return idPremiumPackageProperties;
    }

    public void setIdPremiumPackageProperties(PremiumPackageProperties idPremiumPackageProperties) {
        this.idPremiumPackageProperties = idPremiumPackageProperties;
    }

    @XmlTransient
    @JsonIgnore
    public List<PremiumPackageProperties> getPremiumPackagePropertiesList() {
        return premiumPackagePropertiesList;
    }

    public void setPremiumPackagePropertiesList(List<PremiumPackageProperties> premiumPackagePropertiesList) {
        this.premiumPackagePropertiesList = premiumPackagePropertiesList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PremiumPackageProperties> getPremiumPackagePropertiesList1() {
        return premiumPackagePropertiesList1;
    }

    public void setPremiumPackagePropertiesList1(List<PremiumPackageProperties> premiumPackagePropertiesList1) {
        this.premiumPackagePropertiesList1 = premiumPackagePropertiesList1;
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
        if (!(object instanceof PremiumPackage)) {
            return false;
        }
        PremiumPackage other = (PremiumPackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.PremiumPackage[ id=" + id + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<Salesorder> getSalesorderList() {
        return salesorderList;
    }

    public void setSalesorderList(List<Salesorder> salesorderList) {
        this.salesorderList = salesorderList;
    }
    
}
