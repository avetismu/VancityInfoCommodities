package vancityinfo.vancityinfocommidities.Model;

import java.util.HashMap;

public class Commodity extends Quote {


    //TODO: Add other fields (prices, etc...)

    /* Fields */

    private double mPrice;
    private String mUnits;

    /* Constructors */

    /**
     *
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     * @param col column in data JSONArray where value is fetched
     * @param units units used in the JSON values
     */
    public Commodity(String name, String symbol, String url, int col, String units) {
        //Superclass Constructor
        super(name, symbol, url, col);
        mUnits = units;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public String getUnits() {
        return mUnits;
    }

    public void setUnits(String mUnits) {
        this.mUnits = mUnits;
    }
}


