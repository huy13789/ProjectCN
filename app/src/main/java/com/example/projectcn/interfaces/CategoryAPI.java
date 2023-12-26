package com.example.projectcn.interfaces;

import com.example.projectcn.model.Category;
import com.example.projectcn.model.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryAPI {
    @GET("/category/getall")
    Call<List<Category>> getAllCategories();

    @GET("/category/getquizbyCateId/{categoryId}")
    Call<List<Quiz>> getQuizzesByCategoryId(@Path("categoryId") Long categoryId);

}
