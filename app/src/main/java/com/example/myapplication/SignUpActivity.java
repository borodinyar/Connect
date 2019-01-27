package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity
        extends AppCompatActivity {
    private String login, email, pass;
    private User user;
    private Button button, signin;
    DataBaseHelper dbhelper;
    private String TAG = "SignUpActivity";
    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button = (Button) findViewById(R.id.Button);
        signin = (Button) findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Clicked????");
                login = ((MaterialEditText) findViewById(R.id.login)).getText().toString().trim();
                email = ((MaterialEditText) findViewById(R.id.e_mail)).getText().toString().trim();
                pass = ((MaterialEditText) findViewById(R.id.password)).getText().toString().trim();
                user = new User(email, pass, login);
                signUp();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    void signUp() {
        try {
            NetworkService.getInstance()
                    .getUserAPI().register(new ToRegister(user)).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        String token = response.body().getToken();
                        Log.e(TAG, token);
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.putExtra("token", token);
                        dbhelper = new DataBaseHelper(getApplicationContext(), "regin");
                        dbhelper.insertToken(token);
                        startActivity(intent);
                        SignUpActivity.this.finish();
                    } else {
                        Log.e(TAG, "Error");
                    }
                    Log.e(TAG, "Finished");
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e(TAG, "Failure");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
