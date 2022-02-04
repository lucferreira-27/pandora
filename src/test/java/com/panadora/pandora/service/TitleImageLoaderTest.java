package com.panadora.pandora.service;

import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.CollectionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TitleImageLoaderTest {

    private TitleImageLoader titleImageLoader;

    @BeforeEach
    public void setup(){
        titleImageLoader = new TitleImageLoader();
    }

    @Test
    void shouldReturnInputStream_WhenLoadImageCover() throws IOException {

        //given
        String imageType = "cover";
        TitleDetails  titleDetails = new TitleDetails();
        byte[] bytes = new byte[1024];

        titleDetails.setImageCover(bytes);
        //then
        InputStream inputStream = titleImageLoader.load(imageType,titleDetails);

        //assert
        assertNotNull(inputStream);
        assertThat(inputStream.available()).isEqualTo(bytes.length);

    }
    @Test
    void shouldReturnInputStream_WhenLoadImageThumbnail() throws IOException {

        //given
        String imageType = "thumbnail";
        TitleDetails  titleDetails = new TitleDetails();
        byte[] bytes = new byte[1024];
        titleDetails.setImageThumbnail(bytes);
        //then
        InputStream inputStream = titleImageLoader.load(imageType,titleDetails);

        //assert
        assertNotNull(inputStream);
        assertThat(inputStream.available()).isEqualTo(bytes.length);

    }
    @Test
    void shouldThrowIllegalArgumentException_WhenLoadInvalidImage(){

        //given
        String imageType = "invalidImageType";
        TitleDetails  titleDetails = new TitleDetails();
        byte[] bytes = new byte[1024];
        titleDetails.setImageThumbnail(bytes);
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> titleImageLoader.load(imageType,titleDetails)
        );

    }
    @Test
    void shouldThrowIllegalArgumentException_WhenLoadNullImage(){

        //given
        String imageType = null;
        TitleDetails  titleDetails = new TitleDetails();
        byte[] bytes = new byte[1024];
        titleDetails.setImageThumbnail(bytes);
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> titleImageLoader.load(imageType,titleDetails)
        );

    }
}