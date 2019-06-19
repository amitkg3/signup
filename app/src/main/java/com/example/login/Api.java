package com.example.login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("users/")
    Call<ResponseBody> createUser(
            //@Field("name") String name,
            @Field("email") String email,
            //@Field("rollno") String rollNo,
            @Field("username") String userName,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );
}
