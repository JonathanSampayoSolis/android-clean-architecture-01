package com.example.jjsampayo.mvvmsample1.di.modules;

import com.example.jjsampayo.mvvmsample1.repositories.external.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jjsampayo.mvvmsample1.config.Constants.API_URL;

@Module
public class NetModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return loggingInterceptor;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public OkHttpClient.Builder provideHttpBuilder(HttpLoggingInterceptor logging) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(logging);

        return builder;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Retrofit provideRetrofit(OkHttpClient.Builder httpClient) {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public WebService provideWebService(Retrofit retrofit) {
        return retrofit.create(WebService.class);
    }
}
