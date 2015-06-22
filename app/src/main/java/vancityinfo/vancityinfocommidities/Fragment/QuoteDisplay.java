package vancityinfo.vancityinfocommidities.Fragment;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import vancityinfo.vancityinfocommidities.Model.Calculator;
import vancityinfo.vancityinfocommidities.Model.Commodity;
import vancityinfo.vancityinfocommidities.Model.Currency;
import vancityinfo.vancityinfocommidities.Model.DateFormatter;
import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.Parsers.CommodityParser;
import vancityinfo.vancityinfocommidities.R;

/**
 * A simple {@link Fragment} subclass.
 * Implements Singleton Pattern
 */
public class QuoteDisplay extends Fragment implements DatePickerFragment.DatePickerListener{

    /* Fields */
    private static volatile QuoteDisplay instance;

    private static Quote mQuote;

    private LinearLayout mLayout;
    private RelativeLayout mCalcLayout;
    private TextView mPrice;
    private TextView mChange;
    private TextView mName;
    private TextView mDesc;
    private TextView mSymbol;
    private TextView mResult;
    private EditText mWeight;
    private Spinner mUnits;
    private Spinner mFineness;
    private Button mCalculate;
    private Button mStartDate;
    private Button mEndDate;
    private ImageButton mRefresh;
    private ProgressBar mPBar;

    private XYPlot mPlot;
    private SimpleXYSeries mSeries;
    private LineAndPointFormatter mFormatter;

    private GregorianCalendar trimStart;
    private GregorianCalendar trimEnd;

    private Calculator mCalculator;

    private boolean mQuoteLocked = false;
    private boolean mError = false;

    private final String regexDec = "^[0-9]*\\.?[0-9]*$";
    private final String regexNum = "^[0-9]+";

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

        trimStart = new GregorianCalendar();
        trimStart.add(Calendar.DATE, -7);
        trimEnd = new GregorianCalendar();

        mCalculator = new Calculator(getActivity());

        setupUI();

