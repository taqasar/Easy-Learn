package com.edu.easylearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class Forgot_pwd extends AppCompatActivity {

    private ImageView back_to_log;
    private EditText recovery_mail;
    private Button recover_btn;

    private String email_r = "";

    private FirebaseAuth rAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        getSupportActionBar().hide();

        back_to_log = findViewById(R.id.back_to_login);

        back_to_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forgot_pwd.this,Login.class));
            }
        });

        recovery_mail = findViewById(R.id.pass_recover_mail);
        recover_btn = findViewById(R.id.pwd_recover_btn);
        rAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        recover_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_r = recovery_mail.getText().toString();

                if(!email_r.isEmpty()) {
                    dialog.setMessage("Invio il link...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    reset_Pass();
                }else{
                    Toasty.warning(Forgot_pwd.this,"Specificare l'email di recupero", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void reset_Pass(){
        rAuth.setLanguageCode("IT");
        rAuth.sendPasswordResetEmail(email_r).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toasty.success(Forgot_pwd.this,"Controllare la posta elettronica",Toast.LENGTH_LONG).show();
                }else{
                    Toasty.error(Forgot_pwd.this,"Non è stato possibile inviare il link...",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
    }
}
