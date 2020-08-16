package com.edu.easylearn;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {

    private TextView title;
    private ImageView back;
    private ImageView help_icon;
    private TextView help;
    private TextView forgot_pass;
    private Button log;
    private EditText mail;
    private EditText pwd;

    // Variabili per il login

    private String mail_l;
    private String pwd_l;

    private FirebaseAuth sAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        sAuth = FirebaseAuth.getInstance();

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

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail_l = mail.getText().toString();
                pwd_l = pwd.getText().toString();

                if(!mail_l.isEmpty() && !pwd_l.isEmpty()){
                    login_User();

                }else{
                    Toasty.warning(Login.this,"Dati non corretti", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Forgot_pwd.class));
            }
        });
    }

    private void login_User(){
        sAuth.signInWithEmailAndPassword(mail_l,pwd_l).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toasty.success(Login.this,"Accesso effettuato",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this,temp.class));
                    finish();
                }else{
                    Toasty.error(Login.this,"Email e/o password non valido",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this,temp.class));
            finish();
        }
        }
}
