package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.AddImageException;
import org.springframework.stereotype.Service;

@Service
public class AddImageCover extends AddImage {


    @Override
    protected byte[] download(TitleDetailsForm titleDetails) {
        byte[] imageBytes = downloadImageIfExist(titleDetails.getImageCoverUrl());
        return imageBytes;
    }

    @Override
    public void addImageBytesInDetails(TitleDetails titleDetails, byte[] imageBytes) throws AddImageException {
        if(imageBytes == null)
            throw new AddImageException("Image bytes can't be null");
        if(imageBytes.length == 0)
            throw new AddImageException("Image bytes can't be empty");

        titleDetails.setImageCover(imageBytes);

    }
}
