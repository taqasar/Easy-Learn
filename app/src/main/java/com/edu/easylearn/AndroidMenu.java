package com.edu.easylearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AndroidMenu extends AppCompatActivity {

    private ImageView back_arrow;
    private Button home_button;
    private Button quiz_button;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_menu);


        getSupportActionBar().hide();

        home_button = findViewById(R.id.button_home);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_intent = new Intent(AndroidMenu.this,SviluppoMobile.class);
                startActivity(home_intent);
            }
        });

        quiz_button = findViewById(R.id.button_quiz);
        quiz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quiz_intent = new Intent(AndroidMenu.this,Quiz.class);
                startActivity(quiz_intent);
            }
        });

        /**BOTTOM MENU CODE **/
        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId() ) {
                    case R.id.quiz:
                        startActivity(new Intent(getApplicationContext(), Quiz.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
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
        /**END BOTTOM MENU CODE **/



        back_arrow = findViewById(R.id.freccia_dietro);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(AndroidMenu.this, SviluppoMobile.class);
                startActivity(back_home);
            }
        });


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

    }

    /**HAMBURGER MENU CODE **/

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
        closeDrawer(drawerLayout);
        redirectActivity(this, Home.class);

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
        redirectActivity(this,Condividi.class );
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

    /**END HAMBURGER MENU CODE **/
}