package com.example.projectcn.interfaces;

import com.example.projectcn.model.QuestionsScore;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QuestionScoreAPI {
    @POST("questionsscrore/submit")
    Call<ResponseBody> saveDetailScore (@Body List<QuestionsScore> questionsScore);
}
