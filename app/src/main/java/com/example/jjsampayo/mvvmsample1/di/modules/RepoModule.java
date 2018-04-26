package com.example.jjsampayo.mvvmsample1.di.modules;

import com.example.jjsampayo.mvvmsample1.data.repositories.albums.AlbumRepository;
import com.example.jjsampayo.mvvmsample1.data.repositories.comments.CommentsRepository;
import com.example.jjsampayo.mvvmsample1.data.repositories.posts.PostsRepository;
import com.example.jjsampayo.mvvmsample1.data.repositories.users.UsersDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public UsersDataSource provideUserListRepo() {
        return new UsersDataSource();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public PostsRepository providePostsListRepo() {
        return new PostsRepository();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public AlbumRepository provideAlbumListRepo() {
        return new AlbumRepository();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public CommentsRepository provideCommentsRepo() {
        return new CommentsRepository();
    }
}