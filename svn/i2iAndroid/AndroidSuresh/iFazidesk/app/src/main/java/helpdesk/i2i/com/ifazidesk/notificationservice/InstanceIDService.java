package helpdesk.i2i.com.ifazidesk.notificationservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.Hashtable;

import helpdesk.i2i.com.ifazidesk.activities.DashboardNew;
import helpdesk.i2i.com.ifazidesk.activities.LoginActivity;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;

public class InstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "Helpdesk_PUSH";
    //String string_url = "http://fmservicemgmt.com/carapi/TestService.svc/FCMUpdate";
    //HttpClient httpclient;
    String refreshedToken = "";
    String string_deviceid = "";
    String string_userID = "";
    Preferences prefs;
    @Override
    public void onTokenRefresh() {

        try {
            //Getting registration token
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
            prefs = new Preferences(getApplicationContext());
            string_userID = ""+prefs.getInt("UserID_SP");
            //FirebaseInstanceId.getToken();
            //Displaying token on logcat
            Log.d(TAG, "Refreshed token: " + refreshedToken);
            sendRegistrationToServer(refreshedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
        new WebService_NotificationID().execute();
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

            String[] keys = { "userid","tokenid"};
            String[] values = {string_userID,refreshedToken};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(getApplicationContext()).postDataToSOAPService(ht, "InsertUpdateNotificationID");
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
                        JSONObject json_obj=new JSONObject(result);
                       // pDialog.dismiss();
                        boolean status = json_obj.getBoolean("Status");
                        if(status == true)
                        {

                        }
                        else
                        {

                        }
                    }
                    catch (JSONException e) {

                    }
                } catch (NumberFormatException e) {
                    if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if(dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

        }
    }
    /*public class WebService_UpdateFCMToken extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            string_deviceid = Settings.Secure.getString(getBaseContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                // httpclient = new DefaultHttpClient();
                String string_temp_token = params[0];
                HttpPost httpost = new HttpPost(string_url);

                JSONStringer img = new JSONStringer()
                        .object()
                        .key("gcmID").value(string_temp_token)
                        .key("deviceSerialNo").value(string_deviceid)
                        .endObject();
                StringEntity se = new StringEntity(img.toString());
                httpost.setEntity(se);

                httpost.setHeader("Accept", "application/json");
                httpost.setHeader("Content-type", "application/json");

                Log.i("URL", "string_url:" + string_url);
                Log.i("JSON", "JSON:" + img.toString());
                Log.i("HttpPost", "string_post:" + httpost.toString());

                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, 90000);
                HttpConnectionParams.setSoTimeout(httpParameters, 90000);
                httpclient = new DefaultHttpClient(httpParameters);
                HttpResponse response = httpclient.execute(httpost);
                HttpEntity entity = response.getEntity();
                // Read the contents of an entity and return it as a String.
                result = EntityUtils.toString(entity);
                System.out.println(result);

                Log.i("HttpResponse", "content:" + result);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //try {
            Log.i("data:", "" + result);

            try {
                JSONObject jsonObject_root = new JSONObject(result);
                JSONObject jsonObject_SignInResult = jsonObject_root.getJSONObject("FCMUpdateResult");
                String jsonObject_message = jsonObject_SignInResult.getString("msg");
                boolean jsonObject_status = jsonObject_SignInResult.getBoolean("status");
                if (jsonObject_status == true) {

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/
}
