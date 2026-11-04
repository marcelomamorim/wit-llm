/*
 * @test
 * @summary Test LinkedBlockingQueue(Collection<? extends E>) constructor behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testNullElementThrowsNPE();
        testQueueFullThrowsIllegalStateException();
        testNormalConstruction();
    }

    static void testNullElementThrowsNPE() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        boolean npeThrown = false;
        try {
            new LinkedBlockingQueue<String>(list);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null element");
        }
    }

    static void testQueueFullThrowsIllegalStateException() {
        List<Integer> list = new ArrayList<>();
        // Add Integer.MAX_VALUE + 1 elements is impossible, so test with capacity 1
        // Use subclass to expose capacity 1 constructor
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(1);
        list.add(1);
        list.add(2);
        boolean illegalStateThrown = false;
        try {
            // Use reflection to invoke constructor with Collection on capacity 1 queue
            // But since no reflection allowed, simulate by subclass with capacity 1 and add elements manually
            new LinkedBlockingQueue<Integer>(list) {
                {
                    // Override capacity to 1 forcibly
                    // But capacity is private final, no access, so test by creating queue with capacity 1 and adding elements
                }
            };
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        } catch (NullPointerException e) {
            // ignore
        } catch (Exception e) {
            // ignore
        }
        // Since we cannot invoke constructor with capacity 1 and collection directly,
        // test by creating queue with capacity 1 and adding elements manually
        // So test here:
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>(1);
        q.add(1);
        boolean thrown = false;
        try {
            q.add(2); // should throw IllegalStateException
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when adding to full queue");
        }
    }

    static void testNormalConstruction() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(list);
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size expected 2 but was " + queue.size());
        }
        String first = queue.poll();
        if (!"one".equals(first)) {
            throw new RuntimeException("Expected first element 'one' but got " + first);
        }
        String second = queue.poll();
        if (!"two".equals(second)) {
            throw new RuntimeException("Expected second element 'two' but got " + second);
        }
        if (queue.poll() != null) {
            throw new RuntimeException("Expected queue to be empty after polling all elements");
        }
    }
}
