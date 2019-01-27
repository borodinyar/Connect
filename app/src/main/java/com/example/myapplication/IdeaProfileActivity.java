package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.*;

public class IdeaProfileActivity
        extends AppCompatActivity {
    final boolean[] like_flag = {false};
    final boolean[] dislike_flag = {false};
    TextView name, author, description;
    private boolean flag = false;
    private ImageButton CounterButton;
    DataBaseHelper dbhelper;
    Bundle arguments;
    String token;
    String TAG = "IdeaProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getIntent().getExtras();

        if (arguments.get("my").toString().equals("true".toString())) {
            setContentView(R.layout.activity_editing_user_idea);
            ImageButton editButton = findViewById(R.id.edit);
            editButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IdeaProfileActivity.this, EditIdeaActivity.class);

                    intent.putExtra("id", arguments.get("id").toString());
                    intent.putExtra("likes", arguments.get("likes").toString());
                    intent.putExtra("dislikes", arguments.get("dislikes").toString());
                    intent.putExtra("title", arguments.get("title").toString());
                    intent.putExtra("short", arguments.get("short").toString());
                    intent.putExtra("long", arguments.get("long").toString());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            ImageButton button = (ImageButton) findViewById(R.id.back);
            Button delete = findViewById(R.id.delete);
            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        NetworkService.getInstance().getIdeasAPI().deleteIdea(Integer.parseInt(arguments.get("id").toString())).enqueue(new Callback<Idea>() {
                            @Override
                            public void onResponse(Call<Idea> call, Response<Idea> response) {
                                if (response.isSuccessful()) {
                                    IdeaProfileActivity.this.finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<Idea> call, Throwable t) {}
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "Err1");
                        e.printStackTrace();
                    }
                }
            });
            ImageView image = (ImageView) findViewById(R.id.iv);

            String image_byte = arguments.get("image").toString();
            byte[] decodedString = Base64.decode(image_byte.getBytes(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(decodedByte);

            name = (TextView) findViewById(R.id.name);
            name.setText(arguments.get("title").toString());
            description = (TextView) findViewById(R.id.description);
            description.setText(arguments.get("long").toString());
            TextView likes = findViewById(R.id.textView2);
            TextView dislikes = findViewById(R.id.textView3);
            likes.setText(arguments.get("likes").toString());
            dislikes.setText(arguments.get("dislikes").toString());
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IdeaProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    IdeaProfileActivity.this.finish();
                }
            });

            Button subscr = findViewById(R.id.subscr);
            subscr.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IdeaProfileActivity.this, SubscriptionsActivity.class);
                    intent.putExtra("my", arguments.get("my").toString());
                    intent.putExtra("id", arguments.get("id").toString());
                    intent.putExtra("image", arguments.get("image").toString());
                    intent.putExtra("likes", arguments.get("likes").toString());
                    intent.putExtra("dislikes", arguments.get("dislikes").toString());
                    intent.putExtra("title", arguments.get("title").toString());
                    intent.putExtra("short", arguments.get("short").toString());
                    intent.putExtra("long", arguments.get("long").toString());

                    startActivity(intent);
                }
            });
        } else {
            setContentView(R.layout.activity_idea_profile);
            //ImageButton button = (ImageButton) findViewById(R.id.back);
            CounterButton = (ImageButton) findViewById(R.id.imageButton);
            CounterButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = !flag;
                    if (flag) {
                        CounterButton.setColorFilter(Color.parseColor("#CBCC00"));
                    } else {
                        CounterButton.setColorFilter(Color.parseColor("#92a2bc"));
                    }
                }
            });
            final ImageButton button = (ImageButton) findViewById(R.id.back);

            ImageView image1 = (ImageView) findViewById(R.id.iv);
            //String image = arguments.get("image").toString();
            String image_byte = arguments.get("image").toString();
            byte[] decodedString = Base64.decode(image_byte.getBytes(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image1.setImageBitmap(decodedByte);

            name = (TextView) findViewById(R.id.name);
            name.setText(arguments.get("title").toString());
            author = (TextView) findViewById(R.id.generated);
            author.setText("" + arguments.get("author").toString());
            description = (TextView) findViewById(R.id.description);
            description.setText(arguments.get("long").toString());
            final TextView likes = findViewById(R.id.textView2);
            TextView dislikes = findViewById(R.id.textView3);
            likes.setText("0");
            dislikes.setText("0");
            dbhelper = new DataBaseHelper(this, "ideaprofile");
            token = dbhelper.getToken();
            Button but = findViewById(R.id.subscript);
            final ImageButton positives_button = findViewById(R.id.positives_button);
            final ImageButton negatives_button = findViewById(R.id.negatives_button);
            but.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IdeaProfileActivity.this, SubscriptionsActivity.class);
                    intent.putExtra("my", arguments.get("my").toString());
                    intent.putExtra("id", arguments.get("id").toString());
                    intent.putExtra("image", arguments.get("image").toString());
                    intent.putExtra("likes", arguments.get("likes").toString());
                    intent.putExtra("dislikes", arguments.get("dislikes").toString());
                    intent.putExtra("title", arguments.get("title").toString());
                    intent.putExtra("short", arguments.get("short").toString());
                    intent.putExtra("long", arguments.get("long").toString());
                    startActivity(intent);
                }
            });
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    sayFavourite();

                }
            });
            positives_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    likes.setText("1");
                    if (like_flag[0] && !dislike_flag[0]) {
                        like_flag[0] = !like_flag[0];
                        positives_button.setColorFilter(Color.parseColor("#737373"));
                    }else if (!like_flag[0] && !dislike_flag[0]) {
                        positives_button.setColorFilter(Color.parseColor("#00CC3A"));
                        like_flag[0] = true;
                    } else if (!like_flag[0] && dislike_flag[0]) {
                        positives_button.setColorFilter(Color.parseColor("#00CC3A"));
                        negatives_button.setColorFilter(Color.parseColor("#737373"));
                        dislike_flag[0] = false;
                        like_flag[0] = true;
                    }
                }
            });
            negatives_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!like_flag[0] && dislike_flag[0]) {
                        dislike_flag[0] = !dislike_flag[0];
                        negatives_button.setColorFilter(Color.parseColor("#737373"));
                    } else if (!like_flag[0] && !dislike_flag[0]) {
                        negatives_button.setColorFilter(Color.parseColor("#AB0000"));
                        dislike_flag[0] = true;
                    } else if (like_flag[0] && !dislike_flag[0]) {
                        negatives_button.setColorFilter(Color.parseColor("#AB0000"));
                        positives_button.setColorFilter(Color.parseColor("#737373"));
                        dislike_flag[0] = true;
                        like_flag[0] = false;
                    }
                }
            });
        }
    }

    void sayFavourite() {
        try {
            NetworkService.getInstance().getIdeasAPI().addToFavorites(Integer.parseInt(arguments.get("id").toString()), new SerializedToken(token)).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    IdeaProfileActivity.this.finish();
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    IdeaProfileActivity.this.finish();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Err1");
            e.printStackTrace();
        }
    }

    void like() {
        try {
            NetworkService.getInstance().getIdeasAPI().likeIdea(Integer.parseInt(arguments.get("id").toString()), new SerializedToken(token)).enqueue(new Callback<LDS>() {
                @Override
                public void onResponse(Call<LDS> call, Response<LDS> response) {
                    if (response.isSuccessful()) {}
                }
                @Override
                public void onFailure(Call<LDS> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "ErrLike");
            e.printStackTrace();
        }
    }
    void dislike() {
        try {
            NetworkService.getInstance().getIdeasAPI().dislikeIdea(Integer.parseInt(arguments.get("id").toString()), new SerializedToken(token)).enqueue(new Callback<LDS>() {
                @Override
                public void onResponse(Call<LDS> call, Response<LDS> response) {
                    if (response.isSuccessful()) {
                        IdeaProfileActivity.this.finish();
                    }
                }
                @Override
                public void onFailure(Call<LDS> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "ErrDisLike");
            e.printStackTrace();
        }
    }
}
