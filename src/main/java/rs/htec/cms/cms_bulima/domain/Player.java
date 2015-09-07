/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findById", query = "SELECT p FROM Player p WHERE p.id = :id"),
    @NamedQuery(name = "Player.findByIdSport1Player", query = "SELECT p FROM Player p WHERE p.idSport1Player = :idSport1Player"),
    @NamedQuery(name = "Player.findByUpdateAt", query = "SELECT p FROM Player p WHERE p.updateAt = :updateAt"),
    @NamedQuery(name = "Player.findByFirstName", query = "SELECT p FROM Player p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Player.findByLastName", query = "SELECT p FROM Player p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Player.findByTrikotName", query = "SELECT p FROM Player p WHERE p.trikotName = :trikotName"),
    @NamedQuery(name = "Player.findByIsCaptain", query = "SELECT p FROM Player p WHERE p.isCaptain = :isCaptain"),
    @NamedQuery(name = "Player.findBySizeCm", query = "SELECT p FROM Player p WHERE p.sizeCm = :sizeCm"),
    @NamedQuery(name = "Player.findByWeightKg", query = "SELECT p FROM Player p WHERE p.weightKg = :weightKg"),
    @NamedQuery(name = "Player.findByDateOfBirth", query = "SELECT p FROM Player p WHERE p.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Player.findByPhotoUrl", query = "SELECT p FROM Player p WHERE p.photoUrl = :photoUrl"),
    @NamedQuery(name = "Player.findByDateJoinedTeam", query = "SELECT p FROM Player p WHERE p.dateJoinedTeam = :dateJoinedTeam"),
    @NamedQuery(name = "Player.findByShirtNumber", query = "SELECT p FROM Player p WHERE p.shirtNumber = :shirtNumber"),
    @NamedQuery(name = "Player.findByMatchesTopLeage", query = "SELECT p FROM Player p WHERE p.matchesTopLeage = :matchesTopLeage"),
    @NamedQuery(name = "Player.findByScoreCountTopLeague", query = "SELECT p FROM Player p WHERE p.scoreCountTopLeague = :scoreCountTopLeague"),
    @NamedQuery(name = "Player.findByGradeAutoSeason", query = "SELECT p FROM Player p WHERE p.gradeAutoSeason = :gradeAutoSeason"),
    @NamedQuery(name = "Player.findByGradeAutoSeasonLeagueAvg", query = "SELECT p FROM Player p WHERE p.gradeAutoSeasonLeagueAvg = :gradeAutoSeasonLeagueAvg"),
    @NamedQuery(name = "Player.findByTotalBLMPoints", query = "SELECT p FROM Player p WHERE p.totalBLMPoints = :totalBLMPoints"),
    @NamedQuery(name = "Player.findByMarketValue", query = "SELECT p FROM Player p WHERE p.marketValue = :marketValue"),
    @NamedQuery(name = "Player.findByMarketValueUpdateAt", query = "SELECT p FROM Player p WHERE p.marketValueUpdateAt = :marketValueUpdateAt"),
    @NamedQuery(name = "Player.findByIsHurt", query = "SELECT p FROM Player p WHERE p.isHurt = :isHurt"),
    @NamedQuery(name = "Player.findByHasYellowCard", query = "SELECT p FROM Player p WHERE p.hasYellowCard = :hasYellowCard"),
    @NamedQuery(name = "Player.findByHasYellowRedCard", query = "SELECT p FROM Player p WHERE p.hasYellowRedCard = :hasYellowRedCard"),
    @NamedQuery(name = "Player.findByHasRedCard", query = "SELECT p FROM Player p WHERE p.hasRedCard = :hasRedCard"),
    @NamedQuery(name = "Player.findByCreateDate", query = "SELECT p FROM Player p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "Player.findByPhotoUrl2", query = "SELECT p FROM Player p WHERE p.photoUrl2 = :photoUrl2"),
    @NamedQuery(name = "Player.findByBlmPointsDiff", query = "SELECT p FROM Player p WHERE p.blmPointsDiff = :blmPointsDiff"),
    @NamedQuery(name = "Player.findByBlockType", query = "SELECT p FROM Player p WHERE p.blockType = :blockType"),
    @NamedQuery(name = "Player.findByBlockMatchdayAmount", query = "SELECT p FROM Player p WHERE p.blockMatchdayAmount = :blockMatchdayAmount"),
    @NamedQuery(name = "Player.findByFullname", query = "SELECT p FROM Player p WHERE p.fullname = :fullname")})
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ID_SPORT1_PLAYER")
    private String idSport1Player;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Size(max = 255)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 255)
    @Column(name = "lastName")
    private String lastName;
    @Size(max = 255)
    @Column(name = "trikotName")
    private String trikotName;
    @Column(name = "isCaptain")
    private Short isCaptain;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sizeCm")
    private Double sizeCm;
    @Column(name = "weightKg")
    private Double weightKg;
    @Column(name = "dateOfBirth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    @Size(max = 255)
    @Column(name = "photoUrl")
    private String photoUrl;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "dateJoinedTeam")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateJoinedTeam;
    @Size(max = 255)
    @Column(name = "shirtNumber")
    private String shirtNumber;
    @Column(name = "matchesTopLeage")
    private Integer matchesTopLeage;
    @Column(name = "scoreCountTopLeague")
    private Integer scoreCountTopLeague;
    @Column(name = "gradeAutoSeason")
    private Double gradeAutoSeason;
    @Column(name = "gradeAutoSeasonLeagueAvg")
    private Double gradeAutoSeasonLeagueAvg;
    @Column(name = "totalBLMPoints")
    private Integer totalBLMPoints;
    @Column(name = "marketValue")
    private Integer marketValue;
    @Column(name = "marketValueUpdateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date marketValueUpdateAt;
    @Column(name = "isHurt")
    private Short isHurt;
    @Column(name = "hasYellowCard")
    private Short hasYellowCard;
    @Column(name = "hasYellowRedCard")
    private Short hasYellowRedCard;
    @Column(name = "hasRedCard")
    private Short hasRedCard;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "photoUrl2")
    private String photoUrl2;
    @Column(name = "blmPointsDiff")
    private Integer blmPointsDiff;
    @Size(max = 255)
    @Column(name = "blockType")
    private String blockType;
    @Column(name = "blockMatchdayAmount")
    private Integer blockMatchdayAmount;
    @Size(max = 255)
    @Column(name = "fullname")
    private String fullname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlayer")
    private List<FantasyLeaguePlayer> fantasyLeaguePlayerList;
    @JoinColumn(name = "ID_PLAYER_POSITION", referencedColumnName = "ID")
    @ManyToOne
    private PlayerPosition idPlayerPosition;
    @JoinColumn(name = "ID_NATION", referencedColumnName = "ID")
    @ManyToOne
    private Nation idNation;
    @JoinColumn(name = "ID_SEASON_CURRENT", referencedColumnName = "ID")
    @ManyToOne
    private Season idSeasonCurrent;
    @JoinColumn(name = "ID_BLOCK_START_MATCHDAY", referencedColumnName = "ID")
    @ManyToOne
    private Matchday idBlockStartMatchday;
    @JoinColumn(name = "ID_CLUB", referencedColumnName = "ID")
    @ManyToOne
    private Club idClub;

    public Player() {
    }

    public Player(Long id) {
        this.id = id;
    }

    public Player(Long id, Date updateAt, Date createDate) {
        this.id = id;
        this.updateAt = updateAt;
        this.createDate = createDate;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    @XmlTransient
    @JsonIgnore
    public List<FantasyLeaguePlayer> getFantasyLeaguePlayerList() {
        return fantasyLeaguePlayerList;
    }

    public void setFantasyLeaguePlayerList(List<FantasyLeaguePlayer> fantasyLeaguePlayerList) {
        this.fantasyLeaguePlayerList = fantasyLeaguePlayerList;
    }

    public PlayerPosition getIdPlayerPosition() {
        return idPlayerPosition;
    }

    public void setIdPlayerPosition(PlayerPosition idPlayerPosition) {
        this.idPlayerPosition = idPlayerPosition;
    }

    public Nation getIdNation() {
        return idNation;
    }

    public void setIdNation(Nation idNation) {
        this.idNation = idNation;
    }

    public Season getIdSeasonCurrent() {
        return idSeasonCurrent;
    }

    public void setIdSeasonCurrent(Season idSeasonCurrent) {
        this.idSeasonCurrent = idSeasonCurrent;
    }

    public Matchday getIdBlockStartMatchday() {
        return idBlockStartMatchday;
    }

    public void setIdBlockStartMatchday(Matchday idBlockStartMatchday) {
        this.idBlockStartMatchday = idBlockStartMatchday;
    }

    public Club getIdClub() {
        return idClub;
    }

    public void setIdClub(Club idClub) {
        this.idClub = idClub;
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
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Player[ id=" + id + " ]";
    }
    
}
