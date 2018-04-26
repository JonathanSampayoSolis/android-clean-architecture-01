package com.example.jjsampayo.mvvmsample1.presentation.posts;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jjsampayo.mvvmsample1.di.components.DaggerReposComponent;
import com.example.jjsampayo.mvvmsample1.di.components.ReposComponent;
import com.example.jjsampayo.mvvmsample1.data.repositories.posts.PostsRepository;
import com.example.jjsampayo.mvvmsample1.data.models.Post;
import com.example.jjsampayo.mvvmsample1.util.reactive.ReactiveEvent;

import java.util.List;

import javax.inject.Inject;

public class ListPostsViewModel extends AndroidViewModel {

    @Inject
    PostsRepository repository;

    @Inject
    ReactiveEvent onClickItemEvent;

    private MutableLiveData<List<Post>> mutableLiveData;

    public ListPostsViewModel(@NonNull Application application) {
        super(application);

        ReposComponent reposComponent = DaggerReposComponent
                .builder()
                .build();
        reposComponent.inject(this);
    }

    public MutableLiveData<List<Post>> getPosts(int user) {
        if (mutableLiveData == null)
            mutableLiveData = repository.getListPost(user);

        return mutableLiveData;
    }

    public ReactiveEvent getOnClickItemEvent() {
        return onClickItemEvent;
    }

    public void onClickItemEvent(Object[] data) {
        onClickItemEvent.setValue(data);
    }

}
