package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.HashMap;

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

    public HashMap<String, String> validate(){
        HashMap<String, String> errors = new HashMap<>();
        if (this.showTimeId < 0) {
            errors.put("showTimeId", "Please enter showTimeId");
        }
        if (this.date == null || this.date.length() == 0){
            errors.put("date", "Please enter date");
        }
        if (this.day < 0){
            errors.put("day", "Please enter day");
        }
        if (this.month < 0){
            errors.put("month", "Please enter month");
        }
        if (this.hour < 0){
            errors.put("hour", "Please enter hour");
        }
        if (this.slotLeft < 0){
            errors.put("slotLeft", "Please enter slotLeft");
        }
        if (this.movieId == null || this.movieId.length() == 0){
            errors.put("movieId", "Please enter movieId");
        }
        if (this.theaterId == null || this.theaterId.length() == 0){
            errors.put("theaterId", "Please enter theaterId");
        }
        if (this.minute <0){
            errors.put("minute", "Please enter minute");
        }


        return errors;
    }
}
