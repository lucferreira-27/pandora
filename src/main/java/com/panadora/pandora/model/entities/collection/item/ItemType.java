package com.panadora.pandora.model.entities.collection.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ItemType {
    @JsonProperty("episode")
    EPISODE,
    @JsonProperty("chapter")
    CHAPTER
}
