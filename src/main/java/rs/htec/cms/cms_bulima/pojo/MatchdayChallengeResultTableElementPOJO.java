/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeResultTableElement;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class MatchdayChallengeResultTableElementPOJO {
    
    private Long id;
    private int elementPosition;
    private String elementName;
    private String elementType;
    private String elementTable;
    private String elementField;
    private Long idMatchdayChallenge;
    private String urlToMatchdayChallenge;

    public MatchdayChallengeResultTableElementPOJO(MatchdayChallengeResultTableElement element) {
        this.id = element.getId();
        this.elementPosition = element.getElementPosition();
        this.elementName = element.getElementName();
        this.elementType = element.getElementType();
        this.elementTable = element.getElementTable();
        this.elementField = element.getElementField();
        if (element.getIdMatchdayChallenge() != null) {
            this.idMatchdayChallenge = element.getIdMatchdayChallenge().getId();
            this.urlToMatchdayChallenge = Util.getInstance().getUrl() + "rest/matchday_challenge/" + element.getIdMatchdayChallenge().getId();
        }
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getElementPosition() {
        return elementPosition;
    }

    public void setElementPosition(int elementPosition) {
        this.elementPosition = elementPosition;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementTable() {
        return elementTable;
    }

    public void setElementTable(String elementTable) {
        this.elementTable = elementTable;
    }

    public String getElementField() {
        return elementField;
    }

    public void setElementField(String elementField) {
        this.elementField = elementField;
    }

    public Long getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(Long idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
    }

    public String getUrlToMatchdayChallenge() {
        return urlToMatchdayChallenge;
    }

    public void setUrlToMatchdayChallenge(String urlToMatchdayChallenge) {
        this.urlToMatchdayChallenge = urlToMatchdayChallenge;
    }
    
        public static List<MatchdayChallengeResultTableElementPOJO> toMatchdayChallengeResultTableElementPOJOList(List<MatchdayChallengeResultTableElement> list) {
        MatchdayChallengeResultTableElementPOJO pojo;
        List<MatchdayChallengeResultTableElementPOJO> pojos = new ArrayList<>();
        for (MatchdayChallengeResultTableElement element : list) {
            pojo = new MatchdayChallengeResultTableElementPOJO(element);
            pojos.add(pojo);
        }
        return pojos;
    }
}
