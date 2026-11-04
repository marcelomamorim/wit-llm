/*
 * @test
 * @summary Verifies Timer.schedule(TimerTask, Date, long) rejects non-positive periods and runs a periodic task
 * @run main TimerWitupTest
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {

    public static void main(String[] args) throws Exception {
        testZeroPeriodThrowsIllegalArgument();
        testNegativePeriodThrowsIllegalArgument();
        testPositivePeriodExecutesTask();
    }

    static void testZeroPeriodThrowsIllegalArgument() {
        Timer timer = new Timer(true);
        try {
            TimerTask task = new TimerTask() {
                public void run() { }
            };
            try {
                timer.schedule(task, new Date(), 0L);
                throw new AssertionError("Expected IllegalArgumentException for period 0");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        } finally {
            timer.cancel();
        }
    }

    static void testNegativePeriodThrowsIllegalArgument() {
        Timer timer = new Timer(true);
        try {
            TimerTask task = new TimerTask() {
                public void run() { }
            };
            try {
                timer.schedule(task, new Date(), -1L);
                throw new AssertionError("Expected IllegalArgumentException for negative period");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        } finally {
            timer.cancel();
        }
    }

    static void testPositivePeriodExecutesTask() throws InterruptedException {
        Timer timer = new Timer(true);
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            TimerTask task = new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            };
            timer.schedule(task, new Date(), 10L);
            if (!latch.await(30, TimeUnit.SECONDS)) {
                throw new AssertionError("Periodic task did not run");
            }
        } finally {
            timer.cancel();
        }
    }
}
