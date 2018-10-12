package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Today_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Today_Tickets;
import helpdesk.i2i.com.ifazidesk.adapter.SimpleDividerItemDecoration;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;
import jp.wasabeef.blurry.Blurry;

public class Dashboard extends AppCompatActivity {
    RelativeLayout rl_root;
    Preferences prefs;
    String string_username;
    String string_usercompany;
    String string_companylogo;
    String string_companydetails;
    String string_locationdetails;
    int int_roleid;

    TextView textView_username;
    TextView textView_company;
    TextView textview_date;

    Spinner spinner_location;
    Spinner spinner_company;

    String string_userid = "";
    String string_locationid = "";
    String string_companyid = "";
    int int_companyid = 0;

    List<GetSet> companylist = new ArrayList<GetSet>();
    List<GetSet> locationlist = new ArrayList<GetSet>();

    private RecyclerView recyclerView_status,recyclerView_tickets;
    ImageView imageView_companylogo;
    int cday, cmonth, cyear;
    String string_date = "";
    JSONArray jsonArray_ticketdetails;
    RelativeLayout rl_new;
    RelativeLayout rl_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        rl_root = (RelativeLayout)findViewById(R.id.rl_root);
        Blurry.with(Dashboard.this)
                .radius(25)
                .sampling(2)
                .color(Color.argb(66, 255, 255, 0))
                .async()
                .onto(rl_root);

        prefs = new Preferences(Dashboard.this);
        int_roleid = prefs.getInt("RoleID");
        string_userid = ""+prefs.getInt("UserID_SP");
        string_username = prefs.getString("UserName_SP");
        string_usercompany = prefs.getString("User_Company");
        string_companydetails = prefs.getString("JSONArray_Company");
        string_locationdetails = prefs.getString("JSONArray_Location");
        string_companylogo = prefs.getString("Company_Logo");



        rl_new = (RelativeLayout) findViewById(R.id.rl_new);
        rl_user = (RelativeLayout) findViewById(R.id.rl_user);
        imageView_companylogo = (ImageView) findViewById(R.id.imageview_user);
        textView_username = (TextView) findViewById(R.id.textview_username);
        textView_company = (TextView) findViewById(R.id.textview_usercompany);
        textview_date = (TextView) findViewById(R.id.textview_date);
        spinner_company = (Spinner) findViewById(R.id.spinner_company);
        spinner_company.setPrompt("Select Company");
        spinner_location = (Spinner) findViewById(R.id.spinner_location);
        spinner_location.setPrompt("Select Location");

        recyclerView_status = (RecyclerView) findViewById(R.id.recyclerView_status);
        recyclerView_tickets = (RecyclerView) findViewById(R.id.recyclerView_tickets);

