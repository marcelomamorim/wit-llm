/*
 * @test
 * @summary Verify LinkedTransferQueue.forEach visits elements in FIFO order, leaves the queue unchanged, and rejects a null action
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachFifoOrder();
        testForEachDoesNotRemoveElements();
        testForEachEmptyQueueInvokesNothing();
        testForEachAfterPollSkipsRemovedHead();
        testForEachOnCollectionConstructedQueue();
        testForEachNullActionThrowsNpe();
        System.out.println("PASS");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testForEachFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.put(1);
        q.add(2);
        q.offer(3);
        List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)), "forEach order was " + seen);
    }

    static void testForEachDoesNotRemoveElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(10);
        q.add(20);
        q.forEach(x -> { });
        check(q.size() == 2, "size changed after forEach: " + q.size());
        check(q.poll() == 10, "head changed after forEach");
        check(q.poll() == 20, "second element changed after forEach");
    }

    static void testForEachEmptyQueueInvokesNothing() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        int[] calls = new int[1];
        q.forEach(s -> calls[0]++);
        check(calls[0] == 0, "action invoked " + calls[0] + " times on empty queue");
    }

    static void testForEachAfterPollSkipsRemovedHead() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll() == 1, "poll did not return head");
        List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList(2, 3)), "forEach after poll was " + seen);
    }

    static void testForEachOnCollectionConstructedQueue() {
        LinkedTransferQueue<String> q =
                new LinkedTransferQueue<>(Arrays.asList("a", "b", "c"));
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")), "forEach on constructed queue was " + seen);
    }

    static void testForEachNullActionThrowsNpe() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        Consumer<Object> nullAction = null;
        boolean thrown = false;
        try {
            q.forEach(nullAction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) did not throw NullPointerException");
    }
}
