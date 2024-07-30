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

public class BuyMedicineDetailActivity extends AppCompatActivity {

    TextView tvMedicineName, tvTotalCost;
    EditText etMedicineDetails;
    Button btnBack, btnAddtoCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_detail);

        tvMedicineName = findViewById(R.id.textViewBMDBuyMedicine);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        etMedicineDetails = findViewById(R.id.editTextBMDMultiLine);
        etMedicineDetails.setKeyListener(null);
        btnAddtoCart = findViewById(R.id.buttonBMDAddtoCart);
        btnBack = findViewById(R.id.buttonBMDBack);

        Intent it = getIntent();
        tvMedicineName.setText(it.getStringExtra("text1"));
        etMedicineDetails.setText(it.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost: "+it.getStringExtra("text3")+"/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineDetailActivity.this, BuyMedicineActivity.class));
            }
        });

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product = tvMedicineName.getText().toString();
                float price = Float.parseFloat(it.getStringExtra("text3").toString());

                Database db = new Database(getApplicationContext(),"healthcare", null, 1);
                if(db.checkCart(username, product)==1){
                    Toast.makeText(getApplicationContext(),"Product Already Added", Toast.LENGTH_LONG).show();
                }
                else {
                    db.addToCart(username,product,price,"medicine");
                    Toast.makeText(getApplicationContext(), "Record inserted to Cart", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BuyMedicineDetailActivity.this, BuyMedicineActivity.class));
                }
            }
        });
    }
}