package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HealthArticlesDetailsActivity extends AppCompatActivity {

    TextView tv1;
    Button btnBack;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles_details);

        btnBack = findViewById(R.id.buttonHADBack);
        tv1 = findViewById(R.id.textViewHealthArticlesDetails);
        img = findViewById(R.id.imageViewHAD);

        Intent it = getIntent();
        tv1.setText(it.getStringExtra("text1"));

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            int resId = bundle.getInt("text2");
            img.setImageResource(resId);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthArticlesDetailsActivity.this, HealthArticlesActivity.class));
            }
        });

    }
}