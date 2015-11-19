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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "DASHBOARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dashboard.findAll", query = "SELECT d FROM Dashboard d"),
    @NamedQuery(name = "Dashboard.findById", query = "SELECT d FROM Dashboard d WHERE d.id = :id"),
    @NamedQuery(name = "Dashboard.findByPlatform", query = "SELECT d FROM Dashboard d WHERE d.platform = :platform"),
    @NamedQuery(name = "Dashboard.findByRegistrations", query = "SELECT d FROM Dashboard d WHERE d.registrations = :registrations"),
    @NamedQuery(name = "Dashboard.findByPayingUsers", query = "SELECT d FROM Dashboard d WHERE d.payingUsers = :payingUsers"),
    @NamedQuery(name = "Dashboard.findByRevenue", query = "SELECT d FROM Dashboard d WHERE d.revenue = :revenue"),
    @NamedQuery(name = "Dashboard.findByActiveUsers", query = "SELECT d FROM Dashboard d WHERE d.activeUsers = :activeUsers"),
    @NamedQuery(name = "Dashboard.findByStatisticsTime", query = "SELECT d FROM Dashboard d WHERE d.statisticsTime = :statisticsTime")})
public class Dashboard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "platform")
    private String platform;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registrations")
    private long registrations;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payingUsers")
    private long payingUsers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "revenue")
    private long revenue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activeUsers")
    private long activeUsers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "statisticsTime")
    private String statisticsTime;

    public Dashboard() {
    }

    public Dashboard(Long id) {
        this.id = id;
    }

    public Dashboard(Long id, String platform, long registrations, long payingUsers, long revenue, long activeUsers, String statisticsTime) {
        this.id = id;
        this.platform = platform;
        this.registrations = registrations;
        this.payingUsers = payingUsers;
        this.revenue = revenue;
        this.activeUsers = activeUsers;
        this.statisticsTime = statisticsTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getRegistrations() {
        return registrations;
    }

    public void setRegistrations(long registrations) {
        this.registrations = registrations;
    }

    public long getPayingUsers() {
        return payingUsers;
    }

    public void setPayingUsers(long payingUsers) {
        this.payingUsers = payingUsers;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
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
        if (!(object instanceof Dashboard)) {
            return false;
        }
        Dashboard other = (Dashboard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Dashboard[ id=" + id + " ]";
    }
    
}
