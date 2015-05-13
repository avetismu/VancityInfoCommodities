package vancityinfo.vancityinfocommidities.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.GregorianCalendar;

import vancityinfo.vancityinfocommidities.Model.Commodity;

/**
 * Implements Singleton Pattern
 */
public class CommodityParser {

    /* Fields */

    private static volatile CommodityParser instance;

    /* Constructors and Instance*/

    /**
     *
     * @return Instance of QuoteParser
     */
    public static CommodityParser Instance(){
        if (instance == null)
            instance = new CommodityParser();

        return instance;
    }

    public CommodityParser(){
        //empty constructor
    }

    /**
     * Sets daily Price for commodities_en, and adds data points for Plot
     * @param response response from input stream
     * @param trimEnd end Date of fetched data
     * @param commodity Commodity object to be updated
     * @return produces an ArrayList of all the commodities_en that need to be displayed in fragment
     * QuoteList
     */
    public static void Parse(String response, GregorianCalendar trimEnd, Commodity commodity) throws JSONException{
        JSONTokener tokener = new JSONTokener(response);
        JSONObject root = new JSONObject(tokener);

        updateDescription(root, commodity);

        JSONArray data = root.getJSONArray("data");
        updatePrice(data, commodity);
        updateChange(data, commodity);
        updatePlotValues(data, trimEnd, commodity);

    }

    private static void updateDescription(JSONObject root, Commodity commodity) throws JSONException{
        commodity.setDesc(root.getString("description"));
    }

    private static void updatePrice(JSONArray data, Commodity commodity) throws JSONException{
        double price = data.getJSONArray(0).getDouble(commodity.getCol());
        commodity.setPrice(price);
    }

    private static void updateChange(JSONArray data, Commodity commodity){

        try {
            double previous = data.getJSONArray(1).getDouble(commodity.getCol());
            double price = commodity.getPrice();
            commodity.setChange(100 * (price-previous)/previous);
        }
        catch(JSONException e){
            commodity.setChange(0.0);
        }
    }

    /**
     * If data is prior to or equal to trim date, add price in USD(index = 1) to date(index = 0) in HashMap
     * @param data
     * @param trimEnd
     * @param commodity
     * @throws JSONException
     */
    private static void updatePlotValues(JSONArray data, GregorianCalendar trimEnd, Commodity commodity) throws JSONException{
        int n = determineInterval(data.length());
        for(int i = 0; i<data.length(); i+= n){
            GregorianCalendar gregorianCalendar = convertToCalendar(data.getJSONArray(i).getString(0));
            if(trimEnd.compareTo(gregorianCalendar) == 0 || trimEnd.compareTo(gregorianCalendar) == 1){
                commodity.addPair(gregorianCalendar, data.getJSONArray(i).getDouble(commodity.getCol()));
            }
        }
    }

    /**
     * converts string from JSON into a Gregorian Calendar
     * @param date in format yyyy-mm-dd
     * @return Gregorian Calendar with year, month and day field equivalent to string
     */
    private static GregorianCalendar convertToCalendar(String date){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
/*        String substring = date.substring(0,4);
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));*/
        gregorianCalendar.set(
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)),
                Integer.parseInt(date.substring(8, 10))
        );

        return gregorianCalendar;
    }

    /**
     * sets the interval of days to fetch data from JSON
     * @param length number of days to check
     * @return interval of incrementation
     */
    private static int determineInterval(int length) {
        if(length<32){
            return 1;
        }
        else if(length<100){
            return 5;
        }
        else{
            return 32;
        }
    }

}

