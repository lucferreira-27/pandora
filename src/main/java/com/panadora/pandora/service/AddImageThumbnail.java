package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;

public class AddImageThumbnail extends AddImage {


    @Override
    protected byte[] download(TitleDetailsForm titleDetails) {
        byte[] imageBytes = downloadImageIfExist(titleDetails.getImageThumbnailUrl());
        return imageBytes;
    }

    @Override
    public void addImageBytesInDetails(TitleDetails titleDetails, byte[] imageBytes) {
        if (imageBytes.length > 0) {
            titleDetails.setImageThumbnail(imageBytes);
        }
    }
}
