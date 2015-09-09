/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.exception;

import javax.ws.rs.core.Response;

/**
 *
 * @author stefan
 */
public class InputValidationException extends CMSException{
    public InputValidationException (String message){
        super(message, 400, Response.Status.BAD_REQUEST);
    }
}
