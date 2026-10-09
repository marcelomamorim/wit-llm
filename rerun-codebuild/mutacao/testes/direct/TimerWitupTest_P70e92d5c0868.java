/* @test    @summary Test Timer.scheduleAtFixedRate with valid parameters and exception cases    @run main TimerWitupTest_P70e92d5c0868 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P70e92d5c0868 {

    public static void main(String[] args) {
        testScheduleAtFixedRateValid();
        testScheduleAtFixedRateNegativeDelay();
        testScheduleAtFixedRateZeroPeriod();
    }

    static void testScheduleAtFixedRateValid() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        long delay = 100;
        long period = 200;
        try {
            timer.scheduleAtFixedRate(task, delay, period);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid schedule", e);
        }
        // Wait enough time for the task to execute at least once
        try {
            Thread.sleep(delay + period + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // No direct observable state; test passes if no exception thrown
        timer.cancel();
    }

    static void testScheduleAtFixedRateNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, -10, 100);
            throw new AssertionError("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    static void testScheduleAtFixedRateZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 0, 0);
            throw new AssertionError("Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            timer.cancel();
        }
    }
}
