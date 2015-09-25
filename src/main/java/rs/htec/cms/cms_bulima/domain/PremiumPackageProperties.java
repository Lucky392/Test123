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
@Table(name = "PREMIUM_PACKAGE_PROPERTIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumPackageProperties.findAll", query = "SELECT p FROM PremiumPackageProperties p"),
    @NamedQuery(name = "PremiumPackageProperties.findById", query = "SELECT p FROM PremiumPackageProperties p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumPackageProperties.findByHighlightImageUrl", query = "SELECT p FROM PremiumPackageProperties p WHERE p.highlightImageUrl = :highlightImageUrl"),
    @NamedQuery(name = "PremiumPackageProperties.findByShowOnlySpecial", query = "SELECT p FROM PremiumPackageProperties p WHERE p.showOnlySpecial = :showOnlySpecial"),
    @NamedQuery(name = "PremiumPackageProperties.findByImageUrlSpecial", query = "SELECT p FROM PremiumPackageProperties p WHERE p.imageUrlSpecial = :imageUrlSpecial"),
    @NamedQuery(name = "PremiumPackageProperties.findByForPayingUsers", query = "SELECT p FROM PremiumPackageProperties p WHERE p.forPayingUsers = :forPayingUsers"),
    @NamedQuery(name = "PremiumPackageProperties.findByForNonPayingUsers", query = "SELECT p FROM PremiumPackageProperties p WHERE p.forNonPayingUsers = :forNonPayingUsers"),
    @NamedQuery(name = "PremiumPackageProperties.findByRedirectUrl", query = "SELECT p FROM PremiumPackageProperties p WHERE p.redirectUrl = :redirectUrl"),
    @NamedQuery(name = "PremiumPackageProperties.findByRedirectPositionTop", query = "SELECT p FROM PremiumPackageProperties p WHERE p.redirectPositionTop = :redirectPositionTop"),
    @NamedQuery(name = "PremiumPackageProperties.findByRedirectPositionLeft", query = "SELECT p FROM PremiumPackageProperties p WHERE p.redirectPositionLeft = :redirectPositionLeft"),
    @NamedQuery(name = "PremiumPackageProperties.findByRedirectImageUrl", query = "SELECT p FROM PremiumPackageProperties p WHERE p.redirectImageUrl = :redirectImageUrl"),
    @NamedQuery(name = "PremiumPackageProperties.findByCharityDonation", query = "SELECT p FROM PremiumPackageProperties p WHERE p.charityDonation = :charityDonation"),
    @NamedQuery(name = "PremiumPackageProperties.findByCharityDescription", query = "SELECT p FROM PremiumPackageProperties p WHERE p.charityDescription = :charityDescription"),
    @NamedQuery(name = "PremiumPackageProperties.findByShowFrom", query = "SELECT p FROM PremiumPackageProperties p WHERE p.showFrom = :showFrom"),
    @NamedQuery(name = "PremiumPackageProperties.findByShowUntil", query = "SELECT p FROM PremiumPackageProperties p WHERE p.showUntil = :showUntil"),
    @NamedQuery(name = "PremiumPackageProperties.findByUpdateTimestamp", query = "SELECT p FROM PremiumPackageProperties p WHERE p.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "PremiumPackageProperties.findByCreateDate", query = "SELECT p FROM PremiumPackageProperties p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "PremiumPackageProperties.findByMaxPurchasesPerUser", query = "SELECT p FROM PremiumPackageProperties p WHERE p.maxPurchasesPerUser = :maxPurchasesPerUser")})
public class PremiumPackageProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "highlightImageUrl")
    private String highlightImageUrl;
    @Column(name = "showOnlySpecial")
    private Short showOnlySpecial;
    @Size(max = 255)
    @Column(name = "imageUrlSpecial")
    private String imageUrlSpecial;
    @Column(name = "forPayingUsers")
    private Short forPayingUsers;
    @Column(name = "forNonPayingUsers")
    private Short forNonPayingUsers;
    @Size(max = 255)
    @Column(name = "redirectUrl")
    private String redirectUrl;
    @Column(name = "redirectPositionTop")
    private Integer redirectPositionTop;
    @Column(name = "redirectPositionLeft")
    private Integer redirectPositionLeft;
    @Size(max = 255)
    @Column(name = "redirectImageUrl")
    private String redirectImageUrl;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "charityDonation")
    private BigDecimal charityDonation;
    @Size(max = 255)
    @Column(name = "charityDescription")
    private String charityDescription;
    @Column(name = "showFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date showFrom;
    @Column(name = "showUntil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date showUntil;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "maxPurchasesPerUser")
    private Integer maxPurchasesPerUser;
    @OneToMany(mappedBy = "idPremiumPackageProperties")
    private List<PremiumPackage> premiumPackageList;
    @JoinColumn(name = "ID_PREMIUM_PACKAGE_UPGRADE", referencedColumnName = "ID")
    @ManyToOne
    private PremiumPackage idPremiumPackageUpgrade;
    @JoinColumn(name = "ID_FAVORITE_CLUB", referencedColumnName = "ID")
    @ManyToOne
    private FavoriteClub idFavoriteClub;
    @JoinColumn(name = "ID_PREMIUM_PACKAGE_SUCCESSOR", referencedColumnName = "ID")
    @ManyToOne
    private PremiumPackage idPremiumPackageSuccessor;

    public PremiumPackageProperties() {
    }

    public PremiumPackageProperties(Long id) {
        this.id = id;
    }

    public PremiumPackageProperties(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
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

    @XmlTransient
    @JsonIgnore
    public List<PremiumPackage> getPremiumPackageList() {
        return premiumPackageList;
    }

    public void setPremiumPackageList(List<PremiumPackage> premiumPackageList) {
        this.premiumPackageList = premiumPackageList;
    }

    @XmlTransient
    @JsonIgnore
    public PremiumPackage getIdPremiumPackageUpgrade() {
        return idPremiumPackageUpgrade;
    }

    public void setIdPremiumPackageUpgrade(PremiumPackage idPremiumPackageUpgrade) {
        this.idPremiumPackageUpgrade = idPremiumPackageUpgrade;
    }

    @XmlTransient
    @JsonIgnore
    public FavoriteClub getIdFavoriteClub() {
        return idFavoriteClub;
    }

    public void setIdFavoriteClub(FavoriteClub idFavoriteClub) {
        this.idFavoriteClub = idFavoriteClub;
    }

    @XmlTransient
    @JsonIgnore
    public PremiumPackage getIdPremiumPackageSuccessor() {
        return idPremiumPackageSuccessor;
    }

    public void setIdPremiumPackageSuccessor(PremiumPackage idPremiumPackageSuccessor) {
        this.idPremiumPackageSuccessor = idPremiumPackageSuccessor;
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
        if (!(object instanceof PremiumPackageProperties)) {
            return false;
        }
        PremiumPackageProperties other = (PremiumPackageProperties) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.PremiumPackageProperties[ id=" + id + " ]";
    }
    
}
