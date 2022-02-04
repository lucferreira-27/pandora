package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.controller.form.EpisodeForm;
import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.repository.CollectionRepository;
import com.panadora.pandora.repository.item.ItemRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.CollectionNotFoundException;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import com.panadora.pandora.service.title.TitleManagerUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EpisodeItemInsertImplTest {
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private TitleManagerUtil titleManagerUtil;
    @InjectMocks
    @Spy
    private EpisodeItemInsertImpl episodeItemInsert;
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldAddEpisodeOnAnimeAndSave() {
        //given
        Anime anime = new Anime();
        Episode episode = new Episode();
        //when
        doNothing().when(episodeItemInsert).saveAndFlush(episode);
        //then
        episodeItemInsert.addItemOnTitleAndSave(anime,episode);

        //assert
        Assertions.assertThat(episode.getAnime()).isEqualTo(anime);
        verify(episodeItemInsert, times(1)).saveAndFlush(episode);

    }
    @Test
    void shouldSaveAndFlush_Episode(){
        // given
        Episode episode = new Episode();

        //when
        when(itemRepository.saveAndFlush(eq(episode))).thenReturn(episode);

        //then
        episodeItemInsert.saveAndFlush(episode);

        //assert
        verify(episodeItemInsert,times(1)).saveAndFlush(episode);
    }
    @Test
    void shouldInsertEpisodeOnTitle(){
        //given
        Episode episode = new Episode();
        Anime anime = new Anime();
        EpisodeForm episodeForm = new EpisodeForm();
        episodeForm.setTitleId(1L);
        //when
        doReturn(anime).when(titleManagerUtil).getTitleIfExist(1L,titleRepository);

        doNothing()
                .when(episodeItemInsert)
                .addItemOnTitleAndSave(
                        anime,
                        episode
                );
        //then
        episodeItemInsert.insertItemOnTitle(episode,episodeForm);

        //assert
        verify(titleManagerUtil,times(1)).getTitleIfExist(1L,titleRepository);
        verify(episodeItemInsert,times(1)).addItemOnTitleAndSave(anime,episode);

    }

    @Test
    void shouldThrowTitleNotFoundException_OnInsertEpisodeOnTitle(){
        //given
        String expectExceptionMsg = "Resource Not Found";
        Episode episode = new Episode();
        Anime anime = new Anime();
        EpisodeForm episodeForm = new EpisodeForm();
        episodeForm.setTitleId(0L);
        //when
        when(titleManagerUtil.getTitleIfExist(0L,titleRepository)).thenThrow(new TitleNotFoundException());

        //then
        TitleNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> episodeItemInsert.insertItemOnTitle(episode,episodeForm));

        //assert
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectExceptionMsg);
        verify(titleManagerUtil,times(1)).getTitleIfExist(0L,titleRepository);
        verify(episodeItemInsert,never()).addItemOnTitleAndSave(anime,episode);

    }

}