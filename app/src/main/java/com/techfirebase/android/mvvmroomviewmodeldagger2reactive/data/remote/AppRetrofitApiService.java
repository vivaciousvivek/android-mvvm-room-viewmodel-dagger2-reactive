package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.AppConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VIVEK KUMAR SINGH on 4/6/2018.
 */
public class AppRetrofitApiService {
    private final Retrofit retrofit;
    private static AppRetrofitApiService INSTANCE;

    public AppRetrofitApiService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL.toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    public static synchronized AppRetrofitApiService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AppRetrofitApiService();
        }
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
