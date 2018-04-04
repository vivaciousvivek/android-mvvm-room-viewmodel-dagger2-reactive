package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob;

import com.jakewharton.rxrelay2.PublishRelay;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.SyncResponse;

import io.reactivex.Observable;

//@Singleton
public class SyncWordRxBus {
    private static SyncWordRxBus instance;
    private final PublishRelay<SyncWordResponse> relay;

    public static synchronized SyncWordRxBus getInstance() {
        if (instance == null) {
            instance = new SyncWordRxBus();
        }
        return instance;
    }

    private SyncWordRxBus() {
        relay = PublishRelay.create();
    }

    public void post(SyncResponse eventType, Word word) {
        relay.accept(new SyncWordResponse(eventType, word));
    }

    public Observable<SyncWordResponse> toObservable() {
        return relay;
    }
}
