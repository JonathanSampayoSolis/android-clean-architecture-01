package com.example.jjsampayo.mvvmsample1.di.modules;

import android.app.Application;

import com.example.jjsampayo.mvvmsample1.di.scopes.ApplicationScope;
import com.example.jjsampayo.mvvmsample1.repositories.AlbumRepository;
import com.example.jjsampayo.mvvmsample1.repositories.CommentsRepository;
import com.example.jjsampayo.mvvmsample1.repositories.PostsRepository;
import com.example.jjsampayo.mvvmsample1.repositories.UsersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public UsersRepository provideUserListRepo() {
        return new UsersRepository();
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