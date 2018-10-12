package helpdesk.i2i.com.ifazidesk.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Hashtable;

import helpdesk.i2i.com.ifazidesk.BuildConfig;
import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;

import static android.Manifest.permission.CAMERA;

public class SplashActivity extends AppCompatActivity {
    String rememberme = "false";
    private static int SPLASH_TIME_OUT = 3500;
    public Preferences prefs;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    private static final int STORAGE_REQUEST_CODE = 1;
    Boolean storageAccepted = false;
    String string_userID = "";
    String string_token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        File ImageFolder = new File(Environment.getExternalStorageDirectory() + "/i2i_ifazidesk/Images");
        if (!ImageFolder.exists()) {
            ImageFolder.mkdir();
        }

        /*if (checkPermission() == true)
        {
            File ImageFolder = new File(Environment.getExternalStorageDirectory() + "/iFazidesk/Images");
            if (!ImageFolder.exists()) {
                ImageFolder.mkdir();
            }
        }

        else
        {
            requestPermission();
        }*/



        prefs = new Preferences (this);
        rememberme = ""+(prefs.getString("RememberMe_SP"));

        TextView textView_buildnumber = (TextView)findViewById(R.id.textview_versionname);
        textView_buildnumber.setText("Build "+versionName);

        if(rememberme.equals("true"))
        {
            string_userID = ""+prefs.getInt("UserID_SP");
            new WebService_NotificationID().execute();
            /*Intent i = new Intent(SplashActivity.this, DashboardNew.class);
            startActivity(i);
            finish();*/
        }
        else
        {
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                    }
                }, SPLASH_TIME_OUT);
            } catch (Exception e) {

            }
        }

    }



    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),STORAGE_SERVICE);
        boolean isAccessGranted = false;
        if(result1 == 0)
        {
            isAccessGranted = false;
        }
        if(result1 == 0)
        {
            isAccessGranted = true;
        }
        return isAccessGranted;
    }

    private void requestPermission() {

       /* ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);*/
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted)
                    {
                        File ImageFolder = new File(Environment.getExternalStorageDirectory() + "/iFazidesk/Images");
                        if (!ImageFolder.exists()) {
                            ImageFolder.mkdir();
                        }
                    }

                }
                break;

        }
    }


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
                result = new NetworkCall(SplashActivity.this).postDataToSOAPService(ht, "InsertUpdateNotificationID");
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
                            try {
                                Intent i = new Intent(SplashActivity.this, PreDashboard.class);
                                //Intent i = new Intent(SplashActivity.this, DashboardNew.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            } catch (Exception e) {

                            }
                            //Boolean boolean_opencomplaint = prefs.getBoolean("OpenNotification");
                           /* if(boolean_opencomplaint == true)
                            {
                                Intent intent_complaint;
                                Bundle bundle = new Bundle();
                                bundle.putString("TicketNo", "" +  prefs.getString("TicketNo"));
                                bundle.putString("Ticket_isClosed", "" +  prefs.getString("Ticket_isClosed"));
                                bundle.putString("Ticket_StatusCount", "" +  prefs.getString("Ticket_StatusCount"));
                                String string_isadmin = prefs.getString("isAdmin_SP");
                                String string_isserviceengg = prefs.getString("isServiceEngineer_SP");
                                if(string_isadmin.equals("true")||string_isserviceengg.equals("true"))
                                {
                                    intent_complaint = new Intent(getApplicationContext(), TicketDetailsData.class);
                                    intent_complaint.putExtras(bundle);
                                    intent_complaint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent_complaint.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                }
                                else
                                {
                                    intent_complaint = new Intent(getApplicationContext(), TicketDetails_User.class);
                                    intent_complaint.putExtras(bundle);
                                    intent_complaint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent_complaint.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                }
                                startActivity(intent_complaint);
                            }
                            else {
                                Intent i = new Intent(SplashActivity.this, DashboardNew.class);
                                startActivity(i);
                                finish();
                            }*/
                        }
                        else
                        {
                            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    }
                    catch (JSONException e) {
                       // pDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), R.string.toast_somethingwrong, 2000).show();
                    }
                } catch (NumberFormatException e) {
                    /*if(pDialog != null && pDialog.isShowing())
                    {
                        pDialog.dismiss();
                    }*/
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

        }
    }
}
