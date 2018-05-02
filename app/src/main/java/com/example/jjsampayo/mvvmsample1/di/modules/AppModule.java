package com.example.jjsampayo.mvvmsample1.di.modules;

import android.app.Application;

import com.example.jjsampayo.mvvmsample1.util.other.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}
