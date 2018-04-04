package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.remote;

import com.techfirebase.android.mvvmroomviewmodeldagger2reactive.data.domain.entity.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by VIVEK KUMAR SINGH on 4/2/2018.
 * <p>
 * <p>REST API access points
 */
public interface AppRetrofitApi {
    @GET("word")
    Call<List<Word>> getAllWords();
//  LiveData<ApiResponse<List<Word>>> getAllWords();

    @POST("word")
    Call<Word> saveWord(@Body Word newWord);
//  LiveData<ApiResponse<Word>> saveWord(@Body Word newWord);
}
