package com.panadora.pandora.controller;
import com.panadora.pandora.controller.dtos.EpisodeDto;
import com.panadora.pandora.controller.form.EpisodeForm;
import com.panadora.pandora.service.item.EpisodeItemManagerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class EpisodeController {

    private final EpisodeItemManagerImpl episodeManager;

    public EpisodeController(EpisodeItemManagerImpl episodeManager) {
        this.episodeManager = episodeManager;
    }

    @GetMapping("/episodes")
    public ResponseEntity listEpisodes(){
        List<EpisodeDto> listEpisodesDto = episodeManager.listItems();
        return ResponseEntity.ok().body(listEpisodesDto);
    }
    @GetMapping("/episodes/{id}")
    public ResponseEntity getItem(@PathVariable String id){
        EpisodeDto episodeDto = episodeManager.getItem(id);
        return ResponseEntity.ok().body(episodeDto);

    }
    @DeleteMapping("/episodes/{id}")
    public ResponseEntity deleteItem(@PathVariable String id){
        episodeManager.deleteItem(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping(value ="/episodes",consumes="application/json")
    public ResponseEntity addTitle(@RequestBody EpisodeForm episodeForm){
        EpisodeDto episodeDto = episodeManager.addItem(episodeForm);
        return  ResponseEntity.created(URI.create("/episodes/" + episodeDto.getId())).build();

    }
}
