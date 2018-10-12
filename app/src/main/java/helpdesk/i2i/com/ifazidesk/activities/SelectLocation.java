package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List;
import helpdesk.i2i.com.ifazidesk.getset.BuildingdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.CompanydetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.FloordetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.getset.LocationdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.WingdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SelectLocation extends AppCompatActivity {
    Preferences prefs;
    String string_userid = "";
    String string_username = "";
    String string_usercompany = "";
    String string_companylogo = "";
    String string_companydetails = "";
    String string_locationdetails = "";
    String string_buildingdetails = "";
    String string_floordetails = "";
    String string_wingdetails = "";
    int int_roleid = 0;
    List<GetSet> list_company = new ArrayList<GetSet>();
    List<GetSet> list_location = new ArrayList<GetSet>();
    List<GetSet> list_building = new ArrayList<GetSet>();
    List<GetSet> list_floor = new ArrayList<GetSet>();
    List<GetSet> list_wing = new ArrayList<GetSet>();
    JSONArray jsonArray_list;

    RelativeLayout rl_selectcompany;
    RelativeLayout rl_selectlocation;
    RelativeLayout rl_selectbuilding;
    RelativeLayout rl_selectfloor;
    RelativeLayout rl_selectwing;

    TextView textview_company;
    TextView textview_location;
    TextView textview_building;
    TextView textview_floor;
    TextView textview_wing;

    String string_companyname;
    String string_companyid;
    String string_locationname;
    String string_locationid;
    String string_buildingname;
    String string_buildingid;
    String string_floorname;
    String string_floorid;
    String string_wingname;
    String string_wingid;

    final static String ARG_PARAM1 = "ListData";
    final static String ARG_PARAM2 = "SearchTitle";
    final static String ARG_PARAM3 = "ListType";
    final static String ARG_PARAM4 = "ID";

    Boolean isCompanySelected = false;
    Boolean isLocationSelected = false;
    Boolean isBuildingSelected = false;
    Boolean isFloorSelected = false;
    Boolean isWingSelected = false;

    int int_defaultcompanyid = 0;
    Button button_next;
    //Model_CompanyList model_companyList;
    private ProgressDialog pDialog;
    private Realm mRealm;
    RealmResults<CompanydetailsItem> companylist;
    RealmResults<LocationdetailsItem> locationlist;
    RealmResults<BuildingdetailsItem> buildinglist;
    RealmResults<FloordetailsItem> floorlist;
    RealmResults<WingdetailsItem> winglist;
    String string_date_temp = "";
    String string_date = "";

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Create Realm instance for the UI thread
//        Realm.init(SelectLocation.this);
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
//                .name("ifazidesk_db.realm")
//                .schemaVersion(0)
//                .build();
//        mRealm = Realm.getInstance(realmConfig);
//
//        companylist = mRealm.where(CompanydetailsItem.class).findAllAsync();
//        companylist.load();
//
////        locationlist = mRealm.where(LocationdetailsItem.class).findAllAsync();
////        locationlist.load();
////
////        buildinglist = mRealm.where(BuildingdetailsItem.class).findAllAsync();
////        buildinglist.load();
////
////        floorlist = mRealm.where(FloordetailsItem.class).findAllAsync();
////        floorlist.load();
////
////        winglist = mRealm.where(WingdetailsItem.class).findAllAsync();
////        winglist.load();
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Raise New Ticket");

//        prefs.setString("JSONArray_Building",""+jsonArray_building);
//        prefs.setString("JSONArray_Floor",""+jsonArray_floor);
//        prefs.setString("JSONArray_Wing",""+jsonArray_wing);

        prefs = new Preferences(this);


        int_defaultcompanyid = prefs.getInt("CompanyID_SP");
        int_roleid = prefs.getInt("RoleID");
        string_userid = ""+prefs.getInt("UserID_SP");
        string_username = prefs.getString("UserName_SP");
        string_usercompany = prefs.getString("User_Company");
        //string_companydetails = prefs.getString("JSONArray_Company");
        //string_locationdetails = prefs.getString("JSONArray_Location");
        //string_buildingdetails = prefs.getString("JSONArray_Building");
        //string_floordetails = prefs.getString("JSONArray_Floor");
        //string_wingdetails = prefs.getString("JSONArray_Wing");
        string_companylogo = prefs.getString("Company_Logo");

        rl_selectcompany = (RelativeLayout)findViewById(R.id.rl_selectcompany);
        rl_selectlocation = (RelativeLayout)findViewById(R.id.rl_selectlocation);
        rl_selectbuilding = (RelativeLayout)findViewById(R.id.rl_selectbuilding);
        rl_selectfloor = (RelativeLayout)findViewById(R.id.rl_selectfloor);
        rl_selectwing = (RelativeLayout)findViewById(R.id.rl_selectwing);

        textview_company = (TextView)findViewById(R.id.textview_companyname);
        textview_location = (TextView)findViewById(R.id.textview_locationname);
        textview_building = (TextView)findViewById(R.id.textview_buildingname);
        textview_floor = (TextView)findViewById(R.id.textview_floorname);
        textview_wing = (TextView)findViewById(R.id.textview_wingname);

        button_next = (Button)findViewById(R.id.button_next);
        button_next.setEnabled(false);
        Realm.init(SelectLocation.this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("ifazidesk_db.realm")
                .schemaVersion(0)
                .build();
        mRealm = Realm.getInstance(realmConfig);


        companylist = mRealm.where(CompanydetailsItem.class).findAllAsync();
        companylist.load();

//        Realm.init(SelectLocation.this);
//        mRealm = Realm.getDefaultInstance();
//
//        mRealm.beginTransaction();
//        companylist = mRealm.where(Model_Company.class).findAllAsync();
//        companylist.load();
//
//        locationlist = mRealm.where(Model_Location.class).findAllAsync();
//        locationlist.load();
//
//        buildinglist = mRealm.where(Model_Building.class).findAllAsync();
//        buildinglist.load();
//
//        floorlist = mRealm.where(Model_Floor.class).findAllAsync();
//        floorlist.load();
////
//        winglist = mRealm.where(Model_Wing.class).findAllAsync();
//        winglist.load();
//
//        mRealm.close();

//        Log.i("CompanyModel",""+companylist);
//        Log.i("LocationModel",""+locationlist);
//        Log.i("BuildingModel",""+buildinglist);
//        Log.i("FloorModel",""+floorlist);
//        Log.i("WingModel",""+winglist);

        for(int j = 0;j<companylist.size();j++)
        {
            String temp_Company = companylist.get(j).getCompany();
            int temp_CompanyId =  companylist.get(j).getCompanyId();
            if(int_defaultcompanyid == temp_CompanyId)
            {
                SetCompany(temp_Company,""+temp_CompanyId);
                //isCompanySelected = true;
                break;
            }else
            {
                SetCompany(companylist.get(0).getCompany(), ""+companylist.get(0).getCompanyId());
                int_defaultcompanyid = companylist.get(0).getCompanyId();
            }
        }

        Calendar c = Calendar.getInstance();
        int current_year = c.get(Calendar.YEAR);
        int current_month = c.get(Calendar.MONTH)+1;
        int current_date = c.get(Calendar.DAY_OF_MONTH);
        String string_currentmonth = ""+current_month;
        String string_currentdate = ""+current_date;
        if(current_month<10)
        {
            string_currentmonth = "0"+current_month;
        }
        if(current_date<10)
        {
            string_currentdate = "0"+current_date;
        }
        string_date_temp = string_currentdate+"/"+string_currentmonth+"/"+current_year;
        string_date = string_currentmonth+"/"+string_currentdate+"/"+current_year;
//
//        for(int i=0;i<locationlist.size();i++)
//        {
//            int CompanyId = locationlist.get(i).getCompanyId();
//            int LocationID = locationlist.get(i).getLocationId();
//            String LocationName = locationlist.get(i).getLocation();
//            if (CompanyId == int_defaultcompanyid) {
//                SetLocation(LocationName, "" + LocationID);
//            }
//        }



        //new GetRealmData().execute();




//        try {
//            JSONArray json_array_company = new JSONArray(string_companydetails);
//            for (int i = 0; i < json_array_company.length(); i++)
//            {
//                int CompanyId = json_array_company.getJSONObject(i).getInt("CompanyId");
//                String Company = json_array_company.getJSONObject(i).getString("Company");
//
//                if(int_defaultcompanyid == CompanyId)
//                {
//                    SetCompany(Company,""+CompanyId);
//                    //isCompanySelected = true;
//                    break;
//                }else
//                {
//                    //textview_company.setText(json_array_company.getJSONObject(0).getString("Company"));
//                    //string_companyid = ""+json_array_company.getJSONObject(0).getInt("CompanyId");
//                    SetCompany(json_array_company.getJSONObject(0).getString("Company"),
//                            ""+json_array_company.getJSONObject(0).getInt("CompanyId"));
//                    int_defaultcompanyid = json_array_company.getJSONObject(0).getInt("CompanyId");
//                }
//            }
//
//            JSONArray json_array_location = new JSONArray(string_locationdetails);
//            for(int i=0;i<json_array_location.length();i++)
//            {
//                int CompanyId = json_array_location.getJSONObject(i).getInt("CompanyId");
//                int LocationID = json_array_location.getJSONObject(i).getInt("LocationId");
//                String LocationName = json_array_location.getJSONObject(i).getString("Location");
//                if(CompanyId == int_defaultcompanyid)
//                {
//                    SetLocation(LocationName,""+LocationID);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWingSelected == true && isFloorSelected == true && isBuildingSelected == true &&
                        isLocationSelected == true && isCompanySelected == true)
                {
                    prefs.setString("DASHBOARD_COMPANY", string_companyname);
                    prefs.setString("DASHBOARD_COMPANYID", string_companyid);
                    prefs.setString("DASHBOARD_LOCATION", string_locationname);
                    prefs.setString("DASHBOARD_LOCATIONID", string_locationid);
                    prefs.setString("DASHBOARD_TYPE", "TODAY");
                    prefs.setString("DASHBOARD_STARTDATE", string_date);
                    prefs.setString("DASHBOARD_ENDDATE", string_date);
                    prefs.setString("DASHBOARD_STARTDATE_TEMP", string_date_temp);
                    prefs.setString("DASHBOARD_ENDDATE_TEMP", string_date_temp);

                    // TODO Auto-generated method stub
                    prefs.setString("selected_CompanyID", string_companyid);
                    prefs.setString("selected_CompanyName", string_companyname);
                    prefs.setString("selected_LocationID", string_locationid);
                    prefs.setString("selected_LocationName", string_locationname);

                    prefs.setString("selected_BuildingID", string_buildingid);
                    prefs.setString("selected_BuildingName", string_buildingname);
                    prefs.setString("selected_FloorID", string_floorid);
                    prefs.setString("selected_FloorName", string_floorname);
                    prefs.setString("selected_WingID", string_wingid);
                    prefs.setString("selected_WingName", string_wingname);

                    Intent intent = new Intent(getApplicationContext(), IssueDept.class);
                    startActivity(intent);
                }
            }
        });

        rl_selectcompany.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                textview_company.setError(null);

                android.support.v4.app.FragmentManager manager = SelectLocation.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();

                bundle.putString(ARG_PARAM1, string_companydetails);
                bundle.putString(ARG_PARAM2, "Company");
                bundle.putString(ARG_PARAM3, "isCompanyList");
                bundle.putString(ARG_PARAM4, "");

                Fragment_List dialog = new Fragment_List();
                dialog.setArguments(bundle);
                dialog.show(manager, "dialog");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });

        rl_selectlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isCompanySelected == false)
                {
                    textview_company.setError("Select Company");
                }
                else {
                    android.support.v4.app.FragmentManager manager = SelectLocation.this.getSupportFragmentManager();
                    Bundle bundle = new Bundle();

                    bundle.putString(ARG_PARAM1, string_locationdetails);
                    bundle.putString(ARG_PARAM2, "Location");
                    bundle.putString(ARG_PARAM3, "isLocationList");
                    bundle.putString(ARG_PARAM4, string_companyid);

                    Fragment_List dialog = new Fragment_List();
                    dialog.setArguments(bundle);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });


        rl_selectbuilding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isLocationSelected == false)
                {
                    textview_location.setError("Select Location");
                }
                else {
                    android.support.v4.app.FragmentManager manager = SelectLocation.this.getSupportFragmentManager();
                    Bundle bundle = new Bundle();

                    bundle.putString(ARG_PARAM1, string_buildingdetails);
                    bundle.putString(ARG_PARAM2, "Building");
                    bundle.putString(ARG_PARAM3, "isBuildingList");
                    bundle.putString(ARG_PARAM4, string_locationid);

                    Fragment_List dialog = new Fragment_List();
                    dialog.setArguments(bundle);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });


        rl_selectfloor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isBuildingSelected == false)
                {
                    textview_building.setError("Select Building");
                }
                else {
                    android.support.v4.app.FragmentManager manager = SelectLocation.this.getSupportFragmentManager();
                    Bundle bundle = new Bundle();

                    bundle.putString(ARG_PARAM1, string_floordetails);
                    bundle.putString(ARG_PARAM2, "Floor");
                    bundle.putString(ARG_PARAM3, "isFloorList");
                    bundle.putString(ARG_PARAM4, string_buildingid);

                    Fragment_List dialog = new Fragment_List();
                    dialog.setArguments(bundle);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });


        rl_selectwing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isFloorSelected == false)
                {
                    textview_building.setError("Select Floor");
                }
                else {
                    android.support.v4.app.FragmentManager manager = SelectLocation.this.getSupportFragmentManager();
                    Bundle bundle = new Bundle();

                    bundle.putString(ARG_PARAM1, string_wingdetails);
                    bundle.putString(ARG_PARAM2, "Wing");
                    bundle.putString(ARG_PARAM3, "isWingList");
                    bundle.putString(ARG_PARAM4, string_floorid);

                    Fragment_List dialog = new Fragment_List();
                    dialog.setArguments(bundle);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });


    }




    public class WebService_Building extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
           /* pDialog = new ProgressDialog(SelectLocation.this);
            pDialog.setMessage(getResources().getString(R.string.progress_loading));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","LocationID"};
            String[] values = {string_companyid,string_locationid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                string_buildingdetails = new NetworkCall(SelectLocation.this).postDataToSOAPService(ht, "GetBuildingDetails");
                Log.i("data:","GetBuildingDetails:"+string_buildingdetails);
            } catch (Exception ee) {
            }
            return string_buildingdetails;
        }
        @Override
        protected void onPostExecute(String string_buildingdetails) {
            try {
                try {
                    Log.i("data:", "" + string_buildingdetails);
                    //pDialog.dismiss();
                    try {
                        JSONArray jsonArray_building = new JSONArray(string_buildingdetails);
                        String BuildingID = jsonArray_building.getJSONObject(0).getString("BuildingID");
                        String BuildingName = jsonArray_building.getJSONObject(0).getString("BuildingName");
                        SetBuilding(BuildingName,BuildingID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                //pDialog.dismiss();
            }

        }
    }


    public class WebService_Floor extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
           /* pDialog = new ProgressDialog(SelectLocation.this);
            pDialog.setMessage(getResources().getString(R.string.progress_loading));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","BuildingID"};
            String[] values = {string_companyid,string_buildingid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                string_floordetails = new NetworkCall(SelectLocation.this).postDataToSOAPService(ht, "GetFloorDetails");
                Log.i("data:","GetFloorDetails:"+string_floordetails);
            } catch (Exception ee) {
            }
            return string_floordetails;
        }
        @Override
        protected void onPostExecute(String string_floordetails) {
            try {
                try {
                    //pDialog.dismiss();
                    Log.i("data:", "" + string_floordetails);
                    try {
                            JSONArray jsonArray_floor = new JSONArray(string_floordetails);
                            String FloorID = jsonArray_floor.getJSONObject(0).getString("FloorID");
                            String FloorName = jsonArray_floor.getJSONObject(0).getString("FloorName");
                            SetFloor(FloorName,FloorID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    /*if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }*/
                    e.printStackTrace();
                }
            } catch (Exception e) {
                //pDialog.dismiss();
            }

        }
    }



    public class WebService_Wing extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            /*dialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
           // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();*/
          /*  pDialog = new ProgressDialog(SelectLocation.this);
            pDialog.setMessage(getResources().getString(R.string.progress_loading));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","FloorID"};
            String[] values = {string_companyid,string_floorid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                string_wingdetails = new NetworkCall(SelectLocation.this).postDataToSOAPService(ht, "GetWingDetails");
                Log.i("data:","GetWingDetails:"+string_wingdetails);
            } catch (Exception ee) {
            }
            return string_wingdetails;
        }
        @Override
        protected void onPostExecute(String string_wingdetails) {
            try {
               /* if(pDialog.isShowing()) {
                    pDialog.dismiss();
                }*/
                try {
                    Log.i("data:", "" + string_wingdetails);
                    try {
                        JSONArray jsonArray_wing = new JSONArray(string_wingdetails);
                            String WingID = jsonArray_wing.getJSONObject(0).getString("WingID");
                            String WingName = jsonArray_wing.getJSONObject(0).getString("WingName");
                            SetWing(WingName,WingID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    /*if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }*/
                    e.printStackTrace();
                }
            } catch (Exception e) {
                //pDialog.dismiss();
            }

        }
    }

    public class GetRealmData extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            /*dialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
           // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();*/
            pDialog = new ProgressDialog(SelectLocation.this);
            pDialog.setMessage(getResources().getString(R.string.progress_loading));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

