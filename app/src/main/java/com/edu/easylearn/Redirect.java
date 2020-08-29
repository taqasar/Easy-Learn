package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

public class Redirect extends AppCompatActivity {

    private Dialog loadingDialog;
    Handler hndl = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        getSupportActionBar().hide();

        loadingDialog = new Dialog(Redirect.this);
        loadingDialog.setContentView(R.layout.loading_progess_bar_page);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        String s = getIntent().getStringExtra("DOVE");

        if (s != null && s.equals("kotlin")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AndroidMenu.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
    }
}
