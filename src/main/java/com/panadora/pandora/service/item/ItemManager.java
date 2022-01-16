package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.*;
import com.panadora.pandora.controller.form.ItemForm;

import java.util.List;

public interface ItemManager<T extends ItemDto, E extends ItemForm> {
    public List<T> listItems();
    public T getItem(String id);
    public void deleteItem(String id);
    public T addItem(E itemForm);
}
