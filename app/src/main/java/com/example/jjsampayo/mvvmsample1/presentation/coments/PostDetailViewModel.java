package com.example.jjsampayo.mvvmsample1.gui.coments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jjsampayo.mvvmsample1.di.components.DaggerReposComponent;
import com.example.jjsampayo.mvvmsample1.di.components.ReposComponent;
import com.example.jjsampayo.mvvmsample1.data.repositories.comments.CommentsRepository;
import com.example.jjsampayo.mvvmsample1.data.models.Comment;

import java.util.List;

import javax.inject.Inject;

public class PostDetailViewModel extends AndroidViewModel {

    @Inject
    CommentsRepository repository;

    private MutableLiveData<List<Comment>> mutableLiveData;

    public PostDetailViewModel(@NonNull Application application) {
        super(application);

        ReposComponent reposComponent = DaggerReposComponent
                .builder()
                .build();
        reposComponent.inject(this);
    }

    public MutableLiveData<List<Comment>> getCommentsOf(int user) {
        if (mutableLiveData == null)
            mutableLiveData = repository.getCommentsOf(user);

        return mutableLiveData;
    }
}
