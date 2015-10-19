/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "BATCHJOB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Batchjob.findAll", query = "SELECT b FROM Batchjob b"),
    @NamedQuery(name = "Batchjob.findById", query = "SELECT b FROM Batchjob b WHERE b.id = :id"),
    @NamedQuery(name = "Batchjob.findByJobName", query = "SELECT b FROM Batchjob b WHERE b.jobName = :jobName"),
    @NamedQuery(name = "Batchjob.findByCronExpression", query = "SELECT b FROM Batchjob b WHERE b.cronExpression = :cronExpression"),
    @NamedQuery(name = "Batchjob.findByDefaultCronExpression", query = "SELECT b FROM Batchjob b WHERE b.defaultCronExpression = :defaultCronExpression"),
    @NamedQuery(name = "Batchjob.findByEnabled", query = "SELECT b FROM Batchjob b WHERE b.enabled = :enabled")})
public class Batchjob implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "jobName")
    private String jobName;
    @Size(max = 255)
    @Column(name = "cronExpression")
    private String cronExpression;
    @Size(max = 255)
    @Column(name = "defaultCronExpression")
    private String defaultCronExpression;
    @Column(name = "enabled")
    private Short enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBatchjob")
    private List<BatchjobStep> batchjobStepList;

    public Batchjob() {
    }

    public Batchjob(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDefaultCronExpression() {
        return defaultCronExpression;
    }

    public void setDefaultCronExpression(String defaultCronExpression) {
        this.defaultCronExpression = defaultCronExpression;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    @JsonIgnore
    public List<BatchjobStep> getBatchjobStepList() {
        return batchjobStepList;
    }

    public void setBatchjobStepList(List<BatchjobStep> batchjobStepList) {
        this.batchjobStepList = batchjobStepList;
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
        if (!(object instanceof Batchjob)) {
            return false;
        }
        Batchjob other = (Batchjob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Batchjob[ id=" + id + " ]";
    }
    
}
