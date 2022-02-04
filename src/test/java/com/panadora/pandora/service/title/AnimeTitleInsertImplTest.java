package com.panadora.pandora.service.title;


import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.repository.CollectionRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.CollectionNotFoundException;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import  org.assertj.core.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.mockito.Mockito.*;


class AnimeTitleInsertImplTest {

    @Mock
    private TitleRepository titleRepository;
    @Mock
    private CollectionRepository collectionRepository;

    @InjectMocks
    @Spy
    private AnimeTitleInsertImpl animeTitleInsert;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddAnimeOnCollectionAndSave() {
        //given
        Collection collection = new Collection();
        Anime anime = new Anime();
        //when
        doNothing().when(animeTitleInsert).saveAndFlush(anime);
        //then
        animeTitleInsert.addTitleOnCollectionAndSave(collection,anime);

        //assert
        Assertions.assertThat(anime.getCollections()).contains(collection);
        verify(animeTitleInsert, times(1)).saveAndFlush(anime);

    }

    @Test
    void shouldSaveAndFlush_Anime(){
        // given
        Anime anime = new Anime();

        //when
        when(titleRepository.saveAndFlush(eq(anime))).thenReturn(anime);

        //then
        animeTitleInsert.saveAndFlush(anime);

        //assert
        verify(animeTitleInsert,times(1)).saveAndFlush(anime);
    }

    @Test
    void shouldInsertAnimeOnCollection(){
        //given
        Collection collection = new Collection();
        Anime anime = new Anime();
        AnimeForm animeForm = new AnimeForm();
        animeForm.setCollectionId(1L);
        //when
        doReturn(collection).when(animeTitleInsert).getIfCollectionExist(1L);

        doNothing()
                .when(animeTitleInsert)
                .addTitleOnCollectionAndSave(
                    collection,
                    anime
                );
        //then
        animeTitleInsert.insertTitleOnCollection(anime,animeForm);

        //assert
        verify(animeTitleInsert,times(1)).getIfCollectionExist(1L);
        verify(animeTitleInsert,times(1)).addTitleOnCollectionAndSave(collection,anime);


    }


    @Test
    void shouldGetCollectionById(){
        //given
        Collection collection = new Collection();

        //when
        when(collectionRepository.findById(anyLong())).thenReturn(Optional.of(collection));

        //then
        Collection resultCollection = animeTitleInsert.getIfCollectionExist(1L);

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
                () -> animeTitleInsert.getIfCollectionExist(1L));

        //assert
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectExceptionMsg);
        verify(collectionRepository,times(1)).findById(anyLong());

    }
}