/*
 * @test
 * @summary Verify that Timer(String name, boolean isDaemon) names and configures the timer thread, observed through a scheduled task
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TimerWitupTest {

    public static void main(String[] args) throws Exception {
        check("witup-timer-daemon", true);
        check("witup-timer-user", false);
    }

    static void check(String name, boolean daemon) throws InterruptedException {
        Timer timer = new Timer(name, daemon);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> seenName = new AtomicReference<>();
        AtomicReference<Boolean> seenDaemon = new AtomicReference<>();
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Thread t = Thread.currentThread();
                    seenName.set(t.getName());
                    seenDaemon.set(t.isDaemon());
                    latch.countDown();
                }
            }, 0L);
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Scheduled task did not run in time");
            }
            if (!name.equals(seenName.get())) {
                throw new RuntimeException("Unexpected thread name: " + seenName.get());
            }
            if (seenDaemon.get().booleanValue() != daemon) {
                throw new RuntimeException("Unexpected daemon flag: " + seenDaemon.get());
            }
        } finally {
            timer.cancel();
        }
    }
}
