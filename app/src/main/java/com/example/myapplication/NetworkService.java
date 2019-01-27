package com.example.myapplication;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://95.163.250.212";
    private static Retrofit mRetrofit;
    private static String TAG = "NetworkService";
    private NetworkService() {
        Log.e(TAG, "Came In");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Log.e(TAG, "Interceptor");
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Log.e(TAG, "SettedLevel");
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        Log.e(TAG, "client");
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        Log.e(TAG, "Exit");
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            Log.e(TAG, "In");
            mInstance = new NetworkService();
        }

        return mInstance;
    }

    public IdeasAPI getIdeasAPI() {
        return mRetrofit.create(IdeasAPI.class);
    }

    public UserAPI getUserAPI() {
        return mRetrofit.create(UserAPI.class);
    }
}
