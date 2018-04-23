package com.example.jjsampayo.mvvmsample1.di.modules;

import com.example.jjsampayo.mvvmsample1.di.scopes.ApplicationScope;
import com.example.jjsampayo.mvvmsample1.util.reactive.ReactiveEvent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ReactiveModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ReactiveEvent provideReactiveEvent() {
        return new ReactiveEvent();
    }
}
