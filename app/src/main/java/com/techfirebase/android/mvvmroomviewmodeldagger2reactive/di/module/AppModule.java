package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.AppRepository;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.AppRepositoryImpl;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.AppRoomDatabase;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service.LocalWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service.LocalWordServiceImpl;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.AppRetrofitApi;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob.SyncWordJobManagerInitializer;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob.SyncWordResponseObserver;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.service.RemoteWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.service.RemoteWordServiceImpl;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.di.DatabaseInfo;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.AppConstants;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.rx.AppSchedulerProvider;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DUKE SINGH on 26-03-2018.
 * <p>
 * <p>AppModule is a Dagger module responsible for providing singleton services on the application
 * level, We have to provide the dependencies expressed in the Application class. This class needs
 * some othre dependencies like DataManager, etc and to provide this class we have to provide the
 * dependencies expressed by DataManager or other dependencies, so we have to provide every
 * dependencies before using them so that Dagger can make dependency graph. We then move further in
 * the graph.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    AppRoomDatabase provideAppDatabase(@DatabaseInfo final String dbName, final Context context) {
        return Room.databaseBuilder(context, AppRoomDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    AppRetrofitApi provideAppWebService() {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL.toString())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(AppRetrofitApi.class);

        // This will be used to convert call object into our custom response object
    /*return new Retrofit.Builder()
    .baseUrl(AppConstants.BASE_URL.toString())
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
    .build()
    .create(AppRetrofitApi.class);*/

        // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        // With this adapter being applied the Retrofit interfaces are able to return RxJava 2.x types,
        // e.g., Observable, Flowable or Single and so on.
    }

    @Provides
    @Singleton
    Context provideContext(final Application application) {
        return application;
    }

    @Provides
    @Singleton
    AppRepository provideAppRepository(final AppRepositoryImpl appRepositoryImpl) {
        return appRepositoryImpl;
    }

    @Provides
    @Singleton
    LocalWordService provideLocalWordService(final LocalWordServiceImpl localWordService) {
        return localWordService;
    }

    @Provides
    @Singleton
    RemoteWordService provideRemoteWordService(final RemoteWordServiceImpl remoteWordService) {
        return remoteWordService;
    }

    @Provides
    @Singleton
    SyncWordResponseObserver provideSyncWordResponseObserver(final LocalWordServiceImpl localWordService) {
        return new SyncWordResponseObserver(localWordService);
    }

    @Provides
    @Singleton
    SyncWordJobManagerInitializer provideSyncWordJobManagerInitializer(final SyncWordResponseObserver syncWordResponseObserver) {
        return new SyncWordJobManagerInitializer(syncWordResponseObserver);
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME.toString();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
