package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.datamodel.assignee.AssigneeResponse;
import helpdesk.i2i.com.ifazidesk.datamodel.assignee.LstassigneeItem;
import helpdesk.i2i.com.ifazidesk.datamodel.assignee.LstproviderItem;
import helpdesk.i2i.com.ifazidesk.datamodel.status.StatusData;
import helpdesk.i2i.com.ifazidesk.datamodel.status.StatusResponse;
import helpdesk.i2i.com.ifazidesk.datamodel.ticketdetails.TicketDetailsData;
import helpdesk.i2i.com.ifazidesk.datamodel.ticketdetails.TicketDetailsResponse;
import helpdesk.i2i.com.ifazidesk.datamodel.ticketdetails.TicketHistoryItem;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;

public class TicketDetails extends AppCompatActivity {
    public Bundle getBundle = null;
    String string_ticketid="";

    TextView textview_TicketNo;
    TextView textview_Category;
    TextView textview_Subject;
    TextView textview_Status;
    TextView textview_Priority;
    TextView textview_RequestorName;
    TextView textview_AssignedTo;
    TextView textview_datetime;
    TextView textview_label_history;
    Button button_update;
    TableLayout tableLayout_history;
    RadioGroup radiogroup_status;
    Spinner spinner_assignee;
    Spinner spinner_reassignee;
    String string_currentstatusid;
    String string_serviceenggxml = "";
    List<GetSet> GetSet_AssigneList = new ArrayList<GetSet>();
    String string_companyid,string_locationid,string_userid,string_statusid,string_roleid;
    Preferences prefs;
    TextView textview_location;
    TextView textview_building;
    TextView textview_floor;
    TextView textview_wing;

    TextView textview_reassign_location;
    TextView textview_reassign_building;
    TextView textview_reassign_floor;
    TextView textview_reassign_wing;
    TextView textview_reassign_RequestorName;

    Dialog alertDialog;
    JSONArray jsonarray_history_temp = null;
    String string_jsonarray = "";
    TextView textview_assignedtouser;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String string_isclosed = "";
    String string_ticketstatus = "";
    int int_ticketstatus = 0;
    String tckt_location = "";
    String tckt_building = "";
    String tckt_floor = "";
    String tckt_wing = "";
    String RequestorName = "";
    RelativeLayout rl_reassigndisabled;
    String string_reassigneeid = "";
    TextView textview_reqhours;
    Long timeInMillis = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    Calendar calendar_countdown;
    TextView textview_reassign_assignedto;
    EditText editText_remarks;
    String string_remarks = "";

    //Multi Languague
    String string_Language = "";
    String string_REASSIGN = "";
    String string_HISTORY = "";
    String string_PROGRESS_STATUSLOADING = "";
    String string_PROGRESS_ASSIGNEELOADING = "";
    String string_ASSIGNEDTO = "";
    String string_AVAILABLEASSIGNEE = "";
    String string_HINTREMARKS = "";
    String string_BUTTON_UPDATE = "";
    String string_TV_TIMEHRS = "";
    String string_CANTREASSIGN = "";
    String string_TV_STATUS = "";
    String string_TV_DATEandTIME = "";
    String string_TV_DESCRIPTION = "";
    String string_TV_TICKETDETAILS = "";
    String string_TV_SUMMARY = "";
    ImageView imageview_complaintimage;
    ImageView imageview_complaintfullimage;
    ImageView imageview_updateimage;
    String strImagePath = "";
    String string_DIALOG_MENU_TAKEPHOTO = "Take Photo";
    String string_DIALOG_MENU_CHOOSEIMAGE = "Choose from Gallery";
    String string_DIALOG_MENU_CHOOSEFILES = "Choose Docs";
    String string_DIALOG_MENU_CANCEL = "Cancel";

    public static final int REQUEST_CAMERA = 5555;
    public static final int REQUEST_IMAGES = 1111;
    public static final int SELECT_FILE = 2222;
    public static final int SELECT_DOC = 3333;
    private static final int CAMERA_REQUEST_CODE = 101;
    String string_filetype = "";
    boolean cameraAccepted = false;
    boolean storageAccepted = false;

    ScrollView rl_imageview;
    ImageView imageview_close;
    TextView textview_hrs;
    String string_attachmentlink = "";
    ImageView imageview_status;
    RelativeLayout rl_priority_bg;
    RelativeLayout rl_status_bg;
    String string_isOpenBySE;
    String string_isOpenByAdmin;
    String strImagename = ""+System.currentTimeMillis()+".jpg";
    String temp = "";
    ProgressDialog updatecomplaintdialog;
    ProgressDialog progressDialog_ticketdetails;

