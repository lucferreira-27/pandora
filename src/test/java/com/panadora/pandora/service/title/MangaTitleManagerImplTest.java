package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.MangaDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.form.MangaForm;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.title.MangaRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;
import com.panadora.pandora.service.exceptions.AddImageException;
import com.panadora.pandora.service.exceptions.TitleBadRequestException;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MangaTitleManagerImplTest {

    @Mock
    private MangaRepository mangaRepository;
    @Mock
    private TitleImageLoader titleImageLoader;
    @Mock
    private TitleManagerUtil titleManagerUtil;
    @Mock
    private MangaTitleInsertImpl mangaTitleInsert;
    @Mock
    private TitleImageDownloader titleImageDownloader;
    @InjectMocks
    private MangaTitleManagerImpl mangaTitleManager;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnMangasList() {
       List<Manga> mangas = Arrays.asList(new Manga());
       List<MangaDto> mangasDto = Arrays.asList(new MangaDto());

        doReturn(mangas).when(titleManagerUtil).getListOfTitles(any(MangaRepository.class));
        doReturn(mangasDto).when(titleManagerUtil).fromTitleToTitleDto(eq(mangas), any(MangaDto.class));
        List<MangaDto> resultMangasDto = mangaTitleManager.listTitles();

        Assertions.assertNotNull(resultMangasDto);
        Assertions.assertEquals(resultMangasDto.size(),mangasDto.size());
        verify(titleManagerUtil, times(1)).getListOfTitles(any(MangaRepository.class));
        verify(titleManagerUtil, times(1)).fromTitleToTitleDto(eq(mangas), any(TitleDto.class));
    }
    @Test
    void shouldReturnManga(){
        // given
        Manga expectManga = new Manga();
        expectManga.setId(1L);

        //when
        doReturn(expectManga).when(titleManagerUtil).getTitleIfExist(
                eq(1L),
                any(MangaRepository.class)
        );
        MockedStatic<MangaDto> mb = Mockito.mockStatic(MangaDto.class);
        mb.when(() -> MangaDto.toDto(any(Manga.class)))
                .thenAnswer(i -> new MangaDto((Manga) i.getArguments()[0]));

        //then
        MangaDto resultManga = mangaTitleManager.getTitle("1");


        // assert
        Assertions.assertNotNull(resultManga);
        Assertions.assertEquals(resultManga.getId(), expectManga.getId());
        verify(titleManagerUtil, times(1)).getTitleIfExist(
                eq(1L),
                any(MangaRepository.class)
        );
        mb.verify(
                () -> MangaDto.toDto(any(Manga.class)), times(1)
        );

        mb.close();
    }
    @Test
    void shouldDeleteManga(){
        //given 
        Manga manga = new Manga();

        // when
        doNothing().when(mangaRepository).delete(any(Manga.class));
        doReturn(manga).when(titleManagerUtil).getTitleIfExist(eq(1L), any(MangaRepository.class));
        
        //then
        mangaTitleManager.deleteTitle("1");

        //assert
        verify(mangaRepository, times(1)).delete(eq(manga));

    }

    @Test
    void shouldAddManga() throws AddImageException {
        // given
        Manga manga = new Manga();
        TitleDetails titleDetails = new TitleDetails();
        manga.setTitleDetails(titleDetails);
        MangaForm mangaForm = new MangaForm();
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        mangaForm.setTitleDetailsForm(titleDetailsForm);
        // when
        doNothing().when(titleImageDownloader)
                .downloadAndAddImagesBytes(
                        any(TitleDetails.class),
                        any(TitleDetailsForm.class)
                );
        doReturn(manga).when(titleManagerUtil)
                .createTitleFromForm(
                        any(Manga.class),
                        eq(mangaForm)
                );
        doNothing().when(mangaTitleInsert)
                .insertTitleOnCollection(
                        eq(manga),
                        eq(mangaForm)
                );

        //then
        MangaDto resultManga = mangaTitleManager.addTitle(mangaForm);

        // asserts
        Assertions.assertNotNull(resultManga);
        verify(titleImageDownloader,times(1)).downloadAndAddImagesBytes(
                any(TitleDetails.class),
                any(TitleDetailsForm.class)
        );
        verify(mangaTitleInsert,times(1)).insertTitleOnCollection(
                eq(manga),
                eq(mangaForm)
        );
        verify(titleManagerUtil).createTitleFromForm(
                any(Manga.class),
                eq(mangaForm)
        );

    }

    @Test
    void shouldTitleBadRequestException_When_AddManga() throws AddImageException {
        // given
        Manga manga = new Manga();
        TitleDetails titleDetails = new TitleDetails();
        manga.setTitleDetails(titleDetails);
        MangaForm mangaForm = new MangaForm();
        TitleDetailsForm titleDetailsForm = new TitleDetailsForm();
        mangaForm.setTitleDetailsForm(titleDetailsForm);
        // when
        doThrow(AddImageException.class).when(titleImageDownloader)
                .downloadAndAddImagesBytes(
                        any(TitleDetails.class),
                        any(TitleDetailsForm.class)
                );
        doReturn(manga).when(titleManagerUtil)
                .createTitleFromForm(
                        any(Manga.class),
                        eq(mangaForm)
                );



        // asserts
        assertThrows(TitleBadRequestException.class, () -> mangaTitleManager.addTitle(mangaForm));

        verify(titleImageDownloader,times(1)).downloadAndAddImagesBytes(
                any(TitleDetails.class),
                any(TitleDetailsForm.class)
        );
        verify(mangaTitleInsert,never()).insertTitleOnCollection(
                eq(manga),
                eq(mangaForm)
        );
        verify(titleManagerUtil).createTitleFromForm(
                any(Manga.class),
                eq(mangaForm)
        );

    }

    @Test
    void shouldReturnMangaImage() {
        // given
        Manga manga = new Manga();
        manga.setTitleDetails(new TitleDetails());
        InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());

        // when
        doReturn(Optional.of(manga)).when(mangaRepository).findById(1L);
        when(titleImageLoader.load(anyString(), any(TitleDetails.class))).thenReturn(anyInputStream);

        //then
        InputStream inputStream = mangaTitleManager.getTitleImage("image", "1");

        //assert
        Assertions.assertNotNull(inputStream);
        verify(mangaRepository, times(1)).findById(eq(1L));
        verify(titleImageLoader, times(1)).load(anyString(), any(TitleDetails.class));
    }

    @Test
    public void shouldThrowTitleNotFoundException_OnGetTitleImage() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(mangaRepository).findById(anyLong());

        //then
        TitleNotFoundException exception = Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> mangaTitleManager.getTitleImage("image", "1"));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(titleImageLoader, never()).load(anyString(), any(TitleDetails.class));


    }


}