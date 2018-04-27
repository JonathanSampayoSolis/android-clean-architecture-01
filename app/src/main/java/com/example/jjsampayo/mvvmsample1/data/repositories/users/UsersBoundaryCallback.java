package com.example.jjsampayo.mvvmsample1.data.repositories.users;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jjsampayo.mvvmsample1.App;
import com.example.jjsampayo.mvvmsample1.data.external.WebService;
import com.example.jjsampayo.mvvmsample1.data.models.User;

import javax.inject.Inject;

public class UsersBoundaryCallback extends PagedList.BoundaryCallback<User> {
    private static final String TAG = UsersBoundaryCallback.class.getSimpleName();

    @Inject
    WebService webService;

    public UsersBoundaryCallback() {
        super();
        App.getReposComponent().inject(this);
    }

    @Override
    public void onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded");
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull User itemAtFront) {
        Log.d(TAG, "onItemAtFrontLoaded");
    }

    @Override
    public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
        Log.d(TAG, "onItemAtEndLoaded");
    }
}
