/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingQueue's tryAdvance method.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithNonExhaustedQueue();
        testTryAdvanceWithExhaustedQueue();
    }

    static void testTryAdvanceWithNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testTryAdvanceWithNonExhaustedQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("element1");
        Consumer<Object> consumer = (obj) -> {
            if ("element1".equals(obj)) {
                System.out.println("Consumer received: " + obj);
            } else {
                System.out.println("FAIL: Consumer received unexpected element: " + obj);
            }
        };
        boolean result = queue.tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance returned true on non-exhausted queue");
        } else {
            System.out.println("FAIL: tryAdvance returned false on non-exhausted queue");
        }
    }

    static void testTryAdvanceWithExhaustedQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // Empty queue, should return false
        Consumer<Object> consumer = (obj) -> {
            System.out.println("FAIL: Consumer should not be called on empty queue");
        };
        boolean result = queue.tryAdvance(consumer);
        if (!result) {
            System.out.println("PASS: tryAdvance returned false on empty queue");
        } else {
            System.out.println("FAIL: tryAdvance returned true on empty queue");
        }
    }
}
