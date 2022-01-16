package com.panadora.pandora.service;

import com.panadora.pandora.model.entities.collection.title.ImageType;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.ImageBytesException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class TitleImageLoader {
    public InputStream load(String image, TitleDetails titleDetails){
        if(image.equals(ImageType.THUMBNAIL.getValue())){
           return loadThumbnail(titleDetails);
        }
        if(image.equals(ImageType.COVER.getValue())){
            return loadCover(titleDetails);
        }
        throw new IllegalArgumentException();
    }
    private InputStream loadThumbnail(TitleDetails titleDetails){
        byte[] imageBytes = titleDetails.getImageThumbnail();
        return new ByteArrayInputStream(imageBytes);
    }
    private InputStream loadCover(TitleDetails titleDetails){
        byte[] imageBytes = titleDetails.getImageCover();
        return new ByteArrayInputStream(imageBytes);
    }


}
