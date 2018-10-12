package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_DateRange_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_DateRange_Tickets;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Month_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Month_Tickets;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Today_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Today_Tickets;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Week_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Week_Tickets;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.DashboardData;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.StatusdetailsItem;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.TicketdetailsItem;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_Dashboard_Today;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List;
import helpdesk.i2i.com.ifazidesk.getset.CompanydetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.getset.LocationdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardNew extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard_new);
        rl_root = (RelativeLayout)findViewById(R.id.rl_root);

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("ifazidesk_db.realm")
                .schemaVersion(0)
                .build();
        mRealm = Realm.getInstance(realmConfig);

        apiService = APIClient.getClient().create(APIInterface.class);


        realmResults_company = mRealm.where(CompanydetailsItem.class).findAllAsync();
        realmResults_location = mRealm.where(LocationdetailsItem.class).findAllAsync();
        realmResults_company.load();
        realmResults_location.load();

        prefs = new Preferences(this);
        int_roleid = prefs.getInt("RoleID");
        string_userid = ""+prefs.getInt("UserID_SP");
        string_username = prefs.getString("UserName_SP");
        string_usercompany = prefs.getString("User_Company");
        //string_companydetails = prefs.getString("JSONArray_Company");
        //string_locationdetails = prefs.getString("JSONArray_Location");
        string_companylogo = prefs.getString("Company_Logo");
        string_Language = prefs.getString("Language");
        isServiceEngineer = prefs.getBoolean("isServiceEngineer_Boolean");


        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        rl_new = (RelativeLayout) findViewById(R.id.rl_new);
        rl_user = (RelativeLayout) findViewById(R.id.rl_user);

        rl_companylist = (RelativeLayout) findViewById(R.id.rl_companylist);
        rl_locationlist = (RelativeLayout) findViewById(R.id.rl_locationlist);

        textview_companyname = (TextView) findViewById(R.id.textview_companyname);
        textview_locationname = (TextView) findViewById(R.id.textview_locationname);

        imageView_companylogo = (ImageView) findViewById(R.id.imageview_user);
        textView_username = (TextView) findViewById(R.id.textview_username);
        textView_company = (TextView) findViewById(R.id.textview_usercompany);

        spinner_company = (Spinner) findViewById(R.id.spinner_selectcompany);
        spinner_location = (Spinner) findViewById(R.id.spinner_selectlocation);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        /*try {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
        } catch (Exception e) {

        }*/


        try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("spinner_selectcompany"))
                {
                    spinner_company.setPrompt(langauge);
                }
                if(Control.equals("spinner_selectlocation"))
                {
                    spinner_location.setPrompt(langauge);
                }
                if(Control.equals("textview_today"))
                {
                    string_TODAY = langauge;
                }
                if(Control.equals("textview_week"))
                {
                    string_WEEK = langauge;
                }
                if(Control.equals("textview_month"))
                {
                    string_MONTH = langauge;
                }
                if(Control.equals("textview_summary"))
                {
                    string_SUMMARY = langauge;
                }
                if(Control.equals("textview_summary"))
                {
                    string_SUMMARY = langauge;
                }
                if(Control.equals("textview_reporttype"))
                {
                    string_REPORTTYPE = langauge;
                }
                if(Control.equals("spinner_summarylast3months"))
                {
                    string_LAST3MONTHS = langauge;
                }
                if(Control.equals("spinner_summarylast6months"))
                {
                    string_LAST6MONTHS = langauge;
                }
                if(Control.equals("spinner_summarydaterange"))
                {
                    string_DATERANGE = langauge;
                }
                if(Control.equals("textview_startdate"))
                {
                    string_STARTDATE = langauge;
                }
                if(Control.equals("textview_enddate"))
                {
                    string_ENDDATE = langauge;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        rl_new.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent ii9i=new Intent(getApplicationContext(),IssueDept.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getApplicationContext(), SelectLocation.class);
                //Intent intent = new Intent(getApplicationContext(), IssueDept.class);
                startActivity(intent);
            }
        });

        rl_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ii9i=new Intent(getApplicationContext(),ChangePassword.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });


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

        string_dashboard_startdate = string_currentmonth+"/"+string_currentdate+"/"+current_year;

        SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");

        Calendar dashbaordcalendar = Calendar.getInstance();
        dashbaordcalendar.setTime(new Date());
        dashbaordcalendar.add(Calendar.DAY_OF_YEAR, -30);

        string_dashboard_enddate = dateFormat.format(dashbaordcalendar.getTime());

