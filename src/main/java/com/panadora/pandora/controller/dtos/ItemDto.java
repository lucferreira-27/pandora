package com.panadora.pandora.controller.dtos;

import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.item.ItemDetails;

public class ItemDto {
    private Long id;
    private String path;
    private ItemDetails itemDetails;


    public ItemDto(){

    }

    public ItemDto(Item item){
        this.itemDetails = item.getItemDetails();
        this.path = item.getPath();
        this.id = item.getId();
    }

    public static ItemDto toDto(Item item){

        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetails(item.getItemDetails());
        itemDto.setPath(item.getPath());
        itemDto.setId(item.getId());

        return itemDto;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
