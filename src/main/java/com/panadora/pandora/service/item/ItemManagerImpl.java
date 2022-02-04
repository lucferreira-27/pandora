package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.*;
import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.model.entities.collection.item.*;
import com.panadora.pandora.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


@Service
public class ItemManagerImpl implements ItemManager<ItemDto, ItemForm>{

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private  ItemManagerUtil itemManagerUtil;

    @Override
    public List<ItemDto> listItems() {
        List<Item> items = itemManagerUtil.getListOfItems(itemRepository);
        List<ItemDto> ItemsDto = itemManagerUtil.fromItemToItemDto(items,new ItemDto());
        return ItemsDto;

    }

    @Override
    public ItemDto getItem(String id) {

        Item item = itemManagerUtil.getItemIfExist(Long.valueOf(id), itemRepository);
        return ItemDto.toDto(item);

    }

    @Override
    public void deleteItem(String id) {
        Item item = itemManagerUtil.getItemIfExist(Long.valueOf(id), itemRepository);
        itemRepository.delete(item);
    }

    @Override
    public ItemDto addItem(ItemForm itemForm)  {
        throw new NotImplementedException();
    }



    


}
