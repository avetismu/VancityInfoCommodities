package vancityinfo.vancityinfocommidities.Model;

/**
 * Created by tesla on 31/01/15.
 */
public class Commodity {

    /* Fields */
    private String mName;
    private String mSymbol;
    private String mUrl;

    //TODO: Add other fields (prices, etc...)

    /* Constructors */

    /**
     *
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     */
    public Commodity(String name, String symbol, String url){
        mName= name;
        mSymbol = symbol;
        mUrl = url;

    }

    /* Getter Setters */

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSymbol() {
        return mSymbol;
    }

    public void setmSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}


