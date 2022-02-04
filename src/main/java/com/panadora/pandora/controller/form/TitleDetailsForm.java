package com.panadora.pandora.controller.form;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class TitleDetailsForm {
    private String name;
    private Double score;
    private String synopsis;
    private String imageCoverUrl;
    private String imageThumbnailUrl;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date releaseDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date finishedDate;


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

    public String getImageCoverUrl() {
        return imageCoverUrl;
    }

    public void setImageCoverUrl(String imageCoverUrl) {
        this.imageCoverUrl = imageCoverUrl;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleDetailsForm that = (TitleDetailsForm) o;
        return Objects.equals(name, that.name) && Objects.equals(score, that.score) && Objects.equals(synopsis, that.synopsis) && Objects.equals(imageCoverUrl, that.imageCoverUrl) && Objects.equals(imageThumbnailUrl, that.imageThumbnailUrl) && Objects.equals(releaseDate, that.releaseDate) && Objects.equals(finishedDate, that.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, synopsis, imageCoverUrl, imageThumbnailUrl, releaseDate, finishedDate);
    }
}
