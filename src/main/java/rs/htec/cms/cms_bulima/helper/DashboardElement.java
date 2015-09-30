/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stefan
 */
public class DashboardElement {

    private String platform;
    private List<DashboardColumn> dashboardColumns;

    public DashboardElement(String platform) {
        this.platform = platform;
        dashboardColumns = new ArrayList<>();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<DashboardColumn> getDashboardColumns() {
        return dashboardColumns;
    }

    public void setDashboardColumns(List<DashboardColumn> dashboardColumns) {
        this.dashboardColumns = dashboardColumns;
    }

    public void instantiateElement(String[] time) {
        for (String t : time) {
            DashboardColumn column = new DashboardColumn(platform, t);
            column.instantiateColumn();
            dashboardColumns.add(column);
        }
    }

}
