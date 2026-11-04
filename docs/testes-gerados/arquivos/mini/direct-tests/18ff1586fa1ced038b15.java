/*
 * @test
 * @summary Test java.util.Timer.scheduleAtFixedRate method for argument validation and basic scheduling
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) {
        testNegativeDelayThrows();
        testNonPositivePeriodThrows();
        testValidScheduleAtFixedRate();
    }

    static void testNegativeDelayThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        try {
            timer.scheduleAtFixedRate(task, -1L, 1000L);
            throw new RuntimeException("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            if (!"Negative delay.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } finally {
            timer.cancel();
        }
    }

    static void testNonPositivePeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        try {
            timer.scheduleAtFixedRate(task, 0L, 0L);
            throw new RuntimeException("Expected IllegalArgumentException for non-positive period");
        } catch (IllegalArgumentException e) {
            if (!"Non-positive period.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } finally {
            timer.cancel();
        }
    }

    static void testValidScheduleAtFixedRate() {
        final boolean[] ran = {false};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
                cancel();
            }
        };
        timer.scheduleAtFixedRate(task, 10L, 10L);
        // Wait up to 500ms for task to run
        long start = System.currentTimeMillis();
        while (!ran[0] && System.currentTimeMillis() - start < 500) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for task execution");
            }
        }
        timer.cancel();
        if (!ran[0]) {
            throw new RuntimeException("Scheduled task did not run as expected");
        }
    }
}
