package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.ImageBytesException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;

import java.io.IOException;
import java.net.URL;

public  abstract class AddImage {


    public void downloadAndAddImagesBytes(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm) {
        byte[] imageBytes = download(titleDetailsForm);
        addImageBytesInDetails(titleDetails, imageBytes);
    }


    protected byte[] downloadImageIfExist(String imageUrl) {
        if (checkIfImageExist(imageUrl)) {
            byte[] imageBytes = download(imageUrl);
            return imageBytes;
        }
        throw new IllegalArgumentException();
    }

    private boolean checkIfImageExist(String image) {
        boolean existImage = image != null && !image.isEmpty();
        return existImage;
    }

    private byte[] download(String imageUrl) {
        byte[] imageBytes = getBytesFromURL(imageUrl);
        return imageBytes;
    }

    private byte[] getBytesFromURL(String url) {
        try {
            byte[] imageThumbnailBytes = ImageBytes.convertURLtoBytes(new URL(url));
            return imageThumbnailBytes;
        } catch (IOException | ImageBytesException e) {
            e.printStackTrace();
            throw new TitleBadRequestException();
        }

    }

    protected  abstract byte[] download(TitleDetailsForm titleDetails);
    protected abstract void addImageBytesInDetails(TitleDetails titleDetails, byte[] imageBytes);


}
