package nl._99th_dutchclient.util;

public class NonLinearTime
{
    public static double PercentageToDouble(double in) {
        if(in <= 6) return 30.0D * in;
        if(in <= 13) return 60.0D * (in - 3);
        if(in <= 17) return 300.0D * (in - 11);
        else return 900.0D * (in - 15);
    }

    public static double DoubleToPercentage(double in) {
        if(in <= 180) return in / 30.0D;
        if(in <= 600) return in / 60.0D + 3;
        if(in <= 1800) return in / 300.0D + 11;
        else return in / 900.0D + 15;
    }
}
