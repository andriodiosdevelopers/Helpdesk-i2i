package helpdesk.i2i.com.ifazidesk.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.IssueList_ViewAdapter;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Issue;
import helpdesk.i2i.com.ifazidesk.datamodel.issue.IssueItem;
import helpdesk.i2i.com.ifazidesk.datamodel.issue.IssueItemList;
import helpdesk.i2i.com.ifazidesk.getset.Get_Issues;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.utils.ImageLoader;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueList extends AppCompatActivity {
    private ProgressDialog pDialog;
    public Preferences prefs;

    ArrayList<Get_Issues> getset_issuelist;
    public static String PPID = "CategoryName";
    public static String PIID = "IssueID";
    public static String PNAME = "Priority";
    public static String PID = "PriorityID";
    public static String RPT = "ResponseTime";
    public static String RST = "ResolutionTime";
    public static String IMG = "CategoryLogoFileName";

    int int_compid;
    int int_deptid;
    String string_issueid;
    int int_issueid;
    ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String, String>> array_issuelist = new ArrayList<HashMap<String, String>>();
    EditText editText_search;
    ListView listview_issuelist;
    //IssueList_ViewAdapter newadapter;
    RecyclerViewAdapter_Issue newadapter;
    IssueList_ViewAdapter lazyadapter;
    RecyclerView recyclerview_issue;
    ImageView toolbarImage;
    ImageLoader imageLoader;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String string_Language = "";

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

    APIInterface apiService;
    String TAG ="IssueList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_issue_list);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


        //getSupportActionBar().setCustomView(R.layout.actionbar);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);

        apiService = APIClient.getClient().create(APIInterface.class);
        prefs = new Preferences(getApplicationContext());
        imageLoader = new ImageLoader(getApplicationContext());
        array_issuelist = new ArrayList<HashMap<String, String>>();
        getset_issuelist = new ArrayList<Get_Issues>();
       // editText_search = (EditText) findViewById(R.id.inputSearch);
        //listview_issuelist =(ListView)findViewById(R.id.listview);
        recyclerview_issue =(RecyclerView) findViewById(R.id.recyclerview_issue);
        toolbarImage = (ImageView) findViewById(R.id.imageview_dept);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        int_compid=(prefs.getInt("CompanyID_SP"));
        int_deptid=(prefs.getInt("DeptID_SP"));
        string_Language = prefs.getString("Language");
        String string_deptname = prefs.getString("DeptName_SP");
        String string_depturl = prefs.getString("DeptURL_SP");
        imageLoader.DisplayImage(string_depturl, toolbarImage);

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

        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Issue");
        getSupportActionBar().setSubtitle(string_deptname);
        //new GetIssues().execute();
        LoadIssue();

        try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("textview_title_issue"))
                {
                    getSupportActionBar().setTitle(langauge);
                    getSupportActionBar().setSubtitle(string_deptname);
                    collapsingToolbarLayout.setTitle(string_deptname);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*listview_issuelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


               String issueid = ((TextView) view.findViewById(R.id.issueid)).getText().toString();

                int int_issueid = Integer.parseInt(issueid);
                String issuename = ((TextView) view.findViewById(R.id.issuename)).getText().toString();
                String priority = ((TextView) view.findViewById(R.id.priority)).getText().toString();
                String  priorityid = ((TextView) view.findViewById(R.id.priority_id)).getText().toString();
                String responsetime = ((TextView) view.findViewById(R.id.responsetime)).getText().toString();


                prefs.setString("IssueName_SP", issuename);
                prefs.setInt("IssueID_SP", int_issueid);
                prefs.setString("Priority_SP", priority);
                prefs.setString("PriorityID_SP", priorityid);
                prefs.setString("ResponseTime_SP", responsetime);

                String string_fromqrcode = prefs.getString("QRCODE");
                if(string_fromqrcode.equals("false"))
                {
                    Intent ii9i=new Intent(IssueItemList.this,IssueLocation.class);
                    ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ii9i);

                }
                if(string_fromqrcode.equals("true")) {
                    Intent ii9i = new Intent(IssueItemList.this, IssueSubmit.class);
                    ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ii9i);
                }

            }
        });
