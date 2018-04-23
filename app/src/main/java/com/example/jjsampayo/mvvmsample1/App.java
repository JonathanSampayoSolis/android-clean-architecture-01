package com.example.jjsampayo.mvvmsample1;

import android.app.Application;

import com.example.jjsampayo.mvvmsample1.di.components.DaggerReposComponent;
import com.example.jjsampayo.mvvmsample1.di.components.ReposComponent;
import com.example.jjsampayo.mvvmsample1.di.modules.AppModule;

public class App extends Application {
    private static ReposComponent reposComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        reposComponent = DaggerReposComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static ReposComponent getReposComponent() {
        return reposComponent;
    }
}
