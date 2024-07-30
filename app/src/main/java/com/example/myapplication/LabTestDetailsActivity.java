package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText etDetails;
    Button buttonAddtoCart, buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        tvPackageName = findViewById(R.id.textViewLTDPackageName);
        tvTotalCost = findViewById(R.id.textViewLTDTotalCost);
        etDetails = findViewById(R.id.editTextMultiLine);
        buttonBack = findViewById(R.id.buttonLTDBack);
        buttonAddtoCart = findViewById(R.id.buttonLTDAddtoCart);

        etDetails.setKeyListener(null);

        Intent it = getIntent();
        tvPackageName.setText(it.getStringExtra("text1"));
        etDetails.setText(it.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost:"+it.getStringExtra("text3")+"/-");

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
            }
        });
        buttonAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(it.getStringExtra("text3").toString());

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if(db.checkCart(username, product) == 1){
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_LONG).show();
                }
                else {
                    db.addToCart(username, product, price, "lab");
                    Toast.makeText(getApplicationContext(), "Record inserted to Cart", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
                }
            }
        });
    }
}