package com.example.jjsampayo.mvvmsample1.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.jjsampayo.mvvmsample1.config.Constants;
import com.example.jjsampayo.mvvmsample1.data.local.AppDatabase;
import com.example.jjsampayo.mvvmsample1.data.local.daos.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    @SuppressWarnings("unused")
    public AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, Constants.DB_NAME).build();
    }

    @Singleton
    @Provides
    @SuppressWarnings("unused")
    public UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.getUserDao();
    }
}
