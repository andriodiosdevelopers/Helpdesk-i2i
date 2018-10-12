package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Department;
import helpdesk.i2i.com.ifazidesk.datamodel.department.DepartmentItem;
import helpdesk.i2i.com.ifazidesk.datamodel.department.DepartmentList;
import helpdesk.i2i.com.ifazidesk.getset.Get_Department;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueDept extends AppCompatActivity {

    ArrayList<HashMap<String, String>> DeptList;
    ArrayList<Get_Department> getset_deptlist;
    ListView listview_deptlist;
    ListAdapter adapter;
    BaseAdapter Listadapter;
    RecyclerViewAdapter_Department newadapter;
    //DeptList_ViewAdapter newadapter;
    public static String PDeptID = "DepartmentID";
    public static String PDeptName = "DepartmentName";
    public static String PDeptDesc = "DepartmentDescription";
    public static String PDeptImage = "DepartmentLogoFileName";
    ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String, String>> array_deptlist = new ArrayList<HashMap<String, String>>();
    EditText editText_search;
    String string_deptid = "";
    String string_deptname = "";
    String string_deptdesc = "";
    int int_deptid = 0;
    int int_compid = 0;
    private ProgressDialog pDialog;
    public Preferences prefs;
    String string_Language = "";
    RecyclerView recyclerView_deptlist;
    String string_companyname;
    String string_companyid;
    String string_locationname;
    String string_locationid;
    String string_buildingname;
    String string_buildingid;
    String string_floorname;
    String string_floorid;
    String string_wingname;
    String string_wingid;
    TextView textview_companyname;
    TextView textview_locationname;
    TextView textview_buildingname;
    TextView textview_floorname;
    TextView textview_wingname;


    APIInterface apiService;
    String TAG ="Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_issue_dept);
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
        getSupportActionBar().setTitle("Select Department");

        prefs = new Preferences (getApplicationContext());
        int_compid = (prefs.getInt("CompanyID_SP"));
        string_Language = prefs.getString("Language");

        apiService = APIClient.getClient().create(APIInterface.class);

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

       /* prefs.setString("selected_CompanyID",string_companyid);
        prefs.setString("selected_CompanyName",string_companyname);
        prefs.setString("selected_LocationID",string_locationid);
        prefs.setString("selected_LocationName",string_locationname);
        prefs.setString("selected_BuildingID",string_buildingid);
        prefs.setString("selected_BuildingName",string_buildingname);
        prefs.setString("selected_FloorID",string_floorid);
        prefs.setString("selected_FloorName",string_floorname);
        prefs.setString("selected_WingID",string_wingid);
        prefs.setString("selected_WingName",string_wingname);*/

        array_deptlist = new ArrayList<HashMap<String, String>>();
        getset_deptlist = new ArrayList<Get_Department>();

        textview_companyname = (TextView) findViewById(R.id.textview_companyname);
        textview_locationname = (TextView) findViewById(R.id.textview_locationname);
        textview_buildingname = (TextView) findViewById(R.id.textview_buildingname);
        textview_floorname = (TextView) findViewById(R.id.textview_floorname);
        textview_wingname = (TextView) findViewById(R.id.textview_wingname);
        textview_companyname.setText(string_companyname);
        textview_locationname.setText(string_locationname);
        textview_buildingname.setText(string_buildingname);
        textview_floorname.setText(string_floorname);
        textview_wingname.setText(string_wingname);

        editText_search = (EditText) findViewById(R.id.edittext_search);
        //listview_deptlist=(ListView)findViewById(R.id.listview_dept);
        recyclerView_deptlist = (RecyclerView) findViewById(R.id.recyclerview_dept);

        try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("textview_title_department"))
                {
                    getSupportActionBar().setTitle(langauge);
                }
                if(Control.equals("edittext_hint_searchdepartment"))
                {
                    editText_search.setHint(langauge);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //new GetDept().execute();
        LoadDepartment();
        /*listview_deptlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String deptid = ((TextView) view.findViewById(R.id.deptid)).getText().toString();
                int int_deptid = Integer.parseInt(deptid);
                String deptname = ((TextView) view.findViewById(R.id.deptname)).getText().toString();
                String deptdesc = ((TextView) view.findViewById(R.id.deptdesc)).getText().toString();

                prefs.setString("DeptName_SP", deptname);
                prefs.setInt("DeptID_SP", int_deptid);
                prefs.setString("DeptDesc_SP", deptdesc);

                prefs.setString("QRCODE","false");
                Intent ii9i=new Intent(IssueDept.this,IssueItemList.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });*/

        searchResults=new ArrayList<HashMap<String, String>>(array_deptlist);
        editText_search.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=editText_search.getText().toString().toLowerCase();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<array_deptlist.size();i++)
                {
                    try
                    {
                        String VechNo=array_deptlist.get(i).get("DepartmentName").toString().toLowerCase();
                        System.out.println("Department name "+VechNo);
                        Log.i("Searching",""+VechNo);
                        if(textLength<=VechNo.length())
                        {
                            //compare the String in EditText with Names in the ArrayList
                            // if(searchString.equalsIgnoreCase(VechNo.substring(0,textLength)))
                            if(VechNo.contains(searchString))
                            {
                                searchResults.add(array_deptlist.get(i));
                                newadapter = new RecyclerViewAdapter_Department(IssueDept.this, searchResults);
                                //listview_deptlist.setAdapter(newadapter);
                                newadapter.notifyDataSetChanged();
                                recyclerView_deptlist.setAdapter(newadapter);
                            }
                        }

                    }
                    catch(Exception ee)
                    {

                    }
                }
                try
                {
                    if(searchResults.isEmpty()){
                    }
                }
                catch(Exception ee)
                {
                }
                try
                {
                    newadapter.notifyDataSetChanged();
                }
                catch(Exception ee)
                {
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            }

            public void afterTextChanged(Editable s)
            {
            }
        });


    }


    public class GetDept extends AsyncTask<String, Void, String> {

        String responsedata = "";
        String deptresult = "";


        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueDept.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Log.i("Value inserted", "Value inserted");
            responsedata= WebService.GetDeptIssues(Integer.parseInt(string_companyid));
            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Dept", result);
            if(pDialog.isShowing()){
                pDialog.dismiss();
                deptresult = result;

		/*
		06-22 13:17:31.071: I/Result for Dept(2079):
		[
		{"DepartmentID":40,
		 "DepartmentName":"Wash Rooms Services",
		 "DepartmentDescription":"WRS"}
		]
		*/

                try {

                    JSONArray json_array=new JSONArray(result);
                    for(int i=0;i<json_array.length();i++)
                    {
                        JSONObject jsonobject = json_array.getJSONObject(i);

                        Get_Department dept = new Get_Department();
                        dept.setDeptID(jsonobject.getString("DepartmentID"));
                        dept.setDeptName(jsonobject.getString("DepartmentName"));
                        dept.setDeptDesc(jsonobject.getString("DepartmentDescription"));
                        dept.setImage(jsonobject.getString("DepartmentLogoFileName"));
                        getset_deptlist.add(dept);


                        String DepartmentID = jsonobject.getString("DepartmentID");
                        String DepartmentName = jsonobject.getString("DepartmentName");
                        String DepartmentDescription = jsonobject.getString("DepartmentDescription");
                        String DepartmentImage = jsonobject.getString("DepartmentLogoFileName");

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(PDeptID, DepartmentID);
                        map.put(PDeptName, DepartmentName);
                        map.put(PDeptDesc, DepartmentDescription);
                        map.put(PDeptImage, DepartmentImage);
                        array_deptlist.add(map);

                        searchResults=new ArrayList<HashMap<String, String>>(array_deptlist);
                        newadapter = new RecyclerViewAdapter_Department(IssueDept.this, array_deptlist);
                        //listview_deptlist.setAdapter(newadapter);
                        newadapter.notifyDataSetChanged();
                        recyclerView_deptlist.setAdapter(newadapter);

                        if(pDialog.isShowing()){
                            pDialog.dismiss();
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }

    public void LoadDepartment()
    {
        Call<DepartmentList> call_department = apiService.GetDepartmentDetails(string_companyid);
        Log.i(TAG,"CompanyID:"+string_companyid);
        pDialog = new ProgressDialog(IssueDept.this);
        pDialog.setMessage(getResources().getString(R.string.progress_loading));
        pDialog.setCancelable(false);
        pDialog.show();

        call_department.enqueue(new Callback<DepartmentList>()
        {
            @Override
            public void onResponse(Call<DepartmentList> call, Response<DepartmentList> response) {

                List<DepartmentItem> departmentItemList = response.body().getLstDepartment();

                array_deptlist.clear();
                for(int i = 0;i<departmentItemList.size();i++)
                {
                    String DepartmentID = ""+departmentItemList.get(i).getDepartmentID();
                    String DepartmentName = departmentItemList.get(i).getDepartmentName();
                    String DepartmentDescription = departmentItemList.get(i).getDepartmentDescription();
                    String DepartmentImage = departmentItemList.get(i).getDepartmentLogoFileName();

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(PDeptID, DepartmentID);
                    map.put(PDeptName, DepartmentName);
                    map.put(PDeptDesc, DepartmentDescription);
                    map.put(PDeptImage, DepartmentImage);
                    array_deptlist.add(map);
                }
                searchResults = new ArrayList<HashMap<String, String>>(array_deptlist);
                newadapter = new RecyclerViewAdapter_Department(IssueDept.this, array_deptlist);
                //listview_deptlist.setAdapter(newadapter);
                newadapter.notifyDataSetChanged();
                recyclerView_deptlist.setAdapter(newadapter);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DepartmentList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                pDialog.dismiss();
            }
        });
    }
}
/*
class ViewHolder3 {

    TextView DeptID;
    TextView DeptName;
    TextView DeptDesc;
    ArrayList<Get_Department> vehicledashlist;
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder3 ViewHolder3 = new ViewHolder3();

        if (convertView == null) {
            @SuppressWarnings("null")
            LayoutInflater inflater = (LayoutInflater) convertView.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_dept_images, null);


            TextView IssueID = (TextView) convertView.findViewById(R.id.deptid);
            TextView IssueName= (TextView) convertView.findViewById(R.id.deptname);
            TextView PriorityView = (TextView) convertView.findViewById(R.id.deptdesc);



            ViewHolder3.DeptID = IssueID;
            ViewHolder3.DeptName = IssueName;
            ViewHolder3.DeptDesc = PriorityView;

            convertView.setTag(ViewHolder3);

        }
        else

            ViewHolder3=(ViewHolder3)convertView.getTag();

        Get_Department getveh = vehicledashlist.get(position);

        ViewHolder3.DeptID.setText(getveh.getDeptID());
        ViewHolder3.DeptName.setText(getveh.getDeptName());
        ViewHolder3.DeptDesc.setText(getveh.getDeptDesc());


        return convertView;
    }*/


//}