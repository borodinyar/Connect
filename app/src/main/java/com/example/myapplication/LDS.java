package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LDS {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("user_id")
    @Expose
    int userid;
    @SerializedName("idea_id")
    @Expose
    int ideaid;

    public LDS(int id, int userid, int ideaid) {
        this.id = id;
        this.userid = userid;
        this.ideaid = ideaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getIdeaid() {
        return ideaid;
    }

    public void setIdeaid(int ideaid) {
        this.ideaid = ideaid;
    }
}
