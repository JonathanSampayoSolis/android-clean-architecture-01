package com.example.jjsampayo.mvvmsample1.repositories.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

@Entity(tableName = "posts")
public class Post {

    @ColumnInfo(name = "user_id")
    @Expose
    private int userId;

    @NonNull
    @PrimaryKey
    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String body;

    public Post(int userId, int id, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
