package com.Crawler.entity;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * - id cuả film chính là link trên trang.
 * - phim khi insert, update, delete sẽ được update vào full text search.
 * - search phim theo
 *      + tên.
 *      + thể loại.
 *      + giờ chiếu.
 *      + rạp.
 * */
@Entity
public class Movie {

    @Id
    private String id; // Link phim.
    @Index
    private String category;
    @Unindex
    private String name;
    @Unindex
    private String poster;
    @Unindex
    private int duration; // Thời lượng.
    @Unindex
    private String language;// Ngôn ngữ.
    @Unindex
    private String director; // Đạo diễn.
    @Unindex
    private String actor; // Diễn viên.
    @Unindex
    private String openAt; // Khởi chiếu.
    @Unindex
    private String description; // Mô tả phim.
    @Unindex
    private String trailer; // Link trailer.
    @Unindex
    private String minAge; // Độ tuổi được phép xem phim.
    @Index
    private String type; // 2D, 3D
    @Index
    private int status; // 0: phim hết hạn công chiếu | 1: đã lấy thông tin | 2: chưa lấy thông tin

    public Movie() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.name == null || this.name.length() == 0) {
            errors.put("movieName", "Please enter movieName");
        }
        if (this.id == null || this.id.length() == 0) {
            errors.put("id", "Please enter id");
        }
        if (this.actor == null || this.actor.length() == 0) {
            errors.put("actor", "Please enter actor");
        }
        if (this.description == null || this.description.length() == 0) {
            errors.put("description", "Please enter description");
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
                .addField(Field.newBuilder().setName("id").setText(this.getId()))
                .addField(Field.newBuilder().setName("name").setText(this.getName()))
                .addField(Field.newBuilder().setName("poster").setText(this.getPoster()))
                .addField(Field.newBuilder().setName("duration").setText(String.valueOf(this.getDuration())))
                .addField(Field.newBuilder().setName("language").setText(this.getLanguage()))
                .addField(Field.newBuilder().setName("director").setText(this.getDirector()))
                .addField(Field.newBuilder().setName("description").setText(this.getDescription()))
                .addField(Field.newBuilder().setName("actor").setText(this.getActor()))
                .addField(Field.newBuilder().setName("minAge").setText(this.getMinAge()))
                .addField(Field.newBuilder().setName("type").setText(this.getType()))
                .addField(Field.newBuilder().setName("openAt").setText(this.getOpenAt()))
                .addField(Field.newBuilder().setName("trailer").setText(this.getTrailer()))
                .build();
    }
}


