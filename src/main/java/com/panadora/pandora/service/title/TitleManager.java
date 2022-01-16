package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.controller.dtos.TitleItemsDto;

import java.io.InputStream;
import java.util.List;


public interface TitleManager<T extends TitleDto, E extends TitleForm> {
    public List<T> listTitles();
    public T getTitle(String id);
    public void deleteTitle(String id);
    public T addTitle(E titleForm);
    public InputStream getTitleImage(String image,String id);
}
