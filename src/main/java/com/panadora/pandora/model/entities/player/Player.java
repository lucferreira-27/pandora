package com.panadora.pandora.model.entities.player;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "player")
    private List<PlayerState> playerStates;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }
}
