package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Impostazioni extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        getSupportActionBar().hide();
    }
}
