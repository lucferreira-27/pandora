package com.panadora.pandora.model.entities.collection.title;

import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.item.Item;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance
public abstract class Title {

    private TitleType titleType;
    @ManyToMany
    protected List<Collection> collections = new ArrayList<>();
    @OneToOne(cascade={CascadeType.REMOVE,CascadeType.PERSIST})
    protected TitleDetails titleDetails;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Collection> getCollections() {
        return collections;
    }

    public abstract List<? extends Item> getItems();

    public TitleDetails getTitleDetails() {
        return titleDetails;
    }

    public void setTitleDetails(TitleDetails titleDetails) {
        this.titleDetails = titleDetails;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return titleType == title.titleType && Objects.equals(collections, title.collections) && Objects.equals(titleDetails, title.titleDetails) && Objects.equals(id, title.id) && Objects.equals(path, title.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleType, collections, titleDetails, id, path);
    }
}
