package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.*;

public class ProfileActivity extends AppCompatActivity {
    String TAG = "ProfileActivity";
    ImageView imageView;
    Button button;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    User me;
    DataBaseHelper dbhelper;

    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Получаем данные о себе по токену
        dbhelper = new DataBaseHelper(this, "profile");
        String token = dbhelper.getToken();
        getProfileData(token);
    }

    void getProfileData(String token) {
        try {
            NetworkService.getInstance().getUserAPI().getUser(new SerializedToken(token)).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        me = response.body();
                        TextView username = findViewById(R.id.username);
                        username.setText("alsoalgo");

                        TextView tv_link = findViewById(R.id.tv_link);
                        tv_link.setText("vk.com/alsoalgo");

                        TextView tv_email = findViewById(R.id.tv_email);
                        tv_email.setText("mail@mail.ru");

                        TextView tv_specialization = findViewById(R.id.tv_specialization);
                        tv_specialization.setText("BackEnd dev");

                        ImageView av = findViewById(R.id.profile);


                        av.setImageResource(R.drawable.logo);

                        TextView textView10 = findViewById(R.id.textView10);
                        textView10.setText("Java dev");
                        connectButton();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "Err");
            e.printStackTrace();
        }

    }
    void connectButton() {
        ConstraintLayout layout = findViewById(R.id.editingprofile);
        ImageButton edit = layout.findViewById(R.id.edit);
        ConstraintLayout layout_include = layout.findViewById(R.id.include);
        ImageButton back = layout_include.findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                dbhelper = new DataBaseHelper(ProfileActivity.this, "profile");
                String token = dbhelper.getToken();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("token", me.getToken());
                intent.putExtra("login", me.getUsername());
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("username", me.getUsername());
                intent.putExtra("image", me.getImage());
                intent.putExtra("email", me.getEmail());
                intent.putExtra("link", me.getLink() == null ? "" : me.getLink());
                intent.putExtra("specialisation", me.getSpecialization() == null ? "" : me.getSpecialization());
                intent.putExtra("description", me.getDescription() == null ? "" : me.getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}