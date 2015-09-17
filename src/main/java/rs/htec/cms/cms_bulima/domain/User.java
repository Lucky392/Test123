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
 * @author lazar
 */
@Entity
@Table(name = "USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByNewsletter", query = "SELECT u FROM User u WHERE u.newsletter = :newsletter"),
    @NamedQuery(name = "User.findByConfirmed", query = "SELECT u FROM User u WHERE u.confirmed = :confirmed"),
    @NamedQuery(name = "User.findByUserhash", query = "SELECT u FROM User u WHERE u.userhash = :userhash"),
    @NamedQuery(name = "User.findByAmountPremiumCurrency", query = "SELECT u FROM User u WHERE u.amountPremiumCurrency = :amountPremiumCurrency"),
    @NamedQuery(name = "User.findByCreateDate", query = "SELECT u FROM User u WHERE u.createDate = :createDate"),
    @NamedQuery(name = "User.findByNewEmail", query = "SELECT u FROM User u WHERE u.newEmail = :newEmail"),
    @NamedQuery(name = "User.findByNewEmailActiveTimestamp", query = "SELECT u FROM User u WHERE u.newEmailActiveTimestamp = :newEmailActiveTimestamp"),
    @NamedQuery(name = "User.findByLandingPage", query = "SELECT u FROM User u WHERE u.landingPage = :landingPage"),
    @NamedQuery(name = "User.findBySocialNetwork", query = "SELECT u FROM User u WHERE u.socialNetwork = :socialNetwork"),
    @NamedQuery(name = "User.findByBanned", query = "SELECT u FROM User u WHERE u.banned = :banned"),
    @NamedQuery(name = "User.findByPayingUser", query = "SELECT u FROM User u WHERE u.payingUser = :payingUser"),
    @NamedQuery(name = "User.findByPremiumStatusActiveTimestamp", query = "SELECT u FROM User u WHERE u.premiumStatusActiveTimestamp = :premiumStatusActiveTimestamp")})
public class User implements Serializable {
    @OneToMany(mappedBy = "idUser")
    private List<PremiumHistory> premiumHistoryList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "newsletter")
    private short newsletter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirmed")
    private short confirmed;
    @Size(max = 255)
    @Column(name = "userhash")
    private String userhash;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amountPremiumCurrency")
    private int amountPremiumCurrency;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "newEmail")
    private String newEmail;
    @Column(name = "newEmailActiveTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newEmailActiveTimestamp;
    @Size(max = 255)
    @Column(name = "landingPage")
    private String landingPage;
    @Size(max = 255)
    @Column(name = "socialNetwork")
    private String socialNetwork;
    @Basic(optional = false)
    @NotNull
    @Column(name = "banned")
    private short banned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payingUser")
    private short payingUser;
    @Column(name = "premium_status_active_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date premiumStatusActiveTimestamp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<FantasyManager> fantasyManagerList;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, short newsletter, short confirmed, int amountPremiumCurrency, Date createDate, short banned, short payingUser) {
        this.id = id;
        this.newsletter = newsletter;
        this.confirmed = confirmed;
        this.amountPremiumCurrency = amountPremiumCurrency;
        this.createDate = createDate;
        this.banned = banned;
        this.payingUser = payingUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(short newsletter) {
        this.newsletter = newsletter;
    }

    public short getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(short confirmed) {
        this.confirmed = confirmed;
    }

    public String getUserhash() {
        return userhash;
    }

    public void setUserhash(String userhash) {
        this.userhash = userhash;
    }

    public int getAmountPremiumCurrency() {
        return amountPremiumCurrency;
    }

    public void setAmountPremiumCurrency(int amountPremiumCurrency) {
        this.amountPremiumCurrency = amountPremiumCurrency;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public Date getNewEmailActiveTimestamp() {
        return newEmailActiveTimestamp;
    }

    public void setNewEmailActiveTimestamp(Date newEmailActiveTimestamp) {
        this.newEmailActiveTimestamp = newEmailActiveTimestamp;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public short getBanned() {
        return banned;
    }

    public void setBanned(short banned) {
        this.banned = banned;
    }

    public short getPayingUser() {
        return payingUser;
    }

    public void setPayingUser(short payingUser) {
        this.payingUser = payingUser;
    }

    public Date getPremiumStatusActiveTimestamp() {
        return premiumStatusActiveTimestamp;
    }

    public void setPremiumStatusActiveTimestamp(Date premiumStatusActiveTimestamp) {
        this.premiumStatusActiveTimestamp = premiumStatusActiveTimestamp;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyManager> getFantasyManagerList() {
        return fantasyManagerList;
    }

    public void setFantasyManagerList(List<FantasyManager> fantasyManagerList) {
        this.fantasyManagerList = fantasyManagerList;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
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
    public List<PremiumHistory> getPremiumHistoryList() {
        return premiumHistoryList;
    }

    public void setPremiumHistoryList(List<PremiumHistory> premiumHistoryList) {
        this.premiumHistoryList = premiumHistoryList;
    }
    
}
