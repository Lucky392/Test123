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
@Table(name = "MATCHDAY_CHALLENGE_SCORE_CALCULATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchdayChallengeScoreCalculation.findAll", query = "SELECT m FROM MatchdayChallengeScoreCalculation m"),
    @NamedQuery(name = "MatchdayChallengeScoreCalculation.findById", query = "SELECT m FROM MatchdayChallengeScoreCalculation m WHERE m.id = :id"),
    @NamedQuery(name = "MatchdayChallengeScoreCalculation.findByCalculationSql", query = "SELECT m FROM MatchdayChallengeScoreCalculation m WHERE m.calculationSql = :calculationSql"),
    @NamedQuery(name = "MatchdayChallengeScoreCalculation.findByCreateDate", query = "SELECT m FROM MatchdayChallengeScoreCalculation m WHERE m.createDate = :createDate")})
public class MatchdayChallengeScoreCalculation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "calculationSql")
    private String calculationSql;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private MatchdayChallenge idMatchdayChallenge;

    public MatchdayChallengeScoreCalculation() {
    }

    public MatchdayChallengeScoreCalculation(Long id) {
        this.id = id;
    }

    public MatchdayChallengeScoreCalculation(Long id, Date createDate) {
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
        if (!(object instanceof MatchdayChallengeScoreCalculation)) {
            return false;
        }
        MatchdayChallengeScoreCalculation other = (MatchdayChallengeScoreCalculation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchdayChallengeScoreCalculation[ id=" + id + " ]";
    }
    
}
