package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob;

import android.support.annotation.WorkerThread;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service.LocalWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.AppLogger;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.SyncResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * This class is called by {@link SyncWordJobManagerInitializer}
 */
@Singleton
public class SyncWordResponseObserver {

    private final LocalWordService localWordService;

    @Inject
    public SyncWordResponseObserver(LocalWordService localWordService) {
        this.localWordService = localWordService;
    }

    void observeSyncResponse() {
        SyncWordRxBus.getInstance().toObservable()
                .subscribe(this::handleSyncResponse,
                        t -> AppLogger.e(t, "error handling sync response"));
    }

    private void handleSyncResponse(SyncWordResponse response) {
        if (response.eventType == SyncResponse.SUCCESS) {
            onSyncWordSuccess(response.word);
        } else {
            onSyncWordFailed(response.word);
        }
    }

    @WorkerThread
    private void onSyncWordSuccess(Word word) {
        AppLogger.d("received sync word success event for word %s", word);
        localWordService.update(word)
                .subscribe(() -> AppLogger.d("update word success"),
                        t -> AppLogger.e(t, "update word error"));
    }

    @WorkerThread
    private void onSyncWordFailed(Word word) {
        AppLogger.d("received sync word failed event for word %s", word);
        localWordService.delete(word)
                .subscribe(() -> AppLogger.d("update word success"),
                        t -> AppLogger.e(t, "update word error"));
    }
}
