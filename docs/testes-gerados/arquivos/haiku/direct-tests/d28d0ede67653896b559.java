/*
 * @test
 * @summary Verifies argument validation and observable periodic execution of Timer.schedule(TimerTask, long, long)
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
        testNegativeDelayAndInvalidPeriodRejected();
        testZeroDelayPeriodicTaskExecutes();
        System.out.println("PASS");
    }

    static TimerTask newTask() {
        return new TimerTask() {
            public void run() {
            }
        };
    }

    static void expectIllegalArgument(Timer timer, TimerTask task, long delay, long period, String name) {
        try {
            timer.schedule(task, delay, period);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException(name + ": expected IllegalArgumentException");
    }

    static void testNegativeDelayRejected() {
        Timer timer = new Timer(true);
        try {
            expectIllegalArgument(timer, newTask(), -1L, 10L, "negative delay");
        } finally {
            timer.cancel();
        }
    }

    static void testZeroPeriodRejected() {
        Timer timer = new Timer(true);
        try {
            expectIllegalArgument(timer, newTask(), 0L, 0L, "zero period");
        } finally {
            timer.cancel();
        }
    }

    static void testNegativePeriodRejected() {
        Timer timer = new Timer(true);
        try {
            expectIllegalArgument(timer, newTask(), 0L, -5L, "negative period");
        } finally {
            timer.cancel();
        }
    }

    static void testNegativeDelayAndInvalidPeriodRejected() {
        Timer timer = new Timer(true);
        try {
            expectIllegalArgument(timer, newTask(), -1L, 0L, "negative delay with zero period");
        } finally {
            timer.cancel();
        }
    }

    static void testZeroDelayPeriodicTaskExecutes() throws InterruptedException {
        Timer timer = new Timer(true);
        final CountDownLatch latch = new CountDownLatch(2);
        TimerTask task = new TimerTask() {
            public void run() {
                latch.countDown();
            }
        };
        try {
            timer.schedule(task, 0L, 10L);
            if (!latch.await(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("periodic task did not run twice");
            }
        } finally {
            timer.cancel();
        }
    }
}
