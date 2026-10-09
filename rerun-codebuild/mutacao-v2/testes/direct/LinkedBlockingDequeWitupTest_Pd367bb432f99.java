/*
 * @test
 * @summary Test LinkedBlockingDeque.putLast(E) for normal and exceptional behavior
 * @run main LinkedBlockingDequeWitupTest_Pd367bb432f99
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_Pd367bb432f99 {
    public static void main(String[] args) throws Exception {
        testPutLastNull();
        testPutLastNormal();
        testPutLastBlocksWhenFull();
    }

    static void testPutLastNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.putLast(null);
            throw new Error("Expected NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new Error("Unexpected InterruptedException", e);
        }
    }

    static void testPutLastNormal() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("test");
        String polled = deque.pollLast();
        if (!"test".equals(polled)) {
            throw new Error("Element putLast not found or incorrect: " + polled);
        }
    }

    static void testPutLastBlocksWhenFull() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.putLast("first");

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    deque.putLast("second"); // should block until space available
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();

        // Give thread time to block
        Thread.sleep(200);

        if (!t.isAlive()) {
            throw new Error("Thread did not block on putLast when deque is full");
        }

        // Remove one element to free space
        String removed = deque.takeFirst();
        if (!"first".equals(removed)) {
            throw new Error("Unexpected element removed: " + removed);
        }

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new Error("Thread did not finish after space was freed");
        }

        // Check that second element is present
        String polled = deque.pollLast();
        if (!"second".equals(polled)) {
            throw new Error("Element putLast by blocked thread not found or incorrect: " + polled);
        }
    }
}
