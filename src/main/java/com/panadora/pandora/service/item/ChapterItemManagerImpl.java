package com.panadora.pandora.service.item;
import com.panadora.pandora.controller.dtos.ChapterDto;
import com.panadora.pandora.controller.form.ChapterForm;
import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.repository.item.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterItemManagerImpl implements ItemManager<ChapterDto, ChapterForm> {


    private final ChapterRepository chapterRepository;
    private final ChapterItemInsertImpl chapterItemInsert;
    private final ItemManagerUtil itemManagerUtil;
    public ChapterItemManagerImpl(ChapterRepository chapterRepository, ChapterItemInsertImpl chapterItemInsert, ItemManagerUtil itemManagerUtil) {
        this.chapterRepository = chapterRepository;
        this.chapterItemInsert = chapterItemInsert;
        this.itemManagerUtil = itemManagerUtil;
    }

    @Override
    public List<ChapterDto> listItems() {
        List<Chapter> chapters = itemManagerUtil.getListOfItems(chapterRepository);
        List<ChapterDto> chapterDto = itemManagerUtil.fromItemToItemDto(chapters,new ChapterDto());
        return chapterDto;
    }

    @Override
    public ChapterDto getItem(String id) {
        Chapter chapter = itemManagerUtil.getItemIfExist(Long.valueOf(id), chapterRepository);
        return (ChapterDto) new ChapterDto().toDto(chapter);
    }

    @Override
    public void deleteItem(String id) {
        Chapter chapter = itemManagerUtil.getItemIfExist(Long.valueOf(id), chapterRepository);
        chapterRepository.delete(chapter);
    }

    @Override
    public ChapterDto addItem(ChapterForm itemForm) {
        Chapter newChapter = new Chapter();
        Chapter chapter = createItem(newChapter,itemForm);
        chapterItemInsert.insertItemOnTitle(chapter,itemForm);
        return (ChapterDto) new ChapterDto().toDto(chapter);
    }

    private Chapter createItem(Chapter newChapter, ChapterForm chapterForm){
        Chapter chapter = itemManagerUtil.createItem(newChapter,chapterForm);
        chapter.setCustomPathCover(chapterForm.getCustomPathCover());
        return chapter;
    }
}
