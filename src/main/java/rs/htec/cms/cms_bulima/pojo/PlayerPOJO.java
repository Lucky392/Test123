/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.Player;

/**
 *
 * @author stefan
 */
public class PlayerPOJO {

    private Long id;
    private String idSport1Player;
    private Date updateAt;
    private String firstName;
    private String lastName;
    private String trikotName;
    private Short isCaptain;
    private Double sizeCm;
    private Double weightKg;
    private Date dateOfBirth;
    private String photoUrl;
    private Date dateJoinedTeam;
    private String shirtNumber;
    private Integer matchesTopLeage;
    private Integer scoreCountTopLeague;
    private Double gradeAutoSeason;
    private Double gradeAutoSeasonLeagueAvg;
    private Integer totalBLMPoints;
    private Integer marketValue;
    private Date marketValueUpdateAt;
    private Short isHurt;
    private Short hasYellowCard;
    private Short hasYellowRedCard;
    private Short hasRedCard;
    private Date createDate;
    private String photoUrl2;
    private Integer blmPointsDiff;
    private String blockType;
    private Integer blockMatchdayAmount;
    private String fullname;
//    private List<FantasyLeaguePlayer> fantasyLeaguePlayerList;
    private Long idPlayerPosition;
    private String urlToPlayerPosition;
    private Long idNation;
    private String urlToNation;
    private Long idSeasonCurrent;
    private String urlToSeasonCurrent;
    private Long idBlockStartMatchday;
    private String urlToBlockStartMatchday;
    private Long idClub;
    private String urlToClub;

