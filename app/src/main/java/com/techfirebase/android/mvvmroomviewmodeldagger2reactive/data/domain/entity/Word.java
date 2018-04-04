package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by VIVEK KUMAR SINGH on 3/24/2018.
 */
@Entity
public class Word {
    @PrimaryKey
    @NonNull
    private String word;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "sync_pending")
    private boolean syncPending;

    @Ignore
    public Word(String word) {
        this.word = word;
        this.timestamp = System.currentTimeMillis();
        this.syncPending = true;
    }

    public Word(@NonNull String word, long timestamp, boolean syncPending) {
        this.word = word;
        this.timestamp = timestamp;
        this.syncPending = syncPending;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }
}
