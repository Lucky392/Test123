/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "BATCHJOB_STEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BatchjobStep.findAll", query = "SELECT b FROM BatchjobStep b"),
    @NamedQuery(name = "BatchjobStep.findById", query = "SELECT b FROM BatchjobStep b WHERE b.id = :id"),
    @NamedQuery(name = "BatchjobStep.findByStepName", query = "SELECT b FROM BatchjobStep b WHERE b.stepName = :stepName"),
    @NamedQuery(name = "BatchjobStep.findByEnabled", query = "SELECT b FROM BatchjobStep b WHERE b.enabled = :enabled")})
public class BatchjobStep implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "stepName")
    private String stepName;
    @Column(name = "enabled")
    private Short enabled;
    @JoinColumn(name = "ID_BATCHJOB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Batchjob idBatchjob;

    public BatchjobStep() {
    }

    public BatchjobStep(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public Batchjob getIdBatchjob() {
        return idBatchjob;
    }

    public void setIdBatchjob(Batchjob idBatchjob) {
        this.idBatchjob = idBatchjob;
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
        if (!(object instanceof BatchjobStep)) {
            return false;
        }
        BatchjobStep other = (BatchjobStep) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.BatchjobStep[ id=" + id + " ]";
    }
    
}
