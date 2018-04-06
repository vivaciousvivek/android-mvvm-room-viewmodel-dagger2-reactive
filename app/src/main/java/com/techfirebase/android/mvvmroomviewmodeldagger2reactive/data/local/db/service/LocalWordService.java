package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * Responsible for CRUD operations against Word entity
 */
public interface LocalWordService {
    Flowable<List<Word>> getAllWords();

    Completable insert(Word word);

    Completable insertAll(List<Word> word);

    Completable update(Word word);

    Completable delete(Word word);

    Completable deleteAll();
}
