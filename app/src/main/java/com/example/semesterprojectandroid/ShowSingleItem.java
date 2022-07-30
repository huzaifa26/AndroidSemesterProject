package com.example.semesterprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowSingleItem extends AppCompatActivity {
    TextView textView,textView2,textView3;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_item);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        imageView3 = findViewById(R.id.imageView3);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");
        byte[] bitmap = bundle.getByteArray("image");

        SpannableString content = new SpannableString(description);
        content.setSpan(new UnderlineSpan(), 0, description.length(), 0);

        textView.setText(date);
        textView2.setText(title);
        textView3.setText(content);
        imageView3.setImageBitmap(convertByteArraytoBitmap(bitmap));
    }

    private Bitmap convertByteArraytoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}