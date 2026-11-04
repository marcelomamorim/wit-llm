/*
 * @test
 * @summary Witup-derived behavioural checks for PriorityBlockingQueue.forEach
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionLeavesQueueUnchanged();
        testEmptyQueueDoesNotInvokeAction();
        testVisitsAllElements();
        testVisitsAllElementsAfterPoll();
    }

    static void testNullActionThrowsNpe() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        try {
            q.forEach((Consumer<Integer>) null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullActionLeavesQueueUnchanged() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(4);
        q.add(2);
        try {
            q.forEach((Consumer<Integer>) null);
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 2, "size changed after failed forEach");
        check(q.contains(4) && q.contains(2), "elements changed after failed forEach");
    }

    static void testEmptyQueueDoesNotInvokeAction() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        List<Integer> seen = new ArrayList<>();
        q.forEach(x -> seen.add(x));
        check(seen.isEmpty(), "action invoked on empty queue");
    }

    static void testVisitsAllElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        q.add(1);
        List<Integer> seen = new ArrayList<>();
        q.forEach(x -> seen.add(x));
        assertSortedEquals(seen, new Integer[] {1, 1, 3, 5});
        check(q.size() == 4, "size changed by forEach");
    }

    static void testVisitsAllElementsAfterPoll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        q.add(2);
        q.add(9);
        check(q.poll().equals(2), "unexpected head");
        List<Integer> seen = new ArrayList<>();
        q.forEach(x -> seen.add(x));
        assertSortedEquals(seen, new Integer[] {7, 9});
    }

    static void assertSortedEquals(List<Integer> actual, Integer[] expected) {
        Integer[] arr = actual.toArray(new Integer[0]);
        Arrays.sort(arr);
        if (!Arrays.equals(arr, expected)) {
            throw new RuntimeException("unexpected elements: " + Arrays.toString(arr));
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
