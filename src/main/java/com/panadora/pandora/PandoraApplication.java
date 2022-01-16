package com.panadora.pandora;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class PandoraApplication {

    public static void main(String[] args) {
          SpringApplication.run(PandoraApplication.class, args);
    }


}
