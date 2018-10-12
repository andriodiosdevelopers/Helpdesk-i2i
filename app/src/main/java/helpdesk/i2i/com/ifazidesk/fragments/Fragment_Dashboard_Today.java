package helpdesk.i2i.com.ifazidesk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Today_Status;
import helpdesk.i2i.com.ifazidesk.adapter.RecyclerViewAdapter_Dashboard_Today_Tickets;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.DashboardData;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.StatusdetailsItem;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.TicketdetailsItem;
import helpdesk.i2i.com.ifazidesk.webservice.APIClient;
import helpdesk.i2i.com.ifazidesk.webservice.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Dashboard_Today extends Fragment
{
    TextView textview_today_date;
    RecyclerView recyclerView_today_status;
    RecyclerView recyclerView_today_tickets;
    String TAG="Fragment_Today";
    APIInterface apiService;
    String string_userid = "";
    String string_companyid = "";
    String string_locationid = "";
    String string_startdate = "";
    String string_enddate = "";
    String string_date = "";
    static String ARG_PARAM1 = "DashboardData";
    List<DashboardData> List_TodayData;

    public Fragment_Dashboard_Today() {
        // Required empty public constructor
    }

    public static Fragment_Dashboard_Today newInstance(Serializable param1) {
        Fragment_Dashboard_Today fragment = new Fragment_Dashboard_Today();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            List_TodayData = (List<DashboardData>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_dashboard_today, container, false);

        apiService = APIClient.getClient().create(APIInterface.class);

        textview_today_date = (TextView) rootView.findViewById(R.id.textview_date);
        textview_today_date.setText("Today");

        //spinner_location.setSelection(0);
        recyclerView_today_status = (RecyclerView) rootView.findViewById(R.id.recyclerView_status);
        recyclerView_today_tickets = (RecyclerView) rootView.findViewById(R.id.recyclerView_tickets);
        ImageView imageview_refresh = (ImageView) rootView.findViewById(R.id.imageview_refresh);
        textview_today_date.setVisibility(View.GONE);

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


        if(List_TodayData.get(0).getStatusdetails().size() > 0)
        {
            RecyclerViewAdapter_Dashboard_Today_Status adapter_department = new RecyclerViewAdapter_Dashboard_Today_Status
                    (getActivity(), List_TodayData.get(0).getStatusdetails());

            adapter_department.notifyDataSetChanged();
            recyclerView_today_status.setAdapter(adapter_department);

            if(List_TodayData.get(0).getStatusdetails().size() > 0)
            {
                RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                        (getActivity(), List_TodayData.get(0).getTicketdetails(), false);
                adapter_ticket.notifyDataSetChanged();
                recyclerView_today_tickets.setAdapter(adapter_ticket);
            }
        }

//        textview_today_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Calendar c = Calendar.getInstance();
//                DatePickerDialog da =  new DatePickerDialog(getActivity(), date_today,
//                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
//                        .get(Calendar.DAY_OF_MONTH));
//                //Date newDate = c.getTime();
//                //da.getDatePicker().setMinDate(newDate.getTime());
//                da.show();
//            }
//        });

//        imageview_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                refreshdialog = ProgressDialog.show(DashboardNew.this, "Dashboard Data", "Loading...\nPlease Wait");
//                refreshdialog.setCancelable(false);
//                refreshdialog.show();
//            }
//        });
//
//        //new WebService_DashboardData_today().execute();
//LoadDashboard_Today();
        return rootView;
    }

//    DatePickerDialog.OnDateSetListener date_today = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//
//            cday = dayOfMonth;
//            cmonth = monthOfYear + 1;
//            cyear = year;
//            String str_day = ""+cday;
//            String str_month = ""+cmonth;
//            if(cday < 10)
//            {
//                str_day = "0"+cday;
//            }
//            if(cmonth < 10)
//            {
//                str_month = "0"+cmonth;
//            }
//            textview_today_date.setText(str_day + "/" + str_month + "/" + cyear);
//            string_date = str_month + "/" + str_day + "/" + cyear;
//            new DashboardNew.WebService_DashboardData_today().execute();
//        }
//    };

    public void LoadDashboard_Today(String string_locationid,String string_companyid)
    {

        Call<DashboardData> call_dashboardtoday = apiService.GetDashboardDetails(string_userid,string_startdate,string_enddate,string_companyid,string_locationid);
        Log.i(TAG,"UserID:"+string_userid+" SDate:"+string_startdate+" EDate:"+string_enddate+" CompanyID:"+string_companyid+"LocationID:"+string_locationid);
        call_dashboardtoday.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                int statusCode = response.code();

                List<StatusdetailsItem> StatusDetailsItem_Today = response.body().getStatusdetails();
                List<TicketdetailsItem> TicketDetails_Today = response.body().getTicketdetails();
                //TicketDetails_Today_temp = response.body().getTicketDetailsData();

                if(StatusDetailsItem_Today.size() > 0)
                {
                    RecyclerViewAdapter_Dashboard_Today_Status adapter_department = new RecyclerViewAdapter_Dashboard_Today_Status
                            (getActivity(), response.body().getStatusdetails());

                    adapter_department.notifyDataSetChanged();
                    recyclerView_today_status.setAdapter(adapter_department);

                    if(TicketDetails_Today.size() > 0)
                    {
                        RecyclerViewAdapter_Dashboard_Today_Tickets adapter_ticket = new RecyclerViewAdapter_Dashboard_Today_Tickets
                                (getActivity(), response.body().getTicketdetails(), false);
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


}