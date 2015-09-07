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
@Table(name = "QUESTION_OF_THE_DAY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionOfTheDay.findAll", query = "SELECT q FROM QuestionOfTheDay q"),
    @NamedQuery(name = "QuestionOfTheDay.findById", query = "SELECT q FROM QuestionOfTheDay q WHERE q.id = :id"),
    @NamedQuery(name = "QuestionOfTheDay.findByQuestion", query = "SELECT q FROM QuestionOfTheDay q WHERE q.question = :question"),
    @NamedQuery(name = "QuestionOfTheDay.findByCorrectAnswer", query = "SELECT q FROM QuestionOfTheDay q WHERE q.correctAnswer = :correctAnswer"),
    @NamedQuery(name = "QuestionOfTheDay.findByWrongAnswer1", query = "SELECT q FROM QuestionOfTheDay q WHERE q.wrongAnswer1 = :wrongAnswer1"),
    @NamedQuery(name = "QuestionOfTheDay.findByWrongAnswer2", query = "SELECT q FROM QuestionOfTheDay q WHERE q.wrongAnswer2 = :wrongAnswer2"),
    @NamedQuery(name = "QuestionOfTheDay.findByWrongAnswer3", query = "SELECT q FROM QuestionOfTheDay q WHERE q.wrongAnswer3 = :wrongAnswer3"),
    @NamedQuery(name = "QuestionOfTheDay.findByDate", query = "SELECT q FROM QuestionOfTheDay q WHERE q.date = :date")})
public class QuestionOfTheDay implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "question")
    private String question;
    @Size(max = 255)
    @Column(name = "correctAnswer")
    private String correctAnswer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "wrongAnswer1")
    private String wrongAnswer1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "wrongAnswer2")
    private String wrongAnswer2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "wrongAnswer3")
    private String wrongAnswer3;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public QuestionOfTheDay() {
    }

    public QuestionOfTheDay(Long id) {
        this.id = id;
    }

    public QuestionOfTheDay(Long id, String question, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.id = id;
        this.question = question;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof QuestionOfTheDay)) {
            return false;
        }
        QuestionOfTheDay other = (QuestionOfTheDay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.QuestionOfTheDay[ id=" + id + " ]";
    }
    
}
