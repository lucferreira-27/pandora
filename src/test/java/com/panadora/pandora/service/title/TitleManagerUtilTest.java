package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.AnimeDto;
import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.*;
import com.panadora.pandora.repository.title.AnimeRepository;
import com.panadora.pandora.repository.title.MangaRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TitleManagerUtilTest {

    private TitleManagerUtil titleManagerUtil;
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private AnimeRepository animeRepository;
    @Mock
    private MangaRepository mangaRepository;
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        titleManagerUtil =  new TitleManagerUtil();
    }
    @Test
    void shouldReturnTitle_Using_TitleRepository() {


        //given
        Title title = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };
        //when
        doReturn(Optional.of(title)).when(titleRepository).findById(1L);

        Title  resultTitle = titleManagerUtil.getTitleIfExist(1L,titleRepository);

        //asserts
        Assertions.assertNotNull(resultTitle);
        verify(titleRepository, times(1)).findById(eq(1L));

    }
    @Test
    void shouldReturnTitle_Using_AnimeRepository() {


        //given
        Title title = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };
        //when
        doReturn(Optional.of(title)).when(animeRepository).findById(1L);

        Title  resultTitle = titleManagerUtil.getTitleIfExist(1L,animeRepository);

        //asserts
        Assertions.assertNotNull(resultTitle);
        verify(animeRepository, times(1)).findById(eq(1L));

    }
    @Test
    void shouldReturnTitle_Using_MangaRepository() {


        //given
        Title title = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };
        //when
        doReturn(Optional.of(title)).when(mangaRepository).findById(1L);

        Title  resultTitle = titleManagerUtil.getTitleIfExist(1L,mangaRepository);

        //asserts
        Assertions.assertNotNull(resultTitle);
        verify(mangaRepository, times(1)).findById(eq(1L));

    }
    @Test
    void shouldThrowTitleNotFoundException_Using_TitleRepository_OnGetTitleIfExist() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(titleRepository).findById(1L);

        TitleNotFoundException exception = Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> titleManagerUtil.getTitleIfExist(1L,titleRepository));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(titleRepository, times(1)).findById(eq(1L));
    }
    @Test
    void shouldThrowTitleNotFoundException_Using_AnimeRepository_OnGetTitleIfExist() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(animeRepository).findById(1L);

        TitleNotFoundException exception = Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> titleManagerUtil.getTitleIfExist(1L,animeRepository));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(animeRepository, times(1)).findById(eq(1L));
    }
    @Test
    void shouldThrowTitleNotFoundException_Using_MangaRepository_OnGetTitleIfExist() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(mangaRepository).findById(1L);

        TitleNotFoundException exception = Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> titleManagerUtil.getTitleIfExist(1L,mangaRepository));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(mangaRepository, times(1)).findById(eq(1L));
    }

    @Test
    void shouldReturnListOfTitles_Using_TitleRepository() {
        // given
        List<Title> titles = Arrays.asList(new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        });
        //when
        doReturn(titles).when(titleRepository).findAll();
        List<Title> titleListResult = titleManagerUtil.getListOfTitles(titleRepository);
        // asserts
        Assertions.assertEquals(titleListResult.size(),titles.size());
        verify(titleRepository, times(1)).findAll();

    }
    @Test
    void shouldReturnListOfMangas_Using_MangaRepository() {
        // given
        List<Manga> mangas = Arrays.asList(new Manga());
        //when
        doReturn(mangas).when(mangaRepository).findAll();
        List<Manga> mangasListResult = titleManagerUtil.getListOfTitles(mangaRepository);
        // asserts
        Assertions.assertEquals(mangasListResult.size(),mangas.size());
        verify(mangaRepository, times(1)).findAll();

    }
    @Test
    void shouldReturnListOfAnimes_Using_AnimeRepository() {
        // given
        List<Anime> animes = Arrays.asList(new Anime());
        //when
        doReturn(animes).when(animeRepository).findAll();
        List<Anime> animesListResult = titleManagerUtil.getListOfTitles(animeRepository);
        // asserts
        Assertions.assertEquals(animesListResult.size(),animes.size());
        verify(animeRepository, times(1)).findAll();

    }

    @Test
    void shouldCovertTitleListToTitleDtoList() {
        // given
        List<Title> titles = Arrays.asList(new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        });
        // when
        List<TitleDto> titlesDto = titleManagerUtil.fromTitleToTitleDto(titles,new TitleDto());

        // asserts
        Assertions.assertEquals(titlesDto.size(),titles.size());

    }
    @Test
    void shouldCovertAnimeListToAnimeDtoList() {
        // given
        List<Anime> animes = Arrays.asList(new Anime());
        // when
        List<AnimeDto> animesDto = titleManagerUtil.fromTitleToTitleDto(animes,new AnimeDto());

        // asserts
        Assertions.assertEquals(animesDto.size(),animes.size());
    }
    @Test
    void shouldCovertMangaListToMangaDtoList() {
        // given
        List<Manga> mangas = Arrays.asList(new Manga());
        // when
        List<MangaDto> mangasDto = titleManagerUtil.fromTitleToTitleDto(mangas,new MangaDto());

        // asserts
        Assertions.assertEquals(mangasDto.size(),mangas.size());
    }

    @Test
    void shouldCreateTitleFromTitleForm() {
        // given
        Title title = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };
        TitleForm titleForm = new TitleForm();
        titleForm.setTitleType(TitleType.MOVIE);
        titleForm.setPath("TEST Any Path");
        TitleDetailsForm titleDetailsForm = utilCreateTitleDetailsForm();
        titleForm.setTitleDetailsForm(titleDetailsForm);
        //when
        Title resultTitle = titleManagerUtil.createTitleFromForm(title,titleForm);

        //asserts
        utilCompareEachTitleFormField(resultTitle,titleForm);
        utilCompareEachTitleDetailsFormField(resultTitle.getTitleDetails(),titleDetailsForm);

    }
    @Test
    void shouldCreateAnimeFromAnimeForm() {
        // given
        Anime anime = new Anime();
        TitleForm titleForm = new TitleForm();
        titleForm.setTitleType(TitleType.ANIME);
        titleForm.setPath("TEST Any Path");
        TitleDetailsForm titleDetailsForm = utilCreateTitleDetailsForm();
        titleForm.setTitleDetailsForm(titleDetailsForm);
        //when
        Anime resultAnime = titleManagerUtil.createTitleFromForm(anime,titleForm);

        //asserts
        utilCompareEachTitleFormField(resultAnime,titleForm);
        utilCompareEachTitleDetailsFormField(resultAnime.getTitleDetails(),titleDetailsForm);

    }
    @Test
    void shouldCreateMangaFromMangaForm() {
        // given
        Manga manga = new Manga();
        TitleForm titleForm = new TitleForm();
        titleForm.setTitleType(TitleType.MANGA);
        titleForm.setPath("TEST Any Path");
        TitleDetailsForm titleDetailsForm = utilCreateTitleDetailsForm();
        titleForm.setTitleDetailsForm(titleDetailsForm);
        //when
        Manga resultManga = titleManagerUtil.createTitleFromForm(manga,titleForm);

        //asserts
        utilCompareEachTitleFormField(resultManga,titleForm);
        utilCompareEachTitleDetailsFormField(resultManga.getTitleDetails(),titleDetailsForm);

    }
    private  TitleDetailsForm utilCreateTitleDetailsForm(){
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        titleDetailsForm.setFinishedDate(new Date());
        titleDetailsForm.setReleaseDate(new Date());
        titleDetailsForm.setImageCoverUrl("TEST Any URL Image Cover");
        titleDetailsForm.setImageThumbnailUrl("TEST Any URL Image Thumbnail");
        titleDetailsForm.setName("TEST Any Name");
        titleDetailsForm.setScore(99999.00);
        titleDetailsForm.setSynopsis("TEST Any Text");
        return titleDetailsForm;
    }
    private void utilCompareEachTitleFormField(Title title, TitleForm titleForm){
        Assertions.assertEquals(title.getPath(),titleForm.getPath());
        Assertions.assertEquals(title.getTitleType(),titleForm.getTitleType());


    }
    private void utilCompareEachTitleDetailsFormField(TitleDetails titleDetails, TitleDetailsForm titleDetailsForm){
        Assertions.assertEquals(titleDetails.getFinishedDate(),titleDetailsForm.getFinishedDate());
        Assertions.assertEquals(titleDetails.getReleaseDate(),titleDetailsForm.getReleaseDate());
        Assertions.assertEquals(titleDetails.getName(),titleDetailsForm.getName());
        Assertions.assertEquals(titleDetails.getScore(),titleDetailsForm.getScore());
        Assertions.assertEquals(titleDetails.getSynopsis(),titleDetailsForm.getSynopsis());

    }

}