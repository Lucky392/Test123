/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import rs.htec.cms.cms_bulima.domain.FantasyManager;

/**
 *
 * @author lazar
 */
@XmlRootElement
public class FantasyManagerPOJO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Short secondLeague;
    private Date updateTimestamp;
    private Date createDate;
    private String profilePhotoUrl;
    private long idFavClub;
    private long idUser;
    private String favClubName;
    private String userEmail;
//    private List<Long> fantasyClubList;

    public FantasyManagerPOJO(FantasyManager fantasyManager) {
        id = fantasyManager.getId();
        username = fantasyManager.getUsername();
        firstname = fantasyManager.getFirstname();
        lastname = fantasyManager.getLastname();
        secondLeague = fantasyManager.getSecondLeague();
        updateTimestamp = fantasyManager.getUpdateTimestamp();
        createDate = fantasyManager.getCreateDate();
        profilePhotoUrl = fantasyManager.getProfilePhotoUrl();
        if (fantasyManager.getIdFavClub() != null) {
            idFavClub = fantasyManager.getIdFavClub().getId();
            favClubName = fantasyManager.getIdFavClub().getMediumName();
        }
        if (fantasyManager.getIdUser() != null) {
            idUser = fantasyManager.getIdUser().getId();
            userEmail = fantasyManager.getIdUser().getEmail();
        }
//        fantasyClubList = new ArrayList<>();
//        for (FantasyClub fc : fantasyManager.getFantasyClubList()) {
//            fantasyClubList.add(fc.getId());
//        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Short getSecondLeague() {
        return secondLeague;
    }

    public void setSecondLeague(Short secondLeague) {
        this.secondLeague = secondLeague;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public long getIdFavClub() {
        return idFavClub;
    }

    public void setIdFavClub(long idFavClub) {
        this.idFavClub = idFavClub;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

//    public List<Long> getFantasyClubList() {
//        return fantasyClubList;
//    }
//
//    public void setFantasyClubList(List<Long> fantasyClubList) {
//        this.fantasyClubList = fantasyClubList;
//    }
//    

    public String getFavClubName() {
        return favClubName;
    }

    public void setFavClubName(String favClubName) {
        this.favClubName = favClubName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
