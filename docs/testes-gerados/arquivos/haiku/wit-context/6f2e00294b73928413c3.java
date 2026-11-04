/*
 * @test
 * @summary Black-box checks for LinkedBlockingQueue.forEach null-action contract, FIFO traversal, empty queue and non-destructive iteration
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testNonNullActionVisitsElementsInFifoOrder();
        testEmptyQueueNeverInvokesAction();
        testForEachDoesNotRemoveElements();
        testConstructorFromCollectionPreservesForEachOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        try {
            q.forEach(null);
            throw new AssertionError("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after rejected forEach");
    }

    static void testNonNullActionVisitsElementsInFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> seen = new ArrayList<>();
        q.forEach(v -> seen.add(v));
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "expected FIFO order [1, 2, 3] but was " + seen);
    }

    static void testEmptyQueueNeverInvokesAction() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        int[] calls = new int[1];
        q.forEach(v -> calls[0]++);
        check(calls[0] == 0, "action must not run on empty queue");
    }

    static void testForEachDoesNotRemoveElements() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        List<String> seen = new ArrayList<>();
        q.forEach(v -> seen.add(v));
        check(q.size() == 3, "forEach must not remove elements, size was " + q.size());
        check("a".equals(q.poll()), "head must still be 'a' after forEach");
        check("b".equals(q.poll()), "second element must still be 'b' after forEach");
        check("c".equals(q.poll()), "third element must still be 'c' after forEach");
    }

    static void testConstructorFromCollectionPreservesForEachOrder() {
        LinkedBlockingQueue<String> q =
            new LinkedBlockingQueue<>(Arrays.asList("x", "y", "z"));
        List<String> seen = new ArrayList<>();
        q.forEach(v -> seen.add(v));
        check(seen.equals(Arrays.asList("x", "y", "z")),
              "expected order [x, y, z] but was " + seen);
    }
}
