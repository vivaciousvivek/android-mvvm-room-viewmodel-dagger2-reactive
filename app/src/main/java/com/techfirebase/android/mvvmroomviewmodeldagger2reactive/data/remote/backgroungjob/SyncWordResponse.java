package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.constant.SyncResponse;

public class SyncWordResponse {
    public final SyncResponse eventType;
    public final Word word;

    public SyncWordResponse(SyncResponse eventType, Word word) {
        this.eventType = eventType;
        this.word = word;
    }
}
