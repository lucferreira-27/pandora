package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.AddImageException;
import com.panadora.pandora.service.exceptions.ImageBytesException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class TitleImageDownloader {

    @Autowired
    private AddImageCover addImageCover;
    @Autowired
    private AddImageThumbnail addImageThumbnail;

    public void downloadAndAddImagesBytes(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm) throws AddImageException {
        addImageCover(titleDetails,titleDetailsForm);
        addImageThumbnail(titleDetails,titleDetailsForm);
    }

    private void addImageCover(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm) throws AddImageException {
        addImageCover.downloadAndAddImagesBytes(titleDetails,titleDetailsForm);
    }
    private void addImageThumbnail(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm) throws AddImageException {
        addImageThumbnail.downloadAndAddImagesBytes(titleDetails,titleDetailsForm);
    }
}
