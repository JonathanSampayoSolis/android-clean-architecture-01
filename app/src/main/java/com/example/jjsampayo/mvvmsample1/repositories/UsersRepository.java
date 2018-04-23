package com.example.jjsampayo.mvvmsample1.repositories;

import android.app.Application;

import android.arch.paging.DataSource;

import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jjsampayo.mvvmsample1.App;
import com.example.jjsampayo.mvvmsample1.repositories.external.WebService;
import com.example.jjsampayo.mvvmsample1.repositories.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.repositories.models.User;

import java.util.List;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository {
    private static final String TAG = UsersRepository.class.getSimpleName().toUpperCase();

    @Inject
    WebService webService;

    @Inject
    Application application;

    @Inject
    UserDao userDao;

    public UsersRepository() {
        App.getReposComponent().inject(this);
    }

    public DataSource.Factory<Integer, User> getListUser() {
        final DataSource.Factory[] dataSource = new DataSource.Factory[1];

        webService.getListUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull final Response<List<User>> response) {
                Executors.newFixedThreadPool(5).execute(new Runnable() {
                    @Override
                    public void run() {
                        userDao.clearTable();
                        userDao.insertAll(response.body());

                        dataSource[0] = userDao.getAll();
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });

        return dataSource[0];
    }

}
