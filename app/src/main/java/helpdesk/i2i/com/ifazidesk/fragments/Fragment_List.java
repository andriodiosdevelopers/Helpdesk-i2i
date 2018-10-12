package helpdesk.i2i.com.ifazidesk.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.DashboardNew;
import helpdesk.i2i.com.ifazidesk.activities.PreDashboard;
import helpdesk.i2i.com.ifazidesk.activities.SelectLocation;
import helpdesk.i2i.com.ifazidesk.adapter.ListViewAdapter_Location;
import helpdesk.i2i.com.ifazidesk.getset.BuildingdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.CompanydetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.FloordetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.GetSet;
import helpdesk.i2i.com.ifazidesk.getset.Get_Location;
import helpdesk.i2i.com.ifazidesk.getset.LocationdetailsItem;
import helpdesk.i2i.com.ifazidesk.getset.WingdetailsItem;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class Fragment_List extends android.support.v4.app.DialogFragment implements
        OnItemClickListener {


    ArrayAdapter<String> countrylistadapter;
    int intLocationid = 0;
    String strCountry = "";
    public static String List_Item_Name = "Location";
    public static String List_Item_ID = "LocationID";
    ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String, String>> locationlist = new ArrayList<HashMap<String, String>>();
    EditText inputSearch;
    ListViewAdapter_Location newadapter;
    String string_companyid = "";
    ArrayList<String> array_locationlist;
    ArrayList<Get_Location> getset_locationlist;
    int int_companyid = 0;
    int int_locationid = 0;
    int int_buildingid = 0;
    int int_floorid = 0;
    int int_wingid = 0;

    ListView listview_items;
    ProgressDialog pDialog;
    final static String ARG_PARAM1 = "ListData";
    final static String ARG_PARAM2 = "SearchTitle";
    final static String ARG_PARAM3 = "ListType";
    final static String ARG_PARAM4 = "ID";
    List<GetSet> getset_List;
    String string_ListData = "";
    String string_SearchTitle = "";
    String string_ListType = "";
    String string_ID = "";
    private Realm mRealm;
    RealmResults<CompanydetailsItem> realmResults_company;
    RealmResults<LocationdetailsItem> realmResults_location;
    RealmResults<BuildingdetailsItem> realmResults_building;
    RealmResults<FloordetailsItem> realmResults_floor;
    RealmResults<WingdetailsItem> realmResults_wing;
//    RealmResults<Model_Company> realm_companylist;
//    RealmResults<Model_Location> realm_locationlist;
//    RealmResults<Model_Building> realm_buildinglist;
//    RealmResults<Model_Floor> realm_floorlist;
//    RealmResults<Model_Wing> realm_winglist;

   /* public static Fragment_List newInstance( String getSetList, String searchString, String ListType,String ID) {
        Fragment_List fragment = new Fragment_List();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PARAM1, getSetList);
        bundle.putString(ARG_PARAM2, searchString);
        bundle.putString(ARG_PARAM3, ListType);
        bundle.putString(ARG_PARAM4, ID);
        fragment.setArguments(bundle);
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            //getset_List = (List<GetSet>) getArguments().getSerializable(ARG_PARAM1);
            string_ListData = getArguments().getString(ARG_PARAM1);
            string_SearchTitle = getArguments().getString(ARG_PARAM2);
            string_ListType = getArguments().getString(ARG_PARAM3);
            string_ID = getArguments().getString(ARG_PARAM4);
        }

        Realm.init(getActivity());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("ifazidesk_db.realm")
                .schemaVersion(0)
                .build();
        mRealm = Realm.getInstance(realmConfig);

//        mRealm.beginTransaction();
        realmResults_company = mRealm.where(CompanydetailsItem.class).findAllAsync();
        realmResults_company.load();
        //mRealm.commitTransaction();
//        realmResults_location = mRealm.where(LocationdetailsItem.class).findAllAsync();
//        realmResults_location.load();
//
//        realmResults_building = mRealm.where(BuildingdetailsItem.class).findAllAsync();
//        realmResults_building.load();
//
//        realmResults_floor = mRealm.where(FloordetailsItem.class).findAllAsync();
//        realmResults_floor.load();
//
//        realmResults_wing = mRealm.where(WingdetailsItem.class).findAllAsync();
//        realmResults_wing.load();




        View view = inflater.inflate(R.layout.layout_fragment_list, null, false);
        listview_items = (ListView) view.findViewById(R.id.list);

        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        inputSearch.setHint("Search "+string_SearchTitle);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        try {

            if(string_ListType.equals("isDashboardCompanyList"))
            {
                //JSONArray json_array = new JSONArray(string_ListData);
                locationlist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> allcompanymap = new HashMap<String, String>();
                allcompanymap.put(List_Item_Name, "All Company");
                allcompanymap.put(List_Item_ID, "0");
                locationlist.add(allcompanymap);
                for (int i = 0; i < realmResults_company.size(); i++)
                {
                    int CompanyId = realmResults_company.get(i).getCompanyId();
                    String Company = realmResults_company.get(i).getCompany();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, Company);
                    map.put(List_Item_ID, ""+CompanyId);
                    locationlist.add(map);
                }
//                for (int i = 0; i < json_array.length(); i++)
//                {
//                    int CompanyId = json_array.getJSONObject(i).getInt("CompanyId");
//                    String Company = json_array.getJSONObject(i).getString("Company");
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, Company);
//                    map.put(List_Item_ID, ""+CompanyId);
//                    locationlist.add(map);
//                }
            }

            if(string_ListType.equals("isDashboardLocationList"))
            {
                int_companyid = Integer.parseInt(string_ID);
                //JSONArray json_array = new JSONArray(string_ListData);
                locationlist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> alllocationmap = new HashMap<String, String>();
                alllocationmap.put(List_Item_Name, "All Locations");
                alllocationmap.put(List_Item_ID, "0");
                locationlist.add(alllocationmap);

                if(int_companyid == 0)
                {

                }
                else
                {
                    //mRealm.beginTransaction();
                    realmResults_location = mRealm.where(LocationdetailsItem.class).equalTo("companyId",int_companyid).findAll();
                    realmResults_location.load();
                    //mRealm.commitTransaction();

                    for(int i=0;i<realmResults_location.size();i++)
                    {
                        int CompanyId = realmResults_location.get(i).getCompanyId();
                        int LocationID = realmResults_location.get(i).getLocationId();
                        String LocationName = realmResults_location.get(i).getLocation();
                        GetSet location_item = new GetSet();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(List_Item_Name, LocationName);
                        map.put(List_Item_ID, ""+LocationID);
                        locationlist.add(map);
//                        if(CompanyId == int_companyid)
//                        {
//                            locationlist.add(map);
//                        }

                    }
//                    for(int i=0;i<json_array.length();i++)
//                    {
//                        int CompanyId = json_array.getJSONObject(i).getInt("CompanyId");
//                        int LocationID = json_array.getJSONObject(i).getInt("LocationId");
//                        String LocationName = json_array.getJSONObject(i).getString("Location");
//                        GetSet location_item = new GetSet();
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(List_Item_Name, LocationName);
//                        map.put(List_Item_ID, ""+LocationID);
//                        if(CompanyId == int_companyid)
//                        {
//                            locationlist.add(map);
//                        }
//                    }
                }

            }
            if(string_ListType.equals("isMainCompanyList"))
            {
                //JSONArray json_array = new JSONArray(string_ListData);
                locationlist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> allcompanymap = new HashMap<String, String>();
                allcompanymap.put(List_Item_Name, "All Company");
                allcompanymap.put(List_Item_ID, "0");
                locationlist.add(allcompanymap);
                for (int i = 0; i < realmResults_company.size(); i++)
                {
                    int CompanyId = realmResults_company.get(i).getCompanyId();
                    String Company = realmResults_company.get(i).getCompany();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, Company);
                    map.put(List_Item_ID, ""+CompanyId);
                    locationlist.add(map);
                }
//                for (int i = 0; i < json_array.length(); i++)
//                {
//                    int CompanyId = json_array.getJSONObject(i).getInt("CompanyId");
//                    String Company = json_array.getJSONObject(i).getString("Company");
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, Company);
//                    map.put(List_Item_ID, ""+CompanyId);
//                    locationlist.add(map);
//                }
            }
            if(string_ListType.equals("isMainLocationList"))
            {
                int_companyid = Integer.parseInt(string_ID);
                //JSONArray json_array = new JSONArray(string_ListData);
                locationlist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> alllocationmap = new HashMap<String, String>();
                alllocationmap.put(List_Item_Name, "All Location");
                alllocationmap.put(List_Item_ID, "0");
                locationlist.add(alllocationmap);

                if(int_companyid == 0)
                {

                }
                else
                {
                    //mRealm.beginTransaction();
                    realmResults_location = mRealm.where(LocationdetailsItem.class).equalTo("companyId",int_companyid).findAll();
                    realmResults_location.load();
                    //mRealm.commitTransaction();

                    for(int i=0;i<realmResults_location.size();i++)
                    {
                        int CompanyId = realmResults_location.get(i).getCompanyId();
                        int LocationID = realmResults_location.get(i).getLocationId();
                        String LocationName = realmResults_location.get(i).getLocation();
                        GetSet location_item = new GetSet();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(List_Item_Name, LocationName);
                        map.put(List_Item_ID, ""+LocationID);
                        locationlist.add(map);
//                        if(CompanyId == int_companyid)
//                        {
//                            locationlist.add(map);
//                        }

                    }
//                    for(int i=0;i<json_array.length();i++)
//                    {
//                        int CompanyId = json_array.getJSONObject(i).getInt("CompanyId");
//                        int LocationID = json_array.getJSONObject(i).getInt("LocationId");
//                        String LocationName = json_array.getJSONObject(i).getString("Location");
//                        GetSet location_item = new GetSet();
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(List_Item_Name, LocationName);
//                        map.put(List_Item_ID, ""+LocationID);
//                        if(CompanyId == int_companyid)
//                        {
//                            locationlist.add(map);
//                        }
//                    }
                }

            }
            if(string_ListType.equals("isCompanyList"))
            {
                locationlist.clear();
                locationlist = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < realmResults_company.size(); i++)
                {
                    int CompanyId = realmResults_company.get(i).getCompanyId();
                    String Company = realmResults_company.get(i).getCompany();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, Company);
                    map.put(List_Item_ID, ""+CompanyId);
                    locationlist.add(map);
                }

                //realm_companylist
                //JSONArray json_array = new JSONArray(string_ListData);
//                locationlist = new ArrayList<HashMap<String, String>>();
//                realm_companylist = mRealm.where(Model_Company.class).findAllAsync();
//                realm_companylist.load();
//                for (int i = 0; i < realm_companylist.size(); i++)
//                {
//                    int CompanyId = realm_companylist.get(i).getCompanyId();
//                    String Company = realm_companylist.get(i).getCompany();
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, Company);
//                    map.put(List_Item_ID, ""+CompanyId);
//                    locationlist.add(map);
//                }
//                for (int i = 0; i < json_array.length(); i++)
//                {
//                    int CompanyId = json_array.getJSONObject(i).getInt("CompanyId");
//                    String Company = json_array.getJSONObject(i).getString("Company");
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, Company);
//                    map.put(List_Item_ID, ""+CompanyId);
//                    locationlist.add(map);
//                }
            }
            if(string_ListType.equals("isLocationList"))
            {
                int_companyid = Integer.parseInt(string_ID);
                locationlist = new ArrayList<HashMap<String, String>>();
                locationlist.clear();


                //mRealm.beginTransaction();
                realmResults_location = mRealm.where(LocationdetailsItem.class).equalTo("companyId",int_companyid).findAll();
                realmResults_location.load();
                //mRealm.commitTransaction();

                for (int i = 0; i < realmResults_location.size(); i++)
                {
                    int CompanyId = realmResults_location.get(i).getCompanyId();
                    int LocationID = realmResults_location.get(i).getLocationId();
                    String LocationName = realmResults_location.get(i).getLocation();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, LocationName);
                    map.put(List_Item_ID, ""+LocationID);
                    locationlist.add(map);
//                    if(CompanyId == int_companyid)
//                       {
//                            locationlist.add(map);
//                       }
                }
//                realm_locationlist = mRealm.where(Model_Location.class).findAllAsync();
//                realm_locationlist.load();
//                for (int i = 0; i < realm_locationlist.size(); i++)
//                {
//                    int CompanyId = realm_locationlist.get(i).getCompanyId();
//                    int LocationID = realm_locationlist.get(i).getLocationId();
//                    String LocationName = realm_locationlist.get(i).getLocation();
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, LocationName);
//                    map.put(List_Item_ID, ""+LocationID);
//                    if(CompanyId == int_companyid)
//                    {
//                        locationlist.add(map);
//                    }
//                }

//                JSONArray json_array = new JSONArray(string_ListData);
//                locationlist = new ArrayList<HashMap<String, String>>();
//                for(int i=0;i<json_array.length();i++)
//                {
//                    int CompanyId = json_array.getJSONObject(i).getInt("CompanyId");
//                    int LocationID = json_array.getJSONObject(i).getInt("LocationId");
//                    String LocationName = json_array.getJSONObject(i).getString("Location");
//                    GetSet location_item = new GetSet();
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, LocationName);
//                    map.put(List_Item_ID, ""+LocationID);
//                    if(CompanyId == int_companyid)
//                    {
//                        locationlist.add(map);
//                    }
//                }
            }
            if(string_ListType.equals("isBuildingList"))
            {
                int_locationid = Integer.parseInt(string_ID);
//                JSONArray json_array = new JSONArray(string_ListData);
                locationlist = new ArrayList<HashMap<String, String>>();
//                for(int i=0;i<json_array.length();i++)
//                {
//                    int BuildingID = json_array.getJSONObject(i).getInt("BuildingID");
//                    String BuildingName = json_array.getJSONObject(i).getString("BuildingName");
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, BuildingName);
//                    map.put(List_Item_ID, ""+BuildingID);
//                    locationlist.add(map);
//                }

//                locationlist = new ArrayList<HashMap<String, String>>();
//                realm_buildinglist = mRealm.where(Model_Building.class).findAllAsync();
//                realm_locationlist.load();
                locationlist.clear();

                //mRealm.beginTransaction();
                realmResults_building = mRealm.where(BuildingdetailsItem.class).equalTo("locationId",int_locationid).findAll();
                realmResults_building.load();
                //mRealm.commitTransaction();

                for (int i = 0; i < realmResults_building.size(); i++)
                {
                    int LocationID = realmResults_building.get(i).getLocationId();
                    int BuildingID = realmResults_building.get(i).getBuildingID();
                    String BuildingName = realmResults_building.get(i).getBuilding();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, BuildingName);
                    map.put(List_Item_ID, ""+BuildingID);
                    locationlist.add(map);
//                    if(LocationID == int_locationid)
//                    {
//                        locationlist.add(map);
//                    }
                }


            }
            if(string_ListType.equals("isFloorList"))
            {
//                int_companyid = Integer.parseInt(string_ID);
//                JSONArray json_array = new JSONArray(string_ListData);
//                locationlist = new ArrayList<HashMap<String, String>>();
//                for(int i=0;i<json_array.length();i++)
//                {
//                    int FloorID = json_array.getJSONObject(i).getInt("FloorID");
//                    String FloorName = json_array.getJSONObject(i).getString("FloorName");
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, FloorName);
//                    map.put(List_Item_ID, ""+FloorID);
//                    locationlist.add(map);
//                }

                int_buildingid = Integer.parseInt(string_ID);
                locationlist = new ArrayList<HashMap<String, String>>();
//                realm_floorlist = mRealm.where(Model_Floor.class).findAllAsync();
//                realm_floorlist.load();

                //mRealm.beginTransaction();
                realmResults_floor = mRealm.where(FloordetailsItem.class).equalTo("buildingId",int_buildingid).findAll();
                realmResults_floor.load();
                //mRealm.commitTransaction();

                for (int i = 0; i < realmResults_floor.size(); i++)
                {
                    int BuildingID = realmResults_floor.get(i).getBuildingId();
                    int FloorID = realmResults_floor.get(i).getFloorID();
                    String FloorName = realmResults_floor.get(i).getFloor();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, FloorName);
                    map.put(List_Item_ID, ""+FloorID);
                    if(BuildingID == int_buildingid)
                    {
                        locationlist.add(map);
                    }
                }

            }
            if(string_ListType.equals("isWingList"))
            {
                int_floorid = Integer.parseInt(string_ID);
//                JSONArray json_array = new JSONArray(string_ListData);
//                locationlist = new ArrayList<HashMap<String, String>>();
//                for(int i=0;i<json_array.length();i++)
//                {
//                    int WingID = json_array.getJSONObject(i).getInt("WingID");
//                    String WingName = json_array.getJSONObject(i).getString("WingName");
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(List_Item_Name, WingName);
//                    map.put(List_Item_ID, ""+WingID);
//                    locationlist.add(map);
//                }

                int_floorid = Integer.parseInt(string_ID);
                locationlist = new ArrayList<HashMap<String, String>>();
//                realm_winglist = mRealm.where(Model_Wing.class).findAllAsync();
//                realm_winglist.load();

                //mRealm.beginTransaction();
                realmResults_wing = mRealm.where(WingdetailsItem.class).equalTo("floorId",int_floorid).findAll();
                realmResults_wing.load();
                //mRealm.commitTransaction();
                for (int i = 0; i < realmResults_wing.size(); i++)
                {
                    int FloorID = realmResults_wing.get(i).getFloorId();
                    int WingID = realmResults_wing.get(i).getWingID();
                    String WingName = realmResults_wing.get(i).getWing();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, WingName);
                    map.put(List_Item_ID, ""+WingID);
                    if(int_floorid == FloorID)
                    {
                        locationlist.add(map);
                    }
                }

            }
            searchResults = new ArrayList<HashMap<String, String>>(locationlist);
            newadapter = new ListViewAdapter_Location(getActivity(), locationlist);
            listview_items.setAdapter(newadapter);
            newadapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        listview_items.setOnItemClickListener(this);

        searchResults = new ArrayList<HashMap<String, String>>(locationlist);
        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString().toLowerCase();
                Log.i("Searching", "");
                int textLength = searchString.length();
                searchResults.clear();

                for (int i = 0; i < locationlist.size(); i++) {
                    try {
                        String VechNo = locationlist.get(i).get("Location").toString().toLowerCase();
                        if (textLength <= VechNo.length()) {

                            if (VechNo.contains(searchString)) {
                                searchResults.add(locationlist.get(i));
                                newadapter = new ListViewAdapter_Location(getActivity(), searchResults);
                                listview_items.setAdapter(newadapter);
                                newadapter.notifyDataSetChanged();
                            }
                        }

                    } catch (Exception ee) {

                    }
                }
                try {

                    if (searchResults.isEmpty()) {

                    }
                } catch (Exception ee) {

                }
                try {
                    newadapter.notifyDataSetChanged();
                } catch (Exception ee) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                System.out.println("before changed");

            }

            public void afterTextChanged(Editable s) {


                System.out.println("after changed");
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String temp_cfsname;
        String temp_cfsid;
        temp_cfsname = ((TextView) view.findViewById(R.id.product_name)).getText().toString();
        temp_cfsid = ((TextView) view.findViewById(R.id.product_id)).getText().toString();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        if(string_ListType.equals("isDashboardCompanyList"))
        {
            PreDashboard dashboard = (PreDashboard) getActivity();
            dashboard.SetCompany(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isDashboardLocationList"))
        {
            PreDashboard dashboard = (PreDashboard) getActivity();
            dashboard.SetLocation(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isMainCompanyList"))
        {
            DashboardNew dashboard = (DashboardNew) getActivity();
            dashboard.SetCompany(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isMainLocationList"))
        {
            DashboardNew dashboard = (DashboardNew) getActivity();
            dashboard.SetLocation(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isCompanyList"))
        {
            SelectLocation mainactivity = (SelectLocation) getActivity();
            mainactivity.SetCompany(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isLocationList"))
        {
            SelectLocation mainactivity = (SelectLocation) getActivity();
            mainactivity.SetLocation(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isBuildingList"))
        {
            SelectLocation mainactivity = (SelectLocation) getActivity();
            mainactivity.SetBuilding(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isFloorList"))
        {
            SelectLocation mainactivity = (SelectLocation) getActivity();
            mainactivity.SetFloor(temp_cfsname, temp_cfsid);
        }
        if(string_ListType.equals("isWingList"))
        {
            SelectLocation mainactivity = (SelectLocation) getActivity();
            mainactivity.SetWing(temp_cfsname, temp_cfsid);
        }
        dismiss();
    }

    public void SetCompanyId(int companyid) {
        int_companyid = companyid;
    }

    public class GetLocation extends AsyncTask<String, Void, String> {

        String responsedata = "";
        String deptresult = "";


        @Override
        protected void onPreExecute() {
            Log.i("in pre", "in pre");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading..\nPlease wait...");
            pDialog.setCancelable(true);
            pDialog.show();
            getset_locationlist = new ArrayList<Get_Location>();
            array_locationlist = new ArrayList<String>();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.i("Value inserted", "Value inserted");
            responsedata = WebService.GetLocationIssues(int_companyid);
            return responsedata;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result for Location", result);
            pDialog.dismiss();
            deptresult = result;

		/*
        [
			{
			"LocationID":15,
			"LocationName":"Ferns Icon",
			"LocationShortName":"FI"
			}
		]
		*/

            try {

                JSONArray json_array = new JSONArray(result);
                locationlist = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < json_array.length(); i++) {
                    String LocationID = json_array.getJSONObject(i).getString("LocationID");
                    String LocationName = json_array.getJSONObject(i).getString("LocationName");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(List_Item_Name, LocationName);
                    map.put(List_Item_ID, LocationID);
                    locationlist.add(map);
                }
                searchResults = new ArrayList<HashMap<String, String>>(locationlist);
                newadapter = new ListViewAdapter_Location(getActivity(), locationlist);
                listview_items.setAdapter(newadapter);
                newadapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();

            }


            super.onPostExecute(result);
        }
    }


}
