/*
 * @test
 * @summary Verify LinkedTransferQueue iterator forEachRemaining null check, FIFO traversal and remaining-element behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTraversesInFifoOrder();
        testEmptyQueueDoesNotInvokeAction();
        testRemainingAfterNext();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        Iterator<String> it = queue.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testTraversesInFifoOrder() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        List<String> seen = new ArrayList<>();
        queue.iterator().forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "forEachRemaining must visit elements in FIFO order, got " + seen);
    }

    static void testEmptyQueueDoesNotInvokeAction() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        List<String> seen = new ArrayList<>();
        queue.iterator().forEachRemaining(seen::add);
        check(seen.isEmpty(), "forEachRemaining on empty queue must not invoke action");
    }

    static void testRemainingAfterNext() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Iterator<String> it = queue.iterator();
        check("a".equals(it.next()), "first next() must return a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c")),
              "forEachRemaining must visit only remaining elements, got " + seen);
    }
}
