package com.panadora.pandora.model.entities.collection;

import com.panadora.pandora.model.entities.collection.title.Title;

import javax.persistence.*;
import java.util.List;

@Entity
public class Collection {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;

    @ManyToMany(mappedBy = "collections",cascade={CascadeType.REMOVE,CascadeType.PERSIST})
    private List<Title> titles;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
}
