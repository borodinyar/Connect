package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PostIdeasTask extends AsyncTask<Void, Void, Void> {
    Context context;
    Idea idea;
    ToPost post;
    DataBaseHelper db;
    String TAG = "PostIdeasTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public PostIdeasTask(
            Context context,
            Idea idea, String token) {
        this.context = context;
        this.idea = idea;
        this.db = new DataBaseHelper(context, "post");
        this.post = new ToPost(idea, token);
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}

