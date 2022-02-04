package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.model.entities.collection.title.*;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class TitleManagerImpl implements TitleManager {

    private final TitleRepository titleRepository;
    private final TitleImageLoader titleImageLoader;
    private final TitleManagerUtil titleManagerUtil;

    public TitleManagerImpl(TitleRepository titleRepository,
                            TitleImageLoader titleImageLoader,
                            TitleManagerUtil titleManagerUtil) {
        this.titleRepository = titleRepository;
        this.titleImageLoader = titleImageLoader;
        this.titleManagerUtil = titleManagerUtil;
    }


    @Override
    public List<TitleDto> listTitles() {
        List<Title> titles = titleManagerUtil.getListOfTitles(titleRepository);
        List<TitleDto> chapterDto = titleManagerUtil.fromTitleToTitleDto(titles,new TitleDto());
        return chapterDto;
    }

    @Override
    public TitleItemsDto getTitle(String id) {

        Title title = titleManagerUtil.getTitleIfExist(Long.valueOf(id), titleRepository);
        return (TitleItemsDto) TitleDto.toDto(title);
    }

    @Override
    public void deleteTitle(String id) {
        Title title = titleManagerUtil.getTitleIfExist(Long.valueOf(id), titleRepository);
        titleRepository.delete(title);
    }

    @Override
    public TitleDto addTitle(TitleForm titleForm) {
        throw new NotImplementedException();
    }

    @Override
    public InputStream getTitleImage(String image, String id) {
        Optional<Title> optional = titleRepository.findById(Long.valueOf(id));
        if (optional.isPresent()) {
            Title title = optional.get();
            TitleDetails titleDetails = title.getTitleDetails();
            return titleImageLoader.load(image, titleDetails);
        }
        throw new TitleNotFoundException();
    }



}
