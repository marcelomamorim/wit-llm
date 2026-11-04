/*
 * @test
 * @summary Verifies observable behavior of ArrayBlockingQueue.size() across offers, polls, removals, clear and constructors
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySize();
        testSizeAfterOffersAndPolls();
        testSizeWhenFull();
        testFairConstructorSize();
        testCollectionConstructorSize();
        testSizeAfterClear();
        testSizeAfterRemove();
    }

    static void checkEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    static void checkTrue(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptySize() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        checkEquals(0, q.size(), "new queue size");
    }

    static void testSizeAfterOffersAndPolls() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        checkTrue(q.offer(1), "offer 1");
        checkEquals(1, q.size(), "size after 1 offer");
        checkTrue(q.offer(2), "offer 2");
        checkEquals(2, q.size(), "size after 2 offers");
        checkEquals(1, q.poll(), "poll head");
        checkEquals(1, q.size(), "size after poll");
        checkEquals(2, q.poll(), "poll head 2");
        checkEquals(0, q.size(), "size after draining");
        checkTrue(q.poll() == null, "poll on empty returns null");
        checkEquals(0, q.size(), "size after poll on empty");
    }

    static void testSizeWhenFull() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        checkTrue(q.offer(1), "offer 1");
        checkTrue(q.offer(2), "offer 2");
        checkEquals(2, q.size(), "size when full");
        checkTrue(!q.offer(3), "offer on full queue returns false");
        checkEquals(2, q.size(), "size unchanged after rejected offer");
    }

    static void testFairConstructorSize() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4, true);
        checkEquals(0, q.size(), "fair queue initial size");
        q.offer(10);
        q.offer(20);
        checkEquals(2, q.size(), "fair queue size after offers");
    }

    static void testCollectionConstructorSize() {
        ArrayBlockingQueue<Integer> q =
                new ArrayBlockingQueue<>(5, false, Arrays.asList(1, 2, 3));
        checkEquals(3, q.size(), "size after collection constructor");
        q.poll();
        checkEquals(2, q.size(), "size after poll from collection-initialized queue");
    }

    static void testSizeAfterClear() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.offer(1);
        q.offer(2);
        q.clear();
        checkEquals(0, q.size(), "size after clear");
        checkTrue(q.offer(3), "offer after clear");
        checkEquals(1, q.size(), "size after offer following clear");
    }

    static void testSizeAfterRemove() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        checkTrue(q.remove(Integer.valueOf(2)), "remove existing element");
        checkEquals(2, q.size(), "size after remove");
        checkTrue(!q.remove(Integer.valueOf(42)), "remove missing element");
        checkEquals(2, q.size(), "size unchanged after failed remove");
    }
}
