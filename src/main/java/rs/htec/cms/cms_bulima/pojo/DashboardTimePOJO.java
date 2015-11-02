/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

/**
 *
 * @author stefan
 */
public class DashboardTimePOJO {
    
    private String time;
    private DashboardPOJO data;

    public DashboardTimePOJO(String time, DashboardPOJO data) {
        this.time = time;
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DashboardPOJO getData() {
        return data;
    }

    public void setData(DashboardPOJO data) {
        this.data = data;
    }
    
}
