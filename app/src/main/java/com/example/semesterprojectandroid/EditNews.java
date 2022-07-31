package com.example.semesterprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EditNews extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;

    ImageView imageView;
    DbHandler db;
    TextInputLayout textInputLayout;
    TextInputLayout textInputLayout2;
    Uri uri;
    byte[] inputData;
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        imageView = findViewById(R.id.imageViewf);
        EditText editText = findViewById(R.id.editTextF);
        EditText editText2 = findViewById(R.id.editTextF2);
        textInputLayout = findViewById(R.id.textInputLayoutF);
        textInputLayout2 = findViewById(R.id.textInputLayoutF2);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonF);
        ImageView im2=findViewById(R.id.imageView2);
        Button button = findViewById(R.id.buttonF);

        Intent intent=getIntent();

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");
        byte[] bitmap = bundle.getByteArray("image");

        editText.setText(description);
        editText2.setText(title);
        oldTitle=description;
        imageView.setImageBitmap(convertByteArraytoBitmap(bitmap));

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(EditNews.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditNews.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE);
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
                    if (ActivityCompat.checkSelfPermission(EditNews.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditNews.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE);
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
            @Override
            public void onClick(View view) {
                System.out.println(oldTitle);
                byte[] bytesPP = convertImageViewToByteArray(imageView);
                db=new DbHandler(EditNews.this,"",null,1);
                db.update_news(oldTitle,bytesPP,editText.getText().toString(),editText2.getText().toString());
                Intent intent2 = new Intent(EditNews.this,MainActivity.class);
                startActivity(intent2);
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

    private Bitmap convertByteArraytoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
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