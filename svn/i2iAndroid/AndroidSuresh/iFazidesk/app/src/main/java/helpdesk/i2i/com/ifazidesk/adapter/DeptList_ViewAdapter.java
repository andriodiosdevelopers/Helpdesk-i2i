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
import helpdesk.i2i.com.ifazidesk.activities.IssueDept;
import helpdesk.i2i.com.ifazidesk.utils.ImageLoader;

public class DeptList_ViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public DeptList_ViewAdapter(Context context,
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
        TextView tvID;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listitem_dept_images, parent, false);
        // Get the position
        resultp = data.get(position);

        imageview = (ImageView) itemView.findViewById(R.id.imageviewid);
        tvSa = (TextView) itemView.findViewById(R.id.deptname);
        tvLt = (TextView) itemView.findViewById(R.id.deptdesc);
        tvID = (TextView) itemView.findViewById(R.id.deptid);

        tvSa.setText(resultp.get(IssueDept.PDeptName));
        tvLt.setText(resultp.get(IssueDept.PDeptDesc));
        tvID.setText(resultp.get(IssueDept.PDeptID));

        imageLoader.DisplayImage(resultp.get(IssueDept.PDeptImage), imageview);

        return itemView;
    }
}
