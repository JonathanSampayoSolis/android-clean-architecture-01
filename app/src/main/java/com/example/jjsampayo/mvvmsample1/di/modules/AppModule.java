package com.example.jjsampayo.mvvmsample1.di.modules;

import android.app.Application;

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
}
