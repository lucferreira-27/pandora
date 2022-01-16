package com.panadora.pandora.service.item;

import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.model.entities.collection.title.Manga;
import com.panadora.pandora.service.item.ItemInsertAbstract;
import org.springframework.stereotype.Service;

@Service
public class ChapterItemInsertImpl extends ItemInsertAbstract<Manga, Chapter> {


    @Override
    protected void addItemOnTitleAndSave(Manga title, Chapter item) {
        item.setManga(title);
        saveAndFlush(item);
    }
}
