package com.panadora.pandora.model.entities.player;

import javax.persistence.*;
import java.util.List;

@Entity
public class Reader {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "reader")
    private List<ReaderState> readerStates;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
