package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;

public class IssueSubmit extends AppCompatActivity {
    TextView textView_issuename;
    TextView textView_request_type;
    TextView textView_priority;
    public Preferences prefs;
    EditText editText_description;
    Button button_submit;
    private ProgressDialog pDialog;
    int int_dept_id = 0 ;
    int int_company_id = 0;
    int int_locationid_id = 0;
    int int_building_id = 0;
    int int_floor_id = 0;
    int int_wing_id = 0;
    int int_group_id = 0;
    int int_user_id = 0;
    int int_role_id = 0;
    int int_request_typeid = 0;
    int int_priority_id;
    int int_issue_id;

    String string_issuename = "";
    String string_priority = "";
    String string_priorityid = "";
    String string_responsetime_sp = "";
    boolean boolean_checkinternet = true;
    RadioGroup radioGroup_requestype;
    String string_username ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_submit);
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

        editText_description = (EditText)findViewById(R.id.edittext_comment);
        textView_issuename = (TextView) findViewById(R.id.textview_issue);
        textView_request_type = (TextView) findViewById(R.id.tv_request_type);
        textView_priority = (TextView)findViewById(R.id.textview_priority);
        button_submit = (Button) findViewById(R.id.button_submit);
        radioGroup_requestype = (RadioGroup)findViewById(R.id.radiogroup_requesttype);

        string_issuename = (prefs.getString("IssueName_SP"));
        string_priority = (prefs.getString("Priority_SP"));
        string_priorityid = (prefs.getString("PriorityID_SP"));
        int_priority_id = Integer.parseInt(string_priorityid);
        string_responsetime_sp = (prefs.getString("ResponseTime_SP"));


        int_company_id  = (prefs.getInt("CompanyID_SP"));
        int_group_id  = (prefs.getInt("GroupID_SP"));
        int_dept_id  = (prefs.getInt("DeptID_SP"));
        int_role_id  = (prefs.getInt("RoleID"));
        int_locationid_id  = (prefs.getInt("LocationID_SP"));
        int_building_id  = (prefs.getInt("BuildingID_SP"));
        int_floor_id  = (prefs.getInt("FloorID_SP"));
        int_wing_id  = (prefs.getInt("WingID_SP"));
        int_issue_id = (prefs.getInt("IssueID_SP"));
        int_user_id = (prefs.getInt("UserID_SP"));
        string_username = (prefs.getString("UserName_SP"));


        textView_issuename.setText(string_issuename);
        textView_priority.setText(string_priority);

        new GetRequestType().execute();
        radioGroup_requestype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                int_request_typeid = checkedId;

            }
        });


        button_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(editText_description.getText().toString().trim().length()<1)
                {
                    Toast.makeText(getApplicationContext(), "Enter Description", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean_checkinternet = checkinternetconnection();
                    if(boolean_checkinternet == true)
                    {
                        new IssueSubmitService().execute();
                    }
                }

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
    public class GetRequestType extends AsyncTask<String, Void, String> {
        String responsedata = "";
        String loginresult = "";
        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueSubmit.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            responsedata= WebService.GetRequestType(int_company_id,int_role_id);
            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Request Type", result);
            if(pDialog.isShowing()){
                pDialog.dismiss();
                loginresult = result;
                //  [{"requestTypdId":1,"requestType":"Reactive"}]

                try {
                    JSONArray json_array=new JSONArray(loginresult);

                    RadioButton[] rb = new RadioButton[json_array.length()];
                    for(int i=0; i<json_array.length();i++)
                    {
                        int RequestID = json_array.getJSONObject(i).getInt("requestTypdId");
                        String Request = json_array.getJSONObject(i).getString("requestType");
                        rb[i] = new RadioButton(IssueSubmit.this);
                        rb[i].setButtonDrawable(null);
                        rb[i].setTextColor(Color.WHITE);
                        rb[i].setBackgroundResource(R.drawable.radiobuttonbg_1);
                        rb[i].setButtonDrawable(android.R.color.transparent);
                        rb[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        rb[i].setId(RequestID);
                        rb[i].setText(Request);
                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.weight = 1.0f;
                        params.gravity = Gravity.CENTER_VERTICAL;
                        params.setMargins(5, 5, 5, 5);
                        radioGroup_requestype.addView(rb[i], params);
                    }

                }
                catch (JSONException e) {

                    e.printStackTrace();
                    radioGroup_requestype.setVisibility(View.INVISIBLE);
                    textView_request_type.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "No Request Type Assigned!", 2000).show();

                }
            }
            super.onPostExecute(result);
        }


    }



    public class IssueSubmitService extends AsyncTask<String, Void, String> {

        String desc_text = editText_description.getText().toString();
        String responsedata="";
        String issueresult="";


        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueSubmit.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            responsedata= WebService.SumbitIssues
                    (
                            int_company_id, int_group_id, int_locationid_id, int_building_id, int_floor_id, int_wing_id, int_dept_id, int_issue_id,
                            desc_text, int_priority_id, string_responsetime_sp, int_user_id,int_request_typeid,string_username
                    );

					/*(CompanyID,
					GroupID,
					LocationID,
					BuildingID,
					FloorID,
					WingID,
					DepartmentID,
					IssueID,
					Description,
					PriorityID,
					ResponseTime,
					UserID,
					UserMailID,
					UserMobileNo,
					UserName)*/

            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Issue", result);
            if(pDialog.isShowing()){
                pDialog.dismiss();
                issueresult = result;
                try {
                    JSONObject json_obj = new JSONObject(issueresult);
                    boolean status = json_obj.getBoolean("Status");
                    String string_responsetime = json_obj.getString("TicketResponseTime");
                    if(status == true)
                    {
                        prefs.setString("Ticket_RT",string_responsetime);
                        Toast.makeText(getApplicationContext(), "Ticket Rised Successfully ", 2000).show();
                        Intent ii9i=new Intent(IssueSubmit.this,Finish.class);
                        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii9i);
                    }
                    else
                    {
                        String message = json_obj.getString("Message");
                        Toast.makeText(getApplicationContext(), ""+message, 2000).show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Ticket Rise Failed!\nTry Again", 2000).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}
