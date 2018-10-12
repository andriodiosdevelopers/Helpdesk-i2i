package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_DateRange_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_DateRange_Tickets;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List_Building;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List_Floor;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List_Location;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List_Wing;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.utils.ImageLoader;
import helpdesk.i2i.com.ifazidesk.webservice.NetworkCall;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;

import static android.Manifest.permission.CAMERA;

public class IssueLocation extends AppCompatActivity {
    Preferences prefs;
    Button button_location;
    Button button_building;
    Button button_floor;
    Button button_wing;
    RadioGroup radioGroup_requestype;
    EditText editText_comments;
    EditText editText_requestorname;
    Button button_submit;
    int int_companyid = 0;

    ProgressDialog pDialog;

    String string_locationname = "";
    String string_locationid = "";
    int int_locationid = 0;

    String string_buildingname = "";
    String string_buildingid = "";
    int int_buildingid = 0;

    String string_floorname = "";
    String string_floorid = "";
    int int_floorid = 0;

    String string_wingname = "";
    String string_wingid = "";
    int int_wingid = 0;

    String string_roleid = "";
    int int_roleid = 0;
    String string_userid = "";
    int int_userid = 0;
    String string_groupid = "";
    int int_groupid = 0;

    int int_priority_id;
    int int_issue_id = 0;
    int int_dept_id = 0;
    int int_request_typeid = 0;

    String string_issuename = "";
    String string_priority = "";
    String string_priorityid = "";
    String string_responsetime_sp = "";

    Boolean boolean_checkinternet = true;
    Boolean boolean_location = false;
    Boolean boolean_building = false;
    Boolean boolean_floor = false;
    Boolean boolean_wing = false;
    TextView textView_request_type;

    Spinner spinner_location;
    Spinner spinner_building;
    Spinner spinner_floor;
    Spinner spinner_wing;

    List<GetSet> locationlist = new ArrayList<GetSet>();
    List<GetSet> buildinglist = new ArrayList<GetSet>();
    List<GetSet> floorlist = new ArrayList<GetSet>();
    List<GetSet> winglist = new ArrayList<GetSet>();

    ImageView imageView_camera;
    public static final int REQUEST_CAMERA = 5555;
    public static final int REQUEST_IMAGES = 1111;
    public static final int SELECT_FILE = 2222;
    public static final int SELECT_DOC = 3333;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    boolean cameraAccepted = false;
    boolean storageAccepted = false;
    String temp = "";
    String UserName_SP = "";
    String strImagePath = "";
    String strImagename = ""+System.currentTimeMillis()+".jpg";
    String CompliantId = "";
    File objFile;
    String string_Language = "";
    String string_PROGRESS_SUCCESS = "";
    String string_PROGRESS_LOADING = "";
    String string_DIALOG_MENU_TAKEPHOTO = "";
    String string_DIALOG_MENU_CHOOSEIMAGE = "";
    String string_DIALOG_MENU_CHOOSEFILES = "Choose Docs";
    String string_DIALOG_MENU_CANCEL = "";
    String string_filetype = "";

    String string_companyname;
    String string_companyid;

    TextView textview_companyname;
    TextView textview_locationname;
    TextView textview_buildingname;
    TextView textview_floorname;
    TextView textview_wingname;

    ImageView imageview_issuedepartment;
    ImageView imageview_issue;
    TextView textview_issuedepartment;
    TextView textview_issue;

