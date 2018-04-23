package com.example.jjsampayo.mvvmsample1.repositories.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

@Entity(tableName = "album")
public class Album {

    @ColumnInfo(name = "user_id")
    @Expose
    private int userId;

    @NonNull
    @PrimaryKey
    @Expose
    private int id;

    @Expose
    private String title;

    public Album(int userId, @NonNull int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
