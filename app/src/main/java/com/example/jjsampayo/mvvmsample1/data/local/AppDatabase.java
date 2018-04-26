package com.example.jjsampayo.mvvmsample1.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jjsampayo.mvvmsample1.data.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.data.models.Album;
import com.example.jjsampayo.mvvmsample1.data.models.Comment;
import com.example.jjsampayo.mvvmsample1.data.models.Post;
import com.example.jjsampayo.mvvmsample1.data.models.User;

@Database(entities = {User.class, Post.class, Album.class, Comment.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

}
