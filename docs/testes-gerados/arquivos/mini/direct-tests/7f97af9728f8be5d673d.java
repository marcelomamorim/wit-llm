/*
 * @test
 * @summary Test PriorityBlockingQueue.offer(E) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testOfferNullThrows();
        testOfferAddsElementNaturalOrder();
        testOfferAddsElementWithComparator();
    }

    static void testOfferNullThrows() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            queue.offer(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfferAddsElementNaturalOrder() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean offered = queue.offer(10);
        if (!offered) {
            throw new Error("offer returned false unexpectedly");
        }
        Integer polled = queue.poll();
        if (polled == null || polled.intValue() != 10) {
            throw new Error("Polled element does not match offered element");
        }
    }

    static void testOfferAddsElementWithComparator() {
        Comparator<String> reverseCmp = new Comparator<String>() {
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        };
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(11, reverseCmp);
        boolean offered = queue.offer("a");
        if (!offered) {
            throw new Error("offer returned false unexpectedly");
        }
        String polled = queue.poll();
        if (polled == null || !polled.equals("a")) {
            throw new Error("Polled element does not match offered element");
        }
    }
}
