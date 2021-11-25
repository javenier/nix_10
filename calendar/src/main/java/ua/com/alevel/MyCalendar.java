package ua.com.alevel;

import ua.com.alevel.exceptions.IncorrectDataValueException;
import ua.com.alevel.exceptions.RusMonthException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MyCalendar implements Comparable<MyCalendar> {

    private static final int MS_IN_S = 1000;
    private static final int MS_IN_MIN = 60_000;
    private static final int MS_IN_HOUR = 3_600_000;
    private static final int MS_IN_DAY = 86_400_000;
    private static final long MS_IN_MONTH = 2_628_000_000l;
    private static final long MS_IN_YEAR = 31_560_000_000l;
    private static final int DAYS_IN_LEAP = 366;
    private static final int DAYS_IN_NOT_LEAP = 365;

    private long ms = 0;
    private long s = 0;
    private long min = 0;
    private long hours = 0;
    private long days = 1;
    private long months = 1;
    private long years = 0;
    private long fullDateInMs = 0;
    private String format;
    private String formatOfDate;
    private String formatOfTime;

    public MyCalendar(long ms, long s, long min, long hours, long days, long months, long years) throws IncorrectDataValueException {
        setMs(ms);
        setS(s);
        setMin(min);
        setHours(hours);
        setDays(days);
        setMonths(months);
        setYears(years);
        dateToMs();
    }

    public MyCalendar(String date, String format) throws RusMonthException, IncorrectDataValueException {
        this.format = format;
        String[] tokens = format.split(" ");
        formatOfDate = tokens[0];
        if (tokens.length == 2)
            formatOfTime = tokens[1];
        parseDate(date);
        dateToMs();
    }

    public void addToDate(long value, String type) throws IncorrectDataValueException {
        switch (type) {
            case "s": {
                value *= MS_IN_S;
                break;
            }
            case "min": {
                value *= MS_IN_MIN;
                break;
            }
            case "hour": {
                value *= MS_IN_HOUR;
                break;
            }
            case "day": {
                value *= MS_IN_DAY;
                break;
            }
            case "month": {
                value *= MS_IN_MONTH;
                break;
            }
            case "year": {
                value *= MS_IN_YEAR;
                break;
            }
        }
        fullDateInMs += value;
        msToDate();
    }

    public void subtractFromDate(long value, String type) throws IncorrectDataValueException {
        switch (type) {
            case "s": {
                value *= MS_IN_S;
                break;
            }
            case "min": {
                value *= MS_IN_MIN;
                break;
            }
            case "hour": {
                value *= MS_IN_HOUR;
                break;
            }
            case "day": {
                value *= MS_IN_DAY;
                break;
            }
            case "month": {
                value *= MS_IN_MONTH;
                break;
            }
            case "year": {
                value *= MS_IN_YEAR;
                break;
            }
        }
        fullDateInMs -= value;
        msToDate();
    }

    public void difference(MyCalendar instance) {
        long yearDifference = Math.abs(this.years - instance.years);
        long monthDifference = Math.abs(this.months - instance.months);
        long dayDifference = Math.abs(this.days - instance.days);
        long hourDifference = Math.abs(this.hours - instance.hours);
        long minuteDifference = Math.abs(this.min - instance.min);
        long secondDifference = Math.abs(this.s - instance.s);
        long millisecondDifference = Math.abs(this.ms - instance.ms);
        System.out.println("Difference:\n" +
                "years: " + yearDifference +
                ", months: " + monthDifference +
                ", days: " + dayDifference +
                ", hours: " + hourDifference +
                ", minutes: " + minuteDifference +
                ", seconds: " + secondDifference +
                ", milliseconds: " + millisecondDifference);
    }

    public static List<MyCalendar> sortAsc(MyCalendar... calendars) {
        List<MyCalendar> dates = Arrays.asList(calendars);
        Collections.sort(dates);
        return dates;
    }

    public static List<MyCalendar> sortDesc(MyCalendar... calendars) {
        List<MyCalendar> dates = Arrays.asList(calendars);
        Collections.sort(dates, Collections.reverseOrder());
        return dates;
    }

    public void showDate() throws RusMonthException {
        String[] tokens = format.split(" ");
        if (!formatOfDate.equals(tokens[0]))
            formatOfDate = tokens[0];
        if (tokens.length == 2) {
            if (formatOfTime != null) {
                if (!formatOfTime.equals(tokens[1]))
                    formatOfTime = tokens[1];
            } else {
                formatOfTime = tokens[1];
            }
            showOnlyDate(tokens[0]);
            System.out.print(" ");
            showOnlyTime(tokens[1]);
        } else {
            showOnlyDate(format);
        }
        System.out.println();
    }

    private void showOnlyTime(String token) {
        String[] timeTokens = token.split(":");
        if (timeTokens.length == 2) {
            showHM();
        } else if (timeTokens.length == 3) {
            showHMS();
        } else if (timeTokens.length == 4) {
            showHMSMs();
        }
    }

    private void showHM() {
        System.out.print(hours + ":" + min);
    }

    private void showHMS() {
        System.out.print(hours + ":" + min + ":" + s);
    }

    private void showHMSMs() {
        System.out.print(hours + ":" + min + ":" + s + ":" + ms);
    }

    private void showOnlyDate(String token) throws RusMonthException {
        if (formatOfDate.equals("dd/mm/yyyy")) {
            showDMY();
        } else if (formatOfDate.equals("mm/dd/yyyy")) {
            showMDY();
        } else if (formatOfDate.equals("mmm-dd-yyyy")) {
            showMMMDY();
        } else if (formatOfDate.equals("dd-mmm-yyyy")) {
            showDMMMY();
        }
    }

    private void showDMMMY() throws RusMonthException {
        System.out.print(days + "-" + numberToRusMonth(months) + "-" + years);
    }

    private void showMMMDY() throws RusMonthException {
        System.out.print(numberToRusMonth(months) + "-" + days + "-" + years);
    }

    private String numberToRusMonth(long months) throws RusMonthException {
        switch ((int) months) {
            case 1:
                return "Январь";
            case 2:
                return "Февраль";
            case 3:
                return "Март";
            case 4:
                return "Апрель";
            case 5:
                return "Май";
            case 6:
                return "Июнь";
            case 7:
                return "Июль";
            case 8:
                return "Август";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь";
            case 11:
                return "Ноябрь";
            case 12:
                return "Декабрь";
            default:
                throw new RusMonthException(months + " is not correct number of months. It should be from 1 to 12");
        }
    }

    private void showMDY() {
        System.out.print(months + "/" + days + "/" + years);
    }

    private void showDMY() {
        System.out.print(days + "/" + months + "/" + years);
    }

    private void parseDate(String date) throws RusMonthException, IncorrectDataValueException {
        String[] tokens = date.split(" ");
        if (tokens.length == 2) {
            parseOnlyDate(tokens[0]);
            parseOnlyTime(tokens[1]);
        } else {
            parseOnlyDate(date);
        }
    }

    private void parseOnlyTime(String token) throws IncorrectDataValueException {
        String[] timeTokens = token.split(":");
        if (timeTokens.length == 2) {
            parseHM(timeTokens);
        } else if (timeTokens.length == 3) {
            parseHMS(timeTokens);
        } else if (timeTokens.length == 4) {
            parseHMSMs(timeTokens);
        }
    }

    private void parseHMSMs(String[] timeTokens) throws IncorrectDataValueException {
        if (!timeTokens[0].equals(""))
            setHours(Long.parseLong(timeTokens[0]));
        if (!timeTokens[1].equals(""))
            setMin(Long.parseLong(timeTokens[1]));
        if (!timeTokens[2].equals(""))
            setS(Long.parseLong(timeTokens[2]));
        if (!timeTokens[3].equals(""))
            setMs(Long.parseLong(timeTokens[3]));
    }

    private void parseHMS(String[] timeTokens) throws IncorrectDataValueException {
        if (!timeTokens[0].equals(""))
            setHours(Long.parseLong(timeTokens[0]));
        if (!timeTokens[1].equals(""))
            setMin(Long.parseLong(timeTokens[1]));
        if (!timeTokens[2].equals(""))
            setS(Long.parseLong(timeTokens[2]));
    }

    private void parseHM(String[] timeTokens) throws IncorrectDataValueException {
        if (!timeTokens[0].equals(""))
            setHours(Long.parseLong(timeTokens[0]));
        if (!timeTokens[1].equals(""))
            setMin(Long.parseLong(timeTokens[1]));
    }

    private void parseOnlyDate(String token) throws RusMonthException, IncorrectDataValueException {
        if (formatOfDate.equals("dd/mm/yyyy")) {
            parseDMY(token);
        } else if (formatOfDate.equals("mm/dd/yyyy")) {
            parseMDY(token);
        } else if (formatOfDate.equals("mmm-dd-yyyy")) {
            parseMMMDY(token);
        } else if (formatOfDate.equals("dd-mmm-yyyy")) {
            parseDMMMY(token);
        }
    }

    private void parseDMMMY(String token) throws RusMonthException, IncorrectDataValueException {
        String[] tokens = token.split("-");
        if (!tokens[0].equals(""))
            setDays(Long.parseLong(tokens[0]));
        if (!tokens[1].equals(""))
            setMonths(rusMonthToNumber(tokens[1]));
        if (!tokens[2].equals(""))
            setYears(Long.parseLong(tokens[2]));
    }

    private void parseMMMDY(String token) throws RusMonthException, IncorrectDataValueException {
        String[] tokens = token.split("-");
        if (!tokens[0].equals(""))
            setMonths(rusMonthToNumber(tokens[0]));
        if (!tokens[1].equals(""))
            setDays(Long.parseLong(tokens[1]));
        if (!tokens[2].equals(""))
            setYears(Long.parseLong(tokens[2]));
    }

    private void parseMDY(String token) throws IncorrectDataValueException {
        String[] tokens = token.split("/");
        if (!tokens[0].equals(""))
            setMonths(Long.parseLong(tokens[0]));
        if (!tokens[1].equals(""))
            setDays(Long.parseLong(tokens[1]));
        if (!tokens[2].equals(""))
            setYears(Long.parseLong(tokens[2]));
    }

    private void parseDMY(String token) throws IncorrectDataValueException {
        String[] tokens = token.split("/");
        if (!tokens[0].equals(""))
            setDays(Long.parseLong(tokens[0]));
        if (!tokens[1].equals(""))
            setMonths(Long.parseLong(tokens[1]));
        if (!tokens[2].equals(""))
            setYears(Long.parseLong(tokens[2]));
    }

    private long rusMonthToNumber(String token) throws RusMonthException {
        switch (token) {
            case "Январь":
                return 1;
            case "Февраль":
                return 2;
            case "Март":
                return 3;
            case "Апрель":
                return 4;
            case "Май":
                return 5;
            case "Июнь":
                return 6;
            case "Июль":
                return 7;
            case "Август":
                return 8;
            case "Сентябрь":
                return 9;
            case "Октябрь":
                return 10;
            case "Ноябрь":
                return 11;
            case "Декабрь":
                return 12;
            default:
                throw new RusMonthException(token + " is invalid name of the month. Check if it has written with the capital letter and in nominative");
        }
    }

    public void msToDate() throws IncorrectDataValueException {
        long fullDays = fullDateInMs / MS_IN_DAY;
        long msInCurDay = fullDateInMs - fullDays * MS_IN_DAY;
        setHours(msInCurDay / MS_IN_HOUR);
        msInCurDay = msInCurDay - getHours() * MS_IN_HOUR;
        setMin(msInCurDay / MS_IN_MIN);
        msInCurDay = msInCurDay - getMin() * MS_IN_MIN;
        setS(msInCurDay / MS_IN_S);
        msInCurDay = msInCurDay - getS() * MS_IN_S;
        setMs(msInCurDay);
        int year = 0;
        while (fullDays > 365) {
            if (isLeap(year)) {
                if (fullDays == 366) {
                    break;
                } else {
                    year++;
                    fullDays -= 366;
                }
            } else {
                year++;
                fullDays -= 365;
            }
        }
        setYears(year);
        int month = 1;
        while (fullDays > 32) {
            int counter = 0;
            int daysInMonth = daysInMonth(month);
            for (int i = 0; i < daysInMonth; i++) {
                counter++;
            }
            fullDays -= counter;
            month++;
        }
        setDays(fullDays);
        setMonths(month);
    }

    public void dateToMs() {
        long timeInMs = ms + s * MS_IN_S + min * MS_IN_MIN + hours * MS_IN_HOUR;
        int leapYears = countLeapYears(years);
        long passedDaysToYear = (years - leapYears) * DAYS_IN_NOT_LEAP + leapYears * DAYS_IN_LEAP;
        int passedDaysInYear = 0;
        for (int i = 1; i < months; i++) {
            passedDaysInYear += daysInMonth(i);
        }
        passedDaysInYear += days;
        fullDateInMs = (passedDaysInYear + passedDaysToYear) * MS_IN_DAY + timeInMs;
    }

    private int daysInMonth(int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2: {
                if (isLeap(years))
                    return 29;
                else
                    return 28;
            }
        }
        return 0;
    }

    private int countLeapYears(long year) {
        int count = 0;
        for (int i = 0; i < year; i++) {
            if (isLeap(i))
                count++;
        }
        return count;
    }

    private boolean isLeap(long year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) throws IncorrectDataValueException {
        if (ms >= 0 && ms < 1000) {
            this.ms = ms;
        } else {
            throw new IncorrectDataValueException("Incorrect milliseconds value. It should be from 0 to 999");
        }
    }

    public long getS() {
        return s;
    }

    public void setS(long s) throws IncorrectDataValueException {
        if (s >= 0 && s < 60) {
            this.s = s;
        } else {
            throw new IncorrectDataValueException("Incorrect seconds value. It should be from 0 to 59");
        }
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) throws IncorrectDataValueException {
        if (min >= 0 && min < 60) {
            this.min = min;
        } else {
            throw new IncorrectDataValueException("Incorrect minutes value. It should be from 0 to 59");
        }
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) throws IncorrectDataValueException {
        if (hours >= 0 && hours <= 24) {
            this.hours = hours;
        } else {
            throw new IncorrectDataValueException("Incorrect hours value. It should be from 0 to 24");
        }
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) throws IncorrectDataValueException {
        if (days >= 1 && days < 32) {
            this.days = days;
        } else {
            throw new IncorrectDataValueException("Incorrect days value. It should be from 1 to 31");
        }
    }

    public long getMonths() {
        return months;
    }

    public void setMonths(long months) throws IncorrectDataValueException {
        if (months >= 1 && months < 13) {
            this.months = months;
        } else {
            throw new IncorrectDataValueException("Incorrect months value. It should be from 1 to 12");
        }
    }

    public long getYears() {
        return years;
    }

    public void setYears(long years) throws IncorrectDataValueException {
        if (years >= 0) {
            this.years = years;
        } else {
            throw new IncorrectDataValueException("Incorrect years value. It can't be negative");
        }
    }

    public long getFullDateInMs() {
        return fullDateInMs;
    }

    public void setFullDateInMs(long fullDateInMs) {
        this.fullDateInMs = fullDateInMs;
    }

    @Override
    public String toString() {
        return "MyCalendar{" +
                "ms=" + ms +
                ", s=" + s +
                ", min=" + min +
                ", hours=" + hours +
                ", days=" + days +
                ", months=" + months +
                ", years=" + years +
                ", fullDateInMs=" + fullDateInMs +
                '}';
    }

    @Override
    public int compareTo(MyCalendar o) {
        long res = this.fullDateInMs - o.fullDateInMs;
        if (res > 0)
            return 1;
        else if (res == 0)
            return 0;
        return -1;
    }
}