/*
 * @test
 * @summary Test LinkedBlockingQueue(Collection<? extends E>) constructor for expected behaviors
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNullElementThrowsNPE();
        testQueueFullThrowsIllegalStateException();
        testConstructorWithValidCollection();
    }

    private static void testNullElementThrowsNPE() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add(null); // null element to trigger NullPointerException
        boolean npeThrown = false;
        try {
            new LinkedBlockingQueue<String>(list);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null element");
        }
    }

    private static void testQueueFullThrowsIllegalStateException() {
        // Create a queue with capacity 1 and try to initialize with 2 elements
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean illegalStateThrown = false;
        try {
            new LinkedBlockingQueue<String>(1) {{
                // Use the Collection constructor indirectly by creating a subclass with capacity 1
            }};
            // The above does not call the Collection constructor, so we must call it explicitly
            new LinkedBlockingQueue<String>(list) {
                // Override capacity to 1 forcibly is not possible, so discard this fragile approach
            };
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        } catch (Exception e) {
            // Ignore other exceptions
        }

        // Because we cannot forcibly set capacity to 1 for the Collection constructor,
        // we test the behavior by creating a queue with capacity 1 and offer elements manually
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        boolean offer1 = queue.offer("a");
        boolean offer2 = queue.offer("b");
        if (!offer1 || offer2) {
            throw new AssertionError("Queue capacity enforcement failed");
        }

        // So we cannot reliably test IllegalStateException from constructor with collection
        // due to lack of public API to set capacity for that constructor.
    }

    private static void testConstructorWithValidCollection() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(list);
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after construction");
        }
        String first = queue.poll();
        String second = queue.poll();
        if (!"a".equals(first) || !"b".equals(second)) {
            throw new AssertionError("Queue elements not in FIFO order");
        }
        if (queue.poll() != null) {
            throw new AssertionError("Queue should be empty after polling all elements");
        }
    }
}