        rl_new.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent ii9i=new Intent(Dashboard.this,ScanScreen.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });

        rl_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ii9i=new Intent(Dashboard.this,ChangePassword.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });

        recyclerView_tickets.addItemDecoration(new SimpleDividerItemDecoration(Dashboard.this));

        Picasso.with(this)
                .load(string_companylogo)
                .placeholder(R.drawable.ifazidesk)
                .error(R.drawable.ifazidesk)
                .into(imageView_companylogo);

        textView_username.setText(string_username);
        textView_company.setText(string_usercompany);


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
        string_date = string_currentmonth+"/"+string_currentdate+"/"+current_year;

        textview_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Calendar c = Calendar.getInstance();
                DatePickerDialog da =  new DatePickerDialog(Dashboard.this, d,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                //Date newDate = c.getTime();
                //da.getDatePicker().setMinDate(newDate.getTime());
                da.show();
            }
        });

        try {
            companylist.clear();
            JSONArray jsonArray_location = new JSONArray(string_companydetails);
            for(int i=0;i<jsonArray_location.length();i++)
            {
                int CompanyId = jsonArray_location.getJSONObject(i).getInt("CompanyId");
                String Company = jsonArray_location.getJSONObject(i).getString("Company");
                GetSet company_item = new GetSet();
                company_item.setInt_Item_1(CompanyId);
                company_item.setString_Item_1(Company);
                companylist.add(company_item);
            }
            ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(Dashboard.this,R.layout.layout_spinner, companylist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_company.setAdapter(adapter);
            spinner_company.setSelection(0);
            //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        spinner_location.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            String string_locationname = spinner_location.getSelectedItem().toString();
                            string_locationid = ""+locationlist.get(arg2).getInt_Item_2();
                            prefs.setString("Dashboard_LocationID", string_locationid);
                            new WebService_DashboardData().execute();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );

        spinner_company.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            String string_companyname = spinner_company.getSelectedItem().toString();
                            string_companyid = ""+companylist.get(arg2).getInt_Item_1();
                            int_companyid = companylist.get(arg2).getInt_Item_1();
                            prefs.setString("Dashboard_CompanyID", string_companyid);
                        }

                        try {
                            locationlist.clear();
                            JSONArray jsonArray_location = new JSONArray(string_locationdetails);
                            for(int i=0;i<jsonArray_location.length();i++)
                            {
                                int CompanyId = jsonArray_location.getJSONObject(i).getInt("CompanyId");
                                int LocationId = jsonArray_location.getJSONObject(i).getInt("LocationId");
                                String Location = jsonArray_location.getJSONObject(i).getString("Location");
                                GetSet location_item = new GetSet();
                                location_item.setInt_Item_1(CompanyId);
                                location_item.setInt_Item_2(LocationId);
                                location_item.setString_Item_1(Location);
                                if(CompanyId == int_companyid)
                                {
                                    locationlist.add(location_item);
                                }

                            }
                            ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(Dashboard.this,R.layout.layout_spinner_location, locationlist);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_location.setAdapter(adapter);
                            spinner_location.setSelection(0);
                            //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );



    }



    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            cday = dayOfMonth;
            cmonth = monthOfYear + 1;
            cyear = year;
            String str_day = ""+cday;
            String str_month = ""+cmonth;
            if(cday < 10)
            {
                str_day = "0"+cday;
            }
            if(cmonth < 10)
            {
                str_month = "0"+cmonth;
            }
            textview_date.setText(str_day + "/" + str_month + "/" + cyear);
            string_date = str_month + "/" + str_day + "/" + cyear;
            new WebService_DashboardData().execute();
        }
    };

    public class WebService_DashboardData extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(Dashboard.this, "Dashboard Data", "Loading...\nPlease Wait");
           // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","locationid"};
            String[] values = {string_userid,string_date,string_locationid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(Dashboard.this).postDataToSOAPService(ht, "GetDashboardDetails");
                Log.i("data:","GetDashboardDetails:"+result);
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
                        JSONArray jsonarray_dashboard = new JSONArray(result);
                        if(jsonarray_dashboard.length() > 0)
                        {
//                            JSONArray jsonArray_status = jsonarray_dashboard.getJSONObject(0).getJSONArray("statusdetails");
//
//                            RecyclerViewAdapter_Dashboard_Today_Status adapter_department = new RecyclerViewAdapter_Dashboard_Today_Status
//                                    (Dashboard.this, jsonArray_status);
//                            adapter_department.notifyDataSetChanged();
//                            recyclerView_status.setAdapter(adapter_department);
//
//                            jsonArray_ticketdetails = jsonarray_dashboard.getJSONObject(0).getJSONArray("ticketdetails");
//                            RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
//                                    (Dashboard.this, jsonArray_ticketdetails,false);
//                            adapter_ticket.notifyDataSetChanged();
//                            recyclerView_tickets.setAdapter(adapter_ticket);

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


    public void LoadDashboardData(String statusid, String istotal)
    {
        try {
            JSONArray temp_jsonArray_ticketdetails = new JSONArray();
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;


            for(int i = 0;i<jsonArray_ticketdetails.length();i++)
            {
                int Statusid = jsonArray_ticketdetails.getJSONObject(i).getInt("Statusid");
                if(istotal.equals("true"))
                {
                    int ComplaintId = jsonArray_ticketdetails.getJSONObject(i).getInt("ComplaintId");
                    String TicketNo = jsonArray_ticketdetails.getJSONObject(i).getString("TicketNo");
                    String Category = jsonArray_ticketdetails.getJSONObject(i).getString("Category");
                    String Subject = jsonArray_ticketdetails.getJSONObject(i).getString("Subject");
                    String Priority = jsonArray_ticketdetails.getJSONObject(i).getString("Priority");
                    String RequestDate = jsonArray_ticketdetails.getJSONObject(i).getString("RequestDate");
                    String RequestTime = jsonArray_ticketdetails.getJSONObject(i).getString("RequestTime");
                    String Status = jsonArray_ticketdetails.getJSONObject(i).getString("Status");

                    JSONObject jobject_ticket = new JSONObject();
                    jobject_ticket.put("Statusid",Statusid);
                    jobject_ticket.put("ComplaintId",ComplaintId);
                    jobject_ticket.put("TicketNo",TicketNo);
                    jobject_ticket.put("Category",Category);
                    jobject_ticket.put("Subject",Subject);
                    jobject_ticket.put("Priority",Priority);
                    jobject_ticket.put("RequestDate",RequestDate);
                    jobject_ticket.put("RequestTime",RequestTime);
                    jobject_ticket.put("Status",Status);
                    temp_jsonArray_ticketdetails.put(jobject_ticket);
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid) {
                        int ComplaintId = jsonArray_ticketdetails.getJSONObject(i).getInt("ComplaintId");
                        String TicketNo = jsonArray_ticketdetails.getJSONObject(i).getString("TicketNo");
                        String Category = jsonArray_ticketdetails.getJSONObject(i).getString("Category");
                        String Subject = jsonArray_ticketdetails.getJSONObject(i).getString("Subject");
                        String Priority = jsonArray_ticketdetails.getJSONObject(i).getString("Priority");
                        String RequestDate = jsonArray_ticketdetails.getJSONObject(i).getString("RequestDate");
                        String RequestTime = jsonArray_ticketdetails.getJSONObject(i).getString("RequestTime");
                        String Status = jsonArray_ticketdetails.getJSONObject(i).getString("Status");

                        JSONObject jobject_ticket = new JSONObject();
                        jobject_ticket.put("Statusid", Statusid);
                        jobject_ticket.put("ComplaintId", ComplaintId);
                        jobject_ticket.put("TicketNo", TicketNo);
                        jobject_ticket.put("Category", Category);
                        jobject_ticket.put("Subject", Subject);
                        jobject_ticket.put("Priority", Priority);
                        jobject_ticket.put("RequestDate", RequestDate);
                        jobject_ticket.put("RequestTime", RequestTime);
                        jobject_ticket.put("Status", Status);
                        temp_jsonArray_ticketdetails.put(jobject_ticket);
                        isempty = true;
                    }
                }
            }
//
//            if(isempty == true) {
//                RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
//                        (Dashboard.this, temp_jsonArray_ticketdetails,false);
//                adapter_ticket.notifyDataSetChanged();
//                recyclerView_tickets.setAdapter(adapter_ticket);
//            }
//            else
//            {
//                RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
//                        (Dashboard.this, temp_jsonArray_ticketdetails,false);
//                adapter_ticket.notifyDataSetChanged();
//                recyclerView_tickets.setAdapter(adapter_ticket);
//            }

            //recyclerView_tickets.findViewHolderForAdapterPosition(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Dashboard.this, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                                startActivity(intent);
                                finish();
                                System.exit(0);
                                moveTaskToBack(true);
                            }
                        })
                .setNeutralButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Intent ii9i=new Intent(Dashboard    .this,LoginActivity.class);
                        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii9i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).show();
    }

}
