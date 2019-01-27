package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditIdeaActivity extends AppCompatActivity {
    String image_byte;
    private static final int PICK_IMAGE = 100;
    EditText title, shortDescription, longDescription;
    ImageView imageView;
    Button image_button, ready, cancel;
    Uri imageUri;
    int ideaId;
    Bundle arguments;
    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea_profile);

        arguments = getIntent().getExtras();

        imageView = findViewById(R.id.add_iv);

        title = findViewById(R.id.editText3);
        title.setText(arguments.get("title").toString());

        shortDescription = findViewById(R.id.editText8);
        shortDescription.setText(arguments.get("short").toString());

        longDescription = findViewById(R.id.editText2);
        longDescription.setText(arguments.get("long").toString());

        ideaId = Integer.parseInt(arguments.get("id").toString());

        image_button = findViewById(R.id.button2);
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ready = findViewById(R.id.ready);
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText)findViewById(R.id.editText3)).getText().toString();
                String shortDesc = ((EditText)findViewById(R.id.editText8)).getText().toString();
                String longDesc = ((EditText)findViewById(R.id.editText2)).getText().toString();
                loadIdea(new Idea(title, shortDesc, longDesc, image_byte), ideaId);
                EditIdeaActivity.this.finish();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditIdeaActivity.this, IdeaProfileActivity.class);
                intent.putExtra("my", "true");
                intent.putExtra("id", arguments.get("id").toString());
                intent.putExtra("likes", arguments.get("likes").toString());
                intent.putExtra("dislikes", arguments.get("dislikes").toString());
                intent.putExtra("long", arguments.get("long").toString());
                intent.putExtra("title", arguments.get("title").toString());
                intent.putExtra("short", arguments.get("short").toString());
                intent.putExtra("image", arguments.get("image").toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    void loadIdea(Idea idea, int id) {
        try {
            NetworkService.getInstance()
                    .getIdeasAPI()
                    .updateIdea(id, new ToPost(idea)).enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> call, Response<Idea> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(EditIdeaActivity.this, IdeaProfileActivity.class);
                        intent.putExtra("my", "true");
                        intent.putExtra("id", arguments.get("id").toString());
                        intent.putExtra("likes", arguments.get("likes").toString());
                        intent.putExtra("dislikes", arguments.get("dislikes").toString());
                        intent.putExtra("long", arguments.get("long").toString());
                        intent.putExtra("title", arguments.get("title").toString());
                        intent.putExtra("short", arguments.get("short").toString());
                        intent.putExtra("image", image_byte);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Idea> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView = findViewById(R.id.imageView3);
            imageView.setImageURI(imageUri);
            ImageView edit = findViewById(R.id.edit_iv3);
            edit.setImageURI(imageUri);
            ImageView ideapick = (ImageView)findViewById(R.id.imageView3);

            ideapick.buildDrawingCache();
            Bitmap bitmap = ideapick.getDrawingCache();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            image_byte = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        }
    }

}
