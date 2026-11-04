/*
 * @test
 * @summary Test LinkedBlockingDeque.takeLast() blocking and retrieval behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeLastBlocksUntilElementAvailable();
        testTakeLastReturnsElementIfPresent();
    }

    static void testTakeLastBlocksUntilElementAvailable() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        final AtomicBoolean took = new AtomicBoolean(false);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String result = deque.takeLast();
                    if ("test".equals(result)) {
                        took.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on empty deque
        Thread.sleep(100);

        // At this point, taker should be blocked and not have taken element yet
        if (took.get()) {
            throw new AssertionError("takeLast() returned element prematurely");
        }

        // Add element to unblock taker
        deque.addLast("test");

        // Wait for taker to finish
        taker.join(1000);

        if (!taker.isAlive() && took.get()) {
            // Success
        } else if (taker.isAlive()) {
            taker.interrupt();
            throw new AssertionError("takeLast() did not unblock after element was added");
        } else {
            throw new AssertionError("takeLast() did not return expected element");
        }
    }

    static void testTakeLastReturnsElementIfPresent() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("first");
        deque.addLast("second");

        // takeLast should return "second" immediately
        String result = deque.takeLast();
        if (!"second".equals(result)) {
            throw new AssertionError("Expected 'second' but got: " + result);
        }

        // Next takeLast should return "first"
        result = deque.takeLast();
        if (!"first".equals(result)) {
            throw new AssertionError("Expected 'first' but got: " + result);
        }
    }
}
