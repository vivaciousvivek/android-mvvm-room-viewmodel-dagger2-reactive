package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.service;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.AppRetrofitApi;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote.backgroungjob.SyncWordJob;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.jobscheduling.JobManagerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

/**
 * Created by VIVEK KUMAR SINGH on 4/4/2018.
 *
 * <p>
 *
 * <p>Responsible for syncing data to remote for Word entity
 */
public class RemoteWordServiceImpl implements RemoteWordService {

  @Override
  public Completable sync(Word word) {
    return Completable.fromAction(
        () -> JobManagerFactory.getJobManager().addJobInBackground(new SyncWordJob(word)));
  }
}
