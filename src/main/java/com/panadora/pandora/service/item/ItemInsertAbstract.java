package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.repository.item.ItemRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class ItemInsertAbstract<T extends Title,E extends Item> {

    @Autowired
    protected ItemRepository itemRepository;
    @Autowired
    protected TitleRepository titleRepository;

    public void insertItemOnTitle(E item,ItemForm itemForm){
        T title = getIfTitleExist(itemForm.getTitleId());
        addItemOnTitleAndSave(title,item);

    }
    private T getIfTitleExist(Long id){
        Optional<Title> optional = titleRepository.findById(id);
        if (optional.isPresent()) {
            Title title = optional.get();
            return (T) title;
        }
        throw new TitleNotFoundException();
    }
    protected void saveAndFlush(E item){
        itemRepository.saveAndFlush(item);
    }
    protected  abstract void addItemOnTitleAndSave(T title, E item);
}
