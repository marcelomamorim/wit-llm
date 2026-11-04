/*
 * @test
 * @summary Witup-derived behavioral tests for ArrayBlockingQueue.drainTo(Collection)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainAllToList();
        testDrainEmptyQueue();
        testDrainAppendsToNonEmptyCollection();
        testNullCollectionThrowsNPE();
        testDrainFromFairQueue();
    }

    static void testDrainAllToList() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 3, "expected 3 drained, got " + n);
        check(out.equals(Arrays.asList(1, 2, 3)), "unexpected content " + out);
        check(q.isEmpty(), "queue should be empty after drain");
        check(q.size() == 0, "size should be 0 after drain");
    }

    static void testDrainEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 0, "expected 0 drained from empty queue, got " + n);
        check(out.isEmpty(), "collection should remain empty");
    }

    static void testDrainAppendsToNonEmptyCollection() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(7);
        q.add(8);
        List<Integer> out = new ArrayList<>(Arrays.asList(0));
        int n = q.drainTo(out);
        check(n == 2, "expected 2 drained, got " + n);
        check(out.equals(Arrays.asList(0, 7, 8)), "unexpected content " + out);
        check(q.isEmpty(), "queue should be empty after drain");
    }

    static void testNullCollectionThrowsNPE() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(1);
        boolean thrown = false;
        try {
            q.drainTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "drainTo(null) should throw NullPointerException");
        check(q.size() == 1, "queue must not change when drainTo(null) fails");
    }

    static void testDrainFromFairQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3, true, Arrays.asList(4, 5));
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 2, "expected 2 drained from fair queue, got " + n);
        check(out.equals(Arrays.asList(4, 5)), "unexpected content " + out);
        check(q.isEmpty(), "fair queue should be empty after drain");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
