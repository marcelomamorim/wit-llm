/*
 * @test
 * @summary Verifies LinkedBlockingQueue.toArray() returns elements in FIFO order and a detached snapshot
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testFifoOrder();
        testSnapshotIsDetached();
        testAfterPoll();
        testBoundedQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueue() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        Object[] a = q.toArray();
        check(a != null, "toArray must not return null");
        check(a.length == 0, "empty queue must yield empty array");
    }

    static void testFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        Object[] a = q.toArray();
        check(a.length == 5, "length must equal size, got " + a.length);
        for (int i = 0; i < 5; i++) {
            check(a[i].equals(i + 1), "wrong element order at index " + i);
        }
    }

    static void testSnapshotIsDetached() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("x");
        q.add("y");
        Object[] a = q.toArray();
        a[0] = "changed";
        check(q.size() == 2, "size must be unaffected by array mutation");
        Object[] b = q.toArray();
        check("x".equals(b[0]), "queue must not be affected by mutating returned array");
        check(a != b, "each call must return a new array");
    }

    static void testAfterPoll() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(10);
        q.add(20);
        q.add(30);
        q.poll();
        Object[] a = q.toArray();
        check(a.length == 2, "length after poll must be 2, got " + a.length);
        check(a[0].equals(20), "head after poll must be 20");
        check(a[1].equals(30), "second element after poll must be 30");
        q.clear();
        check(q.toArray().length == 0, "cleared queue must yield empty array");
    }

    static void testBoundedQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        check(q.offer(1), "first offer must succeed");
        check(q.offer(2), "second offer must succeed");
        check(!q.offer(3), "offer beyond capacity must fail");
        Object[] a = q.toArray();
        check(a.length == 2, "bounded queue toArray length must be 2");
        check(a[0].equals(1) && a[1].equals(2), "bounded queue order mismatch");
    }
}
