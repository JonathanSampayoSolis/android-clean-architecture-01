package com.example.jjsampayo.mvvmsample1.data.repo_users;

import android.app.Application;

import android.arch.lifecycle.MutableLiveData;

import android.arch.paging.ItemKeyedDataSource;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jjsampayo.mvvmsample1.App;
import com.example.jjsampayo.mvvmsample1.config.Constants;
import com.example.jjsampayo.mvvmsample1.data.external.WebService;
import com.example.jjsampayo.mvvmsample1.data.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.data.models.User;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;
import com.example.jjsampayo.mvvmsample1.util.network.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository extends ItemKeyedDataSource<Integer, User> {
    private static final String TAG = UsersRepository.class.getSimpleName().toUpperCase();

    @Inject
    WebService webService;

    @Inject
    Application application;

    @Inject
    UserDao userDao;

    private MutableLiveData<RequestState> initialState;
    private MutableLiveData<RequestState> requestState;

    private List<User> userList;

    public UsersRepository() {
        App.getReposComponent().inject(this);

        this.initialState = new MutableLiveData<>();
        this.requestState = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params
                            , @NonNull final LoadInitialCallback<User> callback) {
        Log.d(TAG, "Load initial:: Rang " + 1 + " to " + params.requestedLoadSize);

        int[] ids = vectorXToY(params.requestedLoadSize);

        initialState.postValue(RequestState.LOADING);

        webService.getListUsers(ids).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful() && response.code() == Constants.URL_CODE_OK) {
                    userList = response.body();
                    callback.onResult(userList != null ? userList : new ArrayList<User>());

                    initialState.postValue(RequestState.LOADED);
                } else {
                    Log.e(TAG, "Response Error:: Code: " + response.code() + " Message: " + response.message());
                    initialState.postValue(new RequestState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                String errorMsg = t.getMessage() == null ? "unknow error" : t.getMessage();
                Log.e(TAG, errorMsg);
                initialState.postValue(new RequestState(Status.FAILED, errorMsg + "\nSwipe to refresh c:"));
            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params
                         , @NonNull final LoadCallback<User> callback) {
        Log.d(TAG, "Load after:: Rang " + (params.key + 1) + " to " + (params.key + params.requestedLoadSize));

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
                initialState.postValue(new RequestState(Status.FAILED, errorMsg));
            }
        });

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

    public MutableLiveData<RequestState> getInitialState() {
        return initialState;
    }

    public MutableLiveData<RequestState> getRequestState() {
        return requestState;
    }

    private int[] vectorXToY(int... n) {
        int min;
        int max;
        if (n.length > 1) {
            min = n[0];
            max = (n[1] - n[0]);
        } else {
            min = 1;
            max = n[0];
        }
        int[] v = new int[max];

        for (int i = 0; i < max; i++)
            v[i] = min++;

        return v;
    }

}
