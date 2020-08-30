package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import org.w3c.dom.Node;

public class Redirect extends AppCompatActivity {

    private Dialog loadingDialog;
    Handler hndl = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        getSupportActionBar().hide();

        loadingDialog = new Dialog(Redirect.this);
        loadingDialog.setContentView(R.layout.loading_progess_bar_page);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        String s = getIntent().getStringExtra("DOVE");

        if (s != null && s.equals("responsive")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Flutter.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("web")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, HTML5.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("react js")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, ReactJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("kotlin")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AndroidMenu.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("redux")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, ReactJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("tabella sql")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, SQL.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("json")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Javascript.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("api")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Javascript.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("npm")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, NodeJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("firefox")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, HTML5.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("ricerca lineare")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Complessita.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("bubble sort")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Backtracking.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("linux")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, NodeJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("nosql")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, MongoDB.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("ios")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Swift.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("git")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Javascript.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("data center")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, SQL.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("memoria")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Complessita.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("frontend")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, CSS3.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("backend")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, NodeJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("web server")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, NodeJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("ide")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AngularJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("ui kit")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Swift.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("xml")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, ReactJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("web component")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Javascript.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("n regine")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Backtracking.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("compilatore")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AngularJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("google")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AngularJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("dart")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Flutter.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("xcode")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Swift.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("intellij")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AndroidMenu.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("package")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, ReactJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("terminale")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, NodeJS.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("sistema")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AndroidMenu.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("editor")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, HTML5.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("android studio")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, AndroidMenu.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("sito web")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, HTML5.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("app")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Flutter.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("framework")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Flutter.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
        if (s != null && s.equals("jquery")) {
            hndl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Redirect.this, Javascript.class));
                    loadingDialog.cancel();
                }
            },1100);
        }
    }
}
