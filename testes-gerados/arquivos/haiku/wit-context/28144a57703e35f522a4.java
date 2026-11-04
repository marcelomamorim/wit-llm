/*
 * @test
 * @summary Checks public observable behavior of PriorityBlockingQueue.poll(), including empty queue, priority ordering, custom comparator and collection constructor
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPollOnEmptyQueueReturnsNull();
        testPollReturnsElementsInNaturalOrder();
        testPollWithReverseComparator();
        testPollFromCollectionConstructor();
        testPollAfterDrainReturnsNullAndKeepsSizeZero();
    }

    static void testPollOnEmptyQueueReturnsNull() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        checkEquals(null, q.poll(), "poll on empty queue");
        checkEquals(0, q.size(), "size after poll on empty queue");
        checkEquals(true, q.isEmpty(), "isEmpty after poll on empty queue");
    }

    static void testPollReturnsElementsInNaturalOrder() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(5);
        q.offer(1);
        q.offer(3);
        checkEquals(3, q.size(), "size before polling");
        checkEquals(Integer.valueOf(1), q.poll(), "first poll natural order");
        checkEquals(2, q.size(), "size after first poll");
        checkEquals(Integer.valueOf(3), q.poll(), "second poll natural order");
        checkEquals(Integer.valueOf(5), q.poll(), "third poll natural order");
        checkEquals(null, q.poll(), "poll after all elements removed");
    }

    static void testPollWithReverseComparator() {
        PriorityBlockingQueue<Integer> q =
                new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        q.offer(2);
        q.offer(7);
        q.offer(4);
        checkEquals(Integer.valueOf(7), q.poll(), "reverse comparator first poll");
        checkEquals(Integer.valueOf(4), q.poll(), "reverse comparator second poll");
        checkEquals(Integer.valueOf(2), q.poll(), "reverse comparator third poll");
        checkEquals(null, q.poll(), "reverse comparator poll on empty");
    }

    static void testPollFromCollectionConstructor() {
        PriorityBlockingQueue<Integer> q =
                new PriorityBlockingQueue<>(Arrays.asList(4, 2, 9));
        checkEquals(3, q.size(), "size from collection constructor");
        checkEquals(Integer.valueOf(2), q.poll(), "first poll from collection constructor");
        checkEquals(Integer.valueOf(4), q.poll(), "second poll from collection constructor");
        checkEquals(Integer.valueOf(9), q.poll(), "third poll from collection constructor");
        checkEquals(null, q.poll(), "poll after collection constructor drained");
    }

    static void testPollAfterDrainReturnsNullAndKeepsSizeZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(10);
        checkEquals(Integer.valueOf(10), q.poll(), "single element poll");
        checkEquals(null, q.poll(), "poll after drain first call");
        checkEquals(null, q.poll(), "poll after drain second call");
        checkEquals(0, q.size(), "size after repeated polls on empty queue");
    }

    static void checkEquals(Object expected, Object actual, String msg) {
        if (!Objects.equals(expected, actual)) {
            throw new RuntimeException(msg + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }
}
