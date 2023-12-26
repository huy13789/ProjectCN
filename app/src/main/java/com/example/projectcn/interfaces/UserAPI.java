package com.example.projectcn.interfaces;

import com.example.projectcn.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("user/login")
    Call<ResponseBody> loginUser(@Body User user);
    @POST("user/register")
    Call<ResponseBody> registerUser(@Body User user);
    @GET("user/{id}")
    Call<User> getUserById(@Path("id") String username);

}
