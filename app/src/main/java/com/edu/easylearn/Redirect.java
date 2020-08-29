package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Redirect extends AppCompatActivity {

    items itemsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        getSupportActionBar().hide();

        String s = getIntent().getStringExtra("DOVE");

        if (s != null && s.equals("kotlin")) {
            startActivity(new Intent(Redirect.this, AndroidMenu.class));
        }
    }
}
