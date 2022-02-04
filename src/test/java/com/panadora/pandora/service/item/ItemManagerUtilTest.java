package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.ChapterDto;
import com.panadora.pandora.controller.dtos.EpisodeDto;
import com.panadora.pandora.controller.dtos.ItemDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.form.*;
import com.panadora.pandora.model.entities.collection.item.*;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.item.ChapterRepository;
import com.panadora.pandora.repository.item.EpisodeRepository;
import com.panadora.pandora.repository.item.ItemRepository;
import com.panadora.pandora.repository.title.AnimeRepository;
import com.panadora.pandora.repository.title.MangaRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.ItemNotFoundException;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import com.panadora.pandora.service.title.TitleManagerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ItemManagerUtilTest {


    @Mock
    private ItemRepository itemRepository;
    @Mock
    private EpisodeRepository episodeRepository;
    @Mock
    private ChapterRepository chapterRepository;

    private ItemManagerUtil itemManagerUtil;
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        itemManagerUtil =  new ItemManagerUtil();
    }
    @Test
    void shouldReturnItem_Using_ItemRepository() {


        //given
        Item item = new Item() {
            @Override
            public Long getId() {
                return super.getId();
            }
        };
        //when
        doReturn(Optional.of(item)).when(itemRepository).findById(1L);
        //then
        Item  resultItem = itemManagerUtil.getItemIfExist(1L,itemRepository);

        //asserts
        Assertions.assertNotNull(resultItem);
        verify(itemRepository, times(1)).findById(eq(1L));

    }
    @Test
    void shouldReturnEpisode_Using_EpisodeRepository() {


        //given
        Episode episode = new Episode();
        //when
        doReturn(Optional.of(episode)).when(episodeRepository).findById(1L);
        //then
        Episode  resultEpisode = itemManagerUtil.getItemIfExist(1L,episodeRepository);

        //asserts
        Assertions.assertNotNull(resultEpisode);
        verify(episodeRepository, times(1)).findById(eq(1L));

    }
    @Test
    void shouldReturnChapter_Using_ChapterRepository() {


        //given
        Chapter chapter = new Chapter();
        //when
        doReturn(Optional.of(chapter)).when(chapterRepository).findById(1L);
        //then
        Chapter  resultChapter = itemManagerUtil.getItemIfExist(1L,chapterRepository);

        //asserts
        Assertions.assertNotNull(resultChapter);
        verify(chapterRepository, times(1)).findById(eq(1L));

    }
    @Test
    void shouldThrowItemNotFoundException_Using_ItemRepository_OnGetTitleIfExist() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(itemRepository).findById(1L);
        //then
        ItemNotFoundException exception = Assertions.assertThrows(
                ItemNotFoundException.class,
                () -> itemManagerUtil.getItemIfExist(1L,itemRepository));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(itemRepository, times(1)).findById(eq(1L));
    }
    @Test
    void shouldThrowItemNotFoundException_Using_EpisodeRepository_OnGetTitleIfExist() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(episodeRepository).findById(1L);

        //then
        ItemNotFoundException exception = Assertions.assertThrows(
                ItemNotFoundException.class,
                () -> itemManagerUtil.getItemIfExist(1L,episodeRepository));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(episodeRepository, times(1)).findById(eq(1L));
    }
    @Test
    void shouldThrowItemNotFoundException_Using_ChapterRepository_OnGetTitleIfExist() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(chapterRepository).findById(1L);

        //then
        ItemNotFoundException exception = Assertions.assertThrows(
                ItemNotFoundException.class,
                () -> itemManagerUtil.getItemIfExist(1L,chapterRepository));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(chapterRepository, times(1)).findById(eq(1L));
    }

    @Test
    void shouldReturnListOfItems_Using_ItemRepository() {
        // given
        List<Item> items = Arrays.asList(new Item() {
            @Override
            public Long getId() {
                return super.getId();
            }
        });
        //when
        doReturn(items).when(itemRepository).findAll();
        //then
        List<Item> itemListResult = itemManagerUtil.getListOfItems(itemRepository);
        // asserts
        Assertions.assertEquals(itemListResult.size(),items.size());
        verify(itemRepository, times(1)).findAll();

    }
    @Test
    void shouldReturnListOfEpisodes_Using_EpisodeRepository() {
        // given
        List<Episode> episodes = Arrays.asList(new Episode());
        //when
        doReturn(episodes).when(episodeRepository).findAll();
        //then
        List<Episode> episodeListResult = itemManagerUtil.getListOfItems(episodeRepository);
        // asserts
        Assertions.assertEquals(episodeListResult.size(),episodes.size());
        verify(episodeRepository, times(1)).findAll();

    }
    @Test
    void shouldReturnListOfChapters_Using_ChapterRepository() {
        // given
        List<Chapter> chapters = Arrays.asList(new Chapter());

        //when
        doReturn(chapters).when(chapterRepository).findAll();

        //then
        List<Chapter> chapterListResult = itemManagerUtil.getListOfItems(chapterRepository);

        // asserts
        Assertions.assertEquals(chapterListResult.size(),chapters.size());
        verify(chapterRepository, times(1)).findAll();

    }
    @Test
    void shouldCovertItemListToItemDtoList() {
        // given
        List<Item> items = Arrays.asList(new Item() {
            @Override
            public Long getId() {
                return super.getId();
            }
        });
        // then
        List<ItemDto> itemsDto = itemManagerUtil.fromItemToItemDto(items,new ItemDto());

        // asserts
        Assertions.assertEquals(itemsDto.size(),items.size());

    }
    @Test
    void shouldCovertEpisodeListToEpisodeDtoList() {
        // given
        List<Episode> episodes = Arrays.asList(new Episode());
        // then
        List<EpisodeDto> episodesDto = itemManagerUtil.fromItemToItemDto(episodes,new EpisodeDto());

        // asserts
        Assertions.assertEquals(episodesDto.size(),episodes.size());

    }
    @Test
    void shouldCovertChapterListToChapterDtoList() {
        // given
        List<Chapter> chapters = Arrays.asList(new Chapter());
        // then
        List<ChapterDto> chaptersDto = itemManagerUtil.fromItemToItemDto(chapters,new ChapterDto());

        // asserts
        Assertions.assertEquals(chaptersDto.size(),chapters.size());

    }


    @Test
    void shouldCreateItemFromItemForm() {
        // given
        Item item = new Item(){
            @Override
            public Long getId() {
                return super.getId();
            }
        };
        ItemForm itemForm = new ItemForm();
        itemForm.setPath("Any Path");
        ItemDetailsForm itemDetailsForm = utilCreateItemDetailsForm();
        itemForm.setItemDetailsForm(itemDetailsForm);

        // then
        Item resultItem = itemManagerUtil.createItem(item,itemForm);

        //asserts
        utilCompareEachItemFormField(resultItem,itemForm);
        utilCompareEachTitleDetailsFormField(resultItem.getItemDetails(),itemForm.getItemDetailsForm());

    }
    @Test
    void shouldCreateEpisodeFromEpisodeForm() {
        // given
        Episode episode = new Episode();
        EpisodeForm episodeForm = new EpisodeForm();
        episodeForm.setPath("Any Path");
        ItemDetailsForm itemDetailsForm = utilCreateItemDetailsForm();
        episodeForm.setItemDetailsForm(itemDetailsForm);

        // then
        Episode resultEpisode = itemManagerUtil.createItem(episode,episodeForm);

        //asserts
        utilCompareEachItemFormField(resultEpisode,episodeForm);
        utilCompareEachTitleDetailsFormField(resultEpisode.getItemDetails(),episodeForm.getItemDetailsForm());

    }
    @Test
    void shouldCreateChapterFromChapterForm() {
        // given
        Chapter chapter = new Chapter();
        ChapterForm chapterForm = new ChapterForm();
        chapterForm.setPath("Any Path");
        ItemDetailsForm itemDetailsForm = utilCreateItemDetailsForm();
        chapterForm.setItemDetailsForm(itemDetailsForm);

        // then
        Chapter resultChapter = itemManagerUtil.createItem(chapter,chapterForm);

        //asserts
        utilCompareEachItemFormField(resultChapter,chapterForm);
        utilCompareEachTitleDetailsFormField(resultChapter.getItemDetails(),chapterForm.getItemDetailsForm());

    }
    private ItemDetailsForm utilCreateItemDetailsForm(){
        ItemDetailsForm itemDetailsForm = new ItemDetailsForm();
        itemDetailsForm.setName("Any Name");
        itemDetailsForm.setOfficialName("Any Official Name");
        itemDetailsForm.setOriginalName("Any Original Name");
        itemDetailsForm.setSlug("Any slug");
        itemDetailsForm.setReleaseDate(new Date());
        return itemDetailsForm;
    }

    private void utilCompareEachItemFormField(Item item, ItemForm itemForm){
        Assertions.assertEquals(item.getPath(),itemForm.getPath());


    }
    private void utilCompareEachTitleDetailsFormField(ItemDetails itemDetails, ItemDetailsForm itemDetailsForm){
        Assertions.assertEquals(itemDetails.getSlug(),itemDetailsForm.getSlug());
        Assertions.assertEquals(itemDetails.getName(),itemDetailsForm.getName());
        Assertions.assertEquals(itemDetails.getOfficialName(),itemDetailsForm.getOfficialName());
        Assertions.assertEquals(itemDetails.getOriginalName(),itemDetailsForm.getOriginalName());

    }

}