package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

    private String[][] packages = {
            {"Package 1 : Full Body Checkup","A full body checkup involves detailed tests and examinations of various body systems to assess overall health.","Regular checkups help identify potential health issues early, allowing for timely treatment and prevention of serious complications.","Results from the checkup provide personalized recommendations for lifestyle changes and preventive measures to maintain optimal health.","999"},
            {"Package 2 : Blood Glucose Fasting","Blood glucose fasting test measures blood sugar levels after an overnight fast.","It helps diagnose diabetes and prediabetes by checking how the body manages glucose."
                    ,"Requires 8-12 hours of fasting for accurate results.","299"},
            {"Package 3 : Covid-19 Antibody - IgG","The Covid-19 IgG antibody test identifies antibodies in the blood, indicating a past infection with the virus."
                    ,"Measures the immune system's response by detecting IgG antibodies, which typically develop weeks after infection or vaccination.","Provides data for understanding population immunity levels and potential protection against future infections.","899"},
            {"Package 4 : Thyroid Check","A thyroid check evaluates levels of thyroid hormones, such as T3, T4, and TSH, to assess thyroid function.","It helps diagnose conditions like hypothyroidism (underactive thyroid) or hyperthyroidism (overactive thyroid) which can affect metabolism, energy levels, and other bodily functions.","Results from the thyroid check guide treatment decisions, including medication dosage adjustments, for managing thyroid disorders and maintaining hormonal balance.","499"},
            {"Package 5 : Immunity Check","An immunity check evaluates the body's immune system, measuring various components such as white blood cells, antibodies, and cytokines.","It helps identify the strength and efficiency of the immune response, indicating susceptibility to infections or autoimmune diseases."
                    ,"Results from an immunity check guide health management strategies, including lifestyle changes, vaccinations, and treatments, to enhance immune function and prevent illnesses.","699"}
    };

    private String[] package_details = {
            "Blood Glucose Fasting\n" +
                    " Complete Hemogram\n" +
                    "HbA1c\n" +
                    " Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "LDH Lactate Dehydrogenase, Serum\n" +
                    "Liquid Profile" +
                    "Liver Function Test",
            "Blood Glucose Fasting\n",
            "Covid-19 Antibody - IgG",
            "Thyroid Profile-Total (T3, T4 and TSH Ultra-sensitive)",
            "Complete Hemogram\n" +
                    "CRP (C Reactive Protein) Quantative, Serum\n" +
                    " Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "Vitamin-D Total Hydroxy" +
                    "LDH Lactate Dehydrogenase, Serum\n" +
                    "Liquid Profile" +
                    "Liver Function Test"

    };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView listView;
    Button buttonGotoCart, buttonLTBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        buttonGotoCart = findViewById(R.id.buttonGotoCart);
        buttonLTBack = findViewById(R.id.buttonLTBack);
        listView = findViewById(R.id.listViewLT);

        buttonLTBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList<>();
        for(int i=0; i<packages.length; i++){
            item = new HashMap<String, String>();
            item.put( "line1", packages[i][0]);
            item.put( "line2", packages[i][1]);
            item.put( "line3", packages[i][2]);
            item.put( "line4", packages[i][3]);
            item.put( "line5", "Total Cost:"+packages[i][4]+"/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multiple_lines,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[position][0]);
                it.putExtra("text2", package_details[position]);
                it.putExtra("text3", packages[position][4]);
                startActivity(it);

            }
        });
        buttonGotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));
            }
        });
    }
}