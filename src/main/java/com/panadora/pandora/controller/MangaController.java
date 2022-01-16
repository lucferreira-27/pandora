package com.panadora.pandora.controller;

import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.controller.form.MangaForm;
import com.panadora.pandora.service.title.MangaTitleManagerImpl;
import com.panadora.pandora.service.title.MangaTitleManagerImpl;
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
public class MangaController {
    @Autowired
    private MangaTitleManagerImpl mangaManager;
    @GetMapping("/mangas")
    public List<MangaDto> listMangas(){
        List<MangaDto> listMangasDtos = mangaManager.listTitles();
        return listMangasDtos;
    }

    @GetMapping("/mangas/{id}")
    public ResponseEntity getManga(@PathVariable String id){
        MangaDto mangaDto = mangaManager.getTitle(id);
        return ResponseEntity.ok().body(mangaDto);
    }

    @DeleteMapping("/mangas/{id}")
    public ResponseEntity deleteManga(@PathVariable String id){
        mangaManager.deleteTitle(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping(value ="/mangas",consumes="application/json")
    public ResponseEntity addManga(@RequestBody MangaForm mangaForm){
        MangaDto mangaDto = mangaManager.addTitle(mangaForm);
        if(mangaDto != null){
            return  ResponseEntity.created(URI.create("/mangas/" + mangaDto.getId())).build();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
    @GetMapping(value = "/mangas/{id}/{image}")
    public ResponseEntity getMangaImage(HttpServletResponse response, @PathVariable String id, @PathVariable String image) throws IOException {
        InputStream InputStream = mangaManager.getTitleImage(image,id);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(InputStream, response.getOutputStream());
        return ResponseEntity.ok().build();
    }
}
