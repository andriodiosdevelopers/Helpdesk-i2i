package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Tickets;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.DashboardData;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.StatusdetailsItem;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.TicketdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.CompanydetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.getset.LocationdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDashboard extends AppCompatActivity {
    String string_Language = "";
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
    TextView textview_today_date;
    TextView textview_week;
    TextView textview_week_dates;
    TextView textview_dr_startdate;
    TextView textview_dr_enddate;

    TextView textview_month_date;
    TextView textview_month_startdate;
    TextView textview_month_enddate;

    Spinner spinner_location;
    Spinner spinner_company;
    Spinner spinner_summary;
    RelativeLayout rl_summary_daterange;
    TextView textView_summary_startdate;
    TextView textView_summary_enddate;
    TableLayout tableLayout_summary;
    TableRow tableRow_summary_item;

    String string_userid = "";
    String string_locationid = "";
    String string_companyid = "";
    String string_week_firstdate = "";
    String string_week_enddate = "";
    String string_month_startdate = "";
    String string_month_enddate = "";
    String string_summary_startdate = "";
    String string_summary_enddate = "";
    int int_companyid = 0;

    List<GetSet> summarylist = new ArrayList<GetSet>();
    List<GetSet> companylist = new ArrayList<GetSet>();
    List<GetSet> locationlist = new ArrayList<GetSet>();

    private RecyclerView recyclerView_today_status,recyclerView_today_tickets;
    private RecyclerView recyclerView_week_status,recyclerView_week_tickets;
    private RecyclerView recyclerView_month_status,recyclerView_month_tickets;
    private RecyclerView recyclerView_daterange_status,recyclerView_daterange_tickets;
    private RecyclerView recyclerView_summary_status,recyclerView_summary_tickets;
    ImageView imageView_companylogo;
    int cday, cmonth, cyear;
    String string_date = "";
    String string_daterange_temp_startdate = "";
    String string_daterange_startdate = "";
    String string_daterange_temp_enddate = "";
    String string_daterange_enddate = "";
    JSONArray jsonArray_ticketdetails = null;
    JSONArray jsonArray_ticketdetails_weekwise = null;
    JSONArray jsonArray_ticketdetails_monthwise = null;
    JSONArray jsonArray_ticketdetails_daterange = null;
    JSONArray jsonArray_ticketdetails_summarywise = null;
    RelativeLayout rl_new;
    RelativeLayout rl_user;

    RelativeLayout rl_companylist;
    RelativeLayout rl_locationlist;
    TextView textview_companyname;
    TextView textview_locationname;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FloatingActionButton fab_add;
    FloatingActionButton fab_back;
    ImageView imageview_prevweek,imageview_nextweek;


    String string_MYTICKETS = "My Tickets";
    String string_TODAY = "";
    String string_WEEK = "";
    String string_MONTH = "";
    String string_SUMMARY = "";
    String string_REPORTTYPE = "";
    String string_LAST3MONTHS = "";
    String string_LAST6MONTHS = "";
    String string_DATERANGE = "";
    String string_STARTDATE = "";
    String string_ENDDATE = "";
    Boolean isServiceEngineer;
    private int[] tabIcons = {
            R.drawable.image_dashboard_date,
            R.drawable.image_dashboard_week,
            R.drawable.image_dashboard_monthly,
            R.drawable.image_dashboard_summary
    };

    LocalBroadcastManager broadcastManager;
    ProgressDialog refreshdialog;

    final static String ARG_PARAM1 = "ListData";
    final static String ARG_PARAM2 = "SearchTitle";
    final static String ARG_PARAM3 = "ListType";
    final static String ARG_PARAM4 = "ID";

    String string_selectedcompanyname = "";
    String string_selectedcompanyid = "";

    String string_selectedlocationname = "";
    String string_selectedlocationid = "";
    boolean isTodayFocused = false;
    boolean isWeekFocused = false;
    boolean isMonthFocused = false;
    boolean isDatRangeFocused = false;
    boolean isServiceEngineerFocused = false;
    private Realm mRealm;
    RealmResults<CompanydetailsItem> realmResults_company;
    RealmResults<LocationdetailsItem> realmResults_location;

    APIInterface apiService;
    String TAG ="Dashboard";

    List<StatusdetailsItem> StatusDetailsItem_Today;
    List<StatusdetailsItem> StatusDetailsItem_Today_temp;

    List<StatusdetailsItem> StatusDetailsItem_Week;
    List<StatusdetailsItem> StatusDetailsItem_Week_temp;

    List<StatusdetailsItem> StatusDetailsItem_Month;
    List<StatusdetailsItem> StatusDetailsItem_Month_temp;

    List<StatusdetailsItem> StatusDetailsItem_DateRange;
    List<StatusdetailsItem> StatusDetailsItem_DateRange_temp;

    List<TicketdetailsItem> TicketDetails_Today;
    List<TicketdetailsItem> TicketDetails_Today_temp;

    List<TicketdetailsItem> TicketDetails_Week;
    List<TicketdetailsItem> TicketDetails_Week_temp;

    List<TicketdetailsItem> TicketDetails_Month;
    List<TicketdetailsItem> TicketDetails_Month_temp;

    List<TicketdetailsItem> TicketDetails_DateRange;
    List<TicketdetailsItem> TicketDetails_DateRange_temp;

    List<StatusdetailsItem> StatusDetailsItem_TotalData;
    List<TicketdetailsItem> TicketDetails_TotalData;
    String string_dashboard_startdate = "";
    String string_dashboard_enddate = "";

    String string_DASHBOARD_Company = "";
    String string_DASHBOARD_CompanyID = "";
    String string_DASHBOARD_Location = "";
    String string_DASHBOARD_LocationID = "";
    String string_DASHBOARD_TYPE = "";
    String string_DASHBOARD_startdate = "";
    String string_DASHBOARD_enddate = "";
    String string_DASHBOARD_startdate_temp = "";
    String string_DASHBOARD_enddate_temp = "";

    TextView textView_titile_companyname;
    TextView textView_titile_locationname;
    TextView textView_titile_timeperiod;
    ProgressDialog pDialog;
    EditText editText_search;
    String string_dashboarduser_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_newdashboard);

        apiService = APIClient.getClient().create(APIInterface.class);

        prefs = new Preferences(this);
        int_roleid = prefs.getInt("RoleID");
        string_userid = ""+prefs.getInt("UserID_SP");
        string_username = prefs.getString("UserName_SP");
        string_usercompany = prefs.getString("User_Company");
        string_companylogo = prefs.getString("Company_Logo");
        string_Language = prefs.getString("Language");
        isServiceEngineer = prefs.getBoolean("isServiceEngineer_Boolean");

        string_DASHBOARD_Company = prefs.getString("DASHBOARD_COMPANY");
        string_DASHBOARD_CompanyID = prefs.getString("DASHBOARD_COMPANYID");
        string_DASHBOARD_Location = prefs.getString("DASHBOARD_LOCATION");
        string_DASHBOARD_LocationID = prefs.getString("DASHBOARD_LOCATIONID");
        string_dashboarduser_type = prefs.getString("DASHBOARD_USERTYPE");

        string_DASHBOARD_TYPE = prefs.getString("DASHBOARD_TYPE");
        string_DASHBOARD_startdate = prefs.getString("DASHBOARD_STARTDATE");
        string_DASHBOARD_enddate = prefs.getString("DASHBOARD_ENDDATE");
        string_DASHBOARD_startdate_temp = prefs.getString("DASHBOARD_STARTDATE_TEMP");
        string_DASHBOARD_enddate_temp = prefs.getString("DASHBOARD_ENDDATE_TEMP");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_back = (FloatingActionButton) findViewById(R.id.fab_back);
        textView_titile_companyname = (TextView) findViewById(R.id.textview_companyname);
        textView_titile_locationname = (TextView) findViewById(R.id.textview_locationname);
        textView_titile_timeperiod = (TextView) findViewById(R.id.textview_date);

        recyclerView_today_status = (RecyclerView) findViewById(R.id.recyclerView_status);
        recyclerView_today_tickets = (RecyclerView) findViewById(R.id.recyclerView_tickets);
        ImageView imageview_refresh = (ImageView) findViewById(R.id.imageview_refresh);
        editText_search = (EditText)findViewById(R.id.edittext_search);
        editText_search.setVisibility(View.GONE);

        textView_titile_companyname.setText(string_DASHBOARD_Company);
        textView_titile_locationname.setText(string_DASHBOARD_Location);

        if(string_DASHBOARD_TYPE.equals("TODAY"))
        {
            textView_titile_timeperiod.setText(string_DASHBOARD_TYPE+" ("+string_DASHBOARD_startdate_temp+")");
        }
        if(string_DASHBOARD_TYPE.equals("YESTERDAY"))
        {
            textView_titile_timeperiod.setText(string_DASHBOARD_TYPE+" ("+string_DASHBOARD_startdate_temp+")");
        }
        if(string_DASHBOARD_TYPE.equals("LAST7DAYS"))
        {
                    textView_titile_timeperiod.setText("Last 7 Days"+" ("+string_DASHBOARD_startdate_temp+"-"+string_DASHBOARD_enddate_temp+")");
        }
        if(string_DASHBOARD_TYPE.equals("LAST30DAYS"))
        {
            textView_titile_timeperiod.setText("Last 30 Days"+" ("+string_DASHBOARD_startdate_temp+"-"+string_DASHBOARD_enddate_temp+")");
        }
        if(string_DASHBOARD_TYPE.equals("DATERANGE"))
        {
            textView_titile_timeperiod.setText("Custom Date Range"+" ("+string_DASHBOARD_startdate_temp+"-"+string_DASHBOARD_enddate_temp+")");
        }

        try {
            IntentFilter inF = new IntentFilter("updatetickets");
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(gcmReceiver,inF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(string_dashboarduser_type.equals("SE")) {
            LoadDashboard_ServiceEngineer();
        }
        if(string_dashboarduser_type.equals("USER")) {
            LoadDashboard();
        }


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getApplicationContext(), SelectLocation.class);
                //Intent intent = new Intent(getApplicationContext(), IssueDept.class);
                startActivity(intent);
            }
        });


        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getApplicationContext(), PreDashboard.class);
                //Intent intent = new Intent(getApplicationContext(), IssueDept.class);
                startActivity(intent);
            }
        });
    }

    private BroadcastReceiver gcmReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        LoadDashboard();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

    public void LoadDashboard()
    {

        try {
            pDialog = new ProgressDialog(NewDashboard.this);
            pDialog.setMessage(getResources().getString(R.string.progress_loading));
            pDialog.setCancelable(false);
            pDialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        Call<DashboardData> call_dashboardmonth = apiService.GetDashboardDetails(string_userid,string_DASHBOARD_startdate,string_DASHBOARD_enddate,
                string_DASHBOARD_CompanyID,string_DASHBOARD_LocationID);

        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_DASHBOARD_startdate+" EDate:"+string_DASHBOARD_enddate+
                " CompanyID:"+string_DASHBOARD_CompanyID+"LocationID:"+string_DASHBOARD_LocationID);

        Log.i("Dashboard",""+call_dashboardmonth.toString());

        call_dashboardmonth.enqueue(new Callback<DashboardData>()
        {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {

                StatusDetailsItem_TotalData = response.body().getStatusdetails();
                TicketDetails_TotalData = response.body().getTicketdetails();

                RecyclerViewAdapter_Dashboard_Status adapter_department = new RecyclerViewAdapter_Dashboard_Status
                        (NewDashboard.this, StatusDetailsItem_TotalData);
                adapter_department.notifyDataSetChanged();
                recyclerView_today_status.setAdapter(adapter_department);

                if(TicketDetails_TotalData.size() > 0)
                {
                    RecyclerViewAdapter_Dashboard_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Tickets
                            (NewDashboard.this, TicketDetails_TotalData, false);
                    adapter_ticket.notifyDataSetChanged();
                    recyclerView_today_tickets.setAdapter(adapter_ticket);
                }
                try {
                    pDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                pDialog.dismiss();
            }
        });
    }

    public void LoadDashboard_ServiceEngineer()
    {

        pDialog = new ProgressDialog(NewDashboard.this);
        pDialog.setMessage(getResources().getString(R.string.progress_loading));
        pDialog.setCancelable(false);
        pDialog.show();
        Call<DashboardData> call_dashboardmonth = apiService.GetServiceEngineerTickets_Company(string_userid,string_DASHBOARD_startdate,string_DASHBOARD_enddate,
                string_DASHBOARD_CompanyID,string_DASHBOARD_LocationID);

        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_DASHBOARD_startdate+" EDate:"+string_DASHBOARD_enddate+
                " CompanyID:"+string_DASHBOARD_CompanyID+"LocationID:"+string_DASHBOARD_LocationID);

        Log.i("Dashboard_SE",""+call_dashboardmonth.toString());

        call_dashboardmonth.enqueue(new Callback<DashboardData>()
        {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {

                StatusDetailsItem_TotalData = response.body().getStatusdetails();
                TicketDetails_TotalData = response.body().getTicketdetails();

                RecyclerViewAdapter_Dashboard_Status adapter_department = new RecyclerViewAdapter_Dashboard_Status
                        (NewDashboard.this, StatusDetailsItem_TotalData);
                adapter_department.notifyDataSetChanged();
                recyclerView_today_status.setAdapter(adapter_department);

                if(TicketDetails_TotalData.size() > 0)
                {
                    RecyclerViewAdapter_Dashboard_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Tickets
                            (NewDashboard.this, TicketDetails_TotalData, false);
                    adapter_ticket.notifyDataSetChanged();
                    recyclerView_today_tickets.setAdapter(adapter_ticket);
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                pDialog.dismiss();
            }
        });
    }

    public void LoadDashboardData(String statusid, String istotal)
    {
        try {
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;

            List<TicketdetailsItem> TicketDetails_TotalData_temp = new ArrayList<>();
            for(int i = 0;i<TicketDetails_TotalData.size();i++)
            {
                int Statusid = TicketDetails_TotalData.get(i).getStatusid();
                if(istotal.equals("true"))
                {
                    TicketDetails_TotalData_temp.add(TicketDetails_TotalData.get(i));
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid) {
                        TicketDetails_TotalData_temp.add(TicketDetails_TotalData.get(i));
                        isempty = true;
                    }
                }
            }

            if(isempty == true) {
                RecyclerViewAdapter_Dashboard_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Tickets
                        (NewDashboard.this, TicketDetails_TotalData_temp,false);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_today_tickets.setAdapter(adapter_ticket);
            }
            else
            {
                RecyclerViewAdapter_Dashboard_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Tickets
                        (NewDashboard.this, TicketDetails_TotalData_temp,false);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_today_tickets.setAdapter(adapter_ticket);
            }
            recyclerView_today_tickets.findViewHolderForAdapterPosition(0);
        } catch (Exception e) {
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
                NewDashboard.this, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(R.string.dialog_exitmsg)
                .setPositiveButton(R.string.dialog_button_yes,
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
                .setNeutralButton(R.string.dialog_button_logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        prefs.setString("RememberMe_SP", "false");
                        Intent ii9i=new Intent(NewDashboard.this,LoginActivity.class);
                        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii9i);
                    }
                })
                .setNegativeButton(R.string.dialog_button_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).show();
    }

}




