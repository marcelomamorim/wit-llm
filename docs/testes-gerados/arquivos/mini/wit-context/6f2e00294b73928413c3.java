/*
 * @test
 * @summary Test LinkedBlockingQueue.forEach method behavior and exceptions
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachUnsupportedOperation();
    }

    static void testForEachNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean npeThrown = false;
        try {
            queue.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachUnsupportedOperation() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");

        // The WIT expath suggests UnsupportedOperationException is thrown when action != null
        // but the current source code does not show this explicitly.
        // We test for observable behavior: forEach should invoke the action on all elements.

        final AtomicInteger count = new AtomicInteger(0);
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        };

        try {
            queue.forEach(action);
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("forEach threw UnsupportedOperationException unexpectedly");
        } catch (Throwable t) {
            throw new AssertionError("forEach threw unexpected exception: " + t);
        }

        if (count.get() != 2) {
            throw new AssertionError("forEach did not process all elements");
        }
    }
}
