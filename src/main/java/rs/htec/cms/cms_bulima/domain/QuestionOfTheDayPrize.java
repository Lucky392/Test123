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
@Table(name = "QUESTION_OF_THE_DAY_PRIZE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionOfTheDayPrize.findAll", query = "SELECT q FROM QuestionOfTheDayPrize q"),
    @NamedQuery(name = "QuestionOfTheDayPrize.findById", query = "SELECT q FROM QuestionOfTheDayPrize q WHERE q.id = :id"),
    @NamedQuery(name = "QuestionOfTheDayPrize.findByName", query = "SELECT q FROM QuestionOfTheDayPrize q WHERE q.name = :name"),
    @NamedQuery(name = "QuestionOfTheDayPrize.findByPrizeMoney", query = "SELECT q FROM QuestionOfTheDayPrize q WHERE q.prizeMoney = :prizeMoney"),
    @NamedQuery(name = "QuestionOfTheDayPrize.findByCreateDate", query = "SELECT q FROM QuestionOfTheDayPrize q WHERE q.createDate = :createDate")})
public class QuestionOfTheDayPrize implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "prizeMoney")
    private Integer prizeMoney;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public QuestionOfTheDayPrize() {
    }

    public QuestionOfTheDayPrize(Long id) {
        this.id = id;
    }

    public QuestionOfTheDayPrize(Long id, Date createDate) {
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

    public Integer getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(Integer prizeMoney) {
        this.prizeMoney = prizeMoney;
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
        if (!(object instanceof QuestionOfTheDayPrize)) {
            return false;
        }
        QuestionOfTheDayPrize other = (QuestionOfTheDayPrize) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }
    
}
