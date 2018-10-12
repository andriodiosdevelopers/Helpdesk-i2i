package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;

public class ForgotPassword extends AppCompatActivity {
    EditText editText_username;
    Button button_submit;
    TextView textView_passwordsent;
    TextView textView_userid;
    RelativeLayout rl_forgotpassword;
    RelativeLayout rl_passwordsent;
    String string_username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window =  this.getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(getResources().getColor(android.R.color.black));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_forgot_password);

        editText_username = (EditText)findViewById(R.id.input_email);
        button_submit = (Button)findViewById(R.id.button_submit);
        button_submit.setEnabled(false);
        textView_passwordsent = (TextView)findViewById(R.id.textview_unlock_label);
        textView_userid = (TextView)findViewById(R.id.textview_userid);
        rl_forgotpassword = (RelativeLayout)findViewById(R.id.rl_forgotpassword);
        rl_passwordsent = (RelativeLayout)findViewById(R.id.rl_unlockpassword);


        editText_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                string_username = editText_username.getText().toString();
                if(string_username.length() > 0)
                {
                    button_submit.setEnabled(true);
                    button_submit.setBackgroundColor(Color.parseColor("#5cb85c"));
                }
                if(string_username.length() < 1 || string_username.length() == 0)
                {
                    button_submit.setEnabled(false);
                    button_submit.setBackgroundColor(Color.parseColor("#A9A9A9"));
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

        button_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                string_username = editText_username.getText().toString();
                new WebService_ForgotPassword().execute();

            }
        });

    }




    public class WebService_ForgotPassword extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(ForgotPassword.this, "", "Loading...\nPlease Wait");
            // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("username", "");

            String[] keys = { "username"};
            String[] values = {string_username};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(ForgotPassword.this).postDataToSOAPService(ht, "ForgotPassword_new");
                Log.i("data:","ForgotPassword:"+result);
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
                        JSONObject JSONObject_forgotpassword = new JSONObject(result);
                        if(JSONObject_forgotpassword.length() > 0)
                        {
                            Boolean Status = JSONObject_forgotpassword.getBoolean("Status");
                            if(Status == true)
                            {
                                rl_forgotpassword.setVisibility(View.GONE);
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
                                }
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
