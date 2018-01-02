package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.HashMap;

// Thông tin lịch chiếu.
@Entity
public class Showtime {

    @Id
    private long id;
    @Index
    private String movieId;
    @Index
    private String theaterId;
    @Index
    private long startTimeMLS;
    @Index
    private long endTimeMLS;
    @Unindex
    private int slotLeft; // Số ghế trống.
    @Index
    private int status;
    @Unindex
    private String movieType;

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }

    public long getStartTimeMLS() {
        return startTimeMLS;
    }

    public void setStartTimeMLS(long startTimeMLS) {
        this.startTimeMLS = startTimeMLS;
    }

    public long getEndTimeMLS() {
        return endTimeMLS;
    }

    public void setEndTimeMLS(long endTimeMLS) {
        this.endTimeMLS = endTimeMLS;
    }

    public int getSlotLeft() {
        return slotLeft;
    }

    public void setSlotLeft(int slotLeft) {
        this.slotLeft = slotLeft;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String, String> validate(){
        HashMap<String, String> errors = new HashMap<>();
        return errors;
    }
}
