package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.StatusdetailsItem;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.TicketdetailsItem;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List;
import helpdesk.i2i.com.ifazidesk.getset.CompanydetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.getset.LocationdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class PreDashboard extends AppCompatActivity {
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
    TextView textview_serviceenggtickets;
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
    String string_month_enddate_temp = "";
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

    RadioGroup radioGroup_dashboardtype;
    RadioButton radioButton_today;
    RadioButton radioButton_yesterday;
    RadioButton radioButton_week;
    RadioButton radioButton_month;
    RadioButton radioButton_3months;
    RadioButton radioButton_daterange;
    Button button_go;
    Button button_serviceenggtickets;

    Calendar calendar_week;
    int current_week_weekno = 0;
    int current_week_year = 0;
    int current_week_month = 0;
    int current_week_date = 0;
    String string_week_currentmonth = "";
    String string_week_currentdate = "";
    String temp_week_firstdate = "";
    String temp_week_lastdate = "";
    RelativeLayout rl_daterage;

    boolean boolean_todayselected = true;
    boolean boolean_yesterdayselected = false;
    boolean boolean_last7daysselected = true;
    boolean boolean_last30daysselected = true;
    boolean boolean_daterangeselected = true;

    String string_date_temp = "";
    String string_request_startdate = "";
    String string_request_enddate = "";
    String string_request_startdate_temp = "";
    String string_request_enddate_temp = "";

    String string_daterange_sdate = "";
    String string_daterange_sdate_temp = "";
    String string_daterange_edate = "";
    String string_daterange_edate_temp = "";

    String string_yesterday = "";
    String string_yesterday_temp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_predashboard);

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("ifazidesk_db.realm")
                .schemaVersion(0)
                .build();
        mRealm = Realm.getInstance(realmConfig);

        realmResults_company = mRealm.where(CompanydetailsItem.class).findAllAsync();
        realmResults_location = mRealm.where(LocationdetailsItem.class).findAllAsync();
        realmResults_company.load();
        realmResults_location.load();

        prefs = new Preferences(this);
        int_roleid = prefs.getInt("RoleID");
        string_userid = ""+prefs.getInt("UserID_SP");
        string_username = prefs.getString("UserName_SP");
        string_usercompany = prefs.getString("User_Company");
        string_companylogo = prefs.getString("Company_Logo");
        string_Language = prefs.getString("Language");
        isServiceEngineer = prefs.getBoolean("isServiceEngineer_Boolean");


        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        rl_new = (RelativeLayout) findViewById(R.id.rl_new);
        rl_user = (RelativeLayout) findViewById(R.id.rl_user);

        rl_companylist = (RelativeLayout) findViewById(R.id.rl_selectcompany);
        rl_locationlist = (RelativeLayout) findViewById(R.id.rl_selectlocation);

        textview_companyname = (TextView) findViewById(R.id.textview_companyname);
        textview_locationname = (TextView) findViewById(R.id.textview_locationname);

        imageView_companylogo = (ImageView) findViewById(R.id.imageview_user);
        textView_username = (TextView) findViewById(R.id.textview_username);
        textView_company = (TextView) findViewById(R.id.textview_usercompany);
        textview_serviceenggtickets = (TextView) findViewById(R.id.textview_serviceenggtickets);

        radioGroup_dashboardtype = (RadioGroup) findViewById(R.id.radiogroup_dashboardtype);
        radioButton_today = (RadioButton) findViewById(R.id.rb_today);
        radioButton_yesterday = (RadioButton) findViewById(R.id.rb_yesterday);
        radioButton_week = (RadioButton) findViewById(R.id.rb_week);
        radioButton_month = (RadioButton) findViewById(R.id.rb_month);

        radioButton_3months = (RadioButton) findViewById(R.id.rb_last3months);
        radioButton_3months.setVisibility(View.GONE);
        radioButton_daterange = (RadioButton) findViewById(R.id.rb_daterange);
        rl_daterage = (RelativeLayout) findViewById(R.id.rl_dateselection);
        rl_daterage.setVisibility(View.GONE);
        button_go = (Button)findViewById(R.id.button_next);
        button_serviceenggtickets = (Button)findViewById(R.id.button_serviceenggtickets);
        button_go.setEnabled(false);
        button_serviceenggtickets.setEnabled(false);

        recyclerView_today_status = (RecyclerView) findViewById(R.id.recyclerView_status);
        recyclerView_today_tickets = (RecyclerView) findViewById(R.id.recyclerView_tickets);

        textView_summary_startdate = (TextView)findViewById(R.id.textview_startdate);
        textView_summary_startdate.setEnabled(false);
        textView_summary_enddate = (TextView)findViewById(R.id.textview_enddate);
        textView_summary_enddate.setEnabled(false);

        //ImageView imageview_refresh = (ImageView) findViewById(R.id.imageview_refresh);

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

        if(isServiceEngineer == true)
        {
            button_serviceenggtickets.setVisibility(View.VISIBLE);
            textview_serviceenggtickets.setVisibility(View.VISIBLE);
        }
        else
        {
            button_serviceenggtickets.setVisibility(View.GONE);
            textview_serviceenggtickets.setVisibility(View.GONE);
        }
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
        string_date_temp = string_currentdate+"/"+string_currentmonth+"/"+current_year;
        string_date = string_currentmonth+"/"+string_currentdate+"/"+current_year;

        try {
            companylist.clear();
            GetSet allcompany_item = new GetSet();
            allcompany_item.setInt_Item_1(0);
            allcompany_item.setString_Item_1("All Company");
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

        radioButton_today.setText("Today ("+string_date_temp+")");

        calendar_week = Calendar.getInstance();
        current_week_year = calendar_week.get(Calendar.YEAR);
        current_week_month = calendar_week.get(Calendar.MONTH)+1;
        current_week_date = calendar_week.get(Calendar.DAY_OF_MONTH);

        current_week_weekno = calendar_week.get(Calendar.WEEK_OF_YEAR);

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

        SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat dateFormat_temp = new java.text.SimpleDateFormat("dd/MM/yyyy");

        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(new Date());
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        string_yesterday_temp = dateFormat_temp.format(yesterday.getTime());
        string_yesterday = dateFormat.format(yesterday.getTime());

        radioButton_yesterday.setText("Yesterday ("+string_yesterday_temp+")");

        Calendar last7days = Calendar.getInstance();
        last7days.setTime(new Date());
        last7days.add(Calendar.DAY_OF_YEAR, -7);

        temp_week_firstdate = dateFormat_temp.format(last7days.getTime());
        string_week_firstdate = dateFormat.format(last7days.getTime());

        radioButton_week.setText("Last 7 Days ("+temp_week_firstdate+" - "+string_date_temp+")");

        string_daterange_temp_startdate = temp_week_firstdate;
        string_daterange_temp_enddate = temp_week_lastdate;

        Log.i("WEEK","StartDate:"+string_week_firstdate+" EndDate:"+string_week_enddate);



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

        string_month_enddate_temp = em_string_day + "/" + em_string_month + "/" + em_year1;
        string_month_enddate = em_string_month + "/" + em_string_day + "/" + em_year1;

        calendar_enddate = Calendar.getInstance();
        String month = calendar_enddate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        Calendar last30days = Calendar.getInstance();
        last30days.setTime(new Date());
        last30days.add(Calendar.DAY_OF_YEAR, -30);

        string_month_enddate_temp = dateFormat_temp.format(last30days.getTime());
        string_month_enddate = dateFormat.format(last30days.getTime());

        radioButton_month.setText("Last 30 Days ("+string_month_enddate_temp+" - "+string_date_temp+")");


        Calendar calendar_daterangestartdate = Calendar.getInstance();
        calendar_daterangestartdate.set(Calendar.DAY_OF_MONTH, 1);
        int cm_year1 = calendar_daterangestartdate.get(Calendar.YEAR);
        int cm_month1 = calendar_daterangestartdate.get(Calendar.MONTH)+1;
        if(cm_month1 > 3)
        {
            cm_month1 = cm_month1 - 3;
        }
        else
        {
            cm_month1 = 1;
        }
        int cm_day1 = calendar_daterangestartdate.get(Calendar.DAY_OF_MONTH);
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


        Calendar calendar_daterangeenddate = Calendar.getInstance();
        int dr_year1 = calendar_daterangeenddate.get(Calendar.YEAR);
        int dr_month1 = calendar_daterangeenddate.get(Calendar.MONTH)+1;
        int dr_day1 = calendar_daterangeenddate.get(Calendar.DAY_OF_MONTH);
        String dr_string_month = ""+dr_month1;
        String dr_string_day = ""+dr_day1;

        if(dr_month1<10)
        {
            dr_string_month = "0"+dr_month1;
        }
        if(dr_day1<10)
        {
            dr_string_day = "0"+dr_string_day;
        }

        string_summary_enddate = dr_string_month + "/" + dr_string_day + "/" + dr_year1;
        string_daterange_enddate = dr_string_month + "/" + dr_string_day + "/" + dr_year1;

        String string_dr_startdate = cm_string_day+"/"+cm_string_month+"/"+cm_year1;
        String string_dr_enddate = dr_string_day+"/"+dr_string_month+"/"+dr_year1;

        radioButton_3months.setText("Last 3 Months ("+string_dr_startdate+" - "+string_dr_enddate+")");

        radioGroup_dashboardtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if(checkedId == R.id.rb_daterange)
                {
                    rl_daterage.setVisibility(View.VISIBLE);
                    textView_summary_startdate.setEnabled(true);
                    textView_summary_enddate.setEnabled(true);
                }
                else {
                    if(checkedId == R.id.rb_today)
                    {
                        boolean_todayselected=true;
                        boolean_yesterdayselected=false;
                        boolean_last7daysselected=false;
                        boolean_last30daysselected=false;
                        boolean_daterangeselected=false;
                    }
                    if(checkedId == R.id.rb_yesterday)
                    {
                        boolean_yesterdayselected = true;
                        boolean_todayselected=false;
                        boolean_last7daysselected=false;
                        boolean_last30daysselected=false;
                        boolean_daterangeselected=false;
                    }
                    if(checkedId == R.id.rb_week)
                    {
                        boolean_last7daysselected=true;
                        boolean_todayselected=false;
                        boolean_yesterdayselected=false;
                        boolean_last30daysselected=false;
                        boolean_daterangeselected=false;
                    }
                    if(checkedId == R.id.rb_month)
                    {
                        boolean_last30daysselected=true;
                        boolean_todayselected=false;
                        boolean_yesterdayselected=false;
                        boolean_last7daysselected=false;
                        boolean_daterangeselected=false;
                    }
                    if (isChecked) {
                        // Changes the textview's text to "Checked: example radiobutton text"
                        rl_daterage.setVisibility(View.GONE);
                        button_go.setEnabled(true);
                        button_go.setBackgroundResource(R.drawable.drawable_button_new);

                        button_serviceenggtickets.setEnabled(true);
                        button_serviceenggtickets.setBackgroundResource(R.drawable.drawable_button_new);
                    }
                }

            }
        });

        rl_companylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                android.support.v4.app.FragmentManager manager = PreDashboard.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();

                bundle.putString(ARG_PARAM1, string_companydetails);
                bundle.putString(ARG_PARAM2, "Company");
                bundle.putString(ARG_PARAM3, "isDashboardCompanyList");
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
                android.support.v4.app.FragmentManager manager = PreDashboard.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();

                bundle.putString(ARG_PARAM1, string_locationdetails);
                bundle.putString(ARG_PARAM2, "Location");
                bundle.putString(ARG_PARAM3, "isDashboardLocationList");
                bundle.putString(ARG_PARAM4, string_selectedcompanyid);

                Fragment_List dialog = new Fragment_List();
                dialog.setArguments(bundle);
                dialog.show(manager, "dialog");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });

        button_serviceenggtickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String string_DASHBOARDTYPE = "";
                if(boolean_todayselected == true)
                {
                    string_request_startdate = string_date;
                    string_request_enddate = string_date;
                    string_request_startdate_temp = string_date_temp;
                    string_request_enddate_temp = string_date_temp;
                    string_DASHBOARDTYPE = "TODAY";
                }
                if(boolean_yesterdayselected == true)
                {
                    string_request_startdate = string_yesterday;
                    string_request_enddate = string_yesterday;
                    string_request_startdate_temp = string_yesterday_temp;
                    string_request_enddate_temp = string_yesterday_temp;
                    string_DASHBOARDTYPE = "YESTERDAY";
                }
                if(boolean_last7daysselected == true)
                {
                    string_request_startdate = string_week_firstdate;
                    string_request_enddate = string_date;
                    string_request_startdate_temp = temp_week_firstdate;
                    string_request_enddate_temp = string_date_temp;
                    string_DASHBOARDTYPE = "LAST7DAYS";
                }
                if(boolean_last30daysselected == true)
                {
                    string_request_startdate = string_month_enddate;
                    string_request_enddate = string_date;
                    string_request_startdate_temp = string_month_enddate_temp;
                    string_request_enddate_temp = string_date_temp;
                    string_DASHBOARDTYPE = "LAST30DAYS";
                }
                if(boolean_daterangeselected == true)
                {
                    string_request_startdate = string_daterange_sdate;
                    string_request_enddate = string_daterange_edate;
                    string_request_startdate_temp = string_daterange_sdate_temp;
                    string_request_enddate_temp = string_daterange_edate_temp;
                    string_DASHBOARDTYPE = "DATERANGE";
                }

                prefs.setString("DASHBOARD_USERTYPE", "SE");
                prefs.setString("DASHBOARD_COMPANY", string_selectedcompanyname);
                prefs.setString("DASHBOARD_COMPANYID", string_selectedcompanyid);
                prefs.setString("DASHBOARD_LOCATION", string_selectedlocationname);
                prefs.setString("DASHBOARD_LOCATIONID", string_selectedlocationid);
                prefs.setString("DASHBOARD_TYPE", string_DASHBOARDTYPE);
                prefs.setString("DASHBOARD_STARTDATE", string_request_startdate);
                prefs.setString("DASHBOARD_ENDDATE", string_request_enddate);
                prefs.setString("DASHBOARD_STARTDATE_TEMP", string_request_startdate_temp);
                prefs.setString("DASHBOARD_ENDDATE_TEMP", string_request_enddate_temp);

                Intent i = new Intent(PreDashboard.this, NewDashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
             String string_DASHBOARDTYPE = "";
                    if(boolean_todayselected == true)
                    {
                        string_request_startdate = string_date;
                        string_request_enddate = string_date;
                        string_request_startdate_temp = string_date_temp;
                        string_request_enddate_temp = string_date_temp;
                        string_DASHBOARDTYPE = "TODAY";
                    }
                    if(boolean_yesterdayselected == true)
                    {
                        string_request_startdate = string_yesterday;
                        string_request_enddate = string_yesterday;
                        string_request_startdate_temp = string_yesterday_temp;
                        string_request_enddate_temp = string_yesterday_temp;
                        string_DASHBOARDTYPE = "YESTERDAY";
                    }
                    if(boolean_last7daysselected == true)
                    {
                        string_request_startdate = string_week_firstdate;
                        string_request_enddate = string_date;
                        string_request_startdate_temp = temp_week_firstdate;
                        string_request_enddate_temp = string_date_temp;
                        string_DASHBOARDTYPE = "LAST7DAYS";
                    }
                    if(boolean_last30daysselected == true)
                    {
                        string_request_startdate = string_month_enddate;
                        string_request_enddate = string_date;
                        string_request_startdate_temp = string_month_enddate_temp;
                        string_request_enddate_temp = string_date_temp;
                        string_DASHBOARDTYPE = "LAST30DAYS";
                    }
                    if(boolean_daterangeselected == true)
                    {
                        string_request_startdate = string_daterange_sdate;
                        string_request_enddate = string_daterange_edate;
                        string_request_startdate_temp = string_daterange_sdate_temp;
                        string_request_enddate_temp = string_daterange_edate_temp;
                        string_DASHBOARDTYPE = "DATERANGE";
                    }

                prefs.setString("DASHBOARD_USERTYPE", "USER");
                prefs.setString("DASHBOARD_COMPANY", string_selectedcompanyname);
                prefs.setString("DASHBOARD_COMPANYID", string_selectedcompanyid);
                prefs.setString("DASHBOARD_LOCATION", string_selectedlocationname);
                prefs.setString("DASHBOARD_LOCATIONID", string_selectedlocationid);
                prefs.setString("DASHBOARD_TYPE", string_DASHBOARDTYPE);
                prefs.setString("DASHBOARD_STARTDATE", string_request_startdate);
                prefs.setString("DASHBOARD_ENDDATE", string_request_enddate);
                prefs.setString("DASHBOARD_STARTDATE_TEMP", string_request_startdate_temp);
                prefs.setString("DASHBOARD_ENDDATE_TEMP", string_request_enddate_temp);

                Intent i = new Intent(PreDashboard.this, NewDashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        textView_summary_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Calendar c = Calendar.getInstance();
                DatePickerDialog da =  new DatePickerDialog(PreDashboard.this, date_daterange_startdate,
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
                DatePickerDialog da =  new DatePickerDialog(PreDashboard.this, date_daterange_enddate,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                Date newDate = c.getTime();
                da.getDatePicker().setMaxDate(newDate.getTime());
                da.show();

            }
        });

    }


    public void SetCompany(String Name, String ID)
    {
        textview_companyname.setText(Name);
        string_selectedcompanyname = Name;
        string_selectedcompanyid = ID;
        try {

            realmResults_location = mRealm.where(LocationdetailsItem.class).equalTo("companyId",Integer.parseInt(string_selectedcompanyid)).findAll();
            realmResults_location.load();
            for(int i=0;i<realmResults_location.size();i++)
            {
                int CompanyId = realmResults_location.get(i).getCompanyId();
                int LocationID = realmResults_location.get(i).getLocationId();
                String LocationName = realmResults_location.get(i).getLocation();
                if(Integer.parseInt(string_selectedcompanyid) == 0)
                {
                    SetLocation("All Locations", "0");
                    break;
                }
                else
                {

                    if(CompanyId == Integer.parseInt(string_selectedcompanyid))
                    {
                        SetLocation(LocationName, "" + LocationID);
                        break;
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
        prefs.setString("Dashboard_LocationID", string_selectedlocationid);
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
            textView_summary_startdate.setText(str_day + "/" + str_month + "/" + cyear);
            string_daterange_startdate = str_month + "/" + str_day + "/" + cyear;

            string_daterange_sdate = str_month + "/" + str_day + "/" + cyear;
            string_daterange_sdate_temp = str_day + "/" + str_month + "/" + cyear;

            Calendar c = Calendar.getInstance();
            DatePickerDialog da =  new DatePickerDialog(PreDashboard.this, date_daterange_enddate,
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
            textView_summary_enddate.setText(str_day + "/" + str_month + "/" + cyear);
            string_daterange_enddate = str_month + "/" + str_day + "/" + cyear;

            string_daterange_edate = str_month + "/" + str_day + "/" + cyear;
            string_daterange_edate_temp = str_day + "/" + str_month + "/" + cyear;

            button_go.setEnabled(true);
            button_go.setBackgroundResource(R.drawable.drawable_button_new);

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
                PreDashboard.this, AlertDialog.THEME_HOLO_LIGHT)
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
                        Intent ii9i=new Intent(PreDashboard.this,LoginActivity.class);
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
