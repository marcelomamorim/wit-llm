/*
 * @test
 * @summary Test removeIf method of ConcurrentLinkedQueue
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatchingElements();
        testRemoveIfAllMatch();
        testRemoveIfEmptyQueue();
    }

    static void testRemoveIfRemovesMatchingElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = queue.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert queue.size() == 2 : "Queue should have 2 elements after removal";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(3) : "Queue should contain 3";
        assert !queue.contains(2) : "Queue should not contain 2";
        assert !queue.contains(4) : "Queue should not contain 4";
    }

    static void testRemoveIfNoMatchingElements() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        Predicate<String> isNumber = s -> s.matches("\\d+");
        boolean result = queue.removeIf(isNumber);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert queue.size() == 2 : "Queue should still have 2 elements";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("b") : "Queue should contain 'b'";
    }

    static void testRemoveIfAllMatch() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(10);
        queue.add(20);
        Predicate<Integer> isGreaterThanZero = x -> x > 0;
        boolean result = queue.removeIf(isGreaterThanZero);
        assert result : "Expected removeIf to return true when all elements are removed";
        assert queue.isEmpty() : "Queue should be empty after removing all elements";
    }

    static void testRemoveIfEmptyQueue() {
        ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
        Predicate<Object> alwaysTrue = o -> true;
        boolean result = queue.removeIf(alwaysTrue);
        assert !result : "Expected removeIf to return false on empty queue";
        assert queue.isEmpty() : "Queue should remain empty";
    }
}
