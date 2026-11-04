/*
 * @test
 * @summary Test LinkedBlockingDeque.takeFirst() blocking and retrieval behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeFirstBlocksUntilElementAvailable();
        testTakeFirstReturnsElementImmediatelyIfPresent();
    }

    static void testTakeFirstBlocksUntilElementAvailable() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        final AtomicBoolean tookElement = new AtomicBoolean(false);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String result = deque.takeFirst();
                    if ("test".equals(result)) {
                        tookElement.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on empty deque
        Thread.sleep(100);

        // At this point, taker should be blocked waiting for element
        if (tookElement.get()) {
            throw new RuntimeException("takeFirst() returned element prematurely");
        }

        // Add element to unblock taker
        deque.putFirst("test");

        // Wait for taker to finish
        taker.join(1000);

        if (!taker.isAlive() && tookElement.get()) {
            // Success
        } else {
            throw new RuntimeException("takeFirst() did not return the expected element after insertion");
        }
    }

    static void testTakeFirstReturnsElementImmediatelyIfPresent() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putFirst("immediate");

        String result = deque.takeFirst();

        if (!"immediate".equals(result)) {
            throw new RuntimeException("takeFirst() did not return the expected element immediately");
        }
    }
}
