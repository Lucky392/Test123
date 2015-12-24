/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.Player;

/**
 *
 * @author stefan
 */
public class PlayerIdAndNamePOJO {

    private Long id;
    private String firstName;
    private String lastName;

    public PlayerIdAndNamePOJO(Player player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public static List<PlayerIdAndNamePOJO> toPlayerIdAndNamePOJOList(List<Player> players){
        PlayerIdAndNamePOJO pojo;
        List<PlayerIdAndNamePOJO> pojos = new ArrayList<>();
        for (Player player : players) {
            pojo = new PlayerIdAndNamePOJO(player);
            pojos.add(pojo);
        }
        return pojos;
    }

}
