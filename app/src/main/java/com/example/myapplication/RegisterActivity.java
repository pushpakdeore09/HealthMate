package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.editTextRegUsername);
        etEmail = findViewById(R.id.editTextRegEmail);
        etPassword = findViewById(R.id.editTextRegPassword);
        etConfirmPassword = findViewById(R.id.editTextRegConfirmPassword);
        btn = findViewById(R.id.buttonRegister);

        tv = findViewById(R.id.textViewOldUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                Database db = new Database(getApplicationContext(), "HealthCare", null, 1);
                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();
                }
                else {
                    if (password.equals(confirmPassword)) {
                        if (isPasswordValid(password)){
                            db.register(username, email, password);
                            Toast.makeText(RegisterActivity.this,"Record Registered",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this,"Password must contain at least 8 characters, letters, digit and special symbols",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password didn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public static boolean isPasswordValid(String password){
        int flag1=0, flag2=0, flag3=0;
        if (password.length() < 8){
            return false;
        } else {
            for (int p = 0; p < password.length(); p++) {
                if (Character.isLetter(password.charAt(p))){
                    flag1=1;
                }
            }
            for (int r = 0; r < password.length(); r++) {
                if (Character.isDigit(password.charAt(r))){
                    flag2=1;
                }
            }
            for (int s = 0; s < password.length(); s++) {
                char c = password.charAt(s);
                if (c>=33 && c<=46 || c==64){
                    flag3=1;
                }
            }
            if(flag1==1 && flag2==1 && flag3==1)
                return true;
            return false;
        }
    }
}
