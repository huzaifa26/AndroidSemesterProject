package com.example.semesterprojectandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Display;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    public DbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "test.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS newspaper(picture BLOB,title TEXT,description TEXT,time TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS newspaper;");
        onCreate(db);
    }

    public boolean save(byte[] picture, String title,String description,String time){
        try
        {
            ContentValues cv = new ContentValues();
            cv.put("picture",picture);
            cv.put("title",title);
            cv.put("description",description);
            cv.put("time",time);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert("newspaper", null , cv);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Model> getData(){
        Cursor c=this.getReadableDatabase().rawQuery("Select * from newspaper",null);
        ArrayList<Model> data=new ArrayList<>();
        while (c.moveToNext()) {
            Model m=new Model();
            m.setBitmap(c.getBlob(0));
            m.setDesc(c.getString(1));
            m.setImgname(c.getString(2));
            m.setDate(c.getString(3));
            data.add(m);
        }
        //Returns an Arraylist to MyAdapter object constructor inside MainActivity
        return data;
    }

    public void update_news(String oldtitle,byte[] picture, String title,String description){
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("picture", picture);

        System.out.println(oldtitle);
        System.out.println(title);
        System.out.println(description);
//        this.getWritableDatabase().rawQuery("UPDATE newspaper SET title='"+title+"',description='"+description+"',picture='"+picture +"' WHERE title='"+oldtitle+"'");
        this.getWritableDatabase().update("newspaper",values,"title = '" + oldtitle + "'",null);
    }

    public void delete_news(String title){
        System.out.println(title);
        this.getWritableDatabase().delete("newspaper","title='"+title+"'",null);
    }
}
