package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.service;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;

import io.reactivex.Completable;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * Responsible to sync local(sqlite) data to the remote
 */
public interface RemoteWordService {
    Completable sync(Word word);
}
