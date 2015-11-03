/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.List;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 *
 * @author stefan
 */
@JsonPropertyOrder({ "count","data" })
public class GetObject {
    private long count;
    private Object data;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object o) {
        this.data = o;
    }
    
    
}
