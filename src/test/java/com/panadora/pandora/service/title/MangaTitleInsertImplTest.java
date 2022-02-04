package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.controller.form.MangaForm;
import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.repository.CollectionRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.CollectionNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MangaTitleInsertImplTest {

    @Mock
    private TitleRepository titleRepository;
    @Mock
    private CollectionRepository collectionRepository;

    @InjectMocks
    @Spy
    private MangaTitleInsertImpl mangaTitleInsert;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddMangaOnCollectionAndSave() {
        //given
        Collection collection = new Collection();
        Manga manga = new Manga();
        //when
        doNothing().when(mangaTitleInsert).saveAndFlush(manga);
        //then
        mangaTitleInsert.addTitleOnCollectionAndSave(collection,manga);

        //assert
        Assertions.assertThat(manga.getCollections()).contains(collection);
        verify(mangaTitleInsert, times(1)).saveAndFlush(manga);

    }
    @Test
    void shouldSaveAndFlush_Manga(){
        // given
        Manga manga = new Manga();

        //when
        when(titleRepository.saveAndFlush(eq(manga))).thenReturn(manga);

        //then
        mangaTitleInsert.saveAndFlush(manga);

        //assert
        verify(mangaTitleInsert,times(1)).saveAndFlush(manga);
    }
    @Test
    void shouldInsertMangaOnCollection(){
        //given
        Collection collection = new Collection();
        Manga manga = new Manga();
        MangaForm mangaForm = new MangaForm();
        mangaForm.setCollectionId(1L);
        //when
        doReturn(collection).when(mangaTitleInsert).getIfCollectionExist(1L);

        doNothing()
                .when(mangaTitleInsert)
                .addTitleOnCollectionAndSave(
                        collection,
                        manga
                );
        //then
        mangaTitleInsert.insertTitleOnCollection(manga,mangaForm);

        //assert
        verify(mangaTitleInsert,times(1)).getIfCollectionExist(1L);
        verify(mangaTitleInsert,times(1)).addTitleOnCollectionAndSave(collection,manga);


    }
    @Test
    void shouldGetCollectionById(){
        //given
        Collection collection = new Collection();

        //when
        when(collectionRepository.findById(anyLong())).thenReturn(Optional.of(collection));

        //then
        Collection resultCollection = mangaTitleInsert.getIfCollectionExist(1L);

        //assert
        Assertions.assertThat(resultCollection).isNotNull();
        verify(collectionRepository,times(1)).findById(anyLong());

    }
    @Test
    void shouldThrowCollectionNotFoundException_OnGetCollectionById(){
        //given
        String expectExceptionMsg = "Resource Not Found";

        //when
        when(collectionRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then
        CollectionNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CollectionNotFoundException.class,
                () -> mangaTitleInsert.getIfCollectionExist(1L));

        //assert
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectExceptionMsg);
        verify(collectionRepository,times(1)).findById(anyLong());

    }
}