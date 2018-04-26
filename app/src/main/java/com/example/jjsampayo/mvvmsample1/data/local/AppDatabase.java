package com.example.jjsampayo.mvvmsample1.repositories.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jjsampayo.mvvmsample1.repositories.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.repositories.models.Album;
import com.example.jjsampayo.mvvmsample1.repositories.models.Comment;
import com.example.jjsampayo.mvvmsample1.repositories.models.Post;
import com.example.jjsampayo.mvvmsample1.repositories.models.User;

@Database(entities = {User.class, Post.class, Album.class, Comment.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

}
