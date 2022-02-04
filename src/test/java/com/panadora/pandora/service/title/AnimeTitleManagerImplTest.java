package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.AnimeDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.title.AnimeRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;
import com.panadora.pandora.service.exceptions.AddImageException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
class AnimeTitleManagerImplTest {
    @Mock
    private AnimeRepository animeRepository;
    @Mock
    private TitleImageLoader titleImageLoader;
    @Mock
    private TitleManagerUtil titleManagerUtil;
    @Mock
    private AnimeTitleInsertImpl animeTitleInsert;
    @Mock
    private TitleImageDownloader titleImageDownloader;
    @InjectMocks
    private AnimeTitleManagerImpl animeTitleManager;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldReturnAnimesList() {

        //given
        List<Anime> animes = Arrays.asList(new Anime());
        List<AnimeDto> animesDto = Arrays.asList(new AnimeDto());

        //when
        doReturn(animes).when(titleManagerUtil).getListOfTitles(any(AnimeRepository.class));
        doReturn(animesDto).when(titleManagerUtil).fromTitleToTitleDto(eq(animes), any(AnimeDto.class));

        //then
        List<AnimeDto> resultAnimesDto = animeTitleManager.listTitles();


        //assert
        Assertions.assertNotNull(resultAnimesDto);
        Assertions.assertEquals(resultAnimesDto.size(),animesDto.size());
        verify(titleManagerUtil, times(1)).getListOfTitles(any(AnimeRepository.class));
        verify(titleManagerUtil, times(1)).fromTitleToTitleDto(eq(animes), any(TitleDto.class));
    }

    @Test
    void shouldReturnAnime() {
        // given
        Anime expectAnime = new Anime();
        expectAnime.setId(1L);

        //when
        doReturn(expectAnime).when(titleManagerUtil).getTitleIfExist(eq(1L), any(AnimeRepository.class));
        MockedStatic<AnimeDto> mb = Mockito.mockStatic(AnimeDto.class);
        mb.when(() -> AnimeDto.toDto(any(Anime.class)))
                .thenAnswer(i -> new AnimeDto((Anime) i.getArguments()[0]));

        //then
        AnimeDto resultAnime = animeTitleManager.getTitle("1");


        // assert
        Assertions.assertNotNull(resultAnime);
        Assertions.assertEquals(resultAnime.getId(), expectAnime.getId());
        verify(titleManagerUtil, times(1)).getTitleIfExist(eq(1L), any(AnimeRepository.class));
        mb.verify(() -> AnimeDto.toDto(any(Anime.class)), times(1));

        mb.close();

    }

    @Test
    void shouldDeleteAnime() {

        //given
        Anime anime = new Anime();

        //when
        doReturn(anime).when(titleManagerUtil).getTitleIfExist(eq(1L), any(AnimeRepository.class));
        doNothing().when(animeRepository).delete(any(Anime.class));

        //then
        animeTitleManager.deleteTitle("1");


        //assert
        verify(animeRepository).delete(eq(anime));
    }

    @Test
    void shouldAddAnime() throws AddImageException {
        // given
        Anime anime = new Anime();
        TitleDetails titleDetails = new TitleDetails();
        anime.setTitleDetails(titleDetails);
        AnimeForm animeForm = new AnimeForm();
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        animeForm.setTitleDetailsForm(titleDetailsForm);
        // when
        doNothing().when(titleImageDownloader)
                .downloadAndAddImagesBytes(
                        any(TitleDetails.class),
                        any(TitleDetailsForm.class)
                );
        doReturn(anime).when(titleManagerUtil)
                .createTitleFromForm(
                        any(Anime.class),
                        eq(animeForm)
                );
        doNothing().when(animeTitleInsert)
                .insertTitleOnCollection(
                        eq(anime),
                        eq(animeForm)
                );
        //then
        AnimeDto resultAnime = animeTitleManager.addTitle(animeForm);

        // asserts
        Assertions.assertNotNull(resultAnime);
        verify(titleImageDownloader,times(1)).downloadAndAddImagesBytes(
                any(TitleDetails.class),
                any(TitleDetailsForm.class)
        );
        verify(animeTitleInsert,times(1)).insertTitleOnCollection(
                eq(anime),
                eq(animeForm)
        );
        verify(titleManagerUtil).createTitleFromForm(
                any(Anime.class),
                eq(animeForm)
        );


    }
    @Test
    void shouldTitleBadRequestException_When_AddAnime() throws AddImageException {
        // given
        Anime anime = new Anime();
        TitleDetails titleDetails = new TitleDetails();
        anime.setTitleDetails(titleDetails);
        AnimeForm animeForm = new AnimeForm();
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        animeForm.setTitleDetailsForm(titleDetailsForm);
        // when
        doThrow(AddImageException.class).when(titleImageDownloader)
                .downloadAndAddImagesBytes(
                        any(TitleDetails.class),
                        any(TitleDetailsForm.class)
                );
        doReturn(anime).when(titleManagerUtil)
                .createTitleFromForm(
                        any(Anime.class),
                        eq(animeForm)
                );


        // asserts
        assertThrows(TitleBadRequestException.class, () -> animeTitleManager.addTitle(animeForm));

        verify(titleImageDownloader,times(1)).downloadAndAddImagesBytes(
                any(TitleDetails.class),
                any(TitleDetailsForm.class)
        );
        verify(animeTitleInsert,never()).insertTitleOnCollection(
                eq(anime),
                eq(animeForm)
        );
        verify(titleManagerUtil).createTitleFromForm(
                any(Anime.class),
                eq(animeForm)
        );


    }

    @Test
    void shouldReturnAnimeImage() {
        // given
        Anime anime = new Anime();
        anime.setTitleDetails(new TitleDetails());
        InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());

        // when
        doReturn(Optional.of(anime)).when(animeRepository).findById(1L);
        when(titleImageLoader.load(anyString(), any(TitleDetails.class))).thenReturn(anyInputStream);

        //then
        InputStream inputStream = animeTitleManager.getTitleImage("image", "1");

        //assert
        Assertions.assertNotNull(inputStream);
        verify(animeRepository, times(1)).findById(eq(1L));
        verify(titleImageLoader, times(1)).load(anyString(), any(TitleDetails.class));
    }
    @Test
    public void shouldThrowTitleNotFoundException_OnGetTitleImage() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(animeRepository).findById(anyLong());

        //then
        TitleNotFoundException exception = Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> animeTitleManager.getTitleImage("image", "1"));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(titleImageLoader, never()).load(anyString(), any(TitleDetails.class));


    }
}