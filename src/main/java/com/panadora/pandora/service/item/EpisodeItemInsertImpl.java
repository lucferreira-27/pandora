package com.panadora.pandora.service.item;

import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.title.Anime;
import org.springframework.stereotype.Service;

@Service
public class EpisodeItemInsertImpl extends ItemInsertAbstract<Anime, Episode> {

    @Override
    protected void addItemOnTitleAndSave(Anime title, Episode item) {
        item.setAnime(title);
        saveAndFlush(item);
    }


}
