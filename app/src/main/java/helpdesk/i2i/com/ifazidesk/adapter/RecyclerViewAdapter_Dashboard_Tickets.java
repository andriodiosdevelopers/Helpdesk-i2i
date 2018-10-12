package helpdesk.i2i.com.ifazidesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.TicketDetails;
import helpdesk.i2i.com.ifazidesk.activities.TicketDetails_User;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.TicketdetailsItem;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;

public class RecyclerViewAdapter_Dashboard_Tickets extends RecyclerView.Adapter<RecyclerViewAdapter_Dashboard_Tickets.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    JSONArray data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    View view;
    ArrayList<HashMap<String, String>> arraylist_cobilist = new ArrayList<HashMap<String, String>>();
    public Preferences prefs;
    boolean boolean_isbasedonstatus = false;
    boolean boolean_isSETickets = false;
    int int_statusid = 0;
    List<TicketdetailsItem> data_ticketdetails;
    //public RecyclerViewAdapter_Dashboard_Today_Tickets(Context context, JSONArray jsonArray, boolean SEtickets)
    public RecyclerViewAdapter_Dashboard_Tickets(Context context, List<TicketdetailsItem> ticketdetailsItems, boolean SEtickets)
    {
        this.context = context;
        this.boolean_isSETickets = SEtickets;
        data_ticketdetails = ticketdetailsItems;
        prefs = new Preferences(context);

    }

    @Override
    public RecyclerViewAdapter_Dashboard_Tickets.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        prefs = new Preferences(context);
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_dashboard_tickets, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Dashboard_Tickets.ViewHolder viewHolder, int i) {

        try {

//                int Statusid = data.getJSONObject(i).getInt("Statusid");
//                int ComplaintId = data.getJSONObject(i).getInt("ComplaintId");
//                String TicketNo = data.getJSONObject(i).getString("TicketNo");
//                String Category = data.getJSONObject(i).getString("Category");
//                String Subject = data.getJSONObject(i).getString("Subject");
//                String Priority = data.getJSONObject(i).getString("Priority");
//                String RequestDate = data.getJSONObject(i).getString("RequestDate");
//                String RequestTime = data.getJSONObject(i).getString("RequestTime");
//                String location = data.getJSONObject(i).getString("location");
//
//                String Status = data.getJSONObject(i).getString("Status");
//                int StatusCount = data.getJSONObject(i).getInt("StatusCount");
//                Boolean IsClosed = data.getJSONObject(i).getBoolean("IsClosed");
//                String ColorCode = data.getJSONObject(i).getString("ColorCode");

                int Statusid = data_ticketdetails.get(i).getStatusid();
                int ComplaintId = data_ticketdetails.get(i).getComplaintId();
                String TicketNo = data_ticketdetails.get(i).getTicketNo();
                String Category = data_ticketdetails.get(i).getCategory();
                String Subject = data_ticketdetails.get(i).getSubject();
                String Priority = data_ticketdetails.get(i).getPriority();
                String RequestDate = data_ticketdetails.get(i).getRequestDate();
                String RequestTime = data_ticketdetails.get(i).getRequestTime();
                String location = data_ticketdetails.get(i).getLocation();

                String Status = data_ticketdetails.get(i).getStatus();
                int StatusCount = data_ticketdetails.get(i).getStatusCount();
                Boolean IsClosed = data_ticketdetails.get(i).isIsClosed();
                Boolean IsOnHold = data_ticketdetails.get(i).isIsonHold();
                String ColorCode = data_ticketdetails.get(i).getColorCode();

                viewHolder.textview_isclosed.setText(""+IsClosed);
                viewHolder.textview_isonhold.setText(""+IsOnHold);
                viewHolder.textview_statuscount.setText(""+StatusCount);

                viewHolder.textView_ticketid.setText(""+ComplaintId);
                viewHolder.textView_ticketno.setText(""+TicketNo);
                viewHolder.textView_category.setText(Category);
                viewHolder.textView_subject.setText(Subject);
                viewHolder.textView_priority.setText(Priority);
                viewHolder.textview_location.setText(location);

                String inputPattern = "MMM dd yyyy";
                String outputPattern = "dd/MM/yyyy";
                SimpleDateFormat inputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
                SimpleDateFormat outputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
                Date date = null;
                String str = null;
                try {
                    date = inputFormat.parse(RequestDate);
                    str = outputFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                viewHolder.textView_datetime.setText(str+" "+RequestTime);
                viewHolder.textview_status.setText(Status);
            String status_color;

            String color[] = ColorCode.split(" - ");
            status_color = color[1];
            GradientDrawable bgShape = (GradientDrawable)viewHolder.textview_status.getBackground();
            bgShape.setColor(Color.parseColor(status_color));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return data_ticketdetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_ticketid;
        TextView textView_ticketno;
        TextView textView_category;
        TextView textView_subject;
        TextView textView_datetime;
        TextView textView_priority;
        TextView textview_status;
        TextView textview_isclosed;
        TextView textview_statuscount;
        TextView textview_location;
        TextView textview_isonhold;

        public ViewHolder(View view)
        {
            super(view);
            textView_ticketid = (TextView) itemView.findViewById(R.id.textview_ticketid);
            textView_ticketno = (TextView) itemView.findViewById(R.id.textview_ticketno);
            textView_category = (TextView) itemView.findViewById(R.id.textview_category);
            textView_subject = (TextView) itemView.findViewById(R.id.textview_subject);
            textView_priority = (TextView) itemView.findViewById(R.id.textview_priority);
            textView_datetime = (TextView) itemView.findViewById(R.id.textview_datetime);
            textview_status = (TextView) itemView.findViewById(R.id.textview_status);
            textview_isclosed = (TextView) itemView.findViewById(R.id.textview_isclosed);
            textview_isonhold = (TextView) itemView.findViewById(R.id.textview_isonhold);
            textview_statuscount = (TextView) itemView.findViewById(R.id.textview_count);
            textview_location = (TextView) itemView.findViewById(R.id.textview_location);
            view.setOnClickListener(this);
        }

        public void onClick(View view)
        {
            String temp_ticketid = ((TextView) view.findViewById(R.id.textview_ticketid)).getText().toString();
            String temp_ticketno = ((TextView) view.findViewById(R.id.textview_ticketno)).getText().toString();
            String temp_category = ((TextView) view.findViewById(R.id.textview_category)).getText().toString();
            String temp_isclosed = ((TextView) view.findViewById(R.id.textview_isclosed)).getText().toString();
            String temp_isonhold = ((TextView) view.findViewById(R.id.textview_isonhold)).getText().toString();
            String temp_statuscount = ((TextView) view.findViewById(R.id.textview_count)).getText().toString();

            String string_isadmin = prefs.getString("isAdmin_SP");
            String string_isserviceengg = prefs.getString("isServiceEngineer_SP");
            if(boolean_isSETickets == false){
                if(string_isadmin.equals("true")||string_isserviceengg.equals("true"))
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("TicketNo", "" + temp_ticketid);
                    bundle.putString("Ticket_isClosed", "" + temp_isclosed);
                    bundle.putString("Ticket_isOnHold", "" + temp_isonhold);
                    bundle.putString("Ticket_StatusCount", "" + temp_statuscount);
                    bundle.putString("Ticket_isSE", "" + string_isserviceengg);
                    bundle.putString("Ticket_isAdmin", "" + string_isadmin);
                    Intent act_reg = new Intent(context, TicketDetails.class);
                    act_reg.putExtras(bundle);
                    act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    act_reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(act_reg);
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("TicketNo", "" + temp_ticketid);
                    bundle.putString("Ticket_isClosed", "" + temp_isclosed);
                    bundle.putString("Ticket_isOnHold", "" + temp_isonhold);
                    bundle.putString("Ticket_StatusCount", "" + temp_statuscount);
                    Intent act_reg = new Intent(context, TicketDetails_User.class);
                    act_reg.putExtras(bundle);
                    act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    act_reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(act_reg);
                }
            }else
            {
                Bundle bundle = new Bundle();
                bundle.putString("TicketNo", "" + temp_ticketid);
                bundle.putString("Ticket_isClosed", "" + temp_isclosed);
                bundle.putString("Ticket_isOnHold", "" + temp_isonhold);
                bundle.putString("Ticket_StatusCount", "" + temp_statuscount);
                Intent act_reg = new Intent(context, TicketDetails_User.class);
                act_reg.putExtras(bundle);
                act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                act_reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(act_reg);
            }

        }
    }
}