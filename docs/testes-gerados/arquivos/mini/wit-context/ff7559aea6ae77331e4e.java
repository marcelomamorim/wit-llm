/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, long) for valid and invalid delays
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) {
        testNegativeDelayThrows();
        testValidDelaySchedulesTask();
    }

    private static void testNegativeDelayThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, -1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    private static void testValidDelaySchedulesTask() {
        final boolean[] ran = {false};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        try {
            timer.schedule(task, 10L); // schedule with small positive delay
            // Wait up to 500ms for task to run
            long start = System.currentTimeMillis();
            while (!ran[0] && System.currentTimeMillis() - start < 500) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (!ran[0]) {
                throw new RuntimeException("Scheduled task did not run within expected time");
            }
        } finally {
            timer.cancel();
        }
    }
}
