package com.example.semesterprojectandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Model> data;
    Context context;

    public MyAdapter(ArrayList<Model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //This Model is object to hold data for every individual model with position
        final Model temp = data.get(position);
        System.out.println(position);
        holder.t1.setText(data.get(position).getImgname());
        holder.t2.setText(data.get(position).getDesc());
        holder.t3.setText(data.get(position).getDate());
        holder.img1.setImageBitmap(convertByteArraytoBitmap(data.get(position).getBitmap()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowSingleItem.class);
                Bundle b1 = new Bundle();
                b1.putString("title",temp.getImgname());
                b1.putString("description",temp.getDesc());
                b1.putString("date",temp.getDate());
                b1.putByteArray("image",temp.getBitmap());
                intent.putExtras(b1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private Bitmap convertByteArraytoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
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
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem edit= (MenuItem) contextMenu.add(this.getAdapterPosition(),0, 0, "Edit");
            MenuItem delete= (MenuItem) contextMenu.add(this.getAdapterPosition(),0,  0, "Delete");
            edit.setOnMenuItemClickListener(onContextClickListener);
            delete.setOnMenuItemClickListener(onContextClickListener);
        }

        private final MenuItem.OnMenuItemClickListener onContextClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle() == "Edit") {
                    int clickedItemPosition = item.getGroupId();
                    Model temp = data.get(clickedItemPosition);
                    Bundle b2=new Bundle();
                    b2.putString("title",temp.getImgname());
                    b2.putString("description",temp.getDesc());
                    b2.putString("date",temp.getDate());
                    b2.putByteArray("image", temp.getBitmap());

                    Intent intent1=new Intent(context,EditNews.class);
                    intent1.putExtras(b2);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }

                if (item.getTitle() == "Delete") {
                    int clickedItemPosition = item.getGroupId();
                    Model temp = data.get(clickedItemPosition);
                    String name=temp.getDesc();
                    System.out.println(name);
                    DbHandler db=new DbHandler(context.getApplicationContext(), "",null,1);

                    AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
                    alertDialog.setTitle("Delete Alert");
                    alertDialog.setMessage("Are you sure you want do delete this news?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delete_news(name);
                                    Intent intent3 = new Intent(context,MainActivity.class);
                                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent3);
                                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                }
                            });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });


                    alertDialog.show();


//                    db.delete_news(name);
//                    Intent intent3 = new Intent(context,MainActivity.class);
//                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent3);
                }
                return true;
            }
        };
    }


}
