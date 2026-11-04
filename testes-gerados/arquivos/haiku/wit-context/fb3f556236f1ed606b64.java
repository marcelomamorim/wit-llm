/*
 * @test
 * @summary Verify LinkedBlockingQueue spliterator forEachRemaining null-action check and observable traversal behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testNullActionDoesNotExhaustSpliterator();
        testEmptyQueueVisitsNothing();
        testVisitsAllElementsInOrder();
        testSecondCallVisitsNothing();
        testRemainingAfterTryAdvance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        Spliterator<Integer> sp = queue.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testNullActionDoesNotExhaustSpliterator() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Spliterator<Integer> sp = queue.spliterator();
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException expected) {
            // expected
        }
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(Arrays.asList(1, 2).equals(seen),
              "spliterator should still traverse elements after NPE, got " + seen);
    }

    static void testEmptyQueueVisitsNothing() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        Spliterator<Integer> sp = queue.spliterator();
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty queue should visit no elements, got " + seen);
    }

    static void testVisitsAllElementsInOrder() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Spliterator<Integer> sp = queue.spliterator();
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(Arrays.asList(1, 2, 3).equals(seen),
              "expected [1, 2, 3] but got " + seen);
    }

    static void testSecondCallVisitsNothing() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        Spliterator<Integer> sp = queue.spliterator();
        List<Integer> first = new ArrayList<>();
        sp.forEachRemaining(first::add);
        List<Integer> second = new ArrayList<>();
        sp.forEachRemaining(second::add);
        check(Arrays.asList(10, 20).equals(first),
              "first call expected [10, 20] but got " + first);
        check(second.isEmpty(),
              "second call should visit nothing but got " + second);
    }

    static void testRemainingAfterTryAdvance() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        Spliterator<Integer> sp = queue.spliterator();
        List<Integer> head = new ArrayList<>();
        boolean advanced = sp.tryAdvance(head::add);
        check(advanced, "tryAdvance should consume the first element");
        check(Arrays.asList(1).equals(head), "tryAdvance expected [1] but got " + head);
        List<Integer> rest = new ArrayList<>();
        sp.forEachRemaining(rest::add);
        check(Arrays.asList(2, 3, 4).equals(rest),
              "forEachRemaining after tryAdvance expected [2, 3, 4] but got " + rest);
    }
}
