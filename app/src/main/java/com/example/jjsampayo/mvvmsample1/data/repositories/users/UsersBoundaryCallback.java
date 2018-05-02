package com.example.jjsampayo.mvvmsample1.data.repositories.users;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jjsampayo.mvvmsample1.App;
import com.example.jjsampayo.mvvmsample1.config.Constants;
import com.example.jjsampayo.mvvmsample1.data.external.WebService;
import com.example.jjsampayo.mvvmsample1.data.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.data.models.User;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;
import com.example.jjsampayo.mvvmsample1.util.network.Status;
import com.example.jjsampayo.mvvmsample1.util.other.AppExecutors;
import com.example.jjsampayo.mvvmsample1.util.other.RepositoryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersBoundaryCallback extends PagedList.BoundaryCallback<User> {
    private static final String TAG = UsersBoundaryCallback.class.getSimpleName();

    @Inject
    WebService webService;

    @Inject
    AppExecutors executors;

    @Inject
    UserDao userDao;



    private MutableLiveData<RequestState> initialState;
    private MutableLiveData<RequestState> requestState;

    public UsersBoundaryCallback() {
        super();
        App.getReposComponent().inject(this);

        this.initialState = new MutableLiveData<>();
        this.requestState = new MutableLiveData<>();
    }

    @Override
    public void onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded");
        syncWithNetwork(1);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull User itemAtFront) {
        Log.d(TAG, "onItemAtFrontLoaded");

    }

    @Override
    public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
        Log.d(TAG, "onItemAtEndLoaded");
        syncWithNetwork(itemAtEnd.getId());
    }

    public MutableLiveData<RequestState> getInitialState() {
        return initialState;
    }

    public MutableLiveData<RequestState> getRequestState() {
        return requestState;
    }

    private void syncWithNetwork(final int currentID) {
       int ids[];
        if (currentID == 1) {
            initialState.postValue(RequestState.LOADING);
            ids = RepositoryUtil.vectorXToY(Constants.PAGING_INITIAL_HINT);
        } else {
            requestState.postValue(RequestState.LOADING);
            ids = RepositoryUtil.vectorXToY(currentID, currentID + Constants.PAGING_PAGE_SIZE);
        }

        webService.getListUsers(ids).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull final Response<List<User>> response) {
                executors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: OnResponse");

                        if (response.isSuccessful() && response.code() == Constants.URL_CODE_OK)
                            userDao.insertAll((response.body() != null)
                                    ? response.body() : new ArrayList<User>());

                        if (currentID == 1)
                            initialState.postValue(RequestState.LOADED);
                        else
                            requestState.postValue(RequestState.LOADED);
                    }
                });
            }

            @Override
            public void onFailure(@NonNull  Call<List<User>> call, @NonNull  Throwable t) {
                String errorMsg = t.getMessage() == null ? "unknow error" : t.getMessage();
                Log.e(TAG, errorMsg);
                if (currentID == 1)
                    initialState.postValue(new RequestState(Status.FAILED, errorMsg));
                else
                    requestState.postValue(new RequestState(Status.FAILED, errorMsg));
            }
        });
    }
}
