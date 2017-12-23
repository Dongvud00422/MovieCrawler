package com.Crawler.entity;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.HashMap;

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

    public Movie() {

    }

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

    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.movieName == null || this.movieName.length() == 0) {
            errors.put("movieName", "Please enter movieName");
        }
        if (this.movieId == null || this.movieId.length() == 0) {
            errors.put("movieName", "Please enter movieName");
        }
        if (this.actor == null || this.actor.length() == 0) {
            errors.put("movieName", "Please enter movieName");
        }
        if (this.description == null || this.description.length() == 0) {
            errors.put("movieName", "Please enter movieName");
        }
        if (this.duration < 0) {
            errors.put("duration", "Please enter duration");
        }
        if (this.poster == null || this.poster.length() == 0) {
            errors.put("poster", "Please enter poster");
        }
        if (this.language == null || this.language.length() == 0) {
            errors.put("language", "Please enter language");
        }
        if (this.director == null || this.director.length() == 0) {
            errors.put("director", "Please enter director");
        }
        if (this.openAt == null || this.openAt.length() == 0) {
            errors.put("openAt", "Please enter openAt");
        }
        if (this.trailer == null || this.trailer.length() == 0) {
            errors.put("trailer", "Please enter trailer");
        }
        if (this.minAge == null || this.minAge.length() == 0) {
            errors.put("minAge", "Please enter minAge");
        }
        if (this.type == null || this.type.length() == 0) {
            errors.put("type", "Please enter type");
        }


        return errors;
    }


    public Document toSearchDocument() {
        return Document.newBuilder()
                .addField(Field.newBuilder().setName("type").setText(this.getType()))
                .addField(Field.newBuilder().setName("openAt").setText(this.getOpenAt()))
                .addField(Field.newBuilder().setName("movieId").setText(this.getMovieId()))
                .addField(Field.newBuilder().setName("duration").setText(String.valueOf(this.getDuration())))
                .addField(Field.newBuilder().setName("language").setText(this.getLanguage()))
                .addField(Field.newBuilder().setName("director").setText(this.getDirector()))
                .addField(Field.newBuilder().setName("description").setText(this.getDescription()))
                .addField(Field.newBuilder().setName("actor").setText(this.getActor()))
                .addField(Field.newBuilder().setName("movieName").setText(this.getMovieName()))
                .build();
    }
}


