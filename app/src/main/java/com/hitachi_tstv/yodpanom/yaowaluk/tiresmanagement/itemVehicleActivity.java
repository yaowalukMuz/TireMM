package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class itemVehicleActivity extends AppCompatActivity {

    private String[] lineString,  positionString,  positionNoString, tireNoString ,tireIdString;

    private TextView licenseTextView;
    private ImageView tl11ImageView,tl21ImageView,tr31ImageView,tr41ImageView,bl52ImageView,
            bl62ImageView,br72ImageView,br82ImageView,bl93ImageView,bl103ImageView,
            br113ImageView,br123ImageView,spImageView;
    private String licenseString,idString;
    private String urlJSONFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_vehicle);


        //Bind widget
        licenseTextView = (TextView) findViewById(R.id.textView3);
        tl11ImageView = (ImageView) findViewById(R.id.iv11);
        tl21ImageView = (ImageView) findViewById(R.id.iv21);
        tr31ImageView = (ImageView) findViewById(R.id.iv31);
        tr41ImageView = (ImageView) findViewById(R.id.iv41);
        bl52ImageView = (ImageView) findViewById(R.id.iv52);
        bl62ImageView = (ImageView) findViewById(R.id.iv62);
        br72ImageView = (ImageView) findViewById(R.id.iv72);
        br82ImageView = (ImageView) findViewById(R.id.iv82);
        bl93ImageView = (ImageView) findViewById(R.id.iv93);
        bl103ImageView = (ImageView) findViewById(R.id.iv103);
        br113ImageView = (ImageView) findViewById(R.id.iv113);
        br123ImageView = (ImageView) findViewById(R.id.iv123);
        spImageView = (ImageView) findViewById(R.id.ivsp);

        //show view
        licenseString = getIntent().getStringExtra("license");
        idString = getIntent().getStringExtra("id");

        ConstantUrl constantUrl = new ConstantUrl();
        urlJSONFormat = constantUrl.getUrlJSONFormatWhell();

        Log.d("Extra", "license -> " + licenseString);

        licenseTextView.setText("License : " + licenseString);

        SyncFormatWheel syncFormatWheel = new SyncFormatWheel(this, urlJSONFormat, idString);
        syncFormatWheel.execute();

        for (int i = 0;i < positionNoString.length;i++) {
            String temp = "iv" + positionNoString[i] + lineString[i];
            constantUrl.setImageSource(tireIdString[i]);
            String src = constantUrl.getImageSource();

            if (temp.equals(tl11ImageView.getId())) {
                if (tireIdString[i].equals("")) {
                    tl11ImageView.setImageResource(R.drawable.tire_null);
                } else {
                    tl11ImageView.setImageResource(R.drawable.tire);
                }

            }

        }


    }


    private class SyncFormatWheel extends AsyncTask<Void, Void, String> {

        private Context context;
        private String formatURL,vehIdString;


        public SyncFormatWheel(Context context, String formatURL, String vehIdString ) {
            this.context = context;
            this.formatURL = formatURL;
            this.vehIdString = vehIdString;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder().add("veh_id", vehIdString).build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(formatURL).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {

                Log.d("SPN1", "e doInBack ==> " + e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                for(int i=0 ;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tireNoString[i] = jsonObject.getString("tire_serial");
                    tireIdString[i] = jsonObject.getString("tire_id");
                    lineString[i] = jsonObject.getString("fmwDtl_lineNo");
                    positionString[i] = jsonObject.getString("fmwDtl_positionLine");
                    positionNoString[i] = jsonObject.getString("fmwDtl_positionNo");
                }



            } catch (Exception e) {

            }


        }
    }


}
