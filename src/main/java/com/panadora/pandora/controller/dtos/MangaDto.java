package com.panadora.pandora.controller.dtos;

import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.model.entities.collection.title.Title;

public class MangaDto extends TitleDto{
    public MangaDto(Manga manga){
        super(manga);
    }
    public MangaDto(){

    }

    public static MangaDto toDto(Manga manga) {
        MangaDto mangaDto = new MangaDto();
        mangaDto.setId(manga.getId());
        mangaDto.setPath(manga.getPath());
        mangaDto.setTitleDetails(manga.getTitleDetails());
        return mangaDto;
    }
}
