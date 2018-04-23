package com.example.jjsampayo.mvvmsample1.repositories.external;

import com.example.jjsampayo.mvvmsample1.repositories.models.Album;
import com.example.jjsampayo.mvvmsample1.repositories.models.Comment;
import com.example.jjsampayo.mvvmsample1.repositories.models.Post;
import com.example.jjsampayo.mvvmsample1.repositories.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("users/")
    Call<List<User>> getListUsers();

    @GET("users/")
    Call<List<User>> getListUsers(@Query("id") int... id);

    @GET("posts/")
    Call<List<Post>> getPostByUser(@Query("user") int user);

    @GET("albums/")
    Call<List<Album>> getAlbumByUser(@Query("userId") int userId);

    @GET("comments/")
    Call<List<Comment>> getCommentsOf(@Query("postId") int postId);

}
