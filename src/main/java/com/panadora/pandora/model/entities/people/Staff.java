package com.panadora.pandora.model.entities.people;

import com.panadora.pandora.model.entities.collection.title.Title;
import com.panadora.pandora.model.entities.collection.title.TitleDetails;

import javax.persistence.*;
import java.util.List;

@Entity
public class Staff {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    @ManyToMany(mappedBy = "staffs")
    private List<TitleDetails> titleDetails;
    @ManyToMany
    private List<People> peoples;

}
