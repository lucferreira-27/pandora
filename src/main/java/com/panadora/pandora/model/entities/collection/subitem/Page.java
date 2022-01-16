package com.panadora.pandora.model.entities.collection.subitem;

import com.panadora.pandora.model.entities.collection.item.Chapter;

import javax.persistence.*;

@Entity
public class Page {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Chapter chapter;
    private String path;
    private Long bytesLength;
    private FileType fileType;
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

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Long getBytesLength() {
        return bytesLength;
    }

    public void setBytesLength(Long bytesLength) {
        this.bytesLength = bytesLength;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
