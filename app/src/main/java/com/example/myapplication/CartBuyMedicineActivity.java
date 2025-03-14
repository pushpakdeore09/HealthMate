package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartBuyMedicineActivity extends AppCompatActivity {

    HashMap<String, String> item;
    ArrayList list;
    TextView tvTotalCost;
    SimpleAdapter sa;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private Button btnDate, btnCheckout, btnBack;
    private String packages[][] = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy_medicine);

        btnDate = findViewById(R.id.buttonCartMedicineDate);
        btnCheckout = findViewById(R.id.buttonCheckoutMedicine);
        btnBack = findViewById(R.id.buttonCartBackMedicine);
        tvTotalCost = findViewById(R.id.textViewCartMedicineTotalCost);
        lst = findViewById(R.id.listViewCartMedicine);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "").toString();

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);
        float totalAmout = 0;
        ArrayList dbData = db.getCartData(username, "medicine");
        //Toast.makeText(getApplicationContext(),""+dbData,Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for (int i=0; i<packages.length; i++){
            packages[i] = new String[5];
        }

        for (int i=0; i<dbData.size(); i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost: "+strData[1]+"/-";
            totalAmout = totalAmout + Float.parseFloat(strData[1]);
        }
        tvTotalCost.setText("Total Cost: " +totalAmout);
        list = new ArrayList<>();
        for (int i=0; i< packages.length; i++){
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multiple_lines,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        lst.setAdapter(sa);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartBuyMedicineActivity.this, BuyMedicineActivity.class));
            }

        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartBuyMedicineActivity.this, BuyMedicineBookActivity.class);
                it.putExtra("price", tvTotalCost.getText());
                it.putExtra("date", btnDate.getText());
                startActivity(it);
            }
        });
        initDatePicker();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                btnDate.setText(dayOfMonth+"/"+month+"/"+year);
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
}