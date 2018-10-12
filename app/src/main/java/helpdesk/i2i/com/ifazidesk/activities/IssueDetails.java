package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;

public class IssueDetails extends AppCompatActivity {

    TextView textView_map_location;
    TextView textView_map_building;
    TextView textView_map_floor;
    TextView textView_map_wing;
    TextView textView_location;
    TextView textView_building;
    TextView textView_floor;
    TextView textView_wing;
    TextView textView_dept;
    Button button_next;
    private ProgressDialog pDialog;
    public Preferences prefs;
    String string_scannedcode = "";
    int int_companyid = 0;
    int int_locationid = 0;
    String arrow=" -> ";
    boolean boolean_checkinternet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        prefs = new Preferences(getApplicationContext());
        string_scannedcode = prefs.getString("id_qrcode");
        int_companyid = prefs.getInt("CompanyID_SP");

        textView_map_location = (TextView)findViewById(R.id.mloc);
        textView_map_building = (TextView)findViewById(R.id.mbuild);
        textView_map_floor = (TextView)findViewById(R.id.mfloor);
        textView_map_wing = (TextView)findViewById(R.id.mwing);

        textView_location = (TextView)findViewById(R.id.tv_loc);
        textView_building = (TextView)findViewById(R.id.tv_build);
        textView_floor = (TextView)findViewById(R.id.tv_floor);
        textView_wing = (TextView)findViewById(R.id.tv_wing);
        textView_dept = (TextView)findViewById(R.id.tv_dept);
        button_next = (Button) findViewById(R.id.button_next);

        boolean_checkinternet = checkinternetconnection();
        if(boolean_checkinternet == true) {
            new GetDetailsByBarcode().execute();
        }

        button_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prefs.setString("QRCODE","true");
                Intent ii9i=new Intent(IssueDetails.this,IssueList.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });


    }

    public boolean checkinternetconnection()
    {
        try {
            ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nf=cn.getActiveNetworkInfo();

            if(nf != null && nf.isConnected()==true )
            {
                boolean_checkinternet = true;
            }
            else
            {
                boolean_checkinternet = false;
                Toast.makeText(this, "Network Not Available!\nPlease Check the Internet Settings", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boolean_checkinternet;
    }


    public class GetDetailsByBarcode extends AsyncTask<String, Void, String> {

        String LocationName = "";
        String BuildingName = "";
        String FloorName = "";
        String WingName = "";
        String DepartmentName = "";
        int DeptID;
        String L_SN="";
        String B_SN="";
        String F_SN="";
        String W_SN="";
        int L_ID;
        int B_ID;
        int F_ID;
        int W_ID;
        String responsedata = "";
        String loginresult = "";

        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueDetails.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            Log.i("Value inserted", "Value inserted");
            responsedata= WebService.GetDetailsByBarCode(int_companyid, string_scannedcode);
            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Barcode", result);
            if(pDialog.isShowing()){
                pDialog.dismiss();
                loginresult = result;

                //[{"ID":109064,
                // "Barcode":"2040b153",
                // "CompanyID":1,
                // "CompanyName":"Jones Lang LaSalle",
                // "LocationID":1,
                // "LocationName":"UB City",
                // "LocationShortName":"UBC",
                // "BuildingID":1,
                // "BuildingName":"Concorde",
                // "BuildingShortName":"CON",
                // "FloorID":1,
                // "FloorName":"Level 3",
                // "FloorShortName":"L3",
                // "WingID":2040,
                // "WingName":"Cantonment",
                // "WingShortName":"",
                // "DepartmentID":153,
                // "DepartmentName":"Meeting Room"}]



                try {

                    JSONArray json_array=new JSONArray(loginresult);

                    LocationName = json_array.getJSONObject(0).getString("LocationName");
                    BuildingName = json_array.getJSONObject(0).getString("BuildingName");
                    FloorName = json_array.getJSONObject(0).getString("FloorName");
                    WingName = json_array.getJSONObject(0).getString("WingName");
                    DepartmentName = json_array.getJSONObject(0).getString("DepartmentName");
                    DeptID  = json_array.getJSONObject(0).getInt("DepartmentID");
                    L_SN = json_array.getJSONObject(0).getString("LocationShortName");
                    B_SN  = json_array.getJSONObject(0).getString("BuildingShortName");
                    F_SN  = json_array.getJSONObject(0).getString("FloorShortName");
                    W_SN  = json_array.getJSONObject(0).getString("WingShortName");

                    L_ID = json_array.getJSONObject(0).getInt("LocationID");
                    B_ID = json_array.getJSONObject(0).getInt("BuildingID");
                    F_ID = json_array.getJSONObject(0).getInt("FloorID");
                    W_ID = json_array.getJSONObject(0).getInt("WingID");

                    prefs.setInt("DeptID_SP", DeptID);
                    prefs.setInt("LocationID_SP", L_ID);
                    prefs.setInt("BuildingID_SP", B_ID);
                    prefs.setInt("FloorID_SP", F_ID);
                    prefs.setInt("WingID_SP", W_ID);

                    textView_location.setText(LocationName);
                    textView_building.setText(BuildingName);
                    textView_floor.setText(FloorName);
                    textView_wing.setText(WingName);
                    textView_dept.setText(DepartmentName);
                    textView_map_location.setText(L_SN+arrow);
                    textView_map_building.setText(B_SN+arrow);
                    textView_map_floor.setText(F_SN+arrow);
                    textView_map_wing.setText(W_SN);
                }
                catch (JSONException e) {

                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(IssueDetails.this);
                    Log.i("NO data","Dialog");
                    builder.setTitle("No Data!");

                    builder.setIcon(R.drawable.bariconsmall);
                    builder.setMessage("Sorry!\nPlease Try Again");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent ii9i=new Intent(IssueDetails.this,ScanScreen.class);
                            ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ii9i);
                            //do things
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                }
            }
            super.onPostExecute(result);
        }


    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent ii9i=new Intent(IssueDetails.this,ScanScreen.class);
        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ii9i);
        return;
    }
}
