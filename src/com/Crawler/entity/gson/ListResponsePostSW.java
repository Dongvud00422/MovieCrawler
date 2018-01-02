package com.Crawler.entity.gson;

import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;

import java.util.ArrayList;

public class ListResponsePostSW {
    private ArrayList<Showtime> dataShowtime;
//    private ArrayList<Movie> dataMovie;
//    private ArrayList<Theater> dataTheater;
    private Movie movie;
    private Theater theater;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public ListResponsePostSW() {

    }
//
//    public ArrayList<Movie> getDataMovie() {
//        return dataMovie;
//    }
//
//    public void setDataMovie(ArrayList<Movie> dataMovie) {
//        this.dataMovie = dataMovie;
//    }
//
//    public ArrayList<Theater> getDataTheater() {
//        return dataTheater;
//    }
//
//    public void setDataTheater(ArrayList<Theater> dataTheater) {
//        this.dataTheater = dataTheater;
//    }

    public ArrayList<Showtime> getDataShowtime() {
        return dataShowtime;
    }

    public void setDataShowtime(ArrayList<Showtime> dataShowtime) {
        this.dataShowtime = dataShowtime;
    }
}