    APIInterface apiService;
    String TAG ="TicketDetails";
    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("type")) {
                String type = extras.getString("type");
                string_ticketid = extras.getString("TicketNo");
                string_isclosed = extras.getString("Ticket_isClosed");
                string_ticketstatus = extras.getString("Ticket_StatusCount");
                string_isOpenBySE = extras.getString("Ticket_isSE");
                string_isOpenByAdmin = extras.getString("Ticket_isAdmin");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_tabs_ticketdetails);

        apiService = APIClient.getClient().create(APIInterface.class);
        prefs = new Preferences(TicketDetails.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getBundle = this.getIntent().getExtras();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        string_userid = ""+prefs.getInt("UserID_SP");
        string_roleid = ""+prefs.getInt("RoleID");
        string_locationid = prefs.getString("Dashboard_LocationID");
        string_companyid = prefs.getString("Dashboard_CompanyID");
        string_Language = prefs.getString("Language");

        string_ticketid = getBundle.getString("TicketNo");
        string_isclosed = getBundle.getString("Ticket_isClosed");
        string_ticketstatus = getBundle.getString("Ticket_StatusCount");
        string_isOpenBySE = getBundle.getString("Ticket_isSE");
        string_isOpenByAdmin = getBundle.getString("Ticket_isAdmin");

        int_ticketstatus = Integer.parseInt(string_ticketstatus);
        textview_TicketNo = (TextView)findViewById(R.id.textview_ticketno);
        textview_datetime = (TextView)findViewById(R.id.textview_ticketdatetime);

        try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("textview_summary"))
                {
                    string_TV_SUMMARY = langauge;
                }
                if(Control.equals("textview_reassign"))
                {
                    string_REASSIGN = langauge;
                }
                if(Control.equals("textview_history"))
                {
                    string_HISTORY = langauge;
                }
                if(Control.equals("progress_statusloading"))
                {
                    string_PROGRESS_STATUSLOADING = langauge;
                }
                if(Control.equals("progress_assigneeloading"))
                {
                    string_PROGRESS_ASSIGNEELOADING = langauge;
                }
                if(Control.equals("textview_assignedto"))
                {
                    string_ASSIGNEDTO = langauge;
                }
                if(Control.equals("textview_availableassignee"))
                {
                    string_AVAILABLEASSIGNEE = langauge;
                }
                if(Control.equals("edittext_hint_remarks"))
                {
                    string_HINTREMARKS = langauge;
                }
                if(Control.equals("button_update"))
                {
                    string_BUTTON_UPDATE = langauge;
                }
                if(Control.equals("timer_hrs"))
                {
                    string_TV_TIMEHRS = langauge;
                }
                if(Control.equals("textview_cantreassign"))
                {
                    string_CANTREASSIGN = langauge;
                    Log.i("LOG","Can't Assign:"+string_CANTREASSIGN);
                }
                if(Control.equals("textview_status"))
                {
                    string_TV_STATUS = langauge;
                }
                if(Control.equals("textview_datetime"))
                {
                    string_TV_DATEandTIME = langauge;
                }
                if(Control.equals("textview_description"))
                {
                    string_TV_DESCRIPTION = langauge;
                }
                if(Control.equals("textview_ticketdetails"))
                {
                    string_TV_TICKETDETAILS = langauge;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setupViewPager(viewPager);
        prefs.setBoolean("OpenNotification",false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                Intent i = new Intent(TicketDetails.this, NewDashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        LoadTicketDetails();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Summary(), string_TV_SUMMARY);
        adapter.addFragment(new Fragment_ReAssign(), string_REASSIGN);
        adapter.addFragment(new Fragment_History(), string_HISTORY);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
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
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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

            View rootView = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

            button_update = (Button)rootView.findViewById(R.id.button_update);
            textview_location = (TextView)rootView.findViewById(R.id.textview_location);
            textview_building = (TextView)rootView.findViewById(R.id.textview_building);
            textview_floor = (TextView)rootView.findViewById(R.id.textview_floor);
            textview_wing = (TextView)rootView.findViewById(R.id.textview_wing);
            textview_RequestorName = (TextView)rootView.findViewById(R.id.textview_requestedby);
            textview_reqhours = (TextView)rootView.findViewById(R.id.textview_reqhours);
            textview_Category = (TextView)rootView.findViewById(R.id.textview_category);
            textview_Subject = (TextView)rootView.findViewById(R.id.textview_subject);
            textview_Status = (TextView)rootView.findViewById(R.id.textview_status1);
            textview_Priority = (TextView)rootView.findViewById(R.id.textview_priority);
            textview_assignedtouser = (TextView)rootView.findViewById(R.id.textview_assignedtouser);
            editText_remarks = (EditText)rootView.findViewById(R.id.edittext_remarks);
            textview_AssignedTo = (TextView)rootView.findViewById(R.id.textview_assignedto);
            imageview_complaintimage = (ImageView)rootView.findViewById(R.id.imageview_complaintimage);
            imageview_complaintfullimage = (ImageView)rootView.findViewById(R.id.imageview_complaintfullimage);
            rl_imageview = (ScrollView)rootView.findViewById(R.id.rl_imageview);
            imageview_close = (ImageView)rootView.findViewById(R.id.imageview_close);
            imageview_updateimage = (ImageView)rootView.findViewById(R.id.imageview_updateimage);

            TextView textview_requestedby_label = (TextView)rootView.findViewById(R.id.textview_requestedby_label);
            TextView textview_assignee_label = (TextView)rootView.findViewById(R.id.textview_assignee_label);
            TextView textview_assignedtouser_label = (TextView)rootView.findViewById(R.id.textview_assignedtouser_label);
            textview_hrs = (TextView)rootView.findViewById(R.id.textview_hrs);

            radiogroup_status = (RadioGroup)rootView.findViewById(R.id.radiogroup_status);
            spinner_assignee = (Spinner)rootView.findViewById(R.id.spinner_assigne);
            imageview_status = (ImageView)rootView.findViewById(R.id.imageView_status);
            rl_status_bg = (RelativeLayout)rootView.findViewById(R.id.rl_status_bg);
            rl_priority_bg = (RelativeLayout)rootView.findViewById(R.id.rl_priority_bg);
            textview_assignee_label.setText(string_AVAILABLEASSIGNEE);
            textview_assignedtouser_label.setText(string_ASSIGNEDTO);
            editText_remarks.setHint(string_HINTREMARKS);
            button_update.setText(string_BUTTON_UPDATE);

            imageview_updateimage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (checkPermission() == true)
                    {
                        selectImage();
                    }

                    else
                    {
                        requestPermission();
                    }

                }
            });

            RelativeLayout rl_assigneestatus = (RelativeLayout)rootView.findViewById(R.id.rl_assigneestatus);
            if(string_isclosed.equals("true"))
            {
                rl_assigneestatus.setVisibility(View.GONE);
                button_update.setVisibility(View.GONE);
            }

            //new WebService_Ticketdetails().execute();

           // new WebService_Status().execute();

            button_update.setEnabled(false);
            button_update.setText(string_BUTTON_UPDATE);
            button_update.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    string_remarks = editText_remarks.getText().toString();
                    new WebService_UpdateComplaints().execute();
                }
            });


            radiogroup_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++)
                    {
                        RadioButton btn = (RadioButton) group.getChildAt(x);
                        if (btn.getId() == checkedId)
                        {
                            System.out.println(btn.getText().toString());
                            string_statusid = ""+checkedId;
                            Log.i("", "" + btn.getText().toString() + " ID:" + btn.getId());
                        }
                    }
                    button_update.setBackgroundColor(Color.parseColor("#5cb85c"));
                    button_update.setEnabled(true);
                }
            });


            spinner_assignee.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int arg2, long arg3)
                        {
                            if(arg2 != -1 | arg2 > -1)
                            {
                                String string_assigneename = spinner_assignee.getSelectedItem().toString();
                                String string_assigneeid = ""+GetSet_AssigneList.get(arg2).getInt_Item_1();
                                string_serviceenggxml = "<DocumentElement>\n" +
                                        "<ServiceEngineerIDs>\n" +
                                        "<ServiceEngineerID>"+string_assigneeid+"</ServiceEngineerID>\n" +
                                        "</ServiceEngineerIDs>\n" +
                                        "</DocumentElement>";
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    }
            );

            return rootView;
        }

        public void selectImage() {

            final CharSequence[] chararrOptions = {string_DIALOG_MENU_TAKEPHOTO, string_DIALOG_MENU_CHOOSEIMAGE,string_DIALOG_MENU_CHOOSEFILES, string_DIALOG_MENU_CANCEL};
            View v = null;

            strImagePath = Environment.getExternalStorageDirectory() + "/i2i_ifazidesk/Images";

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT);
            //builder.setTitle("Add Photo!");
            builder.setItems(chararrOptions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item)
                {
                    if (chararrOptions[item].equals(string_DIALOG_MENU_TAKEPHOTO))
                    {
                        strImagename = ""+System.currentTimeMillis()+".jpg";
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(strImagePath, strImagename);
                        System.out.println("IMAGE PATH : " + strImagePath + strImagename);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT , Uri.fromFile(f));
                        (getActivity()).startActivityForResult(intent, REQUEST_CAMERA);
                    }
                    else if (chararrOptions[item].equals(string_DIALOG_MENU_CHOOSEIMAGE))
                    {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        (getActivity()).startActivityForResult(Intent.createChooser(intent, string_DIALOG_MENU_CHOOSEIMAGE), SELECT_FILE);

                    }
                    else if (chararrOptions[item].equals(string_DIALOG_MENU_CHOOSEFILES))
                    {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("file/*");
                        (getActivity()).startActivityForResult(Intent.createChooser(intent, string_DIALOG_MENU_CHOOSEFILES), SELECT_DOC);
                    } else if (chararrOptions[item].equals(string_DIALOG_MENU_CANCEL))
                    {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        }


        public class WebService_Ticketdetails extends AsyncTask<String, String, String> {
            String result_dashboarddata = "";
            ProgressDialog dialog;
            TableRow row_dept;
            int position_deptid = 0;
            JSONArray jsonArray_deptwise;
            LinearLayout view_dept;
            @Override
            protected void onPreExecute()
            {
                dialog = ProgressDialog.show(getActivity(), "",string_PROGRESS_ASSIGNEELOADING);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Hashtable<String, String> ht = new Hashtable<String, String>();
                //Keys String Array
                ht.put("complaintId", string_ticketid);

                String[] keys = { "complaintId"};
                String[] values = {string_ticketid};

                //Put key and value into hashTable
                ht.clear();
                for (int i = 0; i < keys.length; i++)
                {
                    ht.put(keys[i], values[i]);
                    Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
                }

                String result = "";
                try {
                    result = new NetworkCall(getActivity()).postDataToSOAPService(ht, "GetTicketHistory");
                    Log.i("data:","GetTicketHistory:"+result);
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
                            JSONArray jsonarray_ticketdetails = new JSONArray(result);
                            if(jsonarray_ticketdetails.length() > 0)
                            {
                                string_companyid = ""+jsonarray_ticketdetails.getJSONObject(0).getInt("CompanyID");
                                string_locationid= ""+jsonarray_ticketdetails.getJSONObject(0).getInt("locationID");
                                int ComplaintId = jsonarray_ticketdetails.getJSONObject(0).getInt("ComplaintId");
                                int Statusid = jsonarray_ticketdetails.getJSONObject(0).getInt("Statusid");
                                string_currentstatusid = ""+Statusid;
                                String TicketNo = jsonarray_ticketdetails.getJSONObject(0).getString("TicketNo");
                                String Category = jsonarray_ticketdetails.getJSONObject(0).getString("Category");
                                String Subject = jsonarray_ticketdetails.getJSONObject(0).getString("Subject");
                                String Status = jsonarray_ticketdetails.getJSONObject(0).getString("Status");
                                String Priority = jsonarray_ticketdetails.getJSONObject(0).getString("Priority");
                                RequestorName = jsonarray_ticketdetails.getJSONObject(0).getString("RequestorName");
                                String AssignedTo = jsonarray_ticketdetails.getJSONObject(0).getString("AssignedTo");
                                String RequestDate = jsonarray_ticketdetails.getJSONObject(0).getString("RequestDate");
                                String RequestTime = jsonarray_ticketdetails.getJSONObject(0).getString("RequestTime");
                                String TicketResponseTime = jsonarray_ticketdetails.getJSONObject(0).getString("TicketResponseTime");
                                String ResponseTime = jsonarray_ticketdetails.getJSONObject(0).getString("ResponseTime");
                                String ColorCode = jsonarray_ticketdetails.getJSONObject(0).getString("ColorCode");
                                String ImageLink = jsonarray_ticketdetails.getJSONObject(0).getString("ImageLink");
                                string_attachmentlink = ImageLink;
                                //"FileType": ".pdf",
                                String FileType = jsonarray_ticketdetails.getJSONObject(0).getString("FileType");

                                String color[] = ColorCode.split(" - ");
                                ColorCode = color[1];
                                imageview_status.setColorFilter(Color.parseColor(ColorCode));
                                rl_status_bg.setBackgroundColor(Color.parseColor(ColorCode));

                                if(ImageLink.length() > 0)
                                {
                                    if(FileType.equals(".jpg")||FileType.equals(".jpeg")||FileType.equals(".png"))
                                    {

                                        if(ImageLink.length() > 5)
                                        {
                                            Picasso.with(TicketDetails.this).load(ImageLink).into(imageview_complaintimage);
                                            Picasso.with(TicketDetails.this).load(ImageLink).into(imageview_complaintfullimage);
                                            rl_imageview.setVisibility(View.GONE);
                                            imageview_complaintfullimage.setVisibility(View.GONE);
                                        }

                                        imageview_complaintimage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //However it is you load your images
                                                if(rl_imageview.getVisibility() == View.GONE)
                                                {
                                                    rl_imageview.setVisibility(View.VISIBLE);
                                                    imageview_complaintfullimage.setVisibility(View.VISIBLE);
                                                }

                                            }
                                        });
                                        imageview_close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //However it is you load your images
                                                if(rl_imageview.getVisibility() == View.VISIBLE)
                                                {
                                                    rl_imageview.setVisibility(View.GONE);
                                                    imageview_complaintfullimage.setVisibility(View.GONE);
                                                }

                                            }
                                        });
                                    }
                                    else
                                    {
                                        imageview_complaintimage.setImageResource(R.drawable.image_icon_attachement);
                                        imageview_complaintimage.setPadding(10,10,10,10);
                                        imageview_complaintimage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //However it is you load your images
                                                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(string_attachmentlink));
                                                startActivity(browserIntent);

                                            }
                                        });
                                    }
                                }


                                String ackwardRipOff = TicketResponseTime.replace("/Date(", "").replace(")/", "");
                                int idx1 = TicketResponseTime.indexOf("(");
                                int idx2 = TicketResponseTime.indexOf(")");
                                String s = TicketResponseTime.substring(idx1+1, idx2);
                                long l = Long.valueOf(s);
                                Date createdOn = new Date(l);
                                timeInMillis = Long.valueOf(ackwardRipOff);
                                textview_reqhours.setText(""+timeInMillis);

                                Calendar rightNow = Calendar.getInstance();
                                long long_systemtime = System.currentTimeMillis();//rightNow.getTimeInMillis();


                                SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                                SimpleDateFormat dateformat2 = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                                dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                Date date1 = new Date(timeInMillis);
                                System.out.println(dateformat.format(date1));
                                Date date2 = new Date(long_systemtime);
                                System.out.println(dateformat2.format(date2));
                                long diffinMilliseconds = date1.getTime() - date2.getTime();

                                SimpleDateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");

                                try {
                                    Calendar cal = Calendar.getInstance();
                                    Date d1 = dateFormat.parse(ResponseTime);
                                    Date d2 = cal.getTime();
                                    System.out.println("DATE : "+d1+" "+d2);
                                    System.out.println("Formated :"+dateFormat.format(d1)+" "+dateFormat.format(d2));

                                    diffinMilliseconds = d1.getTime() - d2.getTime();
                                    Log.i("DAte1"," Date1: "+date1.getTime());
                                    Log.i("DAte2"," Date2: "+date2.getTime());
                                    Log.i("DiffinMS"," Milliseconds: "+diffinMilliseconds);
                                }
                                catch(Exception e) {
                                    System.out.println("Excep"+e);
                                }

                                if(string_isclosed.equals("true"))
                                {
                                    textview_reqhours.setText("Time Over");
                                    textview_reqhours.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.bg_grey));

                                }

                                else {
                                    new CountDownTimer(diffinMilliseconds, 1000) {

                                        public void onTick(long millisUntilFinished) {
                                            if(millisUntilFinished < 1800000)
                                            {
                                                textview_reqhours.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.bg_orange));
                                            }
                                            calendar_countdown = Calendar.getInstance();
                                            calendar_countdown.setTimeInMillis(millisUntilFinished);
                                            DateFormat df = new SimpleDateFormat("HH:mm:ss");
                                            df.setTimeZone(TimeZone.getTimeZone("UTC"));
                                            String date = df.format(calendar_countdown.getTime());
                                            textview_reqhours.setText(date);
                                        }

                                        public void onFinish() {
                                            textview_reqhours.setText("Time Over\nEsc L1");
                                            textview_reqhours.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.bg_red));
                                        }

                                    }.start();
                                }
                                tckt_location = jsonarray_ticketdetails.getJSONObject(0).getString("location");
                                tckt_building = jsonarray_ticketdetails.getJSONObject(0).getString("building");
                                tckt_floor = jsonarray_ticketdetails.getJSONObject(0).getString("floor");
                                tckt_wing = jsonarray_ticketdetails.getJSONObject(0).getString("wing");

                                String inputPattern = "MMM dd yyyy";
                                String outputPattern = "dd/MM/yyyy";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
                                Date date = null;
                                String str_date = null;
                                try {
                                    date = inputFormat.parse(RequestDate);
                                    str_date = outputFormat.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                jsonarray_history_temp = jsonarray_ticketdetails.getJSONObject(0).getJSONArray("lsthistory");
                                for(int k=0;k<jsonarray_history_temp.length();k++)
                                {

                                    TableRow row_history = (TableRow) LayoutInflater.from(TicketDetails.this).inflate(R.layout.layout_history, null);
                                    String StatusDate = jsonarray_history_temp.getJSONObject(k).getString("StatusDate");
                                    String StatusTime = jsonarray_history_temp.getJSONObject(k).getString("StatusTime");
                                    String location = jsonarray_history_temp.getJSONObject(k).getString("location");
                                    String Username = jsonarray_history_temp.getJSONObject(k).getString("Username");
                                    String description = jsonarray_history_temp.getJSONObject(k).getString("description");

                                    TextView textView_datetime = (TextView) row_history.findViewById(R.id.textview_historydatetime);
                                    TextView textview_locationandperson = (TextView) row_history.findViewById(R.id.textview_locationandperson);
                                    TextView textview_desc = (TextView) row_history.findViewById(R.id.textview_desc);
                                    textview_locationandperson.setText(Username+" - "+location);
                                    textview_desc.setText(description);

                                    String str_status_date = null;
                                    try {
                                        date = inputFormat.parse(StatusDate);
                                        str_status_date = outputFormat.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    textView_datetime.setText(str_status_date+"\n"+StatusTime);
                                }


                                textview_location.setText(tckt_location);
                                textview_building.setText(tckt_building);
                                textview_floor.setText(tckt_floor);
                                textview_wing.setText(tckt_wing);
                                textview_RequestorName.setText(RequestorName);

                                textview_reassign_location.setText(tckt_location);
                                textview_reassign_building.setText(tckt_building);
                                textview_reassign_floor.setText(tckt_floor);
                                textview_reassign_wing.setText(tckt_wing);
                                textview_reassign_RequestorName.setText(RequestorName);

                                textview_TicketNo.setText(TicketNo);
                                textview_Category.setText(Category);
                                textview_Subject.setText(Subject);
                                textview_Status.setText(Status);
                                textview_Priority.setText(Priority);

                                textview_AssignedTo.setText(AssignedTo);
                                textview_datetime.setText(str_date+" "+RequestTime);
                            }
                            if(dialog.isShowing())
                            {
                                dialog.dismiss();
                                new WebService_Status().execute();
                            }

                        } catch (JSONException e) {
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }

            }
        }

        public class WebService_Status extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            @Override
            protected void onPreExecute()
            {
                dialog = ProgressDialog.show(getActivity(), "",string_PROGRESS_STATUSLOADING);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Hashtable<String, String> ht = new Hashtable<String, String>();
                //Keys String Array
                ht.put("statusid", string_currentstatusid);

                String[] keys = { "statusid"};
                String[] values = {string_currentstatusid};


                //Put key and value into hashTable
                ht.clear();
                for (int i = 0; i < keys.length; i++)
                {
                    ht.put(keys[i], values[i]);
                    Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
                }

                String result = "";
                try {
                    result = new NetworkCall(getActivity()).postDataToSOAPService(ht, "GetMappedStatus");
                    Log.i("data:","GetMappedStatus:"+result);
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
                            JSONArray jsonarray_statusdetails = new JSONArray(result);
                            if(jsonarray_statusdetails.length() > 0)
                            {
                                RadioButton[] rb = new RadioButton[jsonarray_statusdetails.length()];
                                for(int k=0;k<jsonarray_statusdetails.length();k++)
                                {
                                    int statusid = jsonarray_statusdetails.getJSONObject(k).getInt("statusid");
                                    String status = jsonarray_statusdetails.getJSONObject(k).getString("status");
                                    rb[k] = new RadioButton(TicketDetails.this);
                                    rb[k].setButtonDrawable(null);
                                    rb[k].setBackgroundResource(R.drawable.radiobuttonbg_1);
                                    rb[k].setButtonDrawable(android.R.color.transparent);
                                    rb[k].setTextColor(getResources().getColorStateList(R.color.radiobutton_text));
                                    rb[k].setText(status);
                                    rb[k].setPadding(22,22,22,22);
                                    rb[k].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                                    rb[k].setId(statusid);
                                    rb[k].setTextSize(14);
                                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.gravity = Gravity.CENTER_VERTICAL;
                                    params.setMargins(5, 5, 5, 5);
                                    Log.i("status id:" + statusid, " status:" + status);
                                    radiogroup_status.addView(rb[k], params);
                                }
                                if(dialog.isShowing())
                                {
                                    dialog.dismiss();
                                    new WebService_Assignee().execute();
                                }
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Sorry, No Status", 3000).show();
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Sorry, No Status", 3000).show();
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Sorry, No Status", 3000).show();
                }
            }
        }




        public class WebService_Assignee extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            @Override
            protected void onPreExecute()
            {
                dialog = ProgressDialog.show(getActivity(), "", string_PROGRESS_ASSIGNEELOADING);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Hashtable<String, String> ht = new Hashtable<String, String>();
                //Keys String Array
                ht.put("complaintId", string_ticketid);

                String[] keys = { "complaintId"};
                String[] values = {string_ticketid};

                //Put key and value into hashTable
                ht.clear();
                for (int i = 0; i < keys.length; i++)
                {
                    ht.put(keys[i], values[i]);
                    Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
                }

                String result = "";
                try {
                    result = new NetworkCall(TicketDetails.this).postDataToSOAPService(ht, "GetAssigneeDetails");
                    Log.i("data:","GetAssigneeDetails:"+result);
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
                            JSONArray jsonarray_ticketdetails = new JSONArray(result);
                            if(jsonarray_ticketdetails.length() > 0)
                            {

                                JSONArray jsonarray_lstprovider_history = jsonarray_ticketdetails.getJSONObject(0).getJSONArray("lstprovider");
                                int temp_userid = jsonarray_lstprovider_history.getJSONObject(0).getInt("userid");
                                String temp_name = jsonarray_lstprovider_history.getJSONObject(0).getString("name");
                                textview_assignedtouser.setText(temp_name);
                                textview_reassign_assignedto.setText(temp_name);

                                JSONArray jsonarray_availableassignee = jsonarray_ticketdetails.getJSONObject(0).getJSONArray("lstassignee");
                                GetSet_AssigneList.clear();

                                if(string_isOpenBySE.equals("true"))
                                {
                                    for(int k=0;k<jsonarray_availableassignee.length();k++)
                                    {
                                        int userid = jsonarray_availableassignee.getJSONObject(k).getInt("userid");
                                        String name = jsonarray_availableassignee.getJSONObject(k).getString("name");
                                        if(name.equals(temp_name))
                                        {
                                            GetSet assignee_item = new GetSet();
                                            assignee_item.setInt_Item_1(userid);
                                            assignee_item.setString_Item_1(name);
                                            GetSet_AssigneList.add(assignee_item);
                                        }
                                    }
                                }
                                else
                                {
                                    for(int k=0;k<jsonarray_availableassignee.length();k++)
                                    {
                                        int userid = jsonarray_availableassignee.getJSONObject(k).getInt("userid");
                                        String name = jsonarray_availableassignee.getJSONObject(k).getString("name");
                                        GetSet assignee_item = new GetSet();
                                        assignee_item.setInt_Item_1(userid);
                                        assignee_item.setString_Item_1(name);
                                        GetSet_AssigneList.add(assignee_item);
                                    }
                                }
                                ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(getApplicationContext(), R.layout.layout_spinner_black, GetSet_AssigneList);
                                adapter.setDropDownViewResource(R.layout.layout_spinner_textview);
                                spinner_assignee.setAdapter(adapter);
                                spinner_assignee.setSelection(0);


                                if(GetSet_AssigneList.size() > 0)
                                {
                                    ArrayAdapter<GetSet> adapter_reassign = new ArrayAdapter<GetSet>(getApplicationContext(), R.layout.layout_spinner_black, GetSet_AssigneList);
                                    adapter_reassign.setDropDownViewResource(R.layout.layout_spinner_textview);
                                    spinner_reassignee.setAdapter(adapter_reassign);
                                    spinner_reassignee.setSelection(0);
                                }
                            }
                            if(dialog.isShowing())
                            {
                                dialog.dismiss();
                                //Toast.makeText(getApplicationContext(), "Sorry, No Data", 3000).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Sorry, No Assignee", 3000).show();
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), "Sorry, No Assignee", 3000).show();
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Sorry, No Assignee", 3000).show();
                }
            }
        }


        public class WebService_UpdateComplaints extends AsyncTask<String, String, String> {
            //ProgressDialog dialog;
            @Override
            protected void onPreExecute()
            {
                updatecomplaintdialog = ProgressDialog.show(getActivity(), "Updating Ticket", "Updating...\nPlease Wait");
                updatecomplaintdialog.setCancelable(false);
                updatecomplaintdialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Hashtable<String, String> ht = new Hashtable<String, String>();
                //Keys String Array
                ht.put("statusid", string_currentstatusid);


                String[] keys = { "complaintId","serviceengineerxml","description","companyid","locationid","uid","statusid"};
                String[] values = {string_ticketid,string_serviceenggxml,string_remarks,string_companyid,string_locationid,string_userid,string_statusid};

                //Put key and value into hashTable
                ht.clear();
                for (int i = 0; i < keys.length; i++)
                {
                    ht.put(keys[i], values[i]);
                    Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
                }

                String result = "";
                try {
                    result = new NetworkCall(getActivity()).postDataToSOAPService(ht, "UpdateComplaints");
                    Log.i("data:","UpdateComplaints:"+result);
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

                            JSONObject jsonObject_statusupdate = new JSONObject(result);
                            boolean Status = jsonObject_statusupdate.getBoolean("Status");
                            if(Status == true)
                            {
                                if(temp.length() > 10)
                                {
                                    new WebService_UpdateImage().execute();
                                }
                                else
                                {
                                    updatecomplaintdialog.dismiss();
                                    Toast.makeText(getActivity(), "Ticket Status Updated Successfully!", 3000).show();
                                    Intent ii9i=new Intent(TicketDetails.this,NewDashboard.class);
                                    ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(ii9i);
                                }
                            }
                            else
                            {
                                updatecomplaintdialog.dismiss();
                                Toast.makeText(getActivity(), "Ticket Status Update failed!", 3000).show();
                            }
                        } catch (JSONException e) {
                            updatecomplaintdialog.dismiss();
                            Toast.makeText(getActivity(), "Ticket Status Update failed!", 3000).show();
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        updatecomplaintdialog.dismiss();
                        Toast.makeText(getActivity(), "Ticket Status Update failed!", 3000).show();
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    updatecomplaintdialog.dismiss();
                    Toast.makeText(getActivity(), "Ticket Status Update failed!", 3000).show();
                }

            }
        }

    }



    public class Fragment_ReAssign extends Fragment{

        public Fragment_ReAssign() {
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

            View rootView = inflater.inflate(R.layout.fragment_ticket_reassign, container, false);
            RelativeLayout rl_assignee = (RelativeLayout)rootView.findViewById(R.id.rl_assignee);
            Button button_reassign = (Button)rootView.findViewById(R.id.button_update);
            spinner_reassignee = (Spinner)rootView.findViewById(R.id.spinner_reassigne);
            textview_reassign_location = (TextView)rootView.findViewById(R.id.textview_location);
            textview_reassign_building = (TextView)rootView.findViewById(R.id.textview_building);
            textview_reassign_floor = (TextView)rootView.findViewById(R.id.textview_floor);
            textview_reassign_wing = (TextView)rootView.findViewById(R.id.textview_wing);
            textview_reassign_assignedto = (TextView)rootView.findViewById(R.id.textview_assignedtouser);
            TextView textview_assignedtouser_label = (TextView)rootView.findViewById(R.id.textview_assignedtouser_label);
            TextView textview_requestedby_label = (TextView)rootView.findViewById(R.id.textview_requestedby_label);
            TextView textview_assignee_label = (TextView)rootView.findViewById(R.id.textview_assignee_label);

            textview_reassign_RequestorName = (TextView)rootView.findViewById(R.id.textview_requestedby);
            rl_reassigndisabled = (RelativeLayout) rootView.findViewById(R.id.rl_reassigndisable);
            TextView textview_reassigndisabled = (TextView)rootView.findViewById(R.id.textview_reassigndisabled);
            ImageView imageview_reassign = (ImageView) rootView.findViewById(R.id.imageview_reassign);

            textview_assignedtouser_label.setText(string_ASSIGNEDTO);
            //textview_requestedby_label.setText(string_);
            textview_assignee_label.setText(string_AVAILABLEASSIGNEE);
            if(string_isclosed.equals("true"))
            {
                rl_assignee.setVisibility(View.GONE);
                button_reassign.setVisibility(View.GONE);
                rl_reassigndisabled.setVisibility(View.VISIBLE);
                imageview_reassign.setImageResource(R.drawable.image_ticket_closed);
                Log.i("Log2","Can't:"+string_CANTREASSIGN);
                textview_reassigndisabled.setText(string_CANTREASSIGN);
            }else
            {
                if( int_ticketstatus < 2)
                {
                    rl_assignee.setVisibility(View.GONE);
                    button_reassign.setVisibility(View.GONE);
                    rl_reassigndisabled.setVisibility(View.VISIBLE);
                    textview_reassigndisabled.setText(string_CANTREASSIGN);
                }else {
                    rl_assignee.setVisibility(View.VISIBLE);
                    button_reassign.setVisibility(View.VISIBLE);
                    rl_reassigndisabled.setVisibility(View.GONE);
                    button_reassign.setBackgroundColor(Color.parseColor("#5cb85c"));
                    button_reassign.setVisibility(View.VISIBLE);
                    button_reassign.setEnabled(true);
                }
            }

            textview_reassign_location.setText(tckt_location);
            textview_reassign_building.setText(tckt_building);
            textview_reassign_floor.setText(tckt_floor);
            textview_reassign_wing.setText(tckt_wing);
            textview_reassign_RequestorName.setText(RequestorName);

            button_reassign.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new WebService_Reassign().execute();
                }
            });

            spinner_reassignee.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int arg2, long arg3)
                        {
                            if(arg2 != -1 | arg2 > -1)
                            {
                                String string_assigneename = spinner_assignee.getSelectedItem().toString();
                                string_reassigneeid = ""+GetSet_AssigneList.get(arg2).getInt_Item_1();
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    }
            );

            return rootView;
        }


        public class WebService_Reassign extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            @Override
            protected void onPreExecute()
            {
                dialog = ProgressDialog.show(getActivity(), "Reassigning", "Please Wait...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Hashtable<String, String> ht = new Hashtable<String, String>();
                //Keys String Array
                ht.put("statusid", string_currentstatusid);


                String[] keys = { "complaintId","assigneeid","uid"};
                String[] values = {string_ticketid,string_reassigneeid,string_userid};

                //Put key and value into hashTable
                ht.clear();
                for (int i = 0; i < keys.length; i++)
                {
                    ht.put(keys[i], values[i]);
                    Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
                }

                String result = "";
                try {
                    result = new NetworkCall(TicketDetails.this).postDataToSOAPService(ht, "UpdateReAssign");
                    Log.i("data:","UpdateReAssign:"+result);
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
                            dialog.dismiss();
                            JSONObject jsonObject_statusupdate = new JSONObject(result);
                            boolean Status = jsonObject_statusupdate.getBoolean("Status");
                            if(Status == true)
                            {
                                Toast.makeText(getApplicationContext(), "Ticket Status Updated Successfully!", 3000).show();
                                Intent ii9i=new Intent(TicketDetails.this,NewDashboard.class);
                                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(ii9i);
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Ticket Status Update failed!", 3000).show();
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Ticket Status Update failed!", 3000).show();
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Ticket Status Update failed!", 3000).show();
                }
            }
        }

    }


    public class Fragment_History extends Fragment{

        public Fragment_History() {
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
            View rootView = inflater.inflate(R.layout.fragment_ticket_history, container, false);

            String inputPattern = "MMM dd yyyy";
            String outputPattern = "dd/MM/yyyy";
            TextView textview_historystatus_label = (TextView)rootView.findViewById(R.id.textview_historystatus_label);
            TextView textview_historydatetime_label = (TextView)rootView.findViewById(R.id.textview_historydatetime_label);
            TextView textview_locationandperson_label = (TextView)rootView.findViewById(R.id.textview_locationandperson_label);

            textview_historystatus_label.setText(string_TV_STATUS);
            textview_historydatetime_label.setText(string_TV_DATEandTIME);
            textview_locationandperson_label.setText(string_TV_DESCRIPTION);
            SimpleDateFormat inputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
            SimpleDateFormat outputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
            Date date = null;
            tableLayout_history = (TableLayout) rootView.findViewById(R.id.table_history);
            //tableLayout_history.removeAllViews();
            //new WebService_TicketHistory().execute();
            return rootView;
        }

        public class WebService_TicketHistory extends AsyncTask<String, String, String> {
            String result_dashboarddata = "";
            ProgressDialog dialog;
            TableRow row_dept;
            int position_deptid = 0;
            JSONArray jsonArray_deptwise;
            LinearLayout view_dept;
            @Override
            protected void onPreExecute()
            {
                dialog = ProgressDialog.show(getActivity(), "Ticket Details", "Loading...\nPlease Wait");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Hashtable<String, String> ht = new Hashtable<String, String>();
                //Keys String Array
                ht.put("complaintId", string_ticketid);

                String[] keys = { "complaintId"};
                String[] values = {string_ticketid};

                //Put key and value into hashTable
                ht.clear();
                for (int i = 0; i < keys.length; i++)
                {
                    ht.put(keys[i], values[i]);
                    Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
                }

                String result = "";
                try {
                    result = new NetworkCall(getActivity()).postDataToSOAPService(ht, "GetTicketHistory");
                    Log.i("data:","GetTicketHistory:"+result);
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
                            JSONArray jsonarray_ticketdetails = new JSONArray(result);
                            if(jsonarray_ticketdetails.length() > 0)
                            {
                                int ComplaintId = jsonarray_ticketdetails.getJSONObject(0).getInt("ComplaintId");
                                int Statusid = jsonarray_ticketdetails.getJSONObject(0).getInt("Statusid");
                                string_currentstatusid = ""+Statusid;
                                String TicketNo = jsonarray_ticketdetails.getJSONObject(0).getString("TicketNo");
                                String Category = jsonarray_ticketdetails.getJSONObject(0).getString("Category");
                                String Subject = jsonarray_ticketdetails.getJSONObject(0).getString("Subject");
                                String Status = jsonarray_ticketdetails.getJSONObject(0).getString("Status");
                                String Priority = jsonarray_ticketdetails.getJSONObject(0).getString("Priority");
                                RequestorName = jsonarray_ticketdetails.getJSONObject(0).getString("RequestorName");
                                String AssignedTo = jsonarray_ticketdetails.getJSONObject(0).getString("AssignedTo");
                                String RequestDate = jsonarray_ticketdetails.getJSONObject(0).getString("RequestDate");
                                String RequestTime = jsonarray_ticketdetails.getJSONObject(0).getString("RequestTime");



                                String inputPattern = "MMM dd yyyy";
                                String outputPattern = "dd/MM/yyyy";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
                                Date date = null;
                                String str_date = null;
                                try {
                                    date = inputFormat.parse(RequestDate);
                                    str_date = outputFormat.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                jsonarray_history_temp = jsonarray_ticketdetails.getJSONObject(0).getJSONArray("lsthistory");
                                for(int k=0;k<jsonarray_history_temp.length();k++)
                                {

                                    TableRow row_history = (TableRow) LayoutInflater.from(TicketDetails.this).inflate(R.layout.layout_history, null);
                                    String StatusDate = jsonarray_history_temp.getJSONObject(k).getString("StatusDate");
                                    String StatusTime = jsonarray_history_temp.getJSONObject(k).getString("StatusTime");
                                    String location = jsonarray_history_temp.getJSONObject(k).getString("location");
                                    String Username = jsonarray_history_temp.getJSONObject(k).getString("Username");
                                    String description = jsonarray_history_temp.getJSONObject(k).getString("description");
                                    String temp_Status = jsonarray_history_temp.getJSONObject(k).getString("Status");
                                    String Colorcode = jsonarray_history_temp.getJSONObject(k).getString("Colorcode");

                                    TextView textView_datetime = (TextView) row_history.findViewById(R.id.textview_historydatetime);
                                    TextView textview_locationandperson = (TextView) row_history.findViewById(R.id.textview_locationandperson);
                                    TextView textview_desc = (TextView) row_history.findViewById(R.id.textview_desc);
                                    TextView textview_status = (TextView) row_history.findViewById(R.id.textview_historystatus);
                                    textview_locationandperson.setText(Username+" - "+location);
                                    textview_desc.setText(description);
                                    textview_status.setText(temp_Status);

                                    String status_color;
                                    String color[] = Colorcode.split(" - ");
                                    status_color = color[1];
                                    GradientDrawable bgShape = (GradientDrawable)textview_status.getBackground();
                                    textview_status.setTextColor(Color.parseColor(status_color));

                                    String str_status_date = null;
                                    try {
                                        date = inputFormat.parse(StatusDate);
                                        str_status_date = outputFormat.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    textView_datetime.setText(str_status_date+"\n"+StatusTime);
                                    tableLayout_history.addView(row_history);
                                }

                            }
                            if(dialog.isShowing())
                            {
                                dialog.dismiss();
                            }

                        } catch (JSONException e) {
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();

                            }
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }

            }
        }
    }

    @Override
    public void onBackPressed() {

        if (imageview_complaintfullimage.getVisibility() == View.VISIBLE)
        {
            imageview_complaintfullimage.setVisibility(View.GONE);
            rl_imageview.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
            Intent i = new Intent(TicketDetails.this, NewDashboard.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
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

        ActivityCompat.requestPermissions(this, new String[]{CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    storageAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted)
                    {

                    }
                    if (cameraAccepted)
                    {

                    }
                }
                break;

        }
    }






    protected void onActivityResult(int intRequestCode, int intResultCode, Intent intentData)
    {
        switch (intRequestCode)
        {
            case SELECT_DOC:
                if (intResultCode == RESULT_OK)
                {
                    if (intentData == null) {
                        //Display an error
                        return;
                    }
                    else
                    {
                        Uri uri = intentData.getData();
                        try {
                            File file_docs = new File(uri.getPath());
                            //File file_docs = new File(uri.getPath());
                            int size = (int) file_docs.length();

                            ContentResolver cR = getApplicationContext().getContentResolver();
                            string_filetype = MimeTypeMap.getFileExtensionFromUrl(uri.toString());

                            byte[] bytes = new byte[size];
                            try {
                                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file_docs));
                                buf.read(bytes, 0, bytes.length);
                                buf.close();
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            temp = Base64.encodeToString(bytes, Base64.DEFAULT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case SELECT_FILE:
                if (intResultCode == RESULT_OK)
                {
                    if (intentData == null) {
                        return;
                    }
                    else
                    {
                        Uri uri = intentData.getData();
                        try {
                            ContentResolver cR = getApplicationContext().getContentResolver();
                            string_filetype = "png";

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            Bitmap scaledBitmap = scaleDown(bitmap, 1024, true);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            scaledBitmap.compress(
                                    Bitmap.CompressFormat.JPEG, 90, baos);
                            byte[] b = baos.toByteArray();
                            temp = "";
                            temp = Base64.encodeToString(b,
                                    Base64.DEFAULT);
                            imageview_updateimage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CAMERA:
                if (intResultCode == RESULT_OK) {

                    if (intRequestCode == REQUEST_CAMERA) {
                        strImagePath = Environment.getExternalStorageDirectory() + "/i2i_ifazidesk/Images/"+strImagename;
                        Log.i("Imagepath", "PATH : " + strImagePath);
                        File objFile = new File(strImagePath);
                        Bitmap objBitmap = null;

                        Uri uri = Uri.parse(strImagePath);
                        ContentResolver cR = getApplicationContext().getContentResolver();
                        string_filetype = "png";
                        try {
                            try {
                                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                                btmapOptions.inSampleSize = 5;
                                objBitmap = BitmapFactory.decodeFile(objFile.getAbsolutePath(), btmapOptions);
                            } catch (OutOfMemoryError e) {
                                e.printStackTrace();
                                System.gc();
                                try {
                                    objBitmap = BitmapFactory.decodeFile(objFile.getAbsolutePath());
                                } catch (OutOfMemoryError e2) {
                                    e2.printStackTrace();
                                }
                            }
                            Bitmap scaledBitmap = scaleDown(objBitmap, 1024, true);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            scaledBitmap.compress(
                                    Bitmap.CompressFormat.JPEG, 90, baos);
                            byte[] b = baos.toByteArray();
                            temp = "";
                            temp = Base64.encodeToString(b,
                                    Base64.DEFAULT);
                            temp = "";
                            temp = Base64.encodeToString(b, Base64.DEFAULT);
                            imageview_updateimage.setImageBitmap(objBitmap);
                            try {
                                objFile.delete();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        Bitmap newBitmap;
        float floatRatio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int intWidth = Math.round(floatRatio * realImage.getWidth());
        int intHeight = Math.round(floatRatio * realImage.getHeight());

        float maxHeight = 2048.0f;
        float maxWidth = 2048.0f;

        int intmaxheight = Math.round(maxHeight);
        int intmaxwidth = Math.round(maxWidth);

        if (intWidth > maxHeight || intHeight > maxWidth) {
            newBitmap = Bitmap.createScaledBitmap(realImage, intmaxwidth,
                    intmaxheight, filter);
        } else {
            newBitmap = Bitmap.createScaledBitmap(realImage, intWidth,
                    intHeight, filter);
        }

        return newBitmap;
    }


    public class WebService_UpdateImage extends AsyncTask<String, String, String> {
        String result_dashboarddata = "";
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

            String[] keys = { "compliantid","image","statusid","userid"};
            String[] values = {string_ticketid,temp,string_statusid,string_userid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(TicketDetails.this).postDataToSOAPService(ht, "UpdateTicketImageHistory");
                Log.i("data:","UpdateTicketImageHistory:"+result);
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
                        JSONObject json_obj=new JSONObject(result);
                        updatecomplaintdialog.dismiss();
                        boolean status = json_obj.getBoolean("Status");
                        if(status == true)
                        {
                            Toast.makeText(getApplicationContext(), "Ticket Rised Successfully ", 2000).show();
                            Intent ii9i=new Intent(TicketDetails.this,NewDashboard.class);
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
                        updatecomplaintdialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Ticket Rise Failed!\nTry Again", 2000).show();

                    }
                } catch (NumberFormatException e) {
                    if(updatecomplaintdialog != null && updatecomplaintdialog.isShowing())
                    {
                        updatecomplaintdialog.dismiss();
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if(updatecomplaintdialog != null && updatecomplaintdialog.isShowing())
                {
                    updatecomplaintdialog.dismiss();
                }
            }

        }
    }


    public void LoadTicketDetails()
    {
        progressDialog_ticketdetails = new ProgressDialog(TicketDetails.this);
        progressDialog_ticketdetails.setMessage(getResources().getString(R.string.progress_loading));
        progressDialog_ticketdetails.setCancelable(false);
        progressDialog_ticketdetails.show();
        Call<TicketDetailsResponse> call_dashboardmonth = apiService.GetTicketHistory(string_ticketid);
        Log.i(TAG,"ComplaintID:"+string_ticketid);
        Log.i(TAG,"TicketDetailsResponse:"+call_dashboardmonth.toString());
        call_dashboardmonth.enqueue(new Callback<TicketDetailsResponse>()
        {
            @Override
            public void onResponse(Call<TicketDetailsResponse> call, Response<TicketDetailsResponse> response) {

                TicketDetailsData ticketDetailsData = response.body().getTicketDetailsData();

                    string_companyid = ""+ticketDetailsData.getCompanyID(); //jsonarray_ticketdetails.getJSONObject(0).getInt("CompanyID");
                    string_locationid= ""+ticketDetailsData.getLocationID();//jsonarray_ticketdetails.getJSONObject(0).getInt("locationID");
                    int ComplaintId = ticketDetailsData.getComplaintId(); //jsonarray_ticketdetails.getJSONObject(0).getInt("ComplaintId");
                    int Statusid = ticketDetailsData.getStatusid();//jsonarray_ticketdetails.getJSONObject(0).getInt("Statusid");
                    string_currentstatusid = ""+Statusid;
                    String TicketNo = ticketDetailsData.getTicketNo();//jsonarray_ticketdetails.getJSONObject(0).getString("TicketNo");
                    String Category = ticketDetailsData.getCategory();//jsonarray_ticketdetails.getJSONObject(0).getString("Category");
                    String Subject = ticketDetailsData.getSubject();//jsonarray_ticketdetails.getJSONObject(0).getString("Subject");
                    String Status = ticketDetailsData.getStatus();//jsonarray_ticketdetails.getJSONObject(0).getString("Status");
                    String Priority = ticketDetailsData.getPriority();//jsonarray_ticketdetails.getJSONObject(0).getString("Priority");
                    RequestorName = ticketDetailsData.getRequestorName();//jsonarray_ticketdetails.getJSONObject(0).getString("RequestorName");
                    String AssignedTo = ticketDetailsData.getAssignedTo();//jsonarray_ticketdetails.getJSONObject(0).getString("AssignedTo");
                    String RequestDate = ticketDetailsData.getRequestDate();//jsonarray_ticketdetails.getJSONObject(0).getString("RequestDate");
                    String RequestTime = ticketDetailsData.getRequestTime();//jsonarray_ticketdetails.getJSONObject(0).getString("RequestTime");
                    String TicketResponseTime = ticketDetailsData.getTicketResponseTime();//jsonarray_ticketdetails.getJSONObject(0).getString("TicketResponseTime");
                    String ResponseTime = ticketDetailsData.getResponseTime();//jsonarray_ticketdetails.getJSONObject(0).getString("ResponseTime");
                    String ColorCode = ticketDetailsData.getColorCode();//jsonarray_ticketdetails.getJSONObject(0).getString("ColorCode");
                    String ImageLink = ticketDetailsData.getImageLink();//jsonarray_ticketdetails.getJSONObject(0).getString("ImageLink");
                    string_attachmentlink = ImageLink;
                    String FileType = ticketDetailsData.getFileType();//jsonarray_ticketdetails.getJSONObject(0).getString("FileType");

                    String color[] = ColorCode.split(" - ");
                    ColorCode = color[1];
                    imageview_status.setColorFilter(Color.parseColor(ColorCode));
                    rl_status_bg.setBackgroundColor(Color.parseColor(ColorCode));

                    if(ImageLink.length() > 0)
                    {
                        if(FileType.equals(".jpg")||FileType.equals(".jpeg")||FileType.equals(".png"))
                        {

                            if(ImageLink.length() > 5)
                            {
                                Picasso.with(TicketDetails.this).load(ImageLink).into(imageview_complaintimage);
                                Picasso.with(TicketDetails.this).load(ImageLink).into(imageview_complaintfullimage);
                                rl_imageview.setVisibility(View.GONE);
                                imageview_complaintfullimage.setVisibility(View.GONE);
                            }

                            imageview_complaintimage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //However it is you load your images
                                    if(rl_imageview.getVisibility() == View.GONE)
                                    {
                                        rl_imageview.setVisibility(View.VISIBLE);
                                        imageview_complaintfullimage.setVisibility(View.VISIBLE);
                                    }

                                }
                            });
                            imageview_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //However it is you load your images
                                    if(rl_imageview.getVisibility() == View.VISIBLE)
                                    {
                                        rl_imageview.setVisibility(View.GONE);
                                        imageview_complaintfullimage.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }
                        else
                        {
                            imageview_complaintimage.setImageResource(R.drawable.image_icon_attachement);
                            imageview_complaintimage.setPadding(10,10,10,10);
                            imageview_complaintimage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //However it is you load your images
                                    Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(string_attachmentlink));
                                    startActivity(browserIntent);

                                }
                            });
                        }
                    }


                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a", Locale.ENGLISH);
                            Date date = sdf.parse(TicketResponseTime);
                            timeInMillis = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    textview_reqhours.setText(""+timeInMillis);

                    Calendar rightNow = Calendar.getInstance();
                    long long_systemtime = System.currentTimeMillis();//rightNow.getTimeInMillis();


                    SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                    dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date1 = new Date(timeInMillis);
                    System.out.println(dateformat.format(date1));
                    Date date2 = new Date(long_systemtime);
                    System.out.println(dateformat2.format(date2));
                    long diffinMilliseconds = date1.getTime() - date2.getTime();

                    SimpleDateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");

                    try {
                        Calendar cal = Calendar.getInstance();
                        Date d1 = dateFormat.parse(ResponseTime);
                        Date d2 = cal.getTime();
                        System.out.println("DATE : "+d1+" "+d2);
                        System.out.println("Formated :"+dateFormat.format(d1)+" "+dateFormat.format(d2));

                        diffinMilliseconds = d1.getTime() - d2.getTime();
                        Log.i("DAte1"," Date1: "+date1.getTime());
                        Log.i("DAte2"," Date2: "+date2.getTime());
                        Log.i("DiffinMS"," Milliseconds: "+diffinMilliseconds);
                    }
                    catch(Exception e) {
                        System.out.println("Excep"+e);
                    }

                    if(string_isclosed.equals("true"))
                    {
                        textview_reqhours.setText("Time Over");
                        textview_reqhours.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.bg_grey));

                    }

                    else {
                        new CountDownTimer(diffinMilliseconds, 1000) {

                            public void onTick(long millisUntilFinished) {
                                if(millisUntilFinished < 1800000)
                                {
                                    textview_reqhours.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.bg_orange));
                                }
                                calendar_countdown = Calendar.getInstance();
                                calendar_countdown.setTimeInMillis(millisUntilFinished);
                                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                                String date = df.format(calendar_countdown.getTime());
                                textview_reqhours.setText(date);
                            }

                            public void onFinish() {
                                textview_reqhours.setText("Time Over\nEsc L1");
                                textview_reqhours.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.bg_red));
                            }

                        }.start();
                    }


                    tckt_location = ticketDetailsData.getLocation();//jsonarray_ticketdetails.getJSONObject(0).getString("location");
                    tckt_building = ticketDetailsData.getBuilding();//jsonarray_ticketdetails.getJSONObject(0).getString("building");
                    tckt_floor = ticketDetailsData.getFloor();//jsonarray_ticketdetails.getJSONObject(0).getString("floor");
                    tckt_wing = ticketDetailsData.getWing();//jsonarray_ticketdetails.getJSONObject(0).getString("wing");

                    String inputPattern = "MMM dd yyyy";
                    String outputPattern = "dd/MM/yyyy";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
                    Date date = null;
                    String str_date = null;
                    try {
                        date = inputFormat.parse(RequestDate);
                        str_date = outputFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    List<TicketHistoryItem> ticketHistoryItems = ticketDetailsData.getLsthistory();
                    //jsonarray_history_temp = jsonarray_ticketdetails.getJSONObject(0).getJSONArray("lsthistory");
                    tableLayout_history.removeAllViews();
                    for(int k=0;k<ticketHistoryItems.size();k++)
                    {
                        TableRow row_history = (TableRow) LayoutInflater.from(TicketDetails.this).inflate(R.layout.layout_history, null);
                        String StatusDate = ticketHistoryItems.get(k).getStatusDate();//jsonarray_history_temp.getJSONObject(k).getString("StatusDate");
                        String StatusTime = ticketHistoryItems.get(k).getStatusTime();//jsonarray_history_temp.getJSONObject(k).getString("StatusTime");
                        String location = ticketHistoryItems.get(k).getLocation();//jsonarray_history_temp.getJSONObject(k).getString("location");
                        String Username = ticketHistoryItems.get(k).getUsername();//jsonarray_history_temp.getJSONObject(k).getString("Username");
                        String description = ticketHistoryItems.get(k).getDescription();//jsonarray_history_temp.getJSONObject(k).getString("description");
                        String temp_Status = ticketHistoryItems.get(k).getStatus();//jsonarray_history_temp.getJSONObject(k).getString("Status");
                        String Colorcode = ticketHistoryItems.get(k).getColorcode();//jsonarray_history_temp.getJSONObject(k).getString("Colorcode");

                        TextView textView_datetime = (TextView) row_history.findViewById(R.id.textview_historydatetime);
                        TextView textview_locationandperson = (TextView) row_history.findViewById(R.id.textview_locationandperson);
                        TextView textview_desc = (TextView) row_history.findViewById(R.id.textview_desc);
                        TextView textview_status = (TextView) row_history.findViewById(R.id.textview_historystatus);
                        textview_locationandperson.setText(Username+" - "+location);
                        textview_desc.setText(description);
                        textview_status.setText(temp_Status);

                        String status_color;
                        String statuscolorcode[] = Colorcode.split(" - ");
                        status_color = statuscolorcode[1];
                        GradientDrawable bgShape = (GradientDrawable)textview_status.getBackground();
                        textview_status.setTextColor(Color.parseColor(status_color));

                        String str_status_date = null;
                        try {
                            date = inputFormat.parse(StatusDate);
                            str_status_date = outputFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textView_datetime.setText(str_status_date+"\n"+StatusTime);
                        tableLayout_history.addView(row_history);
                    }


                    textview_location.setText(tckt_location);
                    textview_building.setText(tckt_building);
                    textview_floor.setText(tckt_floor);
                    textview_wing.setText(tckt_wing);
                    textview_RequestorName.setText(RequestorName);

                    textview_reassign_location.setText(tckt_location);
                    textview_reassign_building.setText(tckt_building);
                    textview_reassign_floor.setText(tckt_floor);
                    textview_reassign_wing.setText(tckt_wing);
                    textview_reassign_RequestorName.setText(RequestorName);

                    textview_TicketNo.setText(TicketNo);
                    textview_Category.setText(Category);
                    textview_Subject.setText(Subject);
                    textview_Status.setText(Status);
                    textview_Priority.setText(Priority);

                    textview_AssignedTo.setText(AssignedTo);
                    textview_datetime.setText(str_date+" "+RequestTime);

                    LoadStatus();
                }

            @Override
            public void onFailure(Call<TicketDetailsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog_ticketdetails.dismiss();
            }
        });
    }

    public void LoadStatus()
    {
        Log.i("STATUS","STATUSID:"+string_currentstatusid+" COMPANYID:"+string_companyid+" ROLEID:"+string_roleid);
        Call<StatusResponse> call_dashboardmonth = apiService.GetStatus(string_currentstatusid,string_companyid,string_roleid);

        Log.i(TAG,"StatusResponse:"+call_dashboardmonth.toString());

        call_dashboardmonth.enqueue(new Callback<StatusResponse>()
        {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                if(response.body().isStatus() == true)
                {
                    List<StatusData> statusDataList = response.body().getStatusdetails();
                    if (statusDataList.size() > 0) {
                        RadioButton[] rb = new RadioButton[statusDataList.size()];
                        for (int k = 0; k < statusDataList.size(); k++) {
                            int statusid = statusDataList.get(k).getStatusid();//jsonarray_statusdetails.getJSONObject(k).getInt("statusid");
                            String status = statusDataList.get(k).getStatus(); //jsonarray_statusdetails.getJSONObject(k).getString("status");
                            rb[k] = new RadioButton(TicketDetails.this);
                            rb[k].setButtonDrawable(null);
                            rb[k].setBackgroundResource(R.drawable.radiobuttonbg_1);
                            rb[k].setButtonDrawable(android.R.color.transparent);
                            rb[k].setTextColor(getResources().getColorStateList(R.color.radiobutton_text));
                            rb[k].setText(status);
                            rb[k].setPadding(22, 22, 22, 22);
                            rb[k].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            rb[k].setId(statusid);
                            rb[k].setTextSize(14);
                            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.gravity = Gravity.CENTER_VERTICAL;
                            params.setMargins(5, 5, 5, 5);
                            Log.i("status id:" + statusid, " status:" + status);
                            radiogroup_status.addView(rb[k], params);
                        }
                    }
                }
                LoadAssignee();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog_ticketdetails.dismiss();
            }
        });
    }


    public void LoadAssignee()
    {

        Call<AssigneeResponse> call_dashboardmonth = apiService.GetAssignee(string_ticketid);

        Log.i(TAG,"ComplaintId:"+string_ticketid);

        call_dashboardmonth.enqueue(new Callback<AssigneeResponse>()
        {
            @Override
            public void onResponse(Call<AssigneeResponse> call, Response<AssigneeResponse> response) {

                if(response == null)
                {
                    Toast.makeText(TicketDetails.this, "No assignee found - Please map assignee for this category", 3000).show();
                    Intent ii9i = new Intent(TicketDetails.this,NewDashboard.class);
                    ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ii9i);
                }
                else
                    {

                    List<LstproviderItem> providerlist = response.body().getLstprovider();
                        int temp_userid = providerlist.get(0).getUserid();//jsonarray_lstprovider_history.getJSONObject(0).getInt("userid");
                        String temp_name = providerlist.get(0).getName();//jsonarray_lstprovider_history.getJSONObject(0).getString("name");
                        textview_assignedtouser.setText(temp_name);
                        textview_reassign_assignedto.setText(temp_name);

                    List<LstassigneeItem> assigneelist = response.body().getLstassignee();
                        GetSet_AssigneList.clear();
                        if(string_isOpenBySE.equals("true"))
                        {
                            for(int k=0;k<assigneelist.size();k++)
                            {
                                int userid = assigneelist.get(k).getUserid(); //jsonarray_availableassignee.getJSONObject(k).getInt("userid");
                                String name = assigneelist.get(k).getName(); //jsonarray_availableassignee.getJSONObject(k).getString("name");
                                if(name.equals(temp_name))
                                {
                                    GetSet assignee_item = new GetSet();
                                    assignee_item.setInt_Item_1(userid);
                                    assignee_item.setString_Item_1(name);
                                    GetSet_AssigneList.add(assignee_item);
                                }
                            }
                        }
                        else
                        {
                            for(int k=0;k<assigneelist.size();k++)
                            {
                                int userid = assigneelist.get(k).getUserid(); //jsonarray_availableassignee.getJSONObject(k).getInt("userid");
                                String name = assigneelist.get(k).getName(); //jsonarray_availableassignee.getJSONObject(k).getString("name");
                                GetSet assignee_item = new GetSet();
                                assignee_item.setInt_Item_1(userid);
                                assignee_item.setString_Item_1(name);
                                GetSet_AssigneList.add(assignee_item);
                            }
                        }
                    ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(getApplicationContext(), R.layout.layout_spinner_black, GetSet_AssigneList);
                    adapter.setDropDownViewResource(R.layout.layout_spinner_textview);
                    spinner_assignee.setAdapter(adapter);
                    spinner_assignee.setSelection(0);

                    if(GetSet_AssigneList.size() > 0)
                    {
                        ArrayAdapter<GetSet> adapter_reassign = new ArrayAdapter<GetSet>(getApplicationContext(), R.layout.layout_spinner_black, GetSet_AssigneList);
                        adapter_reassign.setDropDownViewResource(R.layout.layout_spinner_textview);
                        spinner_reassignee.setAdapter(adapter_reassign);
                        spinner_reassignee.setSelection(0);
                    }
                    progressDialog_ticketdetails.dismiss();
                }
            }
            @Override
            public void onFailure(Call<AssigneeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

                progressDialog_ticketdetails.dismiss();
            }
        });
    }
}
