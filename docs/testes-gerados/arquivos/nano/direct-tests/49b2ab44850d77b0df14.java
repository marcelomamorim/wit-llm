/*
 * @test    @summary Test removeIf method of LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Predicate;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatch();
        testRemoveIfAllMatch();
        testRemoveIfEmptyQueue();
    }

    static void testRemoveIfRemovesMatchingElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Predicate<Integer> isEven = (Integer e) -> e % 2 == 0;
        boolean result = queue.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert queue.size() == 2 : "Queue should have 2 elements after removal";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(3) : "Queue should contain 3";
    }

    static void testRemoveIfNoMatch() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        Predicate<String> startsWithZ = (String s) -> s.startsWith("z");
        boolean result = queue.removeIf(startsWithZ);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert queue.size() == 2 : "Queue should still have 2 elements";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("b") : "Queue should contain 'b'";
    }

    static void testRemoveIfAllMatch() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(4);
        queue.add(8);
        Predicate<Integer> isEven = (Integer e) -> e % 2 == 0;
        boolean result = queue.removeIf(isEven);
        assert result : "Expected removeIf to return true when all elements are removed";
        assert queue.isEmpty() : "Queue should be empty after removing all elements";
    }

    static void testRemoveIfEmptyQueue() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        Predicate<Integer> anyPredicate = (Integer e) -> true;
        boolean result = queue.removeIf(anyPredicate);
        assert !result : "Expected removeIf to return false on empty queue";
        assert queue.isEmpty() : "Queue should remain empty";
    }
}
