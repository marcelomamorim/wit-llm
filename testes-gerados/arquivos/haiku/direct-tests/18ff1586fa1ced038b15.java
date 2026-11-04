/*
 * @test
 * @summary Verifies argument validation and periodic execution of Timer.scheduleAtFixedRate
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeDelayRejected();
        testZeroPeriodRejected();
        testNegativePeriodRejected();
        testZeroDelayAccepted();
        testRepeatedExecution();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static TimerTask noopTask() {
        return new TimerTask() {
            public void run() {
            }
        };
    }

    static void testNegativeDelayRejected() {
        Timer timer = new Timer(true);
        try {
            timer.scheduleAtFixedRate(noopTask(), -1, 10);
            throw new RuntimeException("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException expected) {
            check("Negative delay.".equals(expected.getMessage()),
                  "Unexpected message: " + expected.getMessage());
        } finally {
            timer.cancel();
        }
    }

    static void testZeroPeriodRejected() {
        Timer timer = new Timer(true);
        try {
            timer.scheduleAtFixedRate(noopTask(), 0, 0);
            throw new RuntimeException("Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException expected) {
            check("Non-positive period.".equals(expected.getMessage()),
                  "Unexpected message: " + expected.getMessage());
        } finally {
            timer.cancel();
        }
    }

    static void testNegativePeriodRejected() {
        Timer timer = new Timer(true);
        try {
            timer.scheduleAtFixedRate(noopTask(), 0, -5);
            throw new RuntimeException("Expected IllegalArgumentException for negative period");
        } catch (IllegalArgumentException expected) {
            check("Non-positive period.".equals(expected.getMessage()),
                  "Unexpected message: " + expected.getMessage());
        } finally {
            timer.cancel();
        }
    }

    static void testZeroDelayAccepted() throws InterruptedException {
        Timer timer = new Timer(true);
        CountDownLatch latch = new CountDownLatch(1);
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            }, 0, 10);
            check(latch.await(10, TimeUnit.SECONDS), "Task with zero delay was not executed");
        } finally {
            timer.cancel();
        }
    }

    static void testRepeatedExecution() throws InterruptedException {
        Timer timer = new Timer(true);
        CountDownLatch latch = new CountDownLatch(3);
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            }, 0, 10);
            check(latch.await(10, TimeUnit.SECONDS),
                  "Periodic task did not execute at least 3 times, remaining: " + latch.getCount());
        } finally {
            timer.cancel();
        }
    }
}
