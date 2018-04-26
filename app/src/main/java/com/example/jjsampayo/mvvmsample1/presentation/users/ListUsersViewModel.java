package com.example.jjsampayo.mvvmsample1.presentation.users;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.jjsampayo.mvvmsample1.App;
import com.example.jjsampayo.mvvmsample1.data.repositories.users.UsersDataSource;
import com.example.jjsampayo.mvvmsample1.data.models.User;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;
import com.example.jjsampayo.mvvmsample1.util.reactive.ReactiveEvent;

import javax.inject.Inject;

public class ListUsersViewModel extends AndroidViewModel {

    @Inject
    ReactiveEvent onClickItemEvent;

    @Inject
    UsersDataSource usersDataSource;

    private LiveData<PagedList<User>> listLiveData;

    public ListUsersViewModel(@NonNull Application application) {
        super(application);
        App.getReposComponent().inject(this);
    }

    public LiveData<PagedList<User>> getListUser() {
        if (listLiveData == null) {
            PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                    .setInitialLoadSizeHint(3)
                    .setPageSize(2)
                    .setPrefetchDistance(2)
                    .build();

            listLiveData = new LivePagedListBuilder<Integer, User>(usersDataSource, pagedListConfig)
                            .build();
        }

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
        return usersDataSource.getRepository().getInitialState();
    }

    public MutableLiveData<RequestState> getRequestState() {
        return usersDataSource.getRepository().getRequestState();
    }

}
