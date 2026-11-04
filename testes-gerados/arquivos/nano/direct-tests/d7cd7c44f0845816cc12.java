/*
 * @test
 * @summary Test removeIf method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatch();
        testRemoveIfAllMatch();
        testRemoveIfEmptyQueue();
    }

    static void testRemoveIfRemovesMatchingElements() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        boolean result = queue.removeIf(e -> e % 2 == 0); // remove even numbers
        assert result : "Expected removeIf to return true";
        assert queue.size() == 2 : "Expected size 2 after removal";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(3) : "Queue should contain 3";
        assert !queue.contains(2) : "Queue should not contain 2";
        assert !queue.contains(4) : "Queue should not contain 4";
    }

    static void testRemoveIfNoMatch() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        boolean result = queue.removeIf(s -> s.length() > 10); // no match
        assert !result : "Expected removeIf to return false";
        assert queue.size() == 2 : "Size should remain 2";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("b") : "Queue should contain 'b'";
    }

    static void testRemoveIfAllMatch() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        boolean result = queue.removeIf(e -> e >= 10); // all match
        assert result : "Expected removeIf to return true";
        assert queue.isEmpty() : "Queue should be empty";
    }

    static void testRemoveIfEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        boolean result = queue.removeIf(e -> true); // empty queue
        assert !result : "Expected removeIf to return false on empty queue";
        assert queue.isEmpty() : "Queue should remain empty";
    }
}
