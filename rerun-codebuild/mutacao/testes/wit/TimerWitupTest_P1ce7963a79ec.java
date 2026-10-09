/*
 * @test
 * @summary Tests for java.util.Timer.schedule method for exception paths
 * @run main TimerWitupTest_P1ce7963a79ec
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P1ce7963a79ec {

    public static void main(String[] args) {
        testNegativeDelayThrowsException();
        testZeroPeriodThrowsException();
        testDelayPlusMillisExceedsCurrentTimeThrowsException();
    }

    private static void testNegativeDelayThrowsException() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, -1, 1000);
            System.out.println("Failed: Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for negative delay");
        }
    }

    private static void testZeroPeriodThrowsException() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, 1000, 0);
            System.out.println("Failed: Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for zero period");
        }
    }

    private static void testDelayPlusMillisExceedsCurrentTimeThrowsException() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        long delay = 1000;
        long period = 1;
        long currentTime = System.currentTimeMillis();
        // Calculate a delay that, when added to current time, exceeds Long.MAX_VALUE to simulate overflow
        long overflowDelay = Long.MAX_VALUE - currentTime + 1;
        try {
            timer.schedule(task, overflowDelay, period);
            System.out.println("Failed: Expected IllegalArgumentException for delay causing overflow");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for delay causing overflow");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception type: " + e);
        }
    }
}
