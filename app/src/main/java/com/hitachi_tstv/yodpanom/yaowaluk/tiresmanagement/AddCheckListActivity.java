package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Calendar;

public class AddCheckListActivity extends AppCompatActivity {

    private TextView tireIdTextView;
    private EditText odoEditText,deepEditText,presureEditText,commentEditText ;
    private String tireIdString,serialString,odoString,deepString,presureString,commentString,dateString,url,username;
    private DatePicker checkDatePicker;

    static final int  DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_list);

        ConstantUrl constantUrl = new ConstantUrl();

        tireIdTextView = (TextView) findViewById(R.id.textView5);
        odoEditText = (EditText) findViewById(R.id.editText3);
        deepEditText = (EditText) findViewById(R.id.editText5);
        presureEditText = (EditText) findViewById(R.id.editText4);
        commentEditText = (EditText) findViewById(R.id.editText7);
        checkDatePicker = (DatePicker) findViewById(R.id.datePicker);

        setCurrentDateOnView();

        tireIdString = getIntent().getStringExtra("ID");
        serialString = getIntent().getStringExtra("Serial");
        username = getIntent().getStringExtra("username");

        tireIdTextView.setText("Tire Series ::" + serialString);


        url = constantUrl.getUrlAddCheckList();


    }//main method


    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into datepicker
        checkDatePicker.init(year, month, day, null);
    }

    public void clickAddCheckList(View view) {
        odoString = odoEditText.getText().toString().trim();
        deepString = deepEditText.getText().toString().trim();
        presureString = presureEditText.getText().toString().trim();
        commentString = commentEditText.getText().toString().trim();
        int month = checkDatePicker.getMonth() + 1;
        dateString = checkDatePicker.getYear() + "-" + month + "-" + checkDatePicker.getDayOfMonth();
        Log.d("Date", dateString);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("odo", odoString)
                .add("deepTread", deepString)
                .add("presure", presureString)
                .add("comment", commentString)
                .add("checkDate",dateString)
                .add("tire_id",tireIdString)
                .add("username",username).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Call", "Failure");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("Call", "Success");
                //Toast.makeText(AddCheckListActivity.this, "Add Checklist Successful!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

}//main class