//            Realm.init(SelectLocation.this);
//            mRealm = Realm.getDefaultInstance();
//
//            companylist = mRealm.where(Model_Company.class).findAllAsync();
//            companylist.load();
//
//            locationlist = mRealm.where(Model_Location.class).findAllAsync();
//            locationlist.load();
//
//            buildinglist = mRealm.where(Model_Building.class).findAllAsync();
//            buildinglist.load();
//
//            floorlist = mRealm.where(Model_Floor.class).findAllAsync();
//            floorlist.load();
//
//            winglist = mRealm.where(Model_Wing.class).findAllAsync();
//            winglist.load();
//
//            Log.i("CompanyModel",""+companylist);
//            Log.i("LocationModel",""+locationlist);
//            Log.i("BuildingModel",""+buildinglist);
//            Log.i("FloorModel",""+floorlist);
//            Log.i("WingModel",""+winglist);
            return null;
        }
        @Override
        protected void onPostExecute(String string_wingdetails) {
            try {

//                for(int j = 0;j<companylist.size();j++)
//                {
//                    String temp_Company = companylist.get(j).getCompany();
//                    int temp_CompanyId =  companylist.get(j).getCompanyId();
//                    if(int_defaultcompanyid == temp_CompanyId)
//                    {
//                        SetCompany(temp_Company,""+temp_CompanyId);
//                        //isCompanySelected = true;
//                        break;
//                    }else
//                    {
//                        SetCompany(companylist.get(0).getCompany(), ""+companylist.get(0).getCompanyId());
//                        int_defaultcompanyid = companylist.get(0).getCompanyId();
//                    }
//                }

//                for(int i=0;i<locationlist.size();i++)
//                {
//                    int CompanyId = locationlist.get(i).getCompanyId();
//                    int LocationID = locationlist.get(i).getLocationId();
//                    String LocationName = locationlist.get(i).getLocation();
//                    if (CompanyId == int_defaultcompanyid) {
//                        SetLocation(LocationName, "" + LocationID);
//                    }
//                }


            } catch (Exception e) {
                //pDialog.dismiss();
            }

        }
    }

    public void SetCompany(String Name, String ID)
    {
        textview_company.setText(Name);
        string_companyname = Name;
        string_companyid = ID;
        isCompanySelected = true;
        int new_CompanyId = Integer.parseInt(string_companyid);

        locationlist = mRealm.where(LocationdetailsItem.class).equalTo("companyId",new_CompanyId).findAll();
        locationlist.load();

        for(int i=0;i<locationlist.size();i++)
        {
            int CompanyId = locationlist.get(0).getCompanyId();
            int LocationID = locationlist.get(0).getLocationId();
            String LocationName = locationlist.get(0).getLocation();
            if (CompanyId == Integer.parseInt(string_companyid))
            {
                SetLocation(LocationName, "" + LocationID);
            }
        }
    }

    public void SetLocation(String Name, String ID)
    {
        string_locationname = Name;
        textview_location.setText(Name);
        string_locationid = ID;
        isLocationSelected = true;
        int new_LocationId = Integer.parseInt(string_locationid);
        //new WebService_Building().execute();


        buildinglist = mRealm.where(BuildingdetailsItem.class).equalTo("locationId",new_LocationId).findAll();
        buildinglist.load();

        for(int i=0;i<buildinglist.size();i++)
        {
            int LocationID = buildinglist.get(0).getLocationId();
            int BuildingID = buildinglist.get(0).getBuildingID();
            String Building = buildinglist.get(0).getBuilding();
            if (LocationID ==Integer.parseInt(string_locationid)) {
                SetBuilding(Building, "" + BuildingID);
            }
        }
    }

    public void SetBuilding(String Name, String ID)
    {
        string_buildingname = Name;
        textview_building.setText(Name);
        string_buildingid = ID;
        isBuildingSelected = true;
        //new WebService_Floor().execute();
        int new_BuildingId = Integer.parseInt(string_buildingid);

        floorlist = mRealm.where(FloordetailsItem.class).equalTo("buildingId",new_BuildingId).findAll();
        floorlist.load();
        for(int i=0;i<floorlist.size();i++)
        {
            int BuildingId = floorlist.get(0).getBuildingId();
            int FloorID = floorlist.get(0).getFloorID();
            String Floor = floorlist.get(0).getFloor();
            if (BuildingId ==Integer.parseInt(string_buildingid)) {
                SetFloor(Floor, "" + FloorID);
            }
        }

    }

    public void SetFloor(String Name, String ID)
    {
        string_floorname = Name;
        textview_floor.setText(Name);
        string_floorid = ID;
        isFloorSelected = true;
        //new WebService_Wing().execute();
        int new_FloorId = Integer.parseInt(string_floorid);

        winglist = mRealm.where(WingdetailsItem.class).equalTo("floorId",new_FloorId).findAll();
        winglist.load();
        for(int i=0;i<winglist.size();i++)
        {
            int FloorId = winglist.get(0).getFloorId();
            int WingID = winglist.get(0).getWingID();
            String Wing = winglist.get(0).getWing();
            if (FloorId ==Integer.parseInt(string_floorid))
            {
                SetWing(Wing, ""+WingID);
            }
        }
    }

    public void SetWing(String Name, String ID)
    {
        string_wingname = Name;
        textview_wing.setText(Name);
        string_wingid = ID;
        isWingSelected = true;
        button_next.setBackgroundResource(R.drawable.drawable_button_new);
        button_next.setEnabled(true);
    }

}