*/
        //searchResults=new ArrayList<HashMap<String, String>>(array_issuelist);
        /*editText_search.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=editText_search.getText().toString();
                Log.i("Searching","");
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<array_issuelist.size();i++)
                {
                    try
                    {
                        String VechNo=array_issuelist.get(i).get("CategoryName").toString().toLowerCase();;
                        System.out.println("Issue name "+VechNo);
                        Log.i("Searching",""+VechNo);
                        if(textLength<=VechNo.length())
                        {
                            //compare the String in EditText with Names in the ArrayList
                            // if(searchString.equalsIgnoreCase(VechNo.substring(0,textLength)))
                            if(VechNo.contains(searchString))
                            {
                                searchResults.add(array_issuelist.get(i));
                                System.out.println("the array list is "+array_issuelist.get(i));
                                newadapter =new RecyclerViewAdapter_Issue(IssueItemList.this, searchResults);
                                newadapter.notifyDataSetChanged();
                                recyclerview_issue.setAdapter(newadapter);

                            }
                        }

                    }
                    catch(Exception ee)
                    {

                    }
                }
                try
                {

                    if(searchResults.isEmpty())
                    {
                    }
                }
                catch(Exception ee)
                {

                }
                try
                {
                    lazyadapter.notifyDataSetChanged();
                }
                catch(Exception ee)
                {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                System.out.println("before changed");

            }

            public void afterTextChanged(Editable s) {


                System.out.println("after changed");
            }
        });*/

    }

    public class GetIssues extends AsyncTask<String, Void, String> {

        String CategoryName = "";
        String PriorityName = "";
        String FloorName = "";
        String WingName = "";
        String DepartmentName = "";
        String responsedata = "";
        String loginresult = "";



        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(IssueList.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Log.i("Value inserted", "Value inserted");
            responsedata= WebService.GetIssues(Integer.parseInt(string_companyid), int_deptid);
            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Barcode", result);
            if(pDialog.isShowing()){
                pDialog.dismiss();
                loginresult = result;
		/*{"CategoryID":141,"DepartmentID":40,"CategoryName":"Stinking smell",
		 "CategoryDescrition":"STKSML","PriorityID":17,"PriorityName":"Standard",
		 "PriorityDescription":"S","ResolutionTime":"1Days : 00 Hours :00Min",
		 "ResponseTime":"0Days : 01 Hours :00Min"
		}*/

                try {

                    JSONArray json_array=new JSONArray(result);
                    for(int i=0;i<json_array.length();i++)
                    {
                        JSONObject jsonobject = json_array.getJSONObject(i);
                        Get_Issues prdcts = new Get_Issues();
                        String CategoryID = jsonobject.getString("CategoryID");
                        String PriorityID = jsonobject.getString("PriorityID");
                        String issuename = jsonobject.getString("CategoryName");
                        String priority  = jsonobject.getString("PriorityName");
                        String responsetime  = jsonobject.getString("ResponseTime");
                        String resolutiontime  = jsonobject.getString("ResolutionTime");
                        String imagelink  = jsonobject.getString("CategoryLogoFileName");
                        Log.i("List Data"," "+CategoryID+" "+PriorityID+" "
                                +issuename+" "+priority+" "
                                +responsetime+" "+resolutiontime);
                        prdcts.setIssueID(CategoryID);
                        prdcts.setIssueName(jsonobject.getString("CategoryName"));
                        prdcts.setPriority(jsonobject.getString("PriorityName"));
                        prdcts.setPriorityID(PriorityID);
                        prdcts.setResponseTime(jsonobject.getString("ResponseTime"));
                        prdcts.setResolutionTime(jsonobject.getString("ResolutionTime"));
                        prdcts.setImage(jsonobject.getString("CategoryLogoFileName"));
                        getset_issuelist.add(prdcts);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(PIID, CategoryID);
                        map.put(PPID, issuename);
                        map.put(PNAME, priority);
                        map.put(PID, PriorityID);
                        map.put(RPT, responsetime);
                        map.put(RST, resolutiontime);
                        map.put(IMG, imagelink);

                        array_issuelist.add(map);
                        newadapter = new RecyclerViewAdapter_Issue(getApplicationContext(), array_issuelist);
                        recyclerview_issue.setAdapter(newadapter);
                        newadapter.notifyDataSetChanged();

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


    public void LoadIssue()
    {
        Call<IssueItemList> call_department = apiService.GetIssueDetails(string_companyid,""+int_deptid);
        Log.i(TAG,"CompanyID:"+string_companyid);
        pDialog = new ProgressDialog(IssueList.this);
        pDialog.setMessage(getResources().getString(R.string.progress_loading));
        pDialog.setCancelable(false);
        pDialog.show();

        call_department.enqueue(new Callback<IssueItemList>()
        {
            @Override
            public void onResponse(Call<IssueItemList> call, Response<IssueItemList> response) {

                List<IssueItem> issueItemList = response.body().getLstIssue();
                array_issuelist.clear();
                for(int i=0;i<issueItemList.size();i++)
                {
                    String CategoryID = ""+issueItemList.get(i).getCategoryID();
                    String PriorityID = ""+issueItemList.get(i).getPriorityID();
                    String issuename = issueItemList.get(i).getCategoryName();
                    String priority  = issueItemList.get(i).getPriorityName();
                    String responsetime  = issueItemList.get(i).getResponseTime();//jsonobject.getString("ResponseTime");
                    String resolutiontime  = issueItemList.get(i).getResolutionTime(); //jsonobject.getString("ResolutionTime");
                    String imagelink  = issueItemList.get(i).getCategoryLogoFileName(); //jsonobject.getString("CategoryLogoFileName");
                    //Log.i("List Data"," "+CategoryID+" "+PriorityID+" " +issuename+" "+priority+" " +responsetime+" "+resolutiontime);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(PIID, CategoryID);
                    map.put(PPID, issuename);
                    map.put(PNAME, priority);
                    map.put(PID, PriorityID);
                    map.put(RPT, responsetime);
                    map.put(RST, resolutiontime);
                    map.put(IMG, imagelink);
                    array_issuelist.add(map);
                }
                newadapter = new RecyclerViewAdapter_Issue(getApplicationContext(), array_issuelist);
                recyclerview_issue.setAdapter(newadapter);
                newadapter.notifyDataSetChanged();

                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<IssueItemList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                pDialog.dismiss();
            }
        });
    }

}

/*
class ViewHolder2 {

    TextView IssueID;
    TextView IssueName;
    TextView PriorityView;
    TextView PriorityIdView;
    TextView ResponseTimeView;
    TextView ResolutionTimeView;
    TextView Hjtokenid;
    TextView Hvtypeid;


    ArrayList<Get_Issues> vehicledashlist;



    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder2 ViewHolder2 = new ViewHolder2();

        if (convertView == null) {
            @SuppressWarnings("null")
            LayoutInflater inflater = (LayoutInflater) convertView.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_issue_images, null);


            TextView IssueID = (TextView) convertView.findViewById(R.id.issueid);
            TextView IssueName= (TextView) convertView.findViewById(R.id.issuename);
            TextView PriorityView = (TextView) convertView.findViewById(R.id.priority);
            TextView PriorityIdView = (TextView) convertView.findViewById(R.id.priority_id);
            TextView ResponseTimeView =(TextView) convertView.findViewById(R.id.responsetime);
            TextView ResolutionTimeView = (TextView) convertView.findViewById(R.id.resolutiontime);



            ViewHolder2.IssueID = IssueID;
            ViewHolder2.IssueName = IssueName;
            ViewHolder2.PriorityView = PriorityView;
            ViewHolder2.PriorityIdView = PriorityIdView;
            ViewHolder2.ResponseTimeView = ResponseTimeView;
            ViewHolder2.ResolutionTimeView = ResolutionTimeView;
            ViewHolder2.Hjtokenid = Hjtokenid;
            ViewHolder2.Hvtypeid = Hvtypeid;
            convertView.setTag(ViewHolder2);

        }
        else

            ViewHolder2=(ViewHolder2)convertView.getTag();

        Get_Issues getveh = vehicledashlist.get(position);

        ViewHolder2.IssueID.setText(getveh.getIssueID());
        ViewHolder2.IssueName.setText(getveh.getIssueName());
        ViewHolder2.PriorityView.setText(getveh.getPriority());
        ViewHolder2.PriorityIdView.setText(getveh.getPriorityID());
        ViewHolder2.ResponseTimeView.setText(getveh.getResponseTime());
        ViewHolder2.ResolutionTimeView.setText(getveh.getResolutionTime());


        return convertView;
    }


}*/
