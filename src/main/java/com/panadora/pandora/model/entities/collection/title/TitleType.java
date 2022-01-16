package com.panadora.pandora.model.entities.collection.title;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TitleType {
    @JsonProperty("anime")
    ANIME,
    @JsonProperty("manga")
    MANGA,
    @JsonProperty("movie")
    MOVIE,

}
