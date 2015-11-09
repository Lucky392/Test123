/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.FantasyClub;

/**
 *
 * @author marko
 */

public class FantasyClubPOJO {

    private Long id;
    private String name;
    private Integer credit;
    private Integer totalBLMPoints;
    private Date updateTimestamp;
    private Date changeLineUpTimestamp;
    private Date createDate;
    private Short isLeagueFounder;
    private Integer amountSubstituteBench;
    private Integer amountCoTrainer;
    private Date lastLogin;
    private Date firstLogin;
    private Date sportDirectorActiveTimestamp;
    private String activity;
    private Date lastQuestionAnswered;
    private Integer questionRightAnswered;
    private String lastLoginWith;
    private int amountSubstituteBenchSlots;
    private Short isLineUpChangedByCoTrainer;
    private Long formationID;
    private Long fantasyClubLogoID;
    private Long emblemID;
    private Long captainFantasyPlayerID;
    private Long fantasyManagerID;
    private Long fantasyLeagueID;
    private String formationName;
    private String captianName;
    private String fantasyManagerName;
    private String fantasyLeagueName;   

    public String getFormationName() {
        return formationName;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    public String getCaptianName() {
        return captianName;
    }

    public void setCaptianName(String captianName) {
        this.captianName = captianName;
    }

    public String getFantasyManagerName() {
        return fantasyManagerName;
    }

    public void setFantasyManagerName(String fantasyManagerName) {
        this.fantasyManagerName = fantasyManagerName;
    }

    public String getFantasyLeagueName() {
        return fantasyLeagueName;
    }

    public void setFantasyLeagueName(String fantasyLeagueName) {
        this.fantasyLeagueName = fantasyLeagueName;
    }

    public FantasyClubPOJO(FantasyClub club) {
        this.id = club.getId();
        this.name = club.getName();
        this.credit = club.getCredit();
        this.totalBLMPoints = club.getTotalBLMPoints();
        this.updateTimestamp = club.getUpdateTimestamp();
        this.changeLineUpTimestamp = club.getChangeLineUpTimestamp();
        this.createDate = club.getCreateDate();
        this.isLeagueFounder = club.getIsLeagueFounder();
        this.amountSubstituteBench = club.getAmountSubstituteBench();
        this.amountCoTrainer = club.getAmountCoTrainer();
        this.lastLogin = club.getLastLogin();
        this.firstLogin = club.getFirstLogin();
        this.sportDirectorActiveTimestamp = club.getSportDirectorActiveTimestamp();
        this.activity = club.getActivity();
        this.lastQuestionAnswered = club.getLastQuestionAnswered();
        this.questionRightAnswered = club.getQuestionRightAnswered();
        this.lastLoginWith = club.getLastLoginWith();
        this.amountSubstituteBenchSlots = club.getAmountSubstituteBenchSlots();
        this.isLineUpChangedByCoTrainer = club.getIsLineUpChangedByCoTrainer();
        if (club.getIdActiveFormation() != null) {
            this.formationID = club.getIdActiveFormation().getId();
            this.formationName = club.getIdActiveFormation().getName();
        }
        if (club.getIdFantasyClubLogo() != null) {
            this.fantasyClubLogoID = club.getIdFantasyClubLogo().getId();
        }
        if (club.getIdEmblem() != null) {
            this.emblemID = club.getIdEmblem().getId();
        }
        if (club.getIdCaptain() != null) {
            this.captainFantasyPlayerID = club.getIdCaptain().getId();
            this.captianName = club.getIdCaptain().getIdLeaguePlayer().getIdPlayer().getFirstName() + " " + club.getIdCaptain().getIdLeaguePlayer().getIdPlayer().getLastName();
        }
        if (club.getIdFantasyManager() != null) {
            this.fantasyManagerID = club.getIdFantasyManager().getId();
            this.fantasyManagerName = club.getIdFantasyManager().getFirstname() + " " + club.getIdFantasyManager().getLastname();
        }
        if (club.getIdFantasyLeague() != null) {
            this.fantasyLeagueID = club.getIdFantasyLeague().getId();
            this.fantasyLeagueName = club.getIdFantasyLeague().getName();
        }
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

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getTotalBLMPoints() {
        return totalBLMPoints;
    }

    public void setTotalBLMPoints(Integer totalBLMPoints) {
        this.totalBLMPoints = totalBLMPoints;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getChangeLineUpTimestamp() {
        return changeLineUpTimestamp;
    }

    public void setChangeLineUpTimestamp(Date changeLineUpTimestamp) {
        this.changeLineUpTimestamp = changeLineUpTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getIsLeagueFounder() {
        return isLeagueFounder;
    }

    public void setIsLeagueFounder(Short isLeagueFounder) {
        this.isLeagueFounder = isLeagueFounder;
    }

    public Integer getAmountSubstituteBench() {
        return amountSubstituteBench;
    }

    public void setAmountSubstituteBench(Integer amountSubstituteBench) {
        this.amountSubstituteBench = amountSubstituteBench;
    }

    public Integer getAmountCoTrainer() {
        return amountCoTrainer;
    }

    public void setAmountCoTrainer(Integer amountCoTrainer) {
        this.amountCoTrainer = amountCoTrainer;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Date firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Date getSportDirectorActiveTimestamp() {
        return sportDirectorActiveTimestamp;
    }

    public void setSportDirectorActiveTimestamp(Date sportDirectorActiveTimestamp) {
        this.sportDirectorActiveTimestamp = sportDirectorActiveTimestamp;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getLastQuestionAnswered() {
        return lastQuestionAnswered;
    }

    public void setLastQuestionAnswered(Date lastQuestionAnswered) {
        this.lastQuestionAnswered = lastQuestionAnswered;
    }

    public Integer getQuestionRightAnswered() {
        return questionRightAnswered;
    }

    public void setQuestionRightAnswered(Integer questionRightAnswered) {
        this.questionRightAnswered = questionRightAnswered;
    }

    public String getLastLoginWith() {
        return lastLoginWith;
    }

    public void setLastLoginWith(String lastLoginWith) {
        this.lastLoginWith = lastLoginWith;
    }

    public int getAmountSubstituteBenchSlots() {
        return amountSubstituteBenchSlots;
    }

    public void setAmountSubstituteBenchSlots(int amountSubstituteBenchSlots) {
        this.amountSubstituteBenchSlots = amountSubstituteBenchSlots;
    }

    public Short getIsLineUpChangedByCoTrainer() {
        return isLineUpChangedByCoTrainer;
    }

    public void setIsLineUpChangedByCoTrainer(Short isLineUpChangedByCoTrainer) {
        this.isLineUpChangedByCoTrainer = isLineUpChangedByCoTrainer;
    }

    public Long getFormationID() {
        return formationID;
    }

    public void setFormationID(Long formationID) {
        this.formationID = formationID;
    }

    public Long getFantasyClubLogoID() {
        return fantasyClubLogoID;
    }

    public void setFantasyClubLogoID(Long fantasyClubLogoID) {
        this.fantasyClubLogoID = fantasyClubLogoID;
    }

    public Long getEmblemID() {
        return emblemID;
    }

    public void setEmblemID(Long emblemID) {
        this.emblemID = emblemID;
    }

    public Long getCaptainFantasyPlayerID() {
        return captainFantasyPlayerID;
    }

    public void setCaptainFantasyPlayerID(Long captainFantasyPlayerID) {
        this.captainFantasyPlayerID = captainFantasyPlayerID;
    }

    public Long getFantasyManagerID() {
        return fantasyManagerID;
    }

    public void setFantasyManagerID(Long fantasyManagerID) {
        this.fantasyManagerID = fantasyManagerID;
    }

    public Long getFantasyLeagueID() {
        return fantasyLeagueID;
    }

    public void setFantasyLeagueID(Long fantasyLeagueID) {
        this.fantasyLeagueID = fantasyLeagueID;
    }

    public static List<FantasyClubPOJO> toFantasyCLubPOJOList(List<FantasyClub> clubs) {
        FantasyClubPOJO pojo;
        List<FantasyClubPOJO> pojos = new ArrayList<>();
        for (FantasyClub club : clubs) {
            pojo = new FantasyClubPOJO(club);
            pojos.add(pojo);
        }
        return pojos;
    }
}
