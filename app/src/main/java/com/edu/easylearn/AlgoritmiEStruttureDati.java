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

public class AlgoritmiEStruttureDati extends AppCompatActivity {


    //Initialize variable
    private ImageView back_arrow3;
    private ImageView image_bst;
    private ImageView image_backtracking;
    private ImageView image_stack;
    private ImageView image_complex;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmi_e_strutture_dati);

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

        back_arrow3 = findViewById(R.id.freccia_dietro);

        back_arrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(AlgoritmiEStruttureDati.this, Home.class);
                startActivity(back_home);
            }
        });


        image_bst = findViewById(R.id.bst_button);

        image_bst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_bst = new Intent(AlgoritmiEStruttureDati.this, BST.class);
                startActivity(go_bst);
            }
        });

        image_backtracking = findViewById(R.id.backtracking_button);

        image_backtracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_backtracking = new Intent(AlgoritmiEStruttureDati.this, Backtracking.class);
                startActivity(go_backtracking);
            }
        });


        image_stack = findViewById(R.id.stack_button);

        image_stack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_stack = new Intent(AlgoritmiEStruttureDati.this, StackEMemoria.class);
                startActivity(go_stack);
            }
        });


        image_complex = findViewById(R.id.complessita_button);

        image_complex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_complex = new Intent(AlgoritmiEStruttureDati.this, Complessita.class);
                startActivity(go_complex);
            }
        });


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