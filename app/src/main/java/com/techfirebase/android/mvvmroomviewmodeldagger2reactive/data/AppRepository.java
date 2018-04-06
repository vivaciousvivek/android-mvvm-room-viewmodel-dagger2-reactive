package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.api.Resource;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;
import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.service.LocalWordService;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by VIVEK KUMAR SINGH on 3/27/2018.
 */
public interface AppRepository extends LocalWordService {
    Flowable<Resource<List<Word>>> loadWords();


    /*LiveData<List<Word>> getAllWords();

    void insert(Word word);*/
}
