package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PutIdeasTask extends AsyncTask<Void, Void, Void> {
    Context context;
    int id;
    Idea idea;
    ToPost post;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    public PutIdeasTask(Context context, Idea idea, int id) {
        this.context = context;
        this.idea = idea;
        this.id = id;
        post = new ToPost(idea);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            NetworkService.getInstance()
                    .getIdeasAPI()
                    .updateIdea(id, post).enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> call, Response<Idea> response) {
                    if (response.isSuccessful()) {
                        Log.i("OK", "Everything is OK!!!");
                    }
                }

                @Override
                public void onFailure(Call<Idea> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}

