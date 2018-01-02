package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.HashMap;

// Lưu thông tin thành phố.
@Entity
public class City {
    @Id
    private String id;

    @Index
    private String name;

    public City() {
    }

    public City(String id, String name) {
        this.id = id;
        this.name = name;
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

    public HashMap<String, String> validate(){
        HashMap<String, String> errors = new HashMap<>();
        return errors;
    }
}
