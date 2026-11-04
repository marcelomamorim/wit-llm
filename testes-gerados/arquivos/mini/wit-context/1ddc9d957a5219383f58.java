/*
 * @test
 * @summary Test LinkedBlockingDeque.takeFirst() blocking and unlocking behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeFirstBlocksAndReturnsElement();
        testTakeFirstInterruptibility();
    }

    static void testTakeFirstBlocksAndReturnsElement() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        final AtomicBoolean took = new AtomicBoolean(false);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String item = deque.takeFirst();
                    if ("test".equals(item)) {
                        took.set(true);
                    }
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on empty deque
        Thread.sleep(100);

        // At this point, taker thread should be blocked waiting for element
        if (took.get()) {
            throw new RuntimeException("takeFirst returned prematurely");
        }

        // Add element to unblock taker
        deque.putFirst("test");

        // Wait for taker to finish
        taker.join(1000);

        if (!took.get()) {
            throw new RuntimeException("takeFirst did not return the expected element");
        }
    }

    static void testTakeFirstInterruptibility() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    deque.takeFirst();
                    throw new RuntimeException("takeFirst should have been interrupted");
                } catch (InterruptedException e) {
                    // Expected interruption
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting
        Thread.sleep(100);

        // Interrupt the taker thread
        taker.interrupt();

        // Wait for taker to finish
        taker.join(1000);

        if (taker.isAlive()) {
            throw new RuntimeException("takeFirst did not respond to interrupt");
        }
    }
}
