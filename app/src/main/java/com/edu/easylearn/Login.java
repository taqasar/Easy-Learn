package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private TextView title;
    private ImageView back;
    private ImageView help_icon;
    private TextView help;
    private TextView forgot_pass;
    private Button log;
    private EditText mail;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        title = findViewById(R.id.login_title);
        String text = "Bentornato.";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan el_pink =  new ForegroundColorSpan(getColor(R.color.el_pink));
        ss.setSpan(el_pink,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        title.setText(ss);

        back = findViewById(R.id.login_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_log_reg_intent = new Intent(Login.this,Log_Reg.class);
                startActivity(back_to_log_reg_intent);
            }
        });

        help_icon = findViewById(R.id.login_help_btn);

        help = findViewById(R.id.login_help);

        help_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faq_intent = new Intent(Login.this,User_Help.class);
                startActivity(faq_intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faq_intent_2 = new Intent(Login.this,User_Help.class);
                startActivity(faq_intent_2);
            }
        });

        forgot_pass = findViewById(R.id.forgot_pwd);
        log = findViewById(R.id.login_btn);
        mail = findViewById(R.id.login_email);
        pwd = findViewById(R.id.login_pwd);
    }
}
