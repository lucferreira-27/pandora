package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.ChapterDto;
import com.panadora.pandora.controller.dtos.ChapterDto;
import com.panadora.pandora.controller.dtos.ChapterDto;
import com.panadora.pandora.controller.form.ChapterForm;
import com.panadora.pandora.controller.form.ItemDetailsForm;
import com.panadora.pandora.model.entities.collection.item.*;
import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.repository.item.ChapterRepository;
import com.panadora.pandora.repository.item.ChapterRepository;
import com.panadora.pandora.repository.item.ChapterRepository;
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

class ChapterItemManagerImplTest {
    @Mock
    private ChapterRepository chapterRepository;
    @Mock
    private ChapterItemInsertImpl chapterItemInsert;;
    @Mock
    private ItemManagerUtil itemManagerUtil;
    @InjectMocks
    private ChapterItemManagerImpl chapterManager;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldReturnChaptersList() {
        List<Chapter> chapters = Arrays.asList(new Chapter());
        List<ChapterDto> chaptersDto = Arrays.asList(new ChapterDto());

        doReturn(chapters).when(itemManagerUtil).getListOfItems(any(ChapterRepository.class));
        doReturn(chaptersDto).when(itemManagerUtil).fromItemToItemDto(eq(chapters), any(ChapterDto.class));

        Assertions.assertNotNull(chapterManager.listItems());

        verify(itemManagerUtil, times(1)).getListOfItems(any(ChapterRepository.class));
        verify(itemManagerUtil, times(1)).fromItemToItemDto(eq(chapters), any(ChapterDto.class));
    }

    @Test
    public void shouldReturnChapter() {

        // given
        Chapter expectChapter = new Chapter();

        //when
        doReturn(expectChapter).when(itemManagerUtil).getItemIfExist(eq(1L), any(ChapterRepository.class));
        MockedStatic<ChapterDto> mb = Mockito.mockStatic(ChapterDto.class);
        mb.when(() -> ChapterDto.toDto(any(Chapter.class)))
                .thenAnswer(i -> new ChapterDto((Chapter) i.getArguments()[0]));

        //then
        ChapterDto resultItem = chapterManager.getItem("1");

        // assert
        Assertions.assertNotNull(resultItem);
        verify(itemManagerUtil, times(1)).getItemIfExist(eq(1L), any(ChapterRepository.class));
        mb.verify(() ->ChapterDto.toDto(any(Chapter.class)), times(1));

        mb.close();

    }
    @Test
    public void shouldDeleteChapter() {

        // given
        Chapter Chapter = new Chapter();

        //when
        doReturn(Chapter).when(itemManagerUtil).getItemIfExist(eq(1L), any(ChapterRepository.class));

        //then
        chapterManager.deleteItem("1");

        //assert
        verify(chapterRepository).delete(eq(Chapter));
    }

    @Test
    public void shouldAddChapter(){
        // given
        Chapter chapter = new Chapter();
        ItemDetails itemDetails = new ItemDetails();
        chapter.setItemDetails(itemDetails);
        ChapterForm chapterForm = new ChapterForm();
        ItemDetailsForm itemDetailsForm = new ItemDetailsForm();
        chapterForm.setItemDetailsForm(itemDetailsForm);
        // when

        doReturn(chapter).when(itemManagerUtil)
                .createItem(
                        any(Chapter.class),
                        eq(chapterForm)
                );
        doNothing().when(chapterItemInsert)
                .insertItemOnTitle(
                        eq(chapter),
                        eq(chapterForm)
                );
        //then
        ChapterDto resultChapter = chapterManager.addItem(chapterForm);

        // asserts
        Assertions.assertNotNull(resultChapter);

        verify(chapterItemInsert,times(1)).insertItemOnTitle(
                eq(chapter),
                eq(chapterForm)
        );
        verify(itemManagerUtil).createItem(
                any(Chapter.class),
                eq(chapterForm)
        );
    }
}