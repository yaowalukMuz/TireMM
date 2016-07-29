package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
    private TextView licenseTextView,tv11TextView,tv21TextView,tv31TextView,tv41TextView ,tv52TextView ,tv62TextView,
            tv72TextView,tv82TextView,tv93TextView ,tv103TextView,tv113TextView,tv123TextView,tvspTextView;
    private ImageView imageView11,imageView21,imageView31,imageView41,imageView52,
            imageView62,imageView72,imageView82,imageView93,imageView103,
            imageView113,imageView123,spImageView;

    private String licenseString,idString;
    private String urlJSONFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_vehicle);


        //Bind widget
        licenseTextView = (TextView) findViewById(R.id.textView3);

        tv11TextView = (TextView) findViewById(R.id.tv11);
        tv21TextView = (TextView) findViewById(R.id.tv21);
        tv31TextView = (TextView) findViewById(R.id.tv31);
        tv41TextView = (TextView) findViewById(R.id.tv41);
        tv52TextView = (TextView) findViewById(R.id.tv52);
        tv62TextView = (TextView) findViewById(R.id.tv62);
        tv72TextView = (TextView) findViewById(R.id.tv72);
        tv82TextView = (TextView) findViewById(R.id.tv82);
        tv93TextView = (TextView) findViewById(R.id.tv93);
        tv103TextView = (TextView) findViewById(R.id.tv103);
        tv113TextView = (TextView) findViewById(R.id.tv113);
        tv123TextView = (TextView) findViewById(R.id.tv123);
        tvspTextView = (TextView) findViewById(R.id.tvsp);

        imageView11 = (ImageView) findViewById(R.id.iv11);
        imageView21 = (ImageView) findViewById(R.id.iv21);
        imageView31 = (ImageView) findViewById(R.id.iv31);
        imageView41 = (ImageView) findViewById(R.id.iv41);
        imageView52 = (ImageView) findViewById(R.id.iv52);
        imageView62 = (ImageView) findViewById(R.id.iv62);
        imageView72 = (ImageView) findViewById(R.id.iv72);
        imageView82 = (ImageView) findViewById(R.id.iv82);
        imageView93 = (ImageView) findViewById(R.id.iv93);
        imageView103 = (ImageView) findViewById(R.id.iv103);
        imageView113 = (ImageView) findViewById(R.id.iv113);
        imageView123 = (ImageView) findViewById(R.id.iv123);
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


         //for (int i = 0;i < positionNoString.length;i++) {

          /*   String temp = "iv" + positionNoString[i] + lineString[i];
           // constantUrl.setImageSource(tireIdString[i]);
         //   String src = constantUrl.getImageSource();
            /*
            if (temp.equals(ImageView.getId())) {
                if (tireIdString[i].equals("")) {
                    tl11ImageView.setImageResource(R.drawable.tire_null);
                } else {
                    tl11ImageView.setImageResource(R.drawable.tire);
                }

            }*/



       // }



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
                RequestBody requestBody = new FormEncodingBuilder().add("isSearch","true").add("veh_id", vehIdString).build();
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

                tireIdString = new String[jsonArray.length()];
                tireNoString = new String[jsonArray.length()];
                lineString = new String[jsonArray.length()];
                positionNoString = new String[jsonArray.length()];
                positionString = new String[jsonArray.length()];

                Log.d("JSON", "JSON ==> " + jsonArray);
                for(int i=0 ;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tireNoString[i] = jsonObject.getString("tire_serial");
                    tireIdString[i] = jsonObject.getString("tire_id");
                    lineString[i] = jsonObject.getString("fmwDtl_lineNo");
                    positionString[i] = jsonObject.getString("fmwDtl_positionLine");
                    positionNoString[i] = jsonObject.getString("fmwDtl_positionNo");
                }

                for (int i = 0;i < positionNoString.length;i++) {

                    Log.d("Position", "Tire ID in Loop --> " + tireIdString[i]);
            String temp = positionNoString[i] + lineString[i];

                    switch (temp){
                        case ("11") :

                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {

                                imageView21.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView21.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("21") :

                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView31.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView31.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("31") :

                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView31.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView31.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("32") :

                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView52.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView52.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("41") :

                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView41.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView41.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("42") :

                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView62.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView62.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("52") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView72.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView72.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("62") :
                            if (tireIdString[i].equals("null")|| tireIdString[i].equals("")) {
                                imageView82.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView82.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("72") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView72.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView72.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("73") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView93.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView93.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("82") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView82.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView82.setImageResource(R.drawable.tire);
                            }
                            continue;
//                            break;
                        case ("83") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView103.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView103.setImageResource(R.drawable.tire);
                            }
                            continue;
//                            break;
                        case ("93") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView113.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView113.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("103") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView123.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView123.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("113") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView113.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView113.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("123") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                imageView123.setImageResource(R.drawable.tire_null);
                            } else {
                                imageView123.setImageResource(R.drawable.tire);
                            }
                            continue;
                        case ("SP0") :
                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
                                spImageView.setImageResource(R.drawable.tire_null);
                            } else {
                                spImageView.setImageResource(R.drawable.tire);
                            }
                            continue;
                        default:
                            if(temp == "11"){
                                imageView11.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "21"){
                                imageView21.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "31"){
                                imageView31.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "41"){
                                imageView41.setImageResource(R.drawable.tire_null);
                            }if(temp == "52"){
                            imageView52.setImageResource(R.drawable.tire_null);
                            }if(temp == "62"){
                                imageView62.setImageResource(R.drawable.tire_null);
                            }if(temp == "72"){
                                imageView72.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "82"){
                                imageView82.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "93"){
                                imageView11.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "103"){
                                imageView103.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "113"){
                                imageView113.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "123"){
                                imageView123.setImageResource(R.drawable.tire_null);
                            }
                            if(temp == "SP0"){
                                spImageView.setImageResource(R.drawable.tire_null);
                            }


                           // spImageView.setImageResource(R.drawable.tire_null);

//                            break;
                    }

/*
            if (temp.equals(ImageView.getId())) {
                if (tireIdString[i].equals("")) {
                    imageView11.setImageResource(R.drawable.tire_null);
                } else {
                    tl11ImageView.setImageResource(R.drawable.tire);
                }

            }*/



                 }

//                imageView11.setImageResource(R.drawable.tire);
//                imageView21.setImageResource(R.drawable.tire_null);
//                imageView31.setImageResource(R.drawable.tire_null);
//                imageView41.setImageResource(R.drawable.tire_null);
//                imageView52.setImageResource(R.drawable.tire_null);
//                imageView62.setImageResource(R.drawable.tire_null);
//                imageView72.setImageResource(R.drawable.tire_null);
//                imageView82.setImageResource(R.drawable.tire_null);
//                imageView93.setImageResource(R.drawable.tire_null);
//                imageView103.setImageResource(R.drawable.tire_null);
//                imageView113.setImageResource(R.drawable.tire_null);
//                imageView123.setImageResource(R.drawable.tire);
//                spImageView.setImageResource(R.drawable.tire);
//                Log.d("Extra", "positionNoString Length -> " + positionNoString.length);
//
//
//


            } catch (Exception e) {
                Log.d("ItemVeh", "e doInBack ==> " + e);
            }


        }
    }


}
