package vancityinfo.vancityinfocommidities.Model;

public abstract class Quote {

    /* Fields */
    private String mName;
    private String mSymbol;
    private String mUrl;

    /**
     *
     * @param name common name of commodity
     * @param symbol trading symbol
     * @param url YQL url used to fetch JSON file
     */
    public Quote(String name, String symbol, String url){
        mName= name;
        mSymbol = symbol;
        mUrl = url;
    }

     /* Getter Setters */

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
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
}
