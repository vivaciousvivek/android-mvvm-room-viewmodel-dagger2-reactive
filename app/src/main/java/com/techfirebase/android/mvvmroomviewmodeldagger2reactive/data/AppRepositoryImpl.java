package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data;

import android.support.annotation.NonNull;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.api.Resource;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service.LocalWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.AppRetrofitApi;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.service.RemoteWordService;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.NetworkBoundResource;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class AppRepositoryImpl implements AppRepository {
    //    private final AppRoomDatabase db;

    private final LocalWordService localWordService;
    private final RemoteWordService remoteWordService;
    private final SchedulerProvider schedulerProvider;
    private final AppRetrofitApi api;

    @Inject
    public AppRepositoryImpl(
            LocalWordService localWordService,
            RemoteWordService remoteWordService,
            SchedulerProvider schedulerProvider,
            AppRetrofitApi api) {
        this.localWordService = localWordService;
        this.remoteWordService = remoteWordService;
        this.schedulerProvider = schedulerProvider;
        this.api = api;
    }

    public Flowable<Resource<List<Word>>> loadWords() {
        return Flowable.create(
                emitter ->
                        new NetworkBoundResource<List<Word>, List<Word>>(emitter) {
                            /*
                             * Implement abstract methods of this class
                             */
                            @Override
                            protected void saveCallResult(@NonNull List<Word> item) {
                                localWordService.insertAll(item);
                            }

                            @NonNull
                            @Override
                            protected Flowable<List<Word>> loadFromDb() {
                                return localWordService.getAllWords();
                            }

                            @NonNull
                            @Override
                            protected Single<List<Word>> createCall() {
                                return (Single<List<Word>>) api.getAllWords();
                            }

                            @Override
                            protected void onFetchFailed() {
                                super.onFetchFailed();
                                // need to implement
                            }
                        },
                BackpressureStrategy.BUFFER);
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
    public Completable insertAll(List<Word> word) {
        return null;
        //        return localWordService.insertAll(word).andThen(remoteWordService.sync(word));
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
