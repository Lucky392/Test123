/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "SALESORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salesorder.findAll", query = "SELECT s FROM Salesorder s"),
    @NamedQuery(name = "Salesorder.findById", query = "SELECT s FROM Salesorder s WHERE s.id = :id"),
    @NamedQuery(name = "Salesorder.findByRequestDate", query = "SELECT s FROM Salesorder s WHERE s.requestDate = :requestDate"),
    @NamedQuery(name = "Salesorder.findByApprovedDate", query = "SELECT s FROM Salesorder s WHERE s.approvedDate = :approvedDate"),
    @NamedQuery(name = "Salesorder.findByHashCode", query = "SELECT s FROM Salesorder s WHERE s.hashCode = :hashCode"),
    @NamedQuery(name = "Salesorder.findByMerchantSignature", query = "SELECT s FROM Salesorder s WHERE s.merchantSignature = :merchantSignature"),
    @NamedQuery(name = "Salesorder.findByPspReference", query = "SELECT s FROM Salesorder s WHERE s.pspReference = :pspReference"),
    @NamedQuery(name = "Salesorder.findByPaymentMethod", query = "SELECT s FROM Salesorder s WHERE s.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Salesorder.findByAuthResult", query = "SELECT s FROM Salesorder s WHERE s.authResult = :authResult"),
    @NamedQuery(name = "Salesorder.findByAmountIngameCurrency", query = "SELECT s FROM Salesorder s WHERE s.amountIngameCurrency = :amountIngameCurrency"),
    @NamedQuery(name = "Salesorder.findBySellingPrice", query = "SELECT s FROM Salesorder s WHERE s.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "Salesorder.findByComment", query = "SELECT s FROM Salesorder s WHERE s.comment = :comment"),
    @NamedQuery(name = "Salesorder.findByAdditionalInfo", query = "SELECT s FROM Salesorder s WHERE s.additionalInfo = :additionalInfo"),
    @NamedQuery(name = "Salesorder.findByAgbAcceptedTimestamp", query = "SELECT s FROM Salesorder s WHERE s.agbAcceptedTimestamp = :agbAcceptedTimestamp"),
    @NamedQuery(name = "Salesorder.findByAgbAcceptedVersion", query = "SELECT s FROM Salesorder s WHERE s.agbAcceptedVersion = :agbAcceptedVersion"),
    @NamedQuery(name = "Salesorder.findByConfirmationEmailSendTimestamp", query = "SELECT s FROM Salesorder s WHERE s.confirmationEmailSendTimestamp = :confirmationEmailSendTimestamp"),
    @NamedQuery(name = "Salesorder.findByProviderDataString", query = "SELECT s FROM Salesorder s WHERE s.providerDataString = :providerDataString"),
    @NamedQuery(name = "Salesorder.findByCustomerIP", query = "SELECT s FROM Salesorder s WHERE s.customerIP = :customerIP"),
    @NamedQuery(name = "Salesorder.findByOrderstateInternal", query = "SELECT s FROM Salesorder s WHERE s.orderstateInternal = :orderstateInternal"),
    @NamedQuery(name = "Salesorder.findByDirectCallbackURL", query = "SELECT s FROM Salesorder s WHERE s.directCallbackURL = :directCallbackURL"),
    @NamedQuery(name = "Salesorder.findByCreateDate", query = "SELECT s FROM Salesorder s WHERE s.createDate = :createDate"),
    @NamedQuery(name = "Salesorder.findByNspyreOrderHash", query = "SELECT s FROM Salesorder s WHERE s.nspyreOrderHash = :nspyreOrderHash"),
    @NamedQuery(name = "Salesorder.findByActiveTimestamp", query = "SELECT s FROM Salesorder s WHERE s.activeTimestamp = :activeTimestamp")})
