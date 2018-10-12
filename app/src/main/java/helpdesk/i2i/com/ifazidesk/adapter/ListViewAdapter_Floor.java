package helpdesk.i2i.com.ifazidesk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.fragments.Fragment_List_Floor;


public class ListViewAdapter_Floor extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter_Floor(Context context,
                                 ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
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
        TextView tv_name;
        TextView tv_id;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.layout_list_item_spin2, parent, false);
        // Get the position
        resultp = data.get(position);

        tv_name = (TextView) itemView.findViewById(R.id.product_name);
        tv_id = (TextView) itemView.findViewById(R.id.product_id);

        tv_name.setText(resultp.get(Fragment_List_Floor.List_Item_Name));
        tv_id.setText(resultp.get(Fragment_List_Floor.List_Item_ID));


        return itemView;
    }
}
