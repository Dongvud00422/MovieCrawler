package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class City {
    // Lưu thông tin thành phố.
    @Id
    private String cityId;
    @Index
    private String cityName;

    public City() {
    }

    public City(String name, String cityId) {
        this.cityName = name;
        this.cityId = cityId;
    }

    public String getName() {
        return cityName;
    }

    public void setName(String name) {
        this.cityName = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
