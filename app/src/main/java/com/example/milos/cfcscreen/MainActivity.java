package com.example.milos.cfcscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import info.hoang8f.android.segmented.SegmentedGroup;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ProgressDialog dialog;
    private RadioButton radioLocal;
    ArrayList<CFCModel> list = new ArrayList<>();
    private CfcAdapter cfcAdapter;
    private Button button;
    private static String url = "https://raw.githubusercontent.com/danieloskarsson/mobile-coding-exercise/master/items.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listjson);
        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.segmented2);
        button = (Button) findViewById(R.id.button_continue);

        getSupportActionBar().setTitle("        Select Cause(s)");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppState.isSwiped = false;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!AppState.isSwiped){
                    Toast.makeText(MainActivity.this, "Please select one of the given causes", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(MainActivity.this,ProgressBarActivity.class));
                }
            }
        });

        radioLocal = (RadioButton) findViewById(R.id.radio_local);
        radioLocal.setChecked(true);
        final ArrayList<String> items = new ArrayList<String>();
        items.add("A");
        items.add("B");
        items.add("C");
        items.add("D");
        items.add("E");
        items.add("F");
        items.add("G");
        items.add("H");
        items.add("I");
        items.add("K");
        items.add("J");
        items.add("L");
        items.add("M");
        items.add("N");
        items.add("O");
        items.add("P");
        items.add("Q");
        items.add("R");
        items.add("S");
        items.add("T");
        items.add("U");
        items.add("V");
        items.add("W");
        items.add("X");
        items.add("Y");
        items.add("Z");

        ArrayAdapter<String> aItems = new ArrayAdapter<>(this, R.layout.alphabet, items);
        final TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               for(int i =0; i<list.size(); i++){
                  if(items.get(position).equalsIgnoreCase(list.get(i).getTitle().substring(0,1).toLowerCase())){
                      lv.setSelection(i);
                      break;
                  }
               }
            }
        });
        lvTest.setAdapter(aItems);
        new getData().execute();
    }

    public class getData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please wait");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONParser parser = new JSONParser();

            String jsonStr = parser.makeServiceCall(url);
            if (jsonStr != null) {
                try {

                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        CFCModel model = new CFCModel();
                        model.setImagecfc(obj.getString("image"));
                        model.setTitle(obj.getString("description"));
                        model.setLocation(obj.getString("title"));
                        list.add(model);

                    }
                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            //sorting the list
            Collections.sort(list, new Comparator<CFCModel>() {
                @Override
                public int compare(CFCModel o1, CFCModel o2) {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }
            });

            // Updating parsed JSON data into ListView
            cfcAdapter = new CfcAdapter(getApplicationContext(), R.layout.list_item, list);
            lv.setAdapter(cfcAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_search, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
