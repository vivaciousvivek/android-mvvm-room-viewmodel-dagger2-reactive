package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service.LocalWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.service.RemoteWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class AppRepositoryImpl implements AppRepository {
//    private final AppRoomDatabase db;

    private final LocalWordService localWordService;
    private final RemoteWordService remoteWordService;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public AppRepositoryImpl(LocalWordService localWordService, RemoteWordService remoteWordService, SchedulerProvider schedulerProvider) {
        this.localWordService = localWordService;
        this.remoteWordService = remoteWordService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Flowable<List<Word>> getAllWords() {
        return localWordService.getAllWords();
    }

    @Override
    public Completable insert(Word word) {
        return localWordService.insert(word).andThen(remoteWordService.sync(word));
    }

    @Override
    public Completable update(Word word) {
        return localWordService.update(word).andThen(remoteWordService.sync(word));
    }

    @Override
    public Completable delete(Word word) {
        return localWordService.delete(word).andThen(remoteWordService.sync(word));
    }

    @Override
    public Completable deleteAll() {
        // neet to implement
//        return localWordService.deleteAll().andThen(remoteWordService.sync(word, api));
        return null;
    }

/*@Inject
    public AppRepositoryImpl(AppRoomDatabase db) {
        this.db = db;
    }*/

    /**
     * Get all words from dao layer, Room executes all queries on a separate thread. Observed LiveData
     * will notify the observer when the data has changed.
     *
     * @return
     */
    /*@Override
    public LiveData<List<Word>> getAllWords() {
        return db.wordDao().getAllWords();
    }*/

    /**
     * Room ensures that you don't do any long-running operations on the main thread, blocking the UI.
     * So we used AsyncTask to create separate thread to do this task
     *
     * @param word
     */
    /*@Override
    public void insert(Word word) {
        new InsertAsyncTask(db.wordDao()).execute(word);
    }

    private class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDaoAsync;

        public InsertAsyncTask(WordDao wordDao) {
            wordDaoAsync = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDaoAsync.insert(words[0]);
            return null;
        }
    }*/
}
