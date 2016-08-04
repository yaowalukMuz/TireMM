package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class itemVehicleActivity extends AppCompatActivity {


    private String[] lineString, positionString, positionNoString, tireNoString, tireIdString;
    private TextView licenseTextView, tv11TextView, tv21TextView, tv31TextView, tv41TextView, tv52TextView, tv62TextView,
            tv72TextView, tv82TextView, tv93TextView, tv103TextView, tv113TextView, tv123TextView,tv134TextView,
    tv144TextView,  tv154TextView, tv164TextView, tvspTextView;
    private ImageView imageView11, imageView21, imageView31, imageView41, imageView52,
            imageView62, imageView72, imageView82, imageView93, imageView103,
            imageView113, imageView123,  imageView134, imageView144, imageView154, imageView164, spImageView;

    private String licenseString, idString;
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
        tv134TextView = (TextView) findViewById(R.id.tv134);
        tv144TextView = (TextView) findViewById(R.id.tv144);
        tv154TextView = (TextView) findViewById(R.id.tv154);
        tv164TextView = (TextView) findViewById(R.id.tv164);
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
        imageView134 = (ImageView) findViewById(R.id.iv134);
        imageView144 = (ImageView) findViewById(R.id.iv144);
        imageView154 = (ImageView) findViewById(R.id.iv154);
        imageView164 = (ImageView) findViewById(R.id.iv164);

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

        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
            }
        });


    }


    private class SyncFormatWheel extends AsyncTask<Void, Void, String> {

        private Context context;
        private String formatURL, vehIdString;


        public SyncFormatWheel(Context context, String formatURL, String vehIdString) {
            this.context = context;
            this.formatURL = formatURL;
            this.vehIdString = vehIdString;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder().add("isSearch", "true").add("veh_id", vehIdString).build();
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tireNoString[i] = jsonObject.getString("tire_serial");
                    tireIdString[i] = jsonObject.getString("tire_id");
                    lineString[i] = jsonObject.getString("fmwDtl_lineNo");
                    positionString[i] = jsonObject.getString("fmwDtl_positionLine");
                    positionNoString[i] = jsonObject.getString("fmwDtl_positionNo");
                }

                Map<String, String> arrMap = new HashMap<String, String>();
                arrMap.put("11", "");
                arrMap.put("21", "");
                arrMap.put("31", "");
                arrMap.put("41", "");
                arrMap.put("52", "");
                arrMap.put("62", "");
                arrMap.put("72", "");
                arrMap.put("82", "");
                arrMap.put("93", "");
                arrMap.put("103", "");
                arrMap.put("113", "");
                arrMap.put("123", "");
                arrMap.put("134", "");
                arrMap.put("144", "");
                arrMap.put("154", "");
                arrMap.put("164", "");
                arrMap.put("sp0", "");

                Map<String, String> seriesMap = new HashMap<String, String>();

                //int arrLine[] = new int[7];
                List<Integer> lineList = new ArrayList<Integer>();
                String tmpLine = "";

                List<HashMap> listTemp = new ArrayList<HashMap>();
                HashMap<String, String> dataMap = new HashMap<String, String>();
                int j = 0;
                for (int i = 0; i < positionNoString.length; i++) {
                    if (!tmpLine.equals(lineString[i])) {
                        lineList.add(Integer.parseInt(lineString[i]));
                        tmpLine = lineString[i];

                        j++;


                    }
                    if (!(tireIdString[i].equals("null"))) {
                        seriesMap.put(tireIdString[i], tireNoString[i]);

                    }


                    dataMap.put( positionNoString[i]+lineString[i], tireIdString[i]);
                    // listTemp.add(dataMap);
                }


                Set eSet = dataMap.entrySet();
                Iterator it = eSet.iterator();
        while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    Log.d("Value", "arrKey ==> " + e.getKey());
                    Log.d("Value", "arrValue ==> " + e.getValue());
            if (e.getKey().equals("11")) {
                // arrMap.put("21", e.getValue().toString());
                if (e.getValue().equals("null")) {
                    imageView21.setImageResource(R.drawable.tire_null);
                } else {
                    imageView21.setImageResource(R.drawable.tire);
                    tv21TextView.setText(seriesMap.get(e.getValue()));
                }

            }
            if (e.getKey().equals("21")) {
                if (e.getValue().equals("null")) {
                    imageView31.setImageResource(R.drawable.tire_null);
                } else {
                    imageView31.setImageResource(R.drawable.tire);
                    tv31TextView.setText(seriesMap.get(e.getValue()));

                }
            }
            if (e.getKey().equals("32")) {
                if (e.getValue().equals("null")) {
                    imageView52.setImageResource(R.drawable.tire_null);
                } else {
                    imageView52.setImageResource(R.drawable.tire);
                    tv52TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("42")) {
                if (e.getValue().equals("null")) {
                    imageView62.setImageResource(R.drawable.tire_null);
                } else {
                    imageView62.setImageResource(R.drawable.tire);
                    tv62TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("52")) {
                if (e.getValue().equals("null")) {
                    imageView72.setImageResource(R.drawable.tire_null);
                } else {
                    imageView72.setImageResource(R.drawable.tire);
                    tv72TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("62")) {
                if (e.getValue().equals("null")) {
                    imageView82.setImageResource(R.drawable.tire_null);
                } else {
                    imageView82.setImageResource(R.drawable.tire);
                    tv82TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("73")) {
                if (e.getValue().equals("null")) {
                    imageView93.setImageResource(R.drawable.tire_null);
                } else {
                    imageView93.setImageResource(R.drawable.tire);
                    tv93TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("83")) {
                if (e.getValue().equals("null")) {
                    imageView103.setImageResource(R.drawable.tire_null);
                } else {
                    imageView103.setImageResource(R.drawable.tire);
                    tv103TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("93")) {
                if (e.getValue().equals("null")) {
                    imageView113.setImageResource(R.drawable.tire_null);
                } else {
                    imageView113.setImageResource(R.drawable.tire);
                    tv113TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("103")) {
                if (e.getValue().equals("null")) {
                    imageView123.setImageResource(R.drawable.tire_null);
                } else {
                    imageView123.setImageResource(R.drawable.tire);
                    tv123TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("114")) {
                if (e.getValue().equals("null")) {
                    imageView134.setImageResource(R.drawable.tire_null);
                } else {
                    imageView134.setImageResource(R.drawable.tire);
                    tv134TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("124")) {
                if (e.getValue().equals("null")) {
                    imageView144.setImageResource(R.drawable.tire_null);
                } else {
                    imageView144.setImageResource(R.drawable.tire);
                    tv144TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("134")) {
                if (e.getValue().equals("null")) {
                    imageView154.setImageResource(R.drawable.tire_null);
                } else {
                    imageView154.setImageResource(R.drawable.tire);
                    tv154TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("144")) {
                if (e.getValue().equals("null")) {
                    imageView164.setImageResource(R.drawable.tire_null);
                } else {
                    imageView164.setImageResource(R.drawable.tire);
                    tv164TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("SP0")) {
                if (e.getValue().equals("null")) {
                    spImageView.setImageResource(R.drawable.tire_null);
                } else {
                    spImageView.setImageResource(R.drawable.tire);
                    tvspTextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("111")) {
                String position = Integer.toString(Integer.parseInt(e.getKey().toString().substring(0, 2)) - 10) + e.getKey().toString().substring(2, 3);
                ;
                if (e.getValue().equals("null")) {
                    imageView11.setImageResource(R.drawable.tire_null);
                } else {
                    imageView11.setImageResource(R.drawable.tire);
                    tv11TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("121")) {
                if (e.getValue().equals("null")) {
                    imageView21.setImageResource(R.drawable.tire_null);
                } else {
                    imageView21.setImageResource(R.drawable.tire);
                    tv21TextView.setText(seriesMap.get(e.getValue()));

                }
            }
            if (e.getKey().equals("131")) {
                if (e.getValue().equals("null")) {
                   imageView31.setImageResource(R.drawable.tire_null);
                } else {
                    imageView31.setImageResource(R.drawable.tire);
                    tv31TextView.setText(seriesMap.get(e.getValue()));

                }
            }
            if (e.getKey().equals("141") && (!e.getValue().equals("null"))) {
                if (e.getValue().equals("null")) {
                         imageView41.setImageResource(R.drawable.tire_null);
                } else {
                    imageView41.setImageResource(R.drawable.tire);
                    tv41TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("152")) {
                if (e.getValue().equals("null")) {
                    imageView52.setImageResource(R.drawable.tire_null);
                } else {
                    imageView52.setImageResource(R.drawable.tire);
                    tv52TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("162")) {
                if (e.getValue().equals("null")) {
                    imageView62.setImageResource(R.drawable.tire_null);
                } else {
                    imageView62.setImageResource(R.drawable.tire);
                    tv62TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("172")) {
                if (e.getValue().equals("null")) {
                    imageView72.setImageResource(R.drawable.tire_null);
                } else {
                    imageView72.setImageResource(R.drawable.tire);
                    tv72TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("182")) {
                if (e.getValue().equals("null")) {
                    imageView82.setImageResource(R.drawable.tire_null);
                } else {
                    imageView82.setImageResource(R.drawable.tire);
                    tv82TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("193")) {
                if (e.getValue().equals("null")) {
                    imageView93.setImageResource(R.drawable.tire_null);
                } else {
                    imageView93.setImageResource(R.drawable.tire);
                    tv93TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("203")) {
                if (e.getValue().equals("null")) {
                  imageView103.setImageResource(R.drawable.tire_null);
              } else {
                    imageView103.setImageResource(R.drawable.tire);
                  tv103TextView.setText(seriesMap.get(e.getValue()));
               }
            }
            if (e.getKey().equals("213") ) {
                if (e.getValue().equals("null")) {
                    imageView113.setImageResource(R.drawable.tire_null);
                } else {
                    imageView113.setImageResource(R.drawable.tire);
                    tv113TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("223") ) {
                if (e.getValue().equals("null")) {
                    imageView123.setImageResource(R.drawable.tire_null);
                } else {
                    imageView123.setImageResource(R.drawable.tire);
                    tv123TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("234") ) {
                if (e.getValue().equals("null")) {
                    imageView134.setImageResource(R.drawable.tire_null);
                } else {
                    imageView134.setImageResource(R.drawable.tire);
                    tv134TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("244")) {
                if (e.getValue().equals("null")) {
                    imageView144.setImageResource(R.drawable.tire_null);
                } else {
                    imageView144.setImageResource(R.drawable.tire);
                    tv144TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("254")) {
                if (e.getValue().equals("null")) {
                    imageView154.setImageResource(R.drawable.tire_null);
                } else {
                    imageView154.setImageResource(R.drawable.tire);
                    tv154TextView.setText(seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("264")) {
                if (e.getValue().equals("null")) {
                    imageView164.setImageResource(R.drawable.tire_null);
                } else {
                    imageView164.setImageResource(R.drawable.tire);
                    tv164TextView.setText(seriesMap.get(e.getValue()));
                }
            }
        }


//                    if(em.getKey().equals("223")){
//                        if (!em.getValue().equals("")) {
//                            imageView123.setImageResource(R.drawable.tire);
//                            tv123TextView.setText(seriesMap.get(em.getValue()));
//                        } else {
//                            imageView123.setImageResource(R.drawable.tire_null);
//                        }
//                    }
//
//
//
//
//
//                }



              /*  if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//
//                                imageView21.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView21.setImageResource(R.drawable.tire);
//                            }*/

          /*      Iterator iter = arrMap.entrySet().iterator();

                while (iter.hasNext()) {
                    Log.d("Value", "arrMap ==> "+ iter.next());
                    Log.d("Position", "dataMap ==> "+ dataMap.get(iter.next()));


                }*/

               /*Iterator i = listTemp.iterator();
                while (i.hasNext()) {
                   HashMap a = (HashMap)i.next();

                    Log.d("getList","--->"+i.next());
                }*/

                //Log.d("subList","::::"+listTemp.subList(1, 1));

               /* for (int k = 0;k<lineList.size();k++) {
                    Log.d("aline","-->"+k+ ". "+lineList.get(k));
                    Log.d("Position", "Position ==> " + dataMap.get(positionNoString[2]));
                }

                Map<String, List<HashMap>> formMap2 = new HashMap<String,List<HashMap>>();
                for (int k = 0;k<lineList.size();k++) {
                    formMap2.put(lineList.get(k).toString(), listTemp);
                }
                Log.d("Map2", "Map2 ==> " + formMap2);

             Map<String, List<String>> arrHMap = new HashMap<String,List<String>>();


                List<Map<String, String>> arrListM = new ArrayList<Map<String, String>>();


                Map<String, Map<String, Map<String, String>>> formatMap = new HashMap<String, Map<String, Map<String,String>>>();

                for (int i = 0;i < positionNoString.length;i++) {

                    //Map<String,String>
                    String subPosition = positionString[i].substring(1,2);

                    formatMap.put(positionNoString[i], new HashMap<String, Map<String, String>>());

                    formatMap.get(positionNoString[i]).put(subPosition, new HashMap<String, String>());
                    formatMap.get(positionNoString[i]).get(subPosition).put("tireSeries",tireNoString[i]);
                    formatMap.get(positionNoString[i]).get(subPosition).put("tireId",tireIdString[i]);
                    formatMap.get(positionNoString[i]).get(subPosition).put("position",positionString[i]);

                }

*/


  /*


                Map<String, Map<String, Map<String, Map<String, String>>>> formatMap = new HashMap<String, Map<String, Map<String, Map<String, String>>>>();



                for (int i = 0;i < positionNoString.length;i++) {

                    String subPosition = positionString[i].substring(1,2);
                    Log.d("Pos", "SubPosition------>" + subPosition);
                    formatMap.put(positionNoString[i], new HashMap<String, Map<String, Map<String,String>>>());

                    formatMap.get(positionNoString[i]).put(lineString[i], new HashMap<String, Map<String, String>>());

                    formatMap.get(positionNoString[i]).get(lineString[i]).put(subPosition, new HashMap<String, String>());

                    formatMap.get(positionNoString[i]).get(lineString[i]).get(subPosition).put("tireSeries", tireNoString[i]);
                    formatMap.get(positionNoString[i]).get(lineString[i]).get(subPosition).put("tireId", tireIdString[i]);
                    formatMap.get(positionNoString[i]).get(lineString[i]).get(subPosition).put("position", positionString[i]);


                    }

                Log.d("ArrFormat", "_____>"  + formatMap);

                Log.d("forMat","Size_____>"+formatMap.size());

                Iterator iter = formatMap.entrySet().iterator();

                while (iter.hasNext()) {
                    Map.Entry mEntry = (Map.Entry) iter.next();
                    Log.d("entry", "--.--" +mEntry.getKey() +">>>"+ mEntry.getValue());
                    //Log.d("cnt", "--.--" + formatMap.get("mEntry.getValue()").size());
                }

*/


                //String[] arrFormat = formatMap.values().toArray(new String[0]);


                // String subPosition = positionString[i].substring(1, 2);

//                    Log.d("Position", "Tire ID in Loop --> " + tireIdString[i]);
//            String temp = positionNoString[i] + lineString[i];
//
//                    switch (temp){
//                        case ("11") :
//
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//
//                                imageView21.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView21.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("21") :
//
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView31.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView31.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("31") :
//
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView31.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView31.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("32") :
//
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView52.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView52.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("41") :
//
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView41.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView41.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("42") :
//
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView62.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView62.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("52") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView72.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView72.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("62") :
//                            if (tireIdString[i].equals("null")|| tireIdString[i].equals("")) {
//                                imageView82.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView82.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("72") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView72.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView72.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("73") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView93.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView93.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("82") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView82.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView82.setImageResource(R.drawable.tire);
//                            }
//                            continue;
////                            break;
//                        case ("83") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView103.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView103.setImageResource(R.drawable.tire);
//                            }
//                            continue;
////                            break;
//                        case ("93") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView113.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView113.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("103") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView123.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView123.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("113") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView113.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView113.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("123") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                imageView123.setImageResource(R.drawable.tire_null);
//                            } else {
//                                imageView123.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        case ("SP0") :
//                            if (tireIdString[i].equals("null") || tireIdString[i].equals("")) {
//                                spImageView.setImageResource(R.drawable.tire_null);
//                            } else {
//                                spImageView.setImageResource(R.drawable.tire);
//                            }
//                            continue;
//                        default:
//                            if(temp == "11"){
//                                imageView11.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "21"){
//                                imageView21.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "31"){
//                                imageView31.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "41"){
//                                imageView41.setImageResource(R.drawable.tire_null);
//                            }if(temp == "52"){
//                            imageView52.setImageResource(R.drawable.tire_null);
//                            }if(temp == "62"){
//                                imageView62.setImageResource(R.drawable.tire_null);
//                            }if(temp == "72"){
//                                imageView72.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "82"){
//                                imageView82.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "93"){
//                                imageView11.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "103"){
//                                imageView103.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "113"){
//                                imageView113.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "123"){
//                                imageView123.setImageResource(R.drawable.tire_null);
//                            }
//                            if(temp == "SP0"){
//                                spImageView.setImageResource(R.drawable.tire_null);
//                            }
//
//
//                           // spImageView.setImageResource(R.drawable.tire_null);
//
////                            break;
//                    }
//
//


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
