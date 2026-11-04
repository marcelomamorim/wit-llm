/*
 * @test
 * @summary Verifies LinkedBlockingQueue.forEach rejects a null action, visits elements in FIFO order without removing them, and respects a bounded queue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueInvokesNothing();
        testFifoOrderAndQueueUnchanged();
        testCollectionConstructorOrder();
        testBoundedQueue();
    }

    static void testNullActionThrowsNPE() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        try {
            q.forEach(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue modified after failed forEach");
        check("a".equals(q.peek()), "head changed after failed forEach");
    }

    static void testEmptyQueueInvokesNothing() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        final int[] calls = {0};
        q.forEach(s -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty queue");
        check(q.isEmpty(), "empty queue changed after forEach");
    }

    static void testFifoOrderAndQueueUnchanged() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")), "wrong order: " + seen);
        check(q.size() == 3, "size changed after forEach");
        check("a".equals(q.poll()), "poll after forEach did not return head");
        check("b".equals(q.poll()), "second poll mismatch");
        check("c".equals(q.poll()), "third poll mismatch");
        check(q.poll() == null, "queue should be empty");
    }

    static void testCollectionConstructorOrder() {
        LinkedBlockingQueue<Integer> q =
                new LinkedBlockingQueue<>(Arrays.asList(10, 20, 30));
        List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(Arrays.asList(10, 20, 30)), "wrong order from collection constructor: " + seen);
    }

    static void testBoundedQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        check(q.offer(1), "offer 1 failed");
        check(q.offer(2), "offer 2 failed");
        check(!q.offer(3), "offer beyond capacity should fail");
        final int[] sum = {0};
        q.forEach(x -> sum[0] += x);
        check(sum[0] == 3, "unexpected sum " + sum[0]);
        check(q.size() == 2, "size changed after forEach on bounded queue");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
