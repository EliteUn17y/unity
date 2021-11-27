package me.eliteun17y.unity.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionUtil {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double metersPerSecondToMilesPerHour(double mps) {
        return round(mps * 2.236936, 2);
    }

    public static double metersPerSecondToKilometersPerHour(double mps) {
        return round(mps * 3.6, 2);
    }
}
