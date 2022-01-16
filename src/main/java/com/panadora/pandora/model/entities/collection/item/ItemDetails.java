package com.panadora.pandora.model.entities.collection.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panadora.pandora.controller.form.ItemDetailsForm;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ItemDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade=CascadeType.ALL)
    @JsonIgnore
    private Item item;
    protected String officialName;
    protected String originalName;
    protected String slug;
    protected String name;
    @Temporal(TemporalType.DATE)
    protected Date releaseDate;

    public static ItemDetails fromForm(Item item, ItemDetailsForm itemDetailsForm) {
        ItemDetails itemDetails = new ItemDetails();
        
        itemDetails.setItem(item);
        itemDetails.setName(itemDetailsForm.getName());
        itemDetails.setOfficialName(itemDetailsForm.getOfficialName());
        itemDetails.setOriginalName(itemDetailsForm.getOriginalName());
        itemDetails.setReleaseDate(itemDetailsForm.getReleaseDate());
        itemDetails.setSlug(itemDetailsForm.getSlug());

        return itemDetails;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Date getReleaseDate() {
        return releaseDate;
    }

    protected void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
