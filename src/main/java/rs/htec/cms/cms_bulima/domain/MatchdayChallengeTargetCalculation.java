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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "MATCHDAY_CHALLENGE_TARGET_CALCULATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findAll", query = "SELECT m FROM MatchdayChallengeTargetCalculation m"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findById", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.id = :id"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findByCalculationSql", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.calculationSql = :calculationSql"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findByOperation", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.operation = :operation"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findByCalculationType", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.calculationType = :calculationType"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findByValue", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.value = :value"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findByUpdateAt", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.updateAt = :updateAt"),
    @NamedQuery(name = "MatchdayChallengeTargetCalculation.findByCreateDate", query = "SELECT m FROM MatchdayChallengeTargetCalculation m WHERE m.createDate = :createDate")})
public class MatchdayChallengeTargetCalculation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "calculationSql")
    private String calculationSql;
    @Size(max = 255)
    @Column(name = "operation")
    private String operation;
    @Size(max = 255)
    @Column(name = "calculationType")
    private String calculationType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private MatchdayChallenge idMatchdayChallenge;

    public MatchdayChallengeTargetCalculation() {
    }

    public MatchdayChallengeTargetCalculation(Long id) {
        this.id = id;
    }

    public MatchdayChallengeTargetCalculation(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalculationSql() {
        return calculationSql;
    }

    public void setCalculationSql(String calculationSql) {
        this.calculationSql = calculationSql;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public MatchdayChallenge getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(MatchdayChallenge idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
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
        if (!(object instanceof MatchdayChallengeTargetCalculation)) {
            return false;
        }
        MatchdayChallengeTargetCalculation other = (MatchdayChallengeTargetCalculation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchdayChallengeTargetCalculation[ id=" + id + " ]";
    }
    
}
