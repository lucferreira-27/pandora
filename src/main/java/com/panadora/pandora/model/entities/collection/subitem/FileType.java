package com.panadora.pandora.model.entities.collection.subitem;

public enum FileType {
    JPG("jpg"),
    PNG("png");
    private final String value;
    FileType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
