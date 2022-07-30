package com.example.semesterprojectandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        DbHandler db=new DbHandler(MainActivity.this,"",null,1);

        recyclerView =findViewById(R.id.recyclerView1);

        //set Layout for RecyclerView
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        myAdapter=new MyAdapter(db.getData(),getApplicationContext());
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater=getMenuInflater();
        // the following statement will put the main_menu xml file as the option menu on App bar
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public void handleCreate(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CreateNews.class);
        startActivity(intent);
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

    public void handleAbout(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img1;
        TextView t1,t2,t3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img1=itemView.findViewById(R.id.img1);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3= itemView.findViewById(R.id.t3);
            cardView = itemView.findViewById(R.id.card);
        }
    }

    public void getData(String imgName, String description,String date, byte[] img){
        Bundle b2=new Bundle();
        b2.putString("title",imgName);
        b2.putString("description",description);
        b2.putString("date",date);
        b2.putByteArray("image",img);
    }

}