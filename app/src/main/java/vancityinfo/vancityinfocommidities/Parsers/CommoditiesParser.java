package vancityinfo.vancityinfocommidities.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Scanner;

import vancityinfo.vancityinfocommidities.Model.Commodity;

/**
 * Implements Singleton Pattern
*/
public class CommoditiesParser {

    /* Fields */

    private static volatile CommoditiesParser instance;

    /* Constructors and Instance*/

    /**
     *
     * @return Instance of CommoditiesParser
     */
    public static CommoditiesParser Instance(){
        if (instance == null)
            instance = new CommoditiesParser();

        return instance;
    }

    public CommoditiesParser(){
        //empty constructor
    }

    /**
     *
     * @param response response from
     * @return produces an ArrayList of all the commodities that need to be displayed in fragment
     * CommoditiesList
     */
    public ArrayList<Commodity> Parse(String response) throws JSONException{
        ArrayList<Commodity> comList = new ArrayList<Commodity>();

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
