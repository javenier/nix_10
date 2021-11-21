package ua.com.alevel;

public final class MyCalendar {

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
    private long days = 0;
    private long months = 0;
    private long years = 0;
    private long fullDateInMs = 0;
    private String format;

    public MyCalendar(long ms, long s, long min, long hours, long days, long months, long years) {
        this.ms = ms;
        this.s = s;
        this.min = min;
        this.hours = hours;
        this.days = days;
        this.months = months;
        this.years = years;
    }

    public MyCalendar() {
    }

    public MyCalendar(String date, String format) {
        this.format = format;
        parseDate(date);
    }

    private void parseDate(String date) {
        String[] tokens;
        if(format.equals("dd/mm/yy")) {
            tokens = date.split("/");
            setDays(Long.parseLong(tokens[0]));
            setMonths(Long.parseLong(tokens[1]));
            setYears(Long.parseLong(tokens[2]));
        } else if(format.equals("m/d/yyyy")) {
            tokens = date.split("/");
            setDays(Long.parseLong(tokens[1]));
            setMonths(Long.parseLong(tokens[0]));
            setYears(Long.parseLong(tokens[2]));
        } else if(format.equals("mmm-d-yy")) {
            tokens = date.split("-");
            setMonths(rusMonthToNumber(tokens[0]));
            setDays(Long.parseLong(tokens[1]));
            setYears(Long.parseLong(tokens[2]));
        } else if(format.equals("dd-mmm-yyyy 00:00:00:000")) {
            tokens = date.split("-| |:");
            setDays(Long.parseLong(tokens[0]));
            setMonths(rusMonthToNumber(tokens[1]));
            setYears(Long.parseLong(tokens[2]));
            setHours(Long.parseLong(tokens[3]));
            setMin(Long.parseLong(tokens[4]));
            setS(Long.parseLong(tokens[5]));
            setMs(Long.parseLong(tokens[6]));
        }
    }

    private long rusMonthToNumber(String token) {
        switch(token) {
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
        }
        return 0;
    }


    public MyCalendar msToDate(long milis) {
        MyCalendar myCalendar = new MyCalendar();
        long fullDays = milis / MS_IN_DAY;
        long msInCurDay = milis - fullDays * MS_IN_DAY;
        myCalendar.setHours(msInCurDay / MS_IN_HOUR);
        msInCurDay = msInCurDay - myCalendar.getHours() * MS_IN_HOUR;
        myCalendar.setMin(msInCurDay / MS_IN_MIN);
        msInCurDay = msInCurDay - myCalendar.getMin() * MS_IN_MIN;
        myCalendar.setS(msInCurDay / MS_IN_S);
        msInCurDay = msInCurDay - myCalendar.getS() * MS_IN_S;
        myCalendar.setMs(msInCurDay);
        int year = 0;
        while(fullDays > 365) {
            if(isLeap(year)) {
                if(fullDays == 366) {
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
        myCalendar.setYears(year);
        int month = 1;
        while(fullDays > 32) {
            int counter = 0;
            int daysInMonth = daysInMonth(month);
            for(int i = 0; i < daysInMonth; i++) {
                counter++;
            }
            fullDays -= counter;
            month++;
        }
        myCalendar.setDays(fullDays);
        myCalendar.setMonths(month);
        return myCalendar;
    }

    public void dateToMs() {
        long timeInMs = ms + s * MS_IN_S + min * MS_IN_MIN + hours * MS_IN_HOUR;
        int leapYears = countLeapYears(years);
        long passedDaysToYear = (years - leapYears) * DAYS_IN_NOT_LEAP + leapYears * DAYS_IN_LEAP;
        int passedDaysInYear = 0;
        for(int i = 1; i < months; i++) {
            passedDaysInYear += daysInMonth(i);
        }
        passedDaysInYear += days;
        fullDateInMs = (passedDaysInYear + passedDaysToYear) * MS_IN_DAY + timeInMs;
    }

    private int daysInMonth(int month) {
        switch(month) {
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
                if(isLeap(years))
                    return 29;
                else
                    return 28;
            }
        }
        return 0;
    }

    private int countLeapYears(long year) {
        int count = 0;
        for(int i = 0; i < year; i++) {
            if(isLeap(i))
                count++;
        }
        return count;
    }

    private boolean isLeap(long year) {
        if(year % 4 == 0) {
            if(year % 100 == 0) {
                if(year % 400 == 0) {
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

    public void setMs(long ms) {
        this.ms = ms;
    }

    public long getS() {
        return s;
    }

    public void setS(long s) {
        this.s = s;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public long getMonths() {
        return months;
    }

    public void setMonths(long months) {
        this.months = months;
    }

    public long getYears() {
        return years;
    }

    public void setYears(long years) {
        this.years = years;
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
}
