package com.sireph.technics.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String DATE_PATTERN = "^(0?[1-9]|1[012])[/ .-](0?[1-9]|[12]\\d|3[01])[ /.-]((19|20)\\d\\d)$";
    private static final String TIME_PATTERN = "^(0?\\d|1\\d|2[0-3])[:. -](0?\\d|[1-5]\\d)$";

    public static boolean validateDate(String date){
        Matcher matcher = Pattern.compile(DATE_PATTERN).matcher(date);

        if(matcher.matches()){
            matcher.reset();

            if(matcher.find()){
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }
                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        return !day.equals("30") && !day.equals("31");
                    } else{
                        return !day.equals("29") && !day.equals("30") && !day.equals("31");
                    }
                } else{
                    return true;
                }
            } else{
                return false;
            }
        } else{
            return false;
        }
    }

    public static boolean validateTime(String time) {
        Matcher matcher = Pattern.compile(TIME_PATTERN).matcher(time);

        return matcher.matches();
    }

    public static boolean validateInt(String integer, int min, int max) {
        int value;
        try {
            value = Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            return false;
        }
        return value >= min && value <= max;
    }

    public static boolean validateDouble(String aDouble, double min, double max) {
        double value;
        try {
            value = Double.parseDouble(aDouble);
        } catch (NumberFormatException e) {
            return false;
        }
        return value >= min && value <= max;
    }
}
