/*
 * @test
 * @summary Witup-derived tests for Timer.schedule(TimerTask, Date)
 * @run main TimerWitupTest
 */
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        testNegativeTimeThrowsIllegalArgumentException();
        testScheduleAfterCancelThrowsIllegalStateException();
        testFutureTaskExecutes();
        testPastTaskExecutes();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeTimeThrowsIllegalArgumentException() {
        Timer timer = new Timer(true);
        try {
            timer.schedule(new TimerTask() {
                public void run() { }
            }, new Date(-1L));
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    static void testScheduleAfterCancelThrowsIllegalStateException() {
        Timer timer = new Timer(true);
        timer.cancel();
        try {
            timer.schedule(new TimerTask() {
                public void run() { }
            }, new Date(System.currentTimeMillis() + 1000L));
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testFutureTaskExecutes() throws InterruptedException {
        Timer timer = new Timer(true);
        CountDownLatch latch = new CountDownLatch(1);
        try {
            timer.schedule(new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            }, new Date(System.currentTimeMillis() + 100L));
            check(latch.await(10, TimeUnit.SECONDS), "future task was not executed");
        } finally {
            timer.cancel();
        }
    }

    static void testPastTaskExecutes() throws InterruptedException {
        Timer timer = new Timer(true);
        CountDownLatch latch = new CountDownLatch(1);
        try {
            timer.schedule(new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            }, new Date(0L));
            check(latch.await(10, TimeUnit.SECONDS), "past task was not executed");
        } finally {
            timer.cancel();
        }
    }
}
