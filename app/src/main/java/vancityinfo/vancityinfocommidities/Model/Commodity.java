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
}


