package com.example.jjsampayo.mvvmsample1.gui.album;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jjsampayo.mvvmsample1.di.components.DaggerReposComponent;
import com.example.jjsampayo.mvvmsample1.di.components.ReposComponent;
import com.example.jjsampayo.mvvmsample1.data.repositories.albums.AlbumRepository;
import com.example.jjsampayo.mvvmsample1.data.models.Album;

import java.util.List;

import javax.inject.Inject;

public class ListAlbumViewModel extends AndroidViewModel {

    @Inject
    AlbumRepository respository;

    private MutableLiveData<List<Album>> mutableLiveData;

    public ListAlbumViewModel(@NonNull Application application) {
        super(application);

        ReposComponent reposComponent = DaggerReposComponent
                .builder().build();
        reposComponent.inject(this);
    }

    public MutableLiveData<List<Album>> getAlbums(int user) {
        if (mutableLiveData == null)
            mutableLiveData = respository.getListAlbum(user);

        return mutableLiveData;
    }
}
