/*
 * @test
 * @summary Behavioral tests for Timer.schedule(TimerTask, Date)
 * @run main TimerWitupTest
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {

    public static void main(String[] args) throws Exception {
        testPastDateRunsTask();
        testFutureDateRunsTask();
        testNullDateThrowsNullPointerException();
    }

    static void testPastDateRunsTask() throws InterruptedException {
        Timer timer = new Timer(true);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    latch.countDown();
                }
            }, new Date(0L));
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Task scheduled with past date was not executed");
            }
        } finally {
            timer.cancel();
        }
    }

    static void testFutureDateRunsTask() throws InterruptedException {
        Timer timer = new Timer(true);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    latch.countDown();
                }
            }, new Date(System.currentTimeMillis() + 50L));
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Task scheduled with near future date was not executed");
            }
        } finally {
            timer.cancel();
        }
    }

    static void testNullDateThrowsNullPointerException() {
        Timer timer = new Timer(true);
        try {
            try {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                    }
                }, null);
            } catch (NullPointerException expected) {
                return;
            }
            throw new RuntimeException("Expected NullPointerException for null Date");
        } finally {
            timer.cancel();
        }
    }
}
