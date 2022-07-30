package com.example.semesterprojectandroid;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

public class CreateNews extends AppCompatActivity {
    ImageView imageView;
    DbHandler db;
    TextInputLayout textInputLayout;
    TextInputLayout textInputLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);

        imageView = findViewById(R.id.imageView);
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText2);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        Button button = findViewById(R.id.button);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(CreateNews.this).start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String title = editText.getText().toString();
                String description = editText2.getText().toString();
                byte[] bytesPP = convertImageViewToByteArray(imageView);
                CurrentTime currentTime = new CurrentTime();
                String time = currentTime.getTime();

                if(title.equals(""))
                { textInputLayout.setError("Please Write Title");}

                if(description.equals(""))
                { textInputLayout2.setError("Please Write Description");}

                if(!title.equals("") && !description.equals("")) {
                    db = new DbHandler(CreateNews.this, "", null, 1);
                    if (db.save(bytesPP, title, description, time)) {
                        Intent intent = new Intent(CreateNews.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(CreateNews.this, "SAVED", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateNews.this, "NOT SAVED", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_up);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //IMAGE PATH WILL BE STORED ON uri
        Uri uri = data.getData();
        imageView.setImageURI(uri);
    }

    private byte[] convertImageViewToByteArray(ImageView imageView){
        // GET BITMAP FROM IMAGEVIEW AFTER CONVERTING INTO BITMAPDRAWABLE
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        //OBJECT OF BYTE ARRAY OUTPUT STREAM
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //COMPRESS BITMAP
        bitmap.compress(Bitmap.CompressFormat.JPEG,80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}