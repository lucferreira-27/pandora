package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.controller.form.MangaForm;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.title.MangaRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;
import com.panadora.pandora.service.exceptions.AddImageException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class MangaTitleManagerImpl implements TitleManager<MangaDto, MangaForm> {
    private final MangaRepository mangaRepository;
    private final MangaTitleInsertImpl mangaTitleInsert;
    private final TitleImageLoader titleImageLoader;
    private final TitleImageDownloader titleImageDownloader;
    private final TitleManagerUtil titleManagerUtil;

    public MangaTitleManagerImpl(MangaRepository mangaRepository,
                                 MangaTitleInsertImpl mangaTitleInsert,
                                 TitleImageLoader titleImageLoader,
                                 TitleImageDownloader titleImageDownloader,
                                 TitleManagerUtil titleManagerUtil) {
        this.mangaRepository = mangaRepository;
        this.mangaTitleInsert = mangaTitleInsert;
        this.titleImageLoader = titleImageLoader;
        this.titleImageDownloader = titleImageDownloader;
        this.titleManagerUtil = titleManagerUtil;

    }


    @Override
    public List<MangaDto> listTitles() {
        List<Manga> mangas = titleManagerUtil.getListOfTitles(mangaRepository);
        List<MangaDto> mangaDto = titleManagerUtil.fromTitleToTitleDto(mangas, new MangaDto());
        return mangaDto;
    }

    @Override
    public MangaDto getTitle(String id) {
        Manga manga = titleManagerUtil.getTitleIfExist(Long.valueOf(id), mangaRepository);
        return MangaDto.toDto(manga);
    }

    @Override
    public void deleteTitle(String id) {
        Manga manga = titleManagerUtil.getTitleIfExist(Long.valueOf(id), mangaRepository);
        mangaRepository.delete(manga);
    }

    @Override
    public MangaDto addTitle(MangaForm titleForm) {
        try {
            Manga newManga = new Manga();
            Manga manga = createTitle(newManga, titleForm);
            mangaTitleInsert.insertTitleOnCollection(manga, titleForm);
            return MangaDto.toDto(manga);
        } catch (AddImageException e) {
            e.printStackTrace();
            throw new TitleBadRequestException("Failed on load image");
        }
    }

    @Override
    public InputStream getTitleImage(String image, String id) {
        Optional<Manga> optional = mangaRepository.findById(Long.valueOf(id));
        if (optional.isPresent()) {
            Manga manga = optional.get();
            TitleDetails titleDetails = manga.getTitleDetails();
            return titleImageLoader.load(image, titleDetails);
        }
        throw new TitleNotFoundException();
    }

    private Manga createTitle(Manga newManga, MangaForm mangaForm) throws AddImageException {
        Manga manga = titleManagerUtil.createTitleFromForm(newManga, mangaForm);
        titleImageDownloader.downloadAndAddImagesBytes(manga.getTitleDetails(), mangaForm.getTitleDetailsForm());

        return manga;
    }
}