    public PlayerPOJO(Player player) {
        this.id = player.getId();
        this.idSport1Player = player.getIdSport1Player();
        this.updateAt = player.getUpdateAt();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.trikotName = player.getTrikotName();
        this.isCaptain = player.getIsCaptain();
        this.sizeCm = player.getSizeCm();
        this.weightKg = player.getWeightKg();
        this.dateOfBirth = player.getDateOfBirth();
        this.photoUrl = player.getPhotoUrl();
        this.dateJoinedTeam = player.getDateJoinedTeam();
        this.shirtNumber = player.getShirtNumber();
        this.matchesTopLeage = player.getMatchesTopLeage();
        this.scoreCountTopLeague = player.getScoreCountTopLeague();
        this.gradeAutoSeason = player.getGradeAutoSeason();
        this.gradeAutoSeasonLeagueAvg = player.getGradeAutoSeasonLeagueAvg();
        this.totalBLMPoints = player.getTotalBLMPoints();
        this.marketValue = player.getMarketValue();
        this.marketValueUpdateAt = player.getMarketValueUpdateAt();
        this.isHurt = player.getIsHurt();
        this.hasYellowCard = player.getHasYellowCard();
        this.hasYellowRedCard = player.getHasYellowRedCard();
        this.hasRedCard = player.getHasRedCard();
        this.createDate = player.getCreateDate();
        this.photoUrl2 = player.getPhotoUrl2();
        this.blmPointsDiff = player.getBlmPointsDiff();
        this.blockType = player.getBlockType();
        this.blockMatchdayAmount = player.getBlockMatchdayAmount();
        this.fullname = player.getFullname();
        if (player.getIdPlayerPosition() != null) {
            this.idPlayerPosition = player.getIdPlayerPosition().getId();
            this.urlToPlayerPosition = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/playerPosition/" + player.getIdPlayerPosition().getId();
        }
        if (player.getIdNation() != null) {
            this.idNation = player.getIdNation().getId();
            this.urlToNation = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/nation/" + player.getIdNation().getId();
        }
        if (player.getIdSeasonCurrent() != null) {
            this.idSeasonCurrent = player.getIdSeasonCurrent().getId();
            this.urlToSeasonCurrent = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/season/" + player.getIdSeasonCurrent().getId();
        }
        if (player.getIdBlockStartMatchday() != null) {
            this.idBlockStartMatchday = player.getIdBlockStartMatchday().getId();
            this.urlToBlockStartMatchday = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/matchday/" + player.getIdBlockStartMatchday().getId();
        }
        if (player.getIdClub() != null) {
            this.idClub = player.getIdClub().getId();
            this.urlToClub = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/club/" + player.getIdClub().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Player() {
        return idSport1Player;
    }

    public void setIdSport1Player(String idSport1Player) {
        this.idSport1Player = idSport1Player;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTrikotName() {
        return trikotName;
    }

    public void setTrikotName(String trikotName) {
        this.trikotName = trikotName;
    }

    public Short getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Short isCaptain) {
        this.isCaptain = isCaptain;
    }

    public Double getSizeCm() {
        return sizeCm;
    }

    public void setSizeCm(Double sizeCm) {
        this.sizeCm = sizeCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getDateJoinedTeam() {
        return dateJoinedTeam;
    }

    public void setDateJoinedTeam(Date dateJoinedTeam) {
        this.dateJoinedTeam = dateJoinedTeam;
    }

    public String getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(String shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public Integer getMatchesTopLeage() {
        return matchesTopLeage;
    }

    public void setMatchesTopLeage(Integer matchesTopLeage) {
        this.matchesTopLeage = matchesTopLeage;
    }

    public Integer getScoreCountTopLeague() {
        return scoreCountTopLeague;
    }

    public void setScoreCountTopLeague(Integer scoreCountTopLeague) {
        this.scoreCountTopLeague = scoreCountTopLeague;
    }

    public Double getGradeAutoSeason() {
        return gradeAutoSeason;
    }

    public void setGradeAutoSeason(Double gradeAutoSeason) {
        this.gradeAutoSeason = gradeAutoSeason;
    }

    public Double getGradeAutoSeasonLeagueAvg() {
        return gradeAutoSeasonLeagueAvg;
    }

    public void setGradeAutoSeasonLeagueAvg(Double gradeAutoSeasonLeagueAvg) {
        this.gradeAutoSeasonLeagueAvg = gradeAutoSeasonLeagueAvg;
    }

    public Integer getTotalBLMPoints() {
        return totalBLMPoints;
    }

    public void setTotalBLMPoints(Integer totalBLMPoints) {
        this.totalBLMPoints = totalBLMPoints;
    }

    public Integer getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Integer marketValue) {
        this.marketValue = marketValue;
    }

    public Date getMarketValueUpdateAt() {
        return marketValueUpdateAt;
    }

    public void setMarketValueUpdateAt(Date marketValueUpdateAt) {
        this.marketValueUpdateAt = marketValueUpdateAt;
    }

    public Short getIsHurt() {
        return isHurt;
    }

    public void setIsHurt(Short isHurt) {
        this.isHurt = isHurt;
    }

    public Short getHasYellowCard() {
        return hasYellowCard;
    }

    public void setHasYellowCard(Short hasYellowCard) {
        this.hasYellowCard = hasYellowCard;
    }

    public Short getHasYellowRedCard() {
        return hasYellowRedCard;
    }

    public void setHasYellowRedCard(Short hasYellowRedCard) {
        this.hasYellowRedCard = hasYellowRedCard;
    }

    public Short getHasRedCard() {
        return hasRedCard;
    }

    public void setHasRedCard(Short hasRedCard) {
        this.hasRedCard = hasRedCard;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhotoUrl2() {
        return photoUrl2;
    }

    public void setPhotoUrl2(String photoUrl2) {
        this.photoUrl2 = photoUrl2;
    }

    public Integer getBlmPointsDiff() {
        return blmPointsDiff;
    }

    public void setBlmPointsDiff(Integer blmPointsDiff) {
        this.blmPointsDiff = blmPointsDiff;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public Integer getBlockMatchdayAmount() {
        return blockMatchdayAmount;
    }

    public void setBlockMatchdayAmount(Integer blockMatchdayAmount) {
        this.blockMatchdayAmount = blockMatchdayAmount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getIdPlayerPosition() {
        return idPlayerPosition;
    }

    public void setIdPlayerPosition(Long idPlayerPosition) {
        this.idPlayerPosition = idPlayerPosition;
    }

    public Long getIdNation() {
        return idNation;
    }

    public void setIdNation(Long idNation) {
        this.idNation = idNation;
    }

    public Long getIdSeasonCurrent() {
        return idSeasonCurrent;
    }

    public void setIdSeasonCurrent(Long idSeasonCurrent) {
        this.idSeasonCurrent = idSeasonCurrent;
    }

    public Long getIdBlockStartMatchday() {
        return idBlockStartMatchday;
    }

    public void setIdBlockStartMatchday(Long idBlockStartMatchday) {
        this.idBlockStartMatchday = idBlockStartMatchday;
    }

    public Long getIdClub() {
        return idClub;
    }

    public void setIdClub(Long idClub) {
        this.idClub = idClub;
    }

    public String getUrlToPlayerPosition() {
        return urlToPlayerPosition;
    }

    public void setUrlToPlayerPosition(String urlToPlayerPosition) {
        this.urlToPlayerPosition = urlToPlayerPosition;
    }

    public String getUrlToNation() {
        return urlToNation;
    }

    public void setUrlToNation(String urlToNation) {
        this.urlToNation = urlToNation;
    }

    public String getUrlToSeasonCurrent() {
        return urlToSeasonCurrent;
    }

    public void setUrlToSeasonCurrent(String urlToSeasonCurrent) {
        this.urlToSeasonCurrent = urlToSeasonCurrent;
    }

    public String getUrlToBlockStartMatchday() {
        return urlToBlockStartMatchday;
    }

    public void setUrlToBlockStartMatchday(String urlToBlockStartMatchday) {
        this.urlToBlockStartMatchday = urlToBlockStartMatchday;
    }

    public String getUrlToClub() {
        return urlToClub;
    }

    public void setUrlToClub(String urlToClub) {
        this.urlToClub = urlToClub;
    }

    
}
