package com.panadora.pandora.controller.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panadora.pandora.model.entities.collection.item.Episode;
import com.panadora.pandora.model.entities.collection.item.Item;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.player.PlayerState;

import javax.persistence.Column;

public class EpisodeDto extends ItemDto{

    private String description;
    private Long bytesLength;
    private Long duration;
    private String imageThumbnailUrl;
    private PlayerState playerState;

    public EpisodeDto(Episode item) {
        super(item);
    }
    public EpisodeDto(){

    }

    public static EpisodeDto toDto(Episode episode) {
        EpisodeDto episodeDto = new EpisodeDto();

        episodeDto.setItemDetails(episode.getItemDetails());
        episodeDto.setPath(episode.getPath());
        episodeDto.setId(episode.getId());
        episodeDto.setDescription(episode.getDescription());
        episodeDto.setBytesLength(episode.getBytesLength());
        episodeDto.setImageThumbnailUrl(episode.getImageThumbnailUrl());

        return episodeDto;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBytesLength() {
        return bytesLength;
    }

    public void setBytesLength(Long bytesLength) {
        this.bytesLength = bytesLength;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}
