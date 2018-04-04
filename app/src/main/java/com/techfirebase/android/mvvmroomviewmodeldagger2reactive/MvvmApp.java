package com.techfirebase.android.mvvmroomviewmodeldagger2reactive;

import android.app.Activity;
import android.app.Application;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob.SyncWordJobManagerInitializer;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.di.component.DaggerAppComponent;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.AppLogger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by VIVEK KUMAR SINGH on 3/27/2018.
 *
 * <p>
 *
 * <p>DaggerApplicationComponent is the generated class by the Dagger, implementing the
 * ApplicationComponent. We provide the ApplicationModule class that is used to construct the
 * dependencies.
 *
 * <p>
 *
 * <p>In our case its name will be DaggerAppComponent.
 *
 * <p>
 *
 * <p>This DaggerAppComponent stub will be generated automatically by Dagger when you build your
 * project.
 *
 * <p>
 *
 * <p>Add name attribute of your application <application ... android:name=".MvvmApp" ....> //
 * activities </application>
 */
public class MvvmApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    SyncWordJobManagerInitializer syncWordJobManagerInitializer;

    /**
     * Application class and MainActivity class. These classes don’t have a constructor and Android
     * System is responsible for instantiating these. To get the dependency we use the OnCreate method
     * as it is called once when they are instantiated.
     * <p>
     * <p>
     * <p>
     * <p>We have called the inject method of appComponent and passed the instance of the MvvmApp
     * class.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        /*
         * To get application component object, Dagger allows us to customize the generated
         * builder by
         *
         * <p>@Component.Builder
         */
        DaggerAppComponent.builder().application(this).build().inject(this);

        AppLogger.init();

        syncWordJobManagerInitializer.initialize(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
