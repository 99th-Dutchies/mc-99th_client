package nl._99th_dutchclient.util;

public class MCStringUtils
{
    public static String strip(String in) {
        return in.replaceAll("\u00a70|\u00a71|\u00a72|\u00a73|\u00a74|\u00a75|\u00a76|\u00a77|\u00a78|\u00a79|\u00a7a|\u00a7b|\u00a7c|\u00a7d|\u00a7e|\u00a7f|\u00a7k|\u00a7l|\u00a7m|\u00a7n|\u00a7o|\u00a7r", "");
    }

    public static String stripMagic(String in) {
        return in.replaceAll("\u00a7k", "");
    }

    public static int tryParse(String in) {
        int parsed = 0;

        try{
            parsed = Integer.parseInt(in);
        } catch(NumberFormatException e) {
            parsed = 0;
        }

        return parsed;
    }
}
