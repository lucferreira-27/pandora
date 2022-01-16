package com.panadora.pandora.model.entities.collection.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panadora.pandora.model.entities.collection.subitem.Page;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.model.entities.player.ReaderState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chapter extends Item{
    @ManyToOne(cascade={CascadeType.PERSIST})
    @JsonIgnore
    private Manga manga;
    @OneToOne
    private ReaderState readerState;
    @OneToMany(mappedBy = "chapter")
    private List<Page> pages = new ArrayList<>();
    private String customPathCover;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public ReaderState getReaderState() {
        return readerState;
    }

    public void setReaderState(ReaderState readerState) {
        this.readerState = readerState;
    }

    public String getCustomPathCover() {
        return customPathCover;
    }

    public void setCustomPathCover(String pathPersonalizedCover) {
        this.customPathCover = pathPersonalizedCover;
    }
}
