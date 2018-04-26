package com.example.jjsampayo.mvvmsample1.data;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.jjsampayo.mvvmsample1.data.external.WebService;
import com.example.jjsampayo.mvvmsample1.data.models.Comment;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository {
    private final String TAG = AlbumRepository.class.getSimpleName();

    @Inject
    WebService webService;

    public CommentsRepository() {

    }

    public MutableLiveData<List<Comment>> getCommentsOf(int user) {
        final MutableLiveData<List<Comment>> data = new MutableLiveData<>();

        webService.getCommentsOf(user).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, "onFailuer: " + t.toString(), t);
                data.setValue(null);
            }
        });
        return data;
    }
}
