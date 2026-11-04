/*
 * @test
 * @summary Verify PriorityBlockingQueue spliterator forEachRemaining visits remaining elements, rejects null, and exhausts the spliterator
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        testForEachRemainingVisitsAll();
        testEmptyQueueVisitsNothing();
        testNullActionRejected();
        testExhaustedAfterForEachRemaining();
        testForEachRemainingAfterPartialAdvance();
    }

    static void testForEachRemainingVisitsAll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(4);
        int[] vals = {5, 1, 4, 2, 3};
        for (int v : vals) {
            q.add(v);
        }
        Spliterator<Integer> sp = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.size() == 5, "expected 5 elements, got " + seen.size());
        Collections.sort(seen);
        check(seen.equals(Arrays.asList(1, 2, 3, 4, 5)), "unexpected elements: " + seen);
    }

    static void testEmptyQueueVisitsNothing() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        int[] counter = {0};
        q.spliterator().forEachRemaining(x -> counter[0]++);
        check(counter[0] == 0, "expected no visits on empty queue, got " + counter[0]);
    }

    static void testNullActionRejected() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.spliterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testExhaustedAfterForEachRemaining() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        q.add(3);
        Spliterator<Integer> sp = q.spliterator();
        int[] first = {0};
        sp.forEachRemaining(x -> first[0]++);
        check(first[0] == 2, "expected 2 visits, got " + first[0]);
        check(!sp.tryAdvance(x -> { }), "spliterator should be exhausted");
        int[] second = {0};
        sp.forEachRemaining(x -> second[0]++);
        check(second[0] == 0, "second forEachRemaining should visit nothing, got " + second[0]);
    }

    static void testForEachRemainingAfterPartialAdvance() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(10);
        q.add(20);
        q.add(30);
        Spliterator<Integer> sp = q.spliterator();
        boolean advanced = sp.tryAdvance(x -> { });
        check(advanced, "tryAdvance should consume one element");
        List<Integer> rest = new ArrayList<>();
        sp.forEachRemaining(rest::add);
        check(rest.size() == 2, "expected 2 remaining elements, got " + rest.size());
    }
}
