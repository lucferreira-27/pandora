package com.panadora.pandora.service.item;

import com.panadora.pandora.controller.dtos.ItemDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.repository.item.ItemRepository;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.exceptions.CollectionNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ItemManagerImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemManagerUtil itemManagerUtil;
    @InjectMocks
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnItemList() {
        List<Item> items = Arrays.asList(new Item(){
            @Override
            public Long getId() {
                return super.getId();
            }
        });
        List<ItemDto> itemsDto = Arrays.asList(new ItemDto());

        doReturn(items).when(itemManagerUtil).getListOfItems(any(ItemRepository.class));
        doReturn(itemsDto).when(itemManagerUtil).fromItemToItemDto(eq(items), any(ItemDto.class));

        Assertions.assertNotNull(itemManager.listItems());

        verify(itemManagerUtil, times(1)).getListOfItems(any(ItemRepository.class));
        verify(itemManagerUtil, times(1)).fromItemToItemDto(eq(items), any(ItemDto.class));


    }
    @Test
    public void shouldReturnItem() {

        // given
        Item expectItem = new Item() {
            @Override
            public Long getId() {
                return super.getId();
            }
        };

        //when
        doReturn(expectItem).when(itemManagerUtil).getItemIfExist(eq(1L), any(ItemRepository.class));
        MockedStatic<ItemDto> mb = Mockito.mockStatic(ItemDto.class);
        mb.when(() -> ItemDto.toDto(any(Item.class)))
                .thenAnswer(i -> new ItemDto((Item) i.getArguments()[0]));

        //then
        ItemDto resultItem = itemManager.getItem("1");

        // assert
        Assertions.assertNotNull(resultItem);
        verify(itemManagerUtil, times(1)).getItemIfExist(eq(1L), any(ItemRepository.class));
        mb.verify(() ->ItemDto.toDto(any(Item.class)), times(1));

        mb.close();

    }
    @Test
    public void shouldDeleteItem() {

        // given
        Item item = new Item() {
            @Override
            public Long getId() {
                return super.getId();
            }
        };

        //when
        doReturn(item).when(itemManagerUtil).getItemIfExist(eq(1L), any(ItemRepository.class));

        //then
        itemManager.deleteItem("1");

        //assert
        verify(itemRepository).delete(eq(item));
    }

    @Test
    public void shouldThrowNotImplementedException_OnAddItem() {
        //given
        ItemForm itemForm = new ItemForm();
        //assert
        Assertions.assertThrows(
                NotImplementedException.class,
                () -> itemManager.addItem(itemForm));

    }
}