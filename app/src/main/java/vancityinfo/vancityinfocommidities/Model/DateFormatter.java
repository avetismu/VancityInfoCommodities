package vancityinfo.vancityinfocommidities.Model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateFormatter {

    public static String formatDate(GregorianCalendar gregorianCalendar){
        return String.valueOf(gregorianCalendar.get(Calendar.YEAR)) + "-"
        + formatString(String.valueOf(gregorianCalendar.get(Calendar.MONTH)+1)) + "-" +
                formatString(String.valueOf(gregorianCalendar.get(Calendar.DAY_OF_MONTH)));
    }

    private static String formatString(String str){
        if(str.length()== 1){
            return "0"+str;
        }
        return str;
    }
}
