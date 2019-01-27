package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.*;

public class EditProfileActivity extends AppCompatActivity {
    String TAG = "EditProfileActivity";
    User me;
    String image_byte;
    Bundle arguments;
    ImageView imageView;
    Button button, ready;
    DataBaseHelper dbhelper;
    TextView username;
    EditText connect_link, et_email, et_specialization, lg_discription;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        arguments = getIntent().getExtras();

        username = findViewById(R.id.username);
        username.setText(arguments.get("username").toString());
        connect_link = findViewById(R.id.et_link);
        connect_link.setText(arguments.get("link").toString());
        et_email = findViewById(R.id.et_email);
        et_email.setText(arguments.get("email").toString());
        et_specialization = findViewById(R.id.et_specialization);
        et_specialization.setText(arguments.get("specialisation").toString());
        lg_discription = findViewById(R.id.lg_discription);
        lg_discription.setText(arguments.get("description").toString());

        imageView = findViewById(R.id.profile);
        imageView.setImageResource(R.drawable.logo);

        ready = (Button) findViewById(R.id.ready);
        ready.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper = new DataBaseHelper(EditProfileActivity.this, "editprofile");
                Log.e(TAG, "hERE");
                getUserByToken();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }
    void getUserByToken() {
        String token = dbhelper.getToken();
        try {
            NetworkService.getInstance().getUserAPI().getUser(new SerializedToken(token)).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "hERE");
                        sendNewData(response.body());
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e("EditProfileActivity", "Err1");
            e.printStackTrace();
        }
    }
    void sendNewData(User m) {
        try {
            NetworkService.getInstance().getUserAPI().updateUser(new ToRefreshProfile(et_email.getText().toString(), m.getId(), m.getPassword(), m.getToken(), m.getUsername(), connect_link.getText().toString(), et_specialization.getText().toString(), lg_discription.getText().toString(), image_byte)).enqueue(new Callback<ToRefreshProfile>() {
                @Override
                public void onResponse(Call<ToRefreshProfile> call, Response<ToRefreshProfile> response) {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "hERE");
                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                @Override
                public void onFailure(Call<ToRefreshProfile> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e("EditProfileActivity", "Err2");
            e.printStackTrace();
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            //imageView.setImageURI(imageUri);
/*            ImageView avatarImageView = (ImageView)findViewById(R.id.imageView);

            Picasso.get()
                    .load(imageUri)
                    .transform(new CircularTransformation(0))
                    .into(avatarImageView);*/
            imageView = findViewById(R.id.profile);
            Picasso.get()
                    .load(imageUri)
                    .resize(60, 60)
                    .centerCrop()
                    .transform(new CircularTransformation(0))
                    .into(imageView);

            ImageView ideapick = (ImageView)findViewById(R.id.profile);

            ideapick.buildDrawingCache();
            Bitmap bitmap = ideapick.getDrawingCache();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            image_byte = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            Log.e(TAG, image_byte);
        }
    }
}

