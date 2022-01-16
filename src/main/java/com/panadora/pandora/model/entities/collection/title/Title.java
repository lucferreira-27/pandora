package com.panadora.pandora.model.entities.collection.title;

import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.item.Item;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance
public abstract class Title {

    private TitleType titleType;
    @ManyToMany
    protected List<Collection> collections;
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

}
