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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "CMS_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmsUser.findAll", query = "SELECT c FROM CmsUser c"),
    @NamedQuery(name = "CmsUser.findById", query = "SELECT c FROM CmsUser c WHERE c.id = :id"),
    @NamedQuery(name = "CmsUser.findByUserName", query = "SELECT c FROM CmsUser c WHERE c.userName = :userName"),
    @NamedQuery(name = "CmsUser.findByPassword", query = "SELECT c FROM CmsUser c WHERE c.password = :password"),
    @NamedQuery(name = "CmsUser.findByToken", query = "SELECT c FROM CmsUser c WHERE c.token = :token")})
public class CmsUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "token")
    private String token;
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    @ManyToOne
    private CmsRole idRole;

    public CmsUser() {
    }

    public CmsUser(Long id) {
        this.id = id;
    }

    public CmsUser(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CmsRole getIdRole() {
        return idRole;
    }

    public void setIdRole(CmsRole idRole) {
        this.idRole = idRole;
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
        if (!(object instanceof CmsUser)) {
            return false;
        }
        CmsUser other = (CmsUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.CmsUser[ id=" + id + " ]";
    }
    
}
