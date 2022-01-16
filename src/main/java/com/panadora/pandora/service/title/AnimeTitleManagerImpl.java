package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.AnimeDto;
import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.title.AnimeRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class AnimeTitleManagerImpl implements TitleManager<AnimeDto, AnimeForm>{

    @Autowired
    private final AnimeRepository animeRepository;
    @Autowired
    private final AnimeTitleInsertImpl animeTitleInsert;
    @Autowired
    private final TitleImageLoader titleImageLoader;
    @Autowired
    private final TitleImageDownloader titleImageDownloader;

    public AnimeTitleManagerImpl(AnimeRepository animeRepository, AnimeTitleInsertImpl animeTitleInsert, TitleImageLoader titleImageLoader, TitleImageDownloader titleImageDownloader) {
        this.animeRepository = animeRepository;
        this.animeTitleInsert = animeTitleInsert;
        this.titleImageLoader = titleImageLoader;
        this.titleImageDownloader = titleImageDownloader;
    }


    @Override
    public List<AnimeDto> listTitles() {
        List<Anime> animes = TitleManagerUtil.getListOfTitles(animeRepository);
        List<AnimeDto> animeDto = TitleManagerUtil.fromTitleToTitleDto(animes,new AnimeDto());
        return animeDto;
    }

    @Override
    public AnimeDto getTitle(String id) {
        Anime anime = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), animeRepository);
        return (AnimeDto) AnimeDto.toDto(anime);
    }

    @Override
    public void deleteTitle(String id) {
        Anime anime = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), animeRepository);
        animeRepository.delete(anime);
    }

    @Override
    public AnimeDto addTitle(AnimeForm titleForm) {
        Anime newAnime = new Anime();
        Anime anime = createTitle(newAnime,titleForm);
        animeTitleInsert.insertTitleOnCollection(anime,titleForm);
        return (AnimeDto) AnimeDto.toDto(anime);
    }

    @Override
    public InputStream getTitleImage(String image, String id) {
        Anime anime = TitleManagerUtil.getTitleIfExist(Long.valueOf(id), animeRepository);
        return  titleImageLoader.load(image, anime.getTitleDetails());
    }
    private Anime createTitle(Anime newAnime, AnimeForm animeForm){
        Anime anime = TitleManagerUtil.createTitle(newAnime,animeForm);
        titleImageDownloader.downloadAndAddImagesBytes(anime.getTitleDetails(), animeForm.getTitleDetailsForm());

        return anime;
    }
}
