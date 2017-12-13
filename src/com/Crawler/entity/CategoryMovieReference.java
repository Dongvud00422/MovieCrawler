package com.Crawler.entity;

public class CategoryMovieReference {
    private String movieID;
    private int categoryId;

    public CategoryMovieReference(String movieID, int categoryId) {
        this.movieID = movieID;
        this.categoryId = categoryId;
    }

    public String getMovieID() {
        return movieID;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
