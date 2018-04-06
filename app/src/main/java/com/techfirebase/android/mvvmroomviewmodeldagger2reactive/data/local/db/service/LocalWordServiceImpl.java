package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.AppRoomDatabase;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.dao.WordDao;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.AppLogger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 * <p>
 * <p>Responsible for CRUD operations against Word entity
 */
@Singleton
public class LocalWordServiceImpl implements LocalWordService {

    private final WordDao wordDao;

    @Inject
    public LocalWordServiceImpl(AppRoomDatabase db) {
        this.wordDao = db.wordDao();
    }

    /**
     * Get all words
     *
     * @return
     */
    @Override
    public Flowable<List<Word>> getAllWords() {
        AppLogger.d("getting all words");
        return wordDao.getAllWords();
    }

    /**
     * Insert a word
     *
     * @param word
     * @return
     */
    @Override
    public Completable insert(Word word) {
    /*return Single.fromCallable(
        () -> {
          // TODO: 04/04/18 will use Logging here
          wordDao.insert(word);
          return WordSyncUtil.clone(word);
        });*/
        AppLogger.d("inserting word %s", word);
        return Completable.fromAction(() -> wordDao.insert(word));
    }

    @Override
    public Completable insertAll(List<Word> word) {
        AppLogger.d("inserting all words %s", word);
        return Completable.fromAction(() -> wordDao.insertAll(word));
    }

    /**
     * Updatre a word
     *
     * @param word
     * @return
     */
    @Override
    public Completable update(Word word) {
        AppLogger.d("updating word sync status for word string %s", word.getWord());
        return Completable.fromAction(() -> wordDao.update(word));
    }

    /**
     * Delete a word
     *
     * @param word
     * @return
     */
    @Override
    public Completable delete(Word word) {
        AppLogger.d("deleting word with word string %s", word.getWord());
        return Completable.fromAction(() -> wordDao.delete(word));
    }

    /**
     * Delete all words
     *
     * @return
     */
    @Override
    public Completable deleteAll() {
        AppLogger.d("deleting all words");
        return Completable.fromAction(() -> wordDao.deleteAll());
    }
}
