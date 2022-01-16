package com.panadora.pandora.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.panadora.pandora.model.entities.collection.title.TitleType;

public class TitleForm {

    private String path;
    @JsonProperty("type")
    private TitleType titleType;
    private TitleDetailsForm titleDetailsForm;
    private Long collectionId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TitleDetailsForm getTitleDetailsForm() {
        return titleDetailsForm;
    }

    public void setTitleDetailsForm(TitleDetailsForm titleDetailsForm) {
        this.titleDetailsForm = titleDetailsForm;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }
}
