package com.panadora.pandora.service.title;

import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.title.Manga;
import org.springframework.stereotype.Service;

@Service
public class MangaTitleInsertImpl extends TitleInsertAbstract<Collection, Manga>{
    @Override
    protected void addTitleOnCollectionAndSave(Collection collection, Manga title) {
        title.getCollections().add(collection);
        saveAndFlush(title);
    }
}
