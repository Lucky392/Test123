/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.exception;

import javax.ws.rs.core.Response;

/**
 *
 * @author marko
 */
public class MethodNotAllowedException  extends CMSException {

    public MethodNotAllowedException(String message) {
        super(message, 405, Response.Status.METHOD_NOT_ALLOWED);
    }
}
