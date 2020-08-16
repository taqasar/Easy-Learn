package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class temp extends AppCompatActivity {

    private Button sign_out;
    private FirebaseAuth oAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        getSupportActionBar().hide();

        sign_out = findViewById(R.id.exit_from_home);
        oAuth = FirebaseAuth.getInstance();

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oAuth.signOut();
                startActivity(new Intent(temp.this,Login.class));
            }
        });
    }
}
