package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CategoryMovie {

    @Id
    private String id; // id = movieId + "_" + categoryId
    @Index
    private String movieId;
    @Index
    private int categoryId;

    public CategoryMovie(String id, String movieId, int categoryId) {
        this.movieId = movieId;
        this.categoryId = categoryId;
        this.id = movieId + "_" + categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
