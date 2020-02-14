package com.example.lesson56douwnloadcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String mailRu = "https://mail.ru/";
    private String triDDDRu = "https://3ddd.ru/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("qwerty", mailRu);
        Log.i("qwerty", triDDDRu);
    }
}
