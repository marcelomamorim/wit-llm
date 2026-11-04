/*
 * @test
 * @summary Test LinkedBlockingDeque.putFirst(E) for basic functionality and null handling
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPutFirstNull();
        testPutFirstBasic();
        testPutFirstBlocksWhenFull();
    }

    static void testPutFirstNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.putFirst(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected InterruptedException", e);
        }
    }

    static void testPutFirstBasic() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putFirst("a");
        String first = deque.takeFirst();
        if (!"a".equals(first)) {
            throw new RuntimeException("Element putFirst and takeFirst mismatch: expected 'a' but got '" + first + "'");
        }
    }

    static void testPutFirstBlocksWhenFull() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.putFirst("x"); // fill to capacity

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    deque.putFirst("y"); // should block until space available
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();

        // Give thread time to block
        Thread.sleep(100);

        if (!t.isAlive()) {
            throw new RuntimeException("putFirst did not block when deque was full");
        }

        // Remove element to free space
        String removed = deque.takeLast();
        if (!"x".equals(removed)) {
            throw new RuntimeException("Unexpected element removed: expected 'x' but got '" + removed + "'");
        }

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new RuntimeException("putFirst thread did not finish after space was freed");
        }

        // Now deque should contain "y" at first
        String first = deque.takeFirst();
        if (!"y".equals(first)) {
            throw new RuntimeException("Element putFirst after blocking mismatch: expected 'y' but got '" + first + "'");
        }
    }
}
