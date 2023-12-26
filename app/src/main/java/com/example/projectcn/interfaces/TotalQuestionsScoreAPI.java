package com.example.projectcn.interfaces;

import com.example.projectcn.model.TotalQuestionsScore;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TotalQuestionsScoreAPI {
    @POST("totalScore/save")
    Call<TotalQuestionsScore> saveTotalScore(@Body TotalQuestionsScore totalScore);
}
