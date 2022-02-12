package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.AddImageException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;

public  abstract class AddImage {

    @Autowired
    private URLBytes URLBytes;

    public void downloadAndAddImagesBytes(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm) throws AddImageException {

        byte[] imageBytes = download(titleDetailsForm);
        addImageBytesInDetails(titleDetails, imageBytes);
    }


    protected byte[] downloadImageIfExist(String imageUrl) {
        if (checkIfImageExist(imageUrl)) {
            byte[] imageBytes = downloadImage(imageUrl);
            return imageBytes;
        }
        throw new IllegalArgumentException("Image doesn't exist");
    }

    private boolean checkIfImageExist(String image) {
        boolean existImage = image != null && !image.isEmpty();
        return existImage;
    }

    private byte[] downloadImage(String imageUrl) {
        byte[] imageBytes = getBytesFromURL(imageUrl);
        return imageBytes;
    }

    private byte[] getBytesFromURL(String url) {
        try {
            byte[] imageThumbnailBytes = URLBytes.convertURLtoBytes(new URL(url));
            return imageThumbnailBytes;
        } catch (IOException  e) {
            e.printStackTrace();
            throw new TitleBadRequestException();
        }

    }

    protected  abstract byte[] download(TitleDetailsForm titleDetails);
    protected abstract void addImageBytesInDetails(TitleDetails titleDetails, byte[] imageBytes) throws AddImageException;


}
