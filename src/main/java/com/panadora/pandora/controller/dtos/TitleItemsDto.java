package com.panadora.pandora.controller.dtos;

import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;

import java.util.List;

public class TitleItemsDto extends TitleDto{
    private Long id;
    private String path;
    private TitleDetails titleDetails;
    private List<? extends Item> items;

    public TitleItemsDto(Title title){
        this.id=  title.getId();
        this.path = title.getPath();
        this.titleDetails = title.getTitleDetails();
        this.items = title.getItems();
    }

    public TitleItemsDto() {

    }

    public TitleDetails getTitleDetails() {
        return titleDetails;
    }

    public String getPath() {
        return path;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<? extends Item> getItems() {
        return items;
    }
}
