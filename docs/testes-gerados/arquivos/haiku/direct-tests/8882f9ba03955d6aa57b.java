/*
 * @test
 * @summary Tests PriorityBlockingQueue.forEach: null action rejection, empty queue, visiting all elements, and queue state preservation
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueInvokesNoAction();
        testVisitsAllElements();
        testQueueUnchangedAfterForEach();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyQueueInvokesNoAction() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        int[] calls = new int[1];
        q.forEach(x -> calls[0]++);
        check(calls[0] == 0, "forEach on empty queue must not invoke action");
    }

    static void testVisitsAllElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(4);
        q.add(2);
        q.add(3);
        List<Integer> seen = new ArrayList<>();
        q.forEach(x -> seen.add(x));
        check(seen.size() == 5, "forEach must visit exactly size() elements, got " + seen.size());
        Integer[] arr = seen.toArray(new Integer[0]);
        Arrays.sort(arr);
        check(Arrays.equals(arr, new Integer[]{1, 2, 3, 4, 5}),
              "forEach must visit every element once: " + Arrays.toString(arr));
    }

    static void testQueueUnchangedAfterForEach() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        q.forEach(x -> { });
        check(q.size() == 3, "forEach must not change size");
        check(q.poll() == 1, "first poll after forEach must be 1");
        check(q.poll() == 2, "second poll after forEach must be 2");
        check(q.poll() == 3, "third poll after forEach must be 3");
        check(q.poll() == null, "queue must be empty after polling all elements");
    }
}
