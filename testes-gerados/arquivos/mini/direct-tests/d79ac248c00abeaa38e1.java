/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, Date, long) for valid and invalid period values
 * @run main java.util.TimerWitupTest
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) {
        testScheduleWithValidPeriod();
        testScheduleWithZeroPeriod();
        testScheduleWithNegativePeriod();
    }

    private static void testScheduleWithValidPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        Date future = new Date(System.currentTimeMillis() + 1000L);
        long period = 1000L;
        try {
            timer.schedule(task, future, period);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid period", e);
        } finally {
            timer.cancel();
        }
    }

    private static void testScheduleWithZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        Date future = new Date(System.currentTimeMillis() + 1000L);
        long period = 0L;
        try {
            timer.schedule(task, future, period);
            throw new RuntimeException("Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    private static void testScheduleWithNegativePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        Date future = new Date(System.currentTimeMillis() + 1000L);
        long period = -1L;
        try {
            timer.schedule(task, future, period);
            throw new RuntimeException("Expected IllegalArgumentException for negative period");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            timer.cancel();
        }
    }
}
