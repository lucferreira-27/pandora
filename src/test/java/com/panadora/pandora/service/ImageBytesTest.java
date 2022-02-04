package com.panadora.pandora.service;

import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.model.entities.collection.title.Manga;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ImageBytesTest extends ImageBytes {
    @Spy
    private ImageBytes imageBytes;

    @Mock
    private URL url;

    @Mock
    private HttpURLConnection httpURLConnection;

    @Spy
    private InputStream inputStream;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertURLtoBytes() throws IOException {

        //given
        byte[] expectBytes = new byte[1024];
        InputStream inputStream = new ByteArrayInputStream(expectBytes);

        //when
        when(url.openConnection()).thenReturn(httpURLConnection);
        when(httpURLConnection.getInputStream()).thenReturn(inputStream);

        MockedStatic<IOUtils> mb = Mockito.mockStatic(IOUtils.class);
        mb.when(() -> IOUtils.toByteArray(inputStream))
                .thenAnswer(i -> expectBytes);

        //then
        byte[] resultBytes = imageBytes.convertURLtoBytes(url);

        //assert
        assertThat(resultBytes).isEqualTo(expectBytes);
        mb.verify(
                () -> IOUtils.toByteArray(inputStream), times(1)
        );

        mb.close();
    }

    @Test
    public void shouldThrowIOException_When_ConvertURLtoBytes() throws IOException {


        //when
        when(url.openConnection()).thenReturn(httpURLConnection);
        when(httpURLConnection.getInputStream()).thenThrow(IOException.class);

        //assert
        assertThrows(IOException.class, () ->   imageBytes.convertURLtoBytes(url));


    }

}
