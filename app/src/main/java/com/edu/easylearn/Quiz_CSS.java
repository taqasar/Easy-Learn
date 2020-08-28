package com.edu.easylearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Quiz_CSS extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;

    private ImageView logout_ic;
    private TextView logout_txt;
    private TextView nome_prof;
    private TextView mail_prof;
    private CircleImageView img_prof;
    private Uri img_uri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    private ImageView back_quiz;
    private TextView quiz_num;
    private TextView question;
    private Button option1,option2,option3,option4;
    private List<Questions> questionsList;
    private int quesNum;
    private int correct;
    private int wrong;
    private FirebaseFirestore firestore;
    private Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__c_s_s);

        getSupportActionBar().hide();

        correct = 0;
        wrong = 0;

        firestore = FirebaseFirestore.getInstance();

        loadingDialog = new Dialog(Quiz_CSS.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Cerca Selected
        bottomNavigationView.setSelectedItemId(R.id.quiz);

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
                Toasty.success(Quiz_CSS.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Quiz_CSS.this,Login.class));
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
                Toasty.success(Quiz_CSS.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Quiz_CSS.this,Login.class));
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

        back_quiz = findViewById(R.id.back_to_quiz);
        back_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Quiz_CSS.this,Quiz_Web.class));
            }
        });

        question = findViewById(R.id.question);
        quiz_num = findViewById(R.id.num_of_quiz);

        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestionList();
    }

    private void getQuestionList(){
        questionsList = new ArrayList<>();

        firestore.collection("QUIZ").document("CAT_1")
                .collection("CSS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();
                    for(QueryDocumentSnapshot doc : questions){
                        questionsList.add(new Questions(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                        ));
                    }
                    setQuestion();

                }else{
                    Toasty.error(Quiz_CSS.this,"Errore nel caricamento delle domande...", Toast.LENGTH_LONG).show();
                }

                Handler hndl = new Handler();
                hndl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.cancel();
                    }
                },1100);
            }
        });
    }

    private void setQuestion(){

        question.setText(questionsList.get(0).getQuestion());
        option1.setText(questionsList.get(0).getOptionA());
        option2.setText(questionsList.get(0).getOptionB());
        option3.setText(questionsList.get(0).getOptionC());
        option4.setText(questionsList.get(0).getOptionD());

        quiz_num.setText(String.valueOf(1) + "/" + String.valueOf(questionsList.size()));

        quesNum = 0;
    }

    @Override
    public void onClick(View v) {

        int selectedOption = 0;

        switch (v.getId()){

            case R.id.option_1:
                selectedOption = 1;
                break;

            case R.id.option_2:
                selectedOption = 2;
                break;

            case R.id.option_3:
                selectedOption = 3;
                break;

            case R.id.option_4:
                selectedOption = 4;
                break;

            default:
        }

        checkAnswer(selectedOption,v);
    }

    private void checkAnswer(int selectedOption,View view){

        if(selectedOption == questionsList.get(quesNum).getCorrectAns()){
            // Right answer
            ((Button)view).setBackgroundColor(Color.GREEN);
            correct++;

        }else{
            // Wrong answer

            ((Button)view).setBackgroundColor(Color.RED);
            wrong++;

            switch (questionsList.get(quesNum).getCorrectAns()){
                case 1:
                    option1.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    option2.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    option3.setBackgroundColor(Color.GREEN);
                    break;
                case 4:
                    option4.setBackgroundColor(Color.GREEN);
                    break;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },1500);
    }

    private void changeQuestion(){

        if(quesNum < questionsList.size() - 1){

            quesNum++;

            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            quiz_num.setText(String.valueOf(quesNum+1) + "/" + String.valueOf(questionsList.size()));

        }else{
            Intent intent = new Intent(Quiz_CSS.this,Score_CSS.class);
            intent.putExtra("SCORE_RIGHT",String.valueOf(correct));
            intent.putExtra("SCORE_WRONG",String.valueOf(wrong));
            startActivity(intent);
        }
    }

    private void playAnim(final View view, final int value, final int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0){
                            switch (viewNum){
                                case 0:
                                    ((TextView)view).setText(questionsList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionsList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionsList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionsList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionsList.get(quesNum).getOptionD());
                                    break;
                            }

                            if(viewNum != 0) {
                                ((Button)view).setBackgroundResource(R.drawable.quiz_options_bkg);
                            }
                            playAnim(view,1,viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

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
                        Toasty.success(Quiz_CSS.this,"Foto caricata", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        pd.dismiss();
                        Toasty.error(Quiz_CSS.this,"Foto non caricata",Toast.LENGTH_LONG).show();
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
                        Toasty.success(Quiz_CSS.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(Quiz_CSS.this,Home.class));

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
            Toasty.error(Quiz_CSS.this,"Errore condivisione",Toast.LENGTH_LONG).show();
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
