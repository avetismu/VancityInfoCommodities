package vancityinfo.vancityinfocommidities.Model;

/**
 * Created by Richard Sarde on 2/23/2015.
 */
public class Currency extends Quote{

    /* Field */

    private double mDailyHigh;
    private double mDailyLow;

    /* Constructors */

    /**
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     * @param dailyHigh daily high price
     * @param dailyLow daily low price
     * @param col column in data JSONArray where value is fetched
     */
    public Currency(String name, String symbol, String url, double dailyHigh, double dailyLow, int col){
        super(name, symbol, url, col);
        mDailyHigh = dailyHigh;
        mDailyLow = dailyLow;
    }

    /**
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     * @param col column in data JSONArray where value is fetched
     */
    public Currency(String name, String symbol, String url, int col){
        super(name, symbol, url, col);
    }

    /* Getter Setter */

    public double getDailyHigh() {
        return mDailyHigh;
    }

    public void setDailyHigh(double mDailyHigh) {
        this.mDailyHigh = mDailyHigh;
    }

    public double getDailyLow() {
        return mDailyLow;
    }

    public void setDailyLow(double mDailyLow) {
        this.mDailyLow = mDailyLow;
    }

}
