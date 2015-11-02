/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.ImageLocationConstants;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("/uploadFile")
public class FileUploadRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    /**
     * API method: .../rest/uploadFile for uploading File to server.
     * @param token is a header parameter for checking permission
     * @param uploadedInputStream
     * @param where 1 - player, 2 - club, 3 - slider, 4 - shop images, 5 - shop images special
     * @param fileDetail
     * @return Response 200 OK status with uploaded file location URL which has to be put in database
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @HeaderParam("authorization") String token,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @QueryParam("imageLocationFolder") int where) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token);
        String uploadedFileLocation;
        String databaseString;
        switch(where){
            case ImageLocationConstants.PLAYER:
                uploadedFileLocation = "../bulima-data/images/bulima-player-card/photos/";
                databaseString = "http://assets.bundesligamanager.htec.co.rs/images/bulima-player-card/photos/";
                break;
            case ImageLocationConstants.CLUB:
                uploadedFileLocation = "../bulima-data/images/favoriteclublogos/";
                databaseString = "http://assets.bundesligamanager.htec.co.rs/images/favoriteclublogos/";
                break;
            case ImageLocationConstants.HOME_SLIDER:
                uploadedFileLocation = "../bulima-data/home_slider/";
                databaseString = "http://assets.bundesligamanager.htec.co.rs/home_slider/";
                break;
            case ImageLocationConstants.SHOP_IMAGES:
                uploadedFileLocation = "../bulima-data/images/shop/";
                databaseString = "images/shop/";
                break;
            case ImageLocationConstants.SHOP_IMAGES_SPECIALS:
                uploadedFileLocation = "../bulima-data/images/shop/specials";
                databaseString = "images/shop/specials/";
                break;
            default:
                uploadedFileLocation = "";
                databaseString = "";
        }
        uploadedFileLocation += fileDetail.getFileName();
        databaseString += fileDetail.getFileName();
        if (databaseString.equals("")) {
            throw new InputValidationException("Enter valid number for image location folder!");
        }
        File objFile = new File(uploadedFileLocation);
        if (objFile.exists()) {
            objFile.delete();
        }
        saveToFile(uploadedInputStream, uploadedFileLocation);
        return Response.status(200).entity(uploadedFileLocation).build();
    }

    private void saveToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {
        try {
            OutputStream out;
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new InputValidationException("Bad file");
        }

    }

}
