package com.panadora.pandora.model.entities.collection.title;

public enum ImageType {
    THUMBNAIL("thumbnail"), COVER("cover");
    private final String value;

    private ImageType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
