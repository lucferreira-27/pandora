package com.panadora.pandora.service;

import com.panadora.pandora.service.exceptions.ImageBytesException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageBytes {
    public byte[] convertURLtoBytes(URL url) throws IOException {
        InputStream inputStream = getInputStreamFromURL(url);
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        return imageBytes;
    }

    private InputStream getInputStreamFromURL(URL url) throws IOException {
        Map<String, String> properties = createMapRequestProperties();
        HttpURLConnection conn = createHttpURLConnection(url, properties);
        InputStream is = getHttpInputStream(conn);
        return is;
    }

    private Map<String, String> createMapRequestProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("User-Agent", "Mozilla/4.0");
        return properties;
    }

    private HttpURLConnection createHttpURLConnection(URL url, Map<String, String> properties) throws IOException {
        HttpURLConnection conn = openConnection(url);
        addAllRequestProperties(conn, properties);
        return conn;
    }

    protected HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    private InputStream getHttpInputStream(HttpURLConnection conn) throws IOException {
        try (InputStream is = conn.getInputStream()) {
            return is;
        } catch (IOException e) {
            throw e;
        }
    }

    private void addAllRequestProperties(HttpURLConnection conn, Map<String, String> properties) {
        properties.forEach(conn::addRequestProperty);
    }


}
