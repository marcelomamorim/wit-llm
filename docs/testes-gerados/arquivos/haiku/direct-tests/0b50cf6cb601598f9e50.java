/*
 * @test
 * @summary ArrayBlockingQueue.forEach visits elements in FIFO order, handles wraparound, rejects null action and releases the lock after an exception
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testEmptyQueueNoCalls();
        testFifoOrder();
        testWraparoundOrder();
        testQueueUnchangedAfterForEach();
        testLockReleasedAfterException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(1);
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyQueueNoCalls() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        final int[] calls = {0};
        q.forEach(e -> calls[0]++);
        check(calls[0] == 0, "forEach on empty queue must not invoke action");
    }

    static void testFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(10);
        q.add(20);
        q.add(30);
        List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(java.util.Arrays.asList(10, 20, 30)),
              "expected FIFO order [10, 20, 30] but was " + seen);
    }

    static void testWraparoundOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll() == 1, "poll should return 1");
        check(q.poll() == 2, "poll should return 2");
        q.add(4);
        q.add(5);
        List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.equals(java.util.Arrays.asList(3, 4, 5)),
              "expected wraparound order [3, 4, 5] but was " + seen);
    }

    static void testQueueUnchangedAfterForEach() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(4);
        q.add("a");
        q.add("b");
        q.forEach(s -> { });
        check(q.size() == 2, "forEach must not remove elements");
        check("a".equals(q.peek()), "head must remain unchanged");
    }

    static void testLockReleasedAfterException() throws InterruptedException {
        final ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(1);
        boolean thrown = false;
        try {
            q.forEach(e -> { throw new IllegalStateException("boom"); });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "action exception must propagate");
        final boolean[] offered = {false};
        Thread t = new Thread(() -> offered[0] = q.offer(9));
        t.setDaemon(true);
        t.start();
        t.join(5000);
        check(!t.isAlive(), "lock must be released after exception in action");
        check(offered[0], "offer from another thread should succeed");
    }
}
