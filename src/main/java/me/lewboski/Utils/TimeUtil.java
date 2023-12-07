package me.lewboski.Utils;

public class TimeUtil {
    public static final long ONE_SECOND = 1_000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("Error caught for 'sleep'");
        }
    }

    public static void longSleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Error caught for 'longSleep'");
        }
    }

    public static void customSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("Error caught for 'customSleep'");
        }
    }
}
