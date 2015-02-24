package vancityinfo.vancityinfocommidities.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Model.Commodity;
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
     * @param response response from
     * @return produces an ArrayList of all the commodities that need to be displayed in fragment
     * QuoteList
     */
    public ArrayList<Quote> Parse(String response) throws JSONException{
        ArrayList<Quote> comList = new ArrayList<Quote>();

        JSONTokener tokener = new JSONTokener(response);
        JSONArray jsonArray = new JSONArray(tokener);
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            comList.add(
                new Commodity(jsonObject.getString("name"),
                              jsonObject.getString("symbol"),
                              jsonObject.getString("url")));
        }

        return comList;
    }



}
