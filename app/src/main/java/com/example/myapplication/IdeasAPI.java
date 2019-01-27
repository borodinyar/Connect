package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IdeasAPI {
    @GET("ideas/{id}")
    public Call<Idea> getIdeaWithID(@Path("id") int id);

    @GET("ideas")
    public Call<List<Idea>> getAllIdeas();

    @POST("my_ideas")
    public Call<List<Idea>> getMyIdeas(@Body SerializedToken tkn);

    @POST("ideas")
    public Call<Idea> postIdea(@Body ToPost request);

    @POST("ideas/{id}/make_favorite")
    Call<Object> addToFavorites(@Path("id") int id, @Body SerializedToken tkn);

    @POST("ideas/{id}/favorites")
    Call<List<User>> getAllSubs(@Path("id") int id, @Body SerializedToken tkn);

    @POST("ideas/{id}/like")
    Call<LDS> likeIdea(@Path("id") int id, @Body SerializedToken tkn);

    @POST("ideas/{id}/dizlike")
    Call<LDS> dislikeIdea(@Path("id") int id, @Body SerializedToken tkn);

    @GET("ideas/{id}/favorites")
    Call<List<User>> getAllFollowers(@Path("id") int id);

    @POST("favorite_ideas")
    Call<List<Idea>> getAllFavorites(@Body SerializedToken tkn);

    @PUT("ideas/{id}")
    Call<Idea> updateIdea(@Path("id") int id, @Body ToPost idea);

    @DELETE("ideas/{id}")
    Call<Idea> deleteIdea(@Path("id") int id);
}
