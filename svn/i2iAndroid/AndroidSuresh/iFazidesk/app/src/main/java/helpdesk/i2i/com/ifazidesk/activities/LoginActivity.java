package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.getset.BuildingdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.CompanydetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.FloordetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.LocationdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.Login;
import helpdesk.i2i.com.ifazidesk.getset.WingdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall_MultiLanguage;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    String string_username = "";
    String string_password = "";
    String temp_string_username = "";
    String temp_string_password = "";
    EditText edittext_username;
    EditText edittext_password;
    Button button_submit;
    Button button_forgotpassword;
    CheckBox checkbox_rememberme;
    private ProgressDialog pDialog;
    public Preferences prefs;
    boolean boolean_checkinternet = false;
    String check_rememberme = "";
    CheckBox checkbox_showpassword;
    String string_forgotpassword_emailid="";
    AlertDialog Dialog_forgotpassword;
    TextView textView_forgotpassword;
    String string_token = "";
    String string_userID = "";
    String string_PRODUCTID = "8";
    String string_Language = "";
    Spinner spinner_language;
    TextView textView_language;
    TextView textView_morelanguage;
    String[] items_languagecode;
    List<String> list_language = new ArrayList<String>();
    List<String> list_languagecode = new ArrayList<String>();
    String string_selectedlanguage = "";
    String string_selectedlanguagecode = "";
    Boolean boolean_changelanguage = false;
    private Realm mRealm;
    APIInterface apiService;
    String TAG ="Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       /* getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
