package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Showtime {

    // Thông tin lịch chiếu.
    @Id
    private long showTimeId;
    @Index
    private String date; // Thứ.
    @Index
    private int day; // Ngày.
    @Index
    private int month; // Tháng.
    @Index
    private int hour; // Giờ.
    @Index
    private int minute; // Phut
    @Index
    private int slotLeft; // Số ghế trống.
    @Index
    private String movieId;
    @Index
    private String theaterId;

    public Showtime() {

    }

    public long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getSlotLeft() {
        return slotLeft;
    }

    public void setSlotLeft(int slotLeft) {
        this.slotLeft = slotLeft;
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

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
