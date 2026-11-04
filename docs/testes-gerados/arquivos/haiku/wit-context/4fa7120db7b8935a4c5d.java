/*
 * @test
 * @summary Witup-generated checks for PriorityBlockingQueue spliterator forEachRemaining behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionLeavesSpliteratorUsable();
        testVisitsAllRemainingElements();
        testExhaustsSpliterator();
        testEmptyQueueVisitsNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        Spliterator<Integer> s = q.spliterator();
        try {
            s.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullActionLeavesSpliteratorUsable() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        Spliterator<Integer> s = q.spliterator();
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException expected) {
            // expected
        }
        int[] count = new int[1];
        boolean advanced = s.tryAdvance(e -> count[0]++);
        check(advanced, "spliterator should still have the element after null action");
        check(count[0] == 1, "element should be delivered once after null action");
    }

    static void testVisitsAllRemainingElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(3);
        q.add(8);
        q.add(1);
        q.add(4);
        Spliterator<Integer> s = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        s.forEachRemaining(seen::add);
        Collections.sort(seen);
        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 8);
        check(seen.equals(expected), "forEachRemaining should visit every element exactly once");
    }

    static void testExhaustsSpliterator() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(2);
        q.add(9);
        Spliterator<Integer> s = q.spliterator();
        int[] count = new int[1];
        s.forEachRemaining(e -> count[0]++);
        check(count[0] == 2, "first traversal should visit two elements");
        check(!s.tryAdvance(e -> { }), "spliterator should be exhausted after forEachRemaining");
        int[] again = new int[1];
        s.forEachRemaining(e -> again[0]++);
        check(again[0] == 0, "second forEachRemaining should visit nothing");
    }

    static void testEmptyQueueVisitsNothing() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Spliterator<Integer> s = q.spliterator();
        int[] count = new int[1];
        s.forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "empty queue should produce no actions");
    }
}
