/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, Date) for IllegalArgumentException on negative time
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class TimerWitupTest {
    public static void main(String[] args) {
        testScheduleWithNegativeTime();
        testScheduleWithValidTime();
    }

    private static void testScheduleWithNegativeTime() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        Date negativeDate = new Date(-1L); // time.getTime() < 0
        try {
            timer.schedule(task, negativeDate);
            throw new RuntimeException("Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    private static void testScheduleWithValidTime() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        Date now = new Date();
        try {
            timer.schedule(task, now);
            // success if no exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid time", e);
        } finally {
            timer.cancel();
        }
    }
}
