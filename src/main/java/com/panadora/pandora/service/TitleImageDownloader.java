package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.ImageBytesException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class TitleImageDownloader {

    public void downloadAndAddImagesBytes(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm) {
        List<AddImage> addImages = Arrays.asList(new AddImageCover(), new AddImageThumbnail());
        for(AddImage addImage : addImages){
            addImage.downloadAndAddImagesBytes(titleDetails,titleDetailsForm);
        }

    }


}
