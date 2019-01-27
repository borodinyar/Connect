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
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddIdeaActivity extends AppCompatActivity {
    String TAG = "AddIdeaActivity";
    ImageView IdeaImage;
    String image_byte;
    TextView IdeaTitle, ShortDescription, LongDescription;
    Button IdeaAddImage, ReadyToPost, CancelEditing;
    DataBaseHelper db;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idea);

        IdeaImage = (ImageView) findViewById(R.id.add_iv);

        IdeaAddImage = (Button) findViewById(R.id.add_button);
        View myLayout = findViewById(R.id.include2);
        ReadyToPost = (Button) myLayout.findViewById(R.id.ready);

        CancelEditing = (Button) myLayout.findViewById(R.id.cancel);

        IdeaTitle = (TextView) findViewById(R.id.editText3);
        ShortDescription = (TextView) findViewById(R.id.editText8);
        LongDescription = (TextView) findViewById(R.id.editText2);

        IdeaAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        ReadyToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String ideaTitle = IdeaTitle.getText().toString();
                //Toast.makeText(getApplicationContext(), (String)ideaTitle, Toast.LENGTH_LONG).show();
                String shortDescription = ShortDescription.getText().toString();
                String longDescription = LongDescription.getText().toString();
                try {
                    db = new DataBaseHelper(AddIdeaActivity.this, "add");
                    String token = db.getToken().toString();
                    Log.e(TAG, "!" + token + "!");
                    loadIdea(new ToPost(new Idea(ideaTitle, shortDescription, longDescription, image_byte), token));
                    AddIdeaActivity.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        CancelEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                //Можно проверить, точно ли он хочет выйти
                finish();
            }
        });
    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    void loadIdea(final ToPost post) {
        try {
            NetworkService.getInstance().getIdeasAPI().postIdea(post).enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> call, Response<Idea> response) {
                    Log.e(TAG, "Inside");
                    if (response.isSuccessful()) {
                        db.insertIdea(post.idea);
                    }
                }
                @Override
                public void onFailure(Call<Idea> call, Throwable t) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "Err");
            e.printStackTrace();
        }
    }

    private boolean check(String Title, String Short, String Long) {
        return (Title.length() <= 40 && Short.length() > 0 && Short.length() <= 140 && Long.length() >= 140);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            IdeaImage.setImageURI(imageUri);

            ImageView ideapick = (ImageView)findViewById(R.id.add_iv);

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
