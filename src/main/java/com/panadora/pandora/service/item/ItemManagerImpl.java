package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.*;
import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.model.entities.collection.item.*;
import com.panadora.pandora.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemManagerImpl implements ItemManager<ItemDto, ItemForm>{

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private final EpisodeItemInsertImpl episodeItemInsert;
    @Autowired
    private final ChapterItemInsertImpl chapterItemInsert;

    public ItemManagerImpl(EpisodeItemInsertImpl episodeItemInsert, ChapterItemInsertImpl chapterItemInsert) {
        this.episodeItemInsert = episodeItemInsert;
        this.chapterItemInsert = chapterItemInsert;
    }
    @Override
    public List<ItemDto> listItems() {
        List<Item> items = ItemManagerUtil.getListOfItems(itemRepository);
        List<ItemDto> ItemsDto = ItemManagerUtil.fromItemToItemDto(items,new ItemDto());
        return ItemsDto;

    }

    @Override
    public ItemDto getItem(String id) {

        Item item = ItemManagerUtil.getItemIfExist(Long.valueOf(id), itemRepository);
        return new ItemDto(item);

    }

    @Override
    public void deleteItem(String id) {
        Item item = ItemManagerUtil.getItemIfExist(Long.valueOf(id), itemRepository);
        itemRepository.delete(item);
    }

    @Override
    public ItemDto addItem(ItemForm itemForm) {
        return  null;
    }



    


}
