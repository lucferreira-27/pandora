package com.panadora.pandora.controller;

import com.panadora.pandora.controller.dtos.ChapterDto;
import com.panadora.pandora.controller.dtos.ErrorMessage;
import com.panadora.pandora.controller.form.ChapterForm;
import com.panadora.pandora.service.exceptions.NotFoundException;
import com.panadora.pandora.service.item.ChapterItemManagerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ChapterController {
    private final ChapterItemManagerImpl chapterManager;

    public ChapterController(ChapterItemManagerImpl chapterManager) {
        this.chapterManager = chapterManager;
    }

    @GetMapping("/chapters")
    public ResponseEntity listChapters(){
        List<ChapterDto> listChaptersDto = chapterManager.listItems();
        return ResponseEntity.ok().body(listChaptersDto);
    }
    @GetMapping("/chapters/{id}")
    public ResponseEntity getItem(@PathVariable String id){
        ChapterDto chapterDto = chapterManager.getItem(id);
        return ResponseEntity.ok().body(chapterDto);

    }
    @DeleteMapping("/chapters/{id}")
    public ResponseEntity deleteItem(@PathVariable String id){
        chapterManager.deleteItem(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping(value ="/chapters",consumes="application/json")
    public ResponseEntity addTitle(@RequestBody ChapterForm chapterForm){

        ChapterDto chapterDto = chapterManager.addItem(chapterForm);
        return  ResponseEntity.created(URI.create("/chapters/" + chapterDto.getId())).build();

    }

}
