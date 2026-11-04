/*
 * @test
 * @summary Verifies that Timer(String name) names its background thread and runs scheduled tasks on it as a non-daemon thread
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        final String name = "witup-timer-name";
        Timer timer = new Timer(name);
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            final String[] seen = new String[1];
            timer.schedule(new TimerTask() {
                public void run() {
                    seen[0] = Thread.currentThread().getName();
                    latch.countDown();
                }
            }, 0L);
            if (!latch.await(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("scheduled task was not executed");
            }
            if (!name.equals(seen[0])) {
                throw new RuntimeException("unexpected thread name: " + seen[0]);
            }
            boolean found = false;
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (name.equals(t.getName())) {
                    found = true;
                    if (t.isDaemon()) {
                        throw new RuntimeException("timer thread should not be daemon");
                    }
                }
            }
            if (!found) {
                throw new RuntimeException("timer thread named '" + name + "' not found");
            }
        } finally {
            timer.cancel();
        }
    }
}
