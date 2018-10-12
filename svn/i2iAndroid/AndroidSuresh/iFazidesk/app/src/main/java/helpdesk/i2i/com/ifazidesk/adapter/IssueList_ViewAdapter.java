package helpdesk.i2i.com.ifazidesk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.IssueList;
import helpdesk.i2i.com.ifazidesk.utils.ImageLoader;

public class IssueList_ViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public IssueList_ViewAdapter(Context context,
                                 ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables

        ImageView imageview;
        TextView tvSa;
        TextView tvLt;
        TextView tvLn;
        TextView tvRT;
        TextView tvRST;
        TextView tvID;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listitem_issue_images, parent, false);

        resultp = data.get(position);

        imageview = (ImageView) itemView.findViewById(R.id.image);
        tvSa = (TextView) itemView.findViewById(R.id.issuename);
        tvLt = (TextView) itemView.findViewById(R.id.priority);
        tvLn = (TextView) itemView.findViewById(R.id.priority_id);
        tvRT = (TextView) itemView.findViewById(R.id.responsetime);
        tvID = (TextView) itemView.findViewById(R.id.issueid);
        tvRST = (TextView) itemView.findViewById(R.id.resolutiontime);

        tvSa.setText(resultp.get(IssueList.PPID));
        tvLt.setText(resultp.get(IssueList.PNAME));
        tvLn.setText(resultp.get(IssueList.PID));
        tvRT.setText(resultp.get(IssueList.RPT));
        tvID.setText(resultp.get(IssueList.PIID));
        tvRST.setText(resultp.get(IssueList.RST));

        imageLoader.DisplayImage(resultp.get(IssueList.IMG), imageview);

        return itemView;
    }
}
