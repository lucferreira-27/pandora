package com.panadora.pandora.controller;

import com.panadora.pandora.controller.dtos.AnimeDto;
import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.service.title.AnimeTitleManagerImpl;
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
public class AnimeController {

    @Autowired
    private AnimeTitleManagerImpl animeManager;
    @GetMapping("/animes")
    public List<AnimeDto> listAnimes(){
        List<AnimeDto> listAnimesDtos = animeManager.listTitles();
        return listAnimesDtos;
    }

    @GetMapping("/animes/{id}")
    public ResponseEntity getAnime(@PathVariable String id){
        AnimeDto animeDto = animeManager.getTitle(id);
        return ResponseEntity.ok().body(animeDto);
    }

    @DeleteMapping("/animes/{id}")
    public ResponseEntity deleteAnime(@PathVariable String id){
        animeManager.deleteTitle(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping(value ="/animes",consumes="application/json")
    public ResponseEntity addAnime(@RequestBody AnimeForm animeForm){
        AnimeDto animeDto = animeManager.addTitle(animeForm);
        if(animeDto != null){
            return  ResponseEntity.created(URI.create("/animes/" + animeDto.getId())).build();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
    @GetMapping(value = "/animes/{id}/{image}")
    public ResponseEntity getAnimeImage(HttpServletResponse response, @PathVariable String id, @PathVariable String image) throws IOException {
        InputStream InputStream = animeManager.getTitleImage(image,id);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(InputStream, response.getOutputStream());
        return ResponseEntity.ok().build();
    }
}
