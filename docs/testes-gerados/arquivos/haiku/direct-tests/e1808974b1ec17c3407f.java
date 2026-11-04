/*
 * @test
 * @summary Observable behavior of LinkedBlockingQueue.drainTo(Collection)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyQueueReturnsZero();
        testDrainAllInFifoOrder();
        testDrainAppendsToExistingCollection();
        testSecondDrainReturnsZero();
        testBoundedQueueDrainsAllElements();
        testQueueReusableAfterDrain();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testEmptyQueueReturnsZero() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        List<Integer> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 0, "expected 0 drained from empty queue, got " + n);
        check(sink.isEmpty(), "sink should remain empty");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testDrainAllInFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 3, "expected 3 drained, got " + n);
        check(sink.size() == 3, "sink size expected 3, got " + sink.size());
        check(sink.get(0) == 1 && sink.get(1) == 2 && sink.get(2) == 3,
              "elements not in FIFO order: " + sink);
        check(q.isEmpty(), "queue should be empty after drainTo");
    }

    static void testDrainAppendsToExistingCollection() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("b");
        q.add("c");
        List<String> sink = new ArrayList<>();
        sink.add("a");
        int n = q.drainTo(sink);
        check(n == 2, "expected 2 drained, got " + n);
        check(sink.size() == 3, "sink size expected 3, got " + sink.size());
        check(sink.get(0).equals("a") && sink.get(1).equals("b") && sink.get(2).equals("c"),
              "unexpected sink contents: " + sink);
    }

    static void testSecondDrainReturnsZero() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(10);
        q.add(20);
        List<Integer> first = new ArrayList<>();
        check(q.drainTo(first) == 2, "first drain should return 2");
        List<Integer> second = new ArrayList<>();
        int n = q.drainTo(second);
        check(n == 0, "second drain should return 0, got " + n);
        check(second.isEmpty(), "second sink should be empty");
    }

    static void testBoundedQueueDrainsAllElements() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        q.add(7);
        q.add(8);
        check(q.remainingCapacity() == 0, "queue should be full before drain");
        List<Integer> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 2, "expected 2 drained from bounded queue, got " + n);
        check(sink.get(0) == 7 && sink.get(1) == 8, "unexpected order: " + sink);
        check(q.remainingCapacity() == 2, "remaining capacity should be restored to 2");
    }

    static void testQueueReusableAfterDrain() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        List<Integer> first = new ArrayList<>();
        q.drainTo(first);
        q.add(2);
        q.add(3);
        List<Integer> second = new ArrayList<>();
        int n = q.drainTo(second);
        check(n == 2, "expected 2 drained after reuse, got " + n);
        check(second.get(0) == 2 && second.get(1) == 3, "unexpected reuse order: " + second);
    }
}
