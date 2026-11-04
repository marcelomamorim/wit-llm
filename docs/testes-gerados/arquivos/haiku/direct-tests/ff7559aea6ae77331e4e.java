/*
 * @test
 * @summary Timer.schedule(TimerTask, long) rejects negative delays, runs tasks with zero delay, and rejects scheduling after cancel
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        testNegativeDelayRejected();
        testNegativeLongMinValueDelayRejected();
        testZeroDelayExecutesTask();
        testScheduleAfterCancelThrows();
    }

    static void testNegativeDelayRejected() {
        Timer timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                public void run() { }
            }, -1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            if (!"Negative delay.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        } finally {
            timer.cancel();
        }
    }

    static void testNegativeLongMinValueDelayRejected() {
        Timer timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                public void run() { }
            }, Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Long.MIN_VALUE delay");
        } catch (IllegalArgumentException e) {
            if (!"Negative delay.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        } finally {
            timer.cancel();
        }
    }

    static void testZeroDelayExecutesTask() throws InterruptedException {
        Timer timer = new Timer();
        final CountDownLatch latch = new CountDownLatch(1);
        try {
            timer.schedule(new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            }, 0L);
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Task with zero delay was not executed");
            }
        } finally {
            timer.cancel();
        }
    }

    static void testScheduleAfterCancelThrows() {
        Timer timer = new Timer();
        timer.cancel();
        try {
            timer.schedule(new TimerTask() {
                public void run() { }
            }, 0L);
            throw new RuntimeException("Expected IllegalStateException after cancel");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
