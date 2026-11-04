/*
 * @test
 * @summary ArrayBlockingQueue.removeAll removes matching elements, preserves order, and rejects null
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static List<Integer> contents(ArrayBlockingQueue<Integer> q) {
        return new ArrayList<>(q);
    }

    static ArrayBlockingQueue<Integer> queueOf(int capacity, Integer... values) {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(capacity);
        for (Integer v : values) {
            q.add(v);
        }
        return q;
    }

    public static void main(String[] args) {
        // 1. null argument must throw NullPointerException and leave queue unchanged
        ArrayBlockingQueue<Integer> q1 = queueOf(5, 1, 2);
        try {
            q1.removeAll(null);
            check(false, "removeAll(null) should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        check(contents(q1).equals(Arrays.asList(1, 2)), "queue changed after removeAll(null)");

        // 2. removes matching elements, returns true, preserves FIFO order of the rest
        ArrayBlockingQueue<Integer> q2 = queueOf(5, 1, 2, 3, 4, 5);
        boolean r2 = q2.removeAll(Arrays.asList(2, 4));
        check(r2, "removeAll should return true when elements were removed");
        check(contents(q2).equals(Arrays.asList(1, 3, 5)), "wrong remaining order: " + contents(q2));

        // 3. no matches: returns false and leaves queue unchanged
        ArrayBlockingQueue<Integer> q3 = queueOf(5, 1, 2, 3);
        boolean r3 = q3.removeAll(Arrays.asList(7, 8));
        check(!r3, "removeAll should return false when nothing matches");
        check(contents(q3).equals(Arrays.asList(1, 2, 3)), "queue changed when nothing matched");

        // 4. empty argument collection: returns false and leaves queue unchanged
        ArrayBlockingQueue<Integer> q4 = queueOf(5, 1, 2);
        boolean r4 = q4.removeAll(new ArrayList<Integer>());
        check(!r4, "removeAll with empty collection should return false");
        check(contents(q4).equals(Arrays.asList(1, 2)), "queue changed for empty argument");

        // 5. removing every element empties the queue
        ArrayBlockingQueue<Integer> q5 = queueOf(3, 1, 2, 3);
        boolean r5 = q5.removeAll(Arrays.asList(1, 2, 3));
        check(r5, "removeAll of all elements should return true");
        check(q5.isEmpty(), "queue should be empty after removing all elements");

        // 6. duplicates in queue are all removed
        ArrayBlockingQueue<Integer> q6 = queueOf(5, 1, 1, 2);
        boolean r6 = q6.removeAll(Arrays.asList(1));
        check(r6, "removeAll with duplicates should return true");
        check(contents(q6).equals(Arrays.asList(2)), "duplicates not fully removed: " + contents(q6));

        // 7. removal frees capacity
        ArrayBlockingQueue<Integer> q7 = queueOf(3, 1, 2, 3);
        q7.removeAll(Arrays.asList(1, 2));
        check(q7.remainingCapacity() == 2, "remainingCapacity should be 2 after removal");
        check(q7.offer(9), "offer should succeed after removal freed capacity");
        check(contents(q7).equals(Arrays.asList(3, 9)), "unexpected contents after offer: " + contents(q7));

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
