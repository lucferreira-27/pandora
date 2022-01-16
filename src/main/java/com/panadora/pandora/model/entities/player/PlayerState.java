package com.panadora.pandora.model.entities.player;

import com.panadora.pandora.model.entities.collection.item.Episode;

import javax.persistence.*;

@Entity
public class PlayerState {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Episode episode;
    @ManyToOne
    private Player player;
    private Long currentPosition;
    private Long totalLength;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
    }

    public Long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Long currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
