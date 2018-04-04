package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob;

import android.content.Context;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.jobscheduling.JobManagerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * This class is called by {@link com.techfirebase.android.mvvmroomviewmodeldagger2reactive.MvvmApp}
 */
@Singleton
public class SyncWordJobManagerInitializer {
    private final SyncWordResponseObserver syncWordResponseObserver;

    @Inject
    public SyncWordJobManagerInitializer(SyncWordResponseObserver syncWordResponseObserver) {
        this.syncWordResponseObserver = syncWordResponseObserver;
    }

    public void initialize(Context context) {
        JobManagerFactory.getJobManager(context);
        syncWordResponseObserver.observeSyncResponse();
    }
}
