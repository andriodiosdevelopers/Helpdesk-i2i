package helpdesk.i2i.com.ifazidesk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.DashboardNew;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.StatusdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;

public class RecyclerViewAdapter_Dashboard_Week_Status extends RecyclerView.Adapter<RecyclerViewAdapter_Dashboard_Week_Status.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    JSONArray data;
    List<StatusdetailsItem> data_StatusdetailsItem;
    HashMap<String, String> resultp = new HashMap<String, String>();
    View view;
    ArrayList<HashMap<String, String>> arraylist_cobilist = new ArrayList<HashMap<String, String>>();
    public Preferences prefs;
    String status_color;
    int statusid = 0;
    //public RecyclerViewAdapter_Dashboard_Week_Status(Context context, JSONArray jsonArray)
    public RecyclerViewAdapter_Dashboard_Week_Status(Context context, List<StatusdetailsItem> StatusdetailsItem)
    {
        this.context = context;
        data_StatusdetailsItem = StatusdetailsItem;
        prefs = new Preferences(context);
    }

    @Override
    public RecyclerViewAdapter_Dashboard_Week_Status.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        prefs = new Preferences(context);
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_dashboard_status, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Dashboard_Week_Status.ViewHolder viewHolder, int i)
    {

        try {

            int statusid = data_StatusdetailsItem.get(i).getStatusid();
            String status = data_StatusdetailsItem.get(i).getStatus();
            int tickets = data_StatusdetailsItem.get(i).getTickets();
            String colorcode = data_StatusdetailsItem.get(i).getColorcode();
            Boolean isTotal = data_StatusdetailsItem.get(i).isIsTotal();

//            int statusid = data.getJSONObject(i).getInt("statusid");
//            String status = data.getJSONObject(i).getString("status");
//            int tickets = data.getJSONObject(i).getInt("tickets");
//            String colorcode = data.getJSONObject(i).getString("colorcode");
//            Boolean isTotal = data.getJSONObject(i).getBoolean("isTotal");

            String color[] = colorcode.split(" - ");
            status_color = color[1];
            Log.i("TextColor","ColorCode:"+status_color);
            viewHolder.textView_statusid.setText(""+statusid);
            viewHolder.textView_statuscount.setText(""+tickets);
            viewHolder.textView_statusname.setText(status);
            viewHolder.textView_istotal.setText(""+isTotal);
            viewHolder.textView_colorcode.setText(""+status_color);
            viewHolder.textView_statuscount.setTextColor(Color.parseColor(status_color));
            viewHolder.textView_statusname.setTextColor(Color.parseColor(status_color));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        //return data.length();
        return data_StatusdetailsItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_statusid;
        TextView textView_statuscount;
        TextView textView_statusname;
        TextView textView_istotal;
        TextView textView_colorcode;

        public ViewHolder(View view)
        {
            super(view);
            textView_istotal = (TextView) itemView.findViewById(R.id.textView_istotal);
            textView_statusid = (TextView) itemView.findViewById(R.id.textview_statusid);
            textView_statuscount = (TextView) itemView.findViewById(R.id.textview_statuscount);
            textView_statusname = (TextView) itemView.findViewById(R.id.textview_statusname);
            textView_colorcode = (TextView) itemView.findViewById(R.id.textView_colorcode);
            view.setOnClickListener(this);
        }

        public void onClick(View view)
        {
            String temp_statusid = ((TextView) view.findViewById(R.id.textview_statusid)).getText().toString();
            String temp_statuscolorcode = ((TextView) view.findViewById(R.id.textView_colorcode)).getText().toString();
            String temp_istotal = ((TextView) view.findViewById(R.id.textView_istotal)).getText().toString();
            TextView temp_textView_statuscount = (TextView) itemView.findViewById(R.id.textview_statuscount);
            TextView temp_textView_statusname = (TextView) itemView.findViewById(R.id.textview_statusname);
            Log.i("TextColor","ColorCode:"+temp_statuscolorcode);
            temp_textView_statusname.setTextColor(Color.parseColor(temp_statuscolorcode));
            temp_textView_statuscount.setTextColor(Color.parseColor(temp_statuscolorcode));
            ((DashboardNew) context).LoadDashboardData_Week(temp_statusid,temp_istotal);
            int int_position  =   getAdapterPosition();
            Log.i("Position","int_position:"+int_position);

            /*for(int i=0;i<data.length();i++)
            {
                Log.i("Position","int_position:"+int_position+" clicked:"+i);
                if(int_position == i)
                {
                    temp_textView_statusname.setTextColor(Color.parseColor(temp_statuscolorcode));
                    temp_textView_statusname.setTextColor(Color.parseColor(temp_statuscolorcode));
                }
                else {
                    textView_statusname.setTextColor(Color.parseColor("#ffffff"));
                    textView_statuscount.setTextColor(Color.parseColor("#ffffff"));
                }
            }*/

        }
    }



}