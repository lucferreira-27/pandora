package com.panadora.pandora.controller;

import com.panadora.pandora.controller.dtos.*;
import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.service.item.ItemManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemManagerImpl itemManager;

    @GetMapping("/items")
    public ResponseEntity listItems(){

        List<ItemDto> listTitlesDtos = itemManager.listItems();
        return ResponseEntity.ok().body(listTitlesDtos);

    }
    @GetMapping("/items/{id}")
    public ResponseEntity getItem(@PathVariable String id){
        ItemDto itemDto = itemManager.getItem(id);
        return ResponseEntity.ok().body(itemDto);

    }
    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(@PathVariable String id){
        itemManager.deleteItem(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping(value ="/items",consumes="application/json")
    public ResponseEntity addTitle(@RequestBody ItemForm itemForm){
        ItemDto itemDto = itemManager.addItem(itemForm);
        if(itemDto != null){
            return  ResponseEntity.created(URI.create("/items/" + itemDto.getId())).build();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
}
