/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
public class Dashboard {

    private List<DashboardElement> elements;
    private String[] time;
    private String[] platforms = {null, "web", "android", "ios"};
    
    public Dashboard() {
        time = new String[]{"today", "yesterday"};
        elements = new ArrayList<>();
    }

    public List<DashboardElement> getElements() {
        return elements;
    }

    public void setElements(List<DashboardElement> elements) {
        this.elements = elements;
    }

    @XmlTransient
    @JsonIgnore
    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public void instantiateDashboard(String platform) {
        DashboardElement element = new DashboardElement(platform);
        element.instantiateElement(time);
        elements.add(element);
    }

    public void instantiateFullDashboard() {
        for (String platform : platforms) {
            DashboardElement element = new DashboardElement(platform);
            element.instantiateElement(time);
            elements.add(element);
        }
    }
}
