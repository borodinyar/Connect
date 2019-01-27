package com.example.myapplication;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ToPost {
    @SerializedName("name")
    @Expose
    private String title;
    @SerializedName("small_description")
    @Expose
    private String shortdesc;
    @SerializedName("description")
    @Expose
    private String longdesc;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("idea")
    @Expose
    public Idea idea;

    public ToPost(Idea idea) {
        this.idea = idea;
        this.title = idea.title;
        this.shortdesc = idea.shortdesc;
        this.longdesc = idea.longdesc;
        this.image = idea.image;
    }
    public ToPost(Idea idea, String token) {
        this.idea = idea;
        this.title = idea.title;
        this.shortdesc = idea.shortdesc;
        this.longdesc = idea.longdesc;
        this.image = idea.image;
        this.token = token;
    }

}

public class Idea {
    String TAG = "Idea";

    @SerializedName("id")
    @Expose
    int id = 0;

    @SerializedName("name")
    @Expose
    String title = "empty";

    @SerializedName("small_description")
    @Expose
    String shortdesc = "empty";

    @SerializedName("description")
    @Expose
    String longdesc = "empty";

    @SerializedName("author_id")
    @Expose
    int author;

    @SerializedName("image")
    @Expose
    String image = "empty";

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("likes")
    @Expose
    private int likes;

    @SerializedName("dizlikes")
    @Expose
    private int dislikes;

    public Idea(String token, int id, String title, String shortdesc, String longdesc, int author, String image, int likes, int dislikes) {
        this.token = token;
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.longdesc = longdesc;
        this.author = author;
        this.image = image;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Idea(String title, String shortdesc, String longdesc, String image) {
        this.title = title;
        this.shortdesc = shortdesc;
        this.longdesc = longdesc;
        this.image = image;
    }

    public Idea(String token, int id, String title, String shortdesc, String longdesc, int author, String image) {
        this.title = title;
        this.id = id;
        this.shortdesc = shortdesc;
        this.longdesc = longdesc;
        this.author = author;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }


    public void print() {
        Log.e(TAG, this.getTitle());
        Log.e(TAG, this.getShortdesc());
        Log.e(TAG, this.getLongdesc());
        Log.e(TAG, String.valueOf(this.getId()));
        Log.e(TAG, String.valueOf(this.getAuthor()));
    }
}

