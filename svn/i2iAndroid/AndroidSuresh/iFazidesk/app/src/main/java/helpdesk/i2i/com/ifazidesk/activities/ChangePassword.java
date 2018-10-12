package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;

public class ChangePassword extends AppCompatActivity {
    EditText editText_firstname;
    EditText editText_companyname;
    EditText editText_emailid;
    EditText editText_mobileno;
    EditText editText_password;
    EditText editText_newpassword;
    EditText editText_confirmpassword;
    Button button_changepassword;
    Button button_updatepassword;
    CheckBox checkBox_showcurrentpassword;
    CheckBox checkBox_shownewpassword;
    CheckBox checkBox_showconfirmpassword;
    TextView textView_username;
    TextView textView_company;
    Preferences prefs;
    RelativeLayout rl_newpassword;
    RelativeLayout rl_confirmpassword;
    String string_newpassword = "";
    String string_confirmpassword = "";
    String string_userid = "";
    String string_username = "";
    String string_usercompany="";
    String string_oldpassword = "";
    Boolean boolean_changepassword = true;
    String string_Language = "";


    String string_language_HINT_EMAILID = "";
    String string_language_HINT_MOBILENO = "";
    String string_language_HINT_PASSWORD = "";
    String string_language_HINT_NEWPASSWORD = "";
    String string_language_HINT_CONFIRMPASSWORD = "";
    String string_language_BUTTON_CHANGE = "";
    String string_language_BUTTON_CANCEL = "";
    String string_language_BUTTON_UPDATE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window =  this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(getResources().getColor(android.R.color.black));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_change_password);

        prefs = new Preferences(getApplicationContext());


        string_username = prefs.getString("UserName_SP");
        string_usercompany = prefs.getString("User_Company");
        string_userid = ""+prefs.getInt("UserID_SP");
        string_oldpassword = prefs.getString("Login_password");
        string_Language = prefs.getString("Language");


        textView_username = (TextView) findViewById(R.id.textview_username);
        textView_username.setText(string_username);

        textView_company = (TextView) findViewById(R.id.textview_companyname);
        textView_company.setText(string_usercompany);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        TextInputLayout textInputLayout_editText_emailid  = (TextInputLayout) findViewById(R.id.input_et_emailid);
        TextInputLayout textInputLayout_editText_mobileno  = (TextInputLayout) findViewById(R.id.input_et_mobileno);
        TextInputLayout textInputLayout_editText_password  = (TextInputLayout) findViewById(R.id.input_et_password);
        TextInputLayout textInputLayout_editText_newpassword  = (TextInputLayout) findViewById(R.id.input_et_changepassword);
        TextInputLayout textInputLayout_editText_confirmpassword = (TextInputLayout) findViewById(R.id.input_et_confirmpassword);

        editText_emailid = (EditText) findViewById(R.id.input_emailid);
        editText_emailid.setEnabled(false);
        editText_emailid.setText(""+ prefs.getString("EmailID_SP"));


        editText_mobileno = (EditText) findViewById(R.id.input_mobileno);
        editText_mobileno.setEnabled(false);
        editText_mobileno.setText(""+ prefs.getString("ContactNo_SP"));

        editText_password = (EditText) findViewById(R.id.input_password);
        editText_password.setEnabled(false);
        editText_password.setText(string_oldpassword);

        editText_newpassword = (EditText) findViewById(R.id.input_changepassword);
        editText_confirmpassword = (EditText) findViewById(R.id.input_confirmpassword);

        button_changepassword = (Button) findViewById(R.id.button_changepassword);
        button_updatepassword = (Button) findViewById(R.id.button_updatepassword);

        checkBox_showcurrentpassword = (CheckBox) findViewById(R.id.radiobutton_showcurrentpassword);
        checkBox_shownewpassword = (CheckBox) findViewById(R.id.radiobutton_changepassword);
        checkBox_showconfirmpassword = (CheckBox) findViewById(R.id.radiobutton_confirmpassword);

        rl_newpassword = (RelativeLayout)findViewById(R.id.rl_info6);
        rl_confirmpassword = (RelativeLayout)findViewById(R.id.rl_info7);
        rl_newpassword.setVisibility(View.GONE);
        rl_confirmpassword.setVisibility(View.GONE);
        button_updatepassword.setVisibility(View.GONE);

        try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("edittext_hint_emailid"))
                {
                    string_language_HINT_EMAILID = langauge;
                    editText_emailid.setHint(string_language_HINT_EMAILID);
                    textInputLayout_editText_emailid.setHint(string_language_HINT_EMAILID);
                }
                if(Control.equals("edittext_hint_mobileno"))
                {
                    string_language_HINT_MOBILENO = langauge;
                    editText_mobileno.setHint(string_language_HINT_MOBILENO);
                    textInputLayout_editText_mobileno.setHint(string_language_HINT_MOBILENO);
                }
                if(Control.equals("edittext_hint_password"))
                {
                    string_language_HINT_PASSWORD = langauge;
                    editText_password.setHint(string_language_HINT_PASSWORD);
                    textInputLayout_editText_password.setHint(string_language_HINT_PASSWORD);
                }
                if(Control.equals("edittext_hint_newpassword"))
                {
                    string_language_HINT_NEWPASSWORD = langauge;
                    editText_newpassword.setHint(string_language_HINT_NEWPASSWORD);
                    textInputLayout_editText_password.setHint(string_language_HINT_NEWPASSWORD);
                }
                if(Control.equals("edittext_hint_conpassword"))
                {
                    string_language_HINT_CONFIRMPASSWORD = langauge;
                    editText_confirmpassword.setHint(string_language_HINT_CONFIRMPASSWORD);
                    textInputLayout_editText_confirmpassword.setHint(string_language_HINT_CONFIRMPASSWORD);
                }
                if(Control.equals("button_change"))
                {
                    string_language_BUTTON_CHANGE = langauge;
                    button_changepassword.setText(string_language_BUTTON_CHANGE);
                }
                if(Control.equals("button_cancel"))
                {
                    string_language_BUTTON_CANCEL = langauge;

                }
                if(Control.equals("button_updatepassword"))
                {
                    string_language_BUTTON_UPDATE = langauge;
                    button_updatepassword.setHint(string_language_BUTTON_UPDATE);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button_changepassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(boolean_changepassword == true)
                {
                    rl_newpassword.setVisibility(View.VISIBLE);
                    rl_confirmpassword.setVisibility(View.VISIBLE);
                    button_updatepassword.setVisibility(View.VISIBLE);
                    button_changepassword.setText(string_language_BUTTON_CANCEL);
                }
                else {
                    if (boolean_changepassword == false)
                    {
                        rl_newpassword.setVisibility(View.GONE);
                        rl_confirmpassword.setVisibility(View.GONE);
                        button_updatepassword.setVisibility(View.GONE);
                        button_changepassword.setText(string_language_BUTTON_CHANGE);
                    }
                }
                if(boolean_changepassword == true)
                {
                    boolean_changepassword = false;
                }
                else
                {
                    if(boolean_changepassword == false)
                    {
                        boolean_changepassword = true;
                    }
                }


            }
        });



        editText_confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                string_newpassword = editText_newpassword.getText().toString();
                string_confirmpassword = editText_confirmpassword.getText().toString();
                if(string_confirmpassword.length() > 0)
                {
                    button_updatepassword.setEnabled(true);
                    button_updatepassword.setBackgroundColor(Color.parseColor("#5cb85c"));
                }
                if(string_confirmpassword.length() < 1 || string_confirmpassword.length() == 0)
                {
                    button_updatepassword.setEnabled(false);
                    button_updatepassword.setBackgroundColor(Color.parseColor("#A9A9A9"));
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        button_updatepassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               if(string_newpassword.equals(string_confirmpassword))
               {
                   new WebService_UpdatePassword().execute();
               }
               else
               {

               }
            }
        });

        checkBox_showcurrentpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editText_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        checkBox_shownewpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    editText_newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editText_newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        checkBox_showconfirmpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    editText_confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editText_confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });




    }




    public class WebService_UpdatePassword extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(ChangePassword.this, "", getResources().getString(R.string.progress_loading));
            // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("userid", "");
            ht.put("username", "");

            String[] keys = { "userid","password"};
            String[] values = {string_userid,string_confirmpassword};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(ChangePassword.this).postDataToSOAPService(ht, "UpdatePassword");
                Log.i("data:","UpdatePassword:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    try {

                        Log.i("data:", "" + result);
                        /*UpdatePassword:{
                            "Status": true,
                                    "Message": "Password Updated Sucessfully"
                        }*/

                        JSONObject JSONObject_forgotpassword = new JSONObject(result);
                        if(JSONObject_forgotpassword.length() > 0)
                        {
                            Boolean Status = JSONObject_forgotpassword.getBoolean("Status");
                            if(Status == true)
                            {
                               /* rl_forgotpassword.setVisibility(View.GONE);
                                rl_passwordsent.setVisibility(View.VISIBLE);
                                Boolean MailStatus = JSONObject_forgotpassword.getBoolean("MailStatus");
                                Boolean SMSStatus = JSONObject_forgotpassword.getBoolean("SMSStatus");
                                String DataValue = JSONObject_forgotpassword.getString("DataValue");
                                if(MailStatus == true)
                                {
                                    textView_passwordsent.setText("Your Password is sent to ");
                                    textView_userid.setText(DataValue);
                                }
                                else
                                {
                                    if(SMSStatus == true)
                                    {
                                        textView_passwordsent.setText("Your Password is sent to ");
                                        textView_userid.setText("******"+DataValue.substring(0, DataValue.length() - 4));
                                    }
                                }*/
                            }
                        }
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }
                    } catch (JSONException e) {
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                        }
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

        }
    }

}
