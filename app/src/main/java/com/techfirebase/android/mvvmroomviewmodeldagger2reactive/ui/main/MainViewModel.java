package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.AppRepository;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.BaseViewModel;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.AppLogger;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by ﻿VIVEK KUMAR SINGH on 3/27/2018.
 *
 * <p>
 *
 * <p>Class is Intentionally blank as we don't have any data to show on its corresponding activity
 */
public class MainViewModel extends BaseViewModel<MainNavigator> {

    AppRepository repository;
    private final MutableLiveData<List<Word>> wordLiveData;

    public MainViewModel(AppRepository appRepository, SchedulerProvider schedulerProvider) {
        super(appRepository, schedulerProvider);
        this.repository = appRepository;
        this.wordLiveData = new MutableLiveData<>();
        //            getAllWords();
    }

    //    public LiveData<List<Word>> getAllWords() {
    //        return repository.getAllWords();
    //    }
    //
    //    public void insert(Word word) {
    //        repository.insert(word);
    //    }

    /**
     * As live data is required to the main activity so we converted flowable to livedata
     *
     * @return
     */
    public LiveData<List<Word>> getAllWords() {
        return LiveDataReactiveStreams.fromPublisher(getAppRepository()
                .getAllWords()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()));
    }

  /*public void getAllWords() {
    getCompositeDisposable()
        .add(
            getAppRepository()
                .getAllWords()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                    wordLiveData::setValue,
                    throwable -> AppLogger.e(throwable, "add comment error")));
  }*/

    public void insert(Word word) {
        getCompositeDisposable()
                .add(
                        getAppRepository()
                                .insert(word)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(
                                        () -> AppLogger.d("add comment success"),
                                        throwable -> AppLogger.e(throwable, "add comment error")));
    }
}
