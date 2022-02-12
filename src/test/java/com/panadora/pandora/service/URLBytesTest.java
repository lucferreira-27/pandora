package com.panadora.pandora.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class URLBytesTest extends URLBytes {
    @Spy
    private URLBytes URLBytes;

    @Mock
    private URL urlObject;

    @Mock
    private HttpURLConnection httpURLConnection;


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
        when(urlObject.openConnection()).thenReturn(httpURLConnection);
        when(httpURLConnection.getInputStream()).thenReturn(inputStream);

        MockedStatic<IOUtils> mb = Mockito.mockStatic(IOUtils.class);
        mb.when(() -> IOUtils.toByteArray(inputStream))
                .thenAnswer(i -> expectBytes);

        //then
        byte[] resultBytes = URLBytes.convertURLtoBytes(urlObject);

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
        when(urlObject.openConnection()).thenReturn(httpURLConnection);
        when(httpURLConnection.getInputStream()).thenThrow(IOException.class);

        //assert
        assertThrows(IOException.class, () -> URLBytes.convertURLtoBytes(urlObject));


    }

    @Test
    public void shouldConvertURLtoBytesWithProperties() throws IOException {


        //given
        byte[] expectBytes = new byte[1024];
        InputStream inputStream = new ByteArrayInputStream(expectBytes);
        Map<String, String> mockProperties = new HashMap<>();
        mockProperties.put("User-Agent", "Mozilla/4.0");

        //when
        when(urlObject.openConnection()).thenReturn(httpURLConnection);
        when(httpURLConnection.getInputStream()).thenReturn(inputStream);

        MockedStatic<IOUtils> mb = Mockito.mockStatic(IOUtils.class);
        mb.when(() -> IOUtils.toByteArray(inputStream))
                .thenAnswer(i -> expectBytes);

        //then
        byte[] resultBytes = URLBytes.convertURLtoBytes(urlObject, mockProperties);

        //assert
        assertThat(resultBytes).isEqualTo(expectBytes);
        mb.verify(() -> IOUtils.toByteArray(inputStream), times(1));

        mb.close();
    }

    @Test
    public void shouldThrowIllegalArgumentException_When_ConvertURLtoBytesWithProperties() throws IOException {

        //given
        Map<String, String> emptyProperties = new HashMap<>();

        //assert
        assertThrows(IllegalArgumentException.class, () -> URLBytes.convertURLtoBytes(urlObject, emptyProperties));
    }


}
