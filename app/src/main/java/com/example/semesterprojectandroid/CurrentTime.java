package com.example.semesterprojectandroid;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CurrentTime {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTime() {
        return new Date().toString().substring(0, 10);
    }
}