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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.dmoral.toasty.Toasty;

public class SviluppoWeb extends AppCompatActivity {


    //Initialize variable
    private ImageView back_arrow;
    private ImageView image_html5;
    private ImageView image_css;
    private ImageView image_javascript;
    private ImageView image_nodejs;
    private ImageView image_react;
    private ImageView image_angular;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sviluppo_web);

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


        //Codice per andare nelle schermate dei MacroMenù

        back_arrow = findViewById(R.id.freccia_dietro);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(SviluppoWeb.this, Home.class);
                startActivity(back_home);
            }
        });


        image_html5 = findViewById(R.id.html_button);

        image_html5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_html = new Intent(SviluppoWeb.this, HTML5.class);
                startActivity(go_html);
            }
        });

        image_css = findViewById(R.id.css_button);

        image_css.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_css = new Intent(SviluppoWeb.this, CSS3.class);
                startActivity(go_css);
            }
        });


        image_javascript = findViewById(R.id.javascript_button);

        image_javascript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_javascript = new Intent(SviluppoWeb.this, Javascript.class);
                startActivity(go_javascript);
            }
        });

        image_nodejs = findViewById(R.id.nodejs_button);

        image_nodejs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_nodejs = new Intent(SviluppoWeb.this, NodeJS.class);
                startActivity(go_nodejs);
            }
        });

        image_react = findViewById(R.id.react_button);

        image_react.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_react = new Intent(SviluppoWeb.this, ReactJS.class);
                startActivity(go_react);
            }
        });

        image_angular = findViewById(R.id.angularjs_button);

        image_angular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_angularjs = new Intent(SviluppoWeb.this, AngularJS.class);
                startActivity(go_angularjs);
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
            Toasty.error(SviluppoWeb.this,"Errore condivisione", Toast.LENGTH_LONG).show();
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

    /**END HAMBURGER MENU CODE **/

}
