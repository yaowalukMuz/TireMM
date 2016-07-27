package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private String usernameString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }// Main Method

    public void clickSignIn(View view) {
        Intent intent = new Intent(MainActivity.this, CheckListActivity.class);
        startActivity(intent);
        finish();
    }

}//Main Class
