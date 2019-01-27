package com.example.myapplication;

public class Model {
    private int id;
    private String title;
    private String shortdesc;
    private String longdesc;
    private int author;
    private String image;
    private String token;
    private int likes;
    private int dislikes;

    public Model(int id, String title, String shortdesc, String longdesc, int author, String image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.longdesc = longdesc;
        this.author = author;
        this.image = image;
    }

    public Model(Idea idea) {
        this.id = idea.getId();
        this.title = idea.getTitle();
        this.shortdesc = idea.getShortdesc();
        this.longdesc = idea.getLongdesc();
        this.author = idea.getAuthor();
        this.image = idea.getImage();
        this.likes = idea.getLikes();
        this.dislikes = idea.getDislikes();
    }

    public Model(String title, String shortdesc, String longdesc, String image) {
        this.title = title;
        this.shortdesc = shortdesc;
        this.longdesc = longdesc;
        this.image = image;
    }
    public Model(String token, int id, String title, String shortdesc, String longdesc, int author, String image, int likes, int dislikes) {
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

}
