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
 * @author marko
 */
@Entity
@Table(name = "REWARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reward.findAll", query = "SELECT r FROM Reward r"),
    @NamedQuery(name = "Reward.findById", query = "SELECT r FROM Reward r WHERE r.id = :id"),
    @NamedQuery(name = "Reward.findByName", query = "SELECT r FROM Reward r WHERE r.name = :name"),
    @NamedQuery(name = "Reward.findByDescription", query = "SELECT r FROM Reward r WHERE r.description = :description"),
    @NamedQuery(name = "Reward.findByAmountPremiumItem", query = "SELECT r FROM Reward r WHERE r.amountPremiumItem = :amountPremiumItem"),
    @NamedQuery(name = "Reward.findByAmountPremiumCurrency", query = "SELECT r FROM Reward r WHERE r.amountPremiumCurrency = :amountPremiumCurrency"),
    @NamedQuery(name = "Reward.findByAmountIngameCurrency", query = "SELECT r FROM Reward r WHERE r.amountIngameCurrency = :amountIngameCurrency"),
    @NamedQuery(name = "Reward.findByChance", query = "SELECT r FROM Reward r WHERE r.chance = :chance"),
    @NamedQuery(name = "Reward.findByUpdateAt", query = "SELECT r FROM Reward r WHERE r.updateAt = :updateAt"),
    @NamedQuery(name = "Reward.findByCreateDate", query = "SELECT r FROM Reward r WHERE r.createDate = :createDate"),
    @NamedQuery(name = "Reward.findByImageUrl", query = "SELECT r FROM Reward r WHERE r.imageUrl = :imageUrl")})
public class Reward implements Serializable {
    @OneToMany(mappedBy = "idReward")
    private List<MatchdayChallenge> matchdayChallengeList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "amountPremiumItem")
    private Integer amountPremiumItem;
    @Column(name = "amountPremiumCurrency")
    private Integer amountPremiumCurrency;
    @Column(name = "amountIngameCurrency")
    private Integer amountIngameCurrency;
    @Column(name = "chance")
    private Integer chance;
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "imageUrl")
    private String imageUrl;
    @OneToMany(mappedBy = "idReward")
    private List<PremiumHistory> premiumHistoryList;
    @JoinColumn(name = "ID_PREMIUM_ITEM", referencedColumnName = "ID")
    @ManyToOne
    private PremiumItem idPremiumItem;

    public Reward() {
    }

    public Reward(Long id) {
        this.id = id;
    }

    public Reward(Long id, Date createDate) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmountPremiumItem() {
        return amountPremiumItem;
    }

    public void setAmountPremiumItem(Integer amountPremiumItem) {
        this.amountPremiumItem = amountPremiumItem;
    }

    public Integer getAmountPremiumCurrency() {
        return amountPremiumCurrency;
    }

    public void setAmountPremiumCurrency(Integer amountPremiumCurrency) {
        this.amountPremiumCurrency = amountPremiumCurrency;
    }

    public Integer getAmountIngameCurrency() {
        return amountIngameCurrency;
    }

    public void setAmountIngameCurrency(Integer amountIngameCurrency) {
        this.amountIngameCurrency = amountIngameCurrency;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @XmlTransient
    @JsonIgnore
    public List<PremiumHistory> getPremiumHistoryList() {
        return premiumHistoryList;
    }

    public void setPremiumHistoryList(List<PremiumHistory> premiumHistoryList) {
        this.premiumHistoryList = premiumHistoryList;
    }

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
        if (!(object instanceof Reward)) {
            return false;
        }
        Reward other = (Reward) object;
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
    public List<MatchdayChallenge> getMatchdayChallengeList() {
        return matchdayChallengeList;
    }

    public void setMatchdayChallengeList(List<MatchdayChallenge> matchdayChallengeList) {
        this.matchdayChallengeList = matchdayChallengeList;
    }
    
}
