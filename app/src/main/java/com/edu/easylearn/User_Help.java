package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_Help extends AppCompatActivity {

    private TextView faq_title;
    private Button back_from_faq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__help);

        getSupportActionBar().hide();

        faq_title = findViewById(R.id.faq_title);

        String text = "FAQ";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan el_pink =  new ForegroundColorSpan(getColor(R.color.el_pink));
        ss.setSpan(el_pink,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        faq_title.setText(ss);

        back_from_faq = findViewById(R.id.faq_back);

        back_from_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_from_faq_intent = new Intent(User_Help.this, Login.class);
                startActivity(back_from_faq_intent);
            }
        });
    }
}
