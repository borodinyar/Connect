package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionsActivity extends AppCompatActivity {
    String TAG = "SubscriptionsActivity";
    ArrayList<ModelSub> listSubs;
    List<User> allSubs;
    Bundle arguments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        arguments = getIntent().getExtras();
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionsActivity.this, IdeaProfileActivity.class);
                intent.putExtra("my", arguments.get("my").toString());
                intent.putExtra("id", arguments.get("id").toString());
                intent.putExtra("likes", arguments.get("likes").toString());
                intent.putExtra("dislikes", arguments.get("dislikes").toString());
                intent.putExtra("title", arguments.get("title").toString());
                intent.putExtra("short", arguments.get("short").toString());
                intent.putExtra("long", arguments.get("long").toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        loadAllSubs();
    }
    void loadAllSubs() {
        try {
            NetworkService.getInstance().getIdeasAPI().getAllFollowers(Integer.parseInt(arguments.get("id").toString())).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        allSubs = response.body();
                        Log.e("TAG", "" + allSubs.size());
                        listSubs = new ArrayList<ModelSub>();
                        for(User u : allSubs) {
                            listSubs.add(new ModelSub(u));
                        }
                        initAll();
                    }
                }
                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "Err2");
            e.printStackTrace();
        }
    }

    void initAll() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_subscription);
        Log.e("TAG", "" + listSubs.size());
        AdapterSub adapter = new AdapterSub(getApplicationContext(), listSubs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent intent = new Intent(SubscriptionsActivity.this, AnotherUserProfile.class);
                intent.putExtra("username", listSubs.get(position).getUsername());
                intent.putExtra("link", listSubs.get(position).getLink());
                intent.putExtra("specialisation", listSubs.get(position).getSpecialization());
                intent.putExtra("image", listSubs.get(position).getImage());

                intent.putExtra("my", arguments.get("my").toString());
                intent.putExtra("id", arguments.get("id").toString());
                intent.putExtra("likes", arguments.get("likes").toString());
                intent.putExtra("dislikes", arguments.get("dislikes").toString());
                intent.putExtra("title", arguments.get("title").toString());
                intent.putExtra("short", arguments.get("short").toString());
                intent.putExtra("long", arguments.get("long").toString());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {}
        }));
    }
}
