/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.List;

/**
 *
 * @author stefan
 */
public class Validator {

    public boolean checkLenght(String text, int length, boolean canBeNull) {
        return text==null?canBeNull:(text.length() < length);
    }

    public boolean checkLenghtForList(List<String> list, int lenght) {
        for (String element : list) {
            if (element != null && element.length() > lenght) {
                return false;
            }
        }
        return true;
    }
}
