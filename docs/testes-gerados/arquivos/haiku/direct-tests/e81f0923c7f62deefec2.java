/*
 * @test
 * @summary Checks ConcurrentLinkedQueue.forEach ordering, empty queue behavior, and null action handling
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {
    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        testEmptyQueueNoActionCalls();
        testFifoOrder();
        testCollectionConstructorOrder();
        testNullActionThrowsNullPointerException();
        testQueueUnchangedAfterForEach();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
        System.out.println("All checks passed");
    }

    private static void testEmptyQueueNoActionCalls() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        final int[] calls = {0};
        q.forEach(e -> calls[0]++);
        check(calls[0] == 0, "forEach on empty queue must not invoke action");
    }

    private static void testFifoOrder() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        q.add(1);
        q.offer(2);
        q.add(3);
        List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "forEach must visit elements in FIFO order, got " + seen);
    }

    private static void testCollectionConstructorOrder() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("a", "b", "c"));
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "forEach after collection constructor must preserve order, got " + seen);
    }

    private static void testNullActionThrowsNullPointerException() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("x");
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    private static void testQueueUnchangedAfterForEach() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("p", "q"));
        q.forEach(e -> { });
        check(q.size() == 2, "forEach must not remove elements, size=" + q.size());
        check("p".equals(q.peek()), "head must remain unchanged after forEach");
    }
}
