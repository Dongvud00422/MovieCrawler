package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;

@Entity
public class Movie {

    // Lưu thông tin các bộ phim.
    @Id
    private String movieId; // Link phim.
    @Index
    private String movieName;
    @Index
    private String poster;
    @Index
    private int duration; // Thời lượng.
    @Index
    private String language;// Ngôn ngữ.
    @Index
    private String director; // Đạo diễn.
    @Index
    private String actor; // Diễn viên.
    @Index
    private String openAt; // Khởi chiếu.
    @Index
    private String description; // Mô tả phim.
    @Index
    private String trailer; // Link trailer.
    @Index
    private String minAge; // Độ tuổi được phép xem phim.
    @Index
    private String type;
    @Index
    private int status; // 0: phim hết hạn công chiếu | 1: đã lấy thông tin | 2: chưa lấy thông tin
    @Index
    private ArrayList<Integer> categoryId;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Integer> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ArrayList<Integer> categoryId) {
        this.categoryId = categoryId;
    }
}
