package com.panadora.pandora.controller.dtos;

import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;

public class TitleDto {
    private Long id;
    private String path;
    private TitleDetails titleDetails;

    public TitleDto(Title title){
        this.id=  title.getId();
        this.path = title.getPath();
        this.titleDetails = title.getTitleDetails();
    }
    public TitleDto(){

    }

    public TitleDetails getTitleDetails() {
        return titleDetails;
    }

    public void setTitleDetails(TitleDetails titleDetails) {
        this.titleDetails = titleDetails;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static TitleDto toDto(Title title) {
        TitleDto titleDto = new TitleDto();
        titleDto.setId(title.getId());
        titleDto.setPath(title.getPath());
        titleDto.setTitleDetails(title.getTitleDetails());
        return titleDto;
    }
}
