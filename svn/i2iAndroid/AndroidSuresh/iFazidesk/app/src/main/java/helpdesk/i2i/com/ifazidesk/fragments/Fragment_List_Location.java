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

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.IssueLocation;
import helpdesk.i2i.com.ifazidesk.adapter.ListViewAdapter_Location;
import helpdesk.i2i.com.ifazidesk.getset.Get_Location;
import helpdesk.i2i.com.ifazidesk.webservice.WebService;


public class Fragment_List_Location extends android.support.v4.app.DialogFragment implements
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

    ListView listview_items;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_list, null, false);
        listview_items = (ListView) view.findViewById(R.id.list);

        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        inputSearch.setHint("Search Location");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        locationlist = new ArrayList<HashMap<String, String>>();
        getset_locationlist = new ArrayList<Get_Location>();

        new GetLocation().execute();
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
        IssueLocation mainactivity = (IssueLocation) getActivity();
        mainactivity.SetLocation(temp_cfsname, temp_cfsid);
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
