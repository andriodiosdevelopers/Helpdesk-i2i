package helpdesk.i2i.com.ifazidesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.IssueDept;
import helpdesk.i2i.com.ifazidesk.activities.IssueList;
import helpdesk.i2i.com.ifazidesk.activities.IssueLocation;
import helpdesk.i2i.com.ifazidesk.activities.IssueSubmit;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.utils.ImageLoader;

public class RecyclerViewAdapter_Issue extends RecyclerView.Adapter<RecyclerViewAdapter_Issue.ViewHolder> {
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
    public RecyclerViewAdapter_Issue(Context context, ArrayList<HashMap<String, String>> arraylist)
    {
        this.context = context;
        data = arraylist;
        prefs = new Preferences(context);
        imageLoader = new ImageLoader(context);

    }

    @Override
    public RecyclerViewAdapter_Issue.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        prefs = new Preferences(context);
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_issue, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Issue.ViewHolder viewHolder, int i) {

        try {
            resultp = data.get(i);

            viewHolder.tvSa.setText(resultp.get(IssueList.PPID));
            viewHolder.tvLt.setText(resultp.get(IssueList.PNAME));
            viewHolder.tvLn.setText(resultp.get(IssueList.PID));
            viewHolder.tvRT.setText(resultp.get(IssueList.RPT));
            viewHolder.tvID.setText(resultp.get(IssueList.PIID));
            viewHolder.tvRST.setText(resultp.get(IssueList.RST));
            viewHolder.imageurl.setText(resultp.get(IssueList.IMG));

            imageLoader.DisplayImage(resultp.get(IssueList.IMG), viewHolder.imageview);

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
        TextView tvLn;
        TextView tvRT;
        TextView tvRST;
        TextView tvID;
        TextView imageurl;

        public ViewHolder(View view)
        {
            super(view);
            imageview = (ImageView) itemView.findViewById(R.id.image);
            tvSa = (TextView) itemView.findViewById(R.id.issuename);
            tvLt = (TextView) itemView.findViewById(R.id.priority);
            tvLn = (TextView) itemView.findViewById(R.id.priority_id);
            tvRT = (TextView) itemView.findViewById(R.id.responsetime);
            tvID = (TextView) itemView.findViewById(R.id.issueid);
            tvRST = (TextView) itemView.findViewById(R.id.resolutiontime);
            imageurl = (TextView) itemView.findViewById(R.id.imageurl);
            view.setOnClickListener(this);
        }

        public void onClick(View view)
        {
            String issueid = ((TextView) view.findViewById(R.id.issueid)).getText().toString();

            int int_issueid = Integer.parseInt(issueid);
            String issuename = ((TextView) view.findViewById(R.id.issuename)).getText().toString();
            String priority = ((TextView) view.findViewById(R.id.priority)).getText().toString();
            String  priorityid = ((TextView) view.findViewById(R.id.priority_id)).getText().toString();
            String responsetime = ((TextView) view.findViewById(R.id.responsetime)).getText().toString();
            String imageurl = ((TextView) view.findViewById(R.id.imageurl)).getText().toString();

            prefs.setString("IssueName_SP", issuename);
            prefs.setInt("IssueID_SP", int_issueid);
            prefs.setString("Priority_SP", priority);
            prefs.setString("PriorityID_SP", priorityid);
            prefs.setString("ResponseTime_SP", responsetime);
            prefs.setString("IssueURL_SP", imageurl);
            String string_fromqrcode = prefs.getString("QRCODE");
            if(string_fromqrcode.equals("false"))
            {
                Intent act_reg = new Intent(context, IssueLocation.class);
                act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                act_reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(act_reg);

            }
            if(string_fromqrcode.equals("true")) {
                Intent act_reg = new Intent(context, IssueSubmit.class);
                act_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(act_reg);
            }

        }
    }
}