public class Salesorder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requestDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Column(name = "approvedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Size(max = 255)
    @Column(name = "hashCode")
    private String hashCode;
    @Size(max = 255)
    @Column(name = "merchantSignature")
    private String merchantSignature;
    @Size(max = 255)
    @Column(name = "pspReference")
    private String pspReference;
    @Size(max = 255)
    @Column(name = "paymentMethod")
    private String paymentMethod;
    @Size(max = 255)
    @Column(name = "authResult")
    private String authResult;
    @Column(name = "amountIngameCurrency")
    private Integer amountIngameCurrency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sellingPrice")
    private BigDecimal sellingPrice;
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    @Size(max = 255)
    @Column(name = "additionalInfo")
    private String additionalInfo;
    @Column(name = "agbAcceptedTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agbAcceptedTimestamp;
    @Size(max = 255)
    @Column(name = "agbAcceptedVersion")
    private String agbAcceptedVersion;
    @Column(name = "confirmationEmailSendTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmationEmailSendTimestamp;
    @Size(max = 1023)
    @Column(name = "providerDataString")
    private String providerDataString;
    @Size(max = 255)
    @Column(name = "customerIP")
    private String customerIP;
    @Column(name = "orderstateInternal")
    private Integer orderstateInternal;
    @Size(max = 1023)
    @Column(name = "directCallbackURL")
    private String directCallbackURL;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "nspyreOrderHash")
    private String nspyreOrderHash;
    @Column(name = "active_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeTimestamp;
    @Lob
    @Size(max = 65535)
    @Column(name = "receipt")
    private String receipt;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User idUser;
    @JoinColumn(name = "ID_PREMIUM_PACKAGE", referencedColumnName = "ID")
    @ManyToOne
    private PremiumPackage idPremiumPackage;
    @JoinColumn(name = "ID_PREMIUM_SEASONAL_PACKAGE", referencedColumnName = "ID")
    @ManyToOne
    private PremiumSeasonalPackage idPremiumSeasonalPackage;

    public Salesorder() {
    }

    public Salesorder(Long id) {
        this.id = id;
    }

    public Salesorder(Long id, Date requestDate, Date createDate) {
        this.id = id;
        this.requestDate = requestDate;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getMerchantSignature() {
        return merchantSignature;
    }

    public void setMerchantSignature(String merchantSignature) {
        this.merchantSignature = merchantSignature;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(String pspReference) {
        this.pspReference = pspReference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAuthResult() {
        return authResult;
    }

    public void setAuthResult(String authResult) {
        this.authResult = authResult;
    }

    public Integer getAmountIngameCurrency() {
        return amountIngameCurrency;
    }

    public void setAmountIngameCurrency(Integer amountIngameCurrency) {
        this.amountIngameCurrency = amountIngameCurrency;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Date getAgbAcceptedTimestamp() {
        return agbAcceptedTimestamp;
    }

    public void setAgbAcceptedTimestamp(Date agbAcceptedTimestamp) {
        this.agbAcceptedTimestamp = agbAcceptedTimestamp;
    }

    public String getAgbAcceptedVersion() {
        return agbAcceptedVersion;
    }

    public void setAgbAcceptedVersion(String agbAcceptedVersion) {
        this.agbAcceptedVersion = agbAcceptedVersion;
    }

    public Date getConfirmationEmailSendTimestamp() {
        return confirmationEmailSendTimestamp;
    }

    public void setConfirmationEmailSendTimestamp(Date confirmationEmailSendTimestamp) {
        this.confirmationEmailSendTimestamp = confirmationEmailSendTimestamp;
    }

    public String getProviderDataString() {
        return providerDataString;
    }

    public void setProviderDataString(String providerDataString) {
        this.providerDataString = providerDataString;
    }

    public String getCustomerIP() {
        return customerIP;
    }

    public void setCustomerIP(String customerIP) {
        this.customerIP = customerIP;
    }

    public Integer getOrderstateInternal() {
        return orderstateInternal;
    }

    public void setOrderstateInternal(Integer orderstateInternal) {
        this.orderstateInternal = orderstateInternal;
    }

    public String getDirectCallbackURL() {
        return directCallbackURL;
    }

    public void setDirectCallbackURL(String directCallbackURL) {
        this.directCallbackURL = directCallbackURL;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNspyreOrderHash() {
        return nspyreOrderHash;
    }

    public void setNspyreOrderHash(String nspyreOrderHash) {
        this.nspyreOrderHash = nspyreOrderHash;
    }

    public Date getActiveTimestamp() {
        return activeTimestamp;
    }

    public void setActiveTimestamp(Date activeTimestamp) {
        this.activeTimestamp = activeTimestamp;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public PremiumPackage getIdPremiumPackage() {
        return idPremiumPackage;
    }

    public void setIdPremiumPackage(PremiumPackage idPremiumPackage) {
        this.idPremiumPackage = idPremiumPackage;
    }

    public PremiumSeasonalPackage getIdPremiumSeasonalPackage() {
        return idPremiumSeasonalPackage;
    }

    public void setIdPremiumSeasonalPackage(PremiumSeasonalPackage idPremiumSeasonalPackage) {
        this.idPremiumSeasonalPackage = idPremiumSeasonalPackage;
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
        if (!(object instanceof Salesorder)) {
            return false;
        }
        Salesorder other = (Salesorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Salesorder[ id=" + id + " ]";
    }
    
}
