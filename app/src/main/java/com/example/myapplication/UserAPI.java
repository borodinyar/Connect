package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    @POST("auth")
    public Call<User> authorize(@Body ToAuthorize request);

    @GET("users")
    public Call<List<User>> getAllUsers();

    @POST("users")
    public Call<User> register(@Body ToRegister request);

    @PUT("users")
    public Call<ToRefreshProfile> updateUser(@Body ToRefreshProfile profile);

    @POST("profile")
    public Call<User> getUser(@Body SerializedToken tkn);

}