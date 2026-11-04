/*
 * @test
 * @summary Test removeIf method of PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Predicate;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatch();
        testRemoveIfNullPredicate();
    }

    static void testRemoveIfRemovesMatchingElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean removed = queue.removeIf(isEven);
        assert removed : "Expected removeIf to return true";
        assert queue.size() == 2 : "Queue size should be 2 after removal";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(3) : "Queue should contain 3";
        assert !queue.contains(2) : "Queue should not contain 2";
    }

    static void testRemoveIfNoMatch() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        boolean removed = queue.removeIf(startsWithZ);
        assert !removed : "Expected removeIf to return false when no elements match";
        assert queue.size() == 2 : "Queue size should remain 2";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("b") : "Queue should contain 'b'";
    }

    static void testRemoveIfNullPredicate() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        try {
            queue.removeIf(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        assert queue.size() == 1 : "Queue size should remain 1 after null predicate";
        assert queue.contains(1) : "Queue should still contain 1";
    }
}
