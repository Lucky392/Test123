package rs.htec.cms.cms_bulima.filter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class Filter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.getHeaders().add("Access-Control-Max-Age", "1209600");

        requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        requestContext.getHeaders().add("Access-Control-Allow-Headers", "Authorization");

        if (requestContext.getMethod().equals("OPTIONS")) {
            System.out.println("OPTIONS is requested!!!!!!!!!!!!!");
        }
        if (requestContext.getMethod().equals("GET")) {
            System.out.println("GET is requested!!!!!!!!!!!!!");
        }
        if (requestContext.getMethod().equals("POST")) {
            System.out.println("POST is requested!!!!!!!!!!!!!");
        }
        if (requestContext.getMethod().equals("DELETE")) {
            System.out.println("DELETE is requested!!!!!!!!!!!!!");
        }
        if (requestContext.getMethod().equals("PUT")) {
            System.out.println("PUT is requested!!!!!!!!!!!!!");

            System.out.println(requestContext.getAcceptableMediaTypes().toArray());
            System.out.println("***");
            System.out.println("***");
//            System.out.println(requestContext.getMediaType().getParameters().entrySet());
        }
    }

//    private Map vratiPodatkeRequsta(ContainerRequestContext r) {
//        Map<String, String> map = new HashMap<String, String>();
// 
//	MultivaluedMap<String, String> headers = r.getHeaders();
//	
////        for ( header : headers) {
////            
////        }
//    }

}
