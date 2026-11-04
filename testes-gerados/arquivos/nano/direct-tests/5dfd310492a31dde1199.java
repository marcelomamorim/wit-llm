/*
 * @test
 * @summary Test retainAll method of ConcurrentLinkedQueue for correct behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;
import java.util.Arrays;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithExistingElements();
        testRetainAllWithNoMatchingElements();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithExistingElements() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList("a", "b", "c", "d"));
        Collection<String> c = Arrays.asList("a", "c");
        boolean changed = queue.retainAll(c);
        assert changed : "Expected queue to be changed";
        assert queue.size() == 2 : "Expected size 2";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("c") : "Queue should contain 'c'";
        assert !queue.contains("b") : "Queue should not contain 'b'";
        assert !queue.contains("d") : "Queue should not contain 'd'";
    }

    static void testRetainAllWithNoMatchingElements() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("x", "y");
        boolean changed = queue.retainAll(c);
        assert changed : "Expected queue to be changed";
        assert queue.isEmpty() : "Queue should be empty";
    }

    static void testRetainAllWithEmptyCollection() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList("a", "b"));
        Collection<String> c = Arrays.asList();
        boolean changed = queue.retainAll(c);
        assert changed : "Expected queue to be changed";
        assert queue.isEmpty() : "Queue should be empty";
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList("a", "b"));
        try {
            queue.retainAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
