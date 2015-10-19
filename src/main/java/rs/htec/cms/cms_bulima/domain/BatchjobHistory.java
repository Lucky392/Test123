/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author marko
 */
@Entity
@Table(name = "BATCHJOB_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BatchjobHistory.findAll", query = "SELECT b FROM BatchjobHistory b"),
    @NamedQuery(name = "BatchjobHistory.findById", query = "SELECT b FROM BatchjobHistory b WHERE b.id = :id"),
    @NamedQuery(name = "BatchjobHistory.findByJobName", query = "SELECT b FROM BatchjobHistory b WHERE b.jobName = :jobName"),
    @NamedQuery(name = "BatchjobHistory.findByStartDate", query = "SELECT b FROM BatchjobHistory b WHERE b.startDate = :startDate"),
    @NamedQuery(name = "BatchjobHistory.findByEndDate", query = "SELECT b FROM BatchjobHistory b WHERE b.endDate = :endDate"),
    @NamedQuery(name = "BatchjobHistory.findByTimeNeeded", query = "SELECT b FROM BatchjobHistory b WHERE b.timeNeeded = :timeNeeded"),
    @NamedQuery(name = "BatchjobHistory.findByReturnValue", query = "SELECT b FROM BatchjobHistory b WHERE b.returnValue = :returnValue"),
    @NamedQuery(name = "BatchjobHistory.findByCreateDate", query = "SELECT b FROM BatchjobHistory b WHERE b.createDate = :createDate")})
public class BatchjobHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "jobName")
    private String jobName;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "timeNeeded")
    private BigInteger timeNeeded;
    @Size(max = 255)
    @Column(name = "returnValue")
    private String returnValue;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "errorStack")
    private String errorStack;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public BatchjobHistory() {
    }

    public BatchjobHistory(Long id) {
        this.id = id;
    }

    public BatchjobHistory(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigInteger getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(BigInteger timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrorStack() {
        return errorStack;
    }

    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if (!(object instanceof BatchjobHistory)) {
            return false;
        }
        BatchjobHistory other = (BatchjobHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.BatchjobHistory[ id=" + id + " ]";
    }
    
}