//        int current_year_enddate = dashbaordcalendar.get(Calendar.YEAR);
//        int current_month_enddate = dashbaordcalendar.get(Calendar.MONTH)+1;
//        int current_date_enddate = dashbaordcalendar.get(Calendar.DAY_OF_MONTH);
//        String string_currentmonth_enddate = ""+current_month;
//        String string_currentdate_enddate = ""+current_date;
//
//        if(current_month_enddate<10)
//        {
//            string_currentmonth_enddate = "0"+current_month;
//        }
//        if(current_date_enddate<10)
//        {
//            string_currentdate_enddate = "0"+current_date;
//        }

        //string_dashboard_enddate = string_currentmonth_enddate+"/"+string_currentdate_enddate+"/"+current_year_enddate;

        try {
            companylist.clear();
            GetSet allcompany_item = new GetSet();
            allcompany_item.setInt_Item_1(0);
            allcompany_item.setString_Item_1("All");
            companylist.add(allcompany_item);
            for(int i=0;i<realmResults_company.size();i++)
            {
                GetSet company_item = new GetSet();
                company_item.setInt_Item_1(realmResults_company.get(i).getCompanyId());
                company_item.setString_Item_1(realmResults_company.get(i).getCompany());
                companylist.add(company_item);
                SetCompany(realmResults_company.get(0).getCompany(),""+realmResults_company.get(0).getCompanyId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }





       /* spinner_company.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            String string_companyname = spinner_company.getSelectedItem().toString();
                            string_companyid = ""+companylist.get(arg2).getInt_Item_1();
                            string_selectedcompanyid = ""+companylist.get(arg2).getInt_Item_1();
                            int_companyid = companylist.get(arg2).getInt_Item_1();
                            prefs.setString("Dashboard_CompanyID", string_companyid);
                        }

                        try {
                            locationlist.clear();
                            for(int i=0;i<realmResults_location.size();i++)
                            {
                                int CompanyId = realmResults_location.get(i).getCompanyId();
                                if(i == 0)
                                {
                                    GetSet location_item = new GetSet();
                                    location_item.setInt_Item_1(CompanyId);
                                    location_item.setInt_Item_2(0);
                                    location_item.setString_Item_1("All");
                                    if(CompanyId == int_companyid)
                                    {
                                        locationlist.add(location_item);
                                    }
                                }
                                int LocationId = realmResults_location.get(i).getLocationId();
                                String Location = realmResults_location.get(i).getLocation();
                                GetSet location_item = new GetSet();
                                location_item.setInt_Item_1(CompanyId);
                                location_item.setInt_Item_2(LocationId);
                                location_item.setString_Item_1(Location);
                                if(CompanyId == int_companyid)
                                {
                                    locationlist.add(location_item);
                                }
                            }

                            ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(DashboardNew.this,R.layout.layout_spinner_location, locationlist);
                            adapter.setDropDownViewResource(R.layout.layout_spinner_black);
                            spinner_location.setAdapter(adapter);
                            spinner_location.setSelection(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );

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
                            string_selectedlocationid = ""+locationlist.get(arg2).getInt_Item_2();;
                            prefs.setString("Dashboard_LocationID", string_locationid);
                            try {
                                //new WebService_DashboardData_today().execute();
                                LoadDashboard_Today();
                                LoadDashboard_Week();
                                LoadDashboard_Month();
                                LoadDashboard_DateRange();
                                //new WebService_DashboardData_Week().execute();
                                //new WebService_DashboardData_Month().execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );*/

        rl_companylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                android.support.v4.app.FragmentManager manager = DashboardNew.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();

                bundle.putString(ARG_PARAM1, string_companydetails);
                bundle.putString(ARG_PARAM2, "Company");
                bundle.putString(ARG_PARAM3, "isMainCompanyList");
                bundle.putString(ARG_PARAM4, "");

                Fragment_List dialog = new Fragment_List();
                dialog.setArguments(bundle);
                dialog.show(manager, "dialog");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });

        rl_locationlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                    android.support.v4.app.FragmentManager manager = DashboardNew.this.getSupportFragmentManager();
                    Bundle bundle = new Bundle();

                    bundle.putString(ARG_PARAM1, string_locationdetails);
                    bundle.putString(ARG_PARAM2, "Location");
                    bundle.putString(ARG_PARAM3, "isMainLocationList");
                    bundle.putString(ARG_PARAM4, string_selectedcompanyid);

                    Fragment_List dialog = new Fragment_List();
                    dialog.setArguments(bundle);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });

        try {
            IntentFilter inF = new IntentFilter("updatetickets");
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(gcmReceiver,inF);
        } catch (Exception e) {
            e.printStackTrace();
        }



        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                if(position == 0)
                {
                    isTodayFocused = true;
                    isWeekFocused = false;
                    isMonthFocused = false;
                    isDatRangeFocused = false;
                    isServiceEngineerFocused = false;

                    //LoadDashboard_Today();
                    //new WebService_DashboardData_today().execute();

                }
                if(position == 1)
                {
                    isTodayFocused = false;
                    isWeekFocused = true;
                    isMonthFocused = false;
                    isDatRangeFocused = false;
                    isServiceEngineerFocused = false;
                    //LoadDashboard_Week();
                    //new WebService_DashboardData_Week().execute();
                }
                if(position == 2)
                {
                    isTodayFocused = false;
                    isWeekFocused = false;
                    isMonthFocused = true;
                    isDatRangeFocused = false;
                    isServiceEngineerFocused = false;
                    //LoadDashboard_Month();
                    //new WebService_DashboardData_Month().execute();
                }
                if(position == 3)
                {
                    isTodayFocused = false;
                    isWeekFocused = false;
                    isMonthFocused = false;
                    isDatRangeFocused = true;
                    isServiceEngineerFocused = false;
                    //LoadDashboard_DateRange();
                    //new WebService_DashboardData_DateRange().execute();
                }

                if(position == 4)
                {
                    isTodayFocused = false;
                    isWeekFocused = false;
                    isMonthFocused = false;
                    isDatRangeFocused = false;
                    isServiceEngineerFocused = true;

                    new WebService_DashboardData_Mytickets().execute();
                }
            }
        });

        isTodayFocused = true;
        //new WebService_DashboardData_today().execute();
        //LoadDashboard_Today();
        //LoadDashboard();
    }

    public void SetCompany(String Name, String ID)
    {
        textview_companyname.setText(Name);
        string_selectedcompanyname = Name;
        string_selectedcompanyid = ID;
        try {

            realmResults_location = mRealm.where(LocationdetailsItem.class).equalTo("companyId",Integer.parseInt(string_selectedcompanyid)).findAll();
            realmResults_location.load();
            outerloop:for(int i=0;i<realmResults_location.size();i++)
            {
                int CompanyId = realmResults_location.get(i).getCompanyId();
                int LocationID = realmResults_location.get(i).getLocationId();
                String LocationName = realmResults_location.get(i).getLocation();
                if(Integer.parseInt(string_selectedcompanyid) == 0)
                {
                    SetLocation("All", "0");
                    break outerloop;
                }
                else
                {

                    if(CompanyId == Integer.parseInt(string_selectedcompanyid))
                    {
                        SetLocation(LocationName, "" + LocationID);
                        break outerloop;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetLocation(String Name, String ID)
    {
        textview_locationname.setText(Name);
        string_selectedlocationname = Name;
        string_selectedlocationid = ID;
        //new SelectLocation.WebService_Building().execute();
        prefs.setString("Dashboard_LocationID", string_selectedlocationid);

//            LoadDashboard_Today();
//            LoadDashboard_Week();
//            LoadDashboard_Month();
//            LoadDashboard_DateRange();

                LoadDashboard();
//            if(isTodayFocused == true)
//            {
//                //new WebService_DashboardData_today().execute();
//                LoadDashboard_Today();
//            }
//            if(isWeekFocused == true)
//            {
//                //new WebService_DashboardData_Week().execute();
//                LoadDashboard_Week();
//            }
//            if(isMonthFocused == true)
//            {
//                LoadDashboard_Month();
//                //new WebService_DashboardData_Month().execute();
//            }
//            if(isDatRangeFocused == true)
//            {
//                LoadDashboard_DateRange();
//                //new WebService_DashboardData_DateRange().execute();
//            }
//            if(isServiceEngineer == true)
//            {
//                //new WebService_DashboardData_Mytickets().execute();
//            }
//
//            //new WebService_DashboardData_DateRange().execute();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }





    DatePickerDialog.OnDateSetListener date_daterange_startdate = new DatePickerDialog.OnDateSetListener() {

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
            textview_dr_startdate.setText(str_day + "/" + str_month + "/" + cyear);
            string_daterange_startdate = str_month + "/" + str_day + "/" + cyear;

            Calendar c = Calendar.getInstance();
            DatePickerDialog da =  new DatePickerDialog(DashboardNew.this, date_daterange_enddate,
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                    .get(Calendar.DAY_OF_MONTH));
            da.getDatePicker().setMaxDate(System.currentTimeMillis());
            da.show();
        }
    };


    DatePickerDialog.OnDateSetListener date_daterange_enddate = new DatePickerDialog.OnDateSetListener() {

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
            textview_dr_enddate.setText(str_day + "/" + str_month + "/" + cyear);
            string_daterange_enddate = str_month + "/" + str_day + "/" + cyear;

            new WebService_DashboardData_DateRange().execute();
        }
    };

    private void setupTabIcons() {
        if(isServiceEngineer == true)
        {

            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);
            tabLayout.getTabAt(4).setIcon(R.drawable.image_dashboard_mytickets);
        }
        else
        {
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        }

    }




    public class WebService_DashboardData_DateRange extends AsyncTask<String, String, String> {
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

            string_locationid = string_selectedlocationid;
            string_companyid = string_selectedcompanyid;
            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","enddate","locationid","companyid"};
            String[] values = {string_userid,string_daterange_startdate,string_daterange_enddate,string_locationid,string_companyid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent:DateRange",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                //result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_New");
                result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_Company");
                Log.i("data:DateRange","GetDashboardDetails_Company:"+result);
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
//                            recyclerView_daterange_status.setAdapter(null);
//                            recyclerView_daterange_tickets.setAdapter(null);
//
//                            RecyclerViewAdapter_Dashboard_DateRange_Status adapter_department = new RecyclerViewAdapter_Dashboard_DateRange_Status
//                                    (DashboardNew.this, jsonArray_status);
//                            adapter_department.notifyDataSetChanged();
//                            recyclerView_daterange_status.setAdapter(adapter_department);
//
//                            jsonArray_ticketdetails_daterange = jsonarray_dashboard.getJSONObject(0).getJSONArray("ticketdetails");
//                            RecyclerViewAdapter_Dashboard_DateRange_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_DateRange_Tickets
//                                    (DashboardNew.this, jsonArray_ticketdetails_daterange);
//                            adapter_ticket.notifyDataSetChanged();
//                            recyclerView_daterange_tickets.setAdapter(adapter_ticket);

                        }
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }*/
                    } catch (JSONException e) {
                       /* if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                        }*/
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

            }

        }
    }


    public void LoadDashboardData_Today(String statusid, String istotal)
    {
        try {
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;
            TicketDetails_Today_temp.clear();
            for(int i = 0;i<TicketDetails_Today.size();i++)
            {
                int Statusid = TicketDetails_Today.get(i).getStatusid();
                if(istotal.equals("true"))
                {
                    TicketDetails_Today_temp.add(TicketDetails_Today.get(i));
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid) {
                        TicketDetails_Today_temp.add(TicketDetails_Today.get(i));
                        isempty = true;
                    }
                }
            }

            if(isempty == true) {
                RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                        (DashboardNew.this, TicketDetails_Today_temp,false);
                adapter_ticket.notifyDataSetChanged();
               recyclerView_today_tickets.setAdapter(adapter_ticket);
            }
            else
            {
               RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                       (DashboardNew.this, TicketDetails_Today_temp,false);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_today_tickets.setAdapter(adapter_ticket);
            }

            recyclerView_today_tickets.findViewHolderForAdapterPosition(0);

        } catch (Exception e) {
            e.printStackTrace();
        }/*try {
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
                    String ColorCode = jsonArray_ticketdetails.getJSONObject(i).getString("ColorCode");
                    String location = jsonArray_ticketdetails.getJSONObject(i).getString("location");
                    Boolean IsClosed = jsonArray_ticketdetails.getJSONObject(i).getBoolean("IsClosed");
                    int StatusCount = jsonArray_ticketdetails.getJSONObject(i).getInt("StatusCount");


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
                    jobject_ticket.put("IsClosed",IsClosed);
                    jobject_ticket.put("StatusCount",StatusCount);
                    jobject_ticket.put("ColorCode",ColorCode);
                    jobject_ticket.put("location",location);
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
                        String ColorCode = jsonArray_ticketdetails.getJSONObject(i).getString("ColorCode");
                        String location = jsonArray_ticketdetails.getJSONObject(i).getString("location");
                        Boolean IsClosed = jsonArray_ticketdetails.getJSONObject(i).getBoolean("IsClosed");
                        int StatusCount = jsonArray_ticketdetails.getJSONObject(i).getInt("StatusCount");


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
                        jobject_ticket.put("IsClosed",IsClosed);
                        jobject_ticket.put("StatusCount",StatusCount);
                        jobject_ticket.put("ColorCode",ColorCode);
                        jobject_ticket.put("location",location);
                        temp_jsonArray_ticketdetails.put(jobject_ticket);
                        isempty = true;
                    }
                }
            }

            if(isempty == true) {
                RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                        (DashboardNew.this, temp_jsonArray_ticketdetails,false);
                adapter_ticket.notifyDataSetChanged();
               recyclerView_today_tickets.setAdapter(adapter_ticket);
            }
            else
            {
               RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                       (DashboardNew.this, temp_jsonArray_ticketdetails,false);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_today_tickets.setAdapter(adapter_ticket);
            }

            recyclerView_today_tickets.findViewHolderForAdapterPosition(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }





    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        if(isServiceEngineer == true)
//        {
//            adapter.addFragment(new Fragment_Date(), string_TODAY);
//            adapter.addFragment(new Fragment_Week(), string_WEEK);
//            adapter.addFragment(new Fragment_Month(), string_MONTH);
//            adapter.addFragment(new Fragment_DateRange(), string_SUMMARY);
//            adapter.addFragment(new Fragment_Mytickets(), string_MYTICKETS);
//        }
//        else
//        {
//            adapter.addFragment(new Fragment_Date(), string_TODAY);
//            adapter.addFragment(new Fragment_Week(), string_WEEK);
//            adapter.addFragment(new Fragment_Month(), string_MONTH);
//            adapter.addFragment(new Fragment_DateRange(), string_SUMMARY);
//        }

        if(isServiceEngineer == true)
        {
            adapter.addFragment(new Fragment_Dashboard_Today().newInstance((Serializable) TicketDetails_TotalData),"Today");
            //adapter.addFragment(new Fragment_Dashboard_Today(), string_TODAY);
        }
        else
        {
            adapter.addFragment(new Fragment_Dashboard_Today().newInstance((Serializable) TicketDetails_TotalData),"Today");
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(null);
            //fragment.setRetainInstance(true);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public class Fragment_Mytickets extends Fragment{

        public Fragment_Mytickets() {
            // Required empty public constructor
        }

//        public Fragment_Mytickets newInstance() {
//            Fragment_Mytickets fragment = new Fragment_Mytickets();
//            return fragment;
//        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_dashboard_mytickets, container, false);



            textview_today_date = (TextView) rootView.findViewById(R.id.textview_date);
            textview_today_date.setText(string_TODAY);

            //spinner_location.setSelection(0);
            recyclerView_today_status = (RecyclerView) rootView.findViewById(R.id.recyclerView_status);
            recyclerView_today_tickets = (RecyclerView) rootView.findViewById(R.id.recyclerView_tickets);
            ImageView imageview_refresh = (ImageView) rootView.findViewById(R.id.imageview_refresh);
            textview_today_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog da =  new DatePickerDialog(getActivity(), date_today,
                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH));
                    //Date newDate = c.getTime();
                    //da.getDatePicker().setMinDate(newDate.getTime());
                    da.show();
                }
            });

            imageview_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    refreshdialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
                    refreshdialog.setCancelable(false);
                    refreshdialog.show();
                    //new WebService_DashboardData_today().execute();
                    //new WebService_DashboardData_Week().execute();
                    //new WebService_DashboardData_Month().execute();
                    new WebService_DashboardData_Mytickets().execute();
                    //dialog.dismiss();
                }
            });

            new WebService_DashboardData_Mytickets().execute();

            return rootView;
        }

        DatePickerDialog.OnDateSetListener date_today = new DatePickerDialog.OnDateSetListener() {

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
                textview_today_date.setText(str_day + "/" + str_month + "/" + cyear);
                string_date = str_month + "/" + str_day + "/" + cyear;
                new WebService_DashboardData_Mytickets().execute();
            }
        };


    }

    public class WebService_DashboardData_Mytickets extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","enddate","locationid"};
            String[] values = {string_userid,string_date,string_date,string_locationid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent:MyTickets",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetServiceEngineerTickets");
                Log.i("data:MyTickets","GetServiceEngineerTickets:"+result);
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
                            JSONArray jsonArray_status = jsonarray_dashboard.getJSONObject(0).getJSONArray("statusdetails");
//
//                            RecyclerViewAdapter_Dashboard_Today_Status adapter_department = new RecyclerViewAdapter_Dashboard_Today_Status
//                                    (DashboardNew.this, jsonArray_status);
//                            adapter_department.notifyDataSetChanged();
//                            recyclerView_today_status.setAdapter(adapter_department);
//
//                            jsonArray_ticketdetails = jsonarray_dashboard.getJSONObject(0).getJSONArray("ticketdetails");
//                            RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
//                            (DashboardNew.this, jsonArray_ticketdetails,true);
//                            adapter_ticket.notifyDataSetChanged();
//                            recyclerView_today_tickets.setAdapter(adapter_ticket);

                        }
                        if(refreshdialog.isShowing())
                        {
                            refreshdialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

        }
    }

    public class Fragment_Date extends Fragment
    {

        public Fragment_Date() {
            // Required empty public constructor
        }

        public Fragment_Date newInstance() {
            Fragment_Date fragment = new Fragment_Date();
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_dashboard_today, container, false);



            textview_today_date = (TextView) rootView.findViewById(R.id.textview_date);
            textview_today_date.setText(string_TODAY);

            //spinner_location.setSelection(0);
            recyclerView_today_status = (RecyclerView) rootView.findViewById(R.id.recyclerView_status);
            recyclerView_today_tickets = (RecyclerView) rootView.findViewById(R.id.recyclerView_tickets);
            ImageView imageview_refresh = (ImageView) rootView.findViewById(R.id.imageview_refresh);
            textview_today_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog da =  new DatePickerDialog(getActivity(), date_today,
                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH));
                    //Date newDate = c.getTime();
                    //da.getDatePicker().setMinDate(newDate.getTime());
                    da.show();
                }
            });

            imageview_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    refreshdialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
                    refreshdialog.setCancelable(false);
                    refreshdialog.show();
                    //new WebService_DashboardData_today().execute();
                    //dialog.dismiss();
                }
            });

            //new WebService_DashboardData_today().execute();
            LoadDashboard_Today();
            return rootView;
        }

        DatePickerDialog.OnDateSetListener date_today = new DatePickerDialog.OnDateSetListener() {

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
                textview_today_date.setText(str_day + "/" + str_month + "/" + cyear);
                string_date = str_month + "/" + str_day + "/" + cyear;
                new WebService_DashboardData_today().execute();
            }
        };


    }

    public class WebService_DashboardData_today extends AsyncTask<String, String, String> {
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

            string_locationid = string_selectedlocationid;
            string_companyid = string_selectedcompanyid;
            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","enddate","locationid","companyid"};
            String[] values = {string_userid,string_date,string_date,string_locationid,string_companyid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent:Today",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                //result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_New");
                result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_Company");
                Log.i("data:Today","GetDashboardDetails_Company:"+result);
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
                            JSONArray jsonArray_status = jsonarray_dashboard.getJSONObject(0).getJSONArray("statusdetails");

//                            RecyclerViewAdapter_Dashboard_Today_Status adapter_department = new RecyclerViewAdapter_Dashboard_Today_Status
//                                    (DashboardNew.this, jsonArray_status);
//                            adapter_department.notifyDataSetChanged();
//                            recyclerView_today_status.setAdapter(adapter_department);
//
//                            jsonArray_ticketdetails = jsonarray_dashboard.getJSONObject(0).getJSONArray("ticketdetails");
//                            RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
//                                    (DashboardNew.this, jsonArray_ticketdetails,false);
//                            adapter_ticket.notifyDataSetChanged();
//                            recyclerView_today_tickets.setAdapter(adapter_ticket);

                        }
                        if(refreshdialog.isShowing())
                        {
                            refreshdialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

        }
    }

    public class Fragment_Week extends Fragment{
        Calendar calendar_week;
        int current_week_weekno = 0;
        int current_week_year = 0;
        int current_week_month = 0;
        int current_week_date = 0;
        String string_week_currentmonth = "";
        String string_week_currentdate = "";
        String temp_week_firstdate = "";
        String temp_week_lastdate = "";
        int int_prevweekno = 0;
        int int_nextweekno = 0;

        public Fragment_Week() {
            // Required empty public constructor
        }

//        public Fragment_Week newInstance() {
//            Fragment_Week fragment = new Fragment_Week();
//            return fragment;
//        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_dashboard_week, container, false);

            textview_week = (TextView) rootView.findViewById(R.id.textview_week);
            textview_week_dates = (TextView) rootView.findViewById(R.id.textview_weekdates);
            recyclerView_week_status = (RecyclerView) rootView.findViewById(R.id.recyclerView_status);
            recyclerView_week_tickets = (RecyclerView) rootView.findViewById(R.id.recyclerView_tickets);
            imageview_prevweek = (ImageView) rootView.findViewById(R.id.imageview_prevweek);
            imageview_nextweek = (ImageView) rootView.findViewById(R.id.imageview_nextweek);

            //spinner_location.setSelection(1);
            calendar_week = Calendar.getInstance();
            current_week_year = calendar_week.get(Calendar.YEAR);
            current_week_month = calendar_week.get(Calendar.MONTH)+1;
            current_week_date = calendar_week.get(Calendar.DAY_OF_MONTH);

            current_week_weekno = calendar_week.get(Calendar.WEEK_OF_YEAR);
            textview_week.setText(string_WEEK+" "+current_week_weekno);
            string_week_currentmonth = ""+current_week_month;
            string_week_currentdate = ""+current_week_date;

            Calendar c1 = Calendar.getInstance();

            //first day of week
            c1.set(Calendar.DAY_OF_WEEK, 1);

            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH)+1;
            int day1 = c1.get(Calendar.DAY_OF_MONTH);
            string_week_currentmonth = ""+month1;
            string_week_currentdate = ""+day1;
            if(month1<10)
            {
                string_week_currentmonth = "0"+month1;
            }
            if(day1<10)
            {
                string_week_currentdate = "0"+day1;
            }
            string_week_firstdate = string_week_currentmonth + "/" + string_week_currentdate + "/" + year1;
            temp_week_firstdate = string_week_currentdate + "/" + string_week_currentmonth + "/" + year1;


            //last day of week
            c1.set(Calendar.DAY_OF_WEEK, 7);

            int year7 = c1.get(Calendar.YEAR);
            int month7 = c1.get(Calendar.MONTH)+1;
            int day7 = c1.get(Calendar.DAY_OF_MONTH);
            string_week_currentmonth = ""+month7;
            string_week_currentdate = ""+day7;
            if(month7<10)
            {
                string_week_currentmonth = "0"+month7;
            }
            if(day7<10)
            {
                string_week_currentdate = "0"+day7;
            }

            string_week_enddate = string_week_currentmonth + "/" + string_week_currentdate + "/" + year7;
            temp_week_lastdate = string_week_currentdate + "/" + string_week_currentmonth + "/" + year7;

            textview_week_dates.setText(temp_week_firstdate+" - "+temp_week_lastdate);

            string_daterange_temp_startdate = temp_week_firstdate;
            string_daterange_temp_enddate = temp_week_lastdate;

            Log.i("WEEK","StartDate:"+string_week_firstdate+" EndDate:"+string_week_enddate);


            imageview_prevweek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    int_prevweekno = int_prevweekno - 1;
                    current_week_weekno = current_week_weekno - 1;
                    textview_week.setText(string_WEEK+" "+current_week_weekno);
                    Calendar c = Calendar.getInstance();
                    // Set the calendar to monday of the current week
                    c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    c.add(Calendar.DATE, int_prevweekno * 7);
                    // Print dates of the current week starting on Monday
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                    ArrayList<String> listDate = new ArrayList<String>();
                    ArrayList<String> listDate_format = new ArrayList<String>();

                    for (int i = 0; i < 7; i++)
                    {
                        listDate.add(df.format(c.getTime()));
                        listDate_format.add(df2.format(c.getTime()));
                        c.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    temp_week_firstdate =listDate.get(0);
                    string_week_firstdate =listDate_format.get(0);

                    temp_week_lastdate = listDate.get(6);
                    string_week_enddate = listDate_format.get(6);

                    textview_week_dates.setText(temp_week_firstdate+" - "+temp_week_lastdate);


                    Log.i("WEEK","StartDate:"+string_week_firstdate+" EndDate:"+string_week_enddate);

                    //new WebService_DashboardData_Week().execute();
                    LoadDashboard_Week();

                }
            });

            imageview_nextweek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    current_week_weekno = current_week_weekno + 1;
                    textview_week.setText(string_WEEK+" "+current_week_weekno);

                    int_nextweekno = int_nextweekno + 1;
                    Calendar c = Calendar.getInstance();
                    // Set the calendar to monday of the current week
                    c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    c.add(Calendar.DATE, int_nextweekno * 7);
                    // Print dates of the current week starting on Monday
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                    ArrayList<String> listDate = new ArrayList<String>();
                    ArrayList<String> listDate_format = new ArrayList<String>();

                    for (int i = 0; i < 7; i++)
                    {
                        listDate.add(df.format(c.getTime()));
                        listDate_format.add(df2.format(c.getTime()));
                        c.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    temp_week_firstdate =listDate.get(0);
                    string_week_firstdate =listDate_format.get(0);

                    temp_week_lastdate = listDate.get(6);
                    string_week_enddate = listDate_format.get(6);

                    Log.i("WEEK","StartDate:"+string_week_firstdate+" EndDate:"+string_week_enddate);
                    textview_week_dates.setText(temp_week_firstdate+" - "+temp_week_lastdate);
                    //new WebService_DashboardData_Week().execute();
                    LoadDashboard_Week();
                }
            });

            //new WebService_DashboardData_Week().execute();
            //LoadDashboard_Week();
            return rootView;
        }

    }

    public class Fragment_Month extends Fragment{

        public Fragment_Month() {
            // Required empty public constructor
        }

//        public Fragment_Month newInstance() {
//            Fragment_Month fragment = new Fragment_Month();
//            return fragment;
//        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_dashboard_month, container, false);

            textview_month_date = (TextView) rootView.findViewById(R.id.textview_month);

            recyclerView_month_status = (RecyclerView) rootView.findViewById(R.id.recyclerView_status);
            recyclerView_month_tickets = (RecyclerView) rootView.findViewById(R.id.recyclerView_tickets);

            //spinner_location.setSelection(1);
            Calendar calendar_startdate = Calendar.getInstance();
            calendar_startdate.set(Calendar.DAY_OF_MONTH, 1);
            int cm_year1 = calendar_startdate.get(Calendar.YEAR);
            int cm_month1 = calendar_startdate.get(Calendar.MONTH)+1;
            int cm_day1 = calendar_startdate.get(Calendar.DAY_OF_MONTH);
            String cm_string_month = ""+cm_month1;
            String cm_string_day = ""+cm_day1;

            if(cm_month1<10)
            {
                cm_string_month = "0"+cm_month1;
            }
            if(cm_day1<10)
            {
                cm_string_day = "0"+cm_day1;
            }

            string_month_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;


            Calendar calendar_enddate = Calendar.getInstance();
            int em_year1 = calendar_enddate.get(Calendar.YEAR);
            int em_month1 = calendar_enddate.get(Calendar.MONTH)+1;
            int em_day1 = calendar_enddate.get(Calendar.DAY_OF_MONTH);
            String em_string_month = ""+em_month1;
            String em_string_day = ""+em_day1;

            if(em_month1<10)
            {
                em_string_month = "0"+em_month1;
            }
            if(em_day1<10)
            {
                em_string_day = "0"+em_day1;
            }

            string_month_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;

            calendar_enddate = Calendar.getInstance();
            String month = calendar_enddate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            textview_month_date.setText(month+" "+em_year1);
            //new WebService_DashboardData_Month().execute();
            return rootView;
        }

    }

    public class Fragment_DateRange extends Fragment{

        public Fragment_DateRange() {
            // Required empty public constructor
        }

//        public Fragment_DateRange newInstance() {
//            Fragment_DateRange fragment = new Fragment_DateRange();
//            return fragment;
//        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_dashboard_daterange, container, false);
            TextView textview_startdate_label = (TextView) rootView.findViewById(R.id.textview_startdate_label);
            TextView textview_enddate_label = (TextView) rootView.findViewById(R.id.textview_enddate_label);

            textview_dr_startdate = (TextView) rootView.findViewById(R.id.textview_dr_startdate);
            textview_dr_enddate = (TextView) rootView.findViewById(R.id.textview_dr_enddate);
            recyclerView_daterange_status = (RecyclerView) rootView.findViewById(R.id.recyclerView_status);
            recyclerView_daterange_tickets = (RecyclerView) rootView.findViewById(R.id.recyclerView_tickets);
            string_daterange_startdate = string_week_firstdate;
            string_daterange_enddate = string_week_enddate;
            textview_dr_startdate.setText(string_daterange_temp_startdate);
            textview_dr_enddate.setText(string_daterange_temp_enddate);
            spinner_summary = (Spinner)rootView.findViewById(R.id.spinner_summary);

            //spinner_location.setSelection(1);
            textview_startdate_label.setText(string_STARTDATE);
            textview_enddate_label.setText(string_ENDDATE);
            spinner_summary.setPrompt(string_REPORTTYPE);
            try {
                summarylist.clear();
                GetSet summary_item_1 = new GetSet();
                summary_item_1.setInt_Item_1(0);
                summary_item_1.setString_Item_1(string_LAST3MONTHS);
                summarylist.add(summary_item_1);

                GetSet summary_item_2 = new GetSet();
                summary_item_2.setInt_Item_1(1);
                summary_item_2.setString_Item_1(string_LAST6MONTHS);
                summarylist.add(summary_item_2);

                GetSet summary_item_3 = new GetSet();
                summary_item_3.setInt_Item_1(2);
                summary_item_3.setString_Item_1(string_DATERANGE);
                summarylist.add(summary_item_3);

                ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(DashboardNew.this,R.layout.layout_spinner, summarylist);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_summary.setAdapter(adapter);
                spinner_summary.setSelection(0);
                //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
            } catch (Exception e) {
                e.printStackTrace();
            }

            spinner_summary.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int arg2, long arg3)
                        {
                            if(arg2 != -1 | arg2 > -1)
                            {
                                int int_summaryid = summarylist.get(arg2).getInt_Item_1();

                                if(int_summaryid == 0)
                                {
                                    Calendar calendar_startdate = Calendar.getInstance();
                                    calendar_startdate.set(Calendar.DAY_OF_MONTH, 1);
                                    int cm_year1 = calendar_startdate.get(Calendar.YEAR);
                                    int cm_month1 = calendar_startdate.get(Calendar.MONTH)+1;
                                    if(cm_month1 > 3)
                                    {
                                        cm_month1 = cm_month1 - 3;
                                    }
                                    else
                                    {
                                        cm_month1 = 1;
                                    }
                                    int cm_day1 = calendar_startdate.get(Calendar.DAY_OF_MONTH);
                                    String cm_string_month = ""+cm_month1;
                                    String cm_string_day = ""+cm_day1;

                                    if(cm_month1<10)
                                    {
                                        cm_string_month = "0"+cm_month1;
                                    }
                                    if(cm_day1<10)
                                    {
                                        cm_string_day = "0"+cm_day1;
                                    }

                                    string_summary_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;
                                    string_daterange_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;
                                    textview_dr_startdate.setText(cm_string_day+"/"+cm_string_month+"/"+cm_year1);

                                    Calendar calendar_enddate = Calendar.getInstance();
                                    int em_year1 = calendar_enddate.get(Calendar.YEAR);
                                    int em_month1 = calendar_enddate.get(Calendar.MONTH)+1;
                                    int em_day1 = calendar_enddate.get(Calendar.DAY_OF_MONTH);
                                    String em_string_month = ""+em_month1;
                                    String em_string_day = ""+em_day1;

                                    if(em_month1<10)
                                    {
                                        em_string_month = "0"+em_month1;
                                    }
                                    if(em_day1<10)
                                    {
                                        em_string_day = "0"+em_day1;
                                    }

                                    string_summary_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;
                                    string_daterange_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;
                                    textview_dr_enddate.setText(em_string_day+"/"+em_string_month+"/"+em_year1);

                                    textview_dr_startdate.setEnabled(false);
                                    textview_dr_enddate.setEnabled(false);
                                    new WebService_DashboardData_DateRange().execute();
                                }
                                if(int_summaryid == 1)
                                {
                                    Calendar calendar_startdate = Calendar.getInstance();
                                    calendar_startdate.set(Calendar.DAY_OF_MONTH, 1);
                                    int cm_year1 = calendar_startdate.get(Calendar.YEAR);
                                    int cm_month1 = calendar_startdate.get(Calendar.MONTH)+1;
                                    if(cm_month1 > 6)
                                    {
                                        cm_month1 = cm_month1 - 6;
                                    }
                                    else
                                    {
                                        cm_month1 = 1;
                                    }
                                    int cm_day1 = calendar_startdate.get(Calendar.DAY_OF_MONTH);
                                    String cm_string_month = ""+cm_month1;
                                    String cm_string_day = ""+cm_day1;

                                    if(cm_month1<10)
                                    {
                                        cm_string_month = "0"+cm_month1;
                                    }
                                    if(cm_day1<10)
                                    {
                                        cm_string_day = "0"+cm_day1;
                                    }


                                    string_summary_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;
                                    string_daterange_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;
                                    textview_dr_startdate.setText(cm_string_day+"/"+cm_string_month+"/"+cm_year1);

                                    Calendar calendar_enddate = Calendar.getInstance();
                                    int em_year1 = calendar_enddate.get(Calendar.YEAR);
                                    int em_month1 = calendar_enddate.get(Calendar.MONTH)+1;
                                    int em_day1 = calendar_enddate.get(Calendar.DAY_OF_MONTH);
                                    String em_string_month = ""+em_month1;
                                    String em_string_day = ""+em_day1;

                                    if(em_month1<10)
                                    {
                                        em_string_month = "0"+em_month1;
                                    }
                                    if(em_day1<10)
                                    {
                                        em_string_day = "0"+em_day1;
                                    }

                                    string_summary_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;
                                    string_daterange_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;
                                    textview_dr_enddate.setText(em_string_day+"/"+em_string_month+"/"+em_year1);

                                    textview_dr_startdate.setEnabled(false);
                                    textview_dr_enddate.setEnabled(false);

                                    new WebService_DashboardData_DateRange().execute();
                                }
                                if(int_summaryid == 2)
                                {
                                    textview_dr_startdate.setEnabled(true);
                                    textview_dr_enddate.setEnabled(true);

                                    Calendar c = Calendar.getInstance();
                                    DatePickerDialog da =  new DatePickerDialog(DashboardNew.this, date_daterange_startdate,
                                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                                            .get(Calendar.DAY_OF_MONTH));
                                    Date newDate = c.getTime();
                                    da.getDatePicker().setMaxDate(newDate.getTime());
                                    da.show();
                                }
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    }
            );

            textview_dr_startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog da =  new DatePickerDialog(DashboardNew.this, date_daterange_startdate,
                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH));
                    //Date newDate = c.getTime();
                    //da.getDatePicker().setMinDate(newDate.getTime());
                    da.show();
                }
            });

            textview_dr_enddate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Calendar c = Calendar.getInstance();
                                DatePickerDialog da =  new DatePickerDialog(DashboardNew.this, date_daterange_enddate,
                                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                                        .get(Calendar.DAY_OF_MONTH));
                                da.getDatePicker().setMaxDate(System.currentTimeMillis());
                                da.show();
                            }
                        });


            //new WebService_DashboardData_DateRange().execute();

            return rootView;
        }

    }

    public class Fragment_Summary extends Fragment{

        public Fragment_Summary() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_dashboard_summary, container, false);
            rl_summary_daterange = (RelativeLayout)rootView.findViewById(R.id.rl_dateselection);
            textView_summary_startdate = (TextView)rootView.findViewById(R.id.textview_startdate);
            textView_summary_startdate.setEnabled(false);
            textView_summary_enddate = (TextView)rootView.findViewById(R.id.textview_enddate);
            textView_summary_enddate.setEnabled(false);
            tableLayout_summary = (TableLayout)rootView.findViewById(R.id.table_summary);
            spinner_summary = (Spinner)rootView.findViewById(R.id.spinner_summary);
            spinner_summary.setPrompt("Select Report Type");
            try {
                    summarylist.clear();
                    GetSet summary_item_1 = new GetSet();
                    summary_item_1.setInt_Item_1(0);
                    summary_item_1.setString_Item_1("Last 3 Months");
                    summarylist.add(summary_item_1);

                    GetSet summary_item_2 = new GetSet();
                    summary_item_2.setInt_Item_1(1);
                    summary_item_2.setString_Item_1("Last 6 Months");
                    summarylist.add(summary_item_2);

                    GetSet summary_item_3 = new GetSet();
                    summary_item_3.setInt_Item_1(2);
                    summary_item_3.setString_Item_1("Date Range");
                    summarylist.add(summary_item_3);

                ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(DashboardNew.this,R.layout.layout_spinner, summarylist);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_summary.setAdapter(adapter);
                spinner_summary.setSelection(0);
                //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
            } catch (Exception e) {
                e.printStackTrace();
            }

            spinner_summary.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int arg2, long arg3)
                        {
                            if(arg2 != -1 | arg2 > -1)
                            {
                                int int_summaryid = summarylist.get(arg2).getInt_Item_1();

                                if(int_summaryid == 0)
                                {
                                    Calendar calendar_startdate = Calendar.getInstance();
                                    calendar_startdate.set(Calendar.DAY_OF_MONTH, 1);
                                    int cm_year1 = calendar_startdate.get(Calendar.YEAR);
                                    int cm_month1 = calendar_startdate.get(Calendar.MONTH)+1;
                                        if(cm_month1 > 3)
                                        {
                                            cm_month1 = cm_month1 - 3;
                                        }
                                        else
                                        {
                                            cm_month1 = 1;
                                        }
                                    int cm_day1 = calendar_startdate.get(Calendar.DAY_OF_MONTH);
                                    String cm_string_month = ""+cm_month1;
                                    String cm_string_day = ""+cm_day1;

                                    if(cm_month1<10)
                                    {
                                        cm_string_month = "0"+cm_month1;
                                    }
                                    if(cm_day1<10)
                                    {
                                        cm_string_day = "0"+cm_day1;
                                    }

                                    string_summary_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;
                                    textView_summary_startdate.setText(cm_string_day+"/"+cm_string_month+"/"+cm_year1);

                                    Calendar calendar_enddate = Calendar.getInstance();
                                    int em_year1 = calendar_enddate.get(Calendar.YEAR);
                                    int em_month1 = calendar_enddate.get(Calendar.MONTH)+1;
                                    int em_day1 = calendar_enddate.get(Calendar.DAY_OF_MONTH);
                                    String em_string_month = ""+em_month1;
                                    String em_string_day = ""+em_day1;

                                    if(em_month1<10)
                                    {
                                        em_string_month = "0"+em_month1;
                                    }
                                    if(em_day1<10)
                                    {
                                        em_string_day = "0"+em_day1;
                                    }

                                    string_summary_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;
                                    textView_summary_enddate.setText(em_string_day+"/"+em_string_month+"/"+em_year1);

                                    //new WebService_DashboardData_Summary().execute();
                                    LoadDashboard_DateRange();
                                }
                                if(int_summaryid == 1)
                                {
                                    Calendar calendar_startdate = Calendar.getInstance();
                                    calendar_startdate.set(Calendar.DAY_OF_MONTH, 1);
                                    int cm_year1 = calendar_startdate.get(Calendar.YEAR);
                                    int cm_month1 = calendar_startdate.get(Calendar.MONTH)+1;
                                    if(cm_month1 > 6)
                                    {
                                        cm_month1 = cm_month1 - 6;
                                    }
                                    else
                                    {
                                        cm_month1 = 1;
                                    }
                                    int cm_day1 = calendar_startdate.get(Calendar.DAY_OF_MONTH);
                                    String cm_string_month = ""+cm_month1;
                                    String cm_string_day = ""+cm_day1;

                                    if(cm_month1<10)
                                    {
                                        cm_string_month = "0"+cm_month1;
                                    }
                                    if(cm_day1<10)
                                    {
                                        cm_string_day = "0"+cm_day1;
                                    }

                                    string_summary_startdate = cm_string_month + "/" + cm_string_day + "/" + cm_year1;
                                    textView_summary_startdate.setText(cm_string_day+"/"+cm_string_month+"/"+cm_year1);

                                    Calendar calendar_enddate = Calendar.getInstance();
                                    int em_year1 = calendar_enddate.get(Calendar.YEAR);
                                    int em_month1 = calendar_enddate.get(Calendar.MONTH)+1;
                                    int em_day1 = calendar_enddate.get(Calendar.DAY_OF_MONTH);
                                    String em_string_month = ""+em_month1;
                                    String em_string_day = ""+em_day1;

                                    if(em_month1<10)
                                    {
                                        em_string_month = "0"+em_month1;
                                    }
                                    if(em_day1<10)
                                    {
                                        em_string_day = "0"+em_day1;
                                    }

                                    string_summary_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;
                                    textView_summary_enddate.setText(em_string_day+"/"+em_string_month+"/"+em_year1);

                                    //new WebService_DashboardData_Summary().execute();
                                    LoadDashboard_DateRange();
                                }
                                if(int_summaryid == 2)
                                {
                                    textView_summary_startdate.setEnabled(true);
                                    textView_summary_enddate.setEnabled(true);

                                    Calendar c = Calendar.getInstance();
                                    DatePickerDialog da =  new DatePickerDialog(getApplicationContext(), daterange_startdate,
                                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                                            .get(Calendar.DAY_OF_MONTH));
                                    Date newDate = c.getTime();
                                    da.getDatePicker().setMaxDate(newDate.getTime());
                                    da.show();
                                }
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    }
            );

            textView_summary_startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog da =  new DatePickerDialog(getApplicationContext(), daterange_startdate,
                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH));
                    Date newDate = c.getTime();
                    da.getDatePicker().setMaxDate(newDate.getTime());
                    da.show();
                }
            });
            textView_summary_enddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog da =  new DatePickerDialog(getApplicationContext(), daterange_enddate,
                            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH));
                    Date newDate = c.getTime();
                    da.getDatePicker().setMaxDate(newDate.getTime());
                    da.show();
                }
            });

            return rootView;
        }

    }








    public class WebService_DashboardData_Week extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            Log.i("WEEK","StartDate:"+string_week_firstdate+" EndDate:"+string_week_enddate);
            /*dialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
            // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {

            string_locationid = string_selectedlocationid;
            string_companyid = string_selectedcompanyid;
            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","enddate","locationid","companyid"};
            String[] values = {string_userid,string_week_firstdate,string_week_enddate,string_locationid,string_companyid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }
            String result = "";
            try {
                //result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_New");
                result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_Company");
                Log.i("Week:","GetDashboardDetails_Company:"+result);
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
//                            RecyclerViewAdapter_Dashboard_Week_Status adapter_department = new RecyclerViewAdapter_Dashboard_Week_Status
//                                    (DashboardNew.this, jsonArray_status);
//                            adapter_department.notifyDataSetChanged();
//                            recyclerView_week_status.setAdapter(adapter_department);
//
//                            jsonArray_ticketdetails_weekwise = jsonarray_dashboard.getJSONObject(0).getJSONArray("ticketdetails");
//                            RecyclerViewAdapter_Dashboard_Week_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Week_Tickets
//                                    (DashboardNew.this, jsonArray_ticketdetails_weekwise);
//                            adapter_ticket.notifyDataSetChanged();
//                            recyclerView_week_tickets.setAdapter(adapter_ticket);
                        }
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }*/
                    } catch (JSONException e) {
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                        }*/
                        //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                   /* if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }*/
                    //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    e.printStackTrace();

                }
            } catch (Exception e) {

            }

        }
    }



    public void LoadDashboardData_Week(String statusid, String istotal)
    {
        try {
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;
            //TicketDetails_Week_temp.clear();;
            for(int i = 0;i<TicketDetails_Week.size();i++)
            {
                int Statusid = TicketDetails_Week.get(i).getStatusid();
                if(istotal.equals("true"))
                {
                    TicketDetails_Week_temp.add(TicketDetails_Week.get(i));
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid) {
                        TicketDetails_Week_temp.add(TicketDetails_Week.get(i));
                        isempty = true;
                    }
                }
            }
            if(istotal.equals("true"))
            {
                RecyclerViewAdapter_Dashboard_Week_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Week_Tickets
                        (DashboardNew.this, TicketDetails_Week_temp);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_week_tickets.setAdapter(adapter_ticket);
            }
            else
            {
                RecyclerViewAdapter_Dashboard_Week_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Week_Tickets
                        (DashboardNew.this, TicketDetails_Week_temp);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_week_tickets.setAdapter(adapter_ticket);
            }
            recyclerView_week_tickets.findViewHolderForAdapterPosition(0);

        } catch (Exception e) {
            e.printStackTrace();
       }
