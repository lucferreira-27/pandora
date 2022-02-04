package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.AnimeDto;
import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.title.AnimeRepository;
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
public class AnimeTitleManagerImpl implements TitleManager<AnimeDto, AnimeForm> {

    private final AnimeRepository animeRepository;
    private final AnimeTitleInsertImpl animeTitleInsert;
    private final TitleImageLoader titleImageLoader;
    private final TitleImageDownloader titleImageDownloader;
    private final TitleManagerUtil titleManagerUtil;

    public AnimeTitleManagerImpl(AnimeRepository animeRepository,
                                 AnimeTitleInsertImpl animeTitleInsert,
                                 TitleImageLoader titleImageLoader,
                                 TitleImageDownloader titleImageDownloader,
                                 TitleManagerUtil titleManagerUtil) {
        this.animeRepository = animeRepository;
        this.animeTitleInsert = animeTitleInsert;
        this.titleImageLoader = titleImageLoader;
        this.titleImageDownloader = titleImageDownloader;
        this.titleManagerUtil = titleManagerUtil;
    }


    @Override
    public List<AnimeDto> listTitles() {
        List<Anime> animes = titleManagerUtil.getListOfTitles(animeRepository);
        List<AnimeDto> animeDto = titleManagerUtil.fromTitleToTitleDto(animes, new AnimeDto());
        return animeDto;
    }

    @Override
    public AnimeDto getTitle(String id) {

        Anime anime = titleManagerUtil.getTitleIfExist(Long.valueOf(id), animeRepository);
        return AnimeDto.toDto(anime);
    }

    @Override
    public void deleteTitle(String id) {
        Anime anime = titleManagerUtil.getTitleIfExist(Long.valueOf(id), animeRepository);
        animeRepository.delete(anime);
    }

    @Override
    public AnimeDto addTitle(AnimeForm titleForm) {
        try {
            Anime newAnime = new Anime();
            Anime anime = createTitle(newAnime, titleForm);
            animeTitleInsert.insertTitleOnCollection(anime, titleForm);
            return AnimeDto.toDto(anime);
        } catch (AddImageException e) {
            e.printStackTrace();
            throw new TitleBadRequestException("Failed on load image");
        }

    }

    @Override
    public InputStream getTitleImage(String image, String id) {
        Optional<Anime> optional = animeRepository.findById(Long.valueOf(id));
        if (optional.isPresent()) {
            Anime anime = optional.get();
            TitleDetails titleDetails = anime.getTitleDetails();
            return titleImageLoader.load(image, titleDetails);
        }
        throw new TitleNotFoundException();
    }

    private Anime createTitle(Anime newAnime, AnimeForm animeForm) throws AddImageException {
        Anime anime = titleManagerUtil.createTitleFromForm(newAnime, animeForm);
        titleImageDownloader.downloadAndAddImagesBytes(anime.getTitleDetails(), animeForm.getTitleDetailsForm());

        return anime;
    }
}
