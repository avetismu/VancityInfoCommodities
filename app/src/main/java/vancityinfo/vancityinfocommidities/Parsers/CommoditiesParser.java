package vancityinfo.vancityinfocommidities.Parsers;

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
    public ArrayList<Commodity> Parse(String response){
        ArrayList<Commodity> comList = new ArrayList<Commodity>();

        //TODO: Implement Parser

        return comList;
    }



}
