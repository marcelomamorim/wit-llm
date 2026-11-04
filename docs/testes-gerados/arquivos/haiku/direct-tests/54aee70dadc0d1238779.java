/*
 * @test
 * @summary Verifies observable ordering and emptiness behavior of PriorityBlockingQueue(int initialCapacity), which delegates to the comparator constructor with a null comparator (natural ordering).
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testSmallCapacityNaturalOrdering();
        testLargeCapacityNaturalOrdering();
        testEmptyQueueAfterConstruction();
    }

    static void testSmallCapacityNaturalOrdering() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(1);
        check(q.isEmpty(), "new queue should be empty");
        check(q.size() == 0, "new queue size should be 0");
        check(q.offer(5), "offer should return true");
        check(q.offer(1), "offer should return true");
        check(q.offer(3), "offer should return true");
        check(q.size() == 3, "size should be 3 after three offers");
        check(q.poll() == 1, "poll should return smallest element first");
        check(q.poll() == 3, "poll should return next smallest element");
        check(q.poll() == 5, "poll should return largest remaining element");
        check(q.poll() == null, "poll on empty queue should return null");
        check(q.isEmpty(), "queue should be empty after draining");
    }

    static void testLargeCapacityNaturalOrdering() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(100);
        for (int i = 199; i >= 0; i--) {
            check(q.offer(i), "offer should return true");
        }
        check(q.size() == 200, "size should be 200");
        for (int i = 0; i < 200; i++) {
            Integer v = q.poll();
            check(v != null && v == i, "expected " + i + " but got " + v);
        }
        check(q.isEmpty(), "queue should be empty after draining all elements");
    }

    static void testEmptyQueueAfterConstruction() {
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>(10);
        check(q.poll() == null, "poll on new queue should return null");
        check(q.size() == 0, "size of new queue should be 0");
        q.offer("b");
        q.offer("a");
        check("a".equals(q.poll()), "string natural ordering should yield 'a' first");
        check("b".equals(q.poll()), "string natural ordering should yield 'b' second");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
