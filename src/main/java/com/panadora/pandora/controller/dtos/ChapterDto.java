package com.panadora.pandora.controller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.subitem.Page;
import com.panadora.pandora.model.entities.player.ReaderState;

import java.util.List;

public class ChapterDto extends ItemDto{

    private ReaderState readerState;
    private String customPathCover;
    private List<Page> pages;

    public ChapterDto(Chapter chapter) {
        super(chapter);
    }

    public ChapterDto(){

    }

    @Override
    public ItemDto toDto(Item item) {
        Chapter chapter = (Chapter) item;
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setItemDetails(chapter.getItemDetails());
        chapterDto.setPath(chapter.getPath());
        chapterDto.setId(chapter.getId());
        chapterDto.setCustomPathCover(chapter.getCustomPathCover());
        chapter.setPages(chapter.getPages());
        return chapterDto;
    }

    public String getCustomPathCover() {
        return customPathCover;
    }

    public void setCustomPathCover(String customPathCover) {
        this.customPathCover = customPathCover;
    }

    public ReaderState getReaderState() {
        return readerState;
    }

    public void setReaderState(ReaderState readerState) {
        this.readerState = readerState;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<Page> getPages() {
        return pages;
    }
}
