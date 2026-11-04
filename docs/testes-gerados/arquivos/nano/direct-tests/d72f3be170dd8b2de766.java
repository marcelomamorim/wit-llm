/*
 * @test    @summary Test removeAll method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithExistingElements();
        testRemoveAllWithNonExistingElements();
        testRemoveAllWithNullCollection();
    }

    static void testRemoveAllWithExistingElements() {
        Collection<String> initial = Arrays.asList("a", "b", "c", "d");
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(initial);
        Collection<String> toRemove = Arrays.asList("b", "c");
        boolean changed = queue.removeAll(toRemove);
        assert changed : "Expected removeAll to return true when elements are removed";
        assert queue.size() == 2 : "Expected size 2 after removal";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("d") : "Queue should contain 'd'";
        assert !queue.contains("b") : "Queue should not contain 'b'";
        assert !queue.contains("c") : "Queue should not contain 'c'";
    }

    static void testRemoveAllWithNonExistingElements() {
        Collection<String> initial = Arrays.asList("x", "y", "z");
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(initial);
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = queue.removeAll(toRemove);
        assert !changed : "Expected removeAll to return false when no elements are removed";
        assert queue.size() == 3 : "Size should remain 3";
        assert queue.contains("x") : "Queue should contain 'x'";
        assert queue.contains("y") : "Queue should contain 'y'";
        assert queue.contains("z") : "Queue should contain 'z'";
    }

    static void testRemoveAllWithNullCollection() {
        Collection<String> initial = Arrays.asList("a", "b");
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(initial);
        try {
            queue.removeAll(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
