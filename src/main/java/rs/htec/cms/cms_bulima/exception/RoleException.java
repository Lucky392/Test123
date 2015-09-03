/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.exception;

/**
 *
 * @author stefan
 */
public class RoleException extends Exception {

    public RoleException(String message) {
        super(message);
    }

    public RoleException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
