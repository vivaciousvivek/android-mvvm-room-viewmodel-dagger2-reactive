package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * <p>Responsible to create Word object with sync indicator
 * <p>
 * <p>will try to achieve this generically for every entity
 */
public class WordSyncUtil {
    public static Word clone(Word from, boolean syncPending) {
        return new Word(from.getWord(), from.getTimestamp(), syncPending);
    }

    public static Word clone(Word from) {
        return new Word(from.getWord(), from.getTimestamp(), from.isSyncPending());
    }

    // this will be used when we sync word object by its id.
    public static Word clone(Word from, long id) {
        //        return new Word(id, from.getWord(), from.getTimestamp(), from.isSyncPending());
        return new Word(from.getWord(), from.getTimestamp(), from.isSyncPending());
    }
}
