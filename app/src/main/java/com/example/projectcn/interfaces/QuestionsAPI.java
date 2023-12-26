package com.example.projectcn.interfaces;

import com.example.projectcn.model.QuestionsRespone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionsAPI {
    @GET("questions/getQsQuizId/{quizId}")
    Call<List<QuestionsRespone>> getQuestions(@Path("quizId") Long categoryId);
}
