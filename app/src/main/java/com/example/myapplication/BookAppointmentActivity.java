package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnBook, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewBookAppointment);
        et1 = findViewById(R.id.editTextAppFullName);
        et2 = findViewById(R.id.editTextAppAddress);
        et3 = findViewById(R.id.editTextAppContactNumber);
        et4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonBookAppointment);
        btnBack = findViewById(R.id.buttonAppBack);

        et1.setKeyListener(null);
        et2.setKeyListener(null);
        et3.setKeyListener(null);
        et4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String name = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contactno = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        et1.setText(name);
        et2.setText(address);
        et3.setText(contactno);
        et4.setText(fees);

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class));
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                    SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");

                    if (username.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String date = dateButton.getText().toString();
                    String time = timeButton.getText().toString();

                    // Check for null or empty values
                    if (title == null || name == null || address == null || contactno == null || date.isEmpty() || time.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Remove the dollar sign from fees before parsing
                    String feeStr = fees.replace("$", "");
                    float fee = Float.parseFloat(feeStr);

                    String appointmentDetails = title + "=> " + name;
                    if (db.checkAppointmentExists(username, appointmentDetails, address, contactno, date, time) == 1) {
                        Toast.makeText(getApplicationContext(), "Appointment already Booked", Toast.LENGTH_LONG).show();
                    } else {
                        db.addOrder(username, appointmentDetails, address, contactno, 0, date, time, fee, "appointment");
                        Toast.makeText(getApplicationContext(), "Your appointment is booked successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Log the exception
                    Toast.makeText(getApplicationContext(), "Invalid fee format", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateButton.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.R.style.Theme_DeviceDefault_Dialog_Alert;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeButton.setText(hourOfDay+":"+minute);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = android.R.style.Theme_DeviceDefault_Dialog_Alert;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener,hrs, mins, true);
    }
}