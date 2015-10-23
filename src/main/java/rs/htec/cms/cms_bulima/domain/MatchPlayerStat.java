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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "MATCH_PLAYER_STAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchPlayerStat.findAll", query = "SELECT m FROM MatchPlayerStat m"),
    @NamedQuery(name = "MatchPlayerStat.findById", query = "SELECT m FROM MatchPlayerStat m WHERE m.id = :id"),
    @NamedQuery(name = "MatchPlayerStat.findByRating", query = "SELECT m FROM MatchPlayerStat m WHERE m.rating = :rating"),
    @NamedQuery(name = "MatchPlayerStat.findByScore", query = "SELECT m FROM MatchPlayerStat m WHERE m.score = :score"),
    @NamedQuery(name = "MatchPlayerStat.findByAssists", query = "SELECT m FROM MatchPlayerStat m WHERE m.assists = :assists"),
    @NamedQuery(name = "MatchPlayerStat.findByScorePenalty", query = "SELECT m FROM MatchPlayerStat m WHERE m.scorePenalty = :scorePenalty"),
    @NamedQuery(name = "MatchPlayerStat.findByScoreFoot", query = "SELECT m FROM MatchPlayerStat m WHERE m.scoreFoot = :scoreFoot"),
    @NamedQuery(name = "MatchPlayerStat.findByScoreHeader", query = "SELECT m FROM MatchPlayerStat m WHERE m.scoreHeader = :scoreHeader"),
    @NamedQuery(name = "MatchPlayerStat.findByScoreOwnGoal", query = "SELECT m FROM MatchPlayerStat m WHERE m.scoreOwnGoal = :scoreOwnGoal"),
    @NamedQuery(name = "MatchPlayerStat.findByGoalsAgainst", query = "SELECT m FROM MatchPlayerStat m WHERE m.goalsAgainst = :goalsAgainst"),
    @NamedQuery(name = "MatchPlayerStat.findByCleanSheet", query = "SELECT m FROM MatchPlayerStat m WHERE m.cleanSheet = :cleanSheet"),
    @NamedQuery(name = "MatchPlayerStat.findByShots", query = "SELECT m FROM MatchPlayerStat m WHERE m.shots = :shots"),
    @NamedQuery(name = "MatchPlayerStat.findByShotsFoot", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotsFoot = :shotsFoot"),
    @NamedQuery(name = "MatchPlayerStat.findByShotsHeader", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotsHeader = :shotsHeader"),
    @NamedQuery(name = "MatchPlayerStat.findByShotsInsideBox", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotsInsideBox = :shotsInsideBox"),
    @NamedQuery(name = "MatchPlayerStat.findByShotsOutsideBox", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotsOutsideBox = :shotsOutsideBox"),
    @NamedQuery(name = "MatchPlayerStat.findByShotsPenalty", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotsPenalty = :shotsPenalty"),
    @NamedQuery(name = "MatchPlayerStat.findByShotsPenaltyShotMissed", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotsPenaltyShotMissed = :shotsPenaltyShotMissed"),
    @NamedQuery(name = "MatchPlayerStat.findByShotAssists", query = "SELECT m FROM MatchPlayerStat m WHERE m.shotAssists = :shotAssists"),
    @NamedQuery(name = "MatchPlayerStat.findByPenaltySaves", query = "SELECT m FROM MatchPlayerStat m WHERE m.penaltySaves = :penaltySaves"),
    @NamedQuery(name = "MatchPlayerStat.findByPlaying", query = "SELECT m FROM MatchPlayerStat m WHERE m.playing = :playing"),
    @NamedQuery(name = "MatchPlayerStat.findByPlayingLineup", query = "SELECT m FROM MatchPlayerStat m WHERE m.playingLineup = :playingLineup"),
    @NamedQuery(name = "MatchPlayerStat.findByPlayingSubstituteIn", query = "SELECT m FROM MatchPlayerStat m WHERE m.playingSubstituteIn = :playingSubstituteIn"),
    @NamedQuery(name = "MatchPlayerStat.findByPlayingSubstituteOut", query = "SELECT m FROM MatchPlayerStat m WHERE m.playingSubstituteOut = :playingSubstituteOut"),
    @NamedQuery(name = "MatchPlayerStat.findByPlayingMinutes", query = "SELECT m FROM MatchPlayerStat m WHERE m.playingMinutes = :playingMinutes"),
    @NamedQuery(name = "MatchPlayerStat.findByCardYellow", query = "SELECT m FROM MatchPlayerStat m WHERE m.cardYellow = :cardYellow"),
    @NamedQuery(name = "MatchPlayerStat.findByCardYellowRed", query = "SELECT m FROM MatchPlayerStat m WHERE m.cardYellowRed = :cardYellowRed"),
    @NamedQuery(name = "MatchPlayerStat.findByCardRed", query = "SELECT m FROM MatchPlayerStat m WHERE m.cardRed = :cardRed"),
    @NamedQuery(name = "MatchPlayerStat.findByBallsTouched", query = "SELECT m FROM MatchPlayerStat m WHERE m.ballsTouched = :ballsTouched"),
    @NamedQuery(name = "MatchPlayerStat.findByPassesComplete", query = "SELECT m FROM MatchPlayerStat m WHERE m.passesComplete = :passesComplete"),
    @NamedQuery(name = "MatchPlayerStat.findByPassesCompletePercentage", query = "SELECT m FROM MatchPlayerStat m WHERE m.passesCompletePercentage = :passesCompletePercentage"),
    @NamedQuery(name = "MatchPlayerStat.findByPassesFailed", query = "SELECT m FROM MatchPlayerStat m WHERE m.passesFailed = :passesFailed"),
    @NamedQuery(name = "MatchPlayerStat.findByPassesFailedPercentage", query = "SELECT m FROM MatchPlayerStat m WHERE m.passesFailedPercentage = :passesFailedPercentage"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsWon", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsWon = :duelsWon"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsWonGround", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsWonGround = :duelsWonGround"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsWonHeader", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsWonHeader = :duelsWonHeader"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsWonPercentage", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsWonPercentage = :duelsWonPercentage"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsLost", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsLost = :duelsLost"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsLostGround", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsLostGround = :duelsLostGround"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsLostHeader", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsLostHeader = :duelsLostHeader"),
    @NamedQuery(name = "MatchPlayerStat.findByDuelsLostPercentage", query = "SELECT m FROM MatchPlayerStat m WHERE m.duelsLostPercentage = :duelsLostPercentage"),
    @NamedQuery(name = "MatchPlayerStat.findByFoulsCommitted", query = "SELECT m FROM MatchPlayerStat m WHERE m.foulsCommitted = :foulsCommitted"),
    @NamedQuery(name = "MatchPlayerStat.findByFoulsSuffered", query = "SELECT m FROM MatchPlayerStat m WHERE m.foulsSuffered = :foulsSuffered"),
    @NamedQuery(name = "MatchPlayerStat.findByCrosses", query = "SELECT m FROM MatchPlayerStat m WHERE m.crosses = :crosses"),
    @NamedQuery(name = "MatchPlayerStat.findByCornerKicks", query = "SELECT m FROM MatchPlayerStat m WHERE m.cornerKicks = :cornerKicks"),
    @NamedQuery(name = "MatchPlayerStat.findByFreekicks", query = "SELECT m FROM MatchPlayerStat m WHERE m.freekicks = :freekicks"),
    @NamedQuery(name = "MatchPlayerStat.findByOffsides", query = "SELECT m FROM MatchPlayerStat m WHERE m.offsides = :offsides"),
    @NamedQuery(name = "MatchPlayerStat.findBySaves", query = "SELECT m FROM MatchPlayerStat m WHERE m.saves = :saves"),
    @NamedQuery(name = "MatchPlayerStat.findByTrackingDistance", query = "SELECT m FROM MatchPlayerStat m WHERE m.trackingDistance = :trackingDistance"),
    @NamedQuery(name = "MatchPlayerStat.findByTrackingFastRuns", query = "SELECT m FROM MatchPlayerStat m WHERE m.trackingFastRuns = :trackingFastRuns"),
    @NamedQuery(name = "MatchPlayerStat.findByTrackingSprints", query = "SELECT m FROM MatchPlayerStat m WHERE m.trackingSprints = :trackingSprints"),
    @NamedQuery(name = "MatchPlayerStat.findByTrackingMaxSpeed", query = "SELECT m FROM MatchPlayerStat m WHERE m.trackingMaxSpeed = :trackingMaxSpeed"),
    @NamedQuery(name = "MatchPlayerStat.findByPositionId", query = "SELECT m FROM MatchPlayerStat m WHERE m.positionId = :positionId"),
    @NamedQuery(name = "MatchPlayerStat.findByUniformNumber", query = "SELECT m FROM MatchPlayerStat m WHERE m.uniformNumber = :uniformNumber"),
    @NamedQuery(name = "MatchPlayerStat.findByXCoordinate", query = "SELECT m FROM MatchPlayerStat m WHERE m.xCoordinate = :xCoordinate"),
    @NamedQuery(name = "MatchPlayerStat.findByYCoordinate", query = "SELECT m FROM MatchPlayerStat m WHERE m.yCoordinate = :yCoordinate"),
    @NamedQuery(name = "MatchPlayerStat.findByCreateDate", query = "SELECT m FROM MatchPlayerStat m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "MatchPlayerStat.findByGrade", query = "SELECT m FROM MatchPlayerStat m WHERE m.grade = :grade")})
public class MatchPlayerStat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Double rating;
    @Column(name = "score")
    private Integer score;
    @Column(name = "assists")
    private Integer assists;
    @Column(name = "score_penalty")
    private Integer scorePenalty;
    @Column(name = "score_foot")
    private Integer scoreFoot;
    @Column(name = "score_header")
    private Integer scoreHeader;
    @Column(name = "score_own_goal")
    private Integer scoreOwnGoal;
    @Column(name = "goals_against")
    private Integer goalsAgainst;
    @Column(name = "clean_sheet")
    private Integer cleanSheet;
    @Column(name = "shots")
    private Integer shots;
    @Column(name = "shots_foot")
    private Integer shotsFoot;
    @Column(name = "shots_header")
    private Integer shotsHeader;
    @Column(name = "shots_inside_box")
    private Integer shotsInsideBox;
    @Column(name = "shots_outside_box")
    private Integer shotsOutsideBox;
    @Column(name = "shots_penalty")
    private Integer shotsPenalty;
    @Column(name = "shots_penalty_shot_missed")
    private Integer shotsPenaltyShotMissed;
    @Column(name = "shot_assists")
    private Integer shotAssists;
    @Column(name = "penalty_saves")
    private Integer penaltySaves;
    @Column(name = "playing")
    private Integer playing;
    @Column(name = "playing_lineup")
    private Integer playingLineup;
    @Column(name = "playing_substitute_in")
    private Integer playingSubstituteIn;
    @Column(name = "playing_substitute_out")
    private Integer playingSubstituteOut;
    @Column(name = "playing_minutes")
    private Integer playingMinutes;
    @Column(name = "card_yellow")
    private Integer cardYellow;
    @Column(name = "card_yellow_red")
    private Integer cardYellowRed;
    @Column(name = "card_red")
    private Integer cardRed;
    @Column(name = "balls_touched")
    private Integer ballsTouched;
    @Column(name = "passes_complete")
    private Integer passesComplete;
    @Column(name = "passes_complete_percentage")
    private Double passesCompletePercentage;
    @Column(name = "passes_failed")
    private Integer passesFailed;
    @Column(name = "passes_failed_percentage")
    private Double passesFailedPercentage;
    @Column(name = "duels_won")
    private Integer duelsWon;
    @Column(name = "duels_won_ground")
    private Integer duelsWonGround;
    @Column(name = "duels_won_header")
    private Integer duelsWonHeader;
    @Column(name = "duels_won_percentage")
    private Double duelsWonPercentage;
    @Column(name = "duels_lost")
    private Integer duelsLost;
    @Column(name = "duels_lost_ground")
    private Integer duelsLostGround;
    @Column(name = "duels_lost_header")
    private Integer duelsLostHeader;
    @Column(name = "duels_lost_percentage")
    private Double duelsLostPercentage;
    @Column(name = "fouls_committed")
    private Integer foulsCommitted;
    @Column(name = "fouls_suffered")
    private Integer foulsSuffered;
    @Column(name = "crosses")
    private Integer crosses;
    @Column(name = "corner_kicks")
    private Integer cornerKicks;
    @Column(name = "freekicks")
    private Integer freekicks;
    @Column(name = "offsides")
    private Integer offsides;
    @Column(name = "saves")
    private Integer saves;
    @Column(name = "tracking_distance")
    private Integer trackingDistance;
    @Column(name = "tracking_fast_runs")
    private Integer trackingFastRuns;
    @Column(name = "tracking_sprints")
    private Integer trackingSprints;
    @Column(name = "tracking_max_speed")
    private Integer trackingMaxSpeed;
    @Column(name = "position_id")
    private Integer positionId;
    @Column(name = "uniform_number")
    private Integer uniformNumber;
    @Column(name = "x_coordinate")
    private Double xCoordinate;
    @Column(name = "y_coordinate")
    private Double yCoordinate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "grade")
    private Double grade;
    @JoinColumn(name = "ID_MATCH_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MatchPlayer idMatchPlayer;

    public MatchPlayerStat() {
    }

    public MatchPlayerStat(Long id) {
        this.id = id;
    }

    public MatchPlayerStat(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
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

    public Double getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(Double yCoordinate) {
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

    public MatchPlayer getIdMatchPlayer() {
        return idMatchPlayer;
    }

    public void setIdMatchPlayer(MatchPlayer idMatchPlayer) {
        this.idMatchPlayer = idMatchPlayer;
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
        if (!(object instanceof MatchPlayerStat)) {
            return false;
        }
        MatchPlayerStat other = (MatchPlayerStat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchPlayerStat[ id=" + id + " ]";
    }
    
}
