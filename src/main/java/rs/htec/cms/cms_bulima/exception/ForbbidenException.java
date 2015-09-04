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
public class ForbbidenException extends CMSException {

    public ForbbidenException(String message) {
        super(message, 403, Response.Status.FORBIDDEN);
    }

}
