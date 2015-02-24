package vancityinfo.vancityinfocommidities.Model;

public class Commodity extends Quote {


    //TODO: Add other fields (prices, etc...)

    /* Fields */

    /* Constructors */

    /**
     *
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     */
    public Commodity(String name, String symbol, String url){
        //Superclass Constructor
        super(name, symbol, url);
    }


}


