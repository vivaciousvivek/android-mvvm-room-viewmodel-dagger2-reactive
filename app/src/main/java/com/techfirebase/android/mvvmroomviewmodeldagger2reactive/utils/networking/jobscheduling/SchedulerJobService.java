package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.jobscheduling;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * <p>Need to add service to Manifest.xml
 * <p>
 * <p>﻿<service android:name=".utils.networking.jobscheduling.SchedulerJobService"
 * android:permission="android.permission.BIND_JOB_SERVICE" />
 */
public class SchedulerJobService extends FrameworkJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return JobManagerFactory.getJobManager();
    }
}
