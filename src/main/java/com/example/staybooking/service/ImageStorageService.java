package com.example.staybooking.service;

import com.example.staybooking.exception.GCSUploadException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageStorageService {
    @Value("${gcs.bucket}")
    private String bucketName;
    private final String credentialPath="credentials.json";
    /*
    * input: an image
    * output: url to identify the corresponding image
    * */
    public String save(MultipartFile file) throws GCSUploadException {
        Credentials credentials=null;
        try{
            /*
            * class GoogleCredentials
            * Base type for credentials for authorizing calls to Google APIs using OAuth2.
            * */
            credentials= GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(this.credentialPath));
        }catch(IOException e){
            throw new GCSUploadException("Failed to load GCP credentials");
        }
        Storage storage= StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        String fileName= UUID.randomUUID().toString();
        BlobInfo blobInfo=null;
        try {
            blobInfo=storage.createFrom(
                    BlobInfo
                            .newBuilder(this.bucketName,fileName)
                            .setContentType("image/jpeg")
                            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(),Acl.Role.READER))))
                            .build()
                    ,file.getInputStream());
        } catch(IOException e){
            throw new GCSUploadException("Failed upload image to GCS");
        }
        return blobInfo.getMediaLink();
    }
}
