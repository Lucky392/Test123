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
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "BUG_REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BugReport.findAll", query = "SELECT b FROM BugReport b"),
    @NamedQuery(name = "BugReport.findById", query = "SELECT b FROM BugReport b WHERE b.id = :id"),
    @NamedQuery(name = "BugReport.findByEmail", query = "SELECT b FROM BugReport b WHERE b.email = :email"),
    @NamedQuery(name = "BugReport.findByErrorType", query = "SELECT b FROM BugReport b WHERE b.errorType = :errorType"),
    @NamedQuery(name = "BugReport.findByDescription", query = "SELECT b FROM BugReport b WHERE b.description = :description"),
    @NamedQuery(name = "BugReport.findByOrigin", query = "SELECT b FROM BugReport b WHERE b.origin = :origin"),
    @NamedQuery(name = "BugReport.findBySystem", query = "SELECT b FROM BugReport b WHERE b.system = :system"),
    @NamedQuery(name = "BugReport.findByDeviceType", query = "SELECT b FROM BugReport b WHERE b.deviceType = :deviceType"),
    @NamedQuery(name = "BugReport.findByTask", query = "SELECT b FROM BugReport b WHERE b.task = :task"),
    @NamedQuery(name = "BugReport.findByStatus", query = "SELECT b FROM BugReport b WHERE b.status = :status"),
    @NamedQuery(name = "BugReport.findByOther", query = "SELECT b FROM BugReport b WHERE b.other = :other"),
    @NamedQuery(name = "BugReport.findByReportDate", query = "SELECT b FROM BugReport b WHERE b.reportDate = :reportDate"),
    @NamedQuery(name = "BugReport.findByAppVersion", query = "SELECT b FROM BugReport b WHERE b.appVersion = :appVersion"),
    @NamedQuery(name = "BugReport.findByClubName", query = "SELECT b FROM BugReport b WHERE b.clubName = :clubName"),
    @NamedQuery(name = "BugReport.findByUserId", query = "SELECT b FROM BugReport b WHERE b.userId = :userId")})
public class BugReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "errorType")
    private String errorType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "origin")
    private String origin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "system")
    private String system;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "deviceType")
    private String deviceType;
    @Size(max = 255)
    @Column(name = "task")
    private String task;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 500)
    @Column(name = "other")
    private String other;
    @Basic(optional = false)
    @NotNull
    @Column(name = "report_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;
    @Size(max = 255)
    @Column(name = "appVersion")
    private String appVersion;
    @Size(max = 255)
    @Column(name = "clubName")
    private String clubName;
    @Column(name = "USER_ID")
    private BigInteger userId;
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public BugReport() {
    }

    public BugReport(Long id) {
        this.id = id;
    }

    public BugReport(Long id, String email, String errorType, String description, String origin, String system, String deviceType, Date reportDate) {
        this.id = id;
        this.email = email;
        this.errorType = errorType;
        this.description = description;
        this.origin = origin;
        this.system = system;
        this.deviceType = deviceType;
        this.reportDate = reportDate;
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

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    @XmlTransient
    @JsonIgnore
    public User getUser() {
        return user;
    }

    @XmlTransient
    @JsonIgnore
    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof BugReport)) {
            return false;
        }
        BugReport other = (BugReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.BugReport[ id=" + id + " ]";
    }
    
}
