package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView myListView;
    private SearchView mySearchView;
    private String urlJSON,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        ConstantUrl constantUrl = new ConstantUrl();
        urlJSON = constantUrl.getUrlJSONLicense();


        mySearchView = (SearchView) findViewById(R.id.searchView2);
        myListView = (ListView) findViewById(R.id.listView2);

        username = new String();
        username = getIntent().getStringExtra("username");

        SyncVehicle syncVehicle = new SyncVehicle(this, urlJSON, myListView);
        syncVehicle.execute();

    }//main method


    private class SyncVehicle extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private String myURL;
        private ListView listView;
        private String[] licenseStrings, idStrings;

        public SyncVehicle(Context context, String myURL, ListView listView) {
            this.context = context;
            this.myURL = myURL;
            this.listView =  listView;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();
            } catch (IOException e) {
                Log.d("SPN1", "e doInBack ==> " + e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                Log.d("JSON", "JSON ==> " + jsonArray);


                licenseStrings = new String[jsonArray.length()];
                idStrings = new String[jsonArray.length()];
                final Map<String, String> licenceMap = new HashMap<String,String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    licenseStrings[i] = jsonObject.getString("veh_license");
                    idStrings[i] = jsonObject.getString("veh_id");
                    licenceMap.put(licenseStrings[i], idStrings[i]);

                }


                final ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, licenseStrings);
                listView.setTextFilterEnabled(true);
             //   filter = arrayAdapter.getFilter();
                listView.setAdapter(arrayAdapter);

                setUpSearchView();

              // Onclick on by item

                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String[] arrayStrings = getStringArray(arrayAdapter);
                        Intent intent = new Intent(CheckListActivity.this, itemVehicleActivity.class);
//                        intent.putExtra("license", licenseStrings[position]);
                        intent.putExtra("license", arrayStrings[position]);
                        intent.putExtra("id", licenceMap.get(arrayStrings[position]));
                        intent.putExtra("username", username);
                        Log.d("ID","Map ==> " + licenceMap.get(arrayStrings[position]));
                        startActivity(intent);
                    }


                });



            } catch (JSONException e) {
                Log.d("SPN2", "e doInBack ==> " + e);
            }
        }
    }

    public static String[] getStringArray(ArrayAdapter adapter){
        String[] a = new String[adapter.getCount()];

        for(int i=0; i<a.length; i++)
            a[i] = adapter.getItem(i).toString();

        return a;
    }

  /*  public void addItemOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);




    }*/


    private void setUpSearchView(){
        mySearchView.setIconifiedByDefault(false);
        mySearchView.setOnQueryTextListener(this);
        mySearchView.setSubmitButtonEnabled(true);

        mySearchView.setQueryHint("Search Vehicle");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            myListView.clearTextFilter();
        } else {
            myListView.setFilterText(newText.toString());

            //filter.filter(newText);
        }
        return true;
    }



}//Main class
