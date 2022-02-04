package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.AddImageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TitleImageDownloaderTest {




    @Mock
    private AddImageThumbnail addImageThumbnail;
    @Mock
    private AddImageCover addImageCover;

    @InjectMocks
    private TitleImageDownloader titleImageDownloader;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldAddImageCoverAndThumbnail_WhenDownloadImage() throws AddImageException {
        //given
        TitleDetails titleDetails = new TitleDetails();
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();

        //when
        doNothing().when(addImageCover).downloadAndAddImagesBytes(titleDetails,titleDetailsForm);
        doNothing().when(addImageThumbnail).downloadAndAddImagesBytes(titleDetails,titleDetailsForm);

        //then
        titleImageDownloader.downloadAndAddImagesBytes(titleDetails,titleDetailsForm);

        //verify
        verify(addImageCover,times(1)).downloadAndAddImagesBytes(titleDetails,titleDetailsForm);
        verify(addImageThumbnail,times(1)).downloadAndAddImagesBytes(titleDetails,titleDetailsForm);


    }
}