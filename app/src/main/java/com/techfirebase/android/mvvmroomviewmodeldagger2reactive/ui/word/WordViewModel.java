package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.word;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.AppRepository;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.BaseViewModel;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.rx.SchedulerProvider;

/**
 * Created by VIVEK KUMAR SINGH on 3/27/2018.
 */
public class WordViewModel extends BaseViewModel<WordNavigator> {
    //      @Inject
    AppRepository repository;

    //    public WordViewModel(@NonNull Application application) {
    //        super(application);
    //    }

    public WordViewModel(AppRepository appRepository, SchedulerProvider schedulerProvider) {
        super(appRepository, schedulerProvider);
        this.repository = appRepository;
    }

    /*public LiveData<List<Word>> getAllWords() {
        return repository.getAllWords();
    }

    public void insert(Word word) {
        repository.insert(word);
    }*/

    //  public LiveData<List<Word>> getAllWords() {
    //    return getCompositeDisposable().add()
    //  }
    //
    //  public void insert(Word word) {
    //    repository.insert(word);
    //  }

    public void openMainActivity() {
        getNavigator().openMainActivity();
    }
}
