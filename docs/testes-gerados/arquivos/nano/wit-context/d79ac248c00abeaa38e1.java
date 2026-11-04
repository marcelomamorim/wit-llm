/* @test    @summary Test scheduling with valid parameters and exception for non-positive period    @run main TimerWitupTest */

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class TimerWitupTest {

    public static void main(String[] args) {
        testScheduleValid();
        testScheduleNegativePeriod();
        testScheduleZeroPeriod();
        testSchedulePositivePeriod();
    }

    static void testScheduleValid() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        try {
            timer.schedule(task, futureDate, 1000);
            // No exception expected
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected IllegalArgumentException for valid parameters", e);
        }
        timer.cancel();
    }

    static void testScheduleNegativePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        try {
            timer.schedule(task, futureDate, -1000);
            throw new AssertionError("Expected IllegalArgumentException for negative period")
        } catch (IllegalArgumentException e) {
            // expected
        }
        timer.cancel();
    }

    static void testScheduleZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        try {
            timer.schedule(task, futureDate, 0);
            throw new AssertionError("Expected IllegalArgumentException for zero period")
        } catch (IllegalArgumentException e) {
            // expected
        }
        timer.cancel();
    }

    static void testSchedulePositivePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        try {
            timer.schedule(task, futureDate, 1000);
            // No exception expected
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected IllegalArgumentException for positive period", e);
        }
        timer.cancel();
    }
}
