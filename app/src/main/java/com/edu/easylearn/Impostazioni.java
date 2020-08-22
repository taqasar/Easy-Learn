package com.edu.easylearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Impostazioni extends AppCompatActivity {

    DrawerLayout drawerLayout;

    private ImageView logout_ic;
    private TextView logout_txt;
    private TextView nome_prof;
    private TextView mail_prof;

    private CircleImageView setting_img;
    private TextView setting_img_txt;

    private EditText set_name;
    private EditText set_mail;
    private EditText set_num;
    private EditText set_role;
    private EditText set_sex;
    private Button setting_button;

    private String ss_nome = "";
    private String ss_mail = "";
    private String ss_num = "";
    private String ss_role = "";
    private String ss_sex = "";

    private CircleImageView img_prof;
    private Uri img_uri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        getSupportActionBar().hide();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Quiz Selected
        bottomNavigationView.setSelectedItemId(R.id.homeAsUp);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch ( menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.quiz:
                        startActivity(new Intent(getApplicationContext(), Quiz.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.cerca:
                        startActivity(new Intent(getApplicationContext(), Cerca.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profilo:
                        startActivity(new Intent(getApplicationContext(), Profilo.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;

            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        logout_ic = findViewById(R.id.log_out_icon);
        logout_txt = findViewById(R.id.log_txt);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        logout_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                switch (v.getId()) {
                    case R.id.log_out_icon:
                        signOut();
                        break;
                }
                Toasty.success(Impostazioni.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Impostazioni.this,Login.class));
            }
        });

        logout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                switch (v.getId()) {
                    case R.id.log_txt:
                        signOut();
                        break;
                }
                Toasty.success(Impostazioni.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Impostazioni.this,Login.class));
            }
        });

        nome_prof = findViewById(R.id.nome_hambuger);
        mail_prof = findViewById(R.id.mail_hamburger);

        getUserInfo();

        img_prof = findViewById(R.id.profile_image);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        img_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        setting_img = findViewById(R.id.profile_image);
        setting_img_txt = findViewById(R.id.img_setting_title);

        setting_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        setting_img_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        set_name = findViewById(R.id.setting_nome);
        set_mail = findViewById(R.id.setting_mail);
        set_num = findViewById(R.id.setting_tel);
        set_role = findViewById(R.id.setting_ruolo);
        set_sex = findViewById(R.id.setting_sesso);

        getUserInfo_settings();

        setting_button = findViewById(R.id.setting_btn);

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = auth.getCurrentUser().getUid();

                mDatabase.child("Utenti").child(id).child("nome").setValue(set_name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toasty.success(Impostazioni.this,"Dati aggiornati correttamente",Toast.LENGTH_LONG).show();
                        }else{
                            Toasty.error(Impostazioni.this,"Qualcosa è andato storto...",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mDatabase.child("Utenti").child(id).child("e-mail").setValue(set_mail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toasty.success(Impostazioni.this,"Dati aggiornati correttamente",Toast.LENGTH_LONG).show();
                        }else{
                            Toasty.error(Impostazioni.this,"Qualcosa è andato storto...",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mDatabase.child("Utenti").child(id).child("telefono").setValue(set_num.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toasty.success(Impostazioni.this,"Dati aggiornati correttamente",Toast.LENGTH_LONG).show();
                        }else{
                            Toasty.error(Impostazioni.this,"Qualcosa è andato storto...",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mDatabase.child("Utenti").child(id).child("ruolo").setValue(set_role.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toasty.success(Impostazioni.this,"Dati aggiornati correttamente",Toast.LENGTH_LONG).show();
                        }else{
                            Toasty.error(Impostazioni.this,"Qualcosa è andato storto...",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mDatabase.child("Utenti").child(id).child("sesso").setValue(set_sex.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toasty.success(Impostazioni.this,"Dati aggiornati correttamente",Toast.LENGTH_LONG).show();
                        }else{
                            Toasty.error(Impostazioni.this,"Qualcosa è andato storto...",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


    private void getUserInfo_settings(){
        String id = auth.getCurrentUser().getUid();
        mDatabase.child("Utenti").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("nome").getValue().toString();
                    String email = snapshot.child("e-mail").getValue().toString();
                    String tel = snapshot.child("telefono").getValue().toString();
                    String role = snapshot.child("ruolo").getValue().toString();
                    String sex = snapshot.child("sesso").getValue().toString();

                    set_name.setText(name);
                    set_mail.setText(email);
                    set_num.setText(tel);
                    set_role.setText(role);
                    set_sex.setText(sex);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //...
            }
        });
    }

    private void choosePic(){
        Intent gallery_intent = new Intent();
        gallery_intent.setType("image/*");
        gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery_intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            img_uri = data.getData();
            img_prof.setImageURI(img_uri);
            uploadPic();
        }
    }

    private void uploadPic(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Carico la foto...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(img_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        pd.dismiss();
                        Toasty.success(Impostazioni.this,"Foto caricata", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        pd.dismiss();
                        Toasty.error(Impostazioni.this,"Foto non caricata",Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercentage = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage("Stato: " + (int) progressPercentage + "%");
            }
        });
    }

    private void getUserInfo(){
        String id = auth.getCurrentUser().getUid();
        mDatabase.child("Utenti").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("nome").getValue().toString();
                    String email = snapshot.child("e-mail").getValue().toString();

                    nome_prof.setText(name);
                    mail_prof.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //...
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toasty.success(Impostazioni.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

    public void ClickMenu(View view ) {
        //Open drawer
        openDrawer (drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        //Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        //Close drawer
        startActivity(new Intent(Impostazioni.this,Home.class));

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer layout
        //Check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public  void ClickCross (View view) {
        //Recreate activity
        recreate();
    }

//    public void ClickHome(View view) {
//        //Recreate activity
//        recreate();
//    }

    public void ClickProfilo(View view) {
        //Redirect activity to Profilo
        redirectActivity(this, Profilo.class);
    }

    public void ClickCorsiSalvati(View view) {
        //Redirect activity to CorsiSalvati
        redirectActivity(this, CorsiSalvati.class );
    }


    public void ClickEasyUtility(View view) {
        //Redirect activity to EasyUtility
        redirectActivity(this, EasyUtility.class);
    }

    public void ClickImpostazioni(View view) {
        //Redirect activity to Impostazioni
        redirectActivity(this, Impostazioni.class );
    }

    public void ClickAbout(View view) {
        //Redirect activity to About
        redirectActivity(this, About.class);
    }

    public void ClickCondividi(View view) {
        //Redirect activity to Condividi
        //redirectActivity(this,Condividi.class );
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Easy Learn - la strada per il tuo futuro nell'ICT");
            String share_msg = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            intent.putExtra(Intent.EXTRA_TEXT, share_msg);
            startActivity(Intent.createChooser(intent, "Condividi tramite:"));
        }catch (Exception e){
            Toasty.error(Impostazioni.this,"Errore condivisione",Toast.LENGTH_LONG).show();
        }
    }


    public void ClickLogout(View view) {
        //Close app
        logout(this);
    }

    public static void logout(final Activity activity) {
        //Initialize alert dialog
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Logout");
        //Set message
        builder.setMessage("Are you sure you want to logout ?");
        //Positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                activity.finishAffinity();
                //Exit app
                System.exit(0);
            }
        });
        //Negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //Show dialog
        builder.show();

    }

    public static void  redirectActivity(Activity activity, Class aClass) {
        //Initialize intent
        Intent intent= new Intent(activity, aClass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start Activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);

    }
}
