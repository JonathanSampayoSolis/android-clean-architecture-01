package com.example.jjsampayo.mvvmsample1.data;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.example.jjsampayo.mvvmsample1.data.external.WebService;
import com.example.jjsampayo.mvvmsample1.data.models.Album;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private final String TAG = AlbumRepository.class.getSimpleName();

    @Inject
    WebService webService;

    public AlbumRepository() {

    }

    public MutableLiveData<List<Album>> getListAlbum(int user) {
        final MutableLiveData<List<Album>> data = new MutableLiveData<>();

        webService.getAlbumByUser(user).enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
                data.setValue(null);
            }
        });
        return data;
    }
}
