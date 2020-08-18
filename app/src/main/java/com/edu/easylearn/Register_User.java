package com.edu.easylearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Register_User extends AppCompatActivity {

    private TextView title_main;
    private ImageView back;
    private EditText name;
    private EditText mail;
    private EditText pwd;
    private Button register;
    private Button register_goo;

    // Variabili legati ai dati per la registrazione

    private String name_s = "";
    private String mail_s = "";
    private String pwd_s = "";

    private FirebaseAuth Auth;
    private DatabaseReference db_ref;

    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);

        getSupportActionBar().hide();

        Auth = FirebaseAuth.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference();

        title_main = findViewById(R.id.reg_title);

        String text = "Benvenuto.";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan el_pink =  new ForegroundColorSpan(getColor(R.color.el_pink));
        ss.setSpan(el_pink,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        title_main.setText(ss);

        back = findViewById(R.id.reg_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_from_reg_intent = new Intent(Register_User.this,Log_Reg.class);
                startActivity(back_from_reg_intent);
            }
        });

        name = findViewById(R.id.reg_name);
        mail = findViewById(R.id.reg_mail_s);
        pwd = findViewById(R.id.reg_pwd_s);

        register = findViewById(R.id.avvia_app);
        register_goo = findViewById(R.id.reg_goo);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_s = name.getText().toString();
                mail_s = mail.getText().toString();
                pwd_s = pwd.getText().toString();

                if(!name_s.isEmpty() && !mail_s.isEmpty() && !pwd_s.isEmpty()){

                    if(pwd_s.length() >= 6){

                        register_User();

                    }else{
                        Toasty.warning(Register_User.this,"La password deve avere almeno 6 caratteri",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toasty.warning(Register_User.this,"Compilare i campi richiesti",Toast.LENGTH_LONG).show();
                }
            }
        });

        register_goo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.reg_goo:
                        signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    private void register_User(){
        Auth.createUserWithEmailAndPassword(mail_s,pwd_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String,Object> map = new HashMap<>();

                    map.put("nome",name_s);
                    map.put("e-mail",mail_s);
                    map.put("password",pwd_s);

                    String id = Auth.getCurrentUser().getUid();

                    db_ref.child("Utenti").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> subtask) {
                            if(subtask.isSuccessful()){
                                Toasty.success(Register_User.this,"Account creato!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register_User.this,Home.class));
                                finish();
                            }else{
                                Toasty.error(Register_User.this,"C'è stato qualche problema...account non creato",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toasty.error(Register_User.this,"Qualcosa è andato storto...account non creato",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toasty.success(Register_User.this,"Accesso eseguito",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Register_User.this,Home.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toasty.warning(Register_User.this,"Tentativo di accesso interrotto",Toast.LENGTH_LONG).show();
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}