*/
        prefs = new Preferences (getApplicationContext());
        apiService = APIClient.getClient().create(APIInterface.class);
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("ifazidesk_db.realm")
                .schemaVersion(0)
                .build();
        mRealm = Realm.getInstance(realmConfig);
        //mRealm = Realm.getDefaultInstance();


        textView_forgotpassword = (TextView) findViewById(R.id.textview_forgotpassword);
        edittext_username = (EditText) findViewById(R.id.input_email);
        edittext_username.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edittext_password = (EditText) findViewById(R.id.input_password);
        button_submit = (Button) findViewById(R.id.btn_login);
        //button_forgotpassword = (Button) findViewById(R.id.btn_forgotpassword);
        checkbox_rememberme = (CheckBox) findViewById(R.id.ch_rememberme);
        checkbox_showpassword = (CheckBox) findViewById(R.id.radiobutton_showpassword);
        //spinner_language = (Spinner) findViewById(R.id.spinner_language);
        textView_language = (TextView) findViewById(R.id.textview_language);
        textView_morelanguage = (TextView) findViewById(R.id.textview_changelanguage);
        boolean_checkinternet = checkinternetconnection();

        temp_string_username = prefs.getString("Login_username");
        temp_string_password = prefs.getString("Login_password");

        string_selectedlanguage = prefs.getString("Lang");
        boolean_changelanguage = prefs.getBoolean("LangChange");

        checkbox_rememberme.setChecked(false);
        new WebService_GetLanguages().execute();
        if(temp_string_username.length() > 2 && temp_string_password.length() > 2)
        {
            edittext_username.setText(temp_string_username);
            edittext_password.setText(temp_string_password);
        }

        if (boolean_checkinternet == true) {
           // rl_nointernet.setVisibility(View.GONE);
            try {
                string_token = "" + FirebaseInstanceId.getInstance().getToken();

                if (string_token.length() < 10) {
                    string_token = FirebaseInstanceId.getInstance().getToken();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        checkbox_showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edittext_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edittext_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (edittext_username.getText().toString().trim().length() < 1 || edittext_password.getText().toString().trim().length() < 1)
                {
                    Toast.makeText(getApplicationContext(), R.string.toast_loginfailed, Toast.LENGTH_SHORT).show();
                }
                else {
                    string_username = edittext_username.getText().toString().trim();
                    string_password = edittext_password.getText().toString().trim();
                    boolean_checkinternet = checkinternetconnection();
                    if(boolean_checkinternet == true)
                    {
                        //new UserLoginAsync().execute();
                        Login();
                    }
                }
            }
        });

        textView_forgotpassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

       /* button_forgotpassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final EditText input = new EditText(LoginActivity.this);
                input.setHint("Please enter the Email ID");
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                Dialog_forgotpassword = new AlertDialog.Builder(
                        LoginActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle("Forgot Password")
                        .setView(input)
                        .setPositiveButton("Submit",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        string_forgotpassword_emailid = input.getText().toString().trim();
                                        new ForgotPassword().execute();
                                       *//* if(string_forgotpassword_emailid.length()<2 &&
                                                string_forgotpassword_emailid.contains("@") &&
                                                string_forgotpassword_emailid.contains("."))
                                        {
                                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                                            new ForgotPassword().execute();
                                        }*//*
                                    }
                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                            }

                        }).show();

            }
        });*/


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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mRealm.close();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }

    public void Login()
    {
        Call<Login> call = apiService.GetLoginDetails(string_username,string_password);
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage(getResources().getString(R.string.progress_loading));
        pDialog.setCancelable(false);
        pDialog.show();

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                int statusCode = response.code();

                mRealm.beginTransaction();
                mRealm.deleteAll();
                mRealm.commitTransaction();

                mRealm.beginTransaction();
                mRealm.copyToRealmOrUpdate(response.body());
                mRealm.commitTransaction();

                RealmList<CompanydetailsItem> companylist = response.body().getCompanydetails();
                RealmList<LocationdetailsItem> locationlist = response.body().getLocationdetails();
                RealmList<BuildingdetailsItem> buildinglist = response.body().getBuildingdetails();
                RealmList<FloordetailsItem> floorlist = response.body().getFloordetails();
                RealmList<WingdetailsItem> winglist = response.body().getWingdetails();
                String message = response.body().getMessage();
                Boolean Status = response.body().isStatus();
                int compid = response.body().getCompanyID();
                int GroupID = response.body().getGroupID();
                int UserID = response.body().getUserID();
                int RoleID = response.body().getRoleID();
                String ContactNo = response.body().getContactNo();
                String EmailID = response.body().getEmailID();
                String UserName = response.body().getFirstName();
                String User_Company = response.body().getCompanyName();
                String Company_Logo = response.body().getLogopath();
                string_Language = response.body().getLanguage();
                Boolean isResTimeReq = response.body().isIsResTimeReq();
                Boolean IsAdmin = response.body().isIsAdmin();
                Boolean isEmployee = response.body().isIsEmployee();
                Boolean isServiceEngineer = response.body().isIsServiceEngineer();
                if(message.equals("Login Success") || Status == true )
                {

                    if(checkbox_rememberme.isChecked())
                    {
                        check_rememberme = "true";
                    }
                    else
                    {
                        check_rememberme = "false";
                    }

                    prefs.setInt("CompanyID_SP", compid);
                    prefs.setInt("GroupID_SP", GroupID);
                    prefs.setString("UserName_SP", UserName);
                    prefs.setString("User_Company", User_Company);
                    prefs.setString("Company_Logo", Company_Logo);
                    prefs.setInt("UserID_SP", UserID);
                    prefs.setInt("RoleID", RoleID);
                    prefs.setString("ContactNo_SP", ContactNo);
                    prefs.setString("EmailID_SP", EmailID);
                    prefs.setString("Login_username", string_username);
                    prefs.setString("Login_password", string_password);
                    prefs.setString("isResTimeReq", ""+isResTimeReq);
                    prefs.setString("RememberMe_SP", check_rememberme);
                    prefs.setString("isAdmin_SP", ""+IsAdmin);
                    prefs.setString("isEmployee_SP", ""+isEmployee);
                    prefs.setString("isServiceEngineer_SP", ""+isServiceEngineer);
                    prefs.setBoolean("isServiceEngineer_Boolean", isServiceEngineer);
                    Log.i("Login Status", "Login Success");
                    Toast.makeText(getApplicationContext(), R.string.toast_loginsuccess, 2000).show();
                    string_userID = ""+UserID;
                    new WebService_NotificationID().execute();
                }
                else
                {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.toast_loginfailed, 2000).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                pDialog.dismiss();
            }
        });
    }

    public class UserLoginAsync extends AsyncTask<String, Void, String> {
        String responsedata = "";
        String loginresult = "";
        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getResources().getString(R.string.progress_loading));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Log.i("Value inserted", "Value inserted");
            responsedata= WebService.invokeLoginWS(string_username, string_password);
            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Login", result);
           /* if(pDialog.isShowing())
            {
               // pDialog.dismiss();*/
                loginresult = result;
	 /*
	  * [
	  *  {
	  *   "IsAdmin":false,"RoleID":21,"UserID":74,
	  *   "GroupID":1,"CompanyID":5,"CompanyName":"Ericsson",
	  *   "GroupName":"Jones Lang LaSalle","EmailID":"narasimhan.s@ericsson.com",
	  *   "FirstName":"Narasimhan","ContactNo":"9945199447",
	  *   "isServiceEngineer":false,"isController":false,
	  *   "Status":true,"Message":"Login Success"
	  *  }
	  * ]*/

                try {
                    JSONArray json_array=new JSONArray(loginresult);

                    String message = json_array.getJSONObject(0).getString("Message");
                    String status = json_array.getJSONObject(0).getString("Status");
                    int compid = json_array.getJSONObject(0).getInt("CompanyID");
                    int GroupID = json_array.getJSONObject(0).getInt("GroupID");
                    int UserID = json_array.getJSONObject(0).getInt("UserID");
                    int RoleID = json_array.getJSONObject(0).getInt("RoleID");
                    String ContactNo = json_array.getJSONObject(0).getString("ContactNo");
                    String EmailID = json_array.getJSONObject(0).getString("EmailID");
                    String UserName = json_array.getJSONObject(0).getString("FirstName");
                    String User_Company = json_array.getJSONObject(0).getString("CompanyName");
                    String Company_Logo = json_array.getJSONObject(0).getString("logopath");
                    string_Language = json_array.getJSONObject(0).getString("Language");
                    Boolean isResTimeReq = json_array.getJSONObject(0).getBoolean("isResTimeReq");
                    Boolean IsAdmin = json_array.getJSONObject(0).getBoolean("IsAdmin");
                    Boolean isEmployee = json_array.getJSONObject(0).getBoolean("isEmployee");
                    Boolean isServiceEngineer = json_array.getJSONObject(0).getBoolean("isServiceEngineer");
                    JSONArray jsonArray_company = json_array.getJSONObject(0).getJSONArray("companydetails");
                    JSONArray jsonArray_location = json_array.getJSONObject(0).getJSONArray("locationdetails");
                    JSONArray jsonArray_building = json_array.getJSONObject(0).getJSONArray("buildingdetails");
                    JSONArray jsonArray_floor = json_array.getJSONObject(0).getJSONArray("floordetails");
                    final JSONArray jsonArray_wing = json_array.getJSONObject(0).getJSONArray("wingdetails");

                    //mRealm.beginTransaction();
                    //mRealm.deleteAll();
                    //mRealm.commitTransaction();

//                    long numberOfObjects = 1000000;
//                    SharedGroup sharedGroup = new SharedGroup("default.realm");
//                    WriteTransaction writeTransaction = sharedGroup.beginWrite();
//                    Table table = writeTransaction.getTable("class_Product");
//                    table.addEmptyRows(numberOfObjects);
//                    for (int i = 0; i < numberOfObjects; i++) {
//                        table.setLong(0, i, i);              // id
//                        table.setString(1, i, "Product_"+i); // name
//                        table.setString(2, i, "SKU__"+i);    // sku
//                        table.SetDate(3, i, new Date());     // date
//                    }
//                    writeTransaction.commit();
//                    sharedGroup.close();



//                    for(int i = 0;i<jsonArray_company.length();i++)
//                    {
//                        mRealm.beginTransaction();
//                        CompanydetailsItem companyitem = mRealm.createObject(CompanydetailsItem.class);
//                        companyitem.setCompany(jsonArray_company.getJSONObject(i).getString("Company"));
//                        companyitem.setCompanyId(jsonArray_company.getJSONObject(i).getInt("CompanyId"));
//                        mRealm.commitTransaction();
//                    }
//                    for(int i = 0;i<jsonArray_location.length();i++)
//                    {
//                        mRealm.beginTransaction();
//                        LocationdetailsItem locationitem = mRealm.createObject(LocationdetailsItem.class);
//                        locationitem.setCompanyId(jsonArray_location.getJSONObject(i).getInt("CompanyId"));
//                        locationitem.setLocationId(jsonArray_location.getJSONObject(i).getInt("LocationId"));
//                        locationitem.setLocation(jsonArray_location.getJSONObject(i).getString("Location"));
//                        mRealm.commitTransaction();
//                    }
//                    for(int i = 0;i<jsonArray_building.length();i++)
//                    {
//                        mRealm.beginTransaction();
//                        BuildingdetailsItem buildingitem = mRealm.createObject(BuildingdetailsItem.class);
//                        buildingitem.setLocationId(jsonArray_building.getJSONObject(i).getInt("LocationId"));
//                        buildingitem.setBuildingID(jsonArray_building.getJSONObject(i).getInt("BuildingID"));
//                        buildingitem.setBuilding(jsonArray_building.getJSONObject(i).getString("Building"));
//                        mRealm.commitTransaction();
//                    }
//                    for(int i = 0;i<jsonArray_floor.length();i++)
//                    {
//                        mRealm.beginTransaction();
//                        FloordetailsItem flooritem = mRealm.createObject(FloordetailsItem.class);
//                        flooritem.setBuildingId(jsonArray_floor.getJSONObject(i).getInt("BuildingId"));
//                        flooritem.setFloorID(jsonArray_floor.getJSONObject(i).getInt("FloorID"));
//                        flooritem.setFloor(jsonArray_floor.getJSONObject(i).getString("Floor"));
//                        mRealm.commitTransaction();
//                    }
//                    for(int i = 0;i<jsonArray_wing.length();i++)
//                    {
//                        mRealm.beginTransaction();
//                        WingdetailsItem wingitem = mRealm.createObject(WingdetailsItem.class);
//                        wingitem.setFloorId(jsonArray_wing.getJSONObject(i).getInt("FloorId"));
//                        wingitem.setWingID(jsonArray_wing.getJSONObject(i).getInt("WingID"));
//                        wingitem.setWing(jsonArray_wing.getJSONObject(i).getString("Wing"));
//                        mRealm.commitTransaction();
//                    }
//                    final Model_Wing wingitem = new Model_Wing();
//
//                    try(Realm realm = Realm.getDefaultInstance()) {
//                        realm.executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                try {
//                                    for(int i = 0 ; i < jsonArray_wing.length(); i++){
//                                        wingitem.setFloorId(jsonArray_wing.getJSONObject(i).getInt("FloorId"));
//                                        wingitem.setWingID(jsonArray_wing.getJSONObject(i).getInt("WingID"));
//                                        wingitem.setWing(jsonArray_wing.getJSONObject(i).getString("Wing"));
//                                        realm.insert(wingitem);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }


//                    Gson gson = new Gson();
//                    String temp_stringcompany = jsonArray_company.toString();
//                    Model_CompanyList companyItems;
//                    Type listType = new TypeToken<List<Model_Company>>(){}.getType();
//                    companyItems = new Gson().fromJson(temp_stringcompany, listType);
//                    Log.i("ModelCompany",companyItems.toString());

                    //companyItems.add(model_company);
                    if(message.equals("Login Success") || status.equals("true") )
                    {

                        if(checkbox_rememberme.isChecked())
                        {
                            check_rememberme = "true";
                        }
                        else
                        {
                            check_rememberme = "false";
                        }

                        prefs.setInt("CompanyID_SP", compid);
                        prefs.setInt("GroupID_SP", GroupID);
                        prefs.setString("UserName_SP", UserName);
                        prefs.setString("User_Company", User_Company);
                        prefs.setString("Company_Logo", Company_Logo);
                        prefs.setString("JSONArray_Company",""+jsonArray_company);
                        prefs.setString("JSONArray_Location",""+jsonArray_location);
//                        prefs.setString("JSONArray_Building",""+jsonArray_building);
//                        prefs.setString("JSONArray_Floor",""+jsonArray_floor);
//                        prefs.setString("JSONArray_Wing",""+jsonArray_wing);
                        prefs.setInt("UserID_SP", UserID);
                        prefs.setInt("RoleID", RoleID);
                        prefs.setString("ContactNo_SP", ContactNo);
                        prefs.setString("EmailID_SP", EmailID);
                        prefs.setString("Login_username", string_username);
                        prefs.setString("Login_password", string_password);
                        prefs.setString("isResTimeReq", ""+isResTimeReq);
                        prefs.setString("RememberMe_SP", check_rememberme);
                        prefs.setString("isAdmin_SP", ""+IsAdmin);
                        prefs.setString("isEmployee_SP", ""+isEmployee);
                        prefs.setString("isServiceEngineer_SP", ""+isServiceEngineer);
                        prefs.setBoolean("isServiceEngineer_Boolean", isServiceEngineer);
                        Log.i("Login Status", "Login Success");
                        Toast.makeText(getApplicationContext(), R.string.toast_loginsuccess, 2000).show();
                        string_userID = ""+UserID;
                        new WebService_NotificationID().execute();
                        //pDialog.dismiss();
                        /*Intent i =new Intent(LoginActivity.this, DashboardNew.class);
                        startActivity(i);
                        finish();*/
                    }
                }
                catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.toast_loginfailed, 2000).show();
                }
           // }
            super.onPostExecute(result);
        }
    }


    //InsertUpdateNotificationID
    public class WebService_NotificationID extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
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
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("userid", "");
            string_token = FirebaseInstanceId.getInstance().getToken();
            String[] keys = { "userid","tokenid"};
            String[] values = {string_userID,string_token};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(LoginActivity.this).postDataToSOAPService(ht, "InsertUpdateNotificationID");
                Log.i("data:","InsertUpdateNotificationID:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                   /* {
                        "Status": true,
                            "Message": "Saved Sucessfully",
                            "CompliantId": 0
                    }*/
                    try {
                       // pDialog.dismiss();
                        JSONObject json_obj=new JSONObject(result);

                        boolean status = json_obj.getBoolean("Status");
                        if(status == true)
                        {
                            if(boolean_changelanguage == true)
                            {
                                string_Language = string_selectedlanguage;
                                new WebService_GetLanguageForUser().execute();
                            }
                            else {
                                new WebService_GetLanguageForUser().execute();
                            }
                            /*Intent i = new Intent(LoginActivity.this, DashboardNew.class);
                            startActivity(i);
                            finish();*/
                        }
                        else
                        {

                        }
                    }
                    catch (JSONException e) {
                        pDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), R.string.toast_somethingwrong, 2000).show();
                    }
                } catch (NumberFormatException e) {
                    if(pDialog != null && pDialog.isShowing())
                    {
                        pDialog.dismiss();
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if(pDialog != null && dialog.isShowing())
                {
                    pDialog.dismiss();
                }
            }

        }
    }




    //Get Language For User
    public class WebService_GetLanguageForUser extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
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
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("userid", "");

            String[] keys = { "productid","language"};
            String[] values = {string_PRODUCTID,string_Language};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall_MultiLanguage(LoginActivity.this).postDataToSOAPService(ht, "GetLanguageControls");
                Log.i("data:","GetLanguageControls:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                    try {
                        pDialog.dismiss();
                        prefs.setString("Language", result);
                        prefs.setString("Lang",string_selectedlanguage);
                        prefs.setBoolean("LangChange",boolean_changelanguage);
                        //Intent i = new Intent(LoginActivity.this, DashboardNew.class);
                        Intent i = new Intent(LoginActivity.this, PreDashboard.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                    catch (Exception e) {
                        pDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), R.string.toast_somethingwrong, 2000).show();
                    }
                } catch (NumberFormatException e) {
                    if(pDialog != null && pDialog.isShowing())
                    {
                        pDialog.dismiss();
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if(pDialog != null && dialog.isShowing())
                {
                    pDialog.dismiss();
                }
            }

        }
    }




    public class WebService_GetLanguages extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
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
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("userid", "");

            String[] keys = { "productid"};
            String[] values = {string_PRODUCTID};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall_MultiLanguage(LoginActivity.this).postDataToSOAPService(ht, "GetLanguesByProduct");
                Log.i("data:","GetLanguesByProduct:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                    try {
                        list_language.clear();
                        //[{"language":"English"},{"language":"French"},{"language":"German"},{"language":"Spanish"}]
                        JSONArray jsonArray_language = new JSONArray(result);

                        for(int i=0; i<jsonArray_language.length();i++)
                        {
                            String string_Language = jsonArray_language.getJSONObject(i).getString("language");
                            String string_langcode= jsonArray_language.getJSONObject(i).getString("langcode");
                            string_selectedlanguage = jsonArray_language.getJSONObject(0).getString("language");
                            textView_language.setText(string_selectedlanguage);
                            list_language.add(string_Language);
                            list_languagecode.add(string_langcode);
                        }
                        textView_language.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                showDialog_Language();

                            }
                        });

                        textView_morelanguage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                showDialog_Language();

                            }
                        });
                        prefs.setString("AvailableLanguages", result);


                    }
                    catch (Exception e) {

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

        }
    }


    public void showDialog_Language() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.spinner_selectlanguage);
        int j_selected = 0;
        //list of items
        final String[] items = list_language.toArray(new String[list_language.size()]);
        final String[] items_code = list_languagecode.toArray(new String[list_language.size()]);
        for(int k=0;k<items.length;k++)
        {
            if(string_selectedlanguage.equals(items[k]))
            {
                j_selected = k;
                break;
            }
        }

        builder.setSingleChoiceItems(items, j_selected,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // item selected logic
                        string_selectedlanguage = items[i];
                        string_selectedlanguagecode = items_code[i];
                        /*Resources res = getApplicationContext().getResources();
                        DisplayMetrics dm = res.getDisplayMetrics();
                        android.content.res.Configuration conf = res.getConfiguration();
                        conf.setLocale(new Locale(string_selectedlanguage.toLowerCase()));
                        res.updateConfiguration(conf, dm);*/


                    }
                });

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        textView_language.setText(string_selectedlanguage);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            Resources resources = getApplicationContext().getResources();
                            Configuration configuration = resources.getConfiguration();
                            configuration.setLocale(new Locale(string_selectedlanguagecode));
                            getApplicationContext().getApplicationContext().createConfigurationContext(configuration);
                            getApplicationContext().getResources().updateConfiguration(configuration, getApplicationContext().getResources().getDisplayMetrics());

                        } else {
                            Locale locale = new Locale(string_selectedlanguagecode);
                            Locale.setDefault(locale);
                            Configuration config = getApplicationContext().getResources().getConfiguration();
                            config.locale = locale;
                            getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
                        }
                        boolean_changelanguage = true;
                        prefs.setString("Lang",string_selectedlanguage);
                        prefs.setBoolean("LangChange",boolean_changelanguage);

                        /*Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);*/

                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        @SuppressWarnings("unused")
        AlertDialog objAlertDialog = new AlertDialog.Builder(
                LoginActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(R.string.dialog_exitmsg)
                .setPositiveButton(R.string.dialog_button_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                               if(checkbox_rememberme.isChecked())
                                {
                                    check_rememberme = "true";
                                }
                                else
                                {
                                    check_rememberme = "false";
                                }
                                prefs.setString("RememberMe_SP", check_rememberme);
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                                startActivity(intent);
                                System.exit(0);
                                moveTaskToBack(true);
                                finish();
                            }
                        })
                .setNegativeButton(R.string.dialog_button_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).show();
    }


}
