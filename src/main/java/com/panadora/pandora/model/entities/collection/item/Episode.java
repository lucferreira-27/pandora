package com.panadora.pandora.model.entities.collection.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panadora.pandora.model.entities.collection.title.Anime;
import com.panadora.pandora.model.entities.player.PlayerState;

import javax.persistence.*;

@Entity
public class Episode extends Item {
    @ManyToOne(cascade={CascadeType.PERSIST})
    @JsonIgnore
    private Anime anime;
    private Long duration;
    @Column(name = "image_thumbnail", columnDefinition="BLOB")
    private byte[] imageThumbnailBytes;
    private String imageThumbnailUrl;
    private String description;
    private Long bytesLength;

    @OneToOne
    private PlayerState playerState;

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public byte[] getImageThumbnailBytes() {
        return imageThumbnailBytes;
    }

    public void setImageThumbnailBytes(byte[] imageThumbnail) {
        this.imageThumbnailBytes = imageThumbnail;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getBytesLength() {
        return bytesLength;
    }

    public void setBytesLength(Long bytesLength) {
        this.bytesLength = bytesLength;
    }

    @Override
    public ItemDetails getItemDetails() {
        return super.getItemDetails();
    }

    @Override
    public void setItemDetails(ItemDetails itemDetails) {
        super.setItemDetails(itemDetails);
    }

    @Override
    public String getPath() {
        return super.getPath();
    }

    @Override
    public void setPath(String path) {
        super.setPath(path);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
