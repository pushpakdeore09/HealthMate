package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LabTestBookActivity extends AppCompatActivity {

    EditText etName, etAddress, etContact, etPinCode;
    Button buttonBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        etName = findViewById(R.id.textViewLTBFullName);
        etAddress = findViewById(R.id.textViewLTBAddress);
        etContact = findViewById(R.id.textViewLTBContactNumber);
        etPinCode = findViewById(R.id.textViewLTBPinCode);
        buttonBook = findViewById(R.id.buttonLTBBook);

        Intent it = getIntent();
        String[] price = it.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = it.getStringExtra("date");
        String time = it.getStringExtra("time");

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    if (username.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String name = etName.getText().toString();
                    String address = etAddress.getText().toString();
                    String contact = etContact.getText().toString();
                    String pincode = etPinCode.getText().toString();
                    if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || pincode.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int pinCode = Integer.parseInt(pincode);

                    // Check if the price array has at least two elements
                    if (price.length < 2) {
                        Toast.makeText(getApplicationContext(), "Price information is incomplete", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    float priceValue = Float.parseFloat(price[1]);

                    Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                    db.addOrder(username, name, address, contact, pinCode, date.toString(), time.toString(), priceValue, "lab");
                    db.removeCart(username, "lab");

                    Toast.makeText(getApplicationContext(), "Booking done Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid number format", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }
}