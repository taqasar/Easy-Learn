package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash_intent = new Intent(Splash.this,Log_Reg.class);
                startActivity(splash_intent);
                finish();
            }
        },5000);

        splash = findViewById(R.id.splash_logo);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.myanim);
        splash.setAnimation(anim);
    }
}
