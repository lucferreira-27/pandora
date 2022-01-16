package com.panadora.pandora.model.entities.player;

import com.panadora.pandora.model.entities.collection.item.Chapter;

import javax.persistence.*;
import java.util.List;

@Entity
public class ReaderState {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long currentPosition;
    private Long totalLength;
    @OneToOne
    private Chapter chapter;
    @ManyToOne
    private Reader reader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Long currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
