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
 * @author lazar
 */
@Entity
@Table(name = "BATCHJOB_CONTROLLER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BatchjobController.findAll", query = "SELECT b FROM BatchjobController b"),
    @NamedQuery(name = "BatchjobController.findById", query = "SELECT b FROM BatchjobController b WHERE b.id = :id"),
    @NamedQuery(name = "BatchjobController.findByDailyJob1CronExpression", query = "SELECT b FROM BatchjobController b WHERE b.dailyJob1CronExpression = :dailyJob1CronExpression"),
    @NamedQuery(name = "BatchjobController.findByDailyJob2CronExpression", query = "SELECT b FROM BatchjobController b WHERE b.dailyJob2CronExpression = :dailyJob2CronExpression"),
    @NamedQuery(name = "BatchjobController.findByDailyJob3CronExpression", query = "SELECT b FROM BatchjobController b WHERE b.dailyJob3CronExpression = :dailyJob3CronExpression"),
    @NamedQuery(name = "BatchjobController.findByUpdateLeagueActivity", query = "SELECT b FROM BatchjobController b WHERE b.updateLeagueActivity = :updateLeagueActivity"),
    @NamedQuery(name = "BatchjobController.findByUpdatePlayerData", query = "SELECT b FROM BatchjobController b WHERE b.updatePlayerData = :updatePlayerData"),
    @NamedQuery(name = "BatchjobController.findByUpdateMatchData", query = "SELECT b FROM BatchjobController b WHERE b.updateMatchData = :updateMatchData"),
    @NamedQuery(name = "BatchjobController.findByUpdateClubLineUp", query = "SELECT b FROM BatchjobController b WHERE b.updateClubLineUp = :updateClubLineUp"),
    @NamedQuery(name = "BatchjobController.findByUpdateMatchday", query = "SELECT b FROM BatchjobController b WHERE b.updateMatchday = :updateMatchday"),
    @NamedQuery(name = "BatchjobController.findByUpdateMarketValue", query = "SELECT b FROM BatchjobController b WHERE b.updateMarketValue = :updateMarketValue"),
    @NamedQuery(name = "BatchjobController.findByCalculateBLMPoint", query = "SELECT b FROM BatchjobController b WHERE b.calculateBLMPoint = :calculateBLMPoint"),
    @NamedQuery(name = "BatchjobController.findByCalculateFanLeaguePoint", query = "SELECT b FROM BatchjobController b WHERE b.calculateFanLeaguePoint = :calculateFanLeaguePoint"),
    @NamedQuery(name = "BatchjobController.findByDoAll", query = "SELECT b FROM BatchjobController b WHERE b.doAll = :doAll"),
    @NamedQuery(name = "BatchjobController.findByCreateDate", query = "SELECT b FROM BatchjobController b WHERE b.createDate = :createDate")})
public class BatchjobController implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "dailyJob1CronExpression")
    private String dailyJob1CronExpression;
    @Size(max = 255)
    @Column(name = "dailyJob2CronExpression")
    private String dailyJob2CronExpression;
    @Size(max = 255)
    @Column(name = "dailyJob3CronExpression")
    private String dailyJob3CronExpression;
    @Column(name = "updateLeagueActivity")
    private Short updateLeagueActivity;
    @Column(name = "updatePlayerData")
    private Short updatePlayerData;
    @Column(name = "updateMatchData")
    private Short updateMatchData;
    @Column(name = "updateClubLineUp")
    private Short updateClubLineUp;
    @Column(name = "updateMatchday")
    private Short updateMatchday;
    @Column(name = "updateMarketValue")
    private Short updateMarketValue;
    @Column(name = "calculateBLMPoint")
    private Short calculateBLMPoint;
    @Column(name = "calculateFanLeaguePoint")
    private Short calculateFanLeaguePoint;
    @Column(name = "doAll")
    private Short doAll;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public BatchjobController() {
    }

    public BatchjobController(Long id) {
        this.id = id;
    }

    public BatchjobController(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDailyJob1CronExpression() {
        return dailyJob1CronExpression;
    }

    public void setDailyJob1CronExpression(String dailyJob1CronExpression) {
        this.dailyJob1CronExpression = dailyJob1CronExpression;
    }

    public String getDailyJob2CronExpression() {
        return dailyJob2CronExpression;
    }

    public void setDailyJob2CronExpression(String dailyJob2CronExpression) {
        this.dailyJob2CronExpression = dailyJob2CronExpression;
    }

    public String getDailyJob3CronExpression() {
        return dailyJob3CronExpression;
    }

    public void setDailyJob3CronExpression(String dailyJob3CronExpression) {
        this.dailyJob3CronExpression = dailyJob3CronExpression;
    }

    public Short getUpdateLeagueActivity() {
        return updateLeagueActivity;
    }

    public void setUpdateLeagueActivity(Short updateLeagueActivity) {
        this.updateLeagueActivity = updateLeagueActivity;
    }

    public Short getUpdatePlayerData() {
        return updatePlayerData;
    }

    public void setUpdatePlayerData(Short updatePlayerData) {
        this.updatePlayerData = updatePlayerData;
    }

    public Short getUpdateMatchData() {
        return updateMatchData;
    }

    public void setUpdateMatchData(Short updateMatchData) {
        this.updateMatchData = updateMatchData;
    }

    public Short getUpdateClubLineUp() {
        return updateClubLineUp;
    }

    public void setUpdateClubLineUp(Short updateClubLineUp) {
        this.updateClubLineUp = updateClubLineUp;
    }

    public Short getUpdateMatchday() {
        return updateMatchday;
    }

    public void setUpdateMatchday(Short updateMatchday) {
        this.updateMatchday = updateMatchday;
    }

    public Short getUpdateMarketValue() {
        return updateMarketValue;
    }

    public void setUpdateMarketValue(Short updateMarketValue) {
        this.updateMarketValue = updateMarketValue;
    }

    public Short getCalculateBLMPoint() {
        return calculateBLMPoint;
    }

    public void setCalculateBLMPoint(Short calculateBLMPoint) {
        this.calculateBLMPoint = calculateBLMPoint;
    }

    public Short getCalculateFanLeaguePoint() {
        return calculateFanLeaguePoint;
    }

    public void setCalculateFanLeaguePoint(Short calculateFanLeaguePoint) {
        this.calculateFanLeaguePoint = calculateFanLeaguePoint;
    }

    public Short getDoAll() {
        return doAll;
    }

    public void setDoAll(Short doAll) {
        this.doAll = doAll;
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
        if (!(object instanceof BatchjobController)) {
            return false;
        }
        BatchjobController other = (BatchjobController) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.BatchjobController[ id=" + id + " ]";
    }
    
}
