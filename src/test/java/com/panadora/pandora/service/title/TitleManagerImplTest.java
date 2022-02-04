package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.title.TitleRepository;
import com.panadora.pandora.service.TitleImageDownloader;
import com.panadora.pandora.service.TitleImageLoader;

import com.panadora.pandora.service.exceptions.TitleNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TitleManagerImplTest {
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private TitleImageLoader titleImageLoader;
    @Mock
    private TitleManagerUtil titleManagerUtil;

    @InjectMocks
    private TitleManagerImpl titleManager;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTitlesList() {
        List<Title> titles = Arrays.asList(new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        });
        List<TitleDto> titlesDto = Arrays.asList(new TitleDto());

        doReturn(titles).when(titleManagerUtil).getListOfTitles(any(TitleRepository.class));
        doReturn(titlesDto).when(titleManagerUtil).fromTitleToTitleDto(eq(titles), any(TitleDto.class));

        Assertions.assertNotNull(titleManager.listTitles());

        verify(titleManagerUtil, times(1)).getListOfTitles(any(TitleRepository.class));
        verify(titleManagerUtil, times(1)).fromTitleToTitleDto(eq(titles), any(TitleDto.class));


    }

    @Test
    public void shouldReturnTitle() {

        // given
        Title expectTitle = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }

        };
        expectTitle.setId(1L);

        //when
        doReturn(expectTitle).when(titleManagerUtil).getTitleIfExist(eq(1L), any(TitleRepository.class));
        MockedStatic<TitleDto> mb = Mockito.mockStatic(TitleDto.class);
        mb.when(() -> TitleDto.toDto(any(Title.class)))
                .thenAnswer(i -> new TitleItemsDto((Title) i.getArguments()[0]));

        // assert
        TitleItemsDto resultTitle = titleManager.getTitle("1");
        Assertions.assertNotNull(resultTitle);
        Assertions.assertEquals(resultTitle.getId(), expectTitle.getId());
        verify(titleManagerUtil, times(1)).getTitleIfExist(eq(1L), any(TitleRepository.class));
        mb.verify(() -> TitleDto.toDto(any(Title.class)), times(1));

        mb.close();

    }

    @Test
    public void shouldDeleteTitle() {

        //given
        Title title = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };

        //when
        doReturn(title).when(titleManagerUtil).getTitleIfExist(eq(1L), any(TitleRepository.class));

        //assert
        titleManager.deleteTitle("1");
        verify(titleRepository).delete(eq(title));
    }

    @Test(expected = NotImplementedException.class)
    public void shouldThrowNotImplementedException_OnAddTitle() {
        //given
        TitleForm titleForm = new TitleForm();
        //when
        titleManager.addTitle(titleForm);

        //assert
        Assertions.fail();
    }


    @Test
    public void shouldGetTitleImage() {
        // given
        Title title = new Title() {
            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };
        title.setTitleDetails(new TitleDetails());
        InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());

        // when
        doReturn(Optional.of(title)).when(titleRepository).findById(1L);
        when(titleImageLoader.load(anyString(), any(TitleDetails.class))).thenReturn(anyInputStream);

        InputStream inputStream = titleManager.getTitleImage("image", "1");

        //assert
         verify(titleRepository, times(1)).findById(eq(1L));
        verify(titleImageLoader, times(1)).load(anyString(), any(TitleDetails.class));
    }

    @Test
    public void shouldThrowTitleNotFoundException_OnGetTitleImage() {
        //given
        String expectExceptionMsg = "Resource Not Found";

        // when
        doReturn(Optional.empty()).when(titleRepository).findById(anyLong());

        TitleNotFoundException exception = Assertions.assertThrows(
                TitleNotFoundException.class,
                () -> titleManager.getTitleImage("image", "1"));
        //assert
        Assertions.assertEquals(expectExceptionMsg, exception.getMessage());
        verify(titleImageLoader, never()).load(anyString(), any(TitleDetails.class));


    }
}