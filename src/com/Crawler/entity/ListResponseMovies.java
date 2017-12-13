package com.Crawler.entity;

import java.util.ArrayList;

public class ListResponseMovies {
    private ArrayList<Movie> dataMovie;
    private int totalPage;
    private int totalItem;
    private int limit;
    private int page;

    public ArrayList<Movie> getDataMovie() {
        return dataMovie;
    }

    public void setDataMovie(ArrayList<Movie> dataMovie) {
        this.dataMovie = dataMovie;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
