package com.sireph.technics.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String DATE_PATTERN = "^(0?[1-9]|[12]\\d|3[01])[ /.-](0?[1-9]|1[012])[/ .-]((19|20)\\d\\d)$";
    private static final String TIME_PATTERN = "^(0?\\d|1\\d|2[0-3])[:. -](0?\\d|[1-5]\\d)$";

    public static boolean validateDate(String s, boolean allowEmpty) {
        if (allowEmpty && s.equals("")) {
            return true;
        }
        Matcher matcher = Pattern.compile(DATE_PATTERN).matcher(s);

        if (matcher.matches()) {
            matcher.reset();

            if (matcher.find()) {
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(Objects.requireNonNull(matcher.group(3)));

                assert day != null;
                if (day.equals("31") &&
                        (Objects.equals(month, "4") || Objects.equals(month, "6") || Objects.equals(month, "9") ||
                                Objects.equals(month, "11") || Objects.equals(month, "04") || Objects.equals(month, "06") ||
                                Objects.equals(month, "09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else {
                    assert month != null;
                    if (month.equals("2") || month.equals("02")) {
                        //leap year
                        if (year % 4 == 0) {
                            return !day.equals("30") && !day.equals("31");
                        } else {
                            return !day.equals("29") && !day.equals("30") && !day.equals("31");
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validateTime(String s, boolean allowEmpty) {
        if (allowEmpty && s.equals("")) {
            return true;
        }
        Matcher matcher = Pattern.compile(TIME_PATTERN).matcher(s);

        return matcher.matches();
    }

    public static boolean validateInt(String s, int min, int max, boolean allowEmpty) {
        if (allowEmpty && s.equals("")) {
            return true;
        }
        int value;
        try {
            value = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return value >= min && value <= max;
    }

    public static boolean validateDouble(String s, double min, double max, boolean allowEmpty) {
        if (allowEmpty && s.equals("")) {
            return true;
        }
        double value;
        try {
            value = Double.parseDouble(s.replace(',', '.'));
        } catch (NumberFormatException e) {
            return false;
        }
        return value >= min && value <= max;
    }
}
