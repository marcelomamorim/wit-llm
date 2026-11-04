/*
 * @test
 * @summary Tests Timer.schedule(TimerTask, long) argument validation and basic scheduling
 * @run main TimerWitupTest
 */
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeDelayThrowsIllegalArgumentException();
        testOverflowingDelayThrowsIllegalArgumentException();
        testZeroDelayTaskIsExecuted();
    }

    static void testNegativeDelayThrowsIllegalArgumentException() {
        Timer timer = new Timer();
        try {
            TimerTask task = new TimerTask() {
                public void run() {
                }
            };
            boolean thrown = false;
            try {
                timer.schedule(task, -1L);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("IllegalArgumentException expected for negative delay");
            }
        } finally {
            timer.cancel();
        }
    }

    static void testOverflowingDelayThrowsIllegalArgumentException() {
        Timer timer = new Timer();
        try {
            TimerTask task = new TimerTask() {
                public void run() {
                }
            };
            boolean thrown = false;
            try {
                timer.schedule(task, Long.MAX_VALUE);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("IllegalArgumentException expected for overflowing execution time");
            }
        } finally {
            timer.cancel();
        }
    }

    static void testZeroDelayTaskIsExecuted() throws InterruptedException {
        Timer timer = new Timer();
        try {
            CountDownLatch latch = new CountDownLatch(1);
            timer.schedule(new TimerTask() {
                public void run() {
                    latch.countDown();
                }
            }, 0L);
            if (!latch.await(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("Task scheduled with zero delay was not executed");
            }
        } finally {
            timer.cancel();
        }
    }
}
