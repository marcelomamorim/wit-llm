/*
 * @test    @summary Test removeIf method of ArrayBlockingQueue
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatch();
        testRemoveIfNullPredicate();
    }

    static void testRemoveIfRemovesMatchingElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        Predicate<Integer> isEven = (Integer n) -> n % 2 == 0;
        boolean result = queue.removeIf(isEven);
        assert result : "Expected removeIf to return true";
        assert queue.size() == 3 : "Expected size 3 after removal";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(3) : "Queue should contain 3";
        assert queue.contains(5) : "Queue should contain 5";
        assert !queue.contains(2) : "Queue should not contain 2";
        assert !queue.contains(4) : "Queue should not contain 4";
    }

    static void testRemoveIfNoMatch() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Predicate<String> startsWithZ = (String s) -> s.startsWith("z");
        boolean result = queue.removeIf(startsWithZ);
        assert !result : "Expected removeIf to return false when no elements match";
        assert queue.size() == 3 : "Size should remain 3";
        assert queue.contains("a") : "Queue should contain 'a'";
        assert queue.contains("b") : "Queue should contain 'b'";
        assert queue.contains("c") : "Queue should contain 'c'";
    }

    static void testRemoveIfNullPredicate() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(10);
        queue.add(20);
        try {
            queue.removeIf(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        assert queue.size() == 2 : "Size should remain 2 after null predicate";
        assert queue.contains(10) : "Queue should contain 10";
        assert queue.contains(20) : "Queue should contain 20";
    }
}
