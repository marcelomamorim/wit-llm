/*
 * @test
 * @summary Deterministic tests for java.util.Timer.scheduleAtFixedRate(TimerTask, long, long)
 * @run main TimerWitupTest_P64fe5ac57b80
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P64fe5ac57b80 {
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
        } catch (IllegalArgumentException iae) {
            // expected
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
            timer.scheduleAtFixedRate(task, 1000L, 0L);
            throw new RuntimeException("Expected IllegalArgumentException for non-positive period");
        } catch (IllegalArgumentException iae) {
            // expected
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
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 10L, 10L);
            // Wait briefly to allow task to run
            Thread.sleep(50);
            if (!ran[0]) {
                throw new RuntimeException("Task did not run as expected");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted during sleep", e);
        } finally {
            timer.cancel();
        }
    }
}
