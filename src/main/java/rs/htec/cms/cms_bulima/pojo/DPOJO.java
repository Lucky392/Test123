/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stefan
 */
public class DPOJO {
    
    private String platform;
    private long a1;
    private long a2;
    private long a3;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getA1() {
        return a1;
    }

    public void setA1(long a1) {
        this.a1 = a1;
    }

    public long getA2() {
        return a2;
    }

    public void setA2(long a2) {
        this.a2 = a2;
    }

    public long getA3() {
        return a3;
    }

    public void setA3(long a3) {
        this.a3 = a3;
    }
    
    public static List toList(List dblist){
        List<DPOJO> list = new ArrayList<>();
        for (Object value : dblist) {
            Object[] li = (Object[]) value;
            DPOJO d = new DPOJO();
            d.platform = (String) li[0];
            d.a1 = (long) li[1];
            d.a2 = (long) li[2];
            d.a3 = (long) li[3];
            list.add(d);
        }
        return list;
    }
    
//    private List<DPOJO> getPlatformList(List objects) {
//        List<PlatformPOJO> list = new ArrayList<>(objects.size());
//        PlatformPOJO pojo = null;
//
//        long total = 0;
//        for (List object : objects) {
//            pojo = new PlatformPOJO((String) object[1], (long) object[0]);
//            list.add(pojo);
//            total += (long) object[0];
//        }
//        pojo = new PlatformPOJO("TOTAL", total);
//        list.add(pojo);
//
//        return list;
//    }
}
