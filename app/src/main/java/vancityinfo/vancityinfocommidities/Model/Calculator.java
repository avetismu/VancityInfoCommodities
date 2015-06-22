package vancityinfo.vancityinfocommidities.Model;

import android.app.Activity;
import java.math.BigDecimal;
import java.math.RoundingMode;

import vancityinfo.vancityinfocommidities.R;

public class Calculator {

    private Activity mActivity;

    public Calculator(Activity activity){
        mActivity = activity;
    }

    public double goldValue(Commodity commodity, String units, double weight, double fineness){
        double pricePerGram = getValuePerGram(commodity.getPrice(), commodity.getUnits());
        double weightInGrams = getValueInGrams(weight, units);

        double pureValue = pricePerGram * weightInGrams;
        return round(((pureValue/24.0)*fineness), 2);
    }

    public double silverValue(Commodity commodity, String units, double weight, double fineness){
        double pricePerGram = getValuePerGram(commodity.getPrice(), commodity.getUnits());
        double weightInGrams = getValueInGrams(weight, units);

        double pureValue = pricePerGram * weightInGrams;
        return round(((pureValue/24.0)*fineness), 2);
    }

    public double platinumValue(Commodity commodity, String units, double weight, double fineness){
        double pricePerGram = getValuePerGram(commodity.getPrice(), commodity.getUnits());
        double weightInGrams = getValueInGrams(weight, units);

        double pureValue = pricePerGram * weightInGrams;
        return round(((pureValue/1000.0)*fineness), 2);
    }

    public double GrossValue(Commodity commodity, String units, double weight){
        double pricePerGram = getValuePerGram(commodity.getPrice(), commodity.getUnits());
        double weightInGrams = getValueInGrams(weight, units);

        return round((pricePerGram * weightInGrams), 2);
    }


    private double getValuePerGram(double value, String units){

        String[] weights = mActivity.getResources().getStringArray(R.array.calc_spinner_array_weight_precious_metals);

        if(units.equalsIgnoreCase(weights[0]))//troy ounces
        {
            return (value / 31.10347689);
        }
        else if(units.equalsIgnoreCase((weights[1])))//grams
        {
            return value;
        }
        else if(units.equalsIgnoreCase(weights[2]))//U.S pounds
        {
            return (value / 453.592);
        }
        else if(units.equalsIgnoreCase(weights[3]))//kilograms
        {
            return (value / 1000.0);
        }
        else if(units.equalsIgnoreCase(weights[4]))//tonnes
        {
            return (value / 1000000.0);
        }

        return value;
    }

    private double getValueInGrams(double value, String units){

        String[] weights = mActivity.getResources().getStringArray(R.array.calc_spinner_array_weight_precious_metals);

        if(units.equalsIgnoreCase(weights[0]))//troy ounces
        {
            return (value * 31.10347689);
        }
        else if(units.equalsIgnoreCase(weights[1]))//grams
        {
            return value;
        }
        else if(units.equalsIgnoreCase(weights[2]))//U.S pounds
        {
            return (value * 453.592);
        }
        else if(units.equalsIgnoreCase(weights[3]))//kilograms
        {
            return (value * 1000.0);
        }
        else if(units.equalsIgnoreCase(weights[4]))//tons
        {
            return (value * 1000000.0);
        }

        return value;
    }

    public  double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public  static double staticRound(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
