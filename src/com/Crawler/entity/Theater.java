package com.Crawler.entity;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

// Thông tin các cụm rạp.
@Entity
public class Theater {

    @Id
    private String theaterId;
    @Unindex
    private String theaterName;
    @Unindex
    private String address;
    @Unindex
    private String hotline;
    @Unindex
    private String fax;
    @Index
    private String cityId;
    @Index
    private int status;

    public Theater() {

    }

    public Theater(String theaterId, String theaterName, String address, String hotline, String fax) {
        this.theaterId = theaterId;
        this.theaterName = theaterName;
        this.address = address;
        this.hotline = hotline;
        this.fax = fax;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Document toSearchDocument(){
        return Document.newBuilder()
                .addField(Field.newBuilder().setName("theaterName").setText(this.getTheaterName()))
                .addField(Field.newBuilder().setName("theaterId").setText(this.getTheaterId()))
                .build();
    }
}
