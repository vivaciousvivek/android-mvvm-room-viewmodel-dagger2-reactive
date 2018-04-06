package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by VIVEK KUMAR SINGH on 3/24/2018.
 */
@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Word> item);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("DELETE from Word")
    void deleteAll();

    /**
     * Once loaded initially, our View data always stays up-to-date by virtue of using Flowable. There
     * are no explicit calls to refresh data. This makes the interaction between our data and UI truly
     * Reactive.
     */
    @Query("SELECT * from Word ORDER BY word ASC")
    Flowable<List<Word>> getAllWords();
}
