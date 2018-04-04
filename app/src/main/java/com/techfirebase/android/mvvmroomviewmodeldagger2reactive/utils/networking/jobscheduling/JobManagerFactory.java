package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.jobscheduling;

import android.content.Context;
import android.os.Build;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import timber.log.Timber;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * <p>Responsible for syncing data to remote for Word entity
 * <p>
 * <p>Add RECEIVE_BOOT_COMPLETED permission to AndroidManifest.xml so that this application can be
 * woken up by the scheduler after the device is rebooted.
 */
public class JobManagerFactory {

    private static JobManager jobManager;

    /**
     * This JobManager will be used lollipop version onwards
     *
     * @return
     */
    public static synchronized JobManager getJobManager() {
        return jobManager;
    }

    /**
     * @param context
     * @return
     */
    public static synchronized JobManager getJobManager(Context context) {
        if (jobManager == null) {
            jobManager = configureJobManager(context);
        }
        return jobManager;
    }

    /**
     * Provide custom logger to log the JobManager details
     */
    private static CustomLogger customLogger =
            new CustomLogger() {

                @Override
                public boolean isDebugEnabled() {
                    return true;
                }

                @Override
                public void d(String text, Object... args) {
                    Timber.d(String.format(text, args));
                }

                @Override
                public void e(Throwable t, String text, Object... args) {
                    Timber.e(t, String.format(text, args));
                }

                @Override
                public void e(String text, Object... args) {
                    Timber.e(String.format(text, args));
                }

                @Override
                public void v(String text, Object... args) {
                    // no-op
                }
            };

    private static JobManager configureJobManager(Context context) {
        Configuration.Builder builder =
                new Configuration.Builder(context)
                        .minConsumerCount(1) // always keep at least one consumer alive
                        .maxConsumerCount(3) // up to 3 consumers at a time
                        .loadFactor(3) // 3 jobs per consumer
                        .consumerKeepAlive(120) // wait 2 minutes
                        .customLogger(customLogger);

        // we are setting batch param below to false so that sync results are observed and acted upon in
        // the background.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.scheduler(
                    FrameworkJobSchedulerService.createSchedulerFor(context, SchedulerJobService.class),
                    false);
        } else {
            int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            if (enableGcm == ConnectionResult.SUCCESS) {
                builder.scheduler(
                        GcmJobSchedulerService.createSchedulerFor(context, GcmJobSchedulerService.class),
                        false);
            }
        }
        return new JobManager(builder.build());
    }
}
