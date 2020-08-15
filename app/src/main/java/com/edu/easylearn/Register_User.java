package com.edu.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Register_User extends AppCompatActivity {

    private TextView title_main;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);

        getSupportActionBar().hide();

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
    }
}
