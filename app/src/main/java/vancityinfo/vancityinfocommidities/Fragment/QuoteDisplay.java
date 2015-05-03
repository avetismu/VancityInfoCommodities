package vancityinfo.vancityinfocommidities.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import vancityinfo.vancityinfocommidities.Model.Commodity;
import vancityinfo.vancityinfocommidities.Model.Currency;
import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.R;

/**
 * A simple {@link Fragment} subclass.
 * Implements Singleton Pattern
 */
public class QuoteDisplay extends Fragment {

    /* Fields */
    private static volatile QuoteDisplay instance;

    private Quote mQuote;

    private TextView mPrice;
    private TextView mName;
    private TextView mSymbol;

    /* Fragment States */

    public static QuoteDisplay getInstance(){
        if (instance == null){
            instance = new QuoteDisplay();
        }

        return instance;
    }


    public QuoteDisplay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote_display, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        setupUI();
    }

    /* Helper Methods */

    /**
     * setup UI
     * includes TextView mName, Symbol, Price
     */
    private void setupUI(){
        if(mName == null)
            mName  = (TextView) getActivity().findViewById(R.id.quote_display_name);
        if(mSymbol == null)
            mSymbol = (TextView) getActivity().findViewById(R.id.quote_display_symbol);
        if(mPrice == null)
            mPrice = (TextView) getActivity().findViewById(R.id.quote_display_price);
    }

    /**
     * display the values of mQuote
     */
    public void renderUI(){

        if(getActivity() != null) {
            try {
                mName.setText(mQuote.getName());
                mSymbol.setText(mQuote.getSymbol());
            }
            //uninitialised mQuote
            catch (NullPointerException e) {
                Log.e("Null Pointer Exception", null);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // fetch data
            } else {
                // display error
            }


            //Quote is a Currency
            /*if (mQuote instanceof Currency) {

                Currency currency = (Currency) mQuote;

                mPrice.setText(R.string.quote_display_price_currency_daily_high_text
                        + Double.toString(currency.getDailyHigh()) + "\n"
                        + R.string.quote_display_price_currency_daily_low_text +
                        Double.toString(currency.getDailyLow()));
            }*/
        }
    }

    /* Getter Setters */

    /**
     * @param quote Quote to be displayed
     */
    public void setQuote(Quote quote){
        this.mQuote = quote;

        renderUI();
    }

    /* Nested Classes and Interfaces */

    //used to fetch commodity data from YQL
    private class CommodityData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

    //used to fetch currency data from YQL
    private class CurrencyData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

}
