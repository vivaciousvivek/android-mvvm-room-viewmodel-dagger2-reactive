package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.WordSyncUtil;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.AppRetrofitApi;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.AppRetrofitApiService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.AppLogger;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.JobPriority;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.SyncResponse;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.jobscheduling.RemoteException;

import javax.inject.Inject;

public class SyncWordJob extends Job {

    private static final String TAG = SyncWordJob.class.getCanonicalName();
    private final Word word;
    private final transient AppRetrofitApi api;

    public SyncWordJob(Word word) {
        super(new Params(JobPriority.MID.getPriority()).requireNetwork().groupBy(TAG).persist());
        this.word = word;
        api = AppRetrofitApiService.getINSTANCE().getRetrofit().create(AppRetrofitApi.class);
    }

    @Override
    public void onAdded() {
        AppLogger.d("Executing onAdded() for Word ", word);
    }

    @Override
    public void onRun() throws Throwable {
        AppLogger.d("Executing onRun() for Word ", word);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        api.saveWord(word);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no
        // longer pending
        Word updatedWord = WordSyncUtil.clone(word, false);
        SyncWordRxBus.getInstance().post(SyncResponse.SUCCESS, updatedWord);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        AppLogger.d("canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        // sync to remote failed
        SyncWordRxBus.getInstance().post(SyncResponse.FAILED, word);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(
            @NonNull Throwable throwable, int runCount, int maxRunCount) {
        AppLogger.d("Executing shouldReRunOnThrowable() for Word ", word);
        if (throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode < 500) {
                return RetryConstraint.CANCEL;
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY;
    }
}
