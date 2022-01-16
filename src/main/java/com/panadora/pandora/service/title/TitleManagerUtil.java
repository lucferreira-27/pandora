package com.panadora.pandora.service.title;

import com.panadora.pandora.controller.dtos.ItemDto;
import com.panadora.pandora.controller.dtos.TitleDto;
import com.panadora.pandora.controller.form.ItemDetailsForm;
import com.panadora.pandora.controller.form.ItemForm;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.controller.form.TitleForm;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.item.ItemDetails;
import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;
import com.panadora.pandora.repository.item.ItemBaseRepository;
import com.panadora.pandora.repository.title.TitleBaseRepository;
import com.panadora.pandora.service.exceptions.TitleNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TitleManagerUtil {
    public static <T extends Title> T getTitleIfExist(Long id, TitleBaseRepository<T> repository){
        Optional<T> optional = repository.findById(id);
        if (optional.isPresent()) {
            T title = optional.get();
            return title;
        }
        throw new TitleNotFoundException();
    }
    public static <T extends Title> List<T> getListOfTitles(TitleBaseRepository<T> repository){
        List<T> titles = repository.findAll();
        return titles;
    }
    public static <T extends Title,E extends TitleDto> List<E> fromTitleToTitleDto(List<T> list, E ItemDto){
        Stream<TitleDto> streamItemsDto = list.stream().map(TitleDto::toDto);
        List<TitleDto> itemsDto = streamItemsDto.collect(Collectors.toList());
        return (List<E>) itemsDto;
    }
    public static <T extends  Title, E extends TitleForm> T createTitle(T title, E titleForm){
        TitleDetailsForm itemDetailsForm = titleForm.getTitleDetailsForm();
        TitleDetails titleDetails = TitleDetails.fromForm(title, itemDetailsForm);
        title.setTitleDetails(titleDetails);
        title.setPath(titleForm.getPath());
        title.setTitleType(titleForm.getTitleType());
        return title;
    }
}
