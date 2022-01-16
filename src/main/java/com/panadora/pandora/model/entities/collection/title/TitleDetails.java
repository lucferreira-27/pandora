package com.panadora.pandora.model.entities.collection.title;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panadora.pandora.controller.form.TitleDetailsForm;
import com.panadora.pandora.model.entities.people.Staff;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class TitleDetails {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToOne
    private Title title;
    @ManyToMany
    private List<Staff> staffs;
    private String name;
    private Double score;
    private String synopsis;
    @Column(name = "image_cover", columnDefinition="BLOB")
    @JsonIgnore
    private byte[] imageCover;
    @Column(name = "image_thumbnail", columnDefinition="BLOB")
    @JsonIgnore
    private byte[] imageThumbnail;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Temporal(TemporalType.DATE)
    private Date finishedDate;

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public byte[] getImageCover() {
        return imageCover;
    }

    public void setImageCover(byte[] imageCover) {
        this.imageCover = imageCover;
    }

    public byte[] getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(byte[] imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public static TitleDetails fromForm(Title title,TitleDetailsForm titleDetailsForm){
        TitleDetails titleDetails = new TitleDetails();
        titleDetails.setTitle(title);
        titleDetails.setName(titleDetailsForm.getName());
        titleDetails.setFinishedDate(titleDetailsForm.getFinishedDate());
        titleDetails.setReleaseDate(titleDetailsForm.getReleaseDate());
        titleDetails.setScore(titleDetailsForm.getScore());
        titleDetails.setSynopsis(titleDetailsForm.getSynopsis());
        return titleDetails;
    }
}
