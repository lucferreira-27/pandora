package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.Collection;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.repository.CollectionRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.CollectionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class  TitleInsertAbstract<T extends Collection,E extends Title> {
    @Autowired
    protected TitleRepository titleRepository;
    @Autowired
    protected CollectionRepository collectionRepository;

    public <C extends TitleForm> void  insertTitleOnCollection(E title, C titleForm){
        T collection = getIfCollectionExist(titleForm.getCollectionId());
        addTitleOnCollectionAndSave(collection,title);

    }
    private T getIfCollectionExist(Long id){
        Optional<Collection> optional = collectionRepository.findById(id);
        if (optional.isPresent()) {
            Collection collection = optional.get();
            return (T) collection;
        }
        throw new CollectionNotFoundException();
    }
    protected void saveAndFlush(E collection){
        titleRepository.saveAndFlush(collection);
    }
    protected  abstract void addTitleOnCollectionAndSave(T collection, E title);
}
