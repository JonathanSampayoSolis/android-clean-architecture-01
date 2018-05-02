package com.example.jjsampayo.mvvmsample1.presentation.users;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.jjsampayo.mvvmsample1.App;
import com.example.jjsampayo.mvvmsample1.config.Constants;
import com.example.jjsampayo.mvvmsample1.data.local.daos.UserDao;
import com.example.jjsampayo.mvvmsample1.data.repositories.users.UsersBoundaryCallback;
import com.example.jjsampayo.mvvmsample1.data.repositories.users.UsersDataSource;
import com.example.jjsampayo.mvvmsample1.data.models.User;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;
import com.example.jjsampayo.mvvmsample1.util.other.AppExecutors;
import com.example.jjsampayo.mvvmsample1.util.reactive.ReactiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListUsersViewModel extends AndroidViewModel {

    @Inject
    ReactiveEvent onClickItemEvent;

    @Inject
    UsersDataSource usersDataSource;

    @Inject
    AppExecutors appExecutors;

    @Inject
    UserDao userDao;

    private LiveData<PagedList<User>> listLiveData;

    private UsersBoundaryCallback usersBoundaryCallback;

    public ListUsersViewModel(@NonNull Application application) {
        super(application);
        App.getReposComponent().inject(this);
        usersBoundaryCallback = new UsersBoundaryCallback();
    }

    public LiveData<PagedList<User>> getListUser() {
            PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(Constants.PAGING_INITIAL_HINT)
                    .setPageSize(Constants.PAGING_PAGE_SIZE)
                    .setPrefetchDistance(Constants.PAGING_PREFETCH)
                    .build();

            listLiveData = new LivePagedListBuilder<>(userDao.getAllByFactory(), pagedListConfig)
                    .setBoundaryCallback(usersBoundaryCallback)
                    .build();

        return listLiveData;
    }

    public void refresh() {

    }

    public ReactiveEvent getOnClickItemEvent() {
        return onClickItemEvent;
    }

    public void onClickItemEvent(int id, String name) {
        onClickItemEvent.setValue(String.valueOf(id) + ":" + name);
    }

    public MutableLiveData<RequestState> getInitialState() {
        return usersBoundaryCallback.getInitialState();
    }

    public MutableLiveData<RequestState> getRequestState() {
        return usersBoundaryCallback.getRequestState();
    }

    private void clearTable() {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.clearTable();
            }
        });
    }

}
