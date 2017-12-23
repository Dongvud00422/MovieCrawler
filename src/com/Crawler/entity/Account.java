
package com.Crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.HashMap;

@Entity
public class Account {
    @Id
    private String account;
    @Index
    private String password;
    @Unindex
    private long createdTimeMLS;
    @Unindex
    private long updatedTimeMLS;
    @Index
    private int status; // 0: không hoạt động , 1: có hoạt động
    @Index
    private String fullName;
    @Unindex
    private String gender;
    @Unindex
    private String birthday;
    @Unindex
    private String email;
    @Unindex
    private String phoneNumber;
    @Unindex
    private String address;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatedTimeMLS() {
        return createdTimeMLS;
    }

    public void setCreatedTimeMLS(long createdTimeMLS) {
        this.createdTimeMLS = createdTimeMLS;
    }

    public long getUpdatedTimeMLS() {
        return updatedTimeMLS;
    }

    public void setUpdatedTimeMLS(long updatedTimeMLS) {
        this.updatedTimeMLS = updatedTimeMLS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, String> validate(){
        HashMap<String, String> errors = new HashMap<>();

        if(this.account == null || this.password.length() == 0){
            errors.put("username", "username is not valid or empty");
        }
        if(this.password == null || this.password.length() == 0){
            errors.put("password", "password is not valid or empty");
        }
        if(this.status != 0 && this.status != 1){
            errors.put("status", "status must be 0 or 1");
        }
        return errors;
    }

}
