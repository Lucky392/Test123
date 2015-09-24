/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import javax.ws.rs.Path;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/packageContent")
public class PremiumPackageContentRESTEndpoint {
    
    RestHelperClass helper;
    Validator validator;

    public PremiumPackageContentRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }
}
