package com.panadora.pandora.service;

import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.service.exceptions.AddImageException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AddImageCoverTest {

    @Mock
    private URLBytes URLBytes;

    @Spy
    @InjectMocks
    private AddImageCover addImageCover;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldDownloadAndAddImagesBytes() throws AddImageException {

        //given
        byte [] expectBytes = new byte[1024];
        TitleDetails titleDetails = new TitleDetails();
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        //when
        doReturn(expectBytes)
                .when(addImageCover).download(any(TitleDetailsForm.class));
        doNothing()
                .when(addImageCover)
                .addImageBytesInDetails(any(TitleDetails.class),eq(expectBytes));
        //then
        addImageCover.downloadAndAddImagesBytes(titleDetails,titleDetailsForm);

        //assert
        verify(addImageCover, times(1)).download(any(TitleDetailsForm.class));
        verify(addImageCover, times(1)).addImageBytesInDetails(any(TitleDetails.class),eq(expectBytes));

    }

    @Test
    void shouldDownloadImageBytes() throws IOException {
        //given
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        String image =  "https://testFakeImageUrl";
        titleDetailsForm.setImageCoverUrl(image);
        byte [] expectBytes = new byte[1024];

        //when
        doReturn(expectBytes).when(URLBytes).convertURLtoBytes(any(URL.class));

        //then
        byte [] resultBytes = addImageCover.download(titleDetailsForm);
        //assert
        assertThat(resultBytes).isEqualTo(expectBytes);
        verify(URLBytes,times(1)).convertURLtoBytes(any(URL.class));


    }

    @Test
    void shouldThrowTitleBadRequestException_When_DownloadImageBytes() throws IOException {
        //given
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        String image =  "https://testFakeImageUrl";
        titleDetailsForm.setImageCoverUrl(image);

        //when
        doThrow(IOException.class).when(URLBytes).convertURLtoBytes(any(URL.class));


        //assert
        assertThrows(TitleBadRequestException.class, () ->   addImageCover.download(titleDetailsForm));
        verify(URLBytes,times(1)).convertURLtoBytes(any(URL.class));

    }
    @Test
    public void shouldDownloadImage() throws IOException {


        //given
        String imageUrl =  "https://testFakeImageUrl";
        byte [] expectBytes = new byte[1024];
        //when
        doReturn(expectBytes).when(URLBytes).convertURLtoBytes(any(URL.class));
        //then
        byte [] resultBytes = addImageCover.downloadImageIfExist(imageUrl);

        //assert
        assertThat(resultBytes).isEqualTo(expectBytes);
        verify(URLBytes,times(1)).convertURLtoBytes(any(URL.class));

    }
    @Test
    public void shouldThrowIllegalArgumentException_When_DownloadImage() throws IOException {
        //given
        String imageUrl =  "";

        //assert
        assertThrows(IllegalArgumentException.class, () ->   addImageCover.downloadImageIfExist(imageUrl));
        verify(URLBytes,never()).convertURLtoBytes(any(URL.class));

    }
    @Test
    void shouldAddImageBytesInDetails() throws AddImageException {
        //given
        TitleDetails titleDetails = mock(TitleDetails.class);
        byte[] imageBytes = new byte[1024];

        //then
        addImageCover.addImageBytesInDetails(titleDetails,imageBytes);

        //assert
        verify(titleDetails,times(1)).setImageCover(imageBytes);

    }
    @Test
    void shouldThrowAddImageBytesException_When_BytesArrayEmpty_AddImageBytesInDetails() throws AddImageException {
        //given
        String expectExceptionMsg =  "Image bytes can't be empty";
        TitleDetails titleDetails = mock(TitleDetails.class);
        byte[] imageBytes = new byte[0];

        //assert
        AddImageException exception = assertThrows(AddImageException.class,() ->  addImageCover.addImageBytesInDetails(titleDetails,imageBytes));
        assertThat(exception.getMessage()).isEqualTo(expectExceptionMsg);
        verify(titleDetails,never()).setImageCover(imageBytes);

    }
    @Test
    void shouldThrowAddImageBytesException_When_BytesArrayNull_AddImageBytesInDetails() throws AddImageException {
        //given
        String expectExceptionMsg =  "Image bytes can't be null";
        TitleDetails titleDetails = mock(TitleDetails.class);
        byte[] imageBytes = null;

        //assert
        AddImageException exception = assertThrows(AddImageException.class,() ->  addImageCover.addImageBytesInDetails(titleDetails,imageBytes));
        assertThat(exception.getMessage()).isEqualTo(expectExceptionMsg);
        verify(titleDetails,never()).setImageThumbnail(imageBytes);

    }


}