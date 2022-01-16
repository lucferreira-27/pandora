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

    public ItemType getItemType() {
        return itemType;
    }

    public String getPath() {
        return path;
    }

    public Long getTitleId() {
        return titleId;
    }
}
