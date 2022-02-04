package com.panadora.pandora.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.panadora.pandora.model.entities.collection.item.ItemType;

public class ItemForm {
    protected String path;
    protected Long titleId;
    @JsonProperty("type")
    protected ItemType itemType;
    @JsonProperty("details")
    protected ItemDetailsForm itemDetailsForm;

    public ItemDetailsForm getItemDetailsForm() {
        return  itemDetailsForm;
    }

    public void setItemDetailsForm(ItemDetailsForm itemDetailsForm) {
        this.itemDetailsForm = itemDetailsForm;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
    public Long getTitleId() {
        return titleId;
    }
    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



}
