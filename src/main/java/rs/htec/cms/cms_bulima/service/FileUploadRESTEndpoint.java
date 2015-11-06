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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import rs.htec.cms.cms_bulima.constants.ImageLocationConstants;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Util;

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
     *
     * @param token is a header parameter for checking permission
     * @param uploadedInputStream
     * @param where 1 - player, 2 - club, 3 - slider, 4 - shop images, 5 - shop
     * images special
     * @param fileDetail
     * @return Response 200 OK status with uploaded file location URL which has
     * to be put in database
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @HeaderParam("authorization") String token,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @QueryParam("imageLocationFolder") int where) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        String uploadedFileLocation;
        String databaseString;
        switch (where) {
            case ImageLocationConstants.PLAYER:
                uploadedFileLocation = "webapps/bulima-data/images/bulima-player-card/photos/";
                databaseString = Util.getInstance().getAssetsUrl() + "images/bulima-player-card/photos/";
                break;
            case ImageLocationConstants.CLUB:
                uploadedFileLocation = "webapps/bulima-data/images/favoriteclublogos/";
                databaseString = Util.getInstance().getAssetsUrl() + "images/favoriteclublogos/";
                break;
            case ImageLocationConstants.HOME_SLIDER:
                uploadedFileLocation = "webapps/bulima-data/home_slider/";
                databaseString = Util.getInstance().getAssetsUrl() + "home_slider/";
                break;
            case ImageLocationConstants.SHOP_IMAGES:
                uploadedFileLocation = "webapps/bulima-data/images/shop/";
                databaseString = "images/shop/";
                break;
            case ImageLocationConstants.SHOP_IMAGES_SPECIALS:
                uploadedFileLocation = "webapps/bulima-data/images/shop/specials/";
                databaseString = "images/shop/specials/";
                break;
            default:
                throw new InputValidationException("Enter valid number for image location folder!");
        }
        uploadedFileLocation += fileDetail.getFileName();
        databaseString += fileDetail.getFileName();
        File objFile = new File(uploadedFileLocation);
        if (objFile.exists()) {
            objFile.delete();
        }
        saveToFile(uploadedInputStream, uploadedFileLocation);
        JSONObject json = new JSONObject();
        json.put("URL", databaseString);
        return Response.status(200).entity(json).build();
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
            throw new InputValidationException(e.getMessage());
        }

    }

}
