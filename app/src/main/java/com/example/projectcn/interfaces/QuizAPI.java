package com.example.projectcn.interfaces;

import com.example.projectcn.model.Quiz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuizAPI {
    @GET("quiz/{id}")
    Call<Quiz> getQuizById(@Path("id") String id);
}
