package com.panadora.pandora.service.item;
import com.panadora.pandora.controller.dtos.EpisodeDto;
import com.panadora.pandora.controller.form.EpisodeForm;
import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.repository.item.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeItemManagerImpl implements ItemManager<EpisodeDto, EpisodeForm> {


    private final EpisodeRepository episodeRepository;
    private final EpisodeItemInsertImpl episodeItemInsert;

    public EpisodeItemManagerImpl(EpisodeRepository episodeRepository, EpisodeItemInsertImpl episodeItemInsert) {
        this.episodeRepository = episodeRepository;
        this.episodeItemInsert = episodeItemInsert;
    }

    @Override
    public List<EpisodeDto> listItems() {
        List<Episode> episodes = ItemManagerUtil.getListOfItems(episodeRepository);
        List<EpisodeDto> episodesDto = ItemManagerUtil.fromItemToItemDto(episodes,new EpisodeDto());
        return episodesDto;
    }

    @Override
    public EpisodeDto getItem(String id) {
        Episode episode = ItemManagerUtil.getItemIfExist(Long.valueOf(id), episodeRepository);
        return (EpisodeDto) new EpisodeDto().toDto(episode);
    }

    @Override
    public void deleteItem(String id) {
        Episode episode = ItemManagerUtil.getItemIfExist(Long.valueOf(id), episodeRepository);
        episodeRepository.delete(episode);
    }


    @Override
    public EpisodeDto addItem(EpisodeForm itemForm) {
        Episode newEpisode = new Episode();
        Episode episode = createItem(newEpisode,itemForm);
        episodeItemInsert.insertItemOnTitle(episode,itemForm);
        return  (EpisodeDto) new EpisodeDto().toDto(episode);
    }
    private Episode createItem(Episode newEpisode, EpisodeForm episodeForm){
        Episode episode = ItemManagerUtil.createItem(newEpisode,episodeForm);
        episode.setDescription(episodeForm.getDescription());
        return episode;
    }
}
