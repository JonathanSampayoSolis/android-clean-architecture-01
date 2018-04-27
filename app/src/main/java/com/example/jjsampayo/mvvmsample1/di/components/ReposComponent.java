package com.example.jjsampayo.mvvmsample1.di.components;

import com.example.jjsampayo.mvvmsample1.data.repositories.users.UsersBoundaryCallback;
import com.example.jjsampayo.mvvmsample1.di.modules.AppModule;
import com.example.jjsampayo.mvvmsample1.di.modules.NetModule;
import com.example.jjsampayo.mvvmsample1.di.modules.ReactiveModule;
import com.example.jjsampayo.mvvmsample1.di.modules.RepoModule;
import com.example.jjsampayo.mvvmsample1.di.modules.RoomModule;

import com.example.jjsampayo.mvvmsample1.presentation.album.ListAlbumViewModel;
import com.example.jjsampayo.mvvmsample1.presentation.coments.PostDetailViewModel;
import com.example.jjsampayo.mvvmsample1.presentation.posts.ListPostsViewModel;
import com.example.jjsampayo.mvvmsample1.presentation.users.ListUsersViewModel;
import com.example.jjsampayo.mvvmsample1.data.repositories.users.UsersRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, RoomModule.class, RepoModule.class, ReactiveModule.class})
public interface ReposComponent {

    void inject(ListUsersViewModel viewModel);

    void inject(UsersRepository repository);

    void inject(UsersBoundaryCallback usersBoundaryCallback);


    void inject(ListPostsViewModel viewModel);


    void inject(ListAlbumViewModel viewModel);


    void inject(PostDetailViewModel viewModel);

}
