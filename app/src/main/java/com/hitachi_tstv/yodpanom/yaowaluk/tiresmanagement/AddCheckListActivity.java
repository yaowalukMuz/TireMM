package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddCheckListActivity extends AppCompatActivity {

    private TextView tireIdTextView;
    private String tireIdString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_list);

        tireIdTextView = (TextView) findViewById(R.id.textView5);

        tireIdString = getIntent().getStringExtra("tireId");
        tireIdTextView.setText("Tire id ::" + tireIdString);

    }//main method
}//main class
