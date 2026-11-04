/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, Date, long) for argument validation and scheduling
 * @run main TimerWitupTest
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) {
        testScheduleWithNonPositivePeriod();
        testScheduleWithNegativeFirstTime();
        testScheduleWithValidArguments();
    }

    private static void testScheduleWithNonPositivePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        Date now = new Date();
        try {
            timer.schedule(task, now, 0L);
            throw new RuntimeException("Expected IllegalArgumentException for period=0");
        } catch (IllegalArgumentException expected) {
        }
        try {
            timer.schedule(task, now, -1L);
            throw new RuntimeException("Expected IllegalArgumentException for period<0");
        } catch (IllegalArgumentException expected) {
        }
        timer.cancel();
    }

    private static void testScheduleWithNegativeFirstTime() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        Date negativeDate = new Date(-1L);
        try {
            timer.schedule(task, negativeDate, 1L);
            throw new RuntimeException("Expected IllegalArgumentException for firstTime.getTime()<0");
        } catch (IllegalArgumentException expected) {
        }
        timer.cancel();
    }

    private static void testScheduleWithValidArguments() {
        Timer timer = new Timer();
        final boolean[] ran = {false};
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        Date future = new Date(System.currentTimeMillis() + 100);
        timer.schedule(task, future, 1L);
        // Wait briefly to allow task to run
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        if (!ran[0]) {
            throw new RuntimeException("Scheduled task did not run as expected");
        }
        timer.cancel();
    }
}
