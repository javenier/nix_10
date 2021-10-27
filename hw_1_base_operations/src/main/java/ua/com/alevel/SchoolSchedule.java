package ua.com.alevel;

public class SchoolSchedule {

    private static final int ONE_HOUR = 60;
    private static final int LESSON_DURATION = 45;
    private static final int SMALL_BREAK = 5;
    private static final int BIG_BREAK = 15;

    public static void calcEndOfLesson(int lesson) {
        int start = ONE_HOUR * 9;
        int m = start + lesson * LESSON_DURATION;
        m += (lesson - 1) * SMALL_BREAK;
        m += (lesson - 1) / 2 * (BIG_BREAK - SMALL_BREAK);
        System.out.println("End of " + lesson + " lesson: " + m / 60 + ":" + m % 60);
    }
}
