package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private TextView tv;
    private Button btn;
    private String[][] doctor_details1 = {
            {"Doctor Name: Dr. John Smith", "Hospital address: 123 Main St, Springfield", "Experience: 10 years", "Mobile No.: 555-1234", "200"},
            {"Doctor Name: Dr. Alice Brown", "Hospital address: 456 Oak Ave, Metropolis", "Experience: 8 years", "Mobile No.: 555-5678", "150"},
            {"Doctor Name: Dr. Emily Davis", "Hospital address: 789 Pine Rd, Gotham", "Experience: 15 years", "Mobile No.: 555-8765", "250"},
            {"Doctor Name: Dr. Michael White", "Hospital address: 101 Maple St, Smallville", "Experience: 5 years", "Mobile No.: 555-4321", "180"},
            {"Doctor Name: Dr. Sarah Green", "Hospital address: 202 Birch Blvd, Star City", "Experience: 12 years", "Mobile No.: 555-6789", "220"}
    };
    private String[][] doctor_details2 = {
            {"Doctor Name: Dr. Robert Wilson", "Hospital address: 789 Elm St, Riverdale", "Experience: 7 years", "Mobile No.: 555-3456", "$180"},
            {"Doctor Name: Dr. Laura Johnson", "Hospital address: 321 Cedar Ln, Hill Valley", "Experience: 9 years", "Mobile No.: 555-6543", "$210"},
            {"Doctor Name: Dr. Kevin Martin", "Hospital address: 654 Spruce St, Springfield", "Experience: 11 years", "Mobile No.: 555-9876", "$230"},
            {"Doctor Name: Dr. Rachel Lee", "Hospital address: 987 Willow Rd, Metropolis", "Experience: 6 years", "Mobile No.: 555-6781", "$160"},
            {"Doctor Name: Dr. William Clark", "Hospital address: 543 Aspen Ave, Gotham", "Experience: 10 years", "Mobile No.: 555-4325", "$200"}
    };
    private String[][] doctor_details3 = {
            {"Doctor Name: Dr. Paul Harris", "Hospital address: 789 Birch Blvd, Star City", "Experience: 12 years", "Mobile No.: 555-1239", "$220"},
            {"Doctor Name: Dr. Karen Adams", "Hospital address: 123 Pine Rd, Gotham", "Experience: 15 years", "Mobile No.: 555-5670", "$250"},
            {"Doctor Name: Dr. Lisa Parker", "Hospital address: 456 Oak Ave, Metropolis", "Experience: 8 years", "Mobile No.: 555-9871", "$150"},
            {"Doctor Name: Dr. Daniel Evans", "Hospital address: 101 Maple St, Smallville", "Experience: 5 years", "Mobile No.: 555-4322", "$180"},
            {"Doctor Name: Dr. Nancy Turner", "Hospital address: 202 Birch Blvd, Star City", "Experience: 10 years", "Mobile No.: 555-6782", "$200"}
    };
    private String[][] doctor_details4 = {
            {"Doctor Name: Dr. Patricia Wright", "Hospital address: 789 Maple St, Smallville", "Experience: 14 years", "Mobile No.: 555-4323", "$240"},
            {"Doctor Name: Dr. Steven Carter", "Hospital address: 123 Main St, Springfield", "Experience: 10 years", "Mobile No.: 555-1235", "$200"},
            {"Doctor Name: Dr. Laura Hall", "Hospital address: 456 Oak Ave, Metropolis", "Experience: 8 years", "Mobile No.: 555-5671", "$150"},
            {"Doctor Name: Dr. Ronald Scott", "Hospital address: 789 Pine Rd, Gotham", "Experience: 15 years", "Mobile No.: 555-9872", "$250"},
            {"Doctor Name: Dr. Barbara Young", "Hospital address: 101 Maple St, Smallville", "Experience: 5 years", "Mobile No.: 555-4324", "$180"}
    };
    private String[][] doctor_details5 = {
            {"Doctor Name: Dr. Anna Taylor", "Hospital address: 321 Elm St, Springfield", "Experience: 12 years", "Mobile No.: 555-2345", "$300"},
            {"Doctor Name: Dr. Mark Johnson", "Hospital address: 654 Willow Ave, Metropolis", "Experience: 9 years", "Mobile No.: 555-6780", "$350"},
            {"Doctor Name: Dr. Susan Wilson", "Hospital address: 987 Cedar Rd, Gotham", "Experience: 20 years", "Mobile No.: 555-8766", "$400"},
            {"Doctor Name: Dr. Robert Brown", "Hospital address: 111 Oak St, Smallville", "Experience: 7 years", "Mobile No.: 555-4322", "$320"},
            {"Doctor Name: Dr. Lisa White", "Hospital address: 222 Pine Blvd, Star City", "Experience: 14 years", "Mobile No.: 555-7890", "$280"}
    };
    private String[][] doctor_detail = {};
    private HashMap<String, String> item;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewDDtitle);
        btn = findViewById(R.id.buttonDDBack);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        switch (title) {
            case "Family Physician":
                doctor_detail = doctor_details1;
                break;
            case "Dietician":
                doctor_detail = doctor_details2;
                break;
            case "Dentist":
                doctor_detail = doctor_details3;
                break;
            case "Surgeon":
                doctor_detail = doctor_details4;
                break;
            default:
                doctor_detail = doctor_details5;
                break;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList<>();
        for (String[] doctor : doctor_detail) {
            item = new HashMap<>();
            item.put("line1", doctor[0]);
            item.put("line2", doctor[1]);
            item.put("line3", doctor[2]);
            item.put("line4", doctor[3]);
            item.put("line5", "Consultant fees: " + doctor[4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multiple_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_detail[position][0]);
                it.putExtra("text3", doctor_detail[position][1]);
                it.putExtra("text4", doctor_detail[position][3]);
                it.putExtra("text5", doctor_detail[position][4]);
                startActivity(it);
            }
        });
    }
}
