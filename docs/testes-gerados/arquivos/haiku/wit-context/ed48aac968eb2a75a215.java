/*
 * @test
 * @summary Checks PriorityBlockingQueue iterator forEachRemaining null handling and observable traversal behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueInvokesNothing();
        testVisitsAllRemainingElements();
        testVisitsOnlyElementsAfterPartialNext();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testEmptyQueueInvokesNothing() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        int[] counter = new int[1];
        it.forEachRemaining(x -> counter[0]++);
        check(counter[0] == 0, "empty queue must not invoke action");
        check(!it.hasNext(), "iterator of empty queue must have no next");
    }

    static void testVisitsAllRemainingElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(3);
        q.add(8);
        q.add(1);
        q.add(9);
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        Collections.sort(seen);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        expected.add(5);
        expected.add(8);
        expected.add(9);
        check(seen.equals(expected), "forEachRemaining must visit every element exactly once");
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");

        int[] counter = new int[1];
        it.forEachRemaining(x -> counter[0]++);
        check(counter[0] == 0, "second forEachRemaining on exhausted iterator must not invoke action");
    }

    static void testVisitsOnlyElementsAfterPartialNext() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(3);
        q.add(8);
        q.add(1);
        q.add(9);
        Iterator<Integer> it = q.iterator();
        Integer first = it.next();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 4, "forEachRemaining must visit the four remaining elements");
        check(!seen.contains(first), "forEachRemaining must not revisit the element returned by next");
        int sum = first;
        for (Integer v : seen) {
            sum += v;
        }
        check(sum == 26, "visited elements plus first element must cover all queue elements");
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");
    }
}
