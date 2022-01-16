package com.panadora.pandora.model.entities.collection.title;

import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.item.Item;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Anime extends Title{
    @OneToMany(cascade= {CascadeType.ALL})
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Episode> episodes;

    @Override
    public List<Episode> getItems() {
        return episodes;
    }

    public void setItems(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