    String string_departmentname ="";
    String string_departmenturl ="";
    String string_issueurl ="";
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_location);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imageLoader = new ImageLoader(getApplicationContext());
        prefs = new Preferences(getApplicationContext());
        button_location = (Button)findViewById(R.id.button_location);
        button_building = (Button)findViewById(R.id.button_building);
        textView_request_type = (TextView)findViewById(R.id.tv_request_type);
        button_floor = (Button)findViewById(R.id.button_floor);
        button_wing = (Button)findViewById(R.id.button_wing);
        radioGroup_requestype = (RadioGroup)findViewById(R.id.radiogroup_requesttype);
        editText_comments = (EditText) findViewById(R.id.edittext_comment);
        editText_requestorname = (EditText) findViewById(R.id.editText_requestorname);
        spinner_location = (Spinner) findViewById(R.id.spinner_location);
        spinner_building = (Spinner) findViewById(R.id.spinner_building);
        spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
        spinner_wing = (Spinner) findViewById(R.id.spinner_wing);
        button_submit = (Button)findViewById(R.id.button_submit);
        imageView_camera = (ImageView)findViewById(R.id.imageview_camera);

        TextView tv_request_type = (TextView)findViewById(R.id.tv_request_type);
        TextView textview_location_label = (TextView)findViewById(R.id.textview_location_label);
        TextView textview_building_label = (TextView)findViewById(R.id.textview_building_label);
        TextView textview_floor_label = (TextView)findViewById(R.id.textview_floor_label);
        TextView textview_wing_label = (TextView)findViewById(R.id.textview_wing_label);


        int_companyid = prefs.getInt("CompanyID_SP");
        int_roleid = prefs.getInt("RoleID");
        int_userid = prefs.getInt("UserID_SP");
        int_groupid = prefs.getInt("GroupID_SP");
        string_issuename = (prefs.getString("IssueName_SP"));
        string_priority = (prefs.getString("Priority_SP"));
        string_priorityid = (prefs.getString("PriorityID_SP"));
        UserName_SP = (prefs.getString("UserName_SP"));
        int_priority_id = Integer.parseInt(string_priorityid);
        string_responsetime_sp = (prefs.getString("ResponseTime_SP"));
        int_dept_id  = (prefs.getInt("DeptID_SP"));
        string_departmentname  = (prefs.getString("DeptName_SP"));
        int_issue_id = (prefs.getInt("IssueID_SP"));
        string_Language = prefs.getString("Language");

        string_companyid = prefs.getString("selected_CompanyID");
        string_companyname = prefs.getString("selected_CompanyName");
        string_locationid = prefs.getString("selected_LocationID");
        string_locationname = prefs.getString("selected_LocationName");
        string_buildingid = prefs.getString("selected_BuildingID");
        string_buildingname = prefs.getString("selected_BuildingName");
        string_floorid = prefs.getString("selected_FloorID");
        string_floorname = prefs.getString("selected_FloorName");
        string_wingid = prefs.getString("selected_WingID");
        string_wingname = prefs.getString("selected_WingName");

        string_departmenturl = prefs.getString("DeptURL_SP");
        string_issueurl = prefs.getString("IssueURL_SP");

        int_companyid = Integer.parseInt(string_companyid);
        int_locationid = Integer.parseInt(string_locationid);
        int_buildingid = Integer.parseInt(string_buildingid);
        int_floorid = Integer.parseInt(string_floorid);
        int_wingid = Integer.parseInt(string_wingid);

        editText_requestorname.setText(UserName_SP);

        imageview_issuedepartment = (ImageView) findViewById(R.id.imageview_issuedepartment);
        imageview_issue = (ImageView) findViewById(R.id.imageview_issue);

        textview_issuedepartment = (TextView) findViewById(R.id.textview_issuedepartment);
        textview_issue = (TextView) findViewById(R.id.textview_issue);

        textview_companyname = (TextView) findViewById(R.id.textview_companyname);
        textview_locationname = (TextView) findViewById(R.id.textview_locationname);
        textview_buildingname = (TextView) findViewById(R.id.textview_buildingname);
        textview_floorname = (TextView) findViewById(R.id.textview_floorname);
        textview_wingname = (TextView) findViewById(R.id.textview_wingname);

        textview_issuedepartment.setText(string_departmentname);
        textview_issue.setText(string_issuename);
        textview_companyname.setText(string_companyname);
        textview_locationname.setText(string_locationname);
        textview_buildingname.setText(string_buildingname);
        textview_floorname.setText(string_floorname);
        textview_wingname.setText(string_wingname);

        imageLoader.DisplayImage(string_departmenturl, imageview_issuedepartment);
        imageLoader.DisplayImage(string_issueurl, imageview_issue);
       try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("textview_title_issuelocation"))
                {
                    getSupportActionBar().setTitle(langauge);
                }
                if(Control.equals("spinner_selectlocation"))
                {
                    spinner_location.setPrompt(langauge);
                    textview_location_label.setText(langauge);
                }
                if(Control.equals("spinner_selectbuilding"))
                {
                    spinner_building.setPrompt(langauge);
                    textview_building_label.setText(langauge);
                }
                if(Control.equals("spinner_selectfloor"))
                {
                    spinner_floor.setPrompt(langauge);
                    textview_floor_label.setText(langauge);
                }
                if(Control.equals("spinner_selectwing"))
                {
                    spinner_wing.setPrompt(langauge);
                    textview_wing_label.setText(langauge);
                }
                if(Control.equals("edittext_hint_descriptionabtissue"))
                {
                    editText_comments.setHint(langauge);
                }
                if(Control.equals("edittext_hint_requestorname"))
                {
                    editText_requestorname.setHint(langauge);
                }
                if(Control.equals("button_submit"))
                {
                    button_submit.setText(langauge);
                }
                if(Control.equals("textview_requesttype"))
                {
                    tv_request_type.setText(langauge+" : ");
                }
                if(Control.equals("progress_pleasewait"))
                {
                    string_PROGRESS_LOADING = langauge;
                }
                if(Control.equals("textview_issuesubmitted"))
                {
                    string_PROGRESS_SUCCESS = langauge;
                }
                if(Control.equals("dialog_menu_takephoto"))
                {
                    string_DIALOG_MENU_TAKEPHOTO = langauge;
                }
                if(Control.equals("dialog_menu_choosefromgallery"))
                {
                    string_DIALOG_MENU_CHOOSEIMAGE = langauge;
                }
                if(Control.equals("dialog_menu_cancel"))
                {
                    string_DIALOG_MENU_CANCEL = langauge;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        strImagePath = Environment.getExternalStorageDirectory() + "/i2i_ifazidesk/Images";
        new GetRequestType().execute();
        //new WebService_Location().execute();
        //imageView_camera.setVisibility(View.GONE);
        imageView_camera.setOnClickListener(new View.OnClickListener() {

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

        spinner_location.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            string_locationname = spinner_location.getSelectedItem().toString();
                            string_locationid = ""+locationlist.get(arg2).getInt_Item_1();
                            int_locationid = locationlist.get(arg2).getInt_Item_1();
                            new WebService_Building().execute();
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );

        spinner_building.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            string_buildingname = spinner_building.getSelectedItem().toString();
                            string_buildingid = ""+buildinglist.get(arg2).getInt_Item_1();
                            int_buildingid = buildinglist.get(arg2).getInt_Item_1();
                            new WebService_Floor().execute();
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );


        spinner_floor.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            string_floorname = spinner_floor.getSelectedItem().toString();
                            string_floorid = ""+floorlist.get(arg2).getInt_Item_1();
                            int_floorid = floorlist.get(arg2).getInt_Item_1();
                            new WebService_Wing().execute();
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );


        spinner_wing.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3)
                    {
                        if(arg2 != -1 | arg2 > -1)
                        {
                            string_wingname = spinner_wing.getSelectedItem().toString();
                            string_wingid = ""+winglist.get(arg2).getInt_Item_1();
                            int_wingid = winglist.get(arg2).getInt_Item_1();
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );

        radioGroup_requestype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                int_request_typeid = checkedId;

            }
        });

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.app.FragmentManager manager = IssueLocation.this.getSupportFragmentManager();
                Log.i("Fragment_List_Country", "Opening Country List");
                Fragment_List_Location dialog = new Fragment_List_Location();
                dialog.SetCompanyId(int_companyid);
                dialog.show(manager, "dialog");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                button_building.setText("Select Building");
              //  bl_select_company = false;
                button_floor.setText("Select Floor");
              //  bl_select_location = false;
                button_wing.setText("Select Wing");
                button_location.setError(null);
             //   bl_select_audit = false;

            }
        });

        button_building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(boolean_location == true)
                {
                UserName_SP = editText_requestorname.getText().toString();
                android.support.v4.app.FragmentManager manager = IssueLocation.this.getSupportFragmentManager();
                Log.i("Fragment_List_Country", "Opening Country List");
                Fragment_List_Building dialog = new Fragment_List_Building();
                dialog.SetLocationid(int_companyid,int_locationid);
                dialog.show(manager, "dialog");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                button_floor.setText("Select Floor");
                button_wing.setText("Select Wing");

                    boolean_floor = false;
                    boolean_wing = false;

                }
                else
                {
                    button_location.setError("Select Location");
                }

                button_building.setError(null);
            }
        });

        button_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(boolean_building == true)
                {

                    android.support.v4.app.FragmentManager manager = IssueLocation.this.getSupportFragmentManager();
                    Log.i("Fragment_List_Country", "Opening Country List");
                    Fragment_List_Floor dialog = new Fragment_List_Floor();
                    dialog.SetBuildingId(int_companyid, int_buildingid);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    button_wing.setText("Select Wing");
                    boolean_wing = false;
                }
                else
                {
                    button_building.setError("Select Building");
                }
                button_floor.setError(null);
            }
        });

        button_wing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(boolean_floor == true)
                {
                    android.support.v4.app.FragmentManager manager = IssueLocation.this.getSupportFragmentManager();
                    Log.i("Fragment_List_Country", "Opening Country List");
                    Fragment_List_Wing dialog = new Fragment_List_Wing();
                    dialog.SetFloorId(int_companyid, int_floorid);
                    dialog.show(manager, "dialog");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }else
                {
                    button_floor.setError("Select Floor");
                }
                button_wing.setError(null);

            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(editText_comments.getText().toString().trim().length()<1)
                {
                    Toast.makeText(getApplicationContext(), "Enter Description", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean_checkinternet = checkinternetconnection();
                    if(boolean_checkinternet == true)
                    {
                        /*if(boolean_location == true && boolean_building == true && boolean_floor == true && boolean_wing == true)
                        {
                            new IssueSubmitService().execute();
                        }*/
                        new IssueSubmitService().execute();
                    }
                }

            }
        });
    }

    public void selectImage() {

        final CharSequence[] chararrOptions = {string_DIALOG_MENU_TAKEPHOTO, string_DIALOG_MENU_CHOOSEIMAGE,string_DIALOG_MENU_CHOOSEFILES, string_DIALOG_MENU_CANCEL};
        View v = null;

        strImagePath = Environment.getExternalStorageDirectory() + "/i2i_ifazidesk/Images";

        //strImagePath = Environment.getExternalStorageDirectory() + "/JLL_52Weekpm/Images";
        AlertDialog.Builder builder = new AlertDialog.Builder(IssueLocation.this,
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
                    (IssueLocation.this).startActivityForResult(intent, REQUEST_CAMERA);
                    //Bitmap capturedimage = BitmapFactory.decodeFile(strImagePath);
                    //Drawable d = new BitmapDrawable(capturedimage);
                }
                else if (chararrOptions[item].equals(string_DIALOG_MENU_CHOOSEIMAGE))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    (IssueLocation.this).startActivityForResult(Intent.createChooser(intent, string_DIALOG_MENU_CHOOSEIMAGE), SELECT_FILE);

                }
                else if (chararrOptions[item].equals(string_DIALOG_MENU_CHOOSEFILES))
                {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("file/*");
                    (IssueLocation.this).startActivityForResult(Intent.createChooser(intent, string_DIALOG_MENU_CHOOSEFILES), SELECT_DOC);
                    //startActivityForResult(intent,SELECT_DOC);

                } else if (chararrOptions[item].equals(string_DIALOG_MENU_CANCEL))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void SetLocation(String locationname, String locationid)
    {
        button_location.setText(locationname);
        string_locationid = locationid;
        int_locationid = Integer.parseInt(string_locationid);
        boolean_location = true;
    }

    public void SetBuilding(String buildingname, String buildingid)
    {
        button_building.setText(buildingname);
        string_buildingid = buildingid;
        int_buildingid = Integer.parseInt(string_buildingid);
        boolean_building = true;
    }
    public void SetFloor(String floorname, String floorid)
    {
        button_floor.setText(floorname);
        string_floorid = floorid;
        int_floorid = Integer.parseInt(string_floorid);
        boolean_floor = true;
    }
    public void SetWing(String wingname, String wingid)
    {
        button_wing.setText(wingname);
        string_wingid = wingid;
        int_wingid = Integer.parseInt(string_wingid);
        boolean_wing = true;
    }


    public class GetRequestType extends AsyncTask<String, Void, String> {
        String responsedata = "";
        String loginresult = "";
        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueLocation.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            responsedata= WebService.GetRequestType(int_companyid,int_roleid);
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
                        rb[i] = new RadioButton(IssueLocation.this);
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
                   // Toast.makeText(getApplicationContext(), "No Request Type Assigned!", 2000).show();
                }
            }
            super.onPostExecute(result);
        }


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

    public class WebService_Location extends AsyncTask<String, String, String> {
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
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","RoleID"};
            String[] values = {""+int_companyid,""+int_roleid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(IssueLocation.this).postDataToSOAPService(ht, "GetRoleLocationDetails");
                Log.i("data:","GetRoleLocationDetails:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                        Log.i("data:", "" + result);
                        //JSONArray jsonarray_dashboard = new JSONArray(result);
                        try {
                            locationlist.clear();
                            JSONArray jsonArray_location = new JSONArray(result);
                            for(int i=0;i<jsonArray_location.length();i++)
                            {
                                String LocationID = jsonArray_location.getJSONObject(i).getString("LocationID");
                                String LocationName = jsonArray_location.getJSONObject(i).getString("LocationName");
                                int int_locationid_temp = Integer.parseInt(LocationID);
                                GetSet location_item = new GetSet();
                                location_item.setInt_Item_1(int_locationid_temp);
                                location_item.setString_Item_1(LocationName);
                                locationlist.add(location_item);
                            }
                            ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(IssueLocation.this,R.layout.layout_spinner_black, locationlist);
                            adapter.setDropDownViewResource(R.layout.layout_spinner_textview);
                            spinner_location.setAdapter(adapter);
                            spinner_location.setSelection(0);
                            //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }*/
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




    public class WebService_Building extends AsyncTask<String, String, String> {
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
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","LocationID"};
            String[] values = {""+int_companyid,""+int_locationid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(IssueLocation.this).postDataToSOAPService(ht, "GetBuildingDetails");
                Log.i("data:","GetBuildingDetails:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                    //JSONArray jsonarray_dashboard = new JSONArray(result);
                    try {
                        buildinglist.clear();
                        JSONArray jsonArray_building = new JSONArray(result);
                        for(int i=0;i<jsonArray_building.length();i++)
                        {
                            String BuildingID = jsonArray_building.getJSONObject(i).getString("BuildingID");
                            String BuildingName = jsonArray_building.getJSONObject(i).getString("BuildingName");
                            int int_BuildingID_temp = Integer.parseInt(BuildingID);
                            GetSet building_item = new GetSet();
                            building_item.setInt_Item_1(int_BuildingID_temp);
                            building_item.setString_Item_1(BuildingName);
                            buildinglist.add(building_item);
                        }
                        ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(IssueLocation.this,R.layout.layout_spinner_black, buildinglist);
                        adapter.setDropDownViewResource(R.layout.layout_spinner_textview);
                        spinner_building.setAdapter(adapter);
                        spinner_building.setSelection(0);
                        //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }*/
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


    public class WebService_Floor extends AsyncTask<String, String, String> {
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
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","BuildingID"};
            String[] values = {""+int_companyid,""+int_buildingid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(IssueLocation.this).postDataToSOAPService(ht, "GetFloorDetails");
                Log.i("data:","GetFloorDetails:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                    //JSONArray jsonarray_dashboard = new JSONArray(result);
                    try {
                        floorlist.clear();
                        JSONArray jsonArray_floor = new JSONArray(result);
                        for(int i=0;i<jsonArray_floor.length();i++)
                        {
                            String FloorID = jsonArray_floor.getJSONObject(i).getString("FloorID");
                            String FloorName = jsonArray_floor.getJSONObject(i).getString("FloorName");
                            int int_FloorID_temp = Integer.parseInt(FloorID);
                            GetSet floor_item = new GetSet();
                            floor_item.setInt_Item_1(int_FloorID_temp);
                            floor_item.setString_Item_1(FloorName);
                            floorlist.add(floor_item);
                        }
                        ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(IssueLocation.this,R.layout.layout_spinner_black, floorlist);
                        adapter.setDropDownViewResource(R.layout.layout_spinner_textview);
                        spinner_floor.setAdapter(adapter);
                        spinner_floor.setSelection(0);
                        //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }*/
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



    public class WebService_Wing extends AsyncTask<String, String, String> {
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
            ht.put("LocationID", string_locationid);

            String[] keys = { "CompanyID","FloorID"};
            String[] values = {""+int_companyid,""+int_floorid};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                result = new NetworkCall(IssueLocation.this).postDataToSOAPService(ht, "GetWingDetails");
                Log.i("data:","GetWingDetails:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                    //JSONArray jsonarray_dashboard = new JSONArray(result);
                    try {
                        winglist.clear();
                        JSONArray jsonArray_wing = new JSONArray(result);
                        for(int i=0;i<jsonArray_wing.length();i++)
                        {
                            String WingID = jsonArray_wing.getJSONObject(i).getString("WingID");
                            String WingName = jsonArray_wing.getJSONObject(i).getString("WingName");
                            int int_WingID_temp = Integer.parseInt(WingID);
                            GetSet wing_item = new GetSet();
                            wing_item.setInt_Item_1(int_WingID_temp);
                            wing_item.setString_Item_1(WingName);
                            winglist.add(wing_item);
                        }
                        ArrayAdapter<GetSet> adapter = new ArrayAdapter<GetSet>(IssueLocation.this,R.layout.layout_spinner_black, winglist);
                        adapter.setDropDownViewResource(R.layout.layout_spinner_textview);
                        spinner_wing.setAdapter(adapter);
                        spinner_wing.setSelection(0);
                        //String tring_locationid = ""+locationlist.get(0).getInt_Item_1();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        /*if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();

                        }*/
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


    public class IssueSubmitService extends AsyncTask<String, Void, String> {

        String desc_text = editText_comments.getText().toString();
        String responsedata="";
        String issueresult="";

        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueLocation.this);
            pDialog.setMessage(string_PROGRESS_LOADING);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            responsedata= WebService.SumbitIssues
                    (
                            int_companyid, int_groupid, int_locationid, int_buildingid, int_floorid, int_wingid, int_dept_id, int_issue_id,
                            desc_text, int_priority_id, string_responsetime_sp, int_userid, int_request_typeid,UserName_SP
                    );

            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Issue", result);
            if(pDialog.isShowing()){

                issueresult = result;

                try {
                    JSONObject json_obj=new JSONObject(issueresult);
/*
                    {
                            "Status": true,
                            "Message": "#SR17921111070035 - ticket raised successfully",
                            "CompliantId": 70030
                    }*/

                    boolean status = json_obj.getBoolean("Status");
                    CompliantId = ""+json_obj.getInt("CompliantId");
                    if(status == true)
                    {
                        /*if(temp.length() > 10) {
                            new WebService_UpdateImage().execute();
                        }else {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Ticket Rised Successfully ", 2000).show();
                            Intent ii9i=new Intent(IssueLocation.this,Finish.class);
                            ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ii9i);
                        }*/
                        //pDialog.dismiss();
                        if(temp.length() > 10) {
                            new WebService_UpdateImage().execute();
                        }
                        else
                        {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), string_PROGRESS_SUCCESS, 2000).show();
                            Intent ii9i=new Intent(IssueLocation.this,Finish.class);
                            ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ii9i);
                        }
                       /* Toast.makeText(getApplicationContext(), "Ticket Rised Successfully ", 2000).show();
                        Intent ii9i=new Intent(IssueLocation.this,Finish.class);
                        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii9i);*/
                    }
                    else
                    {
                        pDialog.dismiss();
                        String message = json_obj.getString("Message");
                        Toast.makeText(getApplicationContext(), ""+message, 2000).show();
                    }
                }
                catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_somethingwrong), 2000).show();

                }
            }
            super.onPostExecute(result);
        }
    }


    public class WebService_UpdateImage extends AsyncTask<String, String, String> {
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
            ht.put("LocationID", string_locationid);

            String[] keys = { "compliantid","bas64Image","filetype"};
            String[] values = {""+CompliantId,temp,string_filetype};

            //Put key and value into hashTable
            ht.clear();
            for (int i = 0; i < keys.length; i++)
            {
                ht.put(keys[i], values[i]);
                Log.i("Data Sent",""+keys[i]+" : "+ values[i]);
            }

            String result = "";
            try {
                //result = new NetworkCall(IssueLocation.this).postDataToSOAPService(ht, "UpdateImage");
                result = new NetworkCall(IssueLocation.this).postDataToSOAPService(ht, "UploadFile");
                Log.i("data:","UploadFile:"+result);
            } catch (Exception ee) {
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                try {
                    Log.i("data:", "" + result);
                    //JSONArray jsonarray_dashboard = new JSONArray(result);
                    try {
                        JSONObject json_obj=new JSONObject(result);
                        pDialog.dismiss();
                        boolean status = json_obj.getBoolean("Status");
                        if(status == true)
                        {
                            Toast.makeText(getApplicationContext(), "Ticket Rised Successfully ", 2000).show();
                            Intent ii9i=new Intent(IssueLocation.this,Finish.class);
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
                        pDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Ticket Rise Failed!\nTry Again", 2000).show();

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
                    storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
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
                        //intentData inputStream = get.getContentResolver().openInputStream(intentData.getData());
                        Uri uri = intentData.getData();
                        try {
                            File file_docs = new File(uri.getPath());
                            //File file_docs = new File(uri.getPath());
                            int size = (int) file_docs.length();

                            ContentResolver cR = getApplicationContext().getContentResolver();
                            //string_filetype = cR.getType(uri);
                            //string_filetype =  FilenameUtils.getExtension();
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
                        //Display an error
                        return;
                    }
                    else
                    {
                        //intentData inputStream = get.getContentResolver().openInputStream(intentData.getData());
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
                            imageView_camera.setImageBitmap(bitmap);
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
                            //string_filetype = cR.getType(uri);
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
                                imageView_camera.setImageBitmap(objBitmap);
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
                //}
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
}
