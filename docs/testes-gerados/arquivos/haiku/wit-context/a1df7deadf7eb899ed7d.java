/*
 * @test
 * @summary Witup-derived checks for ConcurrentLinkedQueue spliterator forEachRemaining null handling and traversal
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueVisitsNothing();
        testVisitsRemainingElementsInOrder();
        testSpliteratorExhaustedAfterForEachRemaining();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2));
        Spliterator<Integer> spliterator = queue.spliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyQueueVisitsNothing() {
        Spliterator<Integer> spliterator = new ConcurrentLinkedQueue<Integer>().spliterator();
        List<Integer> seen = new ArrayList<>();
        spliterator.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty queue must not visit any element");
    }

    static void testVisitsRemainingElementsInOrder() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3));
        Spliterator<Integer> spliterator = queue.spliterator();
        List<Integer> seen = new ArrayList<>();
        spliterator.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)), "expected elements [1, 2, 3] in FIFO order but got " + seen);
    }

    static void testSpliteratorExhaustedAfterForEachRemaining() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>(Arrays.asList(4, 5));
        Spliterator<Integer> spliterator = queue.spliterator();
        List<Integer> first = new ArrayList<>();
        spliterator.forEachRemaining(first::add);
        check(first.equals(Arrays.asList(4, 5)), "first traversal mismatch: " + first);
        List<Integer> second = new ArrayList<>();
        spliterator.forEachRemaining(second::add);
        check(second.isEmpty(), "second forEachRemaining must visit nothing but got " + second);
        check(!spliterator.tryAdvance(x -> { }), "tryAdvance must return false after exhaustion");
    }
}
