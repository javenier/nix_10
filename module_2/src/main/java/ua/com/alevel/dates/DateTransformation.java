package ua.com.alevel.dates;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTransformation {

    private static final String YEAR_MONTH_DAY_SLASH_REGEX = "\\d{4}/\\d{2}/\\d{2}";
    private static final String DAY_MONTH_YEAR_SLASH_REGEX = "\\d{2}/\\d{2}/\\d{4}";
    private static final String MONTH_DAY_YEAR_DASH_REGEX = "\\d{2}-\\d{2}-\\d{4}";

    public static Set<String> transformInputToDates(String input) {
        Set<String> dates = getDates(input);
        dates = filterValidDates(dates);
        return toFinalFormat(dates);
    }

    private static Set<String> toFinalFormat(Set<String> set) {
        Set<String> result = new HashSet<>();
        for(String s : set) {
            String[] tokens;
            String date;
            if(s.contains("/")) {
                tokens = s.split("/");
                if(tokens[0].length() == 4) {
                    date = tokens[0] + tokens[1] + tokens[2];
                } else {
                    date = tokens[2] + tokens[1] + tokens[0];
                }
            } else {
                tokens = s.split("-");
                date = tokens[2] + tokens[0] + tokens[1];
            }
            result.add(date);
        }
        return result;
    }

    private static Set<String> filterValidDates(Set<String> set) {
        Set<String> filtered = new HashSet<>();
        for(String s : set) {
            if(isValidDate(s))
                filtered.add(s);
        }
        return filtered;
    }

    private static boolean isValidDate(String date) {
        String[] tokens;
        if(date.contains("/")) {
            tokens = date.split("/");
            if(tokens[0].length() == 4) {
                return checkDaysAndMonths(tokens, 2, 1);
            } else {
                return checkDaysAndMonths(tokens, 0, 1);
            }
        } else {
            tokens = date.split("-");
            return checkDaysAndMonths(tokens, 1, 0);
        }
    }

    private static boolean checkDaysAndMonths(String tokens[], int indexDays, int indexMonth) {
        if(Integer.parseInt(tokens[indexMonth]) < 1 || Integer.parseInt(tokens[indexMonth]) > 12)
            return false;
        if(Integer.parseInt(tokens[indexDays]) < 1 || Integer.parseInt(tokens[indexDays]) > 31)
            return false;
        return true;
    }

    private static Set<String> getDates(String input) {
        Set<String> dates = new HashSet<>();
        Pattern pattern = Pattern.compile(YEAR_MONTH_DAY_SLASH_REGEX + "|" + DAY_MONTH_YEAR_SLASH_REGEX + "|" + MONTH_DAY_YEAR_DASH_REGEX);
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            dates.add(input.substring(matcher.start(), matcher.end()));
        }
        return dates;
    }
}