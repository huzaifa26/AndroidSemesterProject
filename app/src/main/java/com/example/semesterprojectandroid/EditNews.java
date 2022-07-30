package com.example.semesterprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditNews extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;


    ImageView imageView;
    DbHandler db;
    TextInputLayout textInputLayout;
    TextInputLayout textInputLayout2;

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
        Button button = findViewById(R.id.buttonF);

        Intent intent=getIntent();


        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");
        byte[] bitmap = bundle.getByteArray("image");

        System.out.println(bitmap);

        editText.setText(title);
        editText2.setText(description);
        imageView.setImageBitmap(convertByteArraytoBitmap(bitmap));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            System.out.println(data);
            Uri uri = data.getData();
            imageView.setImageURI(uri);

        }
    }

    private Bitmap convertByteArraytoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}