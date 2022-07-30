package com.example.semesterprojectandroid;

public class Model {
    private byte[] bitmap;
    private String desc;
    private String imgname;
    private String date;

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setImgname(String imgname) {this.imgname = imgname;}
    public String getImgname() {
        return imgname;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }
    public byte[] getBitmap() {
        return bitmap;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
}
