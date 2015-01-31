package vancityinfo.vancityinfocommidities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Fragment.CommoditiesList;
import vancityinfo.vancityinfocommidities.Model.Commodity;
import vancityinfo.vancityinfocommidities.R;


public class CommoditiesListAdapter extends ArrayAdapter<Commodity> {

    /* Fields */

    private Context mContext;
    private ArrayList<Commodity> mCommodities;


    /* Constructors */

    public CommoditiesListAdapter(Context context, ArrayList<Commodity> commodities){
        super(context, R.layout.commodities_listview_row, commodities);

        mContext = context;
        mCommodities = commodities;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View rowView = convertView;

        if (convertView == null) {

            //initialise inflater
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //inflate row layout
            rowView = inflater.inflate(R.layout.commodities_listview_row, parent, false);

            TextView name = (TextView) rowView.findViewById(R.id.commodities_listview_row_name);
            TextView symbol = (TextView) rowView.findViewById(R.id.commodities_listview_row_symbol);

            name.setText(mCommodities.get(position).getmName());
            symbol.setText(mCommodities.get(position).getmSymbol());
        }

        return rowView;

    }
}
