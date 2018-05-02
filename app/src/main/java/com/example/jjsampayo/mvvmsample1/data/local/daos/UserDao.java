package com.example.jjsampayo.mvvmsample1.data.local.daos;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jjsampayo.mvvmsample1.data.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from users")
    List<User> getAll();

    @Query("select * from users")
    DataSource.Factory<Integer, User> getAllByFactory();

    @Query("select * from users where id = :userID")
    User getByID(int userID);

    @Query("delete from users")
    void clearTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

}
