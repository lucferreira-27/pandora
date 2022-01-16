package com.panadora.pandora.service.title;

import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.title.Anime;
import org.springframework.stereotype.Service;

@Service
public class AnimeTitleInsertImpl extends TitleInsertAbstract<Collection, Anime>{
    @Override
    protected void addTitleOnCollectionAndSave(Collection collection, Anime title) {
        title.getCollections().add(collection);
        saveAndFlush(title);
    }
}
