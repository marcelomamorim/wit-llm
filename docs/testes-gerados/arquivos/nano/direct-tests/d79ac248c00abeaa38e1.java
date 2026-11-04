/* @test    @summary Test Timer.schedule with valid Date and positive period    @run main TimerWitupTest */

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class TimerWitupTest {

    public static void main(String[] args) {
        testScheduleWithValidDateAndPeriod();
        testScheduleWithZeroPeriodThrows();
        testScheduleWithNegativePeriodThrows();
    }

    static void testScheduleWithValidDateAndPeriod() {
        Timer timer = new Timer();
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        TimerTask task = new TimerTask() {
            boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        timer.schedule(task, futureDate, 1000L);
        // Since task execution is asynchronous, wait briefly to allow scheduling
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        // No exception thrown, test passes
        timer.cancel();
    }

    static void testScheduleWithZeroPeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // do nothing
            }
        };
        try {
            timer.schedule(task, new Date(System.currentTimeMillis() + 1000), 0L);
            assert false : "Expected IllegalArgumentException for zero period";
        } catch (IllegalArgumentException e) {
            // expected
        }
        timer.cancel();
    }

    static void testScheduleWithNegativePeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // do nothing
            }
        };
        try {
            timer.schedule(task, new Date(System.currentTimeMillis() + 1000), -100L);
            assert false : "Expected IllegalArgumentException for negative period";
        } catch (IllegalArgumentException e) {
            // expected
        }
        timer.cancel();
    }
}
