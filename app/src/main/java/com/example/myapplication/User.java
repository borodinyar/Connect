package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ToRegister{
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("user_name")
    @Expose
    String username;

    public ToRegister(User user){
        this.email = user.email;
        this.password = user.password;
        this.username = user.username;
    }
}

class ToAuthorize{
    @SerializedName("token")
    @Expose
    String token;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("login")
    @Expose
    String username;

    public ToAuthorize(String username, String password) {
        this.token = "";
        this.password = password;
        this.username = username;
    }
}

class ToRefreshProfile {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("user_name")
    @Expose
    String user_name;

    @SerializedName("link_for_connect")
    @Expose
    String link_for_connect;

    @SerializedName("specialization")
    @Expose
    String specialization;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("image")
    @Expose
    String image;


    public ToRefreshProfile(String email, int id, String password, String token, String user_name, String link_for_connect, String specialization, String description, String image) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.token = token;
        this.user_name = user_name;
        this.link_for_connect = link_for_connect;
        this.specialization = specialization;
        this.description = description;
        this.image = image;
    }
}

public class User {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("image")
    @Expose
    String image;

    @SerializedName("specialization")
    @Expose
    String specialization;

    @SerializedName("user_name")
    @Expose
    String username;

    @SerializedName("link_for_connect")
    @Expose
    String link;

    public User(String email, String password, String description, String image, String specialization, String username, String link) {
        this.email = email;
        this.password = password;
        this.description = description;
        this.image = image;
        this.specialization = specialization;
        this.username = username;
        this.link = link;
    }
    public User(String description, String email, int id, String image, String link_for_connect, String password, String specialization, String token, String user_name) {
        this.email = email;
        this.password = password;
        this.description = description;
        this.image = image;
        this.specialization = specialization;
        this.username = user_name;
        this.link = link_for_connect;
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
