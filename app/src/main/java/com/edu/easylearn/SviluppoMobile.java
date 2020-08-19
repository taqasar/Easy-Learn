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
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SviluppoMobile extends AppCompatActivity {

    //Initialize variable
    private ImageView back_arrow2;
    private ImageView image_swift;
    private ImageView image_android;
    private ImageView image_flutter;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sviluppo_mobile);

        getSupportActionBar().hide();



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



        /**CODICE PER MUOVERSI NEI MACROMENU**/

        back_arrow2 = findViewById(R.id.freccia_dietro2);

        back_arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(SviluppoMobile.this, Home.class);
                startActivity(back_home);
            }
        });



        image_swift = findViewById(R.id.swift_button);

        image_swift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_swift = new Intent(SviluppoMobile.this, Swift.class);
                startActivity(go_swift);
            }
        });

        image_android = findViewById(R.id.android_button);

        image_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_android = new Intent(SviluppoMobile.this, AndroidMenu.class);
                startActivity(go_android);
            }
        });

        image_flutter = findViewById(R.id.flutter_button);

        image_flutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_flutter = new Intent(SviluppoMobile.this, Flutter.class);
                startActivity(go_flutter);
            }
        });

        /**FINE CODICE PER MUOVERSI NEI MACRO MENU **/

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

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
        closeDrawer(drawerLayout);

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

}


