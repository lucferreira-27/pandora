package com.panadora.pandora.model.entities.collection.title;

import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.model.entities.collection.item.Item;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Manga extends Title {

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Chapter> chapters;

    @Override
    public List<Chapter> getItems() {
        return chapters;
    }

    public void setItems(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
