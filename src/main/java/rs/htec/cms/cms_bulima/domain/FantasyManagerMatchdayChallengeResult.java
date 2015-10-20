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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_MANAGER_MATCHDAY_CHALLENGE_RESULT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyManagerMatchdayChallengeResult.findAll", query = "SELECT f FROM FantasyManagerMatchdayChallengeResult f"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeResult.findById", query = "SELECT f FROM FantasyManagerMatchdayChallengeResult f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeResult.findByScore", query = "SELECT f FROM FantasyManagerMatchdayChallengeResult f WHERE f.score = :score"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeResult.findByIsSucceed", query = "SELECT f FROM FantasyManagerMatchdayChallengeResult f WHERE f.isSucceed = :isSucceed"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeResult.findByUpdateAt", query = "SELECT f FROM FantasyManagerMatchdayChallengeResult f WHERE f.updateAt = :updateAt"),
    @NamedQuery(name = "FantasyManagerMatchdayChallengeResult.findByCreateDate", query = "SELECT f FROM FantasyManagerMatchdayChallengeResult f WHERE f.createDate = :createDate")})
public class FantasyManagerMatchdayChallengeResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "score")
    private Double score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isSucceed")
    private short isSucceed;
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE_LINE_UP", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private FantasyManagerMatchdayChallengeLineUp idMatchdayChallengeLineUp;

    public FantasyManagerMatchdayChallengeResult() {
    }

    public FantasyManagerMatchdayChallengeResult(Long id) {
        this.id = id;
    }

    public FantasyManagerMatchdayChallengeResult(Long id, short isSucceed, Date createDate) {
        this.id = id;
        this.isSucceed = isSucceed;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public short getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(short isSucceed) {
        this.isSucceed = isSucceed;
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

    public FantasyManagerMatchdayChallengeLineUp getIdMatchdayChallengeLineUp() {
        return idMatchdayChallengeLineUp;
    }

    public void setIdMatchdayChallengeLineUp(FantasyManagerMatchdayChallengeLineUp idMatchdayChallengeLineUp) {
        this.idMatchdayChallengeLineUp = idMatchdayChallengeLineUp;
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
        if (!(object instanceof FantasyManagerMatchdayChallengeResult)) {
            return false;
        }
        FantasyManagerMatchdayChallengeResult other = (FantasyManagerMatchdayChallengeResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeResult[ id=" + id + " ]";
    }
    
}
