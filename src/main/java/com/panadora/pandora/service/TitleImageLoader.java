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
        if(isImageValid(image)){
            return loadImage(image,titleDetails);
        }
        throw new IllegalArgumentException("Invalid image name");
    }

    private boolean isImageValid(String image){

        if(image == null){
            return false;
        }
        return isImageThumbnail(image) || isImageCover(image);

    }

    private boolean isImageThumbnail(String image){
        return image.equals(ImageType.THUMBNAIL.getValue());
    }

    private boolean isImageCover(String image){
        return image.equals(ImageType.COVER.getValue());
    }

    private InputStream loadImage(String image, TitleDetails titleDetails){
        if(isImageThumbnail(image)){
            return loadImageBytes(titleDetails.getImageThumbnail());
        }
        else{
            return loadImageBytes(titleDetails.getImageCover());
        }

    }

    private InputStream loadImageBytes(byte [] imageBytes){
        return new ByteArrayInputStream(imageBytes);
    }


}
