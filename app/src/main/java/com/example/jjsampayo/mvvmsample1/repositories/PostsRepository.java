package com.example.jjsampayo.mvvmsample1.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.jjsampayo.mvvmsample1.repositories.external.WebService;
import com.example.jjsampayo.mvvmsample1.repositories.models.Post;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository {
    private static final String TAG = PostsRepository.class.getSimpleName();

    private WebService webService;

    @Inject
    public PostsRepository(WebService webService) {
        this.webService = webService;
    }

    public PostsRepository() {

    }

    public MutableLiveData<List<Post>> getListPost(int user) {
        final MutableLiveData<List<Post>> data = new MutableLiveData<>();

        webService.getPostByUser(user).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
                data.setValue(null);
            }
        });
        return data;
    }
}