//       try {
//            JSONArray temp_jsonArray_ticketdetails = new JSONArray();
//            JSONArray temp_jsonArray_ticketdetails_total = new JSONArray();
//            int int_statusid = Integer.parseInt(statusid);
//            boolean isempty = false;
//            for(int i = 0;i<jsonArray_ticketdetails_weekwise.length();i++)
//            {
//                int Statusid = jsonArray_ticketdetails_weekwise.getJSONObject(i).getInt("Statusid");
//                if(istotal.equals("true"))
//                {
//                    int ComplaintId = jsonArray_ticketdetails_weekwise.getJSONObject(i).getInt("ComplaintId");
//                    String TicketNo = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("TicketNo");
//                    String Category = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Category");
//                    String Subject = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Subject");
//                    String Priority = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Priority");
//                    String RequestDate = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("RequestDate");
//                    String RequestTime = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("RequestTime");
//                    String Status = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Status");
//                    Boolean IsClosed = jsonArray_ticketdetails_weekwise.getJSONObject(i).getBoolean("IsClosed");
//                    int StatusCount = jsonArray_ticketdetails_weekwise.getJSONObject(i).getInt("StatusCount");
//                    String ColorCode = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("ColorCode");
//                    String location = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("location");
//
//                    JSONObject jobject_ticket = new JSONObject();
//                    jobject_ticket.put("Statusid",Statusid);
//                    jobject_ticket.put("ComplaintId",ComplaintId);
//                    jobject_ticket.put("TicketNo",TicketNo);
//                    jobject_ticket.put("Category",Category);
//                    jobject_ticket.put("Subject",Subject);
//                    jobject_ticket.put("Priority",Priority);
//                    jobject_ticket.put("RequestDate",RequestDate);
//                    jobject_ticket.put("RequestTime",RequestTime);
//                    jobject_ticket.put("Status",Status);
//                    jobject_ticket.put("IsClosed",IsClosed);
//                    jobject_ticket.put("StatusCount",StatusCount);
//                    jobject_ticket.put("ColorCode",ColorCode);
//                    jobject_ticket.put("location",location);
//                    temp_jsonArray_ticketdetails_total.put(jobject_ticket);
//                    isempty = true;
//                }
//                else {
//                    if (Statusid == int_statusid) {
//                        int ComplaintId = jsonArray_ticketdetails_weekwise.getJSONObject(i).getInt("ComplaintId");
//                        String TicketNo = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("TicketNo");
//                        String Category = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Category");
//                        String Subject = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Subject");
//                        String Priority = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Priority");
//                        String RequestDate = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("RequestDate");
//                        String RequestTime = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("RequestTime");
//                        String Status = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("Status");
//                        Boolean IsClosed = jsonArray_ticketdetails_weekwise.getJSONObject(i).getBoolean("IsClosed");
//                        int StatusCount = jsonArray_ticketdetails_weekwise.getJSONObject(i).getInt("StatusCount");
//                        String ColorCode = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("ColorCode");
//                        String location = jsonArray_ticketdetails_weekwise.getJSONObject(i).getString("location");
//
//                        JSONObject jobject_ticket = new JSONObject();
//                        jobject_ticket.put("Statusid", Statusid);
//                        jobject_ticket.put("ComplaintId", ComplaintId);
//                        jobject_ticket.put("TicketNo", TicketNo);
//                        jobject_ticket.put("Category", Category);
//                        jobject_ticket.put("Subject", Subject);
//                        jobject_ticket.put("Priority", Priority);
//                        jobject_ticket.put("RequestDate", RequestDate);
//                        jobject_ticket.put("RequestTime", RequestTime);
//                        jobject_ticket.put("Status", Status);
//                        jobject_ticket.put("IsClosed",IsClosed);
//                        jobject_ticket.put("StatusCount",StatusCount);
//                        jobject_ticket.put("ColorCode",ColorCode);
//                        jobject_ticket.put("location",location);
//                        temp_jsonArray_ticketdetails.put(jobject_ticket);
//                        isempty = true;
//                    }
//                }
//            }
//            if(istotal.equals("true"))
//            {
//                RecyclerViewAdapter_Dashboard_Week_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Week_Tickets
//                        (DashboardNew.this, temp_jsonArray_ticketdetails_total);
//                adapter_ticket.notifyDataSetChanged();
//                recyclerView_week_tickets.setAdapter(adapter_ticket);
//            }
//            else
//            {
//                RecyclerViewAdapter_Dashboard_Week_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Week_Tickets
//                        (DashboardNew.this, temp_jsonArray_ticketdetails);
//                adapter_ticket.notifyDataSetChanged();
//                recyclerView_week_tickets.setAdapter(adapter_ticket);
//            }
//            recyclerView_week_tickets.findViewHolderForAdapterPosition(0);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


    public void LoadDashboardData_DateRange(String statusid, String istotal)
    {
        try {
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;
            //TicketDetails_DateRange_temp.clear();
            for(int i = 0;i<TicketDetails_DateRange.size();i++)
            {
                int Statusid = TicketDetails_DateRange.get(i).getStatusid();
                if(istotal.equals("true"))
                {
                    TicketDetails_DateRange_temp.add(TicketDetails_DateRange.get(i));
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid) {
                        TicketDetails_DateRange_temp.add(TicketDetails_DateRange.get(i));
                        isempty = true;
                    }
                }
            }

            if(isempty == true) {
                RecyclerViewAdapter_Dashboard_DateRange_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_DateRange_Tickets
                        (DashboardNew.this, TicketDetails_DateRange_temp);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_daterange_tickets.setAdapter(adapter_ticket);
            }
            else
            {
                RecyclerViewAdapter_Dashboard_DateRange_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_DateRange_Tickets
                        (DashboardNew.this, TicketDetails_DateRange_temp);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_daterange_tickets.setAdapter(adapter_ticket);
            }

            recyclerView_daterange_tickets.findViewHolderForAdapterPosition(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            JSONArray temp_jsonArray_ticketdetails = new JSONArray();
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;
            for(int i = 0;i<jsonArray_ticketdetails_daterange.length();i++)
            {
                int Statusid = jsonArray_ticketdetails_daterange.getJSONObject(i).getInt("Statusid");
                if(istotal.equals("true"))
                {
                    int ComplaintId = jsonArray_ticketdetails_daterange.getJSONObject(i).getInt("ComplaintId");
                    String TicketNo = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("TicketNo");
                    String Category = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Category");
                    String Subject = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Subject");
                    String Priority = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Priority");
                    String RequestDate = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("RequestDate");
                    String RequestTime = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("RequestTime");
                    String Status = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Status");
                    Boolean IsClosed = jsonArray_ticketdetails_daterange.getJSONObject(i).getBoolean("IsClosed");
                    int StatusCount = jsonArray_ticketdetails_daterange.getJSONObject(i).getInt("StatusCount");
                    String ColorCode = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("ColorCode");
                    String location = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("location");

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
                    jobject_ticket.put("IsClosed",IsClosed);
                    jobject_ticket.put("StatusCount",StatusCount);
                    jobject_ticket.put("ColorCode",ColorCode);
                    jobject_ticket.put("location",location);
                    temp_jsonArray_ticketdetails.put(jobject_ticket);
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid) {
                        int ComplaintId = jsonArray_ticketdetails_daterange.getJSONObject(i).getInt("ComplaintId");
                        String TicketNo = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("TicketNo");
                        String Category = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Category");
                        String Subject = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Subject");
                        String Priority = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Priority");
                        String RequestDate = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("RequestDate");
                        String RequestTime = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("RequestTime");
                        String Status = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("Status");
                        Boolean IsClosed = jsonArray_ticketdetails_daterange.getJSONObject(i).getBoolean("IsClosed");
                        int StatusCount = jsonArray_ticketdetails_daterange.getJSONObject(i).getInt("StatusCount");
                        String ColorCode = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("ColorCode");
                        String Location = jsonArray_ticketdetails_daterange.getJSONObject(i).getString("location");

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
                        jobject_ticket.put("IsClosed",IsClosed);
                        jobject_ticket.put("StatusCount",StatusCount);
                        jobject_ticket.put("ColorCode",ColorCode);
                        jobject_ticket.put("location",Location);
                        temp_jsonArray_ticketdetails.put(jobject_ticket);
                        isempty = true;
                    }
                }
            }

           *//* if(isempty == true) {
                RecyclerViewAdapter_Dashboard_DateRange_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_DateRange_Tickets
                        (DashboardNew.this, temp_jsonArray_ticketdetails);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_daterange_tickets.setAdapter(adapter_ticket);
            }
            else
            {
                RecyclerViewAdapter_Dashboard_DateRange_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_DateRange_Tickets
                        (DashboardNew.this, temp_jsonArray_ticketdetails);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_daterange_tickets.setAdapter(adapter_ticket);
            }*//*

            recyclerView_daterange_tickets.findViewHolderForAdapterPosition(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }





    public class WebService_DashboardData_Month extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            Log.i("Month","StartDate:"+string_month_startdate+" EndDate:"+string_month_enddate);
            /*dialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
            // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {

            string_locationid = string_selectedlocationid;
            string_companyid = string_selectedcompanyid;
            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","enddate","locationid","companyid"};
            String[] values = {string_userid,string_month_startdate,string_month_enddate,string_locationid,string_companyid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }
            String result = "";
            try {
                //result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_New");
                result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_Company");
                Log.i("Month:","GetDashboardDetails_Company:"+result);
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
//                            RecyclerViewAdapter_Dashboard_Month_Status adapter_department = new RecyclerViewAdapter_Dashboard_Month_Status
//                                    (DashboardNew.this, jsonArray_status);
//                            adapter_department.notifyDataSetChanged();
//                            recyclerView_month_status.setAdapter(adapter_department);
//
//                            jsonArray_ticketdetails_monthwise = jsonarray_dashboard.getJSONObject(0).getJSONArray("ticketdetails");
//                            RecyclerViewAdapter_Dashboard_Month_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Month_Tickets
//                                    (DashboardNew.this, jsonArray_ticketdetails_monthwise);
//                            adapter_ticket.notifyDataSetChanged();
//                            recyclerView_month_tickets.setAdapter(adapter_ticket);
                        }
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }
                        if(refreshdialog != null && refreshdialog.isShowing())
                        {
                            refreshdialog.dismiss();
                        }
                    } catch (JSONException e) {
                       /* if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                        }*/
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }
                } catch (NumberFormatException e) {
                   /* if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }*/
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                }
            } catch (Exception e) {

            }

        }
    }




    public void LoadDashboardData_Month(String statusid, String istotal)
    {
        try {
            int int_statusid = Integer.parseInt(statusid);
            boolean isempty = false;
            //TicketDetails_Month_temp.clear();
            for(int i = 0;i<TicketDetails_Month.size();i++)
            {
                int Statusid = TicketDetails_Month.get(i).getStatusid();
                if(istotal.equals("true"))
                {
                    TicketdetailsItem ticketdetailsItem = TicketDetails_Month.get(i);
                    TicketDetails_Month_temp.add(ticketdetailsItem);
                    isempty = true;
                }
                else {
                    if (Statusid == int_statusid)
                    {
                        TicketdetailsItem ticketdetailsItem = TicketDetails_Month.get(i);
                        TicketDetails_Month_temp.add(ticketdetailsItem);
                        isempty = true;
                    }
                }
            }

            if(isempty == true)
            {
                RecyclerViewAdapter_Dashboard_Month_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Month_Tickets
                        (DashboardNew.this, TicketDetails_Month_temp);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_month_tickets.setAdapter(adapter_ticket);
            }
            else
            {
                RecyclerViewAdapter_Dashboard_Month_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Month_Tickets
                        (DashboardNew.this, TicketDetails_Month_temp);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_month_tickets.setAdapter(adapter_ticket);
            }

            recyclerView_month_tickets.findViewHolderForAdapterPosition(0);

            } catch (Exception e) {
                e.printStackTrace();
           }
// try {
//            JSONArray temp_jsonArray_ticketdetails = new JSONArray();
//            int int_statusid = Integer.parseInt(statusid);
//            boolean isempty = false;
//            for(int i = 0;i<jsonArray_ticketdetails_monthwise.length();i++)
//            {
//                int Statusid = jsonArray_ticketdetails_monthwise.getJSONObject(i).getInt("Statusid");
//                if(istotal.equals("true"))
//                {
//                    int ComplaintId = jsonArray_ticketdetails_monthwise.getJSONObject(i).getInt("ComplaintId");
//                    String TicketNo = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("TicketNo");
//                    String Category = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Category");
//                    String Subject = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Subject");
//                    String Priority = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Priority");
//                    String RequestDate = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("RequestDate");
//                    String RequestTime = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("RequestTime");
//                    String Status = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Status");
//                    String ColorCode = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("ColorCode");
//                    String location = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("location");
//                    Boolean IsClosed = jsonArray_ticketdetails_monthwise.getJSONObject(i).getBoolean("IsClosed");
//                    int StatusCount = jsonArray_ticketdetails_monthwise.getJSONObject(i).getInt("StatusCount");
//
//
//                    JSONObject jobject_ticket = new JSONObject();
//                    jobject_ticket.put("Statusid",Statusid);
//                    jobject_ticket.put("ComplaintId",ComplaintId);
//                    jobject_ticket.put("TicketNo",TicketNo);
//                    jobject_ticket.put("Category",Category);
//                    jobject_ticket.put("Subject",Subject);
//                    jobject_ticket.put("Priority",Priority);
//                    jobject_ticket.put("RequestDate",RequestDate);
//                    jobject_ticket.put("RequestTime",RequestTime);
//                    jobject_ticket.put("Status",Status);
//                    jobject_ticket.put("IsClosed",IsClosed);
//                    jobject_ticket.put("StatusCount",StatusCount);
//                    jobject_ticket.put("ColorCode",ColorCode);
//                    jobject_ticket.put("location",location);
//                    temp_jsonArray_ticketdetails.put(jobject_ticket);
//                    isempty = true;
//                }
//                else {
//                    if (Statusid == int_statusid)
//                    {
//                        int ComplaintId = jsonArray_ticketdetails_monthwise.getJSONObject(i).getInt("ComplaintId");
//                        String TicketNo = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("TicketNo");
//                        String Category = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Category");
//                        String Subject = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Subject");
//                        String Priority = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Priority");
//                        String RequestDate = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("RequestDate");
//                        String RequestTime = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("RequestTime");
//                        String Status = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("Status");
//                        String ColorCode = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("ColorCode");
//                        String location = jsonArray_ticketdetails_monthwise.getJSONObject(i).getString("location");
//                        Boolean IsClosed = jsonArray_ticketdetails_monthwise.getJSONObject(i).getBoolean("IsClosed");
//                        int StatusCount = jsonArray_ticketdetails_monthwise.getJSONObject(i).getInt("StatusCount");
//
//
//                        JSONObject jobject_ticket = new JSONObject();
//                        jobject_ticket.put("Statusid", Statusid);
//                        jobject_ticket.put("ComplaintId", ComplaintId);
//                        jobject_ticket.put("TicketNo", TicketNo);
//                        jobject_ticket.put("Category", Category);
//                        jobject_ticket.put("Subject", Subject);
//                        jobject_ticket.put("Priority", Priority);
//                        jobject_ticket.put("RequestDate", RequestDate);
//                        jobject_ticket.put("RequestTime", RequestTime);
//                        jobject_ticket.put("Status", Status);
//                        jobject_ticket.put("IsClosed",IsClosed);
//                        jobject_ticket.put("StatusCount",StatusCount);
//                        jobject_ticket.put("ColorCode",ColorCode);
//                        jobject_ticket.put("location",location);
//                        temp_jsonArray_ticketdetails.put(jobject_ticket);
//                        isempty = true;
//                    }
//                }
//            }
//
//            if(isempty == true)
//            {
//                RecyclerViewAdapter_Dashboard_Month_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Month_Tickets
//                        (DashboardNew.this, temp_jsonArray_ticketdetails);
//                adapter_ticket.notifyDataSetChanged();
//                recyclerView_month_tickets.setAdapter(adapter_ticket);
//            }
//            else
//            {
//                RecyclerViewAdapter_Dashboard_Month_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Month_Tickets
//                        (DashboardNew.this, temp_jsonArray_ticketdetails);
//                adapter_ticket.notifyDataSetChanged();
//                recyclerView_month_tickets.setAdapter(adapter_ticket);
//            }
//
//            recyclerView_month_tickets.findViewHolderForAdapterPosition(0);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }






    public class WebService_DashboardData_Summary extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
        ProgressDialog dialog;
        TableRow row_dept;
        int position_deptid = 0;
        JSONArray jsonArray_deptwise;
        LinearLayout view_dept;
        @Override
        protected void onPreExecute()
        {
            Log.i("Summary","StartDate:"+string_summary_startdate+" EndDate:"+string_summary_enddate);
           /* dialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
            // dialog.setContentView(R.layout.layout_loading);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {

            Hashtable<String, String> ht = new Hashtable<String, String>();
            //Keys String Array
            ht.put("LocationID", string_locationid);

            String[] keys = { "userid","TicketDate","enddate","locationid"};
            String[] values = {string_userid,string_summary_startdate,string_summary_enddate,string_locationid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }
            String result = "";
            try {
                result = new NetworkCall(DashboardNew.this).postDataToSOAPService(ht, "GetDashboardDetails_New");
                Log.i("Summary:","GetDashboardDetails_New:"+result);
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
                            JSONArray jsonArray_status = jsonarray_dashboard.getJSONObject(0).getJSONArray("statusdetails");
                            tableLayout_summary.removeAllViews();
                            for(int i=0; i<jsonArray_status.length();i++)
                            {
                                tableRow_summary_item = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_summary_item, null);


                                String status_color;
                                String status = jsonArray_status.getJSONObject(i).getString("status");
                                String tickets = ""+jsonArray_status.getJSONObject(i).getInt("tickets");
                                String colorcode = jsonArray_status.getJSONObject(i).getString("colorcode");
                                Boolean isTotal = jsonArray_status.getJSONObject(i).getBoolean("isTotal");
                                String color[] = colorcode.split(" - ");
                                status_color = color[1];

                                TextView textView_status = (TextView) tableRow_summary_item.findViewById(R.id.textview_status);
                                TextView textView_statuscount = (TextView) tableRow_summary_item.findViewById(R.id.textview_count);
                                View view_status = (View)tableRow_summary_item.findViewById(R.id.view_statusview);

                                if(isTotal == false)
                                {
                                    textView_status.setText(status);
                                    textView_statuscount.setText("" + tickets);
                                    GradientDrawable bgShape = (GradientDrawable)view_status.getBackground();
                                    bgShape.setColor(Color.parseColor(status_color));
                                    tableLayout_summary.addView(tableRow_summary_item);
                                }

                            }
                        }
                       /* if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }*/
                    } catch (JSONException e) {
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                            //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                        }*/
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    /*if(dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                        //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                    }*/
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

        }
    }



    DatePickerDialog.OnDateSetListener daterange_startdate = new DatePickerDialog.OnDateSetListener() {

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
            textView_summary_startdate.setText(str_day + "/" + str_month + "/" + cyear);
            string_summary_startdate = str_month + "/" + str_day + "/" + cyear;

            Calendar c = Calendar.getInstance();
            DatePickerDialog da =  new DatePickerDialog(getApplicationContext(), daterange_enddate,
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                    .get(Calendar.DAY_OF_MONTH));
            Date newDate = c.getTime();
            da.getDatePicker().setMaxDate(newDate.getTime());
            da.show();
        }
    };


    DatePickerDialog.OnDateSetListener daterange_enddate = new DatePickerDialog.OnDateSetListener() {

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
            textView_summary_enddate.setText(str_day + "/" + str_month + "/" + cyear);
            string_summary_enddate = str_month + "/" + str_day + "/" + cyear;

            //new WebService_DashboardData_Summary().execute();
            LoadDashboard_DateRange();
        }
    };


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
                DashboardNew.this, AlertDialog.THEME_HOLO_LIGHT)
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
                        Intent ii9i=new Intent(DashboardNew.this,LoginActivity.class);
                        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii9i);
                    }
                })
                .setNegativeButton(R.string.dialog_button_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).show();
    }

    private BroadcastReceiver gcmReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // Update view
                    //new WebService_DashboardData_today().execute();
                    LoadDashboard_Today();
                    LoadDashboard_Week();
                    LoadDashboard_Month();
                    LoadDashboard_DateRange();
                    //new WebService_DashboardData_Week().execute();
                    //new WebService_DashboardData_Month().execute();
                   // prefs.setBoolean("OpenNotification",true);
                }
            };

    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(gcmReceiver);
    }


    public void LoadDashboard_Today()
    {
        string_locationid = string_selectedlocationid;
        string_companyid = string_selectedcompanyid;

        Call<DashboardData> call_dashboardtoday = apiService.GetDashboardDetails(string_userid,string_date,string_date,string_companyid,string_locationid);
        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_date+" EDate:"+string_date+" CompanyID:"+string_companyid+"LocationID:"+string_locationid);
        call_dashboardtoday.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                int statusCode = response.code();


                StatusDetailsItem_Today = response.body().getStatusdetails();
                TicketDetails_Today = response.body().getTicketdetails();
                TicketDetails_Today_temp = response.body().getTicketdetails();

                if(StatusDetailsItem_Today.size() > 0)
                {
                    RecyclerViewAdapter_Dashboard_Today_Status adapter_department = new RecyclerViewAdapter_Dashboard_Today_Status
                            (DashboardNew.this, StatusDetailsItem_Today);

                    adapter_department.notifyDataSetChanged();
                    recyclerView_today_status.setAdapter(adapter_department);

                    if(TicketDetails_Today.size() > 0)
                    {
                        RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                                (DashboardNew.this, TicketDetails_Today, false);
                        adapter_ticket.notifyDataSetChanged();

                    recyclerView_today_tickets.setAdapter(adapter_ticket);
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
    }

    public void LoadDashboard_Week()
    {
        string_locationid = string_selectedlocationid;
        string_companyid = string_selectedcompanyid;
        //String[] values = {string_userid,string_week_firstdate,string_week_enddate,string_locationid,string_companyid};
        Call<DashboardData> call_dashboardweek = apiService.GetDashboardDetails(string_userid,string_week_firstdate,string_week_enddate,string_companyid,string_locationid);
        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_week_firstdate+" EDate:"+string_week_enddate+" CompanyID:"+string_companyid+"LocationID:"+string_locationid);

        call_dashboardweek.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {


                StatusDetailsItem_Week = response.body().getStatusdetails();
                TicketDetails_Week = response.body().getTicketdetails();
                TicketDetails_Week_temp = response.body().getTicketdetails();

                RecyclerViewAdapter_Dashboard_Week_Status adapter_department = new RecyclerViewAdapter_Dashboard_Week_Status
                        (DashboardNew.this, StatusDetailsItem_Week);
                adapter_department.notifyDataSetChanged();
                recyclerView_week_status.setAdapter(adapter_department);

                RecyclerViewAdapter_Dashboard_Week_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Week_Tickets
                        (DashboardNew.this, TicketDetails_Week);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_week_tickets.setAdapter(adapter_ticket);
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
    }


    public void LoadDashboard_Month()
    {
        string_locationid = string_selectedlocationid;
        string_companyid = string_selectedcompanyid;

        Call<DashboardData> call_dashboardmonth = apiService.GetDashboardDetails(string_userid,string_month_startdate,string_month_enddate,string_companyid,string_locationid);
        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_month_startdate+" EDate:"+string_month_enddate+" CompanyID:"+string_companyid+"LocationID:"+string_locationid);

        call_dashboardmonth.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {



                StatusDetailsItem_Month = response.body().getStatusdetails();
                TicketDetails_Month = response.body().getTicketdetails();
                TicketDetails_Month_temp = response.body().getTicketdetails();

                RecyclerViewAdapter_Dashboard_Month_Status adapter_department = new RecyclerViewAdapter_Dashboard_Month_Status
                        (DashboardNew.this, StatusDetailsItem_Month);
                adapter_department.notifyDataSetChanged();
                recyclerView_month_status.setAdapter(adapter_department);

                RecyclerViewAdapter_Dashboard_Month_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Month_Tickets
                        (DashboardNew.this, TicketDetails_Month);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_month_tickets.setAdapter(adapter_ticket);

            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
    }


    public void LoadDashboard_DateRange()
    {
        string_locationid = string_selectedlocationid;
        string_companyid = string_selectedcompanyid;

        Call<DashboardData> call_dashboardmonth = apiService.GetDashboardDetails(string_userid,string_daterange_startdate,string_daterange_enddate,string_companyid,string_locationid);
        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_daterange_startdate+" EDate:"+string_daterange_enddate+" CompanyID:"+string_companyid+"LocationID:"+string_locationid);

        call_dashboardmonth.enqueue(new Callback<DashboardData>()
        {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {

                StatusDetailsItem_DateRange = response.body().getStatusdetails();
                TicketDetails_DateRange = response.body().getTicketdetails();
                TicketDetails_DateRange_temp = response.body().getTicketdetails();

                recyclerView_daterange_status.setAdapter(null);
                recyclerView_daterange_tickets.setAdapter(null);

                RecyclerViewAdapter_Dashboard_DateRange_Status adapter_department = new RecyclerViewAdapter_Dashboard_DateRange_Status
                        (DashboardNew.this, StatusDetailsItem_DateRange);
                adapter_department.notifyDataSetChanged();
                recyclerView_daterange_status.setAdapter(adapter_department);

                RecyclerViewAdapter_Dashboard_DateRange_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_DateRange_Tickets
                        (DashboardNew.this, TicketDetails_DateRange);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_daterange_tickets.setAdapter(adapter_ticket);

            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
    }



    public void LoadDashboard()
    {
        string_locationid = string_selectedlocationid;
        string_companyid = string_selectedcompanyid;

        Call<DashboardData> call_dashboardmonth = apiService.GetDashboardDetails(string_userid,string_dashboard_enddate,string_dashboard_startdate,string_companyid,string_locationid);
        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_dashboard_enddate+" EDate:"+string_dashboard_startdate+" CompanyID:"+string_companyid+"LocationID:"+string_locationid);

        call_dashboardmonth.enqueue(new Callback<DashboardData>()
        {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {

                StatusDetailsItem_TotalData = response.body().getStatusdetails();
                TicketDetails_TotalData = response.body().getTicketdetails();


                if(TicketDetails_TotalData.size() >0) {
                    try {
                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                        setupTabIcons();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
    }

}




