package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.api.ApiResponse;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.api.Resource;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by VIVEK KUMAR SINGH on 4/2/2018.
 * <p>
 * <p>Generic helper class that can provide a resource backed by both the sqlite database and the
 * network.
 * <p>
 * <p>It starts by observing database for the resource. When the entry is loaded from the database
 * for the first time, NetworkBoundResource checks whether the result is good enough to be
 * dispatched and/or it should be fetched from network. Note that both of these can happen at the
 * same time since you probably want to show the cached data while updating it from the network.
 * <p>
 * <p>If the network call completes successfully, it saves the response into the database and
 * re-initializes the stream. If network request fails, we dispatch a failure directly.
 *
 * @param <LocalType>:  Type for the Resource data
 * @param <RemoteType>: Type for the API response
 */
public abstract class NetworkBoundResource<LocalType, RemoteType> {
//    private final SchedulerProvider schedulerProvider;
//    private final MediatorLiveData<Resource<LocalType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(FlowableEmitter<Resource<LocalType>> emitter) {
        Disposable firstDataDisposable = loadFromDb()
                .map(Resource::loading)
                .subscribe(emitter::onNext);

        createCall()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(remoteTypeData -> {
                    firstDataDisposable.dispose();
                    saveCallResult(remoteTypeData);
                    loadFromDb().map(Resource::success).subscribe(emitter::onNext);
                });
        /*
         * This method must be called from the main thread. If you need set a value from a background
         * thread, you can use postValue(Object)
         */
        /*result.setValue(Resource.loading(null));

        Flowable<LocalType> dbSource = loadFromDb();
        result.addSource(
                dbSource,
                data -> {
                    result.removeSource(dbSource);

                    if (shouldFetch(data)) {
                        fetchFromNetwork(dbSource);
                    } else {
                        result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
                    }
                });*/
    }

    private Single<RemoteType> fetchFromNetwork() {
        return null;
    }

    /**
     * Fetch from network if data is not available in cache(sqllite)
     *
     */
//    private void fetchFromNetwork(final LiveData<LocalType> dbSource) {
//        // TODO: 4/2/2018 need to check how to do extract data from list of words(response)
//        Single<ApiResponse<RemoteType>> apiResponse = createCall();
//        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
//        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
//        result.addSource(
//                apiResponse,
//                response -> {
//                    result.removeSource(apiResponse);
//                    result.removeSource(dbSource);
//
//                    if (response.isSuccessful()) {
////            schedulerProvider.getDiskIO().execute(() -> saveCallResult(processResponse(response)));
////            schedulerProvider
////                .getMainThread()
////                .execute(
////                    () ->
////                        /**
////                         * we specially request a new live data, otherwise we'll get immediately
////                         * last cached value, which may not be updated with latest results received
////                         * from network.
////                         */
////                        result.addSource(
////                            loadFromDb(), newData -> setValue(Resource.success(newData))));
//                    } else {
//                        onFetchFailed();
//                        result.addSource(
//                                dbSource, newData -> setValue(Resource.error(newData, response.errorMessage)));
//                    }
//                });
//    }

    @WorkerThread
    private RemoteType processResponse(ApiResponse<RemoteType> response) {
        return response.body;
    }

    /**
     * Called to save the result of the API response into the database
     *
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RemoteType item);

    /**
     * Called to get the cached data from the database
     *
     * @return
     */
    @NonNull
    @MainThread
    protected abstract Flowable<LocalType> loadFromDb();

    /**
     * Called to create the API call.
     *
     * @return
     */
    @NonNull
    @MainThread
    protected abstract Single<RemoteType> createCall();

    /**
     * Called when the fetch fails. The child class may want to reset components like rate limiter.
     */
    @MainThread
    protected void onFetchFailed() {
    }
}
