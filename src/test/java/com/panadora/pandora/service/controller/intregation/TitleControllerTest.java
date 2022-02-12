package com.panadora.pandora.service.controller.intregation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.dtos.TitleItemsDto;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.model.entities.collection.title.TitleType;
import com.panadora.pandora.service.title.TitleManagerImpl;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TitleControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TitleManagerImpl titleManager;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnListOfTitles() throws Exception {
        //given
        TitleDto titleDto = createTitleDto();
        List<TitleDto> titlesDto = Arrays.asList(titleDto);

        //when
        when(titleManager.listTitles()).thenReturn(titlesDto);

        //then
        ResultActions resultActions = mvc.perform(
                get("/titles/")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        //assert
        resultActions
                .andExpect(status().isOk()
                ).andExpect(jsonPath("$[0].id", Matchers.is(titleDto.getId().intValue()))
                ).andExpect(jsonPath("$[0].path", Matchers.is(titleDto.getPath()))
                ).andExpect(jsonPath("$[0].titleDetails.id", Matchers.is(titleDto.getTitleDetails().getId().intValue()))
                ).andExpect(jsonPath("$[0].titleDetails.name", Matchers.is(titleDto.getTitleDetails().getName()))
                ).andExpect(jsonPath("$[0].titleDetails.score", Matchers.is(titleDto.getTitleDetails().getScore()))
                ).andExpect(jsonPath("$[0].titleDetails.synopsis", Matchers.is(titleDto.getTitleDetails().getSynopsis()))
                ).andExpect(jsonPath("$[0].titleDetails.releaseDate", Matchers.is(dateToMilliseconds(titleDto.getTitleDetails().getReleaseDate())))
                ).andExpect(jsonPath("$[0].titleDetails.finishedDate", Matchers.is(dateToMilliseconds(titleDto.getTitleDetails().getFinishedDate())))
                );

        verify(titleManager, times(1)).listTitles();


    }
    @Test
    public void shouldReturnTitleById() throws Exception  {
        //given
        TitleItemsDto titleItemsDto = createTitleItemsDto();
        when(titleManager.getTitle(String.valueOf(titleItemsDto.getId()))).thenReturn(titleItemsDto);

        //when
        ResultActions resultActions = mvc.perform(
                get("/titles/" + titleItemsDto.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        //assert
        resultActions
                .andExpect(status().isOk()
                ).andExpect(jsonPath("$.id", Matchers.is(titleItemsDto.getId().intValue()))
                ).andExpect(jsonPath("$.path", Matchers.is(titleItemsDto.getPath()))
                ).andExpect(jsonPath("$.titleDetails.id", Matchers.is(titleItemsDto.getTitleDetails().getId().intValue()))
                ).andExpect(jsonPath("$.titleDetails.name", Matchers.is(titleItemsDto.getTitleDetails().getName()))
                ).andExpect(jsonPath("$.titleDetails.score", Matchers.is(titleItemsDto.getTitleDetails().getScore()))
                ).andExpect(jsonPath("$.titleDetails.synopsis", Matchers.is(titleItemsDto.getTitleDetails().getSynopsis()))
                ).andExpect(jsonPath("$.titleDetails.releaseDate", Matchers.is(dateToMilliseconds(titleItemsDto.getTitleDetails().getReleaseDate())))
                ).andExpect(jsonPath("$.titleDetails.finishedDate", Matchers.is(dateToMilliseconds(titleItemsDto.getTitleDetails().getFinishedDate())))
                );

        verify(titleManager, times(1)).getTitle(String.valueOf(titleItemsDto.getId()));

    }
    @Test
    public void shouldReturn_BadRequest_On_CreateNewTitle() throws Exception{
        //given
        TitleForm titleForm = createTitleForm();
        TitleDto titleDto = createTitleDto();
        when(titleManager.addTitle(titleForm)).thenReturn(titleDto);

        //when
        ResultActions resultActions = mvc.perform(
                post("/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(titleDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        //assert
        resultActions.andExpect(status().isBadRequest());

        verify(titleManager, times(1)).addTitle(titleForm);

    }

    private String asJsonString(TitleDto titleDto) throws JsonProcessingException {

            return new ObjectMapper().writeValueAsString(titleDto);

    }

    private Long dateToMilliseconds(Date date) {
        Long milliseconds = date.getTime();
        return milliseconds;
    }

    private TitleItemsDto createTitleItemsDto(){
        TitleItemsDto titleItemsDto = new TitleItemsDto();
        titleItemsDto.setId(1L);
        titleItemsDto.setPath("/path/");
        titleItemsDto.setTitleDetails(createTitleDetails());
        return titleItemsDto;
    }

    private TitleForm createTitleForm(){
        TitleForm titleForm = new TitleForm();
        return titleForm;
    }

    private TitleDto createTitleDto() {
        Title title = new Title() {

            @Override
            public List<? extends Item> getItems() {
                return null;
            }
        };
        title.setTitleType(TitleType.ANIME);
        title.setPath("path");
        title.setId(1L);
        title.setTitleDetails(createTitleDetails());

        return TitleDto.toDto(title);

    }

    private TitleDetails createTitleDetails() {
        TitleDetails titleDetails = new TitleDetails();
        titleDetails.setId(1L);
        titleDetails.setFinishedDate(new Date());
        titleDetails.setReleaseDate(new Date());
        titleDetails.setName("TEST Any Name");
        titleDetails.setScore(99999.00);
        titleDetails.setSynopsis("TEST Any Text");
        return titleDetails;
    }
}
