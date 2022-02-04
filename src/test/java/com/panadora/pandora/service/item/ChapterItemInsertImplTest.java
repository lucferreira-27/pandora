package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.form.ChapterForm;
import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.repository.item.ItemRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import com.panadora.pandora.service.title.TitleManagerUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ChapterItemInsertImplTest {

    @Mock
    private TitleRepository titleRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private TitleManagerUtil titleManagerUtil;
    @InjectMocks
    @Spy
    private ChapterItemInsertImpl chapterItemInsert;
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldAddChapterOnMangaAndSave() {
        //given
        Manga manga = new Manga();
        Chapter chapter = new Chapter();
        //when
        doNothing().when(chapterItemInsert).saveAndFlush(chapter);
        //then
        chapterItemInsert.addItemOnTitleAndSave(manga,chapter);

        //assert
        Assertions.assertThat(chapter.getManga()).isEqualTo(manga);
        verify(chapterItemInsert, times(1)).saveAndFlush(chapter);

    }
    @Test
    void shouldSaveAndFlush_Chapter(){
        // given
        Chapter chapter = new Chapter();

        //when
        when(itemRepository.saveAndFlush(eq(chapter))).thenReturn(chapter);

        //then
        chapterItemInsert.saveAndFlush(chapter);

        //assert
        verify(chapterItemInsert,times(1)).saveAndFlush(chapter);
    }
    @Test
    void shouldInsertChapterOnTitle(){
        //given
        Chapter chapter = new Chapter();
        Manga manga = new Manga();
        ChapterForm chapterForm = new ChapterForm();
        chapterForm.setTitleId(1L);
        //when
        doReturn(manga).when(titleManagerUtil).getTitleIfExist(1L,titleRepository);

        doNothing()
                .when(chapterItemInsert)
                .addItemOnTitleAndSave(
                        manga,
                        chapter
                );
        //then
        chapterItemInsert.insertItemOnTitle(chapter,chapterForm);

        //assert
        verify(titleManagerUtil,times(1)).getTitleIfExist(1L,titleRepository);
        verify(chapterItemInsert,times(1)).addItemOnTitleAndSave(manga,chapter);

    }

    @Test
    void shouldThrowTitleNotFoundException_OnInsertChapterOnTitle(){
        //given
        String expectExceptionMsg = "Resource Not Found";
        Chapter chapter = new Chapter();
        Manga manga = new Manga();
        ChapterForm chapterForm = new ChapterForm();
        chapterForm.setTitleId(0L);
        //when
        when(titleManagerUtil.getTitleIfExist(0L,titleRepository)).thenThrow(new TitleNotFoundException());

        //then
        TitleNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> chapterItemInsert.insertItemOnTitle(chapter,chapterForm));

        //assert
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectExceptionMsg);
        verify(titleManagerUtil,times(1)).getTitleIfExist(0L,titleRepository);
        verify(chapterItemInsert,never()).addItemOnTitleAndSave(manga,chapter);

    }
}