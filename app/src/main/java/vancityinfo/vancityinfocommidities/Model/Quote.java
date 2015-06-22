package vancityinfo.vancityinfocommidities.Model;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class Quote {

    /* Fields */
    private String mName;
    private String mDesc;
    private String mSymbol;
    private String mUrl;
    private double mChange;
    private HashMap mPlotData;
    private int mCol;

    /**
     *
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     * @param col column in data JSONArray where value is fetched
     */
    public Quote(String name, String symbol, String url, int col){
        mName= name;
        mDesc = "";
        mSymbol = symbol;
        mUrl = url;
        mCol = col;
        mPlotData = new HashMap();
    }

     /* Getter Setters */

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public HashMap getPlotData() {
        return mPlotData;
    }

    public void setPlotData(HashMap mPlotData) {
        this.mPlotData = mPlotData;
    }

    public void addPair(GregorianCalendar key, double price){
        mPlotData.put(key, price);
    }

    public int getCol() {
        return mCol;
    }

    public void setCol(int mCol) {
        this.mCol = mCol;
    }

    public double getChange() {
        return mChange;
    }

    public void setChange(double mChange) {
        this.mChange = mChange;
    }
}
