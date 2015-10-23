/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.MatchPlayerStat;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class MatchPlayerStatPOJO {

    private Long id;
    private Double rating;
    private Integer score;
    private Integer assists;
    private Integer scorePenalty;
    private Integer scoreFoot;
    private Integer scoreHeader;
    private Integer scoreOwnGoal;
    private Integer goalsAgainst;
    private Integer cleanSheet;
    private Integer shots;
    private Integer shotsFoot;
    private Integer shotsHeader;
    private Integer shotsInsideBox;
    private Integer shotsOutsideBox;
    private Integer shotsPenalty;
    private Integer shotsPenaltyShotMissed;
    private Integer shotAssists;
    private Integer penaltySaves;
    private Integer playing;
    private Integer playingLineup;
    private Integer playingSubstituteIn;
    private Integer playingSubstituteOut;
    private Integer playingMinutes;
    private Integer cardYellow;
    private Integer cardYellowRed;
    private Integer cardRed;
    private Integer ballsTouched;
    private Integer passesComplete;
    private Double passesCompletePercentage;
    private Integer passesFailed;
    private Double passesFailedPercentage;
    private Integer duelsWon;
    private Integer duelsWonGround;
    private Integer duelsWonHeader;
    private Double duelsWonPercentage;
    private Integer duelsLost;
    private Integer duelsLostGround;
    private Integer duelsLostHeader;
    private Double duelsLostPercentage;
    private Integer foulsCommitted;
    private Integer foulsSuffered;
    private Integer crosses;
    private Integer cornerKicks;
    private Integer freekicks;
    private Integer offsides;
    private Integer saves;
    private Integer trackingDistance;
    private Integer trackingFastRuns;
    private Integer trackingSprints;
    private Integer trackingMaxSpeed;
    private Integer positionId;
    private Integer uniformNumber;
    private Double xCoordinate;
    private Double yCoordinate;
    private Date createDate;
    private Double grade;
    private Long idMatchPlayer;
    private String urlToMatchPlayer;

    public MatchPlayerStatPOJO(MatchPlayerStat stat) {
        this.id = stat.getId();
        this.rating = stat.getRating();
        this.score = stat.getScore();
        this.assists = stat.getAssists();
        this.scorePenalty = stat.getScorePenalty();
        this.scoreFoot = stat.getScoreFoot();
        this.scoreHeader = stat.getScoreHeader();
        this.scoreOwnGoal = stat.getScoreOwnGoal();
        this.goalsAgainst = stat.getGoalsAgainst();
        this.cleanSheet = stat.getCleanSheet();
        this.shots = stat.getShots();
        this.shotsFoot = stat.getShotsFoot();
        this.shotsHeader = stat.getShotsHeader();
        this.shotsInsideBox = stat.getShotsInsideBox();
        this.shotsOutsideBox = stat.getShotsOutsideBox();
        this.shotsPenalty = stat.getShotsPenalty();
        this.shotsPenaltyShotMissed = stat.getShotsPenaltyShotMissed();
        this.shotAssists = stat.getShotAssists();
        this.penaltySaves = stat.getPenaltySaves();
        this.playing = stat.getPlaying();
        this.playingLineup = stat.getPlayingLineup();
        this.playingSubstituteIn = stat.getPlayingSubstituteIn();
        this.playingSubstituteOut = stat.getPlayingSubstituteOut();
        this.playingMinutes = stat.getPlayingMinutes();
        this.cardYellow = stat.getCardYellow();
        this.cardYellowRed = stat.getCardYellowRed();
        this.cardRed = stat.getCardRed();
        this.ballsTouched = stat.getBallsTouched();
        this.passesComplete = stat.getPassesComplete();
        this.passesCompletePercentage = stat.getPassesCompletePercentage();
        this.passesFailed = stat.getPassesFailed();
        this.passesFailedPercentage = stat.getPassesFailedPercentage();
        this.duelsWon = stat.getDuelsWon();
        this.duelsWonGround = stat.getDuelsWonGround();
        this.duelsWonHeader = stat.getDuelsWonHeader();
        this.duelsWonPercentage = stat.getDuelsWonPercentage();
        this.duelsLost = stat.getDuelsLost();
        this.duelsLostGround = stat.getDuelsLostGround();
        this.duelsLostHeader = stat.getDuelsLostHeader();
        this.duelsLostPercentage = stat.getDuelsLostPercentage();
        this.foulsCommitted = stat.getFoulsCommitted();
        this.foulsSuffered = stat.getFoulsSuffered();
        this.crosses = stat.getCrosses();
        this.cornerKicks = stat.getCornerKicks();
        this.freekicks = stat.getFreekicks();
        this.offsides = stat.getOffsides();
        this.saves = stat.getSaves();
        this.trackingDistance = stat.getTrackingDistance();
        this.trackingFastRuns = stat.getTrackingFastRuns();
        this.trackingSprints = stat.getTrackingSprints();
        this.trackingMaxSpeed = stat.getTrackingMaxSpeed();
        this.positionId = stat.getPositionId();
        this.uniformNumber = stat.getUniformNumber();
        this.xCoordinate = stat.getXCoordinate();
        this.yCoordinate = stat.getYCoordinate();
        this.createDate = stat.getCreateDate();
        this.grade = stat.getGrade();
        if (stat.getIdMatchPlayer() != null) {
            this.idMatchPlayer = stat.getIdMatchPlayer().getId();
            this.urlToMatchPlayer = Util.getInstance().getUrl() + "rest/matchPlayer/" + stat.getIdMatchPlayer().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getScorePenalty() {
        return scorePenalty;
    }

    public void setScorePenalty(Integer scorePenalty) {
        this.scorePenalty = scorePenalty;
    }

    public Integer getScoreFoot() {
        return scoreFoot;
    }

    public void setScoreFoot(Integer scoreFoot) {
        this.scoreFoot = scoreFoot;
    }

    public Integer getScoreHeader() {
        return scoreHeader;
    }

    public void setScoreHeader(Integer scoreHeader) {
        this.scoreHeader = scoreHeader;
    }

    public Integer getScoreOwnGoal() {
        return scoreOwnGoal;
    }

    public void setScoreOwnGoal(Integer scoreOwnGoal) {
        this.scoreOwnGoal = scoreOwnGoal;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public Integer getCleanSheet() {
        return cleanSheet;
    }

    public void setCleanSheet(Integer cleanSheet) {
        this.cleanSheet = cleanSheet;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getShotsFoot() {
        return shotsFoot;
    }

    public void setShotsFoot(Integer shotsFoot) {
        this.shotsFoot = shotsFoot;
    }

    public Integer getShotsHeader() {
        return shotsHeader;
    }

    public void setShotsHeader(Integer shotsHeader) {
        this.shotsHeader = shotsHeader;
    }

    public Integer getShotsInsideBox() {
        return shotsInsideBox;
    }

    public void setShotsInsideBox(Integer shotsInsideBox) {
        this.shotsInsideBox = shotsInsideBox;
    }

    public Integer getShotsOutsideBox() {
        return shotsOutsideBox;
    }

    public void setShotsOutsideBox(Integer shotsOutsideBox) {
        this.shotsOutsideBox = shotsOutsideBox;
    }

    public Integer getShotsPenalty() {
        return shotsPenalty;
    }

    public void setShotsPenalty(Integer shotsPenalty) {
        this.shotsPenalty = shotsPenalty;
    }

    public Integer getShotsPenaltyShotMissed() {
        return shotsPenaltyShotMissed;
    }

    public void setShotsPenaltyShotMissed(Integer shotsPenaltyShotMissed) {
        this.shotsPenaltyShotMissed = shotsPenaltyShotMissed;
    }

    public Integer getShotAssists() {
        return shotAssists;
    }

    public void setShotAssists(Integer shotAssists) {
        this.shotAssists = shotAssists;
    }

    public Integer getPenaltySaves() {
        return penaltySaves;
    }

    public void setPenaltySaves(Integer penaltySaves) {
        this.penaltySaves = penaltySaves;
    }

    public Integer getPlaying() {
        return playing;
    }

    public void setPlaying(Integer playing) {
        this.playing = playing;
    }

    public Integer getPlayingLineup() {
        return playingLineup;
    }

    public void setPlayingLineup(Integer playingLineup) {
        this.playingLineup = playingLineup;
    }

    public Integer getPlayingSubstituteIn() {
        return playingSubstituteIn;
    }

    public void setPlayingSubstituteIn(Integer playingSubstituteIn) {
        this.playingSubstituteIn = playingSubstituteIn;
    }

    public Integer getPlayingSubstituteOut() {
        return playingSubstituteOut;
    }

    public void setPlayingSubstituteOut(Integer playingSubstituteOut) {
        this.playingSubstituteOut = playingSubstituteOut;
    }

    public Integer getPlayingMinutes() {
        return playingMinutes;
    }

    public void setPlayingMinutes(Integer playingMinutes) {
        this.playingMinutes = playingMinutes;
    }

    public Integer getCardYellow() {
        return cardYellow;
    }

    public void setCardYellow(Integer cardYellow) {
        this.cardYellow = cardYellow;
    }

    public Integer getCardYellowRed() {
        return cardYellowRed;
    }

    public void setCardYellowRed(Integer cardYellowRed) {
        this.cardYellowRed = cardYellowRed;
    }

    public Integer getCardRed() {
        return cardRed;
    }

    public void setCardRed(Integer cardRed) {
        this.cardRed = cardRed;
    }

    public Integer getBallsTouched() {
        return ballsTouched;
    }

    public void setBallsTouched(Integer ballsTouched) {
        this.ballsTouched = ballsTouched;
    }

    public Integer getPassesComplete() {
        return passesComplete;
    }

    public void setPassesComplete(Integer passesComplete) {
        this.passesComplete = passesComplete;
    }

    public Double getPassesCompletePercentage() {
        return passesCompletePercentage;
    }

    public void setPassesCompletePercentage(Double passesCompletePercentage) {
        this.passesCompletePercentage = passesCompletePercentage;
    }

    public Integer getPassesFailed() {
        return passesFailed;
    }

    public void setPassesFailed(Integer passesFailed) {
        this.passesFailed = passesFailed;
    }

    public Double getPassesFailedPercentage() {
        return passesFailedPercentage;
    }

    public void setPassesFailedPercentage(Double passesFailedPercentage) {
        this.passesFailedPercentage = passesFailedPercentage;
    }

    public Integer getDuelsWon() {
        return duelsWon;
    }

    public void setDuelsWon(Integer duelsWon) {
        this.duelsWon = duelsWon;
    }

    public Integer getDuelsWonGround() {
        return duelsWonGround;
    }

    public void setDuelsWonGround(Integer duelsWonGround) {
        this.duelsWonGround = duelsWonGround;
    }

    public Integer getDuelsWonHeader() {
        return duelsWonHeader;
    }

    public void setDuelsWonHeader(Integer duelsWonHeader) {
        this.duelsWonHeader = duelsWonHeader;
    }

    public Double getDuelsWonPercentage() {
        return duelsWonPercentage;
    }

    public void setDuelsWonPercentage(Double duelsWonPercentage) {
        this.duelsWonPercentage = duelsWonPercentage;
    }

    public Integer getDuelsLost() {
        return duelsLost;
    }

    public void setDuelsLost(Integer duelsLost) {
        this.duelsLost = duelsLost;
    }

    public Integer getDuelsLostGround() {
        return duelsLostGround;
    }

    public void setDuelsLostGround(Integer duelsLostGround) {
        this.duelsLostGround = duelsLostGround;
    }

    public Integer getDuelsLostHeader() {
        return duelsLostHeader;
    }

    public void setDuelsLostHeader(Integer duelsLostHeader) {
        this.duelsLostHeader = duelsLostHeader;
    }

    public Double getDuelsLostPercentage() {
        return duelsLostPercentage;
    }

    public void setDuelsLostPercentage(Double duelsLostPercentage) {
        this.duelsLostPercentage = duelsLostPercentage;
    }

    public Integer getFoulsCommitted() {
        return foulsCommitted;
    }

    public void setFoulsCommitted(Integer foulsCommitted) {
        this.foulsCommitted = foulsCommitted;
    }

    public Integer getFoulsSuffered() {
        return foulsSuffered;
    }

    public void setFoulsSuffered(Integer foulsSuffered) {
        this.foulsSuffered = foulsSuffered;
    }

    public Integer getCrosses() {
        return crosses;
    }

    public void setCrosses(Integer crosses) {
        this.crosses = crosses;
    }

    public Integer getCornerKicks() {
        return cornerKicks;
    }

    public void setCornerKicks(Integer cornerKicks) {
        this.cornerKicks = cornerKicks;
    }

    public Integer getFreekicks() {
        return freekicks;
    }

    public void setFreekicks(Integer freekicks) {
        this.freekicks = freekicks;
    }

    public Integer getOffsides() {
        return offsides;
    }

    public void setOffsides(Integer offsides) {
        this.offsides = offsides;
    }

    public Integer getSaves() {
        return saves;
    }

    public void setSaves(Integer saves) {
        this.saves = saves;
    }

    public Integer getTrackingDistance() {
        return trackingDistance;
    }

    public void setTrackingDistance(Integer trackingDistance) {
        this.trackingDistance = trackingDistance;
    }

    public Integer getTrackingFastRuns() {
        return trackingFastRuns;
    }

    public void setTrackingFastRuns(Integer trackingFastRuns) {
        this.trackingFastRuns = trackingFastRuns;
    }

    public Integer getTrackingSprints() {
        return trackingSprints;
    }

    public void setTrackingSprints(Integer trackingSprints) {
        this.trackingSprints = trackingSprints;
    }

    public Integer getTrackingMaxSpeed() {
        return trackingMaxSpeed;
    }

    public void setTrackingMaxSpeed(Integer trackingMaxSpeed) {
        this.trackingMaxSpeed = trackingMaxSpeed;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getUniformNumber() {
        return uniformNumber;
    }

    public void setUniformNumber(Integer uniformNumber) {
        this.uniformNumber = uniformNumber;
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getIdMatchPlayer() {
        return idMatchPlayer;
    }

    public void setIdMatchPlayer(Long idMatchPlayer) {
        this.idMatchPlayer = idMatchPlayer;
    }

    public String getUrlToMatchPlayer() {
        return urlToMatchPlayer;
    }

    public void setUrlToMatchPlayer(String urlToMatchPlayer) {
        this.urlToMatchPlayer = urlToMatchPlayer;
    }

    public static List<MatchPlayerStatPOJO> toMatchPlayerStatPOJOList(List<MatchPlayerStat> matchPlayerStats) {
        MatchPlayerStatPOJO pojo;
        List<MatchPlayerStatPOJO> pojos = new ArrayList<>();
        for (MatchPlayerStat matchPlayerStat : matchPlayerStats) {
            pojo = new MatchPlayerStatPOJO(matchPlayerStat);
            pojos.add(pojo);
        }
        return pojos;
    }

}
