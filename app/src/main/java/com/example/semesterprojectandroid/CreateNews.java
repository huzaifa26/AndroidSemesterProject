package com.example.semesterprojectandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
    public static final int PICK_IMAGE = 1;
    Uri uri;
    byte[] inputData;
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
        Button button = findViewById(R.id.button);
        ImageView im4=findViewById(R.id.imageView4);

        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(CreateNews.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreateNews.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(CreateNews.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreateNews.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PICK_IMAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            System.out.println(data);
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_up);
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