/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.List;
import javax.persistence.EntityManager;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;

/**
 *
 * @author stefan
 */
public class LineUpWrapper {

    RestHelperClass helper;

    public LineUpWrapper() {
        helper = new RestHelperClass();
    }

    public List<FantasyClubLineUpPlayer> getLineUpList(long idFantasyClub, long idMatchday) {
        EntityManager em = helper.getEntityManager();
        FantasyClubLineUp lineUp = null;
        try {
            StringBuilder query = new StringBuilder("SELECT f FROM FantasyClubLineUp f WHERE f.idFantasyClub = ")
                    .append(idFantasyClub).append(" AND f.idMatchday = ").append(idMatchday);
            lineUp = (FantasyClubLineUp) em.createQuery(query.toString()).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
        List<FantasyClubLineUpPlayer> lineUpPlayer = em.createNamedQuery("FantasyClubLineUpPlayer.findByLineUpId").setParameter("idLineUp", lineUp.getId()).getResultList();
        //List<LineUpPlayer> lineUpPlayer = toLineUpPlayer(fantasyClubLineUpPlayer);
        if (lineUpPlayer == null || lineUpPlayer.isEmpty()) {
            throw new DataNotFoundException("There is no line up players for this search!");
        }

        return lineUpPlayer;
    }

    public long getCount(List<FantasyClubLineUpPlayer> lineUp) {
        return lineUp.size();
    }

    public boolean areEqual(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) { // drugi list treba da bude lineUpHistory
        return lineUp == lineUpHistory;
    }

    public List<LineUpPlayer> toLineUpPlayer(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayer) {
        List<LineUpPlayer> lineUpPlayer = null;
        for (FantasyClubLineUpPlayer fantasyPlayer : fantasyClubLineUpPlayer) {
            LineUpPlayer player = new LineUpPlayer(fantasyPlayer.getId(), fantasyPlayer.getIsCaptain(), fantasyPlayer.getCreateDate(), fantasyPlayer.getIdPlayerSlot().getId(), fantasyPlayer.getIdLeaguePlayer().getId(), fantasyPlayer.getIdLineUp().getId());
            lineUpPlayer.add(player);
        }
        return lineUpPlayer;
    }
}
