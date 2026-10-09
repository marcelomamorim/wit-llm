/* @test    @summary Test for Timer.scheduleAtFixedRate handling of invalid arguments    @run main TimerWitupTest_P6224cc7e0def */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P6224cc7e0def {

    public static void main(String[] args) {
        testNegativeDelay();
        testZeroPeriod();
        testNegativePeriod();
        testValidScheduling();
    }

    private static void testNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, -1, 1000);
            throw new AssertionError("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative delay: " + e);
        } finally {
            timer.cancel();
        }
    }

    private static void testZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 1000, 0);
            throw new AssertionError("Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero period: " + e);
        } finally {
            timer.cancel();
        }
    }

    private static void testNegativePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 1000, -500);
            throw new AssertionError("Expected IllegalArgumentException for negative period");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative period: " + e);
        } finally {
            timer.cancel();
        }
    }

    private static void testValidScheduling() {
        Timer timer = new Timer();
        final boolean[] executed = {false};
        TimerTask task = new TimerTask() {
            public void run() {
                executed[0] = true;
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 0, 10);
            Thread.sleep(50); // wait for task to run
            if (!executed[0]) {
                throw new AssertionError("Scheduled task did not execute as expected");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        } finally {
            timer.cancel();
        }
    }
}
