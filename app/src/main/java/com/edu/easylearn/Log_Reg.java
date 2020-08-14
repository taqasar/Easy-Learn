package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Log_Reg extends AppCompatActivity {

    private Button lg_btn;
    private TextView reg_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__reg);

        getSupportActionBar().hide();

        lg_btn = findViewById(R.id.logReg_log_btn);

        lg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_intent = new Intent(Log_Reg.this,Login.class);
                startActivity(log_intent);
            }
        });

        reg_txt = findViewById(R.id.Registrati);

        reg_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(Log_Reg.this,Register_User.class);
                startActivity(reg_intent);
            }
        });
    }
}
