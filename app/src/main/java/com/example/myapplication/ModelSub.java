package com.example.myapplication;

public class ModelSub {
    private int id;
    private String email;
    private String description;
    private String image;
    private String link;
    private String username;
    private String specialization;

    public ModelSub(int id, String email, String description, String image, String link, String username, String specialization) {
        this.id = id;
        this.email = email;
        this.description = description;
        this.image = image;
        this.link = link;
        this.username = username;
        this.specialization = specialization;
    }

    public ModelSub(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.link = user.getLink();
        this.username = user.getUsername();
        this.image = user.getImage();
        this.specialization = user.getSpecialization();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