        if(mQuote != null){
            renderUI();
        }
    }

    /* Helper Methods */

    /**
     * setup UI
     * includes TextView mName, Symbol, Price
     */
    private void setupUI(){
            mLayout = (LinearLayout) getActivity().findViewById(R.id.quote_display_layout);
            mCalcLayout = (RelativeLayout) getActivity().findViewById(R.id.quote_display_layout_calculator);

            mName  = (TextView) getActivity().findViewById(R.id.quote_display_name);

            mDesc  = (TextView) getActivity().findViewById(R.id.quote_display_description);

            mSymbol = (TextView) getActivity().findViewById(R.id.quote_display_symbol);

            mPrice = (TextView) getActivity().findViewById(R.id.quote_display_price);

            mChange = (TextView) getActivity().findViewById(R.id.quote_display_change);

            mResult = (TextView) getActivity().findViewById(R.id.quote_display_result);

            mWeight = (EditText) getActivity().findViewById(R.id.quote_display_calc_input);

            mUnits = (Spinner) getActivity().findViewById(R.id.quote_display_calc_spinner);


            mFineness = (Spinner) getActivity().findViewById(R.id.quote_display_calc_fineness);

            mCalculate = (Button) getActivity().findViewById(R.id.quote_display_calc_button);
            mCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mQuote.getName().equalsIgnoreCase(getActivity().getResources().getString(R.string.gold))){
                        String weight = mWeight.getText().toString();
                        if(!(weight.matches(regexNum) || weight.matches(regexDec)) || weight.equals("")) {
                            Toast.makeText(getActivity(),
                                    getActivity().getResources().
                                            getString(R.string.error_wrong_weight_input),
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            mResult.setText(

                                    getActivity().getString(R.string.quote_display_result_text) +
                                            " " +
                                    Double.toString(
                                            mCalculator.goldValue((Commodity) mQuote,
                                                    (String) mUnits.getSelectedItem(),
                                                    Double.valueOf(mWeight.getText().toString()),
                                                    Double.valueOf(mFineness.getSelectedItem().toString().substring(0, 2)))
                                    )
                            );
                        }
                    }
                    else if(mQuote.getName().equalsIgnoreCase(getActivity().getResources().getString(R.string.silver))){
                        String weight = mWeight.getText().toString();
                        if(!(weight.matches(regexNum) || weight.matches(regexDec)) || weight.equals("")) {
                            Toast.makeText(getActivity(),
                                    getActivity().getResources().
                                            getString(R.string.error_wrong_weight_input),
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            mResult.setText(

                                    getActivity().getString(R.string.quote_display_result_text) +
                                            " " +
                                            Double.toString(
                                                    mCalculator.silverValue((Commodity) mQuote,
                                                            (String) mUnits.getSelectedItem(),
                                                            Double.valueOf(mWeight.getText().toString()),
                                                            Double.valueOf(mFineness.getSelectedItem().toString().substring(0, 3)))
                                            )
                            );
                        }
                    }
                    else if(mQuote.getName().equalsIgnoreCase(getActivity().getResources().getString(R.string.platinum))){
                        String weight = mWeight.getText().toString();
                        if(!(weight.matches(regexNum) || weight.matches(regexDec)) || weight.equals("")) {
                            Toast.makeText(getActivity(),
                                    getActivity().getResources().
                                            getString(R.string.error_wrong_weight_input),
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            mResult.setText(

                                    getActivity().getString(R.string.quote_display_result_text) +
                                            " " +
                                            Double.toString(
                                                    mCalculator.platinumValue((Commodity) mQuote,
                                                            (String) mUnits.getSelectedItem(),
                                                            Double.valueOf(mWeight.getText().toString()),
                                                            Double.valueOf(mFineness.getSelectedItem().toString().substring(0, 2)))
                                            )
                            );
                        }
                    }
                    else if(mQuote instanceof Commodity){
                        String weight = mWeight.getText().toString();
                        if(!(weight.matches(regexNum) || weight.matches(regexDec)) || weight.equals("")) {
                            Toast.makeText(getActivity(),
                                    getActivity().getResources().
                                            getString(R.string.error_wrong_weight_input),
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            mResult.setText(

                                    getActivity().getString(R.string.quote_display_result_text) +
                                            " " +
                                            Double.toString(
                                                    mCalculator.GrossValue((Commodity) mQuote,
                                                            (String) mUnits.getSelectedItem(),
                                                            Double.valueOf(mWeight.getText().toString()))
                                            )
                            );
                        }
                    }

                    mResult.setVisibility(View.VISIBLE);
                }
            });


            mStartDate = (Button) getActivity().findViewById(R.id.quote_display_start_date_picker);
            mStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setCalendar(trimStart);
                    newFragment.setListener((DatePickerFragment.DatePickerListener) getFragmentManager().findFragmentByTag("display"));
                    newFragment.setTag("trimStart");
                    newFragment.show(getActivity().getFragmentManager(), "timePicker");
                }
            });


            mEndDate = (Button) getActivity().findViewById(R.id.quote_display_end_date_picker);
            mEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setCalendar(trimEnd);
                    newFragment.setListener((DatePickerFragment.DatePickerListener) getFragmentManager().findFragmentByTag("display"));
                    newFragment.setTag("trimEnd");
                    newFragment.show(getActivity().getFragmentManager(), "timePicker");
                }
            });


            mRefresh = (ImageButton) getActivity().findViewById(R.id.quote_display_refresh);
            mRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        fetchData();
                }
            });


            mPBar = (ProgressBar) getActivity().findViewById(R.id.quote_display_progress);


            //assigns plot to field
            mPlot = (XYPlot) getActivity().findViewById(R.id.quote_display_plot);

            //sets the grid colour to white
            mPlot.getGraphWidget().getGridBackgroundPaint().setColor(
                    getActivity().getResources().getColor(R.color.white)
            );
            //sets the backgrounds to the theme colour
            mPlot.getBackgroundPaint().setColor(
                    getActivity().getResources().getColor(R.color.background_material_light)
            );
            mPlot.getGraphWidget().getBackgroundPaint().setColor(
                    getActivity().getResources().getColor(R.color.background_material_light)
            );
            //sets the colour of the domain and range labels and their ticks
            mPlot.getGraphWidget().getDomainLabelPaint().setColor(
                    getActivity().getResources().getColor(R.color.in_dark_0)
            );
            mPlot.getGraphWidget().getRangeLabelPaint().setColor(
                    getActivity().getResources().getColor(R.color.in_dark_0)
            );
            mPlot.getDomainLabelWidget().getLabelPaint().setColor(
                    getActivity().getResources().getColor(R.color.in_dark_2)
            );
            mPlot.getRangeLabelWidget().getLabelPaint().setColor(
                    getActivity().getResources().getColor(R.color.in_dark_2)
            );

            //removes the legend from the black
            mPlot.getLayoutManager().remove(mPlot.getLegendWidget());

            //blackens the domain and range lines
            mPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
            mPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);

            //erases borders
            mPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
            mPlot.getBorderPaint().setStrokeWidth(0);
            mPlot.getBorderPaint().setAntiAlias(false);
            mPlot.getBorderPaint().setColor(
                    getActivity().getResources().getColor(R.color.background_material_light)
            );


            // Create a formatter to use for drawing a series using LineAndPointRenderer:
            mFormatter = new LineAndPointFormatter(
                    getActivity().getResources().getColor(R.color.in_light_3), // line colour
                    getActivity().getResources().getColor(R.color.in_dark_2), // point colour
                    getActivity().getResources().getColor(R.color.in_light_1), //fill colour
                    new PointLabelFormatter(
                            getActivity().getResources().getColor(R.color.in_dark_2)
                    ));
            //sets padding to match labels
            mPlot.getGraphWidget().setPadding(0,
                    10,
                    30,
                    10);

    }

    /**
     * display the values of mQuote
     */
    public void renderUI(){

        if(getActivity() != null) {
            try {

                //assign text values for Name, Symbol, Description
                mCalcLayout.setVisibility(View.GONE);
                mName.setText(mQuote.getName());
                mSymbol.setText(mQuote.getSymbol());
                mDesc.setText(mQuote.getDesc());

                //sets change percentage and assigns appropriate colour
                mChange.setText(
                        getActivity().getString(R.string.quote_display_change) +
                                String.valueOf(
                                        Calculator.staticRound(mQuote.getChange(), 2))
                                +"%");
                if(mQuote.getChange() > 0.001) {
                    mChange.setTextColor(getActivity().getResources().getColor(R.color.price_change_positive));
                }
                else if(mQuote.getChange() < -0.001){
                    mChange.setTextColor(getActivity().getResources().getColor(R.color.price_change_negative));
                }
                else{
                    mChange.setTextColor(getActivity().getResources().getColor(R.color.primary_text_default_material_light));
                }

                //hides the result
                mResult.setVisibility(View.GONE);
                //sets the buttons' text
                mStartDate.setText(DateFormatter.formatDate(trimStart));
                mEndDate.setText(DateFormatter.formatDate(trimEnd));
                //hides fineness
                mFineness.setVisibility(View.GONE);

            }
            //uninitialised mQuote
            catch (NullPointerException e) {
                Log.e("Null Pointer Exception", e.getLocalizedMessage());
            }

            //Quote is a Commodity
            if(mQuote instanceof Commodity){
                //type cast to commodity
                Commodity commodity = (Commodity) mQuote;
                //makes the calculator visible
                mCalcLayout.setVisibility(View.VISIBLE);
                //sets the price
                mPrice.setText(
                        getActivity().getResources().getString(R.string.quote_display_price_commodity_daily_text) +
                                Double.toString(commodity.getPrice()));

                //assigns the weight spinner array
                ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.support_simple_spinner_dropdown_item,
                        getActivity().getResources().getStringArray(R.array.calc_spinner_array_weight_precious_metals));
                mUnits.setAdapter(weightAdapter);

                //if Gold
                if(mQuote.getName().equalsIgnoreCase(getActivity().getResources().
                        getString(R.string.gold))){

                    //make fineness visible
                    mFineness.setVisibility(View.VISIBLE);

                    //assign gold fineness array to spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.support_simple_spinner_dropdown_item,
                            getActivity().getResources().
                                    getStringArray(R.array.calc_spinner_array_finess_gold));
                    mFineness.setAdapter(adapter);
                }
                //if Silver
                else if(mQuote.getName().equalsIgnoreCase(getActivity().getResources().
                        getString(R.string.silver))){

                    //makes fineness visible
                    mFineness.setVisibility(View.VISIBLE);

                    //asigns silver fineness array to spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.support_simple_spinner_dropdown_item,
                            getActivity().getResources().
                                    getStringArray(R.array.calc_spinner_array_finess_silver));
                    mFineness.setAdapter(adapter);
                }
                //if Platinum
                else if(mQuote.getName().equalsIgnoreCase(getActivity().getResources().
                        getString(R.string.platinum))){

                    //makes fineness visible
                    mFineness.setVisibility(View.VISIBLE);

                    //assigns platinum fineness array to spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.support_simple_spinner_dropdown_item,
                            getActivity().getResources().
                                    getStringArray(R.array.calc_spinner_array_finess_platinum));
                    mFineness.setAdapter(adapter);
                }
            }

            //Quote is a Currency
            /*if (mQuote instanceof Currency) {

                Currency currency = (Currency) mQuote;

                mPrice.setText(R.string.quote_display_price_currency_daily_high_text
                        + Double.toString(currency.getDailyHigh()) + "\n"
                        + R.string.quote_display_price_currency_daily_low_text +
                        Double.toString(currency.getDailyLow()));
            }*/

            /* Plot Data */

            //iterates plot and creates two arraylists of doubles: dates and corresponding values
            Iterator it = mQuote.getPlotData().entrySet().iterator();
            ArrayList dates = new ArrayList<Double>();
            ArrayList values = new ArrayList<Double>();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry)it.next();
                GregorianCalendar calendar = (GregorianCalendar) pair.getKey();
                dates.add(new Double(calendar.getTimeInMillis()));
                values.add(new Double((double) pair.getValue()));
                it.remove(); // avoids a ConcurrentModificationException
            }

            // create our series from our array of nums:
            mSeries = new SimpleXYSeries(dates, values, mQuote.getName());

            //clears previous series in plot
            mPlot.clear();

            //adds new series to plot
            mPlot.addSeries(mSeries, mFormatter);

            // draw a domain tick for each year:
            mPlot.setDomainStep(XYStepMode.SUBDIVIDE, dates.size());


           /* mPlot.position(
                    mPlot.getGraphWidget(),
                    0,
                    XLayoutStyle.ABSOLUTE_FROM_LEFT,
                    0,
                    YLayoutStyle.RELATIVE_TO_CENTER,
                    AnchorPosition.LEFT_MIDDLE);*/

            // customize our domain/range labels
            mPlot.setDomainLabel("Time");
            mPlot.setRangeLabel("Price");

            // get rid of decimal points in our range labels:
            mPlot.setRangeValueFormat(new DecimalFormat("0"));

            mPlot.setDomainValueFormat(new Format() {

                // create a simple date format that draws on the year portion of our timestamp.
                // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
                // for a full description of SimpleDateFormat.
                private SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");

                @Override
                public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

                    long timestamp = ((Number) obj).longValue();
                    Date date = new Date(timestamp);
                    return dateFormat.format(date, toAppendTo, pos);
                }

                @Override
                public Object parseObject(String source, ParsePosition pos) {
                    return null;

                }
            });

            mPlot.redraw();

            //make all subviews visible
            //HELPER METHOD
            setUIVisible();
        }
    }

    /* Getter Setters */

    /**
     * @param quote Quote to be displayed
     */
    public void setQuote(Quote quote){
        if(mQuoteLocked == false) {
            mQuoteLocked = true;
            this.mQuote = quote;
            fetchData();
        }
    }

    private void fetchData(){
        //make progress bar visible
        mPBar.setVisibility(View.VISIBLE);
        //make all subviews invisible
        //HELPER METHOD
        setUIInvisible();

        //start network thread and operations
        if(mQuote instanceof Commodity)
            new CommodityData().execute(mQuote.getUrl());
        else if(mQuote instanceof Currency)
            new CurrencyData().execute(mQuote.getUrl());
    }

    private void setUIVisible(){
        mLayout.setVisibility(View.VISIBLE);
    }

    private void setUIInvisible(){
        mLayout.setVisibility(View.INVISIBLE);
    }


    /* Interface Methods */

    @Override
    public void setPickedDate(GregorianCalendar calendar, String tag) {
        if(tag.equalsIgnoreCase("trimstart")) {
            trimStart = calendar;
            mStartDate.setText(DateFormatter.formatDate(trimStart));
        }
        if(tag.equalsIgnoreCase("trimend")) {
            trimEnd = calendar;
            mEndDate.setText(DateFormatter.formatDate(trimEnd));
        }
    }

    /* Nested Classes and Interfaces */

    //used to fetch commodity data
    private class CommodityData extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];

            url = url + "?auth_token=" + getActivity().getResources().getString(R.string.API_KEY_QUANDL) + "&trim_start=" + trimStart.get(Calendar.YEAR) + "-" + trimStart.get(Calendar.MONTH) + "-"
                    + trimStart.get(Calendar.DAY_OF_MONTH);

            InputStream inputStream = null;
            String result = "";
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                }
                else {
                    //TODO: Launch Connection Dialog
                }
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            try {
                Commodity commodity = (Commodity) mQuote;
                CommodityParser.Parse(result, trimEnd, commodity);
            }
            catch(JSONException e) {
                Log.e("JSON Exception", e.getLocalizedMessage());
                mError = true;
            }

            return null;
        }


        protected void onPostExecute(Void params){
            mPBar.setVisibility(View.INVISIBLE);
            renderUI();
            mQuoteLocked = false;
            if(mError){
                Toast.makeText(getActivity(), R.string.error_daily_maximum_requests, Toast.LENGTH_LONG).show();
                mError = false;
            }
        }
    }

    //used to fetch currency data
    private class CurrencyData extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            return null;
        }
    }


}
