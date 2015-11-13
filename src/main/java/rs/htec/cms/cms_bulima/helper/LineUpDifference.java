/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import com.sun.jersey.api.core.InjectParam;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.pojo.FantasyClubLineUpPlayerPOJO;

/**
 *
 * @author stefan
 */
public class LineUpDifference {

    @InjectParam
    RestHelperClass helper;

    public static FantasyClubLineUp getLineUp(EntityManager em, long idFantasyClub, long idMatchday) {
        FantasyClubLineUp lineUp = null;
        try {
            String query = "SELECT f FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            lineUp = (FantasyClubLineUp) em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
        return lineUp;
    }

    public static List<FantasyClubLineUpPlayer> getLineUpList(EntityManager em, long lineUpId) {
        List<FantasyClubLineUpPlayer> lineUpPlayer = em.createNamedQuery("FantasyClubLineUpPlayer.findByLineUpId").setParameter("idLineUp", lineUpId).getResultList();

        if (lineUpPlayer == null || lineUpPlayer.isEmpty()) {
            throw new DataNotFoundException("There is no LineUpPlayers for this search!");
        }
        return lineUpPlayer;
    }

    public static String getFormation(EntityManager em, long idFantasyClub, long idMatchday) {
        String formation = null;
        try {
            String query = "SELECT f.idFormation.name FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            formation = (String) em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
        return formation;
    }

    public static String returnDifferenceForFormation(String formation1, String formation2) {
        return (formation1.equals(formation2)) ? "Formations are same: " + formation1 : "Formations are different, frst formation: " + formation1 + ", second: " + formation2;
    }

    public static long getCount(List<FantasyClubLineUpPlayer> lineUp) {
        return lineUp.size();
    }

    public static boolean areEqual(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        return lineUp.equals(lineUpHistory);
    }

    public static List<FantasyClubLineUpPlayer> returnDifference(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        List<FantasyClubLineUpPlayer> difference = new ArrayList<>();
        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : lineUpHistory) {
            boolean find = false;
            for (FantasyClubLineUpPlayer lineUpPlayer : lineUp) {
                if (lineUpHistoryPlayer.getIdPlayerSlot().equals(lineUpPlayer.getIdPlayerSlot())) {
                    find = true;
                    if (!lineUpHistoryPlayer.getIdLeaguePlayer().equals(lineUpPlayer.getIdLeaguePlayer()) || !lineUpHistoryPlayer.getIsCaptain().equals(lineUpPlayer.getIsCaptain())) {
                        difference.add(lineUpHistoryPlayer);
                    }
                    break;
                }
            }
            if (!find) {
                difference.add(lineUpHistoryPlayer);
            }
        }
        return difference;
    }

    public static List<FantasyClubLineUpPlayerPOJO> returnDifferencePOJO(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        List<FantasyClubLineUpPlayerPOJO> difference = new ArrayList<>();
        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : lineUpHistory) {
            boolean found = false;
            for (FantasyClubLineUpPlayer lineUpPlayer : lineUp) {
                if (lineUpHistoryPlayer.getIdPlayerSlot().equals(lineUpPlayer.getIdPlayerSlot())) {
                    found = true;
                    if (!lineUpHistoryPlayer.getIdLeaguePlayer().equals(lineUpPlayer.getIdLeaguePlayer()) || !lineUpHistoryPlayer.getIsCaptain().equals(lineUpPlayer.getIsCaptain())) {
                        FantasyClubLineUpPlayerPOJO player = new FantasyClubLineUpPlayerPOJO(lineUpHistoryPlayer);
                        difference.add(player);
                    }
                    break;
                }
            }
            if (!found) {
                FantasyClubLineUpPlayerPOJO player = new FantasyClubLineUpPlayerPOJO(lineUpHistoryPlayer);
                difference.add(player);
            }
        }
        return difference;
    }

    private static String getPositionNameForPlayer(FantasyClubLineUpPlayer player) {
        if (player == null) {
            return null;
        }
        String form = player.getIdLineUp().getIdFormation().getName();
        String[] formation = form.split("-");
        long playerPosition = player.getIdPlayerSlot().getId();
        long position = 1;
        if (playerPosition == position) {
            return "TW";
        }
        position += Long.valueOf(formation[0]);
        if (playerPosition <= position) {
            return "AB";
        }
        position += Long.valueOf(formation[1]);
        if (playerPosition <= position) {
            return "MF";
        }
        position += Long.valueOf(formation[2]);
        if (playerPosition <= position) {
            return "ST";
        }
        return "SUB";
    }

//    public List<LineUpPlayerPOJO> toLineUpPlayerPOJO(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayer) {
//        List<LineUpPlayerPOJO> lineUpPlayer = null;
//        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : fantasyClubLineUpPlayer) {
//            LineUpPlayerPOJO player = new LineUpPlayerPOJO(lineUpHistoryPlayer.getId(), lineUpHistoryPlayer.getIsCaptain(), lineUpHistoryPlayer.getIdPlayerSlot().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getIdPlayer().getFullname(), lineUpHistoryPlayer.getIdLineUp().getId(), getPositionNameForPlayer(lineUpHistoryPlayer));
//            lineUpPlayer.add(player);
//        }
//        return lineUpPlayer;
//    }
    public static Long getBlmPoints(EntityManager em, long idFantasyClub, long idMatchday, Date date) {
        long points = 0;
        try {

            //SELECT sum(mp.blmPoints) FROM bulima.MATCH_PLAYER mp 
            //LEFT JOIN bulima.FANTASY_LEAGUE_PLAYER flp ON (flp.ID_PLAYER=mp.ID_PLAYER)
            //LEFT JOIN bulima.FANTASY_CLUB_LINE_UP_PLAYER lup ON(lup.ID_LEAGUE_PLAYER=flp.ID)
            //LEFT JOIN bulima.FANTASY_CLUB_LINE_UP lu  ON(lu.ID = lup.ID_LINE_UP)
            //WHERE lu.ID_FANTASY_CLUB=27217 AND lu.ID_MATCHDAY=1 AND mp.ratingAt BETWEEN lu.createDate AND date_add(lu.createDate, interval 7 day);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 4);

            String blmPointsQuery = "SELECT sum(mp.blmPoints) FROM bulima.MATCH_PLAYER mp LEFT JOIN bulima.FANTASY_LEAGUE_PLAYER flp ON (flp.ID_PLAYER=mp.ID_PLAYER)"
                    + " LEFT JOIN bulima.FANTASY_CLUB_LINE_UP_PLAYER lup ON(lup.ID_LEAGUE_PLAYER=flp.ID) LEFT JOIN bulima.FANTASY_CLUB_LINE_UP lu ON(lu.ID = lup.ID_LINE_UP)"
                    + "WHERE lu.ID_FANTASY_CLUB=" + idFantasyClub + " AND lu.ID_MATCHDAY=" + idMatchday + " AND mp.ratingAt BETWEEN lu.createDate AND '" + sdf.format(calendar.getTime()) + "'";
            //points = ((BigDecimal) em.createNativeQuery(blmPointsQuery).getSingleResult()).longValue();
            points = ((BigDecimal) em.createNativeQuery(blmPointsQuery).getSingleResult()).longValue();
        } catch (Exception e) {
            return null;
//            throw new DataNotFoundException("No bulima points data for FantasyClub with id " + idFantasyClub + ", and Matchday with id " + idMatchday);
        }

        return points;
    }

}
