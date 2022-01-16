package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.controller.form.MangaForm;
import com.panadora.pandora.controller.form.MangaForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.repository.title.MangaRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class MangaTitleManagerImpl implements TitleManager<MangaDto, MangaForm>{
    @Autowired
    private final MangaRepository mangaRepository;
    @Autowired
    private final MangaTitleInsertImpl mangaTitleInsert;
    @Autowired
    private final TitleImageLoader titleImageLoader;
    @Autowired
    private final TitleImageDownloader titleImageDownloader;

    public MangaTitleManagerImpl(MangaRepository mangaRepository, MangaTitleInsertImpl mangaTitleInsert, TitleImageLoader titleImageLoader, TitleImageDownloader titleImageDownloader) {
        this.mangaRepository = mangaRepository;
        this.mangaTitleInsert = mangaTitleInsert;
        this.titleImageLoader = titleImageLoader;
        this.titleImageDownloader = titleImageDownloader;
    }


    @Override
    public List<MangaDto> listTitles() {
        List<Manga> mangas = TitleManagerUtil.getListOfTitles(mangaRepository);
        List<MangaDto> mangaDto = TitleManagerUtil.fromTitleToTitleDto(mangas,new MangaDto());
        return mangaDto;
    }

    @Override
    public MangaDto getTitle(String id) {
        Manga manga = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), mangaRepository);
        return (MangaDto) MangaDto.toDto(manga);
    }

    @Override
    public void deleteTitle(String id) {
        Manga manga = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), mangaRepository);
        mangaRepository.delete(manga);
    }

    @Override
    public MangaDto addTitle(MangaForm titleForm) {
        Manga newManga = new Manga();
        Manga manga = createTitle(newManga,titleForm);
        mangaTitleInsert.insertTitleOnCollection(manga,titleForm);
        return (MangaDto) MangaDto.toDto(manga);
    }

    @Override
    public InputStream getTitleImage(String image, String id) {
        Manga manga = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), mangaRepository);
        return  titleImageLoader.load(image, manga.getTitleDetails());
    }
    private Manga createTitle(Manga newManga, MangaForm mangaForm){
        Manga manga = TitleManagerUtil.createTitle(newManga,mangaForm);
        titleImageDownloader.downloadAndAddImagesBytes(manga.getTitleDetails(), mangaForm.getTitleDetailsForm());

        return manga;
    }
}
