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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.IssueDept;
import helpdesk.i2i.com.ifazidesk.activities.IssueList;
import helpdesk.i2i.com.ifazidesk.activities.TicketDetails;
import helpdesk.i2i.com.ifazidesk.activities.TicketDetails_User;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.utils.ImageLoader;

public class RecyclerViewAdapter_Department extends RecyclerView.Adapter<RecyclerViewAdapter_Department.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    //JSONArray data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> data;
    View view;
    ArrayList<HashMap<String, String>> arraylist_cobilist = new ArrayList<HashMap<String, String>>();
    public Preferences prefs;
    boolean boolean_isbasedonstatus = false;
    ImageLoader imageLoader;
    int int_statusid = 0;
    public RecyclerViewAdapter_Department(Context context, ArrayList<HashMap<String, String>> arraylist)
    {
        this.context = context;
        data = arraylist;
        prefs = new Preferences(context);
        imageLoader = new ImageLoader(context);

    }

    @Override
    public RecyclerViewAdapter_Department.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        prefs = new Preferences(context);
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_department, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Department.ViewHolder viewHolder, int i) {

        try {
            resultp = data.get(i);
            viewHolder.tvSa.setText(resultp.get(IssueDept.PDeptName));
            viewHolder.tvLt.setText(resultp.get(IssueDept.PDeptDesc));
            viewHolder.tvID.setText(resultp.get(IssueDept.PDeptID));
            viewHolder.tvURL.setText(resultp.get(IssueDept.PDeptImage));
            imageLoader.DisplayImage(resultp.get(IssueDept.PDeptImage), viewHolder.imageview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageview;
        TextView tvSa;
        TextView tvLt;
        TextView tvID;
        TextView tvURL;

        public ViewHolder(View view)
        {
            super(view);
            imageview = (ImageView) itemView.findViewById(R.id.imageviewid);
            tvSa = (TextView) itemView.findViewById(R.id.deptname);
            tvLt = (TextView) itemView.findViewById(R.id.deptdesc);
            tvID = (TextView) itemView.findViewById(R.id.deptid);
            tvURL = (TextView) itemView.findViewById(R.id.deptimageurl);
            view.setOnClickListener(this);
        }

        public void onClick(View view)
        {
            String deptid = ((TextView) view.findViewById(R.id.deptid)).getText().toString();
            int int_deptid = Integer.parseInt(deptid);
            String deptname = ((TextView) view.findViewById(R.id.deptname)).getText().toString();
            String deptdesc = ((TextView) view.findViewById(R.id.deptdesc)).getText().toString();
            String depturl = ((TextView) view.findViewById(R.id.deptimageurl)).getText().toString();

            prefs.setString("DeptName_SP", deptname);
            prefs.setInt("DeptID_SP", int_deptid);
            prefs.setString("DeptDesc_SP", deptdesc);
            prefs.setString("DeptURL_SP", depturl);

            prefs.setString("QRCODE","false");


            Intent act_reg = new Intent(context, IssueList.class);
            act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(act_reg);

           /* String temp_ticketid = ((TextView) view.findViewById(R.id.textview_ticketid)).getText().toString();
            String temp_ticketno = ((TextView) view.findViewById(R.id.textview_ticketno)).getText().toString();
            String temp_category = ((TextView) view.findViewById(R.id.textview_category)).getText().toString();
            String temp_isclosed = ((TextView) view.findViewById(R.id.textview_isclosed)).getText().toString();
            String temp_statuscount = ((TextView) view.findViewById(R.id.textview_count)).getText().toString();

            String string_isadmin = prefs.getString("isAdmin_SP");
            String string_isserviceengg = prefs.getString("isServiceEngineer_SP");
                Bundle bundle = new Bundle();
                bundle.putString("TicketNo", "" + temp_ticketid);
                bundle.putString("Ticket_isClosed", "" + temp_isclosed);
                bundle.putString("Ticket_StatusCount", "" + temp_statuscount);
                Intent act_reg = new Intent(context, TicketDetailsData.class);
                act_reg.putExtras(bundle);
                act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(act_reg);*/
        }
    }
}