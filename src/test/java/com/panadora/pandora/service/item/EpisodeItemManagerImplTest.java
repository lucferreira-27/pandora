package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.AnimeDto;
import com.panadora.pandora.controller.dtos.EpisodeDto;
import com.panadora.pandora.controller.dtos.ItemDto;
import com.panadora.pandora.controller.form.AnimeForm;
import com.panadora.pandora.controller.form.EpisodeForm;
import com.panadora.pandora.controller.form.ItemDetailsForm;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.item.ItemDetails;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.item.EpisodeRepository;
import com.panadora.pandora.repository.item.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EpisodeItemManagerImplTest {


    @Mock
    private EpisodeRepository episodeRepository;
    @Mock
    private EpisodeItemInsertImpl episodeItemInsert;;
    @Mock
    private ItemManagerUtil itemManagerUtil;
    @InjectMocks
    private EpisodeItemManagerImpl episodeManager;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldReturnEpisodesList() {
        List<Episode> episodes = Arrays.asList(new Episode());
        List<EpisodeDto> episodesDto = Arrays.asList(new EpisodeDto());

        doReturn(episodes).when(itemManagerUtil).getListOfItems(any(EpisodeRepository.class));
        doReturn(episodesDto).when(itemManagerUtil).fromItemToItemDto(eq(episodes), any(EpisodeDto.class));

        Assertions.assertNotNull(episodeManager.listItems());

        verify(itemManagerUtil, times(1)).getListOfItems(any(EpisodeRepository.class));
        verify(itemManagerUtil, times(1)).fromItemToItemDto(eq(episodes), any(EpisodeDto.class));
    }
    @Test
    public void shouldReturnEpisode() {

        // given
        Episode expectEpisode = new Episode();

        //when
        doReturn(expectEpisode).when(itemManagerUtil).getItemIfExist(eq(1L), any(EpisodeRepository.class));
        MockedStatic<EpisodeDto> mb = Mockito.mockStatic(EpisodeDto.class);
        mb.when(() -> EpisodeDto.toDto(any(Episode.class)))
                .thenAnswer(i -> new EpisodeDto((Episode) i.getArguments()[0]));

        //then
        EpisodeDto resultItem = episodeManager.getItem("1");

        // assert
        Assertions.assertNotNull(resultItem);
        verify(itemManagerUtil, times(1)).getItemIfExist(eq(1L), any(EpisodeRepository.class));
        mb.verify(() ->EpisodeDto.toDto(any(Episode.class)), times(1));

        mb.close();

    }
    @Test
    public void shouldDeleteEpisode() {

        // given
        Episode episode = new Episode();

        //when
        doReturn(episode).when(itemManagerUtil).getItemIfExist(eq(1L), any(EpisodeRepository.class));

        //then
        episodeManager.deleteItem("1");

        //assert
        verify(episodeRepository).delete(eq(episode));
    }


    @Test
    public void shouldAddEpisode(){
        // given
        Episode episode = new Episode();
        ItemDetails itemDetails = new ItemDetails();
        episode.setItemDetails(itemDetails);
        EpisodeForm episodeForm = new EpisodeForm();
        ItemDetailsForm itemDetailsForm = new ItemDetailsForm();
        episodeForm.setItemDetailsForm(itemDetailsForm);
        // when

        doReturn(episode).when(itemManagerUtil)
                .createItem(
                        any(Episode.class),
                        eq(episodeForm)
                );
        doNothing().when(episodeItemInsert)
                .insertItemOnTitle(
                        eq(episode),
                        eq(episodeForm)
                );
        //then
        EpisodeDto resultEpisode = episodeManager.addItem(episodeForm);

        // asserts
        Assertions.assertNotNull(resultEpisode);

        verify(episodeItemInsert,times(1)).insertItemOnTitle(
                eq(episode),
                eq(episodeForm)
        );
        verify(itemManagerUtil).createItem(
                any(Episode.class),
                eq(episodeForm)
        );
    }

}