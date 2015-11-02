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
public class PlatformPOJO {
    
    private String platform;
    private long count;
    
//    private long android;
//    private long ios;
//    private long web;
//    private long total;

    public PlatformPOJO(String platform, long count) {
        this.platform = platform;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    
    
}
