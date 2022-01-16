package com.panadora.pandora.controller;

import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.service.title.TitleManager;
import com.panadora.pandora.service.title.TitleManagerImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

@RestController
public class TitleController {

    @Autowired
    private TitleManagerImpl titleManager;

    @GetMapping("/titles")
    public List<TitleDto> listTitles(){
        List<TitleDto> listTitlesDtos = titleManager.listTitles();
        return listTitlesDtos;
    }

    @GetMapping("/titles/{id}")
    public ResponseEntity getTitle(@PathVariable String id){
        TitleItemsDto titleItemsDto = (TitleItemsDto) titleManager.getTitle(id);
        return ResponseEntity.ok().body(titleItemsDto);
    }

    @DeleteMapping("/titles/{id}")
    public ResponseEntity deleteTitle(@PathVariable String id){
         titleManager.deleteTitle(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping(value ="/titles",consumes="application/json")
    public ResponseEntity addTitle(@RequestBody TitleForm titleForm){
        TitleDto titleDto = titleManager.addTitle(titleForm);
        if(titleDto != null){
            return  ResponseEntity.created(URI.create("/titles/" + titleDto.getId())).build();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
    @GetMapping(value = "/titles/{id}/{image}")
    public ResponseEntity getTitleImage(HttpServletResponse response, @PathVariable String id, @PathVariable String image) throws IOException {
        InputStream InputStream = titleManager.getTitleImage(image,id);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(InputStream, response.getOutputStream());
        return ResponseEntity.ok().build();
    }

}
