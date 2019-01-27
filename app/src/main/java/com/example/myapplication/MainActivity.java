package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DataBaseReceiver {
    private static final String TAG = "MainActivity FIND";
    DataBaseHelper dbhelper;
    String token;
    TextView username;
    ArrayList<Model> listIdeas;
    Bundle arguments;
    User me;
    ArrayList<Model> AllIdeas;
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    @Override
    public void dataReceived(List<Idea> list) {

    };
    private RecyclerView recyclerView;
    private Adapter adapter;
    DataBaseReceiver r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        arguments = getIntent().getExtras();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout =
                navigationView.getHeaderView(0);
        username = headerLayout.findViewById(R.id.username1);
        username.setText(arguments.get("login").toString());
        navigationView.setNavigationItemSelectedListener(this);
        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper.clear();
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        //token = arguments.get("token").toString();

        username = navigationView.findViewById(R.id.username);
        //username.setText(arguments.get("login").toString());

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        dbhelper = new DataBaseHelper(this, "main");
        token = dbhelper.getToken();
        loadData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void loadData() {
        try {
            NetworkService.getInstance()
                    .getIdeasAPI()
                    .getAllIdeas().enqueue(new Callback<List<Idea>>() {
                @Override
                public void onResponse(Call<List<Idea>> call, Response<List<Idea>> response) {
                    List<Idea> ideas = response.body();
                    listIdeas = new ArrayList<Model>();
                    listIdeas.add(new Model(ideas.get(1)));
                    AllIdeas = listIdeas;
                    listIdeas.add(new Model(ideas.get(0)));

                    for (int i = 2; i < (ideas == null ? 0 : ideas.size()); i++) {
                        listIdeas.add(new Model(ideas.get(i)));
                        dbhelper.insertIdea(ideas.get(i));
                    }
                    initAll();
                }

                @Override
                public void onFailure(Call<List<Idea>> call, Throwable t) {
                    Log.e(TAG,"onFailure() inserted");
                }
            });
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    void initAll() {
        adapter = new Adapter(getApplicationContext(), listIdeas);
        Log.e(TAG, "adapter");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if (me == null) {
                    try {
                        NetworkService.getInstance().getUserAPI().getUser(new SerializedToken(token)).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    me = response.body();
                                    Model idea = listIdeas.get(position);
                                    Intent intent = new Intent(MainActivity.this, IdeaProfileActivity.class);
                                    Log.e(TAG, "" + idea.getAuthor());
                                    Log.e(TAG, "" + me.getId());
                                    if (idea.getAuthor() == me.getId()) {
                                        intent.putExtra("my", "true");
                                    } else {
                                        intent.putExtra("my", "false");
                                    }
                                    intent.putExtra("id", idea.getId());
                                    intent.putExtra("title", idea.getTitle());
                                    intent.putExtra("image", idea.getImage());
                                    intent.putExtra("author", idea.getAuthor());
                                    intent.putExtra("long", idea.getLongdesc());
                                    intent.putExtra("short", idea.getShortdesc());
                                    intent.putExtra("likes", idea.getLikes());
                                    intent.putExtra("dislikes", idea.getDislikes());
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {}
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "Err");
                        e.printStackTrace();
                    }
                } else {
                    Model idea = listIdeas.get(position);
                    Intent intent = new Intent(MainActivity.this, IdeaProfileActivity.class);
                    Log.e(TAG, "" + idea.getAuthor());
                    Log.e(TAG, "" + me.getId());
                    if (idea.getAuthor() == me.getId()) {
                        intent.putExtra("my", "true");
                    } else {
                        intent.putExtra("my", "false");
                    }
                    intent.putExtra("id", idea.getId());
                    intent.putExtra("title", idea.getTitle());
                    intent.putExtra("image", idea.getImage());
                    intent.putExtra("author", idea.getAuthor());
                    intent.putExtra("long", idea.getLongdesc());
                    intent.putExtra("short", idea.getShortdesc());
                    intent.putExtra("likes", idea.getLikes());
                    intent.putExtra("dislikes", idea.getDislikes());
                    startActivity(intent);
                }

            }
            @Override
            public void onLongClick(View view, int position) {}
        }));

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class); //Profile
            startActivity(intent);
        } else if (id == R.id.nav_ideas_feed) {
            loadData();
            initAll();
        } else if (id == R.id.nav_add_idea) {
            List<Idea> ideas = dbhelper.getAll();
            int prevSizeList  = ideas.size();
            Intent intent = new Intent(MainActivity.this, AddIdeaActivity.class);//Add
            startActivity(intent);
            ideas = dbhelper.getAll();
            int curSizeList = ideas.size();
            if (prevSizeList != curSizeList) {
                listIdeas = new ArrayList<Model>();
                for (int i = 0; i < ideas.size(); i++) {
                    listIdeas.add(new Model(ideas.get(i)));
                }
                initAll();
            }
        } else if (id == R.id.nav_my_project) {
            getMyIdeas();
        } else if (id == R.id.nav_chosen) {
            getFavourite();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getMyIdeas() {
        try {
            NetworkService.getInstance()
                    .getIdeasAPI()
                    .getMyIdeas(new SerializedToken(token)).enqueue(new Callback<List<Idea>>() {
                @Override
                public void onResponse(Call<List<Idea>> call, Response<List<Idea>> response) {
                    List<Idea> ideas = response.body();
                    listIdeas = new ArrayList<Model>();
                    for (int i = 0; i < (ideas == null ? 0 : ideas.size()); i++) {
                        listIdeas.add(new Model(ideas.get(i)));
                    }
                    initAll();
                }

                @Override
                public void onFailure(Call<List<Idea>> call, Throwable t) {
                    Log.e(TAG,"onFailure() inserted");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getFavourite() {
        try {
            NetworkService.getInstance()
                    .getIdeasAPI()
                    .getAllFavorites(new SerializedToken(token)).enqueue(new Callback<List<Idea>>() {
                @Override
                public void onResponse(Call<List<Idea>> call, Response<List<Idea>> response) {
                    List<Idea> ideas = response.body();
                    Log.e(TAG, "" + ideas.size());
                    listIdeas = new ArrayList<Model>();
                    listIdeas.add(AllIdeas.get(0));
                    initAll();
                }

                @Override
                public void onFailure(Call<List<Idea>> call, Throwable t) {
                    Log.e(TAG,"onFailure() inserted");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }
}
