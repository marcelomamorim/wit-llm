/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, long, long) for argument validation and normal scheduling
 * @run main TimerWitupTest_Pf6a22c5d483b
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_Pf6a22c5d483b {
    public static void main(String[] args) {
        testNegativeDelayThrows();
        testNonPositivePeriodThrows();
        testValidSchedule();
    }

    static void testNegativeDelayThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        try {
            timer.schedule(task, -1L, 1000L);
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
            timer.schedule(task, 1000L, 0L);
            throw new RuntimeException("Expected IllegalArgumentException for non-positive period");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    static void testValidSchedule() {
        Timer timer = new Timer();
        final boolean[] ran = {false};
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        try {
            timer.schedule(task, 10L, 10L);
            // Wait briefly to allow task to run at least once
            Thread.sleep(50);
            if (!ran[0]) {
                throw new RuntimeException("Scheduled task did not run as expected");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            timer.cancel();
        }
    }
}
