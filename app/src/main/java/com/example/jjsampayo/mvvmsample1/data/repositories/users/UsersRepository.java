package com.example.jjsampayo.mvvmsample1.data.repositories.users;

import android.app.Application;

import android.arch.paging.ItemKeyedDataSource;

import android.support.annotation.NonNull;

import android.util.Log;

import com.example.jjsampayo.mvvmsample1.App;

import com.example.jjsampayo.mvvmsample1.data.external.WebService;
import com.example.jjsampayo.mvvmsample1.data.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.data.models.User;

import com.example.jjsampayo.mvvmsample1.util.other.RepositoryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UsersRepository extends ItemKeyedDataSource<Integer, User> {
    private static final String TAG = UsersRepository.class.getSimpleName().toUpperCase();

    @Inject
    WebService webService;

    @Inject
    Application application;

    @Inject
    UserDao userDao;

    UsersRepository() {
        App.getReposComponent().inject(this);
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params
                            , @NonNull final LoadInitialCallback<User> callback) {
        Log.d(TAG, "Load initial:: Rang " + 1 + " to " + params.requestedLoadSize);

        List<User> users = new ArrayList<>();
        int[] ids = RepositoryUtil.vectorXToY(params.requestedLoadSize);

        for (int id : ids)
            if (userDao.getByID(id) != null)
                users.add(userDao.getByID(id));

        callback.onResult(users);

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params
                         , @NonNull final LoadCallback<User> callback) {
        Log.d(TAG, "Load after:: Rang " + (params.key + 1) + " to " + (params.key + params.requestedLoadSize));
/*
        requestState.postValue(RequestState.LOADING);

        int[] ids = vectorXToY(params.key + 1, params.key + params.requestedLoadSize + 1);

        webService.getListUsers(ids).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful() && response.code() == Constants.URL_CODE_OK) {
                    userList = response.body();
                    callback.onResult(userList != null ? userList : new ArrayList<User>());

                    requestState.postValue(RequestState.LOADED);
                } else {
                    Log.e(TAG, "Response Error:: Code: " + response.code() + " Message: " + response.message());
                    requestState.postValue(new RequestState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                String errorMsg = t.getMessage() == null ? "unknow error" : t.getMessage();
                Log.e(TAG, errorMsg);
                requestState.postValue(new RequestState(Status.FAILED, errorMsg));
            }
        });
*/
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params
                          , @NonNull LoadCallback<User> callback) {
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        return item.getId();
    }

}
