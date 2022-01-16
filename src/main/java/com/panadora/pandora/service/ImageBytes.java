package com.panadora.pandora.service;

import com.panadora.pandora.service.exceptions.ImageBytesException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageBytes {
    public static byte[] convertURLtoBytes(URL url) throws ImageBytesException, IOException {
        InputStream is = null;
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
            is = httpcon.getInputStream();
            byte[] imageBytes = IOUtils.toByteArray(is);
            return imageBytes;
        }catch(Exception e){
            throw new ImageBytesException(e);
        }
        finally {
            if (is != null) { is.close(); }
        }

    }
}
