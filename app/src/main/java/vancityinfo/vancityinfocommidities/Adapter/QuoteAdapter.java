package vancityinfo.vancityinfocommidities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.R;


public class QuoteAdapter extends ArrayAdapter<Quote> {

    /* Fields */

    private Context mContext;
    private ArrayList<Quote> mQuotes;


    /* Constructors */

    public QuoteAdapter(Context context, ArrayList<Quote> commodities){
        super(context, R.layout.quote_listview_row, commodities);

        mContext = context;
        mQuotes = commodities;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View rowView = convertView;

        if (convertView == null) {

            //initialise inflater
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //inflate row layout
            rowView = inflater.inflate(R.layout.quote_listview_row, parent, false);

            TextView name = (TextView) rowView.findViewById(R.id.quote_list_listview_row_name);
            TextView symbol = (TextView) rowView.findViewById(R.id.quote_list_listview_row_symbol);

            name.setText(mQuotes.get(position).getName());
            symbol.setText(mQuotes.get(position).getSymbol());
        }

        return rowView;

    }
}
