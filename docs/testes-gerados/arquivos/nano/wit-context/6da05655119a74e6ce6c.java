/*
 * @test
 * @summary Test for Timer.schedule(TimerTask, Date) method.
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class TimerWitupTest {

    public static void main(String[] args) {
        testScheduleWithValidDate();
        testScheduleWithNullTask();
        testScheduleWithPastDate();
    }

    static void testScheduleWithValidDate() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        try {
            timer.schedule(task, futureDate);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid date", e);
        }
    }

    static void testScheduleWithNullTask() {
        Timer timer = new Timer();
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        try {
            timer.schedule(null, futureDate);
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null task", e);
        }
    }

    static void testScheduleWithPastDate() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        Date pastDate = new Date(System.currentTimeMillis() - 1000);
        try {
            timer.schedule(task, pastDate);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for past date", e);
        }
    }
}
