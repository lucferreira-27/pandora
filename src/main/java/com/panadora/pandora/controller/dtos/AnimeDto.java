package com.panadora.pandora.controller.dtos;

import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Title;

public class AnimeDto extends TitleDto{
    AnimeDto(Anime anime){
        super(anime);
    }
    public AnimeDto(){

    }

    public static TitleDto toDto(Anime anime) {
        AnimeDto animeDto = new AnimeDto();
        animeDto.setId(anime.getId());
        animeDto.setPath(anime.getPath());
        animeDto.setTitleDetails(anime.getTitleDetails());
        return animeDto;
    }
}
