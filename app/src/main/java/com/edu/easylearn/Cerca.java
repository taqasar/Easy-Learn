package com.edu.easylearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Cerca extends AppCompatActivity {

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

    private GridView grid;
    private int images[] = {R.drawable.responsive,R.drawable.web,R.drawable.rjs,R.drawable.kotlin,R.drawable.redux,
                            R.drawable.tabella,R.drawable.json,R.drawable.api,R.drawable.npm,R.drawable.firefox,
                            R.drawable.ricerca_lin,R.drawable.bubble,R.drawable.linux,R.drawable.nosql,R.drawable.ios_safari,
                            R.drawable.git,R.drawable.server,R.drawable.ram,R.drawable.frontend,R.drawable.backend,
                            R.drawable.webserver,R.drawable.ide,R.drawable.uikit,R.drawable.xml,R.drawable.webcomp,
                            R.drawable.nqueen,R.drawable.compilatore,R.drawable.google_cerca,R.drawable.dart,R.drawable.xcode,
                            R.drawable.intellij,R.drawable.pack,R.drawable.command,R.drawable.sistema,R.drawable.editor,
                            R.drawable.astudio,R.drawable.sito,R.drawable.app,R.drawable.framework,R.drawable.jquery};

    private String names[] = {"responsive","web","react js","kotlin","redux","tabella sql","json","api","npm","firefox",
                            "ricerca lineare","bubble sort","linux","nosql","ios","git","data center","memoria","frontend","backend","web server",
                            "ide","ui kit","xml","web component","n regine","compilatore","google","dart","xcode","intellij","package","terminale","sistema",
                            "editor","android studio","sito web","app","framework","jquery"};

    private List<items> itemsList = new ArrayList<>();
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca);

       // getSupportActionBar().hide();
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ff0092"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Cerca qualcosa...");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Cerca Selected
        bottomNavigationView.setSelectedItemId(R.id.cerca);

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
                Toasty.success(Cerca.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Cerca.this,Login.class));
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
                Toasty.success(Cerca.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Cerca.this,Login.class));
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

        grid = findViewById(R.id.griglia);

        for(int i = 0; i < images.length; i++){
            items it = new items(names[i],images[i]);
            itemsList.add(it);
        }

        customAdapter = new CustomAdapter(itemsList,this);
        grid.setAdapter(customAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.view_cerca);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.view_cerca){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  class CustomAdapter extends BaseAdapter implements Filterable {

        private List<items> item_model_list;
        private List<items> item_model_list_filter;
        private Context context;

        public CustomAdapter(List<items> item_model_list, Context context) {
            this.item_model_list = item_model_list;
            this.item_model_list_filter = item_model_list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return item_model_list_filter.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items,null);
            ImageView img_view = view.findViewById(R.id.imageView_tessere);
            TextView tvName = view.findViewById(R.id.tvName);

            img_view.setImageResource(item_model_list_filter.get(position).getImage());
            tvName.setText(item_model_list_filter.get(position).getName());

            img_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Cerca.this,Redirect.class);
                    intent.putExtra("DOVE",String.valueOf(item_model_list_filter.get(position).getName()));
                    startActivity(intent);
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            final Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filter_result = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        filter_result.count = item_model_list.size();
                        filter_result.values = item_model_list;
                    }else{
                        String searchStr = constraint.toString().toLowerCase();
                        List<items> resultData = new ArrayList<>();

                        for(items itm:item_model_list){
                            if(itm.getName().contains(searchStr)){
                                resultData.add(itm);
                            }
                            filter_result.count = resultData.size();
                            filter_result.values = resultData;
                        }
                    }
                    return filter_result;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    item_model_list_filter = (List<items>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
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
                        Toasty.success(Cerca.this,"Foto caricata", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        pd.dismiss();
                        Toasty.error(Cerca.this,"Foto non caricata",Toast.LENGTH_LONG).show();
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
                        Toasty.success(Cerca.this,"Sign out effettuato", Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(Cerca.this,Home.class));

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
            Toasty.error(Cerca.this,"Errore condivisione",Toast.LENGTH_LONG).show();
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
