package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.form.ItemDetailsForm;
import com.panadora.pandora.controller.dtos.ItemDto;
import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.item.ItemDetails;
import com.panadora.pandora.repository.item.ItemBaseRepository;
import com.panadora.pandora.service.exceptions.ItemNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemManagerUtil {
    public static <T extends Item> T getItemIfExist(Long id, ItemBaseRepository<T> repository){
        Optional<T> optional = repository.findById(id);
        if (optional.isPresent()) {
            T item = optional.get();
            return item;
        }
        throw new ItemNotFoundException();
    }
    public static <T extends Item> List<T> getListOfItems(ItemBaseRepository<T> repository){
        List<T> items = repository.findAll();
        return items;
    }
    public static <T extends Item,E extends ItemDto> List<E> fromItemToItemDto(List<T> list, E ItemDto){
        Stream<ItemDto> streamItemsDto = list.stream().map(ItemDto::toDto);
        List<ItemDto> itemsDto = streamItemsDto.collect(Collectors.toList());
        return (List<E>) itemsDto;
    }
    public static <T extends  Item, E extends ItemForm> T createItem(T item, E itemForm){
        ItemDetailsForm itemDetailsForm = itemForm.getItemDetailsForm();
        ItemDetails itemDetails = ItemDetails.fromForm(item, itemDetailsForm);
        item.setItemDetails(itemDetails);
        item.setPath(itemForm.getPath());
        return item;
    }

}
