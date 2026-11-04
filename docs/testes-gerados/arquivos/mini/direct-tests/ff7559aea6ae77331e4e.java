/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, long) for delay validation and scheduling
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) {
        testNegativeDelayThrows();
        testZeroDelaySchedulesTask();
        testPositiveDelaySchedulesTask();
    }

    private static void testNegativeDelayThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        try {
            timer.schedule(task, -1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    private static void testZeroDelaySchedulesTask() {
        final boolean[] ran = {false};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        timer.schedule(task, 0L);
        waitForTaskRun(ran);
        timer.cancel();
        if (!ran[0]) {
            throw new RuntimeException("Task did not run with zero delay");
        }
    }

    private static void testPositiveDelaySchedulesTask() {
        final boolean[] ran = {false};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        timer.schedule(task, 100L);
        waitForTaskRun(ran);
        timer.cancel();
        if (!ran[0]) {
            throw new RuntimeException("Task did not run with positive delay");
        }
    }

    private static void waitForTaskRun(boolean[] ran) {
        long start = System.currentTimeMillis();
        while (!ran[0] && System.currentTimeMillis() - start < 2000) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
