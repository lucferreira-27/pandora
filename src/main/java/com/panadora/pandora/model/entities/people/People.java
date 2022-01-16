package com.panadora.pandora.model.entities.people;

import javax.persistence.*;
import java.util.List;

@Entity
public class People {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    @ManyToMany(mappedBy="peoples")
    private List<Staff> staffs;

}
