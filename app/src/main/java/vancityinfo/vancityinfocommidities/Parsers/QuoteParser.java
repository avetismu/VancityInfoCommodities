package vancityinfo.vancityinfocommidities.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Model.Commodity;
import vancityinfo.vancityinfocommidities.Model.Currency;
import vancityinfo.vancityinfocommidities.Model.Quote;

/**
 * Implements Singleton Pattern
*/
public class QuoteParser {

    /* Fields */

    private static volatile QuoteParser instance;

    /* Constructors and Instance*/

    /**
     *
     * @return Instance of QuoteParser
     */
    public static QuoteParser Instance(){
        if (instance == null)
            instance = new QuoteParser();

        return instance;
    }

    public QuoteParser(){
        //empty constructor
    }

    /**
     *
     * @param response response from input stream
     * @return produces an ArrayList of all the commodities_en that need to be displayed in fragment
     * QuoteList
     */
    public ArrayList<Quote> Parse(String response) throws JSONException{
        ArrayList<Quote> quoteList = new ArrayList<Quote>();

        JSONTokener tokener = new JSONTokener(response);
        JSONArray jsonArray = new JSONArray(tokener);
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            switch (jsonObject.getInt("type")){
                case 0:
                    quoteList.add(
                            new Commodity(jsonObject.getString("name"),
                                    jsonObject.getString("symbol"),
                                    jsonObject.getString("url"),
                                    jsonObject.getInt("col"),
                                    jsonObject.getString("units")));
                    break;
                case 1:
                    quoteList.add(
                            new Currency(jsonObject.getString("name"),
                                    jsonObject.getString("symbol"),
                                    jsonObject.getString("url"),
                                    jsonObject.getInt("col")));
            }

        }

        return quoteList;
    }



}
