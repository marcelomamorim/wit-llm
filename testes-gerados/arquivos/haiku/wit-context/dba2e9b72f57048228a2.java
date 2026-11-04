/*
 * @test
 * @summary Witup-generated tests for ArrayBlockingQueue.contains
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentReturnsFalse();
        testEmptyQueueDoesNotContainValues();
        testPresentAndAbsentElements();
        testWrapAroundIteration();
        testElementsRemovedByPollAreNotContained();
        testFairQueue();
        testCollectionConstructor();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.offer(1);
        check(!q.contains(null), "contains null must return false on non-empty queue");
        ArrayBlockingQueue<Integer> empty = new ArrayBlockingQueue<>(2);
        check(!empty.contains(null), "contains null must return false on empty queue");
    }

    static void testEmptyQueueDoesNotContainValues() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        check(!q.contains(1), "empty queue must not contain 1");
    }

    static void testPresentAndAbsentElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        check(q.contains(1), "1 must be contained");
        check(q.contains(2), "2 must be contained");
        check(q.contains(3), "3 must be contained");
        check(!q.contains(4), "4 must not be contained");
    }

    static void testWrapAroundIteration() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        check(q.poll() == 1, "poll must return head 1");
        check(q.offer(4), "offer after poll must succeed");
        check(q.contains(4), "wrapped element 4 must be contained");
        check(q.contains(2), "element 2 must be contained after wrap");
        check(q.contains(3), "element 3 must be contained after wrap");
        check(!q.contains(1), "removed element 1 must not be contained");
    }

    static void testElementsRemovedByPollAreNotContained() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.offer(1);
        q.offer(2);
        check(q.poll() == 1, "poll must return head 1");
        check(!q.contains(1), "polled element must not be contained");
        check(q.contains(2), "remaining element must be contained");
    }

    static void testFairQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2, true);
        q.offer(5);
        check(q.contains(5), "fair queue must contain 5");
        check(!q.contains(6), "fair queue must not contain 6");
    }

    static void testCollectionConstructor() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3, false, Arrays.asList(7, 8));
        check(q.contains(7), "collection-built queue must contain 7");
        check(q.contains(8), "collection-built queue must contain 8");
        check(!q.contains(9), "collection-built queue must not contain 9");
    }
}
