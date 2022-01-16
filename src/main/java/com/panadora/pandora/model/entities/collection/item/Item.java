package com.panadora.pandora.model.entities.collection.item;

import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance
public abstract class Item {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String path;
    @OneToOne(cascade={CascadeType.ALL})
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected ItemDetails itemDetails;

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
        this.itemDetails = itemDetails;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}

