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

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class TitleManagerImpl implements TitleManager {

    @Autowired
    private final TitleRepository titleRepository;
    @Autowired
    private final TitleImageLoader titleImageLoader;
    @Autowired
    private final TitleImageDownloader titleImageDownloader;

    public TitleManagerImpl(TitleRepository titleRepository, TitleImageLoader titleImageLoader, TitleImageDownloader titleImageDownloader) {
        this.titleRepository = titleRepository;
        this.titleImageLoader = titleImageLoader;
        this.titleImageDownloader = titleImageDownloader;
    }


    @Override
    public List<TitleDto> listTitles() {
        List<Title> titles = TitleManagerUtil.getListOfTitles(titleRepository);
        List<TitleDto> chapterDto = TitleManagerUtil.fromTitleToTitleDto(titles,new TitleDto());
        return chapterDto;
    }

    @Override
    public TitleItemsDto getTitle(String id) {

        Title title = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), titleRepository);
        return (TitleItemsDto) new TitleItemsDto().toDto(title);
    }

    @Override
    public void deleteTitle(String id) {
        Title title = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), titleRepository);
        titleRepository.delete(title);
    }

    @Override
    public TitleDto addTitle(TitleForm titleForm) {return null;}

    @Override
    public InputStream getTitleImage(String image, String id) {
        Optional<Title> optional = titleRepository.findById(Long.valueOf(id));
        if (optional.isPresent()) {
            Title title = optional.get();
            TitleDetails titleDetails = title.getTitleDetails();
            titleImageLoader.load(image, titleDetails);
        }
        throw new TitleNotFoundException();
    }



}
