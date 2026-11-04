/*
 * @test
 * @summary Test LinkedBlockingDeque.put(E) for null input and normal insertion
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test 1: put(null) should throw NullPointerException
        boolean npeThrown = false;
        try {
            deque.put(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when putting null");
        }

        // Test 2: put(non-null) should insert element and increase size
        String element = "testElement";
        deque.put(element);
        if (deque.size() != 1) {
            throw new AssertionError("Expected size 1 after put");
        }
        String polled = deque.poll();
        if (!element.equals(polled)) {
            throw new AssertionError("Expected element retrieved to be the one put");
        }

        // Test 3: put on a deque with capacity 1 blocks when full (cannot test blocking easily here)
        // So test that put does not throw for non-null element
        LinkedBlockingDeque<String> capacityDeque = new LinkedBlockingDeque<>(1);
        capacityDeque.put("one");
        // The next put would block if called in a thread; here we test no exception thrown immediately
        Thread t = new Thread(() -> {
            try {
                capacityDeque.put("two");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.setDaemon(true);
        t.start();
        // Give thread some time to block
        Thread.sleep(100);
        if (!t.isAlive()) {
            throw new AssertionError("Expected thread to be blocked on put when deque is full");
        }
        t.interrupt(); // cleanup
    }
}
