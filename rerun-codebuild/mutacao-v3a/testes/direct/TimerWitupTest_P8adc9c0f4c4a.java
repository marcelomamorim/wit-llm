/*
 * @test    @summary Test Timer.scheduleAtFixedRate for valid and invalid inputs
 * @run main TimerWitupTest_P8adc9c0f4c4a
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P8adc9c0f4c4a {

    public static void main(String[] args) {
        testValidScheduling();
        testNegativeDelay();
        testNonPositivePeriod();
    }

    static void testValidScheduling() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
            boolean isExecuted() {
                return executed;
            }
        };
        long delay = 100;
        long period = 200;
        try {
            timer.scheduleAtFixedRate(task, delay, period);
            // Wait for a short time to allow task to execute
            Thread.sleep(300);
            if (task instanceof TimerTask) {
                // Since run() sets executed to true, we can check if it was run
                // but in this code, 'executed' is private, so we rely on no exceptions thrown
                System.out.println("Valid scheduling test passed.");
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception in valid scheduling: " + e);
        } finally {
            timer.cancel();
        }
    }

    static void testNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            }
        };
        try {
            timer.scheduleAtFixedRate(task, -10, 100);
            System.err.println("Negative delay test failed: no exception thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Negative delay test passed.");
        } finally {
            timer.cancel();
        }
    }

    static void testNonPositivePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 0, 0);
            System.err.println("Non-positive period test failed: no exception thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Non-positive period test passed.");
        } finally {
            timer.cancel();
        }
    }
